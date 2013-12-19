package de.nordakademie.smart_kitchen_ingredients.shopping.barcodescan;

import java.util.List;

import android.os.AsyncTask;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.IShoppingDbHelper;
import de.nordakademie.smart_kitchen_ingredients.shopping.ShoppingListIngredientsActivity;

public class CheckBarcodeAysncTask extends
		AsyncTask<String, IngredientsApplication, String> {

	String barcode;
	IBarcodeServerHandler barcodeServerHandler;
	private ShoppingListIngredientsActivity observer;
	private String apikey;

	public CheckBarcodeAysncTask(String barcode,
			IBarcodeServerHandler barcodeServerHandler,
			List<IShoppingListItem> shoppingItems,
			String currentShoppingListName, IShoppingDbHelper shoppingDbHelper,
			String apikey,
			ShoppingListIngredientsActivity shoppingListIngredientsActivity) {
		this.barcode = barcode;
		this.barcodeServerHandler = barcodeServerHandler;
		observer = shoppingListIngredientsActivity;
		this.apikey = apikey;
	}

	@Override
	protected String doInBackground(String... params) {
		return barcodeServerHandler.getItemDescription(barcode, apikey);
	}

	@Override
	protected void onPostExecute(String result) {
		observer.evaluateBarcodeScan(result);
	}
}
