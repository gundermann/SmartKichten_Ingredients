package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

/**
 * @author frederic.oppermann
 * @date 16.12.2013
 * @description
 */
public class IngredientCollectorActivity extends
		AbstractCollectorActivity<IIngredient> {
	protected Button showRecepiesButton;
	private final IAdapterFactory<IIngredient> adapterFactory = new AdapterFactory<IIngredient>();
	private IngredientsApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (IngredientsApplication) getApplication();
		initiateButtons();
	}

	@Override
	protected void onResume() {
		super.onResume();
		super.fetchDataFromDb(new FetchDataAsyncTask<IIngredient>(
				getProgressWheel(), app.getIngredientsDbHelper(), this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.collection_menu, menu);
		Log.i(TAG, "menu inflated");
		return true;
	}

	private void initiateButtons() {
		showRecepiesButton = (Button) findViewById(R.id.showRecipesButton);
		showRecepiesButton.setVisibility(View.VISIBLE);
		setNextActivityOnClick(showRecepiesButton,
				RecipeCollectorActivity.class);
	}

	@Override
	public void update(AsyncTask<Void, Void, List<IIngredient>> task) {
		super.update(task);
		setNewAdapter(adapterFactory.createAdapter(getApplicationContext(),
				R.layout.list_view_entry, getElementsToShow()));
	}

	@Override
	public void afterTextChanged(Editable s) {
		super.afterTextChanged(s);
		setNewAdapter(adapterFactory.createAdapter(getApplicationContext(),
				R.layout.list_view_entry, getElementsToShow()));
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
}
