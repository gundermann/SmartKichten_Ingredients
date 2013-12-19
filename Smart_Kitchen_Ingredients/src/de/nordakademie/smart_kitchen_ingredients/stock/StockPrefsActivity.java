package de.nordakademie.smart_kitchen_ingredients.stock;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.text.InputFilter;
import android.text.InputType;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

public class StockPrefsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.stockprefs);

		for (Unit unit : Unit.values()) {
			EditTextPreference editTextPreference = new EditTextPreference(this);
			editTextPreference.setKey(unit.toString());
			editTextPreference.setTitle("Knappheit: " + unit);
			editTextPreference.getEditText().setInputType(
					InputType.TYPE_CLASS_NUMBER);
			InputFilter[] filters = new InputFilter[1];
			// Filter to 6 characters
			filters[0] = new InputFilter.LengthFilter(6);
			editTextPreference.getEditText().setFilters(filters);
			getPreferenceScreen().addItemFromInflater(editTextPreference);
		}
	}
}
