package de.nordakademie.smart_kitchen_ingredients.scheduling;


public interface IDateFactory {

	IDate createDate(String title, long timestamp, int intentFlag);

}
