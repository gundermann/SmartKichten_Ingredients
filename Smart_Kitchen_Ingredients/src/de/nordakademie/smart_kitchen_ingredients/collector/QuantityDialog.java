package de.nordakademie.smart_kitchen_ingredients.collector;

import de.nordakademie.smart_kitchen_ingredients.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * @author frederic.oppermann
 * @date 15.12.2013
 * @description
 */
public class QuantityDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				getActivity());
		dialogBuilder.setTitle(R.string.quantityDialogTitle);
		return dialogBuilder.create();
	}
}
