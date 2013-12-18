package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */
public interface IIngredientFactory {

	/**
	 * Erzeugt eine Zutat zurück.
	 * 
	 * @param title
	 * @param amount
	 * @param unit
	 * @return IIngredient
	 */
	IIngredient createIngredient(String title, Unit unit);

}
