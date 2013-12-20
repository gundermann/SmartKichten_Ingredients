package de.nordakademie.smart_kitchen_ingredients.collector;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

/**
 * @author Frederic Oppermann
 * @date 18.12.2013
 */
public interface ShowRecipeIngredientDialogListener {
	void onPositiveFinishedDialog(IRecipe recipe);
}
