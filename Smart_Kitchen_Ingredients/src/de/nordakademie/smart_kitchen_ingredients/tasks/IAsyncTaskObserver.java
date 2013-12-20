package de.nordakademie.smart_kitchen_ingredients.tasks;

import java.util.List;

import android.os.AsyncTask;

/**
* @author frederic.oppermann
* @date 16.12.2013
* @description
* @param <T>
*/
public interface IAsyncTaskObserver<T> {
	void update(AsyncTask<Void, Void, List<T>> task);
}
