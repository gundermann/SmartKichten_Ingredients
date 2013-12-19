package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import de.nordakademie.smart_kitchen_ingredients.R;

public class SmartKitchenIngredientsPrefsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.barcodepref);
	}
}
