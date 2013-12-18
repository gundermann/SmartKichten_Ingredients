/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;

/**
 * @author frederic.oppermann
 * @date 18.12.2013
 * @description
 */
public class InsertNameDialog extends DialogFragment {
	private final static String TAG = InsertNameDialog.class.getSimpleName();

	private EditText inputField;
	private InsertNameDialogListener dialogListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.minimal_dialog_layout, null);
		dialogListener = (InsertNameDialogListener) getActivity();
		inputField = (EditText) view.findViewById(R.id.dialogInput);

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				getActivity());

		dialogBuilder.setTitle(R.string.addShoppingListDialog).setView(view)
				.setPositiveButton(android.R.string.ok, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (!inputField.getText().toString().equals("")) {
							Log.d(TAG, inputField.getText().toString());
							dialogListener.onPositiveFinishedDialog(inputField
									.getText().toString());
						} else {
							((IngredientsApplication) getActivity()
									.getApplication())
									.informUser(R.string.userInformFieldIsEmpty);

						}
					}
				});
		return dialogBuilder.create();
	}
}
