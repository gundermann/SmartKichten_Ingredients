/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.localdata.IDatabaseHelper;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

/**
 * @author frederic.oppermann
 * @date 14.12.2013
 * @description
 */
public class FetchDataAsyncTask<T> extends AsyncTask<Void, Void, List<T>> {

	private ProgressBar progressWheel;
	private IDatabaseHelper<T> databseAccessHelper;
	private IAsyncTaskObserver<T> observer;

	public FetchDataAsyncTask(ProgressBar progressWheel,
			IDatabaseHelper<T> databseAccessHelper,
			IAsyncTaskObserver<T> observer) {
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
		return databseAccessHelper.getDatabaseEntries();
	}

	@Override
	protected void onPostExecute(List<T> result) {
		progressWheel.setVisibility(View.GONE);
		observer.update(this);
	}
}
