package de.nordakademie.smart_kitchen_ingredients.collector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public class AddIngredientActivity extends Activity implements OnClickListener {

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

		// saveIngredientButton.setOnClickListener(this);
		quitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						IngredientCollectorActivity.class));
			}
		});

		saveIngredientButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// Abfrage ob alle benötigten Infos eingegeben

				try {
					TextView titleView = (TextView) findViewById(R.id.ingredientNameEdit);
					String title = titleView.getText().toString();
					TextView amountView = (TextView) findViewById(R.id.ingredientAmountEdit);
					Integer amount = Integer.valueOf(amountView.getText()
							.toString());
					Spinner unitView = (Spinner) findViewById(R.id.ingredientUnitSpinner);
					Unit unit = Unit.valueOf(unitView.getFocusedChild()
							.toString());

					saveNewIngredientToDBs(title, amount, unit);

					// Information Zutat wurde gespeichert
					// PopupWindow savedInfo = new PopupWindow();
					// savedInfo.setContentView(view);
				} finally {
					startActivity(new Intent(getApplicationContext(),
							IngredientCollectorActivity.class));
				}
			}

			private void saveNewIngredientToDBs(String title, Integer quantity,
					Unit unit) {
				IShoppingListItem newItem = app.getShoppingListItemFactory()
						.createShoppingListItem(title, unit, false);
				app.getServerHandler().postIngredientToServer(newItem);
				app.getShoppingDbHelper().addItem(newItem, quantity);
			}
		});
	}

	@Override
	public void onClick(View view) {

	}

}
