package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.ArrayList;
import java.util.List;

public class RecipeImpl implements Recipe {

	private String title;
	private List<Ingredient> ingredients;

	public RecipeImpl() {
		ingredients = new ArrayList<Ingredient>();
	}

	public RecipeImpl(String title, List<Ingredient> ingredients) {
		this.ingredients = ingredients;
		this.title = title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	@Override
	public int compareTo(Recipe another) {
		return title.compareTo(another.getTitle());
	}

}
