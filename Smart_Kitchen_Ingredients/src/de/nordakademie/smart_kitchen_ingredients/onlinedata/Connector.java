package de.nordakademie.smart_kitchen_ingredients.onlinedata;

/**
 * @author Niels Gundermann
 */
public interface Connector {

	String getAllIngredientsFromServer();

	String getAllRecipesFromServer();

	void postIngredientToServer(String jsonToPost);
}
