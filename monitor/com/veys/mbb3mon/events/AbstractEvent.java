package com.veys.mbb3mon.events;

import java.util.EventObject;

/**
 * Raw event to be extended.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class AbstractEvent extends EventObject {

	public static final int FAN_MASK = 1;
	public static final int LIGHT_MASK = 2;
	public static final int TEMPERATURE_MASK = 3;

	private int type;

	/**
	 * Create new AbstractEvent.
	 * 
	 * @param source Source of the event
	 * @param type Type of event
	 */
	public AbstractEvent(Object source, int type) {
		super(source);
		this.type = type;
	}

	/**
	 * @return Returns the event type
	 */
	public int getType() {
		return type;
	}
}
