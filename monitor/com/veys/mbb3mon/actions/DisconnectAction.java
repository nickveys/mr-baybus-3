package com.veys.mbb3mon.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import com.veys.mbb3mon.serial.SerialManager;
import com.veys.mbb3mon.serial.SerialManagerException;

/**
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class DisconnectAction extends AbstractAction {

	public static final String NAME_DISC = "Disconnect";
	public static final String DESC_DISC = "Disconnect from device";
	public static final int MNEMONIC_DISC = 'D';

	private SerialManager serialManager;

	/**
	 * Create new instance of DisconnectAction.
	 * 
	 * @param serialManager Initialized SerialManager to disconnect from
	 */
	public DisconnectAction(SerialManager serialManager) {
		super();
		this.serialManager = serialManager;
		putValue(Action.NAME, NAME_DISC);
		putValue(Action.LONG_DESCRIPTION, DESC_DISC);
		putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_DISC));
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {
		System.out.println("Disconnecting " + serialManager.getComPortName());
		try {
			serialManager.close();
		} catch (SerialManagerException e) {
			String msg = e.getMessage();
			JOptionPane.showMessageDialog(null, msg);
		}
	}
}
