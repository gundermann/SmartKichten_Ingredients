package de.nordakademie.smart_kitchen_ingredients.businessobjects;

import de.nordakademie.smart_kitchen_ingredients.collector.IListElement;

/**
 * 
 * @author Frauke Trautmann
 * 
 */
public interface IIngredient extends IListElement {

	Unit getUnit();
}