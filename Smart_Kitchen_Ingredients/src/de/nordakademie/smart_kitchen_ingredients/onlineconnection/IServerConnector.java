package de.nordakademie.smart_kitchen_ingredients.onlineconnection;

/**
 * 
 * @author Niels Gundermann
 * 
 */
public interface IServerConnector {
	/**
	 * Wandelt den Stream eines Servers in eine String um.
	 * 
	 * @param input
	 * @param apikey
	 * @return String
	 */
	String getResponseForInput(String input);
}
