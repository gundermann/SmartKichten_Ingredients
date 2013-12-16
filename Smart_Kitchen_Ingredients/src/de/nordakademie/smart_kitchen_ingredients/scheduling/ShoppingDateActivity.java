package de.nordakademie.smart_kitchen_ingredients.scheduling;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.R.id;
import de.nordakademie.smart_kitchen_ingredients.stock.StoredIngredientActivity;

/**
 * 
 * @author Frauke Trautmann
 * 
 */
public class ShoppingDateActivity extends Activity {

	private static final String TAG = StoredIngredientActivity.class
			.getSimpleName();

	private DatePicker dpResult;
	private TextView changeDate;
	private TextView changeTime;
	private Button confirmDate;
	private TextView headlineShoppingDate;

	private int year;
	private int month;
	private int day;

	private IngredientsApplication app;

	static final int DATE_DIALOG_ID = 999;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (IngredientsApplication) getApplication();
		setContentView(R.layout.shopping_date);
		headlineShoppingDate = (TextView) findViewById(id.headlineShoppingDate);

		confirmDate = (Button) findViewById(id.confirmButton);
		confirmDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				Intent broadcast_intent = new Intent(getApplicationContext(),
						ShoppingDateAlarmReceiver.class);
				int intentFlag = app.getDateDbHelper().getNextFlag();
				PendingIntent pendingIntent = PendingIntent.getBroadcast(
						getApplicationContext(), 0, broadcast_intent,
						intentFlag);
				GregorianCalendar cal = new GregorianCalendar(year, month, day);
				long triggerAtTime = cal.getTimeInMillis();

				IDate date = app.getDateFactory().createDate(null,
						triggerAtTime, intentFlag);
				app.getDateDbHelper().insertNewDate(date);

				alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtTime,
						pendingIntent);

			}
		});

		setCurrentDateOnView();
		Log.i(TAG, "created");

	}

	// display current date
	public void setCurrentDateOnView() {

		dpResult = (DatePicker) findViewById(R.id.dpResult);
		// changeDate = (TextView) findViewById(R.id.changeDateText);
		// changeTime = (TextView) findViewById(id.changeTimeText);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into datepicker
		dpResult.init(year, month, day, null);

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			dpResult.init(year, month, day, null);

		}
	};

}
