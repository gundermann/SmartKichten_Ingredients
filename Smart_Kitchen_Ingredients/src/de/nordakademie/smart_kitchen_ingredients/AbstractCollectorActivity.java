package de.nordakademie.smart_kitchen_ingredients;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;
import de.nordakademie.smart_kitchen_ingredients.collector.AddIngredientActivity;
import de.nordakademie.smart_kitchen_ingredients.tasks.IAsyncTaskObserver;

/**
 * @author frederic.oppermann
 * @date 09.12.2013
 * @description
 */

public abstract class AbstractCollectorActivity<T> extends AbstractActivity
		implements TextWatcher, IAsyncTaskObserver<T>,
		QuantityPickerDialogListener, OnItemClickListener {

	private EditText searchBar;
	private ListView elementsListView;
	private List<T> allElements = new ArrayList<T>();
	private List<T> elementsToShow = new ArrayList<T>();
	private ProgressBar progressWheel;
	private View noResultsFound;
	protected String currentShoppingList;

	protected List<T> getElementsToShow() {
		return elementsToShow;
	}

	protected ListView getElementsListView() {
		return elementsListView;
	}

	protected void initElements() {
		initiateAllViews();
		// addLayoutChangeListener();
		makeListEntriesClickable();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().getExtras() != null
				&& getIntent().getExtras().size() > 0) {
			currentShoppingList = getIntent().getExtras().getString(
					"shoppingListName");
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addNewIngredient:
			startActivity(new Intent(getApplicationContext(),
					AddIngredientActivity.class).putExtra("shoppingListName",
					currentShoppingList).putExtra("ingredientTitle",
					searchBar.getText().toString()));
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void makeListEntriesClickable() {
		elementsListView.setOnItemClickListener(this);
	}

	protected void openQuantityDialog(int position) {
		DialogFragment quantityDialog = QuantityPickerDialog.newInstance(
				(IListElement) elementsToShow.get(position), app);
		quantityDialog.show(getSupportFragmentManager(), TAG);
	}

	// protected void setNextActivityOnClick(View view,
	// final Class<?> nextActivityClass) {
	// view.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// startActivity(new Intent(getApplicationContext(),
	// nextActivityClass).putExtra("shoppingListName",
	// currentShoppingList));
	// }
	// });
	// }

	// private void addLayoutChangeListener() {
	// final View view = findViewById(R.id.activity_ingredient_collector);
	// view.getViewTreeObserver().addOnGlobalLayoutListener(
	// new OnGlobalLayoutListener() {
	//
	// @SuppressWarnings("deprecation")
	// @Override
	// public void onGlobalLayout() {
	// view.getViewTreeObserver()
	// .removeGlobalOnLayoutListener(this);
	//
	// }
	// });
	// }

	protected void initiateAllViews() {
		searchBar = (EditText) findViewById(R.id.searchBarInput);
		elementsListView = (ListView) findViewById(R.id.elementsList);
		searchBar.addTextChangedListener(this);
		progressWheel = (ProgressBar) this
				.findViewById(R.id.collectorProgressBar);
		noResultsFound = findViewById(R.id.noResultsFoundView);
	}

	protected void fetchDataFromDb(AsyncTask<Void, Void, List<T>> fetchDataTask) {
		fetchDataTask.execute();
	}

	public ProgressBar getProgressWheel() {
		return progressWheel;
	}

	@Override
	public void afterTextChanged(Editable searchText) {
		List<IListElement> elementsInList = new ArrayList<IListElement>();
		try {
			for (IListElement element : (List<IListElement>) allElements) {
				if (ingredientNameMatchSearchString(element,
						searchText.toString())) {
					elementsInList.add(element);
				}
			}
			informUserWhenNoResults(elementsInList);
			elementsToShow = (List<T>) elementsInList;
		} catch (ClassCastException e) {
			((IngredientsApplication) getApplication())
					.informUser(R.string.developerMistake);
		}
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

	private boolean ingredientNameMatchSearchString(IListElement listElement,
			String searchString) {
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
			if (allElements.isEmpty()) {
				((IngredientsApplication) getApplication())
						.informUser(R.string.noNetworkConnection);
			}
			afterTextChanged(((EditText) findViewById(R.id.searchBarInput))
					.getText());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	protected void testNetworkAndInformUser() {
		if (app.isNetworkConnected()) {
			app.informUser(R.string.addedIngredientAlsoOnServer);
		} else {
			app.informUser(R.string.addedIngredientOnListNotServer);
		}
	}

}
