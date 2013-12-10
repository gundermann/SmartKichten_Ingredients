package de.nordakademie.smart_kitchen_ingredients.collectors;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.R;

/**
 * @author frederic.oppermann
 * @date 09.12.2013
 * @description
 */
public class IngredientCollectorActivity extends Activity implements
		TextWatcher {
	EditText searchBar;
	ListView ingredientsList;
	private ListAdapter adapter;
	private IIngredientDb ingredientDb;

	private List<IIngredient> ingredientsFromDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ingredientDb = new IngredientDb();
		setContentView(R.layout.activity_ingredient_collector);
		ingredientsList = (ListView) findViewById(R.id.ingredientList);
		searchBar = (EditText) findViewById(R.id.ingredientNameInput);
		searchBar.addTextChangedListener(this);
		ingredientsFromDb = ingredientDb.getIngredients();
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter = new ArrayAdapter<IIngredient>(getApplicationContext(),
				R.layout.list_view_entry, ingredientsFromDb);
		ingredientsList.setAdapter(adapter);
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		List<IIngredient> ingredientsInList = new ArrayList<IIngredient>();

		for (IIngredient ingredient : ingredientsFromDb) {
			if (existIngredient(ingredient)) {
				ingredientsInList.add(ingredient);
			}
		}

		ingredientsList.setAdapter((ListAdapter) new ArrayAdapter<IIngredient>(
				getApplicationContext(), R.layout.list_view_entry,
				ingredientsInList));
	}

	private boolean existIngredient(IIngredient ingredient) {
		String currentSearch = searchBar.getText().toString();
		return ingredient.getName().toLowerCase()
				.contains(currentSearch.toLowerCase());
	}
}