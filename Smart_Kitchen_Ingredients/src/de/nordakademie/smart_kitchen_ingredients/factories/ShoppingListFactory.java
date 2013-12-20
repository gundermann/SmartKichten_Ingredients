package de.nordakademie.smart_kitchen_ingredients.factories;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingList;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingListFactory {

	public static IShoppingList createShoppingList(String title) {
		return new ShoppingList(title);
	}
}
