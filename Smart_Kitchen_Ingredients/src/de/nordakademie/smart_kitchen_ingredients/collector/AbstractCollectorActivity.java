package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import de.nordakademie.smart_kitchen_ingredients.AbstractListActivity;
import de.nordakademie.smart_kitchen_ingredients.AbstractSmartKitchenActivity;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.shoppinglist.ShoppingListIngredientsActivity;

/**
 * @author frederic.oppermann
 * @date 09.12.2013
 * @description
 */

public abstract class AbstractCollectorActivity<T> extends AbstractListActivity
		implements TextWatcher, IAsyncTaskObserver<T>,
		QuantityPickerDialogListener {
	protected static String TAG;

	private EditText searchBar;
	private ListView elementsListView;
	private List<T> allElements = new ArrayList<T>();
	private List<T> elementsToShow = new ArrayList<T>();
	private ProgressBar progressWheel;
	protected Button confirmShoppingList;
	private View noResultsFound;
	private IngredientsApplication app;

	private Context context;

	protected String currentShoppingList;

	public List<T> getElementsToShow() {
		return elementsToShow;
	}

	protected ListView getElementsListView() {
		return elementsListView;
	}

	public void setNewAdapter(ListAdapter adapter) {
		elementsListView.setAdapter(adapter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredient_collector);
		if (getIntent().getExtras() != null
				&& getIntent().getExtras().size() > 0) {
			currentShoppingList = getIntent().getExtras().getString(
					"shoppingListName");
		}
		TAG = this.getClass().getSimpleName();
		app = (IngredientsApplication) getApplication();
		setContentView(R.layout.activity_ingredient_collector);
		initiateAllViews();
		addLayoutChangeListener();
		makeListEntriesClickable();
		context = getApplicationContext();
		confirmShoppingList = (Button) findViewById(R.id.confirmShoppingList);
		confirmShoppingList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(context,
						ShoppingListIngredientsActivity.class).putExtra(
						"shoppingListName", currentShoppingList));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.collection_menu, menu);
		Log.i(TAG, "menu inflated");
		return true;
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
		elementsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long arg3) {
				DialogFragment quantityDialog = QuantityPickerDialog
						.newInstance((IListElement) adapterView.getAdapter()
								.getItem(position), app);
				quantityDialog.show(getSupportFragmentManager(), TAG);
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
		noResultsFound = findViewById(R.id.noResultsFoundView);

	}

	protected void fetchDataFromDb(AsyncTask<Void, Void, List<T>> fetchDataTask) {
		fetchDataTask.execute();
	}

	public ProgressBar getProgressWheel() {
		return progressWheel;
	}

	@Override
	public void afterTextChanged(Editable s) {
		noResultsFound.setVisibility(View.GONE);

		List<IListElement> elementsInList = new ArrayList<IListElement>();
		try {
			for (IListElement element : (List<IListElement>) allElements) {
				if (ingredientNameMatchSearchString(element)) {
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

	private boolean ingredientNameMatchSearchString(IListElement listElement) {
		String searchString = searchBar.getText().toString();
		return listElement.getName().toLowerCase(Locale.GERMAN)
				.contains(searchString.toLowerCase(Locale.GERMAN));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
