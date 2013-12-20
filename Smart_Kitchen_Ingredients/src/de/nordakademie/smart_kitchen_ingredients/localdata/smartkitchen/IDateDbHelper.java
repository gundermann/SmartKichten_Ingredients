package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen;
/**
 * @author Niels Gundermann
 */
import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;

public interface IDateDbHelper {

	/**
	 * Fügt ein neues Datum ein und gibt Info die geklappt hat.
	 * @param date
	 * @return boolean
	 */
	boolean insertNewDate(IDate date);

	int getIntentFlagByTime(long timestamp);

	List<IDate> getAllDates();

	int getNextFlag();

	/**
	 * Löscht einen bestimmten Termin
	 * @param date
	 */
	void remove(IDate date);

}
