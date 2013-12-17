package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.scheduling.IDate;

public interface IDateDbHelper {

	boolean insertNewDate(IDate date);

	int getIntentFlagByTime(long timestamp);

	List<IDate> getAllDates();

	int getNextFlag();

}
