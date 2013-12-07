package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingListItemImpl extends IngredientImpl implements
		ShoppingListItem {

	private boolean bought;

	public ShoppingListItemImpl(boolean bought, Ingredient ingredient) {
		super(ingredient.getAmount(), ingredient.getTitle(), ingredient
				.getUnit());
		this.bought = bought;
	}

	@Override
	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}

}
