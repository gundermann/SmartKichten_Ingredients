package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import java.util.TreeSet;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Recipe;

public interface IngredientFilter {

	TreeSet<Ingredient> getIngredientListFromServer();

	TreeSet<Recipe> getRecipeListFromServer();

	void postIngredientToServer(Ingredient ingredient);

}
