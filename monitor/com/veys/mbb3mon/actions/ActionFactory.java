package com.veys.mbb3mon.actions;

import javax.swing.Action;

import com.veys.mbb3mon.serial.SerialManager;

/**
 * The ActionFactory forces the Actions to be singletons, thus reducing
 * recreation of Actions for various things such as toolbars, context menus,
 * menu bars, etc.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class ActionFactory {

	private Action aboutAction;
	private Action connectAction;
	private Action disconnectAction;
	private Action portAction;

	/**
	 * Creates a new instance of the ActionFactory class.
	 * 
	 * @param serialManager A initialized SerialManager for the actions to use
	 */
	public ActionFactory(SerialManager serialManager) {
		aboutAction = new AboutAction();
		connectAction = new ConnectAction(serialManager);
		disconnectAction = new DisconnectAction(serialManager);
		portAction = new PortAction(serialManager);
	}

	/**
	 * Get AboutAction singleton.
	 * 
	 * @return AboutAction instance
	 * @see com.veys.mbb3mon.actions.AboutAction
	 */
	public Action createAboutAction() {
		return aboutAction;
	}

	/**
	 * Get ConnectAction singleton.
	 * 
	 * @return ConnectAction instance
	 * @see com.veys.mbb3mon.actions.ConnectAction
	 */
	public Action createConnectAction() {
		return connectAction;
	}

	/**
	 * Get DisconnectAction singleton.
	 * 
	 * @return DisconnectAction instance
	 * @see com.veys.mbb3mon.actions.DisconnectAction
	 */
	public Action createDisconnectAction() {
		return disconnectAction;
	}

	/**
	 * Get PortAction singleton.
	 * 
	 * @return PortAction instance
	 * @see com.veys.mbb3mon.actions.PortAction
	 */
	public Action createPortAction() {
		return portAction;
	}
}
