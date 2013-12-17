package de.nordakademie.smart_kitchen_ingredients.businessobjects;

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
