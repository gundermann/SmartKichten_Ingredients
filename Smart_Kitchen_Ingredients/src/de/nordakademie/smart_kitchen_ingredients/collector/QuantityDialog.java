package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.service.dreams.DreamService;
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

	private List<TextView> getAllNumberViews() {
		List<TextView> views = new ArrayList<TextView>();
		views.add(nextNumber);
		views.add(currentNumber);
		views.add(previousNumber);
		return views;
	}

	void setNewValue(TextView view, int newValue) {
		view.setText(String.valueOf(newValue));
	}

	void increase(TextView view) {
		int value = getValueOf(view) + 1;
		setNewValue(view, value);
	}

	void decrease(TextView view) {
		int value = getValueOf(view) - 1;
		setNewValue(view, value);
	}

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
				for (TextView view : getAllNumberViews()) {
					increase(view);
				}
			}

		});

		decreaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getValueOf(previousNumber) > 0) {
					for (TextView view : getAllNumberViews()) {
						decrease(view);
					}
				}
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
