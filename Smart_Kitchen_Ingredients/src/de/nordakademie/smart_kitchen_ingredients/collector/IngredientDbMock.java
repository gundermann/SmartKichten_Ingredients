/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.localdata.IIngredientData;

/**
 * @author frederic.oppermann
 * @date 07.12.2013
 * @description
 */
public class IngredientDbMock implements IIngredientData {

	@Override
	public List<IIngredient> getAllIngredients() {
		for (Long i = 0L; i < 90000; i++) {
			String nix = "";
			nix = nix + "";
		}

		List<IIngredient> ingredients = new ArrayList<IIngredient>();
		for (int i = 0; i < 50; i++) {
			ingredients.add(new Ingredient());
		}
		return ingredients;
	}
}
