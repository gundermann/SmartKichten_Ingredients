package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.List;

public class RecipeFactoryImpl implements RecipeFactory {

	public Recipe createRecipe(String titleRecipe, List<IIngredient> ingredients) {
		RecipeImpl recipe = new RecipeImpl(titleRecipe, ingredients);
		return recipe;
	}

}
