/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.localdata.IIngredientData;

/**
 * @author frederic.oppermann
 * @date 14.12.2013
 * @description
 */
public class FetchDataAsyncTask<T> extends AsyncTask<Void, Void, List<T>> {

	private ProgressBar progressWheel;
	private IIngredientData databseAccessHelper;
	private IAsyncTaskObserver observer;

	public FetchDataAsyncTask(ProgressBar progressWheel,
			IIngredientData databseAccessHelper, IAsyncTaskObserver observer) {
		this.progressWheel = progressWheel;
		this.databseAccessHelper = databseAccessHelper;
		this.observer = observer;
	}

	@Override
	protected void onPreExecute() {
		progressWheel.setVisibility(View.VISIBLE);
	}

	@Override
	protected List<T> doInBackground(Void... params) {
		return (List<T>) databseAccessHelper.getAllIngredients();
	}

	@Override
	protected void onPostExecute(List<T> result) {
		progressWheel.setVisibility(View.GONE);
//		observer.update(this);
	}
}
