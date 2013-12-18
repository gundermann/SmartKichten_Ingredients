package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;

public abstract class AbstractActivity extends Activity {

	protected IngredientsApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (IngredientsApplication) getApplication();
	}

	protected void startNextActivity(Class<?> activityClass) {
		startActivity(new Intent(getApplicationContext(), activityClass));
	}

	protected void makeLongToast(int textId) {
		Toast.makeText(getApplicationContext(), getText(textId),
				Toast.LENGTH_SHORT).show();
	}

}