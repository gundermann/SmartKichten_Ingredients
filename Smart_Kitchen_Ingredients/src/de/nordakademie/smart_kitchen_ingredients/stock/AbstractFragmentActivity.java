package de.nordakademie.smart_kitchen_ingredients.stock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;

public abstract class AbstractFragmentActivity extends FragmentActivity {

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
		Toast toast = Toast.makeText(getApplicationContext(), getText(textId),
				Toast.LENGTH_SHORT);
		toast.show();
	}
}
