package de.nordakademie.smart_kitchen_ingredients.scheduling;


public class DateFactory implements IDateFactory {

	@Override
	public IDate createDate(String title, long timestamp, int intentFlag) {
		return new Date(title, timestamp, intentFlag);
	}

}
