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
import android.widget.Toast;
import de.nordakademie.smart_kitchen_ingredients.R;

/**
 * @author frederic.oppermann
 * @date 09.12.2013
 * @description
 */

public abstract class AbstractCollectorActivity<T> extends FragmentActivity
		implements TextWatcher, IAsyncTaskObserver<T>,
		QuantityPickerDialogListener {
	protected static String TAG;

	private EditText searchBar;
	private ListView elementsListView;
	private List<T> allElements = new ArrayList<T>();
	private List<T> elementsToShow = new ArrayList<T>();
	private ProgressBar progressWheel;
	private Button addNewIngredient;
	private View noResultsFound;
	private IListElement currentElement;

	private Context context;

	protected String currentShoppingList;

	public List<T> getElementsToShow() {
		return elementsToShow;
	}

	public IListElement getCurrentElement() {
		return currentElement;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().getExtras() != null
				&& getIntent().getExtras().size() > 0) {
			currentShoppingList = getIntent().getExtras().getString(
					"shoppingListName");
		}
		TAG = this.getClass().getSimpleName();
		setContentView(R.layout.activity_ingredient_collector);
		initiateAllViews();
		addLayoutChangeListener();
		makeListEntriesClickable();
		context = getApplicationContext();
		startActivity(new Intent(getApplicationContext(),
				AddIngredientActivity.class).putExtra("shoppingListName",
				currentShoppingList).putExtra("ingredientTitle",
				currentElement.getName()));
	}

	private void makeListEntriesClickable() {
		elementsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long arg3) {
				currentElement = (IListElement) adapterView.getAdapter()
						.getItem(position);

				DialogFragment quantityDialog = QuantityPickerDialog
						.newInstance(currentElement);
				quantityDialog.show(getSupportFragmentManager(), TAG);
			}
		});
	}

	protected void setNextActivityOnClick(View view,
			final Class<?> nextActivityClass) {
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, nextActivityClass));
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
			informUser(R.string.developerMistake);
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

	public void informUser(int stringId) {
		Toast.makeText(context, stringId, Toast.LENGTH_LONG).show();
	}
}