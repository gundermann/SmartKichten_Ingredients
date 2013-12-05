package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.google.gson.Gson;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Recipe;

public class ServerHandlerImpl implements ServerHandler {

	private Gson jsonParser = new Gson();

	@Override
	public TreeSet<Ingredient> getIngredientListFromServer() {
		TreeSet<Ingredient> ingredientList = new TreeSet<Ingredient>();

		String response = Connector.getAllIngredientsFromServer();

		List<String> jsonFromResponse = filterJsonFromResponse(response);

		for (String json : jsonFromResponse) {
			Ingredient ingredient = jsonParser.fromJson(json,
					ServerIngredient.class);
			ingredientList.add(ingredient);
		}
		return ingredientList;
	}

	public List<String> filterJsonFromResponse(String response) {
		List<String> filteredResponse = new ArrayList<String>();
		int firstIndex = 0;
		int secondIndex = 1;
		int counter = -1;

		while (firstIndex < response.length()) {
			String currentChar = response.substring(secondIndex - 1,
					secondIndex);
			if (currentChar.equals("{")) {
				if (counter == -1) {
					counter++;
				}
				counter++;
			}
			if (currentChar.equals("}")) {
				counter--;
			}
			if (counter == 0) {
				filteredResponse.add(response
						.substring(firstIndex, secondIndex));
				firstIndex = secondIndex;
				counter = -1;
			}
			secondIndex++;
		}

		return filteredResponse;
	}

	@Override
	public TreeSet<Recipe> getRecipeListFromServer() {
		TreeSet<Recipe> recipetList = new TreeSet<Recipe>();
		String response = Connector.getAllRecipesFromServer();
		List<String> jsonFromResponse = filterJsonFromResponse(response);

		for (String json : jsonFromResponse) {
			Recipe recipe = jsonParser.fromJson(json, ServerRecipe.class);
			recipetList.add(recipe);
		}
		return recipetList;
	}

	@Override
	public void postIngredientToServer(Ingredient ingredient) {
		ServerIngredient ingredientToPost = (ServerIngredient) ingredient;
		String jsonToPost = jsonParser.toJson(ingredientToPost);
		Connector.postIngredientToServer(jsonToPost);
	}

}
