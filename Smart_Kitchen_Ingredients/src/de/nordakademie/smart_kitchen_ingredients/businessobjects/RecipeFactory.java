package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.List;

public class RecipeFactory implements IRecipeFactory {

	public IRecipe createRecipe(String titleRecipe, List<IIngredient> ingredients) {
		Recipe recipe = new Recipe(titleRecipe, ingredients);
		return recipe;
	}

}
