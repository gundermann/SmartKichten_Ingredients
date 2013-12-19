package de.nordakademie.smart_kitchen_ingredients.collector;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.ShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public class AddIngredientActivity extends Activity {

	IngredientsApplication app;
	String ingredientTitle;
	Button saveIngredientButton;
	TextView ingredientTitleTV;
	TextView ingredientQuantityTV;
	Spinner ingredientUnit;
	private String currentShoppingListName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("SmartKitchenActivity", this.getClass().getSimpleName());
		setContentView(R.layout.add_ingredient_layout);
		app = (IngredientsApplication) this.getApplication();

		saveIngredientButton = (Button) findViewById(R.id.submitNewIngredientButton);

		if (getIntent().getExtras() != null
				&& getIntent().getExtras().size() > 0) {
			ingredientTitle = getIntent().getExtras().getString(
					"ingredientTitle");
			currentShoppingListName = getIntent().getExtras().getString(
					"shoppingListName");

		}

		app = (IngredientsApplication) getApplication();
		ingredientTitleTV = (TextView) findViewById(R.id.ingredientNameEdit);
		ingredientQuantityTV = (TextView) findViewById(R.id.ingredientAmountEdit);
		
		ingredientUnit = (Spinner) findViewById(R.id.ingredientUnitSpinner);
		SpinnerAdapter adapter = (SpinnerAdapter) new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, Unit.values());
		ingredientUnit.setAdapter(adapter);
		
		ingredientTitleTV.setText(ingredientTitle);

		saveIngredientButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView titleView = (TextView) findViewById(R.id.ingredientNameEdit);
				String title = titleView.getText().toString();
				if (!titleAlreadyOnServer(title)) {

					TextView amountView = (TextView) findViewById(R.id.ingredientAmountEdit);
					Spinner unitSpinner = (Spinner) findViewById(R.id.ingredientUnitSpinner);
					Unit unit = Unit.valueOfFromShortening(unitSpinner
							.getSelectedItem().toString());

					if (amountView.getText().toString().equals("")) {
						app.informUser(R.string.amountNeeded);
					} else if (title.equals("")) {
						app.informUser(R.string.nameNeeded);
					} else if (1 > Integer.valueOf(amountView.getText().toString())) {
							app.informUser(R.string.enterAmountMoreThanNull);
					} else {
						Integer amount = Integer.valueOf(amountView.getText()
								.toString());
						saveIngredientAndLeave(title, amount, unit);
					}
				} else {
					app.informUser(R.string.ingredientOnServer);
				}
			}
		});
	}

	private boolean titleAlreadyOnServer(String title) {
		return app.getCacheDbHelper().itemExists(title);
	}

	protected void saveIngredientAndLeave(String title, Integer amount,
			Unit unit) {
		try {
			testNetworkAndInformUser();
			saveNewIngredientToDBs(title, amount, unit);
		} finally {
			startActivity(new Intent(getApplicationContext(),
					IngredientCollectorActivity.class));
			finish();
		}
	}

	protected void testNetworkAndInformUser() {
		if (app.isNetworkConnected()) {
			app.informUser(R.string.addedIngredientAlsoOnServer);
		} else {
			app.informUser(R.string.addedIngredientOnListNotServer);
		}
	}

	protected void fetchDataFromDb(AsyncTask<Void, Void, Boolean> updateDataTask) {
		updateDataTask.execute();
	}

	private void saveNewIngredientToDBs(String title, Integer quantity,
			Unit unit) {
		IShoppingListItem newItem = ShoppingListItemFactory
				.createShoppingListItem(title, quantity, unit, false);

		fetchDataFromDb(new PostNewIngredientAsyncTask(newItem,
				app.getServerHandler(), app.getCacheDbHelper()));

		if (!isItemAlreadyInDb(newItem)) {
			app.getShoppingDbHelper().addItem(newItem, quantity,
					currentShoppingListName);
			//app.informUser(R.string.ingredientSaved);
		}

	}

	private boolean isItemAlreadyInDb(IShoppingListItem newItem) {
		return app.getShoppingDbHelper().getShoppingItem(newItem.getName()) != null;
	}
}
