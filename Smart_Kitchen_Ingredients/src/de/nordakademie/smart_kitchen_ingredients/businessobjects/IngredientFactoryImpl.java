package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class IngredientFactoryImpl implements IngredientFactory {

	@Override
	public IIngredient createIngredient(String title, int amount, Unit unit) {
		IngredientImpl ingredient = new IngredientImpl(title, amount, unit);
		return ingredient;
	}

}
