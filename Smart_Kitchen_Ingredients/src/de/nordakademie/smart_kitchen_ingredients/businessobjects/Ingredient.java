package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class Ingredient implements IIngredient, Comparable<IIngredient> {

	private String title;
	private Unit unit;
	private int amount;

	public Ingredient(String title, int amount, Unit unit) {
		this.title = title;
		this.amount = amount;
		this.unit = unit;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public String getTitle() {
		return title;
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
		return this.getTitle().compareTo(another.getTitle());
	}
}
