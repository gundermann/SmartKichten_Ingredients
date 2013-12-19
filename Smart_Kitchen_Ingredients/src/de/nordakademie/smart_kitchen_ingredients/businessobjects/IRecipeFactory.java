package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.Map;

/**
 * 
 * @author Frauke Trautmann
 * 
 */
public interface IRecipeFactory {

	IRecipe createRecipe(String titleRecipe,
			Map<IIngredient, Integer> ingredients);

}
