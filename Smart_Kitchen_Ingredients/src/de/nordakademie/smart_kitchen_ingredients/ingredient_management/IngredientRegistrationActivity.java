package de.nordakademie.smart_kitchen_ingredients.ingredient_management;

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
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.shoppinglist.IModifyableList;

public class IngredientRegistrationActivity extends Activity implements
		IModifyableList {

	private static String TAG = IngredientRegistrationActivity.class
			.getSimpleName();
	private Button addIngredientsButton;
	private Button addToShoppinglistButton;
	private ListView ingredientsListView;
	private TextView ingredientTitle;
	private List<IIngredient> ingredientsList;
	private IngredientsApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_erfassung);

		app = (IngredientsApplication) getApplication();

		ingredientsList = new ArrayList<IIngredient>();

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

	private void updateList() {
		final DeletableArrayAdapter adapter = new DeletableArrayAdapter(
				this.getApplicationContext(), this);

		ingredientsListView.setAdapter(adapter);
		Log.i(TAG, "list updated");
	}

	private void addIngredientToList() {
		String title = ingredientTitle.getText().toString();
		IIngredientFactory factory = new IngredientFactory();
		IIngredient newIngredient = factory
				.createIngredient(title, 0, Unit.stk);
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
		List<String> ingredientStrings = new ArrayList<String>();
		for (IIngredient ingredient : ingredientsList) {
			ingredientStrings.add(ingredient.getTitle());
		}

		return ingredientStrings;
	}

	@Override
	public void checkAndUpdateValueAtPosition(String title) {
		// ingredientsList.remove(position);
		// Log.i(TAG, "ingredient deleted");
		// updateList();
	}

	@Override
	public List<IShoppingListItem> getShoppingItems() {
		// TODO Auto-generated method stub
		return null;
	}
}
