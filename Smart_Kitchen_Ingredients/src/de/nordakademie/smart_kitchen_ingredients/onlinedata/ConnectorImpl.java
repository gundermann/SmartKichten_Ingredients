package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

/**
 * @author Niels Gundermann
 */
public class ConnectorImpl implements Connector {
	private final static String URL = "http://lx05.nordakademie.de:7002";

	@Override
	public String getAllIngredientsFromServer() {
		WebResource res = Client.create().resource(URL);
		Builder builder = res.path("ingredients").accept(MediaType.TEXT_PLAIN);

		return builder.toString();
	}

	@Override
	public String getAllRecipesFromServer() {
		WebResource res = Client.create().resource(URL);
		Builder builder = res.path("recepies").accept(MediaType.TEXT_PLAIN);

		return builder.toString();
	}

	@Override
	public void postIngredientToServer(String jsonToPost) {
		WebResource res = Client.create().resource(URL);
		res.path("ingredients").type("application/json")
				.post(String.class, jsonToPost);
	}
}
