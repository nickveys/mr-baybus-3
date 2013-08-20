package com.veys.mbb3mon.protocol;

/**
 * Represents an exception during protocol processing.
 * 
 * @author Nick Veys
 * @version $Revision$ $Date$
 */
public class ProtocolException extends Exception {
	
	/**
	 * Create new ProtocolException.
	 * 
	 * @param message Message describing exception
	 * @see java.lang.Exception
	 */
	public ProtocolException(String message) {
		super(message);
	}
}
