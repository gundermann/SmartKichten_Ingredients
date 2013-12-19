package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class Date implements IDate, Comparable<IDate> {

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

	@Override
	public int compareTo(IDate another) {
		if (timestamp > another.getTimestamp()) {
			return 1;
		} else if (timestamp == another.getTimestamp()) {
			return 0;
		} else {
			return -1;
		}
	}

}
