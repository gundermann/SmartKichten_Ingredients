package de.nordakademie.smart_kitchen_ingredients.collector;

import android.content.Intent;
import de.nordakademie.smart_kitchen_ingredients.AbstractAddIngredientActivity;
import de.nordakademie.smart_kitchen_ingredients.PostNewIngredientAsyncTask;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.factories.ShoppingListItemFactory;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public class AddIngredientActivity extends AbstractAddIngredientActivity {

	@Override
	protected void saveNewIngredientToDBs(String title, Integer quantity,
			Unit unit) {
		IShoppingListItem newItem = ShoppingListItemFactory
				.createShoppingListItem(title, quantity, unit, false);

		fetchDataFromDb(new PostNewIngredientAsyncTask(newItem,
				app.getServerHandler(), app.getCacheDbHelper()));

		if (!isItemAlreadyInDb(newItem)) {
			app.getShoppingDbHelper().addItem(newItem, quantity,
					getCurrentShoppingListName());
			// app.informUser(R.string.ingredientSaved);
		}

	}

	private boolean isItemAlreadyInDb(IShoppingListItem newItem) {
		return app.getShoppingDbHelper().getShoppingItem(newItem.getName()) != null;
	}

	@Override
	protected void startPrevActivity() {
		startActivity(new Intent(getApplicationContext(),
				ShoppingListIngredientCollectorActivity.class));
	}
}
