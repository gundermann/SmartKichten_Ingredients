package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

public class Recipe implements IRecipe, IListElement {
	private String title;

	public Recipe(String name) {
		this.title = name;
	}

	@Override
	public int compareTo(IRecipe another) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public List<IIngredient> getIngredients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return getTitle();
	}

	@Override
	public String toString() {
		return getTitle();
	}
}
