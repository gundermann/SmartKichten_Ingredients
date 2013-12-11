package de.nordakademie.smart_kitchen_ingredients.ocr;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import de.nordakademie.smart_kitchen_ingredients.Connector;

public class OCRServiceAPI extends Connector {
	public final String SERVICE_URL = "http://api.ocrapiservice.com/1.0/rest/ocr";

	private final String PARAM_IMAGE = "image";
	private final String PARAM_LANGUAGE = "language";
	private final String PARAM_APIKEY = "apikey";

	private String apiKey;

	private String responseText;

	public OCRServiceAPI(final String apiKey) {
		this.apiKey = apiKey;
	}

	public String getShoppinglistFromFoto(final String language,
			final String filePath) {
		try {
			return getResponseByImage(language, filePath);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private String getResponseByImage(final String language,
			final String filePath) throws ClientProtocolException, IOException {
		final HttpClient httpclient = new DefaultHttpClient();
		final HttpPost httppost = new HttpPost(SERVICE_URL);

		final FileBody image = new FileBody(new File(filePath));

		final MultipartEntity reqEntity = new MultipartEntity();
		reqEntity.addPart(PARAM_IMAGE, image);
		reqEntity.addPart(PARAM_LANGUAGE, new StringBody(language));
		reqEntity.addPart(PARAM_APIKEY, new StringBody(apiKey));
		httppost.setEntity(reqEntity);

		final HttpResponse response = httpclient.execute(httppost);
		final HttpEntity entity = response.getEntity();

		return convertStreamToString(entity.getContent());
	}

	@Override
	public String getResponseForInput(String input) {
		return null;
	}

}
