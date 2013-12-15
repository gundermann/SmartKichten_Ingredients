package de.nordakademie.smart_kitchen_ingredients.collector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import de.nordakademie.smart_kitchen_ingredients.R;

/**
 * @author frederic.oppermann
 * @date 15.12.2013
 * @description
 */
public class QuantityDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.number_picker_layout, null);

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				getActivity());
		dialogBuilder.setTitle(R.string.quantityDialogTitle).setView(view);
		return dialogBuilder.create();
	}
}
