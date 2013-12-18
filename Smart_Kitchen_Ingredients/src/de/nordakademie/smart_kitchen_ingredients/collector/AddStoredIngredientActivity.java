package de.nordakademie.smart_kitchen_ingredients.collector;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

public class AddStoredIngredientActivity extends AddIngredientActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ingredientUnit.setEnabled(false);
	}

	@Override
	public void onClick(View view) {
		String title = ingredientTitleTV.getText().toString();
		Unit unit = Unit.valueOf(ingredientUnit.getSelectedItem().toString());
		int quantity = Integer
				.parseInt(ingredientQuantityTV.getText().toString());

		IIngredient ingredient = app.getIngredientFactory().createIngredient(
				title, unit);
		app.getStoredDbHelper().insertOrUpdateIngredient(ingredient, quantity);
	}
}
