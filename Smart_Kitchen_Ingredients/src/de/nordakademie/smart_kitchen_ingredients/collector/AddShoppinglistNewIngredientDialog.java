package de.nordakademie.smart_kitchen_ingredients.collector;

/**
 * @author Frederic Oppermann
 * @description ein Dialog, der das anlegen einer neuen zutat ermöglicht
 */
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class AddShoppinglistNewIngredientDialog extends
		AbstractAddNewIngredientDialog {

	private String currentShoppingListName;

	public static final AddShoppinglistNewIngredientDialog newInstance(
			IngredientsApplication app, String shoppinglist) {
		AddShoppinglistNewIngredientDialog dialog = new AddShoppinglistNewIngredientDialog();
		dialog.setApplication(app);
		dialog.setCurrentShoppingList(shoppinglist);
		return dialog;
	}

	private void setCurrentShoppingList(String shoppinglist) {
		this.currentShoppingListName = shoppinglist;
	}

	@Override
	protected void insertIntoLocalDb(IIngredient newItem, Integer quantity) {
		getApp().getShoppingListDbHelper().addItem(newItem, quantity,
				currentShoppingListName);
	}

}
