package de.nordakademie.smart_kitchen_ingredients;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.factories.IngredientFactory;

public abstract class AbstractAddNewIngredientDialog extends DialogFragment {

	String ingredientTitle;
	Button saveIngredientButton;
	TextView ingredientTitleTV;
	TextView ingredientQuantityTV;
	Spinner ingredientUnit;
	private IngredientsApplication app;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = getCurrentView();
		instantiaveViews(view);
		setOnClickListener(view);
		return buildDialog(view);
	}

	public IngredientsApplication getApp() {
		return app;
	}

	private Dialog buildDialog(View view) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				getActivity());
		dialogBuilder.setTitle(
				getString(R.string.title_activity_add_ingredient))
				.setView(view);
		return dialogBuilder.create();
	}

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

	private boolean isItemAlreadyInDb(IIngredient newItem) {
		return app.getCacheDbHelper().itemExists(newItem.getName());
	}

	private void setOnClickListener(View view) {
		saveIngredientButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String title = ingredientTitleTV.getText().toString();
				if (!titleAlreadyOnServer(title)) {

					Unit unit = Unit.valueOfFromShortening(ingredientUnit
							.getSelectedItem().toString());

					if (ingredientQuantityTV.getText().toString().equals("")) {
						app.informUser(R.string.amountNeeded);
					} else if (title.equals("")) {
						app.informUser(R.string.nameNeeded);
					} else if (1 > Integer.valueOf(ingredientQuantityTV
							.getText().toString())) {
						app.informUser(R.string.enterAmountMoreThanNull);
					} else {
						Integer amount = Integer.valueOf(ingredientQuantityTV
								.getText().toString());
						saveIngredientAndLeave(title, amount, unit);
					}
				} else {
					app.informUser(R.string.ingredientOnServer);
				}
			}
		});
	}

	private void instantiaveViews(View view) {
		ingredientTitleTV = (EditText) view
				.findViewById(R.id.ingredientNameEdit);
		ingredientQuantityTV = (TextView) view.findViewById(
				R.id.amountLinearLayout)
				.findViewById(R.id.ingredientAmountEdit);

		ingredientUnit = (Spinner) view.findViewById(R.id.amountLinearLayout)
				.findViewById(R.id.ingredientUnitSpinner);
		ingredientUnit.setAdapter(AdapterFactory.createUnitAdapter(
				app.getApplicationContext(),
				android.R.layout.simple_spinner_item, Unit.values()));
		ingredientTitleTV.setText(ingredientTitle);
		saveIngredientButton = (Button) view
				.findViewById(R.id.submitNewIngredientButton);
	}

	private View getCurrentView() {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.add_ingredient_layout, null);
		return view;
	}

	protected void setApplication(IngredientsApplication app) {
		this.app = app;
	}

	protected void saveIngredientAndLeave(String title, Integer amount,
			Unit unit) {
		try {
			testNetworkAndInformUser();
			saveNewIngredientToDBs(title, amount, unit);
		} finally {
			this.dismiss();
		}
	}

	protected void saveNewIngredientToDBs(String title, Integer quantity,
			Unit unit) {
		IIngredient newItem = IngredientFactory.createIngredient(title, unit);

		fetchDataFromDb(new PostNewIngredientAsyncTask(newItem,
				app.getServerHandler(), app.getCacheDbHelper()));

		if (!isItemAlreadyInDb(newItem)) {
			insertIntoLocalDb(newItem, quantity);
		}
	}

	protected abstract void insertIntoLocalDb(IIngredient newItem,
			Integer quantity);
}
