package de.nordakademie.smart_kitchen_ingredients.collector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public class AddIngredientActivity extends Activity implements OnClickListener {

	IngredientsApplication app;
	String ingredientTitle;

	Button addIngredientButton;
	TextView ingredientTitleTV;
	TextView ingredientAmountTV;
	Spinner ingredientUnit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_ingredient);

		if (getIntent().getExtras() != null) {
			ingredientTitle = getIntent().getExtras().get("ingredientTitle")
					.toString();
		}

		app = (IngredientsApplication) getApplication();
		addIngredientButton = (Button) findViewById(R.id.submitNewIngredientButton);
		ingredientTitleTV = (TextView) findViewById(R.id.ingredientNameEdit);
		ingredientAmountTV = (TextView) findViewById(R.id.ingredientAmountEdit);
		ingredientUnit = (Spinner) findViewById(R.id.ingredientUnitSpinner);

		ingredientTitleTV.setText(ingredientTitle);
		addIngredientButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

	}

}
