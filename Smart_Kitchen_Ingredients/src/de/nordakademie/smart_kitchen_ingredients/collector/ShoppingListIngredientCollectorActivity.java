package de.nordakademie.smart_kitchen_ingredients.collector;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.tasks.FetchDataAsyncTask;

/**
 * @author frederic.oppermann
 * @date 16.12.2013
 * @description
 */
public class ShoppingListIngredientCollectorActivity extends
		AbstractShoppingListCollectorActivity<IIngredient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingredient_collector_layout);
		initElements();
	}

	@Override
	protected void onResume() {
		super.onResume();
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
	public void afterTextChanged(Editable s) {
		super.afterTextChanged(s);
	}

	@Override
	public void onPositiveFinishedDialog(IListElement element, int quantity) {
		try {
			IngredientsApplication app = ((IngredientsApplication) getApplication());
			IIngredient ingredientToAdd = app.getIngredientsDbHelper()
					.getExplicitItem(element.getName());
			((IngredientsApplication) getApplication()).getShoppingDbHelper()
					.addItem(ingredientToAdd, quantity, currentShoppingList);
			app.informUser(R.string.addIngredientToShoppingList);

		} catch (ClassCastException e) {
			((IngredientsApplication) getApplication())
					.informUser(R.string.developerMistake);
		} catch (Exception e) {
			((IngredientsApplication) getApplication())
					.informUser(R.string.developerMistake);
		}
	}

	@Override
	protected void switchCollectorActivity() {
		startActivity(new Intent(getApplicationContext(),
				ShoppingListRecipeCollectorActivity.class).putExtra(
				"shoppingListName", currentShoppingList));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		openQuantityDialog(position);
	}

	@Override
	protected ListAdapter getAdapter() {
		return AdapterFactory.createIngredientCollectorAdapter(
				getApplicationContext(), R.layout.list_view_entry,
				getElementsToShow());
	}
}
