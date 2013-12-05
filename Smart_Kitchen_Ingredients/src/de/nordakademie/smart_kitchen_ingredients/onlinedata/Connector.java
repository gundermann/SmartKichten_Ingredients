package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class Connector {

	private static final String URL = "http://lx05.nordakademie.de:7002";

	public static String getAllIngredientsFromServer() {
		WebResource res = Client.create().resource(URL);
		Builder builder = res.path("ingredients").accept(MediaType.TEXT_PLAIN);

		return builder.toString();
	}

	public static String getAllRecipesFromServer() {
		WebResource res = Client.create().resource(URL);
		Builder builder = res.path("recepies").accept(MediaType.TEXT_PLAIN);

		return builder.toString();
	}

	public static void postIngredientToServer(String jsonToPost) {
		WebResource res = Client.create().resource(URL);
		res.path("ingredients").type("application/json")
				.post(String.class, jsonToPost);
	}

}
