package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

/**
 * Stellt die Verbindung zum Server her.
 * 
 * @author Niels Gundermann
 */
public class SKIServerConnector {
	private final static String URL = "http://lx05.nordakademie.de:7002";

	public String getAllIngredientsFromServer() {
		WebResource res = Client.create().resource(URL);
		Builder builder = res.path("ingredients").accept(MediaType.TEXT_PLAIN);

		return builder.get(String.class);
	}

	public String getAllRecipesFromServer() {
		WebResource res = Client.create().resource(URL);
		Builder builder = res.path("recepies").accept(MediaType.TEXT_PLAIN);

		return builder.get(String.class);
	}

	public void postIngredientToServer(String jsonToPost) {
		WebResource res = Client.create().resource(URL);
		res.path("ingredients").type("application/json")
				.post(String.class, jsonToPost);
	}
}
