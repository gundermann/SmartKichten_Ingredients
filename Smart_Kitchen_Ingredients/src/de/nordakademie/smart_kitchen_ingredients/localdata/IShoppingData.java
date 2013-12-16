package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;

/**
 * 
 * @author niels
 * 
 */
public interface IShoppingData {

	/**
	 * Fügt der eine Zutat zum einkaufen in die Datenbank ein.
	 * 
	 * @param ingredient
	 */
	boolean addItem(IIngredient ingredient);

	/**
	 * Fügt die Zutaten eines Rezepten zum einkaufen in die Datenbank ein.
	 * 
	 * @param recipe
	 */
	boolean addItem(IRecipe recipe);

	/**
	 * Liefert alle Artikel, die auf der Einkaufsliste stehen, zurück.
	 * 
	 * @return List<IShoppingListItem>
	 */
	List<IShoppingListItem> getAllShoppingItems();

	/**
	 * Aktualisiert einen einzukaufden Artikel auf der Einkaufsliste.
	 * 
	 * @param item
	 */
	void updateShoppingItem(IShoppingListItem item);

	/**
	 * Entfernt als gekaufte markierte Artikel aus der Datenbank.
	 */
	void cleanShoppingIngredients();

	/**
	 * Liefert einen Artikel basierend auf dessen Namen von der Einkaufsliste
	 * zurück.
	 * 
	 * @param title
	 * @return IShoppingListItem
	 */
	IShoppingListItem getShoppingItem(String title);
}