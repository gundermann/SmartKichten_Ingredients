package de.nordakademie.smart_kitchen_ingredients.onlinedata;

public interface ISKIServerConnector {

	String getAllIngredientsFromServer();

	String getAllRecipesFromServer();

	void postIngredientToServer(String jsonToPost);
}
