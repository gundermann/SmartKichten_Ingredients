package de.nordakademie.smart_kitchen_ingredients.shopping.barcodescan;

/**
 * 
 * @author niels
 * 
 */
public interface IBarcodeServerHandler {

	/**
	 * Liefert die Beschreibung eines Artikels, der über einen Barcode
	 * identifiziert wurde.
	 * 
	 * @param barcode
	 * @param apikey
	 * @return String
	 */
	String getItemDescription(String barcode, String apikey);

}
