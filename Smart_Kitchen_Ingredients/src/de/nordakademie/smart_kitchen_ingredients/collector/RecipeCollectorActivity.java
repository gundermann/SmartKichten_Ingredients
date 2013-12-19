package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.AbstractCollectorActivity;
import de.nordakademie.smart_kitchen_ingredients.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

/**
 * @author frederic.oppermann
 * @date 16.12.2013
 * @description
 */
public class RecipeCollectorActivity extends AbstractCollectorActivity<IRecipe> {
	private Button showIngredientsButton;
	private IngredientsApplication app;
	private ListView elementsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (IngredientsApplication) getApplication();
		showIngredientsButton = (Button) findViewById(R.id.showIngredientsButton);
		elementsListView = getElementsListView();
		showIngredientsButton.setVisibility(View.GONE);
		confirmShoppingList.setVisibility(View.GONE);

		elementsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {

				IRecipe findIngredientInDatabase = findIngredientInDatabase((IRecipe) adapterView
						.getAdapter().getItem(position));

				DialogFragment showIngredientsDialog = ShowRecipeIngredientsDialog
						.newInstance(findIngredientInDatabase, app);

				showIngredientsDialog.show(getSupportFragmentManager(), TAG);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		super.fetchDataFromDb(new FetchDataAsyncTask<IRecipe>(
				getProgressWheel(), app.getRecipeDbHelper(), this));
	}

	@Override
	public void update(AsyncTask<Void, Void, List<IRecipe>> task) {
		super.update(task);
		setNewAdapter(AdapterFactory.createRecipeCollectorAdapter(
				getApplicationContext(), R.layout.list_view_entry,
				getElementsToShow()));
	}

	@Override
	public void afterTextChanged(Editable s) {
		super.afterTextChanged(s);
		setNewAdapter(AdapterFactory.createRecipeCollectorAdapter(
				getApplicationContext(), R.layout.list_view_entry,
				getElementsToShow()));
	}

	@Override
	public void onPositiveFinishedDialog(IListElement element, int quantity) {
		try {
			IRecipe recipeToAdd = findIngredientInDatabase(element);
			((IngredientsApplication) getApplication()).getShoppingDbHelper()

			.addItem(recipeToAdd, quantity, currentShoppingList);
		} catch (ClassCastException e) {
			((IngredientsApplication) getApplication())
					.informUser(R.string.developerMistake);
		}
	}

	private IRecipe findIngredientInDatabase(IListElement element) {
		return app.getRecipeDbHelper().getExplicitItem(element.getName());
	}
}
