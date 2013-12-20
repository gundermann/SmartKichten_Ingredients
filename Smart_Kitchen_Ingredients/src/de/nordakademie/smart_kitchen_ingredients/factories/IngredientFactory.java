package de.nordakademie.smart_kitchen_ingredients.factories;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class IngredientFactory {

	public static IIngredient createIngredient(String title, Unit unit) {
		return new Ingredient(title, unit);
	}

}
