package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.IntentIntegrator;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.IntentResult;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.collector.IngredientCollectorActivity;
import de.nordakademie.smart_kitchen_ingredients.scheduling.ShoppingDateActivity;
import de.nordakademie.smart_kitchen_ingredients.stock.StoredIngredientActivity;

/**
 * 
 * @author Niels Gundermann
 * 
 */
public class ShoppingListActivity extends Activity implements IModifyableList,
		OnClickListener {

	private static final int REQUEST_CODE = 1;
	private static String TAG = ShoppingListActivity.class.getSimpleName();
	private ListView shoppingListView;
	private ImageButton btAddNewShoppingItem;
	private IngredientsApplication app;
	private Bitmap bitmap;
	private BroadcastReceiver notifyShoppingdataChange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_layout);

		app = (IngredientsApplication) getApplication();
		btAddNewShoppingItem = (ImageButton) findViewById(R.id.addNewShoppingItem);
		btAddNewShoppingItem.setOnClickListener(this);
		shoppingListView = (ListView) findViewById(R.id.shoppingList);

		TextView emptyView = new TextView(getApplicationContext());
		emptyView.setText(R.string.emptyShoppingList);
		shoppingListView.setEmptyView(findViewById(android.R.id.empty));

		Log.i(TAG, "created");
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateShoppingList();
		notifyShoppingdataChange = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				updateShoppingList();
				Log.d(this.getClass().getSimpleName(), "onReceive");
			}
		};

		IntentFilter broadcastIntentFilter = new IntentFilter(
				IngredientsApplication.CHANGING);
		registerReceiver(notifyShoppingdataChange, broadcastIntentFilter,
				IngredientsApplication.PERMISSION, null);
		Log.i(TAG, "broadcastreceiver registed");
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(notifyShoppingdataChange);
	}

	private void updateShoppingList() {
		ShoppingListAdapter adapter = new ShoppingListAdapter(
				getApplicationContext(), this);
		shoppingListView.setAdapter(adapter);
		Log.i(TAG, "shoppinglist updated");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_clean_shoppingList:
			Intent cleanIntent = new Intent(this,
					ShoppingDataCleanUpService.class);
			startService(cleanIntent);
			break;
		case R.id.menu_qrscan:
			// Ist nur mit einer "vernünftigen" Kamera zu empfehlen
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
			break;
		case R.id.menu_shoppingdate:
			Intent dateIntent = new Intent(this, ShoppingDateActivity.class);
			startActivity(dateIntent);
			break;
		case R.id.menu_edit_stored_items:
			Intent storedIntent = new Intent(this,
					StoredIngredientActivity.class);
			startActivity(storedIntent);
			break;
		case R.id.menu_import_shoppinglist:
			Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fotoIntent.setType("image/*");
			fotoIntent.setAction(Intent.ACTION_GET_CONTENT);
			fotoIntent.addCategory(Intent.CATEGORY_OPENABLE);
			startActivityForResult(fotoIntent, REQUEST_CODE);
		default:
			break;
		}
		return true;
	}

	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for (IShoppingListItem item : getShoppingItems()) {
			values.add(item.getName());
		}
		Log.i(TAG, "title of shoppingitems collected");
		return values;
	}

	@Override
	public void checkAndUpdateValueAtPosition(String title) {
		IShoppingListItem item = app.getShoppingDbHelper().getShoppingItem(
				title);
		if (!item.isBought()) {
			item.setBought(true);
		} else {
			item.setBought(false);
		}
		app.getShoppingDbHelper().updateShoppingItem(item);
		Log.i(TAG, "ingredient bought");
		updateShoppingList();
	}

	@Override
	public List<IShoppingListItem> getShoppingItems() {
		List<IShoppingListItem> shoppingItems = new ArrayList<IShoppingListItem>();
		TreeSet<IShoppingListItem> ingredients = new TreeSet<IShoppingListItem>();
		ingredients.addAll(app.getShoppingDbHelper().getAllShoppingItems());
		for (IShoppingListItem item : ingredients) {
			shoppingItems.add(item);
		}
		Log.i(TAG, "sort ingredients from database");

		return shoppingItems;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				IngredientCollectorActivity.class);
		startActivity(intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (requestCode == REQUEST_CODE) {
			handleFotoResult(resultCode, intent);
		} else {
			boolean success = false;
			if (scanningResult != null) {
				String itemDescription = app.getBarcodeEvaluator()
						.getItemDescription(scanningResult.getContents());
				success = evaluateBarcodeScan(itemDescription);

				if (success) {
					Toast toast = Toast.makeText(getApplicationContext(),
							getText(R.string.scansuccess), Toast.LENGTH_SHORT);
					toast.show();
					Log.i(TAG, "ingredients matches");
				} else {
					Toast toast = Toast.makeText(getApplicationContext(),
							getText(R.string.scanfault), Toast.LENGTH_SHORT);
					toast.show();
					Log.i(TAG, "ingredients doesn't match");
				}
			} else {
				Toast toast = Toast.makeText(getApplicationContext(),
						getText(R.string.scanerror), Toast.LENGTH_SHORT);
				toast.show();
				Log.i(TAG, "scan finished with errors");
			}
		}
	}

	private void handleFotoResult(int resultCode, Intent intent) {
		InputStream stream = null;
		if (resultCode == Activity.RESULT_OK) {
			try {
				if (bitmap != null) {
					bitmap.recycle();
				}
				stream = getContentResolver().openInputStream(intent.getData());
				bitmap = BitmapFactory.decodeStream(stream);
				stream.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean evaluateBarcodeScan(String content) {
		for (IShoppingListItem shoppingItem : getShoppingItems()) {
			if (content.contains(shoppingItem.getName())) {
				checkAndUpdateValueAtPosition(shoppingItem.getName());
				return true;
			}
		}
		return false;
	}

}
