package de.nordakademie.smart_kitchen_ingredients.onlineconnection;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Allgemeine Verarbeitung einer Serverantwort.
 * 
 * @author Niels Gundermann
 * 
 */
public abstract class ServerHandler {

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
