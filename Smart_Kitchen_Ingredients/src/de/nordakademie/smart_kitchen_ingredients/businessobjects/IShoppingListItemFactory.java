package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public interface IShoppingListItemFactory {

	IShoppingListItem createShoppingListItem(String title, int quantity,
			Unit unit, boolean bought);

}
