package de.nordakademie.smart_kitchen_ingredients.collector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.tasks.FetchDataAsyncTask;

/**
 * @author frederic.oppermann
 * @date 16.12.2013
 * @description
 */
public class ShoppingListRecipeCollectorActivity extends
		AbstractShoppingListCollectorActivity<IRecipe> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipe_collector_layout);
		initElements();
	}

	@Override
	protected void onResume() {
		super.onResume();
		super.fetchDataFromDb(new FetchDataAsyncTask<IRecipe>(
				getProgressWheel(), app.getRecipeDbHelper(), this));
	}

	@Override
	public void onPositiveFinishedDialog(IListElement element, int quantity) {
		try {
			IRecipe recipeToAdd = findIngredientInDatabase(element);
			((IngredientsApplication) getApplication()).getShoppingDbHelper()

			.addItem(recipeToAdd, quantity, currentShoppingList);
			app.informUser(R.string.addIngredientFromRecipeToShoppingList);
		} catch (ClassCastException e) {
			((IngredientsApplication) getApplication())
					.informUser(R.string.developerMistake);
		}
	}

	private IRecipe findIngredientInDatabase(IListElement element) {
		return app.getRecipeDbHelper().getExplicitItem(element.getName());
	}

	@Override
	protected void switchCollectorActivity() {
		startActivity(new Intent(getApplicationContext(),
				ShoppingListIngredientCollectorActivity.class).putExtra(
				"shoppingListName", currentShoppingList));
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		IRecipe findIngredientInDatabase = findIngredientInDatabase(getElementsToShow()
				.get(position));
		ShowRecipeIngredientsDialog.newInstance(findIngredientInDatabase, app)
				.show(getSupportFragmentManager(), TAG);
	}

	@Override
	protected ListAdapter getAdapter() {
		return AdapterFactory.createRecipeCollectorAdapter(
				getApplicationContext(), R.layout.list_view_entry,
				getElementsToShow());
	}
}
