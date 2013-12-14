package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;

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
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.shoppinglist.AddIngredientActivity;

/**
 * @author frederic.oppermann
 * @date 09.12.2013
 * @description
 */

// TODO handle recipe / ingredient views
// TODO show msg if no recipes / ingredients available by search
// TODO show msg if no recipes / ingredients available in general

public abstract class AbstractCollectorActivity<T> extends Activity implements
		TextWatcher, IAsyncTaskObserver<T> {
	private EditText searchBar;
	private ListView elementsListView;
	private List<T> allElements = new ArrayList<T>();
	private ProgressBar progressWheel;

	private Button showIngredients;
	private Button addNewIngredient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredient_collector);

		elementsListView = (ListView) findViewById(R.id.ingredientList);
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

					}
				});

		showIngredients = (Button) findViewById(R.id.showIngredientsButton);

		addNewIngredient = (Button) findViewById(R.id.addNewIngredientButton);

		showIngredients.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showIngredients.setVisibility(View.GONE);
			}
		});

		addNewIngredient.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						AddIngredientActivity.class));
			}
		});
	}

	protected void fetchDataFromDb(AsyncTask<Void, Void, List<T>> fetchDataTask) {
		fetchDataTask.execute();
	}

	public ProgressBar getProgressWheel() {
		return progressWheel;
	}

	@Override
	public void afterTextChanged(Editable s) {
		List<T> ingredientsInList = new ArrayList<T>();

		for (T ingredient : getAllElements()) {
			if (ingredientNameMatchSearchString(ingredient)) {
				ingredientsInList.add(ingredient);
			}
		}

		elementsListView.setAdapter((ListAdapter) new ArrayAdapter<T>(
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

	private boolean ingredientNameMatchSearchString(T ingredient) {
		// String searchString = searchBar.getText().toString();
		// return ingredient.getName().toLowerCase(Locale.GERMAN)
		// .contains(searchString.toLowerCase(Locale.GERMAN));

		return true;
	}

	private void setNewAdapter(ListAdapter adapter) {
		elementsListView.setAdapter(adapter);
		afterTextChanged(searchBar.getText());
	}

	public List<T> getAllElements() {
		return allElements;
	}

	public void setAllElements(List<T> allElements) {
		this.allElements = allElements;
	}
}