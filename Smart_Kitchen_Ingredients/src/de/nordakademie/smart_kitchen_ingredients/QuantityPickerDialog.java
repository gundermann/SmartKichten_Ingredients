package de.nordakademie.smart_kitchen_ingredients;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;

/**
 * @author Frederic Oppermann
 * @description dialog, der die auswahl der gewünschten menge eines rezeptes /
 *              einer zutat ermöglicht
 */
public class QuantityPickerDialog extends DialogFragment implements TextWatcher {
	private InputMethodManager inputManager;
	private ImageButton increaseButton;
	private ImageButton decreaseButton;
	private TextView previousNumber;
	private TextView currentNumber;
	private TextView nextNumber;
	private EditText currentNumberInput;
	private IListElement element;
	private QuantityPickerDialogListener dialogListener;
	private IngredientsApplication app;

	public static final QuantityPickerDialog newInstance(IListElement element,
			IngredientsApplication app) {
		QuantityPickerDialog dialog = new QuantityPickerDialog();
		dialog.setApplication(app);
		dialog.setListElement(element);
		return dialog;
	}

	public void setApplication(IngredientsApplication app) {
		this.app = app;

	}

	public void setListElement(IListElement element) {
		this.element = element;
	}

	private void setCurrentNumber(int newValue) {
		if (newValue > 0) {
			setNewValue(nextNumber, newValue + 1);
			setNewValue(currentNumber, newValue);
			setNewValue(previousNumber, newValue - 1);
		} else {
			((IngredientsApplication) getActivity().getApplication())
					.informUser(R.string.numberHaveToBeGreaterThanZero);
		}
	}

	private void setCurrentNumber(String number) {
		if (!number.trim().isEmpty()) {
			setCurrentNumber(Integer.valueOf(number));
		}
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
				setCurrentNumber(currentNumberInput);
				if (event != null
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

					inputManager.hideSoftInputFromWindow(
							currentNumberInput.getWindowToken(), 0);

					showElement(currentNumber);
					hideElement(currentNumberInput);
				}
				return false;
			}
		});

		currentNumberInput.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				currentNumberInput.selectAll();
				return true;
			}
		});

		dialogListener = (QuantityPickerDialogListener) getActivity();
		return buildDialog(view);
	}

	private void setCurrentNumber(EditText editText) {
		setCurrentNumber(editText.getText().toString());
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

		currentNumberInput.addTextChangedListener(this);
	}

	private void setOnClickListener() {
		increaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				increaseCurrentValue();
				updateCurrentNumberInput();
			}

		});

		decreaseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				decreaseCurrentValue();
				updateCurrentNumberInput();
			}

		});
	}

	private void updateCurrentNumberInput() {
		currentNumberInput.setText(currentNumber.getText());
	}

	private void increaseCurrentValue() {
		setCurrentNumber(getCurrentValue() + 1);
	}

	private void decreaseCurrentValue() {
		setCurrentNumber(getCurrentValue() - 1);
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		setCurrentNumber(s.toString());
	}
}
