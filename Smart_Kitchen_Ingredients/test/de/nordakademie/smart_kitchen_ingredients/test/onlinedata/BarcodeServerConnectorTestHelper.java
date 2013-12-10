package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import de.nordakademie.smart_kitchen_ingredients.onlinedata.IBarcodeServerConnector;

public class BarcodeServerConnectorTestHelper {

	private static final String pathToDir = "test/de/nordakademie/smart_kitchen_ingredients/test/onlinedata/";

	public static IBarcodeServerConnector getBarcodeServerConnector() {
		IBarcodeServerConnector connector = mock(IBarcodeServerConnector.class);

		StringBuilder responseStringBuilder = new StringBuilder();

		File testFile = new File(pathToDir + "upctestside.html");
		try {
			BufferedReader br = new BufferedReader(new FileReader(testFile));
			String line;
			while ((line = br.readLine()) != null) {
				responseStringBuilder.append(line);
			}

			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("Test-Datei nicht gefunden");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		when(connector.getResponseForBarcode("test")).thenReturn(
				responseStringBuilder.toString());

		return connector;
	}
}
