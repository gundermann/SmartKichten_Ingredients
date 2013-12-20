package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frederic Oppermann
 * @description stellt ein Item auf dem Einkaufszettel dar, welches als gekauft
 *              markiert werden kann. Beinhaltet eine Mengenangabe.
 */

public class ShoppingListItem extends Ingredient implements IShoppingListItem {

	private boolean bought;
	private final int quantity;

	public ShoppingListItem(String title, int quantity, Unit unit,
			boolean bought) {
		super(title, unit);
		this.quantity = quantity;
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
	public int compareTo(IIngredient another) {
		IShoppingListItem anotherShoppingListItem = (IShoppingListItem) another;
		if (isBought() == anotherShoppingListItem.isBought()) {
			return getName().compareTo(another.getName());
		} else if (!anotherShoppingListItem.isBought()) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

}
