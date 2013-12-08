package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Recipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ServerIngredient;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.Connector;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.ServerHandlerImpl;

public class ServerHandlerImplTest {

	ServerHandlerImpl serverHandler;

	@Before
	public void setUp() throws Exception {
		Connector serverConnector = ConnectorTestHelper.createConnectorMock();
		serverHandler = new ServerHandlerImpl(serverConnector);
	}

	@Test
	public void testFilterJson() {
		String json = "{\"title\":\"Spinat mit Ei\",\"ingredients\":"
				+ "[{\"title\":\"Spinat\",\"amount\":500,\"unit\":\"g\"},"
				+ "{\"title\":\"Ei\",\"amount\":2,\"unit\":\"stk\"}]}";

		String jsonLong = " {\"title\":\"Spinat mit Ei\",\"ingredients\":"
				+ "[{\"title\":\"Spinat\",\"amount\":500,\"unit\":\"g\"}, "
				+ "{\"title\":\"Ei\",\"amount\":2,\"unit\":\"stk\"}]}, {\"title\":\"Ei\",\"ingredients\":"
				+ "[{\"title\":\"Ei\",\"amount\":2,\"unit\":\"stk\"}]} ";

		List<JsonObject> stringList = serverHandler
				.filterJsonFromResponse(json);

		assertTrue(stringList.size() == 1);

		stringList = serverHandler.filterJsonFromResponse(jsonLong);

		assertTrue(stringList.size() == 2);
	}

	@Test
	public void testGettingRecepies() {
		TreeSet<Recipe> recipeList = serverHandler.getRecipeListFromServer();

		assertTrue(recipeList.size() == 2);

		Recipe firstRecpie = recipeList.pollFirst();
		Recipe secondRecipe = recipeList.pollFirst();

		assertTrue(firstRecpie.getTitle().equals("Ei"));
		assertTrue(secondRecipe.getTitle().equals("Spinat mit Ei"));

		List<Ingredient> ingredients = firstRecpie.getIngredients();
		assertNotNull(ingredients);
		assertTrue(ingredients.size() == 1);

		ingredients = secondRecipe.getIngredients();
		assertNotNull(ingredients);
		assertTrue(ingredients.size() == 2);
	}

	@Test
	public void testGettingIngredient() {
		TreeSet<ServerIngredient> ingredientList = serverHandler
				.getIngredientListFromServer();

		assertTrue(ingredientList.size() == 2);

		ServerIngredient firstIngredient = ingredientList.pollFirst();
		ServerIngredient secondIngredient = ingredientList.pollFirst();

		assertTrue(firstIngredient.getTitle().equals("Ei"));
		assertTrue(secondIngredient.getTitle().equals("Spinat"));
	}
}
