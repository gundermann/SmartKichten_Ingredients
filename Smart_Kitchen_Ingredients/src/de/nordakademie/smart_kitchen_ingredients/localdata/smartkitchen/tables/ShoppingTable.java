package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables;
/**
 * @author Niels Gundermann
 */
import android.content.ContentValues;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;

public class ShoppingTable {

	public static final String TABLE_NAME = "shopping_table";
	public static final String ID = "id";
	public static final String NAME = "ingredient";
	public static final String QUANTITY = "quantity";
	public static final String UNIT = "unit";
	public static final String BOUGHT = "bought";
	public static final String SHOPPING_LIST = "shopping_list";

	public static String getDrop() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
		return sb.toString();
	}

	public static String getTableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(TABLE_NAME).append(" (").append(ID)
				.append(" integer primary key autoincrement, ").append(NAME)
				.append(" text, ").append(QUANTITY).append(" interger, ")
				.append(UNIT).append(" text, ").append(BOUGHT)
				.append(" text, ").append(SHOPPING_LIST).append(" text)");
		return sb.toString();
	}

	public static String[] selectAllButId() {
		return new String[] { NAME, QUANTITY, UNIT, BOUGHT };
	}

	public static ContentValues getContentValuesForQuantityBought(int quantity,
			boolean bought) {
		ContentValues values = new ContentValues();
		values.put(BOUGHT, String.valueOf(bought));
		values.put(QUANTITY, quantity);

		return values;
	}

	public static ContentValues getContentValuesForAllButId(
			IShoppingListItem shoppingItem, String shoppingList) {
		ContentValues values = getContentValuesForQuantityBought(
				shoppingItem.getQuantity(), shoppingItem.isBought());
		values.put(NAME, shoppingItem.getName());
		values.put(UNIT, shoppingItem.getUnit().toString());
		values.put(SHOPPING_LIST, shoppingList);

		return values;
	}

	public static String[] selectQuantity() {
		return new String[] { QUANTITY };
	}

	public static String[] selectNameAndShoppinglist() {
		return new String[] { NAME, SHOPPING_LIST };
	}

}
