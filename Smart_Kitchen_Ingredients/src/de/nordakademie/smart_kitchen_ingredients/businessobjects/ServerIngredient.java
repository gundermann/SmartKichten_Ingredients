package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface ServerIngredient extends Comparable<ServerIngredient> {

	String getTitle();

	Unit getUnit();

}
