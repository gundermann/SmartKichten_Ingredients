package de.nordakademie.smart_kitchen_ingredients.scheduling;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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
	private TimePicker timePicker;

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	private IngredientsApplication app;

	static final int DATE_DIALOG_ID = 999;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (IngredientsApplication) getApplication();
		setContentView(R.layout.shopping_date);

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
				Date cal = new Date(year, month, day, hour, minute);
				long triggerAtTime = cal.getTime();

				IDate date = app.getDateFactory().createDate(null,
						triggerAtTime, intentFlag);
				app.getDateDbHelper().insertNewDate(date);

				alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtTime,
						pendingIntent);
				finish();

			}
		});

		setCurrentDateOnView();
		Log.i(TAG, "created");

	}

	public void setCurrentDateOnView() {

		dpResult = (DatePicker) findViewById(R.id.dpResult);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		dpResult.init(year, month, day, null);

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			dpResult.init(year, month, day, null);

		}
	};

	public void setCurrentTimeOnView() {

		timePicker = (TimePicker) findViewById(R.id.timePicker);

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);

	}

	private final TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

		}
	};
};
