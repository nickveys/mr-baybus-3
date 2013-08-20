package com.veys.mbb3mon.serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.JOptionPane;

import com.veys.mbb3mon.MBB3Prefs;
import com.veys.mbb3mon.events.AbstractEvent;
import com.veys.mbb3mon.protocol.Parser;
import com.veys.mbb3mon.protocol.ProtocolException;

/**
 * The SerialManager contains the methods and control logic
 * to manipulate the serial port at your every whim.
 * 
 * TODO: Make this a thread?
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class SerialManager implements SerialPortEventListener {

	private final int TIMEOUT = 2000; // ms to wait for port to open
	private final int BAUD = 115200;
	private final int DATA = SerialPort.DATABITS_8;
	private final int STOP = SerialPort.STOPBITS_1;
	private final int PARITY = SerialPort.PARITY_NONE;
	private final int FLOW = SerialPort.FLOWCONTROL_NONE;

	private static SerialManager myself; // singleton

	private CommPortIdentifier portId = null;
	private SerialPort serialPort = null;
	private InputStream inStream = null;
	private OutputStream outStream = null;
	private Parser parser = null;

	/**
	 * Create a new instance of SerialManager.
	 */
	private SerialManager(Parser parser) {
		this.parser = parser;
		try {
			initializePort();
		} catch (SerialManagerException e) {
			String msg = e.getMessage();
			JOptionPane.showMessageDialog(null, msg);
		}
	}

	public static SerialManager getInstance() {
		if (myself == null) {
			myself = new SerialManager(new Parser());
		}
		return myself;
	}

	/**
	 * Initializes the desired COM port, will result in 
	 * an error if anything fails or is unruly.
	 * 
	 * @throws SerialManagerException on failure
	 */
	public void initializePort() throws SerialManagerException {
		String port = "COM" + MBB3Prefs.getComPort();
		try {
			portId = CommPortIdentifier.getPortIdentifier(port);
		} catch (NoSuchPortException e) {
			portId = null;
			throw new SerialManagerException("No such port (" + port + ").");
		}
	}

	/**
	 * Attempt to open the current serial port, will result in
	 * an error if the port is unable to be opened.
	 * 
	 * @throws SerialManagerException on failure
	 */
	public void open() throws SerialManagerException {
		if (portId != null) {
			if (serialPort == null) {
				try {
					serialPort = (SerialPort) portId.open("MBB3Mon", TIMEOUT);
				} catch (PortInUseException e) {
					serialPort = null;
					throw new SerialManagerException("Port already in use.");
				}
				try {
					serialPort.setSerialPortParams(BAUD, DATA, STOP, PARITY);
					serialPort.setFlowControlMode(FLOW);
				} catch (UnsupportedCommOperationException e) {
					throw new SerialManagerException("Error setting port settings.");
				}
				try {
					inStream = serialPort.getInputStream();
				} catch (IOException e) {
					serialPort.close();
					serialPort = null;
					inStream = null;
					throw new SerialManagerException("Error getting input stream.");
				}
				try {
					outStream = serialPort.getOutputStream();
				} catch (IOException e) {
					serialPort.close();
					serialPort = null;
					outStream = null;
					throw new SerialManagerException("Error getting output stream.");
				}
			} else {
				throw new SerialManagerException("Port already open.");
			}
		} else {
			throw new SerialManagerException("Error opening port, perhaps none found.");
		}
	}

	/**
	 * Close serial port current opened, will result
	 * in an error if the port is not current open.
	 * 
	 * @throws SerialManagerException on failure
	 */
	public void close() throws SerialManagerException {
		if (inStream != null) {
			try {
				inStream.close();
			} catch (IOException e) {
				throw new SerialManagerException("Error closing input stream.");
			}
			inStream = null;
		}
		if (outStream != null) {
			try {
				outStream.close();
			} catch (IOException e) {
				throw new SerialManagerException("Error closing output stream.");
			}
			outStream = null;
		}
		if (serialPort != null) {
			serialPort.close();
			serialPort = null;
		} else {
			throw new SerialManagerException("Port not open.");
		}
	}

	/**
	 * Get an array of COM Port numbers available on the current system.
	 * 
	 * @return int[] of COM numbers, null if none
	 */
	public int[] getAvailableComPorts() {
		Enumeration ids = CommPortIdentifier.getPortIdentifiers();
		ArrayList comPorts = new ArrayList();

		if (ids != null) {
			while (ids.hasMoreElements()) {
				CommPortIdentifier id = (CommPortIdentifier) ids.nextElement();
				String port = id.getName();
				if (port.substring(0, 3).equals("COM")) {
					comPorts.add(port.substring(3, 4));
				}
			}
		}

		int[] ports = null;
		if (comPorts.size() > 0) {
			ports = new int[comPorts.size()];
			for (int i = 0; i < ports.length; i++) {
				ports[i] = Integer.parseInt((String) comPorts.get(i));
			}
		}
		return ports;
	}

	/**
	 * @return String name of COM Port
	 */
	public String getComPortName() {
		return portId == null ? "" : portId.getName();
	}

	/**
	 * Send a new event out over serial.
	 * 
	 * @param evt The event to send
	 * @throws SerialManagerException upon any errors
	 */
	public void sendEvent(AbstractEvent evt) throws SerialManagerException {
		if (outStream != null) {
			try {
				outStream.write(parser.parseOutgoing(evt));
			} catch (ProtocolException e) {
				throw new SerialManagerException(e.getMessage());
			} catch (IOException e) {
				throw new SerialManagerException(e.getMessage());
			}
		}
	}

	/**
	 * Serial Event handler.
	 * Retreives data and calls parser when needed.
	 * 
	 * @see javax.comm.SerialPortEventListener#serialEvent(javax.comm.SerialPortEvent)
	 */
	public void serialEvent(SerialPortEvent spe) {
		switch (spe.getEventType()) {
			case SerialPortEvent.DATA_AVAILABLE :
				int packetSize = Parser.PACKET_SIZE;
				byte[] buffer = new byte[packetSize];
				try {
					while (inStream.available() >= packetSize) {
						int numBytes = inStream.read(buffer);
						try {
							parser.parseIncoming(buffer);
						} catch (ProtocolException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default :
				break;
		}
	}
}
