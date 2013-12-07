package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.List;

public class RecipeImpl implements Recipe {

	private final String titleRecipe;
	private final List<Ingredient> ingredients;

	public RecipeImpl(String titleRecipe, List<Ingredient> ingredients) {
		this.ingredients = ingredients;
		this.titleRecipe = titleRecipe;
	}

	@Override
	public String getTitleRecipe() {
		return titleRecipe;
	}

	@Override
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

}
