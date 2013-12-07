package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface IngredientFactory {

	Serveringredient createIngredientForServer(String title, Unit unit);

	Ingredient createIngredient(int amount, String title, Unit unit);

}
