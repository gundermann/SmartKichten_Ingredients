package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IServerIngredient;

/**
 * @author Niels Gundermann
 */
public class ServerHandler implements IServerHandler {

	private Gson jsonParser;
	private SKIServerConnector connector;

	public ServerHandler(SKIServerConnector serverConnector) {
		connector = serverConnector;
		jsonParser = new Gson();
	}

	@Override
	public List<String[]> getIngredientListFromServer() {
		String response = connector.getAllIngredientsFromServer();

		List<JsonObject> jsonFromResponse = filterJsonFromResponse(response);

		List<String[]> ingredientList = getIngredientsFromJsonList(jsonFromResponse);

		return removeNullValues(ingredientList);
	}

	private List<String[]> removeNullValues(List<String[]> ingredientList) {
		for (String[] strings : ingredientList) {
			if (strings == null) {
				ingredientList.remove(strings);
			}
		}
		return ingredientList;
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

	@Override
	public Map<String[], List<String[]>> getRecipeListFromServer() {
		Map<String[], List<String[]>> recipeList = new HashMap<String[], List<String[]>>();

		String response = connector.getAllRecipesFromServer();
		List<JsonObject> jsonFromResponse = filterJsonFromResponse(response);

		for (JsonObject json : jsonFromResponse) {
			String[] key = getRecipeKey(json);
			Type listType = new TypeToken<List<JsonObject>>() {
			}.getType();
			List<JsonObject> jsonIngredientList = jsonParser.fromJson(
					json.get("ingredients"), listType);

			List<String[]> value = getIngredientsFromJsonList(jsonIngredientList);

			recipeList.put(key, value);
		}
		return recipeList;
	}

	private List<String[]> getIngredientsFromJsonList(
			List<JsonObject> jsonIngredientList) {
		List<String[]> value = new ArrayList<String[]>();
		for (JsonObject jsonIngredient : jsonIngredientList) {
			try {
				String[] ingredient = new String[3];
				if (jsonIngredient.get("amount") != null) {
					ingredient = new String[4];
					ingredient[3] = jsonIngredient.get("amount").getAsString();
				}
				ingredient[0] = jsonIngredient.get("_id").getAsString();
				ingredient[1] = jsonIngredient.get("title").getAsString();
				ingredient[2] = jsonIngredient.get("unit").getAsString();
				value.add(ingredient);
			} catch (NullPointerException npe) {
				value.add(null);
			}
		}
		return value;

	}

	private String[] getRecipeKey(JsonObject json) {
		try {
			String id = json.get("_id").getAsString();
			String recipeTitle = json.get("title").getAsString();
			String[] key = new String[2];
			key[0] = id;
			key[1] = recipeTitle;
			return key;
		} catch (NullPointerException npe) {
			return null;
		}
	}

	@Override
	public void postIngredientToServer(IServerIngredient ingredient) {
		String jsonToPost = jsonParser.toJson(ingredient);
		connector.postIngredientToServer(jsonToPost);
	}

}
