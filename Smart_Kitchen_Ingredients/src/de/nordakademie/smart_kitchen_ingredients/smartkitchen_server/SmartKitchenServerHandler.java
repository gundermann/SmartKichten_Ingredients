package de.nordakademie.smart_kitchen_ingredients.smartkitchen_server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.onlineconnection.ServerHandler;

/**
 * Verarbeitung der Requests und Responses an und von dem zur Verfügung
 * gestellten Server.
 * 
 * @author Kathrin Kurtz
 */
public class SmartKitchenServerHandler extends ServerHandler implements
		ISmartKitchenServerHandler {

	private Gson jsonParser;
	private ISmartKichtenServerConnector connector;
	private static final String INGREDIENTS = "ingredients";
	private static final String RECIPES = "recepies";

	public SmartKitchenServerHandler(
			ISmartKichtenServerConnector serverConnector) {
		connector = serverConnector;
		jsonParser = new Gson();
	}

	@Override
	public List<String[]> getIngredientListFromServer() {
		List<JsonObject> jsonFromResponse = filterJsonFromResponse(connector
				.getResponseForInput(INGREDIENTS));

		List<String[]> ingredientList = getIngredientsFromJsonList(jsonFromResponse);
		
		return removeNullValues(ingredientList);
	}

	private List<String[]> removeNullValues(List<String[]> ingredientList) {
		List<String[]> returnValues = new ArrayList<String[]>();
		for (String[] strings : ingredientList) {
			if (strings != null) {
				returnValues.add(strings);
			}
		}
		return returnValues;
	}

	@Override
	public Map<String[], List<String[]>> getRecipeListFromServer() {
		Map<String[], List<String[]>> recipeList = new HashMap<String[], List<String[]>>();
		List<JsonObject> jsonFromResponse = filterJsonFromResponse(connector
				.getResponseForInput(RECIPES));
		
		for (JsonObject singleRecipeJson : jsonFromResponse) {
			
			String[] key = getRecipeKey(singleRecipeJson);
			JsonElement allIngredientsForOneRecipe = singleRecipeJson.get("ingredients");
			
			List<String[]> value = new ArrayList<String[]>();
			
			int i = 0;
			while(allIngredientsForOneRecipe.getAsJsonArray().size() > i){
				String[] currentIngredientArray = new String[4]; 
				JsonElement singleIngredient = allIngredientsForOneRecipe.
						getAsJsonArray().get(i);
				String id = ((JsonObject) singleIngredient).get("_id").toString();
				currentIngredientArray[0] = id.substring(1, id.length()-1);
				
				String title = ((JsonObject) singleIngredient).get("title").toString();
				currentIngredientArray[1] = title.substring(1, title.length()-1);
				
				String unitString = ((JsonObject) singleIngredient).get("unit").toString();
				String shortUnitString = unitString.substring(1, unitString.length() - 1);
				Unit unit = Unit.valueOfFromShortening(shortUnitString);			
				currentIngredientArray[2] = unit.toLongString();
				
				String amount = ((JsonObject) singleIngredient).get("amount").toString();
				currentIngredientArray[3] = amount;
				i++;
				value.add(currentIngredientArray);
			}
			
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
			String recipeTitle = json.get("name").getAsString();
			String[] key = new String[2];
			key[0] = id;
			key[1] = recipeTitle;
			return key;
		} catch (NullPointerException npe) {
			return null;
		}
	}

	@Override
	public void postIngredientToServer(IIngredient ingredient)
			throws UnknownHostException {
		String jsonToPost = jsonParser.toJson(ingredient);
		connector.postIngredientToServer(jsonToPost);
	}

}
