package de.nordakademie.smart_kitchen_ingredients;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_layout);
	}

	public void openIngredientsAllocation(View view) {
		startActivity(new Intent(getApplicationContext(),
				ErfassungActivity.class));
	}

	public void openShoppingActivity(View view) {
		startActivity(new Intent(getApplicationContext(),
				ShoppingActivity.class));
	}
}
