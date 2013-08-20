package com.veys.mbb3mon.events;

import java.util.EventListener;

/**
 * Interface required to listen to FanEvents.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public interface FanEventListener extends EventListener {

	public void fanSpeedChanged(FanEvent e);
}
