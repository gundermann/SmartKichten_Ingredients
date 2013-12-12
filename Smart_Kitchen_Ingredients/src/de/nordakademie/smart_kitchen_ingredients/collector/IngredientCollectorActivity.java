package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.localdata.IIngredientData;

/**
 * @author frederic.oppermann
 * @date 09.12.2013
 * @description
 */

// TODO handle recipe / ingredient views
// TODO show msg if no recipes / ingredients available by search
// TODO show msg if no recipes / ingredients available in general

public class IngredientCollectorActivity extends Activity implements
		TextWatcher {
	EditText searchBar;
	ListView ingredientsList;
	private List<IIngredient> ingredientsFromDb = new ArrayList<IIngredient>();
	private AsyncTask<Void, Void, List<IIngredient>> myTask = new GetDataFromDbTask();
	private ProgressBar progressWheel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredient_collector);

		ingredientsList = (ListView) findViewById(R.id.ingredientList);
		searchBar = (EditText) findViewById(R.id.ingredientNameInput);
		searchBar.addTextChangedListener(this);
		final View view = findViewById(R.id.activity_ingredient_collector);

		progressWheel = (ProgressBar) this
				.findViewById(R.id.collectorProgressBar);
		view.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						view.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						myTask = myTask.execute();
						Log.d("nixLos", "ping");
					}
				});
	}

	@Override
	public void afterTextChanged(Editable s) {
		List<IIngredient> ingredientsInList = new ArrayList<IIngredient>();

		for (IIngredient ingredient : ingredientsFromDb) {
			if (ingredientNameMatchSearchString(ingredient)) {
				ingredientsInList.add(ingredient);
			}
		}

		ingredientsList.setAdapter((ListAdapter) new ArrayAdapter<IIngredient>(
				getApplicationContext(), R.layout.list_view_entry,
				ingredientsInList));
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	private boolean ingredientNameMatchSearchString(IIngredient ingredient) {
		String currentSearch = searchBar.getText().toString();
		return ingredient.getName().toLowerCase(Locale.GERMAN)
				.contains(currentSearch.toLowerCase(Locale.GERMAN));
	}

	private class GetDataFromDbTask extends
			AsyncTask<Void, Void, List<IIngredient>> {
		private IIngredientData ingredientDb = new IngredientDb();

		@Override
		protected void onPreExecute() {
			progressWheel.setVisibility(View.VISIBLE);
		}

		@Override
		protected List<IIngredient> doInBackground(Void... params) {
			return ingredientDb.getAllIngredients();
		}

		@Override
		protected void onPostExecute(List<IIngredient> result) {
			try {
				ingredientsFromDb = this.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			progressWheel.setVisibility(View.GONE);
			ListAdapter adapter = new ArrayAdapter<IIngredient>(
					getApplicationContext(), R.layout.list_view_entry,
					ingredientsFromDb);
			ingredientsList.setAdapter(adapter);
			afterTextChanged(searchBar.getText());
		}

	}
}