package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public interface ICacheRecipes {
	
	public void cacheAllRecipes(Map<String[],List<String[]>> recipes);
	public void cacheAllIngredients(List<String[]> ingredients);

}
