/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ListView;
import android.widget.ScrollView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.QuantityPickerDialog;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;

/**
 * @author Frederic Oppermann
 * @date 18.12.2013
 */
@SuppressLint("ValidFragment")
public class ShowRecipeIngredientsDialog extends DialogFragment {
	private IRecipe recipe;
	@SuppressLint("ValidFragment")
	private final IngredientsApplication app;

	public ShowRecipeIngredientsDialog(IngredientsApplication app) {
		this.app = app;
	}

	@SuppressLint("ValidFragment")
	public static ShowRecipeIngredientsDialog newInstance(IRecipe recipe,
			IngredientsApplication app) {
		ShowRecipeIngredientsDialog dialog = new ShowRecipeIngredientsDialog(
				app);
		dialog.setRecipe(recipe);
		return dialog;
	}

	public void setRecipe(IRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				getActivity());
		ListView listview = new ListView(app.getApplicationContext());
		Map<IIngredient, Integer> ingredients = recipe.getIngredients();

		listview.setAdapter(AdapterFactory.createRecipeIngredientAdapter(app,
				ingredients));
		ScrollView scrollView = new ScrollView(getActivity());
		scrollView.addView(listview);
		dialogBuilder
				.setView(scrollView)
				.setPositiveButton(android.R.string.ok, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						QuantityPickerDialog.newInstance(recipe, app).show(
								getFragmentManager(), getTag());
					}
				})
				.setNegativeButton(android.R.string.cancel,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dismiss();
							}
						});

		return dialogBuilder.create();
	}
}
