package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.collector.IListElement;

public class Recipe implements IRecipe, IListElement {

	private String name;
	private List<IIngredient> ingredients;

	public Recipe() {
		ingredients = new ArrayList<IIngredient>();
	}

	public Recipe(String name, List<IIngredient> ingredients) {
		this.ingredients = ingredients;
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIngredients(List<IIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<IIngredient> getIngredients() {
		return ingredients;
	}

	@Override
	public int compareTo(IRecipe another) {
		return name.compareTo(another.getName());
	}

	@Override
	public String getElementUnit() {
		return null;
	}

}
