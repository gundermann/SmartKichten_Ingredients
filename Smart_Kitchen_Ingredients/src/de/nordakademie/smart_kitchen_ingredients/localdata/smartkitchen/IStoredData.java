package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

/**
 * 
 * @author niels
 * 
 */
public interface IStoredData {

	/**
	 * Liefert eine Liste aller Bestands-Zutaten.
	 * 
	 * @return
	 */
	List<IIngredient> getAllStoredIngredients();

	/**
	 * Fügt eine im Haushalt bestehende Zutata hinzu.
	 * 
	 * @param boughtIngredient
	 */
	void insertOrUpdateIngredient(IIngredient boughtIngredient, int quantity);

	/**
	 * Liefert eine Zutat mit dem übergebenen Titel zurück.
	 * 
	 * @param ingredientTitle
	 */
	IIngredient getStoredIngredient(String title);

	int getQuantity(IIngredient item);
	
	void deleteStoredIngredient(String title);
}
