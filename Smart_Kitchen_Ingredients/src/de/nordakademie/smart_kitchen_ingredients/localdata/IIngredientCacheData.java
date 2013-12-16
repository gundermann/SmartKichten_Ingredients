package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public interface IIngredientCacheData {

	List<IIngredient> getAllIngredients();
}
