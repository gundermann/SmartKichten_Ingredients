package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class BarcodeServerConnector extends Connector implements
		IBarcodeServerConnector {

	private static final String URL = "http://www.upcdatabase.com/item/";

	@Override
	public String getResponseForBarcode(String barcode) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(URL + barcode);

		HttpResponse response;
		String result = "";
		try {
			response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				Log.i("Praeda", result);

				instream.close();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
