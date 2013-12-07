package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface IngredientFactory {

	Serveringredient createIngredientForServer(String title, Unit unit);

	Ingredient createIngredient(String title, int amount, Unit unit);

}
