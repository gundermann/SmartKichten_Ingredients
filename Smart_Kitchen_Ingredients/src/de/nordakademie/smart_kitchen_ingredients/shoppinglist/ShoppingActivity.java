package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.R.id;
import de.nordakademie.smart_kitchen_ingredients.R.layout;
import de.nordakademie.smart_kitchen_ingredients.R.menu;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingItem;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class ShoppingActivity extends Activity implements CheckableList {

	private ListView shoppingListView;
	private IngredientsApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_layout);

		app = (IngredientsApplication) getApplication();
		shoppingListView = (ListView) findViewById(R.id.shoppingList);

		updateShoppingList();
	}

	private void updateShoppingList() {
		CheckableArrayAdapter adapter = new CheckableArrayAdapter(
				getApplicationContext(), this);
		shoppingListView.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for (ShoppingItem item : app.getDbHelper().getAllShoppingItems()) {
			values.add(item.getTitle());
		}
		return values;
	}

	@Override
	public void deleteAndUpdateValueAtPosition(int position) {
		ShoppingItem item = app.getDbHelper().getAllShoppingItems()
				.get(position);
		item.setBuyed(true);
		app.getDbHelper().updateShoppingItem(item);
		updateShoppingList();
	}

	@Override
	public List<ShoppingItem> getShoppingItems() {
		return app.getDbHelper().getAllShoppingItems();
	}

}
