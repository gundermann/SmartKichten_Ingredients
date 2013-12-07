package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.List;

public interface RecipeFactory {

	Recipe createRecipe(String titleRecipe, List<Ingredient> ingredients);

}
