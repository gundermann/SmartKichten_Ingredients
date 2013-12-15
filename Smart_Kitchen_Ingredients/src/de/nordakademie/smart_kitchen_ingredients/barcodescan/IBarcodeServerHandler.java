package de.nordakademie.smart_kitchen_ingredients.barcodescan;

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
	 * @return String
	 */
	String getItemDescription(String barcode);

}
