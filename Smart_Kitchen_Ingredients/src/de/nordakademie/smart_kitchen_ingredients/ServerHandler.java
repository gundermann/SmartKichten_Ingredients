package de.nordakademie.smart_kitchen_ingredients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class ServerHandler {

	protected String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
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
