package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingList implements IShoppingList, IListElement {

	private final String name;

	public ShoppingList(String name) {
		this.name = name;
	}

	@Override
	public String getElementUnit() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

}
