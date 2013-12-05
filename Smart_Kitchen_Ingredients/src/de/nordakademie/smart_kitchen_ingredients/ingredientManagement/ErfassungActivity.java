package de.nordakademie.smart_kitchen_ingredients.ingredientManagement;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.ModifyableList;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingItem;

public class ErfassungActivity extends Activity implements ModifyableList {

	private static String TAG = ErfassungActivity.class.getSimpleName();
	private Button addIngredientsButton;
	private Button addToShoppinglistButton;
	private ListView ingredientsListView;
	private TextView ingredientTitle;
	private List<String> ingredientsList;
	private IngredientsApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_erfassung);

		app = (IngredientsApplication) getApplication();

		ingredientsList = new ArrayList<String>();

		ingredientsListView = (ListView) findViewById(R.id.ingredientsList);
		ingredientTitle = (TextView) findViewById(R.id.titleIngedients);
		addIngredientsButton = (Button) findViewById(R.id.addIngedientsButton);
		addToShoppinglistButton = (Button) findViewById(R.id.addToShoppinglistButton);

		addIngredientsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				addIngredientToList();
			}

		});

		addToShoppinglistButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				addToShoppingList();
			}

		});

		Log.i(TAG, "create");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.erfassung, menu);
		return true;
	}

	// TODO als Receiver implementieren???
	private void updateList() {
		final DeletableArrayAdapter adapter = new DeletableArrayAdapter(
				this.getApplicationContext(), this);

		ingredientsListView.setAdapter(adapter);
		Log.i(TAG, "list updated");
	}

	private void addIngredientToList() {
		String newIngredient = ingredientTitle.getText().toString();
		ingredientsList.add(newIngredient);

		updateList();
		Log.i(TAG, "added to list");
	}

	private void addToShoppingList() {
		app.getDbHelper().insertOrIgnore(ingredientsList);
		Log.i(TAG, "added to shoppinglist");
		onBackPressed();
	}

	@Override
	public List<String> getValues() {
		return ingredientsList;
	}

	@Override
	public void deleteAndUpdateValueAtPosition(int position) {
		ingredientsList.remove(position);
		Log.i(TAG, "ingredient deleted");
		updateList();
	}

	@Override
	public List<ShoppingItem> getShoppingItems() {
		// TODO Auto-generated method stub
		return null;
	}
}
