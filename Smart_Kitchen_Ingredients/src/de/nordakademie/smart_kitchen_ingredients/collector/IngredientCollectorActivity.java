package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.localdata.IIngredientData;
import de.nordakademie.smart_kitchen_ingredients.shoppinglist.AddIngredientActivity;

/**
 * @author frederic.oppermann
 * @date 09.12.2013
 * @description
 */

// TODO handle recipe / ingredient views
// TODO show msg if no recipes / ingredients available by search
// TODO show msg if no recipes / ingredients available in general

public class IngredientCollectorActivity extends Activity implements
		TextWatcher, IAsyncTaskObserver {
	private EditText searchBar;
	private ListView ingredientsList;
	private List<IIngredient> ingredientsFromDb = new ArrayList<IIngredient>();
	private AsyncTask<Void, Void, List<IIngredient>> fetchDataTask;
	private ProgressBar progressWheel;

	private Button showRecepies;
	private Button showIngredients;
	private Button addNewIngredient;

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
						fetchDataTask = fetchDataTask.execute();
					}
				});

		showIngredients = (Button) findViewById(R.id.showIngredientsButton);
		showRecepies = (Button) findViewById(R.id.showRecipesButton);
		addNewIngredient = (Button) findViewById(R.id.addNewIngredientButton);

		showRecepies.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showRecepies.setVisibility(View.GONE);
				showIngredients.setVisibility(View.VISIBLE);
			}
		});

		showIngredients.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showIngredients.setVisibility(View.GONE);
				showRecepies.setVisibility(View.VISIBLE);
			}
		});

		addNewIngredient.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						AddIngredientActivity.class));
			}
		});

		fetchDataTask = new FetchDataAsyncTask(this.progressWheel,
				new IngredientDbMock(), this);
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

	@Override
	public void update(AsyncTask<Void, Void, List<IIngredient>> asyncTask) {
		try {
			ingredientsFromDb = asyncTask.get();
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
