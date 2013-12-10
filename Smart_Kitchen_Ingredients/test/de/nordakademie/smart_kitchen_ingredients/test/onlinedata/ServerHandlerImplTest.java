package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;

import de.nordakademie.smart_kitchen_ingredients.onlinedata.SKIServerConnector;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.ServerHandler;

public class ServerHandlerImplTest {

	ServerHandler serverHandler;

	@Before
	public void setUp() throws Exception {
		SKIServerConnector serverConnector = SKIConnectorTestHelper.createConnectorMock();
		serverHandler = new ServerHandler(serverConnector);
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
		Map<String[], List<String[]>> recipeList = serverHandler
				.getRecipeListFromServer();

		Set<String[]> keyset = recipeList.keySet();
		assertTrue(recipeList.size() == 1);

		Iterator<String[]> iterator = keyset.iterator();

		String[] recipe = iterator.next();

		assertTrue(recipe[0].equals("123"));
		assertTrue(recipe[1].equals("Spinat mit Ei"));

		List<String[]> ingredients = recipeList.get(recipe);
		assertNotNull(ingredients);
		assertTrue(ingredients.size() == 2);

		Iterator<String[]> ingredientIterator = ingredients.iterator();

		String[] firstIngredient = ingredientIterator.next();
		String[] secondIngredient = ingredientIterator.next();

		assertTrue(firstIngredient[0].equals("kljsdlfsdi833"));
		assertTrue(firstIngredient[1].equals("Spinat"));
		assertTrue(firstIngredient[2].equals("g"));
		assertTrue(firstIngredient[3].equals("500"));

		assertTrue(secondIngredient[0].equals("kljsdlfsdi832"));
		assertTrue(secondIngredient[1].equals("Ei"));
		assertTrue(secondIngredient[2].equals("stk"));
		assertTrue(secondIngredient[3].equals("2"));
	}

	@Test
	public void testGettingIngredient() {
		List<String[]> ingredientList = serverHandler
				.getIngredientListFromServer();

		assertTrue(ingredientList.size() == 2);

		Iterator<String[]> ingredientIterator = ingredientList.iterator();

		String[] firstIngredient = ingredientIterator.next();
		String[] secondIngredient = ingredientIterator.next();

		assertTrue(firstIngredient[0].equals("kljsdlfsdi833"));
		assertTrue(firstIngredient[1].equals("Spinat"));
		assertTrue(firstIngredient[2].equals("g"));

		assertTrue(secondIngredient[0].equals("kljsdlfsdi832"));
		assertTrue(secondIngredient[1].equals("Ei"));
		assertTrue(secondIngredient[2].equals("stk"));
	}
}
