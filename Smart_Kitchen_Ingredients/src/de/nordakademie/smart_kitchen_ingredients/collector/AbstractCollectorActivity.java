package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
	private List<T> elementsToShow = new ArrayList<T>();
	private ProgressBar progressWheel;
	private Button addNewIngredient;

	public List<T> getElementsToShow() {
		return elementsToShow;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredient_collector);
		initiateAllViews();
		addLayoutChangeListener();
		setNextActivityOnClick(addNewIngredient, AddIngredientActivity.class);
	}

	protected void setNextActivityOnClick(Button button,
			final Class<?> nextActivityClass) {
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						nextActivityClass));
			}
		});
	}

	private void addLayoutChangeListener() {
		final View view = findViewById(R.id.activity_ingredient_collector);
		view.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						view.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);

					}
				});
	}

	private void initiateAllViews() {
		elementsListView = (ListView) findViewById(R.id.elementsList);
		searchBar = (EditText) findViewById(R.id.searchBarInput);
		searchBar.addTextChangedListener(this);
		progressWheel = (ProgressBar) this
				.findViewById(R.id.collectorProgressBar);
		addNewIngredient = (Button) findViewById(R.id.addNewIngredientButton);
	}

	protected void fetchDataFromDb(AsyncTask<Void, Void, List<T>> fetchDataTask) {
		fetchDataTask.execute();
	}

	public ProgressBar getProgressWheel() {
		return progressWheel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterTextChanged(Editable s) {
		findViewById(R.id.noResultsFoundView).setVisibility(View.GONE);
		List<IListElement> elementsInList = new ArrayList<IListElement>();

		for (IListElement element : (List<IListElement>) allElements) {
			if (ingredientNameMatchSearchString(element)) {
				elementsInList.add(element);
			}
		}

		if (elementsInList.isEmpty()) {
			findViewById(R.id.noResultsFoundView).setVisibility(View.VISIBLE);
		}
		elementsToShow = (List<T>) elementsInList;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	private boolean ingredientNameMatchSearchString(IListElement listElement) {
		String searchString = searchBar.getText().toString();
		return listElement.getName().toLowerCase(Locale.GERMAN)
				.contains(searchString.toLowerCase(Locale.GERMAN));
	}

	public void setNewAdapter(ListAdapter adapter) {
		elementsListView.setAdapter(adapter);
	}

	public void setAllElements(List<T> allElements) {
		this.allElements = allElements;
	}

	@Override
	public void update(AsyncTask<Void, Void, List<T>> task) {
		try {
			setAllElements(task.get());
			afterTextChanged(((EditText) findViewById(R.id.searchBarInput))
					.getText());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}