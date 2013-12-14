/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

/**
 * @author frederic.oppermann
 * @date 14.12.2013
 * @description
 */
public interface IAsyncTaskObserver {
	void update(AsyncTask<Void, Void, List<IIngredient>> asyncTask);
}
