package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

public class RecipeDbMock implements IDatabaseHelper<IRecipe> {

	@Override
	public List<IRecipe> getDatabaseEntries() {
		for (Long i = 0L; i < 900000; i++) {
			String nix = "";
			nix = nix + "";
		}

		List<IRecipe> recipes = new ArrayList<IRecipe>();
		for (int i = 0; i < 500; i++) {
			recipes.add(new Recipe("Recipe" + i));
		}
		return recipes;
	}

}
