package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItem;
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
	TextView ingredientAmountTV;
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
		ingredientAmountTV = (TextView) findViewById(R.id.ingredientAmountEdit);
		ingredientUnit = (Spinner) findViewById(R.id.ingredientUnitSpinner);

		ingredientTitleTV.setText(ingredientTitle);
		
		//saveIngredientButton.setOnClickListener(this);
		quitButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						IngredientCollectorActivity.class));
			}});
saveIngredientButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Abfrage ob alle benötigten Infos eingegeben
				//speichere Zutat in Datenbank
				
				//füge Zutat dem Einkaufszettel hinzu
				//saveNewShoppingListItem(title, amount, unit);
				
				//Information Zutat wurde gespeichert
				PopupWindow savedInfo = new PopupWindow();
				//savedInfo.setContentView(view);
				
				startActivity(new Intent(getApplicationContext(),
						IngredientCollectorActivity.class));
			}

			private void saveNewShoppingListItem(String title, Integer amount, Unit unit) {
				ShoppingListItem newItem = (ShoppingListItem) app.getShoppingListItemFactory()
				.createShoppingListItem(title, amount, unit, false);
				List ingredientList = new ArrayList();
				ingredientList.add(newItem);
				//app.getShoppingDbHelper().insertOrIgnore(ingredientList);
			}
		});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

	}

}
