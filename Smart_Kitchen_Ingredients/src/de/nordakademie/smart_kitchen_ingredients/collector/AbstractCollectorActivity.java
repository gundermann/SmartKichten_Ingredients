package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import de.nordakademie.smart_kitchen_ingredients.AbstractActivity;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.QuantityPickerDialog;
import de.nordakademie.smart_kitchen_ingredients.QuantityPickerDialogListener;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;
import de.nordakademie.smart_kitchen_ingredients.tasks.IAsyncTaskObserver;

/**
 * @author Frederic Oppermann
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

	protected void initElements() {
		initiateAllViews();
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

	private void makeListEntriesClickable() {
		elementsListView.setOnItemClickListener(this);
	}

	protected void openQuantityDialog(int position) {
		DialogFragment quantityDialog = QuantityPickerDialog.newInstance(
				(IListElement) elementsToShow.get(position), app);
		quantityDialog.show(getSupportFragmentManager(), TAG);
	}

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
		elementsListView.setAdapter(getAdapter());
	}

	private void informUserWhenNoResults(List<IListElement> elementsInList) {
		if (elementsInList.isEmpty()) {
			noResultsFound.setVisibility(View.VISIBLE);
			elementsListView.setVisibility(View.GONE);
		} else {
			noResultsFound.setVisibility(View.GONE);
			elementsListView.setVisibility(View.VISIBLE);
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

	@Override
	public void update(AsyncTask<Void, Void, List<T>> task) {
		try {
			this.allElements = task.get();
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
		elementsListView.setAdapter(getAdapter());
	}

	protected abstract ListAdapter getAdapter();

	protected void testNetworkAndInformUser() {
		if (app.isNetworkConnected()) {
			app.informUser(R.string.addedIngredientAlsoOnServer);
		} else {
			app.informUser(R.string.addedIngredientOnListNotServer);
		}
	}

}
