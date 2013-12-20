package de.nordakademie.smart_kitchen_ingredients;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.collector.CollectorAdapter;
import de.nordakademie.smart_kitchen_ingredients.scheduling.DateListAdapter;
import de.nordakademie.smart_kitchen_ingredients.shopping.ShoppingListAdapter;
import de.nordakademie.smart_kitchen_ingredients.stock.StockListAdapter;

/**
 * @author frederic.oppermann
 * @date 16.12.2013
 * @description
 * @param <T>
 */
public class AdapterFactory {

	public static ListAdapter createShoppingListAdapter(
			Context applicationContext, int entryView,
			List<IShoppingList> elements) {
		return new CollectorAdapter<IShoppingList>(applicationContext,
				entryView, entryView, elements);
	}

	public static ListAdapter createCheckableAdapter(
			IngredientsApplication application, String currentShoppingListName) {
		return new ShoppingListAdapter(application, currentShoppingListName);
	}

	public static ListAdapter createStoreAdapter(
			IngredientsApplication application) {
		return new StockListAdapter(application);
	}

	public static ListAdapter createDateAdapter(
			IngredientsApplication application) {
		return new DateListAdapter(application);
	}

	public static ListAdapter createIngredientCollectorAdapter(
			Context applicationContext, int entryView,
			List<IIngredient> elements) {
		return new CollectorAdapter<IIngredient>(applicationContext, entryView,
				entryView, elements);
	}

	public static ListAdapter createRecipeCollectorAdapter(
			Context applicationContext, int entryView, List<IRecipe> elements) {
		return new CollectorAdapter<IRecipe>(applicationContext, entryView,
				entryView, elements);
	}

	public static SpinnerAdapter createUnitAdapter(Context applicationContext,
			int entryView, Unit[] elements) {
		return new ArrayAdapter<Unit>(applicationContext, entryView, elements);
	}

}
