package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class IngredientImpl extends ServeringredientImpl implements Ingredient {

	protected static int amount;

	public IngredientImpl(int amount, String title, Unit unit) {
		super(title, unit);
		this.amount = amount;
	}

	@Override
	public int getAmount() {
		return amount;
	}

}
