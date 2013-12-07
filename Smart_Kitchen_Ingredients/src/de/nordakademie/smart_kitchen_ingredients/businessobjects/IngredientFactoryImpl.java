package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class IngredientFactoryImpl implements IngredientFactory {

	@Override
	public Ingredient createIngredient(double id, String title, Unit unit) {
		IngredientImpl ingredient = new IngredientImpl(id, title, unit);
		return ingredient;
	}

	public ServerIngredient createIngredientForServer() {

	}

}
