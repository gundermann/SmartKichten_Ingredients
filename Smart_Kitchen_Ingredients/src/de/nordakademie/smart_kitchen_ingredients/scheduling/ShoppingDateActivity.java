package de.nordakademie.smart_kitchen_ingredients.scheduling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
import de.nordakademie.smart_kitchen_ingredients.AbstractActivity;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;
import de.nordakademie.smart_kitchen_ingredients.factories.DateFactory;

/**
 * 
 * @author Frauke Trautmann
 * 
 */
public class ShoppingDateActivity extends AbstractActivity implements
		OnTimeChangedListener, OnClickListener, OnDateChangedListener {

	private Button confirmDate;
	private DatePicker datePicke;
	private TimePicker timePicker;
	private EditText dateTitle;
	private IDate chooseDate;

	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;

	static final int DATE_DIALOG_ID = 999;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_date_layout);
		confirmDate = (Button) findViewById(R.id.confirmButton);
		dateTitle = (EditText) findViewById(R.id.chooseDateTitle);
		confirmDate.setOnClickListener(this);
		initTimeOnView();
		initDateOnView();
		Log.i(tag, "created");
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

	public void initTimeOnView() {
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
			app.informUser(R.string.toastInsertNewShoppingDate);
		} else {
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			Intent broadcast_intent = new Intent(getApplicationContext(),
					ShoppingDateAlarmReceiver.class);
			int intentFlag = app.getDateDbHelper().getNextFlag();
			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					getApplicationContext(), 0, broadcast_intent, intentFlag);

			long triggerAtTime = getSettedTime();
			if (triggerAtTime != 0) {
				chooseDate = DateFactory.createDate(dateTitle.getText()
						.toString(), triggerAtTime, intentFlag);
				app.getDateDbHelper().insertNewDate(chooseDate);
				alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtTime,
						pendingIntent);
			}
			finish();
		}
	}

	private long getSettedTime() {
		// Nötig um das korrekte Datum zu erhalten
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm",
				Locale.GERMAN);
		StringBuilder sb = new StringBuilder();
		sb.append(year).append(month).append(day).append(hour).append(minute);
		java.util.Date cal;
		try {
			cal = formatter.parse(sb.toString());
			return cal.getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	private void initDateOnView() {
		datePicke = (DatePicker) findViewById(R.id.calendarView);
		final Calendar c = Calendar.getInstance();
		year = getModifyedCalendarValue(c.get(Calendar.YEAR));
		month = getModifyedCalendarValue(c.get(Calendar.MONTH) + 1);
		day = getModifyedCalendarValue(c.get(Calendar.DAY_OF_MONTH));
		datePicke.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
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
