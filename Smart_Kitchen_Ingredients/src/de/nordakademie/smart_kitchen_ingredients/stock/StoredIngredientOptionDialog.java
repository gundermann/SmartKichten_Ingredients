package de.nordakademie.smart_kitchen_ingredients.stock;

/**
 * @author Kathrin Kurtz
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import de.nordakademie.smart_kitchen_ingredients.QuantityPickerDialog;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class StoredIngredientOptionDialog extends AbstractBuilder {

	public StoredIngredientOptionDialog(final String titleFromList,
			final FragmentActivity activity) {
		super(activity, titleFromList, true);

		setPositiveButton(app.getApplicationContext().getResources().getString(R.string.deleteButton), 
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				app.getStoredDbHelper().deleteStoredIngredient(titleFromList);
				Intent intent = new Intent(app.getApplicationContext(),
						StockOverviewActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				app.startActivity(intent);
			}

		});

		setNeutralButton("Bestand erh�hen",
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
