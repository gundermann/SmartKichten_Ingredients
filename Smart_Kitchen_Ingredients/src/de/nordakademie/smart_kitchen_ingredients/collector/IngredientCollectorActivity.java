package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.localdata.CacheUpdaterService;
import de.nordakademie.smart_kitchen_ingredients.localdata.IIngredientCacheData;

/**
 * @author frederic.oppermann
 * @date 09.12.2013
 * @description
 */
public class IngredientCollectorActivity extends Activity implements
		TextWatcher, OnClickListener, OnItemClickListener {
	EditText searchBar;
	ListView ingredientsList;
	Button button;
	private IIngredientCacheData ingredientDb;
	private ListAdapter adapter;
	private IngredientsApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (IngredientsApplication) getApplication();
		ingredientDb = app.getIngredientDbHelper();
		setContentView(R.layout.activity_ingredient_collector);
		ingredientsList = (ListView) findViewById(R.id.ingredientList);
		ingredientsList.setOnItemClickListener(this);
		searchBar = (EditText) findViewById(R.id.ingredientNameInput);
		searchBar.addTextChangedListener(this);
		button = (Button) findViewById(R.id.newIngredientButton);
		button.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		startService(new Intent(app.getApplicationContext(),
				CacheUpdaterService.class));

		adapter = new ArrayAdapter<IIngredient>(getApplicationContext(),
				R.layout.list_view_entry, ingredientDb.getAllIngredients());
		ingredientsList.setAdapter(adapter);
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		adapter = new ArrayAdapter<IIngredient>(getApplicationContext(),
				R.layout.list_view_entry, filterItemsByName(s));

		ingredientsList.setAdapter(adapter);
	}

	private IIngredient[] filterItemsByName(CharSequence s) {
		List<IIngredient> filteredItems = new ArrayList<IIngredient>();

		for (IIngredient ingredientFromList : ingredientDb.getAllIngredients()) {
			if (ingredientFromList.getTitle().contains(s)) {
				filteredItems.add(ingredientFromList);
			}
		}
		IIngredient[] ingredientArray = new IIngredient[filteredItems.size()];

		for (int i = 0; i < filteredItems.size(); i++) {
			ingredientArray[i] = filteredItems.get(i);
		}
		return ingredientArray;
	}

	@Override
	public void onClick(View view) {
		addNewIngredientToShoppingList(searchBar.getText().toString());
	}

	@Deprecated
	private void addNewIngredientToShoppingList(String title) {
		List<IIngredient> ingredients = new ArrayList<IIngredient>();
		ingredients.add(app.getIngredientFactory().createIngredient(title, 0,
				Unit.g));
		app.getShoppingDbHelper().insertOrIgnoreShoppingItems(ingredients);
		super.onBackPressed();
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position,
			long arg3) {
		addNewIngredientToShoppingList(adapter.getItemAtPosition(position)
				.toString());

	}
}