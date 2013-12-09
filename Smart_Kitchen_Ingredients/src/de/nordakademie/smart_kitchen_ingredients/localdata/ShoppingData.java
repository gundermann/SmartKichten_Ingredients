package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItem;

public interface ShoppingData {

	void insertOrIgnore(List<IIngredient> ingredientsList);

	String getIngredientById(long id);

	boolean getBuyedById(long id);

	List<ShoppingListItem> getAllShoppingItems();

	void updateShoppingItem(ShoppingListItem item);

	void cleanShoppingIngredients();
}