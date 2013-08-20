package com.veys.mbb3mon.events;

import java.util.EventListener;

import javax.swing.event.EventListenerList;

/**
 * A class to be extended by anyone wishing to
 * publish the various supported events.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class EventPublisher {

	private EventListenerList listenerList;

	/**
	 * Create new EventPublisher.
	 */
	public EventPublisher() {
		listenerList = new EventListenerList();
	}

	/**
	 * Add a FanEventListener to the list of Listeners.
	 * 
	 * @param l Object wishing to listen to FanEvents
	 */
	public void addFanEventListener(FanEventListener l) {
		listenerList.add(FanEventListener.class, l);
	}

	/**
	 * Add a LightEventListener to the list of Listeners.
	 * 
	 * @param l Object wishing to listen to LightEvents
	 */
	public void addLightEventListener(LightEventListener l) {
		listenerList.add(LightEventListener.class, l);
	}

	/**
	 * Add a TemperatureEventListener to the list of Listeners.
	 * 
	 * @param l Object wishing to listen to TemperatureEvents
	 */
	public void addTemperatureEventListener(TemperatureEventListener l) {
		listenerList.add(TemperatureEventListener.class, l);
	}

	/**
	 * Notify listeners of a change in fan speed.
	 * 
	 * @param e Populated FanEvent object
	 */
	protected void fireFanSpeedChanged(FanEvent e) {
		EventListener[] listeners;
		listeners = listenerList.getListeners(FanEventListener.class);

		for (int i = 0; i < listeners.length; i++) {
			((FanEventListener) listeners[i]).fanSpeedChanged(e);
		}
	}

	/**
	 * Notify listeners of a change in light settings.
	 * 
	 * @param e Populated LightEvent object
	 */
	protected void fireLightSettingChanged(LightEvent e) {
		EventListener[] listeners;
		listeners = listenerList.getListeners(LightEventListener.class);

		for (int i = 0; i < listeners.length; i++) {
			((LightEventListener) listeners[i]).lightSettingChanged(e);
		}
	}

	/**
	 * Notify listeners of a change in temperature.
	 * 
	 * @param e Populated TemperatureEvent object
	 */
	protected void fireTemperatureChanged(TemperatureEvent e) {
		EventListener[] listeners;
		listeners = listenerList.getListeners(TemperatureEventListener.class);

		for (int i = 0; i < listeners.length; i++) {
			((TemperatureEventListener) listeners[i]).temperatureChanged(e);
		}
	}

	/**
	 * Remove a FanEventListener from the list of Listeners.
	 * 
	 * @param l Object wishing to stop listening to FanEvents
	 */
	public void removeFanEventListener(FanEventListener l) {
		listenerList.remove(FanEventListener.class, l);
	}

	/**
	 * Remove a LightEventListener from the list of Listeners.
	 * 
	 * @param l Object wishing to stop listening to LightEvents
	 */
	public void removeFanEventListener(LightEventListener l) {
		listenerList.remove(LightEventListener.class, l);
	}

	/**
	 * Remove a TemperatureEventListener from the list of Listeners.
	 * 
	 * @param l Object wishing to stop listening to TemperatureEvents
	 */
	public void removeFanEventListener(TemperatureEventListener l) {
		listenerList.remove(TemperatureEventListener.class, l);
	}
}
