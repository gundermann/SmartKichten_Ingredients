package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.Connector;

public class ConnectorTestHelper {

	public static Connector createConnectorMock() {
		Connector connector = mock(Connector.class);

		String recipeReponse = " {\"title\":\"Spinat mit Ei\",\"ingredients\":["
				+ "{\"title\":\"Spinat\",\"amount\": 500,\"unit\":\"g\",\"_id\":\"kljsdlfsdi833\"}, "
				+ "{\"title\":\"Ei\",\"amount\": 2,\"unit\":\"stk\",\"_id\":\"kljsdlfsdi832\"}"
				+ "],\"_id\":\"123\"}";

		when(connector.getAllRecipesFromServer()).thenReturn(recipeReponse);

		String ingredientResponse = "{\"title\":\"Spinat\",\"unit\":\"g\",\"_id\":\"kljsdlfsdi833\"}, "
				+ "{\"title\":\"Ei\",\"unit\":\"stk\",\"_id\":\"kljsdlfsdi832\"} ";
		when(connector.getAllIngredientsFromServer()).thenReturn(
				ingredientResponse);

		return connector;
	}
}
