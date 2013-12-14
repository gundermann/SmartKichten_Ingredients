package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class Ingredient implements IIngredient {

	private String title;
	private Unit unit;
	private int amount;

	public Ingredient(String title, int amount, Unit unit) {
		this.title = title;
		this.amount = amount;
		this.unit = unit;
	}

	@Override
	public int getQuantity() {
		return amount;
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public Unit getUnit() {
		return unit;
	}

}
