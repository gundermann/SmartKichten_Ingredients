package de.nordakademie.smart_kitchen_ingredients.smartkitchen_server;

import java.net.UnknownHostException;

import de.nordakademie.smart_kitchen_ingredients.onlineconnection.IServerConnector;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public interface ISmartKichtenServerConnector extends IServerConnector {

	/**
	 * Sendet einen Post-Request an den Server, um eine Zutat dort hinzuzufügen.
	 * 
	 * @param jsonToPost
	 */
	void postIngredientToServer(String jsonToPost) throws UnknownHostException;
}
