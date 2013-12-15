package de.nordakademie.smart_kitchen_ingredients;

/**
 * 
 * @author niels
 * 
 */
public interface IServerConnector {
	/**
	 * Wandelt den Stream eines Servers in eine String um.
	 * 
	 * @param input
	 * @return String
	 */
	String getResponseForInput(String input);
}
