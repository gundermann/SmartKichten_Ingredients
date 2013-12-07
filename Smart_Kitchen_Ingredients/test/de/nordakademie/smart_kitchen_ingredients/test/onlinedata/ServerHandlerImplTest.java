package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.nordakademie.smart_kitchen_ingredients.onlinedata.ServerHandlerImpl;

public class ServerHandlerImplTest {

	ServerHandlerImpl serverHandler;

	@Before
	public void setUp() throws Exception {
		serverHandler = new ServerHandlerImpl();
	}

	@Test
	public void testFilterJson() {
		String json = " {\"name\":\"Spinat mit Ei\",\"ingredients\":"
				+ "[{\"title\":\"Spinat\",\"amount\":500,\"unit\":\"g\"},"
				+ "{\"title\":\"Ei\",\"amount\":2,\"unit\":\"stk\"}]} ";

		List<String> stringList = serverHandler.filterJsonFromResponse(json);

		assertTrue(json.length() == stringList.get(0).length() - 2);
	}

	@Test
	public void testGettingIngredient() {

	}
}
