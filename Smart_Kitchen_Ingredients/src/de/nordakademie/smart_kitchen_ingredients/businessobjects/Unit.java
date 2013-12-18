package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public enum Unit {

	Gramm, Kilogramm, Milliliter, Liter, Stueck;

	public String getUnitShortening(Unit unit) {
		String shortUnit = "";
		switch (unit) {
		case Gramm:
			shortUnit = "g";
			break;
		case Kilogramm:
			shortUnit = "kg";
			break;
		case Milliliter:
			shortUnit = "ml";
			break;
		case Liter:
			shortUnit = "l";
			break;
		case Stueck:
			shortUnit = "stk";
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

	public int getDefaultMinimum() {
		int defaultMinimun = 0;
		switch (this) {
		case Gramm:
			defaultMinimun = 100;
			break;
		case Kilogramm:
			defaultMinimun = 2;
			break;
		case Milliliter:
			defaultMinimun = 300;
			break;
		case Liter:
			defaultMinimun = 3;
			break;
		case Stueck:
			defaultMinimun = 5;
			break;
		default:
			break;
		}
		return defaultMinimun;
	}

	public static Unit valueOfFromShortening(String unitString) {
		if (unitString.equals("g")) {
			return Gramm;
		} else if (unitString.equals("kg")) {
			return Kilogramm;
		} else if (unitString.equals("ml")) {
			return Milliliter;
		} else if (unitString.equals("l")) {
			return Liter;
		} else if (unitString.equals("stk")) {
			return Stueck;
		} else {
			return Unit.valueOf(unitString);
		}
	}
}
