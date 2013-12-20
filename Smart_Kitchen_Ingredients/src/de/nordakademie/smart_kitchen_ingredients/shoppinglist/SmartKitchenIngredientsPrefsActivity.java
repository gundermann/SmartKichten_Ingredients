package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.InputType;
import de.nordakademie.smart_kitchen_ingredients.R;

public class SmartKitchenIngredientsPrefsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.barcodepref);
		EditTextPreference pref = ((EditTextPreference) getPreferenceScreen().getPreference(0));
		pref.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
		pref.getEditText().setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
	}
}
