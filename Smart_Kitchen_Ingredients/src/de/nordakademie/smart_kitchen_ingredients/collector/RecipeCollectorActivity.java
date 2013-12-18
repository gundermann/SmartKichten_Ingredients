package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
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
	private IAdapterFactory<IRecipe> adapterFactory = new AdapterFactory<IRecipe>();
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
		super.fetchDataFromDb(new FetchDataAsyncTask<IRecipe>(
				getProgressWheel(), app.getRecipeDbHelper(), this));
	}

	private void initiateButtons() {
		showIngredientsButton = (Button) findViewById(R.id.showIngredientsButton);
		showIngredientsButton.setVisibility(View.VISIBLE);
		setNextActivityOnClick(showIngredientsButton,
				IngredientCollectorActivity.class);
	}

	@Override
	public void update(AsyncTask<Void, Void, List<IRecipe>> task) {
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
	public void onPositiveFinishedDialog(int quantity) {
		try {
			IngredientsApplication app = ((IngredientsApplication) getApplication());
			IRecipe recipeToAdd = app.getRecipeDbHelper().getExplicitItem(
					getCurrentElement().getName());
			((IngredientsApplication) getApplication()).getShoppingDbHelper()
					.addItem(recipeToAdd, quantity);
		} catch (ClassCastException e) {
			informUser(R.string.developerMistake);
		}
	}
}
