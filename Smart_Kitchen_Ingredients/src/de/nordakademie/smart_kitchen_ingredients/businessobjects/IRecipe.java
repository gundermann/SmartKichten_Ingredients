package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.Map;

import de.nordakademie.smart_kitchen_ingredients.collector.IListElement;

public interface IRecipe extends IListElement {

	Map<IIngredient, Integer> getIngredients();

}
