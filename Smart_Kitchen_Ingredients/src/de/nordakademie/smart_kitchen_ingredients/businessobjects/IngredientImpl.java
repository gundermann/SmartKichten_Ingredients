package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class IngredientImpl extends ServeringredientImpl implements Ingredient {

	private int amount;

	public IngredientImpl(String title, int amount, Unit unit) {
		super(title, unit);
		this.amount = amount;
	}

	@Override
	public int getAmount() {
		return amount;
	}

}