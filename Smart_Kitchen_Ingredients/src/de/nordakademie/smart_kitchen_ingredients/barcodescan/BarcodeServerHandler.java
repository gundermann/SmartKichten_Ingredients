package de.nordakademie.smart_kitchen_ingredients.barcodescan;

import com.google.gson.JsonObject;

import de.nordakademie.smart_kitchen_ingredients.IServerConnector;
import de.nordakademie.smart_kitchen_ingredients.ServerHandler;

public class BarcodeServerHandler extends ServerHandler implements
		IBarcodeServerHandler {

	private IServerConnector connector;

	public BarcodeServerHandler(IServerConnector connector) {
		this.connector = connector;
	}

	@Override
	public String getItemDescription(String barcode) {
		String response = connector.getResponseForInput(barcode);
		return filterItemDescriptionFromResponse(response);
	}

	private String filterItemDescriptionFromResponse(String response) {
		JsonObject json = filterJsonFromResponse(response).get(0);

		return json.get("product").getAsJsonObject().get("attributes")
				.getAsJsonObject().get("product").getAsString();
	}

}
