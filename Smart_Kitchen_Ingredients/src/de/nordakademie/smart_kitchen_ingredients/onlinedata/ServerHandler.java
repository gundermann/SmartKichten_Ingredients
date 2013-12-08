package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import java.util.TreeSet;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Recipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ServerIngredient;

/**
 * @author Niels Gundermann
 */
public interface ServerHandler {

	TreeSet<ServerIngredient> getIngredientListFromServer();

	TreeSet<Recipe> getRecipeListFromServer();

	void postIngredientToServer(Ingredient ingredient);

}
