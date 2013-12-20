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

	private static final int NUMBER_OF_FIELDS_WITHOUT_QUANTITY = 3;
	private static final int NUMBER_OF_FIELD_WITH_QUANTITY = 4;
	private static final int FIELD_UNIT_ID = 2;
	private static final int FIELD_NAME_ID = 1;
	private static final int FIELD_ID_ID = 0;
	private static final int FIELD_QUANTITY_ID = 3;
	private static final int FIELD_IS_QUANTITY = 3;
	private static final String FIELD_NAME_NAME = "name";
	private static final String FIELD_QUANTITY_NAME = "amount";
	private static final String FIELD_UNIT_NAME = "unit";
	private static final String FIELD_TITLE_NAME = "title";
	private static final String FIELD_ID_NAME = "_id";
	private static final int INGREDIENT_FIELD_NO_ID = 0;
	private static final int NUMBER_OF_FIELDS_OF_INGREDIENT = 4;
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
			JsonElement allIngredientsForOneRecipe = singleRecipeJson
					.get(INGREDIENTS);

			List<String[]> value = new ArrayList<String[]>();

			int i = 0;
			while (allIngredientsForOneRecipe.getAsJsonArray().size() > i) {
				String[] currentIngredientArray = new String[NUMBER_OF_FIELDS_OF_INGREDIENT];
				JsonElement singleIngredient = allIngredientsForOneRecipe
						.getAsJsonArray().get(i);
				String id = ((JsonObject) singleIngredient).get(FIELD_ID_NAME)
						.toString();
				currentIngredientArray[INGREDIENT_FIELD_NO_ID] = id.substring(
						1, id.length() - 1);

				String title = ((JsonObject) singleIngredient).get(
						FIELD_TITLE_NAME).toString();
				currentIngredientArray[1] = title.substring(1,
						title.length() - 1);

				String unitString = ((JsonObject) singleIngredient).get(
						FIELD_UNIT_NAME).toString();
				String shortUnitString = unitString.substring(1,
						unitString.length() - 1);
				Unit unit = Unit.valueOfFromShortening(shortUnitString);
				currentIngredientArray[2] = unit.toLongString();

				String amount = ((JsonObject) singleIngredient).get(
						FIELD_QUANTITY_NAME).toString();
				currentIngredientArray[FIELD_IS_QUANTITY] = amount;
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
				String[] ingredient;
				if (jsonIngredient.get(FIELD_QUANTITY_NAME) != null) {
					ingredient = new String[NUMBER_OF_FIELD_WITH_QUANTITY];
					ingredient[FIELD_QUANTITY_ID] = jsonIngredient.get(
							FIELD_QUANTITY_NAME).getAsString();
				} else {
					ingredient = new String[NUMBER_OF_FIELDS_WITHOUT_QUANTITY];
				}
				ingredient[FIELD_ID_ID] = jsonIngredient.get(FIELD_ID_NAME)
						.getAsString();
				ingredient[FIELD_NAME_ID] = jsonIngredient
						.get(FIELD_TITLE_NAME).getAsString();
				ingredient[FIELD_UNIT_ID] = jsonIngredient.get(FIELD_UNIT_NAME)
						.getAsString();
				value.add(ingredient);
			} catch (NullPointerException npe) {
				value.add(null);
			}
		}
		return value;

	}

	private String[] getRecipeKey(JsonObject json) {
		try {
			String id = json.get(FIELD_ID_NAME).getAsString();
			String recipeTitle = json.get(FIELD_NAME_NAME).getAsString();
			String[] key = new String[2];
			key[INGREDIENT_FIELD_NO_ID] = id;
			key[FIELD_NAME_ID] = recipeTitle;
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
