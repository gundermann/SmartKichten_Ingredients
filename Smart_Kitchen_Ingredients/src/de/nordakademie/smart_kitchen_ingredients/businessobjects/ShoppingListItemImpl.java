package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingListItemImpl extends IngredientImpl {

	private boolean buyed;

	public ShoppingListItemImpl(boolean buyed) {
		super(id, title, unit);
		this.buyed = buyed;
	}

	public boolean isBuyed() {
		return buyed;
	}

	public void setBuyed(boolean buyed) {
		this.buyed = buyed;
	}

}
