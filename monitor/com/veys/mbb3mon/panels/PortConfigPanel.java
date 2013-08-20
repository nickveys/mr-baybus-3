package com.veys.mbb3mon.panels;

import javax.swing.JPanel;

import com.veys.mbb3mon.serial.SerialManager;

/**
 * PortConfigPanel presents the user with a list of available
 * COM Ports, baud rates and other port options to choose from.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class PortConfigPanel extends JPanel {

	private SerialManager serialManager;

	/**
	 * Create new instance of PortChooser.
	 * 
	 * @param serialManager Initialized SerialManager to configure
	 */
	public PortConfigPanel(SerialManager serialManager) {
		super();
		this.serialManager = serialManager;
	}
}
