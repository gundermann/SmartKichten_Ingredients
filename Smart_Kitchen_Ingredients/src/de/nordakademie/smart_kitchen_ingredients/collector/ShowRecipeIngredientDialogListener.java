/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients.collector;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

/**
 * @author frederic.oppermann
 * @date 18.12.2013
 * @description
 */
public interface ShowRecipeIngredientDialogListener {
	void onPositiveFinishedDialog(IRecipe recipe);
}
