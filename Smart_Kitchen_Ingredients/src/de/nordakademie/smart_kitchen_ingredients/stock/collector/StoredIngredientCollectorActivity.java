package de.nordakademie.smart_kitchen_ingredients.stock.collector;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.collector.IListElement;
import de.nordakademie.smart_kitchen_ingredients.collector.IngredientCollectorActivity;

public class StoredIngredientCollectorActivity extends
		IngredientCollectorActivity {
	
	private IngredientsApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showRecepiesButton.setVisibility(View.GONE);
		confirmShoppingList.setVisibility(View.GONE);
		app = (IngredientsApplication) getApplication();
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
}
