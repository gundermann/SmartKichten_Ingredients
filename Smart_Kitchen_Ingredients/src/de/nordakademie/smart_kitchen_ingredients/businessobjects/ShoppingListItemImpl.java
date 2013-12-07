package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingListItemImpl extends IngredientImpl implements
		ShoppingListItem {

	private boolean bought;

	public ShoppingListItemImpl(String title, int amount, Unit unit,
			boolean bought) {
		super(title, amount, unit);
		this.bought = bought;
	}

	@Override
	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}

}
