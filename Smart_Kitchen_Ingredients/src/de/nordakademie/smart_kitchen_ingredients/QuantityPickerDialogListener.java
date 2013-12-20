/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;

/**
 * @author Frederic Oppermann
 * @date 17.12.2013
 */
public interface QuantityPickerDialogListener {
	void onPositiveFinishedDialog(IListElement element, int quantity);
}
