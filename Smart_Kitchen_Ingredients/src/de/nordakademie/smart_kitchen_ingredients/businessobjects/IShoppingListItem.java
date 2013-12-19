package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public interface IShoppingListItem extends IIngredient {

	int getQuantity();

	boolean isBought();

	void setBought(boolean bought);
}
