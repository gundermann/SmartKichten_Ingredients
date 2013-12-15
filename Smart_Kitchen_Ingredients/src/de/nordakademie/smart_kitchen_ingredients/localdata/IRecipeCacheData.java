package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public interface IRecipeCacheData {

	public List<IRecipe> getAllRecipes();

}
