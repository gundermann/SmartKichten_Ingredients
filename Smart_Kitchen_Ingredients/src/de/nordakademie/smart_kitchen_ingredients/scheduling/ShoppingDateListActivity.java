package de.nordakademie.smart_kitchen_ingredients.scheduling;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.R;

public class ShoppingDateListActivity extends Activity {

	private ListView shoppingDateList;
	private Button addShoppingDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);

		shoppingDateList = (ListView) findViewById(R.id.shoppingList);
		addShoppingDate = (Button) findViewById(R.id.addNewShoppingItem);

	}
}
