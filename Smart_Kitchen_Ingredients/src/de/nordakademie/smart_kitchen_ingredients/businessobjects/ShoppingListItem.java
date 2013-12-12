package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingListItem extends Ingredient implements IShoppingListItem,
		Comparable<IShoppingListItem> {

	private boolean bought;

	public ShoppingListItem(String title, int amount, Unit unit, boolean bought) {
		super(title, amount, unit);
		this.bought = bought;
	}

	@Override
	public boolean isBought() {
		return bought;
	}

	@Override
	public void setBought(boolean bought) {
		this.bought = bought;
	}

	@Override
	public int compareTo(IShoppingListItem another) {
		if (isBought() == another.isBought()) {
			return getTitle().compareTo(another.getTitle());
		} else if (!another.isBought()) {
			return 1;
		} else {
			return -1;
		}
	}

}
