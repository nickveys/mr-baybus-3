package com.veys.mbb3mon;

import java.util.prefs.Preferences;

/**
 * Central go-to point for all preferences regarding MBB3Mon.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class MBB3Prefs {

	private static Preferences prefs;

	private static final String PREFSNODE = "/com/veys/MBB3";

	private static final String COMPORT_STRING = "COMPORT";
	private static final int COMPORT_DEFAULT = 1;
	
	private static final String BAUD_STRING = "BAUD";
	private static final int BAUD_DEFAULT = 115200;

	private static final String DEBUG_STRING = "DEBUG";
	private static final boolean DEBUG_DEFAULT = true;

	private static final String LF_STRING = "LF";
	private static final String LF_DEFAULT =
		"com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	private static Preferences getPrefs() {
		return Preferences.systemRoot().node(PREFSNODE);
	}

	/**
	 * Retrieve COM Port value from preferences.
	 * 
	 * @return integer COM Port number
	 */
	public static int getComPort() {
		return getPrefs().getInt(COMPORT_STRING, COMPORT_DEFAULT);
	}

	/**
	 * Store COM Port value in preferences.
	 * 
	 * @param comPort integer COM Port number
	 */
	public static void setComPort(int comPort) {
		getPrefs().putInt(COMPORT_STRING, comPort);
	}

	/**
	 * Retreive baud rate from preferences.
	 * 
	 * @return integer baud rate
	 */
	public static int getBaud() {
		return getPrefs().getInt(BAUD_STRING, BAUD_DEFAULT);
	}
	
	/**
	 * Store baud rate value in preferences.
	 * 
	 * @param baud baud rate
	 */
	public static void setBaud(int baud) {
		getPrefs().putInt(BAUD_STRING, baud);
	}
	
	/**
	 * Retrieve Debug flag from preferences.
	 * 
	 * @return boolean debug enable or disable
	 */
	public static boolean getDebug() {
		return getPrefs().getBoolean(DEBUG_STRING, DEBUG_DEFAULT);
	}

	/**
	 * Store debug flag in preferences.
	 * 
	 * @param debug boolean to enable or disable debug data
	 */
	public static void setDebug(boolean debug) {
		getPrefs().putBoolean(DEBUG_STRING, DEBUG_DEFAULT);
	}
	
	/**
	 * Retrieve Look and Feel preference.
	 * 
	 * @return String representing L&F class
	 */
	public static String getLookAndFeel() {
		return getPrefs().get(LF_STRING, LF_DEFAULT);
	}
	
	/**
	 * Store Look and Feel preference.
	 * 
	 * @param lookAndFeel String representing L&F class
	 */
	public static void setLookAndFeel(String lookAndFeel) {
		getPrefs().put(LF_STRING, lookAndFeel);
	}
}
