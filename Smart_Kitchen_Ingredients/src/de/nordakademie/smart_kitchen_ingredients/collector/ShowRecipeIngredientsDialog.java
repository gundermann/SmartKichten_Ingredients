/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.Map;
import java.util.Map.Entry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

/**
 * @author frederic.oppermann
 * @date 18.12.2013
 * @description
 */
public class ShowRecipeIngredientsDialog extends DialogFragment {
	private IRecipe recipe;

	public static ShowRecipeIngredientsDialog newInstance(IRecipe recipe) {
		ShowRecipeIngredientsDialog dialog = new ShowRecipeIngredientsDialog();
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
		TableLayout tableView = new TableLayout(getActivity());
		Map<IIngredient, Integer> ingredients = recipe.getIngredients();

		ScrollView scrollView = new ScrollView(getActivity());

		for (Entry<IIngredient, Integer> ingredient : ingredients.entrySet()) {
			TableRow row = new TableRow(getActivity());
			TextView ingredientName = new TextView(getActivity());
			TextView ingredientQuantity = new TextView(getActivity());

			ingredientName.setTextColor(Color.WHITE);
			ingredientQuantity.setTextColor(Color.WHITE);
			ingredientName.setText(ingredient.getKey().getName());
			ingredientQuantity.setText(String.valueOf(ingredient.getValue()));
			row.addView(ingredientName);
			row.addView(ingredientQuantity);
			tableView.addView(row);
		}

		scrollView.addView(tableView);
		dialogBuilder
				.setView(scrollView)
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						QuantityPickerDialog.newInstance(recipe).show(
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
