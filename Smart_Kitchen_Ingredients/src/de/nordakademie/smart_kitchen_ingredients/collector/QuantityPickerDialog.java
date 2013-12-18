package de.nordakademie.smart_kitchen_ingredients.collector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.R;

/**
 * @author frederic.oppermann
 * @date 15.12.2013
 * @description
 */
public class QuantityPickerDialog extends DialogFragment {
	private InputMethodManager inputManager;
	private ImageButton increaseButton;
	private ImageButton decreaseButton;
	private TextView previousNumber;
	private TextView currentNumber;
	private TextView nextNumber;
	private EditText currentNumberInput;
	private IListElement element;
	private QuantityPickerDialogListener dialogListener;

	public static final QuantityPickerDialog newInstance(IListElement element) {
		QuantityPickerDialog dialog = new QuantityPickerDialog();
		dialog.setListElement(element);
		return dialog;
	}

	public void setListElement(IListElement element) {
		this.element = element;
	}

	private void setCurrentNumber(int newValue) {
		setNewValue(nextNumber, newValue + 1);
		setNewValue(currentNumber, newValue);
		setNewValue(previousNumber, newValue - 1);
	}

	void setNewValue(TextView view, int newValue) {
		view.setText(String.valueOf(newValue));
	}

	private void hideElement(View view) {
		view.setVisibility(View.GONE);
	}

	private void showElement(View view) {
		view.setVisibility(View.VISIBLE);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		View view = getCurrentView();
		instantiaveViews(view);
		setOnClickListener();

		currentNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				inputManager = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				hideElement(currentNumber);
				showElement(currentNumberInput);
				currentNumberInput.requestFocus();
				inputManager.showSoftInput(currentNumberInput,
						InputMethodManager.SHOW_FORCED);
			}
		});

		currentNumberInput.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				setCurrentNumber(getValueOf(currentNumberInput));
				if (event != null
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					Log.d("quantity", event.getKeyCode() + "");

					inputManager.hideSoftInputFromWindow(
							currentNumberInput.getWindowToken(), 0);

					showElement(currentNumber);
					hideElement(currentNumberInput);
				}
				return false;
			}
		});

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
		dialogListener.onPositiveFinishedDialog(element, getCurrentValue());
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
		currentNumberInput = (EditText) view
				.findViewById(R.id.quantityPickerCurrentQuantityInput);
	}

	private void setOnClickListener() {
		increaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setCurrentNumber(getValueOf(currentNumber) + 1);
			}

		});

		decreaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getValueOf(previousNumber) > 0) {
					setCurrentNumber(getValueOf(currentNumber) - 1);
				}
			}
		});
	}

	private int getValueOf(TextView view) {
		return Integer.valueOf(view.getText().toString());
	}
}
