package de.nordakademie.smart_kitchen_ingredients.collector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
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
	Button quitButton;
	TextView ingredientTitleTV;
	TextView ingredientQuantityTV;
	Spinner ingredientUnit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_ingredient);
		app = (IngredientsApplication) this.getApplication();

		quitButton = (Button) findViewById(R.id.quitButton);
		saveIngredientButton = (Button) findViewById(R.id.submitNewIngredientButton);

		if (getIntent().getExtras() != null) {
			ingredientTitle = getIntent().getExtras().get("ingredientTitle")
					.toString();
		}

		app = (IngredientsApplication) getApplication();
		ingredientTitleTV = (TextView) findViewById(R.id.ingredientNameEdit);
		ingredientQuantityTV = (TextView) findViewById(R.id.ingredientAmountEdit);
		ingredientUnit = (Spinner) findViewById(R.id.ingredientUnitSpinner);

		ingredientTitleTV.setText(ingredientTitle);

		quitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						IngredientCollectorActivity.class));
				finish();
			}
		});

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
						showSavedOrNotInformation("Bitte Menge angeben!");
					} else if (amountView.getText().toString().length() > 6) {
						showSavedOrNotInformation("Die Menge ist zu groß!");
					} else if (title.equals("")) {
						showSavedOrNotInformation("Bitte Bezeichnung angeben!");
					} else {
						Integer amount = Integer.valueOf(amountView.getText()
								.toString());
						saveIngredientAndLeave(title, amount, unit);
					}
				}
			}
		});
	}

	private boolean titleAlreadyOnServer(String title) {
		return app.getCacheDbHelper().itemExists(title);
	}

	private void saveIngredientAndLeave(String title, Integer amount, Unit unit) {
		try {
			saveNewIngredientToDBs(title, amount, unit);
			showSavedOrNotInformation("Zutat gespeichert");
		} finally {
			startActivity(new Intent(getApplicationContext(),
					IngredientCollectorActivity.class));
			finish();
		}
	}

	private void showSavedOrNotInformation(String info) {
		Toast toast = Toast.makeText(app, info, Toast.LENGTH_LONG);
		toast.show();
	}

	private void saveNewIngredientToDBs(String title, Integer quantity,
			Unit unit) {
		IShoppingListItem newItem = app.getShoppingListItemFactory()
				.createShoppingListItem(title, unit, false);
		app.getServerHandler().postIngredientToServer(newItem);
		app.getCacheDbHelper().insertOrUpdateAllIngredientsFromServer(
				app.getServerHandler().getIngredientListFromServer());
		app.getShoppingDbHelper().addItem(newItem, quantity);
	}
}
