package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements IRecipe {

	private String title;
	private List<IIngredient> ingredients;

	public Recipe() {
		ingredients = new ArrayList<IIngredient>();
	}

	public Recipe(String title, List<IIngredient> ingredients) {
		this.ingredients = ingredients;
		this.title = title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIngredients(List<IIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public List<IIngredient> getIngredients() {
		return ingredients;
	}

	@Override
	public int compareTo(IRecipe another) {
		return title.compareTo(another.getTitle());
	}

}