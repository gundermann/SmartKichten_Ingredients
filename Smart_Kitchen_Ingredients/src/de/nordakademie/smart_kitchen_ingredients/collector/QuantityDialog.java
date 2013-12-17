package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
	private ImageButton increaseButton;
	private ImageButton decreaseButton;
	private TextView previousNumber;
	private TextView currentNumber;
	private TextView nextNumber;
	private static IListElement element;
	private QuantityPickerDialogListener dialogListener;

	public static final QuantityDialog newInstance(IListElement element) {
		QuantityDialog.element = element;
		QuantityDialog dialog = new QuantityDialog();
		return dialog;
	}

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
		View view = getCurrentView();
		instantiaveViews(view);
		setOnClickListener();
		dialogListener = (QuantityPickerDialogListener) getActivity();
		return buildDialog(view);
	}

	private Dialog buildDialog(View view) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				getActivity());
		dialogBuilder
				.setTitle(element.getElementUnit())
				.setView(view)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								doOnPositive();
								dismiss();
							}

						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dismiss();
							}
						});
		return dialogBuilder.create();
	}

	public void doOnPositive() {
		dialogListener.onFinishedDialog(getCurrentValue());
	}

	private Integer getCurrentValue() {
		return Integer.valueOf(currentNumber.getText().toString());
	}

	private View getCurrentView() {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.number_picker_layout, null);
		return view;
	}

	private void instantiaveViews(View view) {
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
	}

	private void setOnClickListener() {
		currentNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

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
	}

	private int getValueOf(TextView view) {
		return Integer.valueOf(view.getText().toString());
	}
}
