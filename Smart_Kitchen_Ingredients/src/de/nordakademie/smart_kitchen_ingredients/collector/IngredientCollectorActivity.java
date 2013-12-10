package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.localdata.IIngredientData;

/**
 * @author frederic.oppermann
 * @date 09.12.2013
 * @description
 */
public class IngredientCollectorActivity extends Activity implements
		TextWatcher {
	EditText searchBar;
	ListView ingredientsList;
	private IIngredientData ingredientDb;
	private ListAdapter adapter;
	private IngredientsApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (IngredientsApplication) getApplication();
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
		adapter = new ArrayAdapter<Ingredient>(getApplicationContext(),
				R.layout.list_view_entry, Arrays.asList(new Ingredient()));

		ingredientsList.setAdapter(adapter);
	}
}