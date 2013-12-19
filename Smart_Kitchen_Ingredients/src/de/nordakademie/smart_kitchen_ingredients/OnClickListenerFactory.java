/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import de.nordakademie.smart_kitchen_ingredients.collector.IngredientCollectorActivity;
import de.nordakademie.smart_kitchen_ingredients.collector.RecipeCollectorActivity;

/**
 * @author frederic.oppermann
 * @date 19.12.2013
 * @description
 */
public class OnClickListenerFactory {

	public static OnClickListener startRecipeListActivity(Context context) {
		return setNextActivityOnClick(context, RecipeCollectorActivity.class);
	}

	public static OnClickListener startIngredientListActivity(Context context) {
		return setNextActivityOnClick(context,
				IngredientCollectorActivity.class);
	}

	private static OnClickListener setNextActivityOnClick(
			final Context context, final Class<?> nextActivityClass) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context, nextActivityClass));
			}
		};
	}
}
