package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface IIngredientFactory {

	IIngredient createIngredient(String title, int amount, Unit unit);

}
