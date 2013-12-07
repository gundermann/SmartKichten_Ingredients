package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface ShoppingListItem extends Ingredient {

	boolean isBought();

	void setBought(boolean bought);
}
