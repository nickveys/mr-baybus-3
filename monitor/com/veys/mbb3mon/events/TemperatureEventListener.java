package com.veys.mbb3mon.events;

import java.util.EventListener;

/**
 * Interface required to listen to TemperatureEvents.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public interface TemperatureEventListener extends EventListener {

	public void temperatureChanged(TemperatureEvent e);
}
