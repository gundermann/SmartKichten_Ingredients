package de.nordakademie.smart_kitchen_ingredients;
/**
 * @author Kathrin Kurtz
 */
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;

public abstract class AbstractAddIngredientActivity extends AbstractActivity {

	protected String ingredientTitle;
	protected Button saveIngredientButton;
	protected TextView ingredientTitleTV;
	protected TextView ingredientQuantityTV;
	protected Spinner ingredientUnit;
	private String currentShoppingListName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_ingredient_dialog_layout);

		saveIngredientButton = (Button) findViewById(R.id.submitNewIngredientButton);

		if (getIntent().getExtras() != null
				&& getIntent().getExtras().size() > 0) {
			ingredientTitle = getIntent().getExtras().getString(
					"ingredientTitle");
			currentShoppingListName = getIntent().getExtras().getString(
					"shoppingListName");

		}

		ingredientTitleTV = (TextView) findViewById(R.id.ingredientNameEdit);
		ingredientQuantityTV = (TextView) findViewById(R.id.amountLinearLayout)
				.findViewById(R.id.ingredientAmountEdit);

		ingredientUnit = (Spinner) findViewById(R.id.amountLinearLayout)
				.findViewById(R.id.ingredientUnitSpinner);
		ingredientUnit.setAdapter(AdapterFactory.createUnitAdapter(
				app.getApplicationContext(),
				android.R.layout.simple_spinner_item, Unit.values()));

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
					} else if (1 > Integer.valueOf(amountView.getText()
							.toString())) {
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

	protected void saveIngredientAndLeave(String title, Integer amount,
			Unit unit) {
		try {
			testNetworkAndInformUser();
			saveNewIngredientToDBs(title, amount, unit);
		} finally {
			startPrevActivity();
			finish();
		}
	}

	protected abstract void startPrevActivity();

	protected abstract void saveNewIngredientToDBs(String title,
			Integer amount, Unit unit);

	private boolean titleAlreadyOnServer(String title) {
		return app.getCacheDbHelper().itemExists(title);
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

	public String getCurrentShoppingListName() {
		return currentShoppingListName;
	}

}
