package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.Map;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class RecipeFactory implements IRecipeFactory {

	@Override
	public IRecipe createRecipe(String titleRecipe,
			Map<IIngredient, Integer> ingredients) {
		Recipe recipe = new Recipe(titleRecipe, ingredients);
		return recipe;
	}

}
