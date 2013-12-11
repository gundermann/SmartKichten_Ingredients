package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.nordakademie.smart_kitchen_ingredients.onlinedata.ISmartKichtenServerConnector;

public class SmartKitchenServerConnectorTestHelper {

	public static ISmartKichtenServerConnector createConnectorMock() {
		ISmartKichtenServerConnector connector = mock(ISmartKichtenServerConnector.class);

		String recipeReponse = " {\"title\":\"Spinat mit Ei\",\"ingredients\":["
				+ "{\"title\":\"Spinat\",\"amount\": 500,\"unit\":\"g\",\"_id\":\"kljsdlfsdi833\"}, "
				+ "{\"title\":\"Ei\",\"amount\": 2,\"unit\":\"stk\",\"_id\":\"kljsdlfsdi832\"}"
				+ "],\"_id\":\"123\"}";

		when(connector.getResponseForInput("recepies")).thenReturn(
				recipeReponse);

		String ingredientResponse = "{\"title\":\"Spinat\",\"unit\":\"g\",\"_id\":\"kljsdlfsdi833\"}, "
				+ "{\"title\":\"Ei\",\"unit\":\"stk\",\"_id\":\"kljsdlfsdi832\"} ";
		when(connector.getResponseForInput("ingredients")).thenReturn(
				ingredientResponse);

		List<JsonObject> jsonList = new ArrayList<JsonObject>();
		List<JsonObject> jsonIngredientList = new ArrayList<JsonObject>();
		JsonArray jsonIngredientArray = new JsonArray();

		JsonObject firstIngredient = new JsonObject();
		firstIngredient.addProperty("title", "Spinat");
		firstIngredient.addProperty("amount", "500");
		firstIngredient.addProperty("unit", "g");
		firstIngredient.addProperty("_id", "kljsdlfsdi833");

		JsonObject secondIngredient = new JsonObject();
		secondIngredient.addProperty("title", "Ei");
		secondIngredient.addProperty("amount", "2");
		secondIngredient.addProperty("unit", "stk");
		secondIngredient.addProperty("_id", "kljsdlfsdi832");

		jsonIngredientArray.add(firstIngredient);
		jsonIngredientArray.add(secondIngredient);

		jsonIngredientList.add(firstIngredient);
		jsonIngredientList.add(secondIngredient);

		JsonObject jsonRecipe = new JsonObject();
		jsonRecipe.addProperty("title", "Spinat mit Ei");
		jsonRecipe.add("ingredients", jsonIngredientArray);
		jsonRecipe.addProperty("_id", "123");

		jsonList.add(jsonRecipe);

		when(connector.getFilteredJsonFromResponse("recepies")).thenReturn(
				jsonList);

		when(connector.getFilteredJsonFromResponse("ingredients")).thenReturn(
				jsonIngredientList);

		return connector;
	}
}
