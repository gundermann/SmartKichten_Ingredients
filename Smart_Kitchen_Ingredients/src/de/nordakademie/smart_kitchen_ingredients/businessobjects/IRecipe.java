package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.Map;

import de.nordakademie.smart_kitchen_ingredients.collector.IListElement;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public interface IRecipe extends IListElement {

	Map<IIngredient, Integer> getIngredients();

}
