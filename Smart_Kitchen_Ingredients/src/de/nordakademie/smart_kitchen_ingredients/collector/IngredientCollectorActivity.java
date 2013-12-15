package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class IngredientCollectorActivity extends
		AbstractCollectorActivity<IIngredient> {
	private Button showRecepiesButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.fetchDataFromDb(new FetchDataAsyncTask<IIngredient>(
				getProgressWheel(), new IngredientDbMock(), this));

		showRecepiesButton = (Button) findViewById(R.id.showRecipesButton);
		showRecepiesButton.setVisibility(View.VISIBLE);
		showRecepiesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						RecipeCollectorActivity.class));
			}
		});
	}

	@Override
	public void update(AsyncTask<Void, Void, List<IIngredient>> task) {
		try {
			setAllElements(task.get());
			afterTextChanged(((EditText) findViewById(R.id.searchBarInput))
					.getText());
			setNewAdapter(new ArrayAdapter<IIngredient>(
					getApplicationContext(), R.layout.list_view_entry,
					getElementsToShow()));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		super.afterTextChanged(s);
		setNewAdapter(new ArrayAdapter<IIngredient>(getApplicationContext(),
				R.layout.list_view_entry, getElementsToShow()));
	}

}
