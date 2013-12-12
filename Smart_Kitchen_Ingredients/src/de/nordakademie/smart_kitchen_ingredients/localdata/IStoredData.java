package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public interface IStoredData {

	List<IIngredient> getAllStoredIngredients();

	void insertOrUpdateIngredient(IIngredient boughtIngredient);
}
