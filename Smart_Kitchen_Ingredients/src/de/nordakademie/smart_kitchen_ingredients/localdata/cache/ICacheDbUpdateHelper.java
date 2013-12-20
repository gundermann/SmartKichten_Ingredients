package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

import java.util.List;
import java.util.Map;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public interface ICacheDbUpdateHelper {

	/**
	 * Aktualisiert die lokale Rezeptdatenbank auf Basis der übergebenen Rezepte.
	 * KEY: String Array - Value: List of String Array KEY/rezept 0=id 1=titel
	 * VALUE/zutaten 0=id 1=titel 2=einheit 3=menge
	 * 
	 * @param recipes
	 */
	public List<IRecipe> insertOrUpdateAllRecipesFromServer(
			Map<String[], List<String[]>> recipes);

	/**
	 * Aktualisiert die lokale Zutatendatenbank auf Basis der übergebenen Zutaten.
	 * @param ingredients
	 */
	public List<IIngredient> insertOrUpdateAllIngredientsFromServer(
			List<String[]> ingredients);
	/**
	 * Information ob Zutat auf Server bereits existiert
	 * @param itemTitle
	 * @return boolean
	 */
	public boolean itemExists(String itemTitle);

}
