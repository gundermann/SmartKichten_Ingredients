package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;

public interface IRecipeData {
	
	public List<String> getAllRecipeTitels();
	public List<Ingredient> getIngredientsForRecipe(String recipeTitle);

}
