package de.nordakademie.smart_kitchen_ingredients.barcodescan;

public interface IBarcodeServerConnector {

	String getResponseForBarcode(String barcode);
}
