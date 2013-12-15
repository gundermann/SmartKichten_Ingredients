package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class IngredientCollectorActivity extends
		AbstractCollectorActivity<IIngredient> {

	private static String TAG = "lookingFor";

	private Button showRecepiesButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.fetchDataFromDb(new FetchDataAsyncTask<IIngredient>(
				getProgressWheel(), new IngredientDbMock(), this));

		Log.d(TAG, "onCreate() IngredientActivity");

		showRecepiesButton = (Button) findViewById(R.id.showRecipesButton);
		showRecepiesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						RecipeCollectorActivity.class));
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() IngredientActivity");
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
