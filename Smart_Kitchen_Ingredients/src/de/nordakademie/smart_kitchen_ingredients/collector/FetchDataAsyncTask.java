/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.IAbstractCacheDbHelper;

/**
 * @author frederic.oppermann
 * @date 14.12.2013
 * @description
 */
public class FetchDataAsyncTask<T> extends AsyncTask<Void, Void, List<T>> {

	private ProgressBar progressWheel;
	private IAbstractCacheDbHelper<T> databseAccessHelper;
	private IAsyncTaskObserver<T> observer;

	public FetchDataAsyncTask(ProgressBar progressWheel,
			IAbstractCacheDbHelper<T> databseAccessHelper,
			IAsyncTaskObserver<T> observer) {
		this.progressWheel = progressWheel;
		this.databseAccessHelper = databseAccessHelper;
		this.observer = observer;
	}

	@Override
	protected void onPreExecute() {
		if (progressWheel != null) {
			progressWheel.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected List<T> doInBackground(Void... params) {
		return databseAccessHelper.getDatabaseEntries();
	}

	@Override
	protected void onPostExecute(List<T> result) {
		if (progressWheel != null) {
			progressWheel.setVisibility(View.GONE);
		}
		if (observer != null) {
			observer.update(this);
		}
	}
}
