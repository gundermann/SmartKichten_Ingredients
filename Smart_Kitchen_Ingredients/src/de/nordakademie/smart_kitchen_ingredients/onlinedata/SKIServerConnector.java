package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/**
 * Stellt die Verbindung zum Server her.
 * 
 * @author Niels Gundermann
 */
public class SKIServerConnector extends Connector implements
		ISKIServerConnector {
	private final static String URL = "http://lx05.nordakademie.de:7002";
	private static final String TAG = SKIServerConnector.class.getSimpleName();

	@Override
	public String getAllIngredientsFromServer() {
		return getDataFromServer("ingredients");

	}

	private String getDataFromServer(String entityname) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(URL + "/" + entityname);

		HttpResponse response;
		String result = "";
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				instream.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getAllRecipesFromServer() {
		return getDataFromServer("recepies");
	}

	@Override
	public void postIngredientToServer(String jsonToPost) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(URL + "/ingredients");
		httppost.setHeader("Content-Type", "application/json");

		try {
			StringEntity entity = new StringEntity(jsonToPost);
			httppost.setEntity(entity);
			httpclient.execute(httppost);
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "wrong encoding");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.e(TAG, "not successful");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, "connot read server-response");
			e.printStackTrace();
		}
	}
}
