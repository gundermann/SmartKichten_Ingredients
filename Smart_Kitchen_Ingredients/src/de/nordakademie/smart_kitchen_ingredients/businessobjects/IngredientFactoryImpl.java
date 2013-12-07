package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class IngredientFactoryImpl implements IngredientFactory {

	@Override
	public Ingredient createIngredient(int amount, String title, Unit unit) {
		IngredientImpl ingredient = new IngredientImpl(amount, title, unit);
		return ingredient;
	}

	@Override
	public Serveringredient createIngredientForServer(String title, Unit unit) {
		ServeringredientImpl serveringredient = new ServeringredientImpl(title,
				unit);
		return serveringredient;

	}

}
