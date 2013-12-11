package de.nordakademie.smart_kitchen_ingredients.barcodescan;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.nordakademie.smart_kitchen_ingredients.IServerConnector;

public class BarcodeServerHandler implements IBarcodeServerHandler {

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

	public List<JsonObject> filterJsonFromResponse(String response) {
		List<JsonObject> filteredResponse = new ArrayList<JsonObject>();
		int firstIndex = 0;
		int secondIndex = 1;
		int counter = -1;

		while (secondIndex <= response.length()) {
			String currentChar = response.substring(secondIndex - 1,
					secondIndex);
			if (currentChar.equals("{")) {
				if (counter == -1) {
					firstIndex = secondIndex - 1;
					counter++;
				}
				counter++;
			}
			if (currentChar.equals("}")) {
				counter--;
			}
			if (counter == 0) {
				String filteredString = response.substring(firstIndex,
						secondIndex);
				filteredResponse.add(new JsonParser().parse(filteredString)
						.getAsJsonObject());
				counter = -1;
			}
			secondIndex++;
		}

		return filteredResponse;
	}

}
