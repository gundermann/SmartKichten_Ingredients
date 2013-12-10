/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collectors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author frederic.oppermann
 * @date 07.12.2013
 * @description
 */
public class IngredientDb implements IIngredientDb {

	@Override
	public List<IIngredient> getIngredients() {
		List<IIngredient> ingredients = new ArrayList<IIngredient>();

		for (Long index = 0L; index < 900000; index++) {
			String string = "";
			string = string + "";
		}

		for (int i = 0; i < 1000; i++) {
			ingredients.add(new Ingredient());
		}
		return ingredients;
	}
}
