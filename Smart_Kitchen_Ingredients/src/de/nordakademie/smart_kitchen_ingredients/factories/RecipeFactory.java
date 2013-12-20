package de.nordakademie.smart_kitchen_ingredients.factories;

import java.util.Map;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Recipe;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class RecipeFactory {

	public static IRecipe createRecipe(String titleRecipe,
			Map<IIngredient, Integer> ingredients) {
		Recipe recipe = new Recipe(titleRecipe, ingredients);
		return recipe;
	}

}
