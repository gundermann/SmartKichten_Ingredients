package de.nordakademie.smart_kitchen_ingredients.test.onlinedata;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.nordakademie.smart_kitchen_ingredients.onlinedata.BarcodeEvaluator;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.BarcodeServerConnector;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.IBarcodeEvaluator;

public class BarcodeEvaluatorTest {

	BarcodeServerConnector connector;
	IBarcodeEvaluator evaluator;

	@Before
	public void setUp() {
		connector = BarcodeServerConnectorTestHelper
				.getBarcodeServerConnector();
		evaluator = new BarcodeEvaluator(connector);
	}

	@Test
	public void testEvaluation() {
		String testDescription = "Mars Chocolate Candy Bar";

		assertTrue(evaluator.getItemDescription("test").equals(testDescription));

	}
}
