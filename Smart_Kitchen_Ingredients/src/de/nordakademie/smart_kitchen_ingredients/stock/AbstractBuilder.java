package de.nordakademie.smart_kitchen_ingredients.stock;

import java.util.Map;

import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.collector.IListElement;
import de.nordakademie.smart_kitchen_ingredients.collector.QuantityPickerDialog;

public abstract class AbstractBuilder extends Builder {

	private FragmentActivity activity;
	IngredientsApplication app;

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
		IListElement element = app.getShoppingDbHelper().getShoppingItem(title);

		DialogFragment quantityDialog = QuantityPickerDialog
				.newInstance(element);
		quantityDialog.show(activity.getSupportFragmentManager(), TAG);
	}
}
