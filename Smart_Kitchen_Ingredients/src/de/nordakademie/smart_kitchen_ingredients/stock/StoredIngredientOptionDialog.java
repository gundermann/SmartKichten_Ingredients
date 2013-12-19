package de.nordakademie.smart_kitchen_ingredients.stock;

/**
 * @author Kathrin Kurtz
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.collector.QuantityPickerDialog;

public class StoredIngredientOptionDialog extends AbstractBuilder {

	private static final String TAG = StoredIngredientOptionDialog.class
			.getSimpleName();

	public StoredIngredientOptionDialog(final String titleFromList,
			final FragmentActivity activity) {
		super(activity, titleFromList, true);

		setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				app.getStoredDbHelper().deleteStoredIngredient(titleFromList);
				Intent intent = new Intent(app.getApplicationContext(),
						StoredIngredientActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				app.startActivity(intent);
			}

		});

		setNeutralButton("Bestand erhöhen",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						IIngredient element = app.getStoredDbHelper()
								.getStoredIngredient(titleFromList);
						QuantityPickerDialog dia = QuantityPickerDialog
								.newInstance(element, app);
						dia.show(activity.getSupportFragmentManager(),
								"addBestand");

					}
				});
	}
}
