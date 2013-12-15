package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public abstract class AbstractCollectorActivity<T> extends FragmentActivity
		implements TextWatcher, IAsyncTaskObserver<T> {
	private static String TAG;

	private EditText searchBar;
	private ListView elementsListView;
	private List<T> allElements = new ArrayList<T>();
	private List<T> elementsToShow = new ArrayList<T>();
	private ProgressBar progressWheel;
	private Button addNewIngredient;
	private View noResultsFound;

	public List<T> getElementsToShow() {
		return elementsToShow;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getSimpleName();
		setContentView(R.layout.activity_ingredient_collector);
		initiateAllViews();
		addLayoutChangeListener();

		setNextActivityOnClick(addNewIngredient, AddIngredientActivity.class);
		elementsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DialogFragment quantityDialog = new QuantityDialog();
				quantityDialog.show(getSupportFragmentManager(), TAG);
			}
		});
	}

	protected void setNextActivityOnClick(View view,
			final Class<?> nextActivityClass) {
		view.setOnClickListener(new OnClickListener() {

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
		noResultsFound = findViewById(R.id.noResultsFoundView);

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
		noResultsFound.setVisibility(View.GONE);

		List<IListElement> elementsInList = new ArrayList<IListElement>();

		for (IListElement element : (List<IListElement>) allElements) {
			if (ingredientNameMatchSearchString(element)) {
				elementsInList.add(element);
			}
		}
		informUserWhenNoResults(elementsInList);

		elementsToShow = (List<T>) elementsInList;
	}

	private void informUserWhenNoResults(List<IListElement> elementsInList) {
		if (elementsInList.isEmpty()) {
			noResultsFound.setVisibility(View.VISIBLE);
		}
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