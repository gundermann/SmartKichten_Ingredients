package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.IApiConnector;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.IBarcodeServerHandler;

public class BarcodeEvaluatorTest {

	IApiConnector connector;
	IBarcodeServerHandler evaluator;

	@Before
	public void setUp() {
		connector = BarcodeServerConnectorTestHelper
				.getBarcodeServerConnector();
		evaluator = new BarcodeServerHandler(connector);
	}

	@Test
	public void testEvaluation() {
		String testDescription = "Mars Chocolate Candy Bar";

		assertTrue(evaluator.getItemDescription("test", "null").equals(
				testDescription));

	}
}
