package de.nordakademie.smart_kitchen_ingredients.barcodescan;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import de.nordakademie.smart_kitchen_ingredients.onlineconnection.Connector;

/**
 * Stellt die Verbindung zu einem Server her, der eine API für Barcodes
 * anbietet.
 * 
 * @author niels
 * 
 */
public class BarcodeServerConnector extends Connector {

	private static final String URL = "http://eandata.com/feed/";

	private final String APIKEY = "E1C9A73C52A822FB";

	@Override
	public String getResponseForInput(String barcode) {
		try {
			final HttpClient client = new DefaultHttpClient();

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("v", "3"));
			nameValuePairs.add(new BasicNameValuePair("keycode", APIKEY));
			nameValuePairs.add(new BasicNameValuePair("mode", "json"));
			nameValuePairs.add(new BasicNameValuePair("find", barcode));
			URIBuilder uriBuilder = new URIBuilder(URL);

			uriBuilder.addParameters(nameValuePairs);
			final HttpGet get = new HttpGet(uriBuilder.build());

			HttpResponse response = client.execute(get);

			return convertStreamToString(response.getEntity().getContent());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
