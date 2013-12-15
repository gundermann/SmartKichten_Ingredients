package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class IngredientCollectorActivity extends
		AbstractCollectorActivity<IIngredient> {
	private Button showRecepiesButton;
	private IAdapterFactory<IIngredient> adapterFactory = new AdapterFactory<IIngredient>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.fetchDataFromDb(new FetchDataAsyncTask<IIngredient>(
				getProgressWheel(), new IngredientDbMock(), this));
		initiateButtons();
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
		setNewAdapter(new ArrayAdapter<IIngredient>(getApplicationContext(),
				R.layout.list_view_entry, getElementsToShow()));
	}

	@Override
	public void afterTextChanged(Editable s) {
		super.afterTextChanged(s);
		setNewAdapter(adapterFactory.createAdapter(getApplicationContext(),
				R.layout.list_view_entry, getElementsToShow()));
	}
}
