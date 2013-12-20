package de.nordakademie.smart_kitchen_ingredients.stock;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.text.InputFilter;
import android.text.InputType;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

/**
 * @author Frauke Trautmann
 */
public class StockPrefsActivity extends PreferenceActivity {

	private static final String TITLE = "Knappheit: ";
	private static final int FILTER_LENGTH = 6;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.stockprefs);

		for (Unit unit : Unit.values()) {
			EditTextPreference editTextPreference = new EditTextPreference(this);
			editTextPreference.setKey(unit.toString());
			editTextPreference.setTitle(TITLE + unit.toLongString());
			editTextPreference.getEditText().setInputType(
					InputType.TYPE_CLASS_NUMBER);
			InputFilter[] filters = new InputFilter[1];
			filters[0] = new InputFilter.LengthFilter(FILTER_LENGTH);
			editTextPreference.getEditText().setFilters(filters);
			getPreferenceScreen().addItemFromInflater(editTextPreference);
		}
	}
}
