package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IngredientImpl;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Recipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactoryImpl;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ServerIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ServerIngredientImpl;

/**
 * @author Niels Gundermann
 */
public class ServerHandlerImpl implements ServerHandler {

	private Gson jsonParser;
	private Connector connector;

	public ServerHandlerImpl(Connector serverConnector) {
		connector = serverConnector;
		jsonParser = new Gson();
	}

	@Override
	public TreeSet<ServerIngredient> getIngredientListFromServer() {
		TreeSet<ServerIngredient> ingredientList = new TreeSet<ServerIngredient>();

		String response = connector.getAllIngredientsFromServer();

		List<JsonObject> jsonFromResponse = filterJsonFromResponse(response);

		for (JsonObject json : jsonFromResponse) {
			ServerIngredient ingredient = jsonParser.fromJson(json,
					ServerIngredientImpl.class);
			ingredientList.add(ingredient);
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
	public TreeSet<Recipe> getRecipeListFromServer() {
		TreeSet<Recipe> recipetList = new TreeSet<Recipe>();
		String response = connector.getAllRecipesFromServer();
		List<JsonObject> jsonFromResponse = filterJsonFromResponse(response);

		for (JsonObject json : jsonFromResponse) {
			String recipeTitle = json.get("title").toString();
			recipeTitle = recipeTitle.substring(1, recipeTitle.length() - 1);
			Type listType = new TypeToken<List<JsonObject>>() {
			}.getType();
			List<JsonObject> jsonIngredientList = jsonParser.fromJson(
					json.get("ingredients"), listType);
			List<Ingredient> ingredientList = new ArrayList<Ingredient>();

			for (JsonObject jsonIngred : jsonIngredientList) {
				ingredientList.add(jsonParser.fromJson(jsonIngred,
						IngredientImpl.class));
			}

			RecipeFactory factory = new RecipeFactoryImpl();
			Recipe recipe = factory.createRecipe(recipeTitle, ingredientList);
			recipetList.add(recipe);
		}
		return recipetList;
	}

	@Override
	public void postIngredientToServer(ServerIngredient ingredient) {
		String jsonToPost = jsonParser.toJson(ingredient);
		connector.postIngredientToServer(jsonToPost);
	}

}
