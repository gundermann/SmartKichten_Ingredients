package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class IngredientCollectorActivity extends
		AbstractCollectorActivity<IIngredient> {

	private Button showRecepies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showRecepies = (Button) findViewById(R.id.showRecipesButton);

		super.fetchDataFromDb(new FetchDataAsyncTask<IIngredient>(
				getProgressWheel(), new IngredientDbMock(), this));
	}

	@Override
	public void update(AsyncTask<Void, Void, List<IIngredient>> task) {
		try {
			setAllElements(task.get());
			Log.d("IngredientCollectionActivity", "works.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
