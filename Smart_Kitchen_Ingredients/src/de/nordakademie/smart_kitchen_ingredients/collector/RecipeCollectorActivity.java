package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

public class RecipeCollectorActivity extends AbstractCollectorActivity<IRecipe> {

	private Button showIngredientsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void update(AsyncTask<Void, Void, List<IRecipe>> task) {
		// TODO Auto-generated method stub

	}

}
