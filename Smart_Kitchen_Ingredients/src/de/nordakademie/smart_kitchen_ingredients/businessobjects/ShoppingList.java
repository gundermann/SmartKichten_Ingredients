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
	public String getElementUnitShort() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getElementUnitLong() {
		return null;
	}

}
