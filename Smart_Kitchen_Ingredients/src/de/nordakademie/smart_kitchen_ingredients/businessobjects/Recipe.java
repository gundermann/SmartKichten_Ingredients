package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.List;

public interface Recipe {

	String getTitleRecipe();

	List<Ingredient> getIngredients();

}
