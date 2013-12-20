package de.nordakademie.smart_kitchen_ingredients.factories;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingListItemFactory {

	public static IShoppingListItem createShoppingListItem(String title,
			int quantity, Unit unit, boolean bought) {
		IShoppingListItem item = new ShoppingListItem(title, quantity, unit,
				bought);
		return item;

	}
}
