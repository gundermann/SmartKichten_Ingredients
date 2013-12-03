package de.nordakademie.smart_kitchen_ingredients;

public class ShoppingItem {

	private double id;
	private String title;
	private boolean buyed;

	public ShoppingItem(double id, String title, boolean buyed) {
		this.id = id;
		this.title = title;
		this.buyed = buyed;
	}

	public boolean isBuyed() {
		return buyed;
	}

	public void setBuyed(boolean buyed) {
		this.buyed = buyed;
	}

	public double getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

}
