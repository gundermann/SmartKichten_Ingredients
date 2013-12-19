package de.nordakademie.smart_kitchen_ingredients;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.Date;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;

public class DateFactory {

	public static IDate createDate(String title, long timestamp, int intentFlag) {
		return new Date(title, timestamp, intentFlag);
	}

}
