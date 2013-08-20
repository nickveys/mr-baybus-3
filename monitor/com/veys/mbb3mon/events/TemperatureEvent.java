package com.veys.mbb3mon.events;

/**
 * Represents an event involving temperature.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class TemperatureEvent extends AbstractEvent {

	private int sensorNumber;
	private double temperature;

	/**
	 * Create new TemperatureEvent.  Stores packet information
	 * for the Temperature packet that is to be handled.
	 * 
	 * @param source Event creator
	 */
	public TemperatureEvent(Object source) {
		super(source, AbstractEvent.TEMPERATURE_MASK);
	}

	public int getSensorNumber() {
		return sensorNumber;
	}

	public void setSensorNumber(int sensorNumber) {
		this.sensorNumber = sensorNumber;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
}