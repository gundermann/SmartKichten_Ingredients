package de.nordakademie.smart_kitchen_ingredients.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import de.nordakademie.smart_kitchen_ingredients.MainMenuActivity;

public class MainMenuActivityTest extends
		ActivityUnitTestCase<MainMenuActivity> {

	private MainMenuActivity activity;
	private int buttonNewIngredientsId;
	private int buttonShoppingList;

	public MainMenuActivityTest(Class<MainMenuActivity> activityClass) {
		super(activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				MainMenuActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
	}

	public void testLayout() {
		buttonNewIngredientsId = de.nordakademie.smart_kitchen_ingredients.R.id.buttonForNewIngredients;
		assertNotNull(activity.findViewById(buttonNewIngredientsId));
		Button view = (Button) activity.findViewById(buttonNewIngredientsId);
		assertEquals(
				"Incorrect label of the button",
				activity.getString(de.nordakademie.smart_kitchen_ingredients.R.string.allocateIngredients),
				view.getText());

		buttonShoppingList = de.nordakademie.smart_kitchen_ingredients.R.id.buttonForShopping;
		assertNotNull(activity.findViewById(buttonShoppingList));
		view = (Button) activity.findViewById(buttonShoppingList);
		assertEquals(
				"Incorrect label of the button",
				activity.getString(de.nordakademie.smart_kitchen_ingredients.R.string.shopping),
				view.getText());
	}

}
