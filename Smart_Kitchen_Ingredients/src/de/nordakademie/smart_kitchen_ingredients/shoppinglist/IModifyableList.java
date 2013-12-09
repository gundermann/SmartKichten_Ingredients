package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;

/**
 * 
 * @author Niels Gundermann
 * 
 */
public interface IModifyableList {

	/**
	 * Liefert eine Liste mit den Titeln der einzukaufenden Artikel
	 * 
	 * @return List<String>
	 */
	List<String> getValues();

	/**
	 * Liefert die einzukaufenden Artikel
	 * 
	 * @return List<IShoppingListItem>
	 */
	List<IShoppingListItem> getShoppingItems();

	/**
	 * Verändert den Kaufstatus des Artikels und aktualisiert die Datenbank.
	 * 
	 * @param Title
	 *            des Artikels
	 */
	void checkAndUpdateValueAtPosition(String title);

}
