package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class IngredientFactory implements IIngredientFactory {

	@Override
	public IIngredient createIngredient(String title, Unit unit) {
		Ingredient ingredient = new Ingredient(title, unit);
		return ingredient;
	}

}
