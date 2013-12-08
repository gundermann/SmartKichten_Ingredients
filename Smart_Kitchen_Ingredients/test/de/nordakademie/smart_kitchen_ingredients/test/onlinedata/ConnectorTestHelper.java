package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.Connector;

public class ConnectorTestHelper {

	public static Connector createConnectorMock() {
		Connector connector = mock(Connector.class);

		String recipeReponse = " {\"title\":\"Spinat mit Ei\",\"ingredients\":["
				+ "{\"title\":\"Spinat\",\"amount\": 500,\"unit\":\"g\"}, "
				+ "{\"title\":\"Ei\",\"amount\": 2,\"unit\":\"stk\"}"
				+ "]}, {\"title\":\"Ei\",\"ingredients\":["
				+ "{\"title\":\"Ei\",\"amount\": 2,\"unit\":\"stk\"}" + "]} ";

		when(connector.getAllRecipesFromServer()).thenReturn(recipeReponse);

		String ingredientResponse = "{\"title\":\"Spinat\",\"unit\":\"g\"}, "
				+ "{\"title\":\"Ei\",\"unit\":\"stk\"} ";
		when(connector.getAllIngredientsFromServer()).thenReturn(
				ingredientResponse);

		return connector;
	}
}
