package de.nordakademie.smart_kitchen_ingredients.collector;
/**
 * @author Kathrin Kurtz
 */
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.tasks.FetchDataAsyncTask;

public class StoredIngredientCollectorActivity extends
		AbstractCollectorActivity<IIngredient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_ingredient_collector_layout);
		initElements();
	}

	@Override
	protected void onStart() {
		super.onStart();
		fetchDataFromDb(new FetchDataAsyncTask<IIngredient>(getProgressWheel(),
				app.getIngredientsDbHelper(), this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.collection_menu, menu);
		Log.i(TAG, "menu inflated");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addNewIngredient:
			AddStoredNewIngredientDialog.newInstance(app).show(
					getSupportFragmentManager(), TAG);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onPositiveFinishedDialog(IListElement element, int quantity) {
		try {
			((IngredientsApplication) getApplication()).getStoredDbHelper()
					.insertOrUpdateIngredient((IIngredient) element, quantity);
			app.informUser(R.string.addedToStock);
		} catch (ClassCastException e) {
			((IngredientsApplication) getApplication())
					.informUser(R.string.developerMistake);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		openQuantityDialog(position);
	}

	@Override
	protected ListAdapter getAdapter() {
		return AdapterFactory.createIngredientCollectorAdapter(
				app.getApplicationContext(), R.layout.list_view_entry,
				getElementsToShow());
	}

}
