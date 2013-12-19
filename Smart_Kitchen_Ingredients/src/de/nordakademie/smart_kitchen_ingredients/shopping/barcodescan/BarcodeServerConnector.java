package de.nordakademie.smart_kitchen_ingredients.shopping.barcodescan;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import de.nordakademie.smart_kitchen_ingredients.onlineconnection.Connector;

/**
 * Stellt die Verbindung zu einem Server her, der eine API für Barcodes
 * anbietet.
 * 
 * @author niels
 * 
 */
public class BarcodeServerConnector extends Connector implements IApiConnector {

	private static final String URL = "http://eandata.com/feed/";

	@Override
	public String getResponseForInput(String barcode, String apikey) {
		try {
			final HttpClient client = new DefaultHttpClient();

			StringBuilder sb = new StringBuilder();
			sb.append(URL).append("?v=3&keycode=").append(apikey)
					.append("&mode=json&find=").append(barcode);

			// URIBuilder von Apache hat nicht mehr Funktioniert
			// URIBuilder uriBuilder = new URIBuilder(URL);
			// uriBuilder.addParameter("v", "3");
			// uriBuilder.addParameter("keycode", APIKEY);
			// uriBuilder.addParameter("mode", "json");
			// uriBuilder.addParameter("find", barcode);

			final HttpGet get = new HttpGet(sb.toString());

			HttpResponse response = client.execute(get);

			return convertStreamToString(response.getEntity().getContent());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
