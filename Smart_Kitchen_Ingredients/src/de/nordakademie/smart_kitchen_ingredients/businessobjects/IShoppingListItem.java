package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface IShoppingListItem extends IIngredient {

	boolean isBought();

	void setBought(boolean bought);
}
