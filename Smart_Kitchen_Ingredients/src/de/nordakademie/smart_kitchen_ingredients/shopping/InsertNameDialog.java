package de.nordakademie.smart_kitchen_ingredients.shopping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;

/**
 * @author Frederic Oppermann
 */
public class InsertNameDialog extends DialogFragment {
	private static final String TAG = InsertNameDialog.class.getSimpleName();

	private EditText inputField;
	private IInsertNameDialogListener dialogListener;
	private IngredientsApplication app;

	public static InsertNameDialog newInstance(IngredientsApplication app) {
		InsertNameDialog dialog = new InsertNameDialog();
		dialog.setApplication(app);
		return dialog;
	}

	private void setApplication(IngredientsApplication app) {
		this.app = app;

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.minimal_dialog_layout, null);
		dialogListener = (IInsertNameDialogListener) getActivity();
		inputField = (EditText) view.findViewById(R.id.dialogInput);

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				getActivity());

		dialogBuilder.setTitle(R.string.addShoppingListDialog).setView(view)
				.setPositiveButton(android.R.string.ok, null);

		return dialogBuilder.create();
	}

	@Override
	public void onStart() {
		super.onStart();
		AlertDialog dialog = (AlertDialog) getDialog();
		if (dialog != null) {
			Button positiveButton = (Button) dialog
					.getButton(Dialog.BUTTON_POSITIVE);
			positiveButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!inputField.getText().toString().equals("")) {
						dialogListener.onPositiveFinishedDialog(inputField
								.getText().toString());
						dismiss();
					} else {
						app.informUser(R.string.userInformFieldIsEmpty);

					}
				}
			});
		}
	}

}
