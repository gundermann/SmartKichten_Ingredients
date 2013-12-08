package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ServerIngredientImpl implements ServerIngredient {

	private String title;
	private Unit unit;

	public ServerIngredientImpl() {
	}

	public ServerIngredientImpl(String title, Unit unit) {
		this.title = title;
		this.unit = unit;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Unit getUnit() {
		return unit;
	}

	@Override
	public int compareTo(ServerIngredient another) {
		return title.compareTo(another.getTitle());
	}

}
