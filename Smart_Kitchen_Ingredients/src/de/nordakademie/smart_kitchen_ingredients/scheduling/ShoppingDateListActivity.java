package de.nordakademie.smart_kitchen_ingredients.scheduling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.stock.StoredIngredientActivity;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingDateListActivity extends Activity implements
		OnClickListener {

	private static final String TAG = StoredIngredientActivity.class
			.getSimpleName();

	private ListView shoppingDateList;
	private ImageButton addShoppingDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);

		shoppingDateList = (ListView) findViewById(R.id.shoppingList);
		addShoppingDate = (ImageButton) findViewById(R.id.addNewShoppingItem);

		addShoppingDate.setOnClickListener(this);

		IngredientsApplication app = (IngredientsApplication) getApplication();

		ListAdapter adapter = new ArrayAdapter<IDate>(this,
				android.R.layout.simple_list_item_1, app.getDateDbHelper()
						.getAllDates());
		shoppingDateList.setAdapter(adapter);
		Log.i(TAG, "datelist updated");

	}

	@Override
	public void onClick(View v) {
		Intent dateIntent = new Intent(this, ShoppingDateActivity.class);
		startActivity(dateIntent);

	}

}
