package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;
import java.util.Map;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public interface ICacheData {

	public List<IRecipe> insertOrUpdateAllRecipesFromServer(
			Map<String[], List<String[]>> recipes);

	public List<IIngredient> insertOrUpdateAllIngredientsFromServer(
			List<String[]> ingredients);

	public boolean itemExists(String itemTitle);

}
