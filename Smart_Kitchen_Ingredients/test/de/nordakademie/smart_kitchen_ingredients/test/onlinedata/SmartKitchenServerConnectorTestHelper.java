package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

		return connector;
	}
}
