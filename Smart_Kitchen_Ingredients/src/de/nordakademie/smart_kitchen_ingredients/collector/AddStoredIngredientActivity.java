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
		try {
			saveNewIngredientToDBs(title, amount, unit);
		} finally {
			startActivity(new Intent(getApplicationContext(),
					StoredIngredientActivity.class));
			finish();
		}
	}

	private void saveNewIngredientToDBs(String title, Integer quantity,
			Unit unit) {
		IIngredient newItem = IngredientFactory.createIngredient(title, unit);

		fetchDataFromDb(new PostNewIngredientAsyncTask(newItem,
				app.getServerHandler(), app.getCacheDbHelper()));

		if (!isItemAlreadyInDb(newItem)) {
			app.getStoredDbHelper().insertOrUpdateIngredient(newItem, quantity);
			app.informUser(R.string.ingredientSaved);
		}
	}

	private boolean isItemAlreadyInDb(IIngredient newItem) {
		return app.getStoredDbHelper().getStoredIngredient(newItem.getName()) != null;
	}
}
