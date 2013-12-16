package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import de.nordakademie.smart_kitchen_ingredients.IServerConnector;

/**
 * 
 * @author niels
 * 
 */
public interface ISmartKichtenServerConnector extends IServerConnector {

	/**
	 * Sendet einen Post-Request an den Server, um eine Zutat dort hinzuzufügen.
	 * 
	 * @param jsonToPost
	 */
	void postIngredientToServer(String jsonToPost);
}
