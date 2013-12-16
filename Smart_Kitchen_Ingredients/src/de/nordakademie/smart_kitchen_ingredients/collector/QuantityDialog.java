package de.nordakademie.smart_kitchen_ingredients.collector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.R;

/**
 * @author frederic.oppermann
 * @date 15.12.2013
 * @description
 */
public class QuantityDialog extends DialogFragment {
	ImageButton increaseButton;
	ImageButton decreaseButton;
	TextView previousNumber;
	TextView currentNumber;
	TextView nextNumber;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.number_picker_layout, null);

		increaseButton = (ImageButton) view
				.findViewById(R.id.quantityPickerIncreaseButton);
		decreaseButton = (ImageButton) view
				.findViewById(R.id.quantityPickerDecreaseButton);

		previousNumber = (TextView) view
				.findViewById(R.id.quantityPickerPreviousNumber);
		currentNumber = (TextView) view
				.findViewById(R.id.quantityPickerCurrentNumber);
		nextNumber = (TextView) view
				.findViewById(R.id.quantityPickerNextNumber);

		increaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int nextVal = getValueOf(nextNumber);
				int currentVal = getValueOf(currentNumber);
				int previousVal = getValueOf(previousNumber);

				nextVal++;
				currentVal++;
				previousVal++;

				nextNumber.setText(String.valueOf(nextVal));
				currentNumber.setText(String.valueOf(currentVal));
				previousNumber.setText(String.valueOf(previousVal));
			}

		});

		decreaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int previousVal = getValueOf(previousNumber);
				int currentVal = getValueOf(currentNumber);
				int nextVal = getValueOf(nextNumber);

				if (previousVal > 0) {

					previousVal--;
					currentVal--;
					nextVal--;
				}

				previousNumber.setText(String.valueOf(previousVal));
				currentNumber.setText(String.valueOf(currentVal));
				nextNumber.setText(String.valueOf(nextVal));

			}
		});

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				getActivity());
		dialogBuilder.setTitle(R.string.quantityDialogTitle).setView(view);
		return dialogBuilder.create();
	}

	private int getValueOf(TextView view) {
		return Integer.valueOf(view.getText().toString());
	}
}
