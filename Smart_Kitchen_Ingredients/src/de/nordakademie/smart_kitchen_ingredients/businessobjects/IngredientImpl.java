package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class IngredientImpl implements IIngredient {

	private String title;
	private Unit unit;
	private int amount;

	public IngredientImpl() {
		super();
	}

	public IngredientImpl(String title, int amount, Unit unit) {
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

}
