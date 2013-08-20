package com.veys.mbb3mon.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import com.veys.mbb3mon.serial.SerialManager;
import com.veys.mbb3mon.serial.SerialManagerException;

/**
 * ConnectAction defines the action taken when the user wants
 * to establish a serial connection to the device.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class ConnectAction extends AbstractAction {

	public static final String NAME_CONN = "Connect";
	public static final String DESC_CONN = "Connect to device";
	public static final int MNEMONIC_CONN = 'C';

	private SerialManager serialManager;

	/**
	 * Create new instance of ConnectAction.
	 * 
	 * @param serialManager Initialized SerialManager for use in connecting
	 */
	public ConnectAction(SerialManager serialManager) {
		super();
		this.serialManager = serialManager;
		putValue(Action.NAME, NAME_CONN);
		putValue(Action.LONG_DESCRIPTION, DESC_CONN);
		putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_CONN));
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {
		System.out.println("Connecting " + serialManager.getComPortName());
		try {
			serialManager.open();
		} catch (SerialManagerException e) {
			String msg = e.getMessage();
			JOptionPane.showMessageDialog(null, msg);
		}
	}
}
