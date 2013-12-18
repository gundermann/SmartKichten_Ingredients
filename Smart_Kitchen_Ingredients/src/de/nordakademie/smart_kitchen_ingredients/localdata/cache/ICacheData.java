package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

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

	/**
	 * KEY: String Array - Value: List of String Array KEY/rezept 0=id 1=titel
	 * VALUE/zutaten 0=id 1=titel 2=einheit 3=menge
	 * 
	 * @param recipes
	 * @return
	 */
	public List<IRecipe> insertOrUpdateAllRecipesFromServer(
			Map<String[], List<String[]>> recipes);

	public List<IIngredient> insertOrUpdateAllIngredientsFromServer(
			List<String[]> ingredients);

	public boolean itemExists(String itemTitle);

}
