package de.nordakademie.smart_kitchen_ingredients.collector;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class AddStoredNewIngredientDialog extends AbstractAddNewIngredientDialog {

	public static final AddStoredNewIngredientDialog newInstance(
			IngredientsApplication app) {
		AddStoredNewIngredientDialog dialog = new AddStoredNewIngredientDialog();
		dialog.setApplication(app);
		return dialog;
	}

	@Override
	protected void insertIntoLocalDb(IIngredient newItem, Integer quantity) {
		getApp().getStoredDbHelper()
				.insertOrUpdateIngredient(newItem, quantity);
	}
}
