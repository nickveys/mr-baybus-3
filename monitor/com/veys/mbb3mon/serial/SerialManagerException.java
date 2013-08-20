package com.veys.mbb3mon.serial;

/**
 * Generic exception for handling problems in the SerialManager.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 * @see java.lang.Exception
 */
public class SerialManagerException extends Exception {

	/**
	 * Create a new SerialManagerException.
	 * 
	 * @param message Text message to describe the exception
	 */
	public SerialManagerException(String message) {
		super(message);
	}
}
