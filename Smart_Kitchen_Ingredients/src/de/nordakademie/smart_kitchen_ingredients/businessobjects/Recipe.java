package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class Recipe implements IRecipe, Comparable<IRecipe> {

	private String name;
	private Map<IIngredient, Integer> ingredients;

	public Recipe() {
		ingredients = new HashMap<IIngredient, Integer>();
	}

	public Recipe(String name, Map<IIngredient, Integer> ingredients) {
		this.ingredients = ingredients;
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIngredients(Map<IIngredient, Integer> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Map<IIngredient, Integer> getIngredients() {
		return ingredients;
	}

	@Override
	public int compareTo(IRecipe another) {
		return name.compareTo(another.getName());
	}

	@Override
	public String getElementUnit() {
		return "Personen";
	}

}
