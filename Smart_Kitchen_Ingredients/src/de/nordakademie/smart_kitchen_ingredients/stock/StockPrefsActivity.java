package de.nordakademie.smart_kitchen_ingredients.stock;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import de.nordakademie.smart_kitchen_ingredients.R;

public class StockPrefsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.stockprefs);
	}
}
