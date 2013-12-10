package de.nordakademie.smart_kitchen_ingredients.onlinedata;

public class BarcodeServerHandler implements IBarcodeServerHandler {

	private IBarcodeServerConnector connector;

	public BarcodeServerHandler(IBarcodeServerConnector connector) {
		this.connector = connector;
	}

	@Override
	public String getItemDescription(String barcode) {
		String response = connector.getResponseForBarcode(barcode);
		return filterItemDescriptionFromResponse(response);
	}

	private String filterItemDescriptionFromResponse(String response) {
		String frontIdentifyer = "<tr><td>Description</td><td></td><td>";
		String backItentifyer = "</td>";

		String filteredItem = response.substring(response
				.indexOf(frontIdentifyer) + frontIdentifyer.length());
		filteredItem = filteredItem.substring(0,
				filteredItem.indexOf(backItentifyer));

		return filteredItem;
	}

}
