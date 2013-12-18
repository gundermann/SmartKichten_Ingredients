package de.nordakademie.smart_kitchen_ingredients.collector;

import android.os.Bundle;
import android.view.View;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class StoredIngredientCollectorActivity extends
		IngredientCollectorActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showRecepiesButton.setVisibility(View.GONE);
		confirmShoppingList.setVisibility(View.GONE);
		
	}

	@Override
	public void onPositiveFinishedDialog(IListElement element, int quantity) {
		try {
			((IngredientsApplication) getApplication()).getStoredDbHelper()
					.insertOrUpdateIngredient((IIngredient) element, quantity);
		} catch (ClassCastException e) {
			((IngredientsApplication) getApplication())
					.informUser(R.string.developerMistake);
		}
	}
}
