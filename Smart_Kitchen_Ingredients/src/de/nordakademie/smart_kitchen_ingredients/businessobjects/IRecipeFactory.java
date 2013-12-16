package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.List;

public interface IRecipeFactory {

	IRecipe createRecipe(String titleRecipe, List<IIngredient> ingredients);

}
