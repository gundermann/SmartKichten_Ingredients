package de.nordakademie.smart_kitchen_ingredients.factories;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.Date;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;
/**
 * @author Frauke Trautmann
 */
public class DateFactory {

	public static IDate createDate(String title, long timestamp, int intentFlag) {
		return new Date(title, timestamp, intentFlag);
	}

}
