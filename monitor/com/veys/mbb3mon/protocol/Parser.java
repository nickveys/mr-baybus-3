package com.veys.mbb3mon.protocol;

import com.veys.mbb3mon.events.AbstractEvent;
import com.veys.mbb3mon.events.EventPublisher;
import com.veys.mbb3mon.events.FanEvent;
import com.veys.mbb3mon.events.LightEvent;
import com.veys.mbb3mon.events.TemperatureEvent;

/**
 * The parser is in charge of decoding all incoming packets
 * and dispatching to related events to the listeners.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class Parser extends EventPublisher {

	public static final int PACKET_SIZE = 3;

	private static final byte FAN_SPEED_UPDATE = 0x00;
	private static final byte LIGHT_MODE_UPDATE = 0x10;
	private static final byte TEMPERATURE_UPDATE = 0x20;

	/**
	 * Create a new parser.
	 */
	public Parser() {
		super();
	}

	/**
	 * Parse the data in the array of new bytes.
	 * Events will be triggered based on the contents of the array.
	 * 
	 * @param data byte[] of new packet data
	 * @throws ProtocolException upon any protocol violation
	 */
	public void parseIncoming(byte[] data) throws ProtocolException {
		if (data.length == PACKET_SIZE) {
			switch (data[0]) {
				case FAN_SPEED_UPDATE :
					FanEvent fe = new FanEvent(this);
					fe.setNumber((int) data[1]);
					fe.setSpeed((int) data[2]);
					fireFanSpeedChanged(fe);
					break;
				case LIGHT_MODE_UPDATE :
					LightEvent le = new LightEvent(this);
					le.setNumber((int) data[1]);
					le.setMode((int) data[2]);
					fireLightSettingChanged(le);
					break;
				case TEMPERATURE_UPDATE :
					TemperatureEvent te = new TemperatureEvent(this);
					te.setSensorNumber((int) data[1]);
					te.setTemperature((double) data[2]);
					fireTemperatureChanged(te);
					// TODO: Temperature conversion byte->double
					break;
			}
		} else {
			throw new ProtocolException("Incoming packet length incorrect.");
		}
	}

	/**
	 * Parse the outgoing event packet and send it over serial.
	 * Events will be triggered based on the contents of the event.
	 * 
	 * @param evt Outgoing event
	 * @throws ProtocolException upon any protocol violation
	 */
	public byte[] parseOutgoing(AbstractEvent evt) throws ProtocolException {
		if (evt instanceof FanEvent) {
			int fanNum = ((FanEvent) evt).getNumber();
			if ((fanNum >= 0) && (fanNum <= 3)) {
				int fanPct = ((FanEvent) evt).getSpeed();
				if ((fanPct >= 0) && (fanPct <= 100)) {
					byte[] packet = new byte[3];
					packet[0] = 0x00;
					packet[1] = (byte) fanNum;
					packet[2] = (byte) fanPct;
					fireFanSpeedChanged((FanEvent) evt);
					return packet;
				} else {
					throw new ProtocolException("Fan speed out of bounds.");
				}
			} else {
				throw new ProtocolException("Fan number out of bounds.");
			}
		} else if (evt instanceof LightEvent) {
			int lightNum = ((LightEvent) evt).getNumber();
			if ((lightNum >= 0) && (lightNum <= 1)) {
				int lightMode = ((LightEvent) evt).getMode();
				if ((lightMode >= 0) && (lightMode <= 5)) {
					byte[] packet = new byte[3];
					packet[0] = 0x01;
					packet[1] = (byte) lightNum;
					packet[2] = (byte) lightMode;
					fireLightSettingChanged((LightEvent) evt);
					return packet;
				} else {
					throw new ProtocolException("Light mode out of bounds.");
				}
			} else {
				throw new ProtocolException("Light number out of bounds.");
			}
		} else if (evt instanceof TemperatureEvent) {
			// TODO: build temperature packet, send
			fireTemperatureChanged((TemperatureEvent) evt);
			return null;
		} else {
			throw new ProtocolException("Outgoing packet not known.");
		}
	}
}
