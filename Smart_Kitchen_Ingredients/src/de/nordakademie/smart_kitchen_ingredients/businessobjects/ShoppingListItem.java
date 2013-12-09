package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingListItem extends Ingredient implements
		IShoppingListItem {

	private boolean bought;

	public ShoppingListItem(String title, int amount, Unit unit,
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
