package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

public class RecipeCollectorActivity extends AbstractCollectorActivity<IRecipe> {
	private Button showIngredientsButton;
	private IAdapterFactory<IRecipe> adapterFactory = new AdapterFactory<IRecipe>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.fetchDataFromDb(new FetchDataAsyncTask<IRecipe>(
				getProgressWheel(), new RecipeDbMock(), this));
		initiateButtons();
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
}
