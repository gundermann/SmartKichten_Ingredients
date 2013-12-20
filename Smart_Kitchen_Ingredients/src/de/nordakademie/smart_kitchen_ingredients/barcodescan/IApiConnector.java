package de.nordakademie.smart_kitchen_ingredients.barcodescan;

public interface IApiConnector {

	String getResponseForInput(String barcode, String apikey);

}
