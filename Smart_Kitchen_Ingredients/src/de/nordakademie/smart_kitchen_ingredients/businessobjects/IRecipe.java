package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.Map;

public interface IRecipe extends Comparable<IRecipe> {

	String getName();

	Map<IIngredient, Integer> getIngredients();

}
