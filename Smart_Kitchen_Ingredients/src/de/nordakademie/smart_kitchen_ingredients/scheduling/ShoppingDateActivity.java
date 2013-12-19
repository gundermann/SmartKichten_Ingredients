package de.nordakademie.smart_kitchen_ingredients.scheduling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import de.nordakademie.smart_kitchen_ingredients.DateFactory;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.R.id;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;
import de.nordakademie.smart_kitchen_ingredients.stock.StoredIngredientActivity;

/**
 * 
 * @author Frauke Trautmann
 * 
 */
public class ShoppingDateActivity extends Activity implements
		OnTimeChangedListener, OnClickListener, OnDateChangedListener {

	private static final String TAG = StoredIngredientActivity.class
			.getSimpleName();

	private Button confirmDate;
	private DatePicker calendar;
	private TimePicker timePicker;
	private EditText dateTitle;
	private IDate chooseDate;

	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;

	private IngredientsApplication app;

	static final int DATE_DIALOG_ID = 999;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (IngredientsApplication) getApplication();
		setContentView(R.layout.shopping_date);

		calendar = (DatePicker) findViewById(R.id.calendarView);
		confirmDate = (Button) findViewById(id.confirmButton);
		dateTitle = (EditText) findViewById(R.id.chooseDateTitle);
		confirmDate.setOnClickListener(this);

		setCurrentTimeOnView();
		setCurrentDateOnView();
		Log.i(TAG, "created");

	}

	public boolean dateTitleIsEmpty() {

		return dateTitle.getText().toString().isEmpty();

	}

	private String getModifyedCalendarValue(int value) {
		if (value > 9) {
			return String.valueOf(value);
		} else {
			return "0" + String.valueOf(value);
		}
	}

	public void setCurrentTimeOnView() {

		timePicker = (TimePicker) findViewById(R.id.timePicker);

		timePicker.setIs24HourView(true);
		final Calendar c = Calendar.getInstance();

		hour = getModifyedCalendarValue(c.get(Calendar.HOUR_OF_DAY));
		minute = getModifyedCalendarValue(c.get(Calendar.MINUTE));

		timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
		timePicker.setOnTimeChangedListener(this);
	}

	@Override
	public void onTimeChanged(TimePicker view, int selectedHour,
			int selectedMinute) {
		hour = getModifyedCalendarValue(selectedHour);
		minute = getModifyedCalendarValue(selectedMinute);

	}

	@Override
	public void onClick(View view) {
		if (dateTitleIsEmpty()) {
			Toast.makeText(getApplicationContext(),
					R.string.toastInsertNewShoppingDate, Toast.LENGTH_LONG)
					.show();
		} else {

			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			Intent broadcast_intent = new Intent(getApplicationContext(),
					ShoppingDateAlarmReceiver.class);
			int intentFlag = app.getDateDbHelper().getNextFlag();
			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					getApplicationContext(), 0, broadcast_intent, intentFlag);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm",
					Locale.GERMAN);
			StringBuilder sb = new StringBuilder();
			sb.append(year).append(month).append(day).append(hour)
					.append(minute);
			java.util.Date cal;
			try {
				cal = formatter.parse(sb.toString());
				long triggerAtTime = cal.getTime();
				chooseDate = DateFactory.createDate(dateTitle.getText()
						.toString(), triggerAtTime, intentFlag);
				app.getDateDbHelper().insertNewDate(chooseDate);

				alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtTime,
						pendingIntent);
			} catch (ParseException e) {
				Log.d(TAG, "parseexc");
			} finally {
				finish();
			}
		}
	}

	private void setCurrentDateOnView() {
		final Calendar c = Calendar.getInstance();
		year = getModifyedCalendarValue(c.get(Calendar.YEAR));
		month = getModifyedCalendarValue(c.get(Calendar.MONTH) + 1);
		day = getModifyedCalendarValue(c.get(Calendar.DAY_OF_MONTH));
		calendar.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH), this);

	}

	@Override
	public void onDateChanged(DatePicker view, int curyear, int monthOfYear,
			int dayOfMonth) {

		year = getModifyedCalendarValue(curyear);
		month = getModifyedCalendarValue(monthOfYear + 1);
		day = getModifyedCalendarValue(dayOfMonth);

	}
}
