package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.DateFactory;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.DateTable;

public class SmartKitchenDateData extends AbstractSmartKitchenData implements
		IDateDbHelper {

	public SmartKitchenDateData(IngredientsApplication app) {
		super(app);
		TAG = SmartKitchenDateData.class.getSimpleName();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DateTable.getTableCreation());
		Log.i(TAG, "shoppingDB created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		database.execSQL(DateTable.getDrop());
		onCreate(database);
	}

	@Override
	public boolean insertNewDate(IDate date) {
		ContentValues values = DateTable.getContentValuesForAll(date);
		boolean success = insert(DateTable.TABLE_NAME, values);
		return success;
	}

	@Override
	public int getIntentFlagByTime(long timestamp) {
		openCursorResoures();
		setCursor(DateTable.TABLE_NAME, DateTable.selectIntentFlag(),
				getWhere(DateTable.TIMESTAMP, timestamp));
		cursor.moveToNext();
		int result = cursor.getInt(0);
		closeCursorResources();
		return result;
	}

	@Override
	public List<IDate> getAllDates() {
		List<IDate> dateList = new ArrayList<IDate>();
		openCursorResoures();
		setCursor(DateTable.TABLE_NAME, DateTable.selectAll());
		while (cursor.moveToNext()) {
			String title = cursor.getString(0);
			long timestamp = cursor.getLong(1);
			int intentFlag = cursor.getInt(2);
			dateList.add(DateFactory.createDate(title, timestamp, intentFlag));
		}
		closeCursorResources();
		return dateList;
	}

	@Override
	public int getNextFlag() {
		int flag = 0;
		openCursorResoures();
		setCursor(DateTable.TABLE_NAME, DateTable.selectIntentFlag());
		while (cursor.moveToNext()) {
			if (cursor.getInt(0) == flag) {
				flag++;
			} else {
				break;
			}
		}
		closeCursorResources();
		return flag;
	}

	@Override
	public void remove(IDate date) {
		delete(DateTable.TABLE_NAME,
				getWhere(DateTable.TIMESTAMP, date.getTimestamp()));

	}
}
