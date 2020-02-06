package com.generic.tests.BD.PDP;

import com.generic.page.HomePage;
import com.generic.page.PDP;
import com.generic.page.PDP_BD;
import com.generic.setup.SelTestCase;

public class PDP_VK extends SelTestCase{
	public static void validate() throws Exception {
		getCurrentFunctionName(true);
		String searchTerm = "#MO263";
		PDP.NavigateToPDP(searchTerm);
	    Boolean bundle = PDP.getNumberOfItems() > 1;
			String ProductID = "";
			if (!isMobile() && bundle) 
				ProductID = PDP.getProductID(0);
				
			if(bundle) {
			PDP.selectQuantity(true, ProductID);
			PDP_BD.BDselectSwatches(bundle, ProductID);
			Thread.sleep(3500);
			PDP_BD.clickOnConfigureBtn(ProductID);
			Thread.sleep(1500);
			PDP_BD.BDSelectItemsConfigurations(bundle);
            PDP_BD.clickOnConfigureModalDoneBtn();
			}else {
				PDP.selectQuantity(false, ProductID);
				PDP_BD.BDSelectItemsConfigurations(bundle);

			}
		

		
        String priceErrorMessage;
		if (!bundle) {
			priceErrorMessage = "Top price is not dispayed";
		} else if (!isMobile() && bundle) {
			sassert().assertTrue(PDP.validateBundlePriceIsDisplayed(), "Bundle Price is not dispayed");
			priceErrorMessage = "Top price for the bundle items are not dispayed";
		} else {
			priceErrorMessage = "Price for the bundle items are not dispayed";
		}
		sassert().assertTrue(PDP_BD.validatePriceIsDisplayed(), priceErrorMessage);

	
		Thread.sleep(2500);
		sassert().assertTrue(PDP.validateAddToWLGRIsEnabled(bundle, ProductID), "Add to WL/GR button is not enabled");
		sassert().assertTrue(PDP.validateAddToCartIsEnabled(bundle, ProductID), "Add to Cart button is not enabled");
	
		String bottomPrice ;
	    bottomPrice = PDP.getBottomPrice(bundle, ProductID);
		sassert().assertTrue(!bottomPrice.equals("$0.00"),
				"Bottom price is not updated correctly, Current price: " + bottomPrice);
		Thread.sleep(2500);
		PDP.clickAddToCartButton();
		sassert().assertTrue(PDP.validateProductIsAddedToCart(), "Product is not added successfully");

		getCurrentFunctionName(false);
	}
	
	

}