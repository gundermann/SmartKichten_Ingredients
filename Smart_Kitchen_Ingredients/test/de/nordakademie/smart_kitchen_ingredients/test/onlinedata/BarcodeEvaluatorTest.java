package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.IBarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.onlineconnection.IServerConnector;

public class BarcodeEvaluatorTest {

	IServerConnector connector;
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

		assertTrue(evaluator.getItemDescription("test").equals(testDescription));

	}
}
