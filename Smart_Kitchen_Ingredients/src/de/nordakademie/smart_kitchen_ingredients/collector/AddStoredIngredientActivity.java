package de.nordakademie.smart_kitchen_ingredients.collector;

/**
 * @author Kathrin Kurtz
 **/

import android.content.Intent;
import de.nordakademie.smart_kitchen_ingredients.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.stock.StoredIngredientActivity;

public class AddStoredIngredientActivity extends AddIngredientActivity {

	@Override
	protected void saveIngredientAndLeave(String title, Integer amount,
			Unit unit) {
		if (saveNewIngredientToDBs(title, amount, unit)) {
			app.informUser(R.string.ingredientSaved);
			startActivity(new Intent(getApplicationContext(),
					StoredIngredientActivity.class));
			finish();
		} else {
			app.informUser(R.string.ingredientInDatabase);
		}
	}

	private boolean saveNewIngredientToDBs(String title, Integer quantity,
			Unit unit) {
		IIngredient newItem = IngredientFactory.createIngredient(title, unit);

		fetchDataFromDb(new PostNewIngredientAsyncTask(newItem,
				app.getServerHandler(), app.getCacheDbHelper()));

		if (!isItemAlreadyInDb(newItem)) {
			app.getStoredDbHelper().insertOrUpdateIngredient(newItem, quantity);
			return true;
		} else {
			return false;
		}
	}

	private boolean isItemAlreadyInDb(IIngredient newItem) {
		return app.getCacheDbHelper().itemExists(newItem.getName());
	}
}
