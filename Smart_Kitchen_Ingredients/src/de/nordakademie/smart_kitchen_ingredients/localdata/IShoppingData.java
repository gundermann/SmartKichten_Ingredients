package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;

public interface IShoppingData {

	void insertOrIgnore(List<IIngredient> ingredientsList);

	String getIngredientById(long id);

	boolean getBuyedById(long id);

	List<IShoppingListItem> getAllShoppingItems();

	void updateShoppingItem(IShoppingListItem item);

	void cleanShoppingIngredients();

	IShoppingListItem getShoppingItem(String title);
}