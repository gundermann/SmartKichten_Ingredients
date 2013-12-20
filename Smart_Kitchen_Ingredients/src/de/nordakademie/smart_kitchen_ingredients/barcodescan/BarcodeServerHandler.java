package de.nordakademie.smart_kitchen_ingredients.barcodescan;

import com.google.gson.JsonObject;

import de.nordakademie.smart_kitchen_ingredients.onlineconnection.ServerHandler;

/**
 * Verarbeitung der Requests und Responses an und vom Server f�r die
 * Barcode-Erkennung.
 * 
 * @author Niels Gundermann
 * 
 */
public class BarcodeServerHandler extends ServerHandler implements
		IBarcodeServerHandler {

	private IApiConnector connector;

	public BarcodeServerHandler(IApiConnector connector) {
		this.connector = connector;
	}

	@Override
	public String getItemDescription(String barcode, String apikey) {
		String response = connector.getResponseForInput(barcode, apikey);
		return filterItemDescriptionFromResponse(response);
	}

	private String filterItemDescriptionFromResponse(String response) {
		JsonObject json = filterJsonFromResponse(response).get(0);

		try {
			return json.get("product").getAsJsonObject().get("attributes")
					.getAsJsonObject().get("product").getAsString();
		} catch (NullPointerException npe) {
			return "";
		}
	}

}
