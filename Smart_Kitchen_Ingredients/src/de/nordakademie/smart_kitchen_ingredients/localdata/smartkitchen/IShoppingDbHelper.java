package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;

/**
 * 
 * @author niels
 * 
 */
public interface IShoppingDbHelper {

	List<IShoppingList> getAllShoppingLists();

	/**
	 * Fügt der eine Zutat zum einkaufen in die Datenbank ein.
	 * 
	 * @param ingredient
	 * @param currentShoppingList
	 */
	boolean addItem(IIngredient ingredient, int quantity,
			String currentShoppingList);

	/**
	 * Fügt die Zutaten eines Rezepten zum einkaufen in die Datenbank ein.
	 * 
	 * @param recipe
	 */
	boolean addItem(IRecipe recipe, int quantity, String shoppingList);

	/**
	 * Liefert alle Artikel, die auf der Einkaufsliste stehen, zurück.
	 * 
	 * @param currentShoppingListName
	 * 
	 * @return List<IShoppingListItem>
	 */
	List<IShoppingListItem> getAllShoppingItems(String currentShoppingListName);

	/**
	 * Aktualisiert einen einzukaufden Artikel auf der Einkaufsliste.
	 * 
	 * @param item
	 * @return int
	 */
	int updateShoppingItem(IShoppingListItem item, String shoppingList);

	/**
	 * Entfernt als gekaufte markierte Artikel aus der Datenbank.
	 */
	void cleanShoppingIngredients();

	/**
	 * Liefert einen Artikel basierend auf dessen Namen von der Einkaufsliste
	 * zurück.
	 * 
	 * @param itemName
	 * 
	 * @param title
	 * @return IShoppingListItem
	 */
	IShoppingListItem getShoppingItem(String itemName, String shoppingList);

	boolean addItem(IShoppingList shoppingList);

	int getQuantityShopping(IIngredient item);

	/**
	 * Löscht alle in der Einkaufsliste enthaltenen Zutaten
	 */
	void deleteAllShoppingItems();

	void delete(IShoppingList shoppingList);

	IShoppingListItem getShoppingItem(String itemTitle);

}
