package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.List;

public interface IRecipe extends Comparable<IRecipe> {

	String getTitle();

	List<IIngredient> getIngredients();

}
