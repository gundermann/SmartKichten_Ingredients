package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class IngredientFactory implements IIngredientFactory {

	@Override
	public IIngredient createIngredient(String title, int amount, Unit unit) {
		Ingredient ingredient = new Ingredient(title, amount, unit);
		return ingredient;
	}

}
