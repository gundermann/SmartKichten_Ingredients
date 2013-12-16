package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */
public interface IIngredient extends IServerIngredient {

	/**
	 * Liefert die Menge die von einer Zutat ben�tigt wird.
	 * 
	 * @return int
	 */
	int getQuantity();
}
