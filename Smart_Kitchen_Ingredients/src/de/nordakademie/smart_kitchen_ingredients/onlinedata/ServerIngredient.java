package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

public class ServerIngredient implements Ingredient {

	private Unit unit;
	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getAmount() {
		return 0;
	}

	@Override
	public Unit getUnit() {
		return unit;
	}

}
