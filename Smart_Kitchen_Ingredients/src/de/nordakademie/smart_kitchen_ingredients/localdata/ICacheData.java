package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public interface ICacheData {

	public void insertOrUpdateAllRecipesFromServer(
			Map<String[], List<String[]>> recipes);

	public void insertOrUpdateAllIngredientsFromServer(
			List<String[]> ingredients);

}
