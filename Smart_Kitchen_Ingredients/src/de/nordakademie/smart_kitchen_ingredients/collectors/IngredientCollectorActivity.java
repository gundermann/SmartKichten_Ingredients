package de.nordakademie.smart_kitchen_ingredients.collectors;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
	private IIngredientDb ingredientDb;
	private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ingredientDb = new IngredientDb();
		setContentView(R.layout.activity_ingredient_collector);
		ingredientsList = (ListView) findViewById(R.id.ingredientList);
		searchBar = (EditText) findViewById(R.id.ingredientNameInput);
		searchBar.addTextChangedListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		adapter = new ArrayAdapter<IIngredient>(getApplicationContext(),
				R.layout.list_view_entry, ingredientDb.getIngredients());
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
		adapter = new ArrayAdapter<Ingredient>(getApplicationContext(),
				R.layout.list_view_entry, Arrays.asList(new Ingredient()));

		ingredientsList.setAdapter(adapter);
	}
}