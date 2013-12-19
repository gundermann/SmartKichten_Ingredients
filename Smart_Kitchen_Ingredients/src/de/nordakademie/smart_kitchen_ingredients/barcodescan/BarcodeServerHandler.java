package de.nordakademie.smart_kitchen_ingredients.barcodescan;

import com.google.gson.JsonObject;

import de.nordakademie.smart_kitchen_ingredients.onlineconnection.ServerHandler;

/**
 * Verarbeitung der Requests und Responses an und vom Server für die
 * Barcode-Erkennung.
 * 
 * @author niels
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

		// APIKEY abgelaufen
		if (json.get("product") == null) {
			return "";
		}
		return json.get("product").getAsJsonObject().get("attributes")
				.getAsJsonObject().get("product").getAsString();
	}

}
