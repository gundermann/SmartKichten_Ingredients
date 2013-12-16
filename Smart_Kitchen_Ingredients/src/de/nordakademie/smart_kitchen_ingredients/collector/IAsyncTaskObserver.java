package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;

public interface IAsyncTaskObserver<T> {
	void update(AsyncTask<Void, Void, List<T>> task);
}
