package com.veys.mbb3mon.events;

/**
 * Represents an event involving a Fan.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class FanEvent extends AbstractEvent {

	private int number, speed;

	/**
	 * Create new FanEvent.  Stores packet information
	 * for the Fan packet that is to be handled.
	 * 
	 * @param source Event creator
	 */
	public FanEvent(Object source) {
		super(source, AbstractEvent.FAN_MASK);
	}

	/**
	 * @return Returns the fan number.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number The fan number to set.
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return Returns the speed.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed The speed to set.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
