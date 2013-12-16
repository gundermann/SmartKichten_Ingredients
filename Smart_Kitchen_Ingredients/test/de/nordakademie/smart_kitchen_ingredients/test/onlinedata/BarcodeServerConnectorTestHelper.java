package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import de.nordakademie.smart_kitchen_ingredients.onlineconnection.IServerConnector;

public class BarcodeServerConnectorTestHelper {

	public static IServerConnector getBarcodeServerConnector() {
		IServerConnector connector = mock(IServerConnector.class);

		String responseString = "{\"status\":{\"version\":\"3.1\",\"code\":\"200\",\"message\":\"free\",\"find\":\"5000159407236\",\"run\":\"0.8473\"},\"product\":{\"attributes\":{\"product\":\"Mars Chocolate Candy Bar\",\"price_used\":\"0\",\"category\":\"0\",\"category_text\":\"Unknown\",\"category_text_long\":\"Unknown\"},\"EAN13\":\"5000159407236\"},\"locked\":\"0\",\"modified\":\"2012-05-30 09:23:33\"},\"company\":{\"name\":\"Masterfoods Ltd\",\"logo\":\"\",\"url\":\"\",\"address\":\"\",\"phone\":\"\",\"locked\":\"0\"}}";

		when(connector.getResponseForInput("test"))
				.thenReturn(responseString);

		return connector;
	}
}
