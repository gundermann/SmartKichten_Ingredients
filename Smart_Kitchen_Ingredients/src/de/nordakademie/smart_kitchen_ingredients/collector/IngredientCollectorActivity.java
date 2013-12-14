package de.nordakademie.smart_kitchen_ingredients.collector;

import android.os.Bundle;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class IngredientCollectorActivity extends
		AbstractCollectorActivity<IIngredient> {

	private Button showRecepies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showRecepies = (Button) findViewById(R.id.showRecipesButton);
	}
}
