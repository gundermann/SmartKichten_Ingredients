package de.nordakademie.smart_kitchen_ingredients.barcodescan;
/**
 * @author Niels Gundermann
 */
public interface IApiConnector {

	/**
	 * Gibt für einen Barcode mit ApiKey einen Antwort String 
	 * vom Server zurück.
	 * @param barcode
	 * @param apikey
	 * @return String
	 */
	String getResponseForInput(String barcode, String apikey);

}
