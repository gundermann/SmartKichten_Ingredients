/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collectors;

/**
 * @author frederic.oppermann
 * @date 07.12.2013
 * @description
 */
public class Ingredient implements IIngredient {
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

}
