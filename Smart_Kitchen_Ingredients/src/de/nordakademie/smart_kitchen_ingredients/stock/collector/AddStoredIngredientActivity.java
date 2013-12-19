package de.nordakademie.smart_kitchen_ingredients.stock.collector;

/**
 * @author Kathrin Kurtz
 **/

import android.content.Intent;
import de.nordakademie.smart_kitchen_ingredients.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.collector.AbstractAddIngredientActivity;
import de.nordakademie.smart_kitchen_ingredients.collector.PostNewIngredientAsyncTask;
import de.nordakademie.smart_kitchen_ingredients.stock.StockOverviewActivity;

public class AddStoredIngredientActivity extends AbstractAddIngredientActivity {

	private boolean isItemAlreadyInDb(IIngredient newItem) {
		return app.getCacheDbHelper().itemExists(newItem.getName());
	}

	@Override
	protected void startPrevActivity() {
		startActivity(new Intent(getApplicationContext(),
				StockOverviewActivity.class));
	}

	@Override
	protected void saveNewIngredientToDBs(String title, Integer quantity,
			Unit unit) {
		IIngredient newItem = IngredientFactory.createIngredient(title, unit);

		fetchDataFromDb(new PostNewIngredientAsyncTask(newItem,
				app.getServerHandler(), app.getCacheDbHelper()));

		if (!isItemAlreadyInDb(newItem)) {
			app.getStoredDbHelper().insertOrUpdateIngredient(newItem, quantity);
		}
	}
}
