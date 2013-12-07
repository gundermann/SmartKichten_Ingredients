package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class IngredientImpl implements Ingredient {

	protected static double id;
	protected static String title;
	static Unit unit;

	public IngredientImpl(double id, String title, Unit unit) {
		this.id = id;
		this.title = title;
		this.unit = unit;
	}

	@Override
	public double getId() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Unit getUnit() {
		return unit;
	}

}
