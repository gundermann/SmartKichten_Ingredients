package de.nordakademie.smart_kitchen_ingredients.scheduling;


public class Date implements IDate {

	private int intentFlag;
	private long timestamp;
	private String title;

	public Date(String title, long timestamp, int intentFlag) {
		this.title = title;
		this.timestamp = timestamp;
		this.intentFlag = intentFlag;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getIntentFlag() {
		return intentFlag;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

}
