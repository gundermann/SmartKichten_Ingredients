package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingListFactory implements IShoppingListFactory {

	@Override
	public IShoppingListFactory createShoppingList(String title) {
		IShoppingList list = new ShoppingList(title);
		return (IShoppingListFactory) list;
	}
}
