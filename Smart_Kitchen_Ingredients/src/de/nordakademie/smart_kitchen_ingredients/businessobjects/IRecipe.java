package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.Map;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public interface IRecipe extends IListElement {

	Map<IIngredient, Integer> getIngredients();

}
