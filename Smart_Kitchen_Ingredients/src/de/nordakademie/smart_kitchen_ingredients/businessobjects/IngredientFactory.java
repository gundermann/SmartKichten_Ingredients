package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface IngredientFactory {

	Ingredient createIngredient(double id, String title, Unit unit);

}
