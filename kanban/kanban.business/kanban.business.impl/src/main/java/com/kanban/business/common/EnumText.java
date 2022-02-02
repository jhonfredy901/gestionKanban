/**
 * 
 */
package com.kanban.business.common;

/**
 * @author jhernandez
 *
 */
public enum EnumText {

	ACTION("ACTION"),
	PASS("PASS"),
	IDENTIFICATION("IDENTIFICATION"),
	NAME("NAME"),
	LASTNAME("LASTNAME"),
	ID("ID"),
	STARTPOSITION("STARTPOSITION"),
	MAXRESULT("MAXRESULT")
	
	
	
	;

	/**
	 * 
	 */
	private final String value;

	/**
	 * Constructor
	 * 
	 * @param value
	 */
	private EnumText(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
