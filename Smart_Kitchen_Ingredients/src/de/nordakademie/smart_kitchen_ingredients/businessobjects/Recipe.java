package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.List;

public interface Recipe extends Comparable<Recipe> {

	String getTitle();

	List<Ingredient> getIngredients();

}
