package de.nordakademie.smart_kitchen_ingredients.onlinedata;

import java.util.List;
import java.util.Map;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IServerIngredient;

/**
 * @author Niels Gundermann
 */
public interface IServerHandler {

	/**
	 * Liefert eine Liste von String-Arrays. Inhalt des String-Arrays: 0:
	 * Zutat-ID 1: Title 2: Einheit
	 * 
	 * @return List<String[]>
	 */
	List<String[]> getIngredientListFromServer();

	/**
	 * Liefert eine Map mit einem String-Array als Key und einer Liste von
	 * String-Arrays als Values. Inhalt des Keys: 0: Rezept-ID 1: Rezept-Name.
	 * Inhalt eines Values ist wieder ein String-Array. Inhalt eines
	 * String-Arrays in der Value-List: 0: Zutat-ID 1: Zutat-Title 2: Menge für
	 * die Zutat 3: Einheit der Zutat.
	 * 
	 * @return List<String[]>
	 */
	Map<String[], List<String[]>> getRecipeListFromServer();

	/**
	 * Sendet eine neues Rezept an den Server
	 * 
	 * @param ingredient
	 */
	void postIngredientToServer(IServerIngredient ingredient);

}
