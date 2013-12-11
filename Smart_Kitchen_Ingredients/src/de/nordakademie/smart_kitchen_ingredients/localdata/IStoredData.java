package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;

public interface IStoredData {

	List<IIngredient> getAllStoredIngredients();

	void insertOrUpdateBoughtIngredient(Ingredient boughtIngredient);
}
