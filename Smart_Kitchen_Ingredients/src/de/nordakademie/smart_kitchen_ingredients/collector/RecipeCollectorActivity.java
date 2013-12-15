package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

public class RecipeCollectorActivity extends AbstractCollectorActivity<IRecipe> {
	private Button showIngredientsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.fetchDataFromDb(new FetchDataAsyncTask<IRecipe>(
				getProgressWheel(), new IngredientDbMock(), this));

		showIngredientsButton = (Button) findViewById(R.id.showIngredientsButton);
		showIngredientsButton.setVisibility(View.VISIBLE);
		showIngredientsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						IngredientCollectorActivity.class));
			}
		});
	}

	@Override
	public void update(AsyncTask<Void, Void, List<IRecipe>> task) {
		try {
			setAllElements(task.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
