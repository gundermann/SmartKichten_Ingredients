package de.nordakademie.smart_kitchen_ingredients.collector;

import java.net.UnknownHostException;

import android.os.AsyncTask;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.localdata.ICacheData;
import de.nordakademie.smart_kitchen_ingredients.smartkitchen_server.ISmartKitchenServerHandler;

public class PostNewIngredientAsyncTask extends AsyncTask<Void, Void, Boolean> {

	private ISmartKitchenServerHandler serverHandler;
	private ICacheData cacheDbHelper;
	private IIngredient itemToPost;

	public PostNewIngredientAsyncTask(IIngredient item,
			ISmartKitchenServerHandler serverHandler, ICacheData cacheDbHelper) {
		itemToPost = item;
		this.serverHandler = serverHandler;
		this.cacheDbHelper = cacheDbHelper;
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		try {
			serverHandler.postIngredientToServer(itemToPost);
			cacheDbHelper.insertOrUpdateAllIngredientsFromServer(serverHandler
					.getIngredientListFromServer());
		} catch (UnknownHostException e) {
			return false;
		}
		return true;
	}

}
