package de.nordakademie.smart_kitchen_ingredients.stock;
/**
 * @author Frauke Trautmann
 */
import java.util.Map;

import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.QuantityPickerDialog;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;

public abstract class AbstractBuilder extends Builder {

	private final FragmentActivity activity;
	protected IngredientsApplication app;

	public AbstractBuilder(FragmentActivity fragmentActivity, String title,
			boolean cancelable) {
		super(fragmentActivity);
		activity = fragmentActivity;
		app = (IngredientsApplication) activity.getApplication();
		setMessage(title);
		setCancelable(cancelable);
	}

	protected void startNextActivityWithExtras(Class<?> activityClass,
			Map<String, String> extras) {
		Intent intent = new Intent(app.getApplicationContext(), activityClass);
		for (String extraKey : extras.keySet()) {
			intent.putExtra(extraKey, extras.get(extraKey));
		}
		app.getApplicationContext().startActivity(intent);
	}

	protected void openQualityDialog(String title, String TAG) {
		IListElement element = app.getStoredDbHelper().getStoredIngredient(
				title);

		DialogFragment quantityDialog = QuantityPickerDialog.newInstance(
				element, app);
		quantityDialog.show(activity.getSupportFragmentManager(), TAG);
	}
}
