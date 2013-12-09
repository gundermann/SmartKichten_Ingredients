package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface IngredientFactory {

	IIngredient createIngredient(String title, int amount, Unit unit);

}
