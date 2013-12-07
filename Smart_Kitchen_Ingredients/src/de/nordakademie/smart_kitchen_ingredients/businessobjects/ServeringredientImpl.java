package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ServeringredientImpl implements Serveringredient {

	protected static String title;
	static Unit unit;

	public ServeringredientImpl(String title, Unit unit) {
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

}
