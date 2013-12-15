/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collector;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

/**
 * @author frederic.oppermann
 * @date 07.12.2013
 * @description
 */
public class Ingredient implements IIngredient, IListElement {
	private static int runningNumber = 0;

	private String name;

	public Ingredient() {
		name = "Ingredient" + runningNumber++;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public Unit getUnit() {
		return Unit.g;
	}

	@Override
	public int getQuantity() {
		return 0;
	}

}
