package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface IShoppingListItem extends IIngredient {

	int getQuantity();

	boolean isBought();

	void setBought(boolean bought);
}
