package de.nordakademie.smart_kitchen_ingredients.stock;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.collector.AddIngredientActivity;
import de.nordakademie.smart_kitchen_ingredients.collector.AddStoredIngredientActivity;

public class StoredIngredientOptionDialog extends Builder {

	public StoredIngredientOptionDialog(final String titleFromList,
			final IngredientsApplication app) {
		super(app.getApplicationContext());
		setMessage(titleFromList);
		setCancelable(true);
		setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				app.getStoredDbHelper().getStoredIngredient(titleFromList);
			}

		});

		setNeutralButton("Ändern", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(app.getApplicationContext(),
						AddStoredIngredientActivity.class);
				intent.putExtra("ingredientTitle", titleFromList);
				app.getApplicationContext().startActivity(intent);
			}
		});

		setNegativeButton("Nachkaufen", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Activity zum hinzufügen in die Shoppingliste
				Intent intent = new Intent(app.getApplicationContext(),
						AddIngredientActivity.class);
				intent.putExtra("ingredientTitle", titleFromList);
				app.getApplicationContext().startActivity(intent);
			}
		});
	}

}
