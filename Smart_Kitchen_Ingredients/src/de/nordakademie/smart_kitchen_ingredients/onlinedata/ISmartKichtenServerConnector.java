package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import de.nordakademie.smart_kitchen_ingredients.IServerConnector;

public interface ISmartKichtenServerConnector extends IServerConnector {

	void postIngredientToServer(String jsonToPost);
}
