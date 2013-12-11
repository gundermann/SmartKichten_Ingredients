package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.List;

public interface RecipeFactory {

	IRecipe createRecipe(String titleRecipe, List<IIngredient> ingredients);

}
