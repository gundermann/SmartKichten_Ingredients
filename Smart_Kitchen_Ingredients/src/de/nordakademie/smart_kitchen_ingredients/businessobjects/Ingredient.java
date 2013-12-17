package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import de.nordakademie.smart_kitchen_ingredients.collector.IListElement;

public class Ingredient implements IIngredient, IListElement,
		Comparable<IIngredient> {

	private String title;
	private Unit unit;

	public Ingredient(String title, Unit unit) {
		this.title = title;
		this.unit = unit;
	}

	@Override
	public Unit getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return title;
	}

	@Override
	public int compareTo(IIngredient another) {
		return this.getName().compareTo(another.getName());
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public String getElementUnit() {
		return null;
	}
}
