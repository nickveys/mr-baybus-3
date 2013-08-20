package com.veys.mbb3mon.events;

/**
 * Represents an event involving a Light.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class LightEvent extends AbstractEvent {

	public static final int ON = 0;
	public static final int OFF = 1;
	public static final int STROBE = 2;
	public static final int HDD = 3;
	public static final int HDD_INV = 4;

	private int number, mode;

	/**
	 * Create new LightEvent.  Stores packet information
	 * for the Light packet that is to be handled.
	 * 
	 * @param source Event creator
	 */
	public LightEvent(Object source) {
		super(source, AbstractEvent.LIGHT_MASK);
	}

	/**
	 * @return Returns the light mode.
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @param mode The light mode to set.
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * @return Returns the light number.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number The light number to set.
	 */
	public void setNumber(int number) {
		this.number = number;
	}
}
