package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class IngredientFactoryImpl implements IngredientFactory {

	@Override
	public Ingredient createIngredient(String title, int amount, Unit unit) {
		IngredientImpl ingredient = new IngredientImpl(title, amount, unit);
		return ingredient;
	}

	@Override
	public Serveringredient createIngredientForServer(String title, Unit unit) {
		ServeringredientImpl serveringredient = new ServeringredientImpl(title,
				unit);
		return serveringredient;

	}

}
