package de.nordakademie.smart_kitchen_ingredients.stock;

import java.util.HashMap;
import java.util.Map;

import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import de.nordakademie.smart_kitchen_ingredients.collector.AddStoredIngredientActivity;

public class StoredIngredientOptionDialog extends AbstractBuilder {

	private static final String TAG = StoredIngredientOptionDialog.class
			.getSimpleName();

	public StoredIngredientOptionDialog(final String titleFromList,
			final FragmentActivity activity) {
		super(activity, titleFromList, true);

		setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				app.getStoredDbHelper().getStoredIngredient(titleFromList);
			}

		});

		setNeutralButton("Ändern", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Map<String, String> extras = new HashMap<String, String>();
				extras.put("ingredientTitle", titleFromList);
				startNextActivityWithExtras(AddStoredIngredientActivity.class,
						extras);
			}
		});

		setNegativeButton("Nachkaufen", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				openQualityDialog(titleFromList, TAG);
			}
		});
	}

}
