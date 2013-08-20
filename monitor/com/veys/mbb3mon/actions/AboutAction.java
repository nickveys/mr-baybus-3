package com.veys.mbb3mon.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import com.veys.mbb3mon.MBB3Mon;

/**
 * About this program dialog box.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class AboutAction extends AbstractAction {

	public static final String NAME_ABOUT = "About";
	public static final String DESC_ABOUT = "About this program";
	public static final int MNEMONIC_ABOUT = 'A';

	/**
	 * Create new instance of AboutAction.
	 */
	public AboutAction() {
		super();
		putValue(Action.NAME, NAME_ABOUT);
		putValue(Action.LONG_DESCRIPTION, DESC_ABOUT);
		putValue(Action.MNEMONIC_KEY, new Integer(MNEMONIC_ABOUT));
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {
		String about1 = MBB3Mon.TITLE + " " + MBB3Mon.VERSION + "\n";
		String about2 = "(c) 2003, 2004  Nick Veys, Veys Enterprises";
		infoDialog("About MBB3Mon", about1 + about2);
	}

	/**
	 * Create an information dialog box.
	 * 
	 * @param title Text for the title
	 * @param message Text for the message
	 */
	private void infoDialog(String title, String message) {
		int flag = JOptionPane.INFORMATION_MESSAGE;
		JOptionPane.showMessageDialog(null, message, title, flag);
	}
}
