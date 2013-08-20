package com.veys.mbb3mon.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import com.veys.mbb3mon.serial.SerialManager;

/**
 * PortAction allows the user to choose serial port configuration.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class PortAction extends AbstractAction {

	public static final String NAME_PORT = "Configure Port";
	public static final String DESC_PORT = "Set up your serial port";
	public static final int MNEMONIC_PORT = 'P';

	private SerialManager serialManager;

	/**
	 * Create new instance of PortAction.
	 * 
	 * @param serialManager Initialized SerialManager to configure
	 */
	public PortAction(SerialManager serialManager) {
		super();
		this.serialManager = serialManager;
		putValue(Action.NAME, NAME_PORT);
		putValue(Action.LONG_DESCRIPTION, DESC_PORT);
		putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_PORT));
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {
		String portsAvail = "COM Ports available: ";
		int[] ports = serialManager.getAvailableComPorts();

		if (ports != null) {
			for (int i = 0; i < ports.length; i++) {
				portsAvail += "COM" + ports[i] + " ";
			}
		}

		JOptionPane.showMessageDialog(null, portsAvail, "Available ports", 0);
		// TODO: Replace this with PortConfigPanel display and response
	}
}
