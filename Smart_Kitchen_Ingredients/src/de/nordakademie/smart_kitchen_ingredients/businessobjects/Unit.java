package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public enum Unit {

	Gramm, Kilogramm, Milliliter, Liter, Stueck;

	private static final int DEFAULT_MINIMUM_PIECE = 5;
	private static final int LITRE = 3;
	private static final int DEFAULT_MINIMUM_MILILITRE = 300;
	private static final int DEFAULT_MINIMUM_KILOGRAMM = 2;
	private static final int DEFAULT_MINIMUM_GRAMM = 100;
	private static final String SHORT_PIECE = "stk";
	private static final String SHORT_LITRES = "l";
	private static final String SHORT_MILILITRES = "ml";
	private static final String SHORT_KILOGRAMM = "kg";
	private static final String SHORT_GRAMM = "g";

	public String getUnitShortening(Unit unit) {
		String shortUnit = "";
		switch (unit) {
		case Gramm:
			shortUnit = SHORT_GRAMM;
			break;
		case Kilogramm:
			shortUnit = SHORT_KILOGRAMM;
			break;
		case Milliliter:
			shortUnit = SHORT_MILILITRES;
			break;
		case Liter:
			shortUnit = SHORT_LITRES;
			break;
		case Stueck:
			shortUnit = SHORT_PIECE;
			break;
		default:
			break;
		}
		return shortUnit;
	}

	@Override
	public String toString() {
		return getUnitShortening(this);
	}

	public String toLongString() {
		String unitString = this.toString();
		if (unitString.equals(SHORT_GRAMM)) {
			return "Gramm";
		} else if (unitString.equals(SHORT_KILOGRAMM)) {
			return "Kilogramm";
		} else if (unitString.equals(SHORT_MILILITRES)) {
			return "Milliliter";
		} else if (unitString.equals(SHORT_LITRES)) {
			return "Liter";
		} else if (unitString.equals(SHORT_PIECE)) {
			return "Stueck";
		} else {
			return unitString;
		}
	}

	public int getDefaultMinimum() {
		int defaultMinimun = 0;
		switch (this) {
		case Gramm:
			defaultMinimun = DEFAULT_MINIMUM_GRAMM;
			break;
		case Kilogramm:
			defaultMinimun = DEFAULT_MINIMUM_KILOGRAMM;
			break;
		case Milliliter:
			defaultMinimun = DEFAULT_MINIMUM_MILILITRE;
			break;
		case Liter:
			defaultMinimun = LITRE;
			break;
		case Stueck:
			defaultMinimun = DEFAULT_MINIMUM_PIECE;
			break;
		default:
			break;
		}
		return defaultMinimun;
	}

	public static Unit valueOfFromShortening(String unitString) {
		if (unitString.equals(SHORT_GRAMM)) {
			return Gramm;
		} else if (unitString.equals(SHORT_KILOGRAMM)) {
			return Kilogramm;
		} else if (unitString.equals(SHORT_MILILITRES)) {
			return Milliliter;
		} else if (unitString.equals(SHORT_LITRES)) {
			return Liter;
		} else if (unitString.equals(SHORT_PIECE)) {
			return Stueck;
		} else {
			return Unit.valueOf(unitString);
		}
	}

}
