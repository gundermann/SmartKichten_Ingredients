package de.nordakademie.smart_kitchen_ingredients.stock.collector;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import de.nordakademie.smart_kitchen_ingredients.AbstractCollectorActivity;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;

public class StoredIngredientCollectorActivity extends
		AbstractCollectorActivity<IIngredient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_ingredient_collector_layout);
		initElements();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addNewIngredient:
			startActivity(new Intent(getApplicationContext(),
					AddStoredIngredientActivity.class));
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

}
