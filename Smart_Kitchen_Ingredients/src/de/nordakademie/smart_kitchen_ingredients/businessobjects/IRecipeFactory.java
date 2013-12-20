package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.Map;

/**
 * 
 * @author Frauke Trautmann
 * 
 */
public interface IRecipeFactory {
	
	/**
	 * Erstellt ein neues Rezept.
	 * @param titleRecipe
	 * @param ingredients
	 * @return IRecipe
	 */

	IRecipe createRecipe(String titleRecipe,
			Map<IIngredient, Integer> ingredients);

}
