/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;

/**
 * @author frederic.oppermann
 * @date 17.12.2013
 * @description
 */
public interface QuantityPickerDialogListener {
	void onPositiveFinishedDialog(IListElement element, int quantity);
}
