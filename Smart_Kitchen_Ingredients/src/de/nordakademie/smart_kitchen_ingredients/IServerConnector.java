package de.nordakademie.smart_kitchen_ingredients;

import java.util.List;

import com.google.gson.JsonObject;

public interface IServerConnector {

	String getResponseForInput(String input);

	List<JsonObject> getFilteredJsonFromResponse(String response);
}
