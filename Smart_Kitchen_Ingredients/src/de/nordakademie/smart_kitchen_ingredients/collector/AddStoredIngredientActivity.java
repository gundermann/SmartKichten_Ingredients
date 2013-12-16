package de.nordakademie.smart_kitchen_ingredients.collector;

import android.os.Bundle;
import android.view.View;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

public class AddStoredIngredientActivity extends AddIngredientActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ingredientUnit.setEnabled(false);
	}

	@Override
	public void onClick(View view) {
		String title = ingredientTitleTV.getText().toString();
		Unit unit = Unit.valueOf(ingredientUnit.getSelectedItem().toString());
		int amount = Integer.parseInt(ingredientAmountTV.getText().toString());

		IIngredient ingredient = app.getIngredientFactory().createIngredient(
				title, amount, unit);
		app.getStoredDbHelper().insertOrUpdateIngredient(ingredient);
	}
}
