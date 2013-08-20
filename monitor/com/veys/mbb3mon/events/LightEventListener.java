package com.veys.mbb3mon.events;

import java.util.EventListener;

/**
 * Interface required to listen to LightEvents.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public interface LightEventListener extends EventListener {

	public void lightSettingChanged(LightEvent e);
}
