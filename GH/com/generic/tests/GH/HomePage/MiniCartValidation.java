package com.generic.tests.GH.HomePage;

import com.generic.page.HomePage;
import com.generic.page.PDP.*;
import com.generic.setup.SelTestCase;

public class MiniCartValidation extends SelTestCase {
	
	public static void validate() throws Exception {
		String expectedEmptyCartText="empty";
		
		getCurrentFunctionName(true);
		HomePage.clickOnMiniCart();
		Thread.sleep(2000);
		String emptyCartText = HomePage.getMiniCartText();
		sassert().assertTrue(emptyCartText.contains(expectedEmptyCartText), "<font color=#f442cb>expected text is: " + expectedEmptyCartText
		+ "<br>actual text is: " + emptyCartText + " </font>");
		//The mini cart close button is only available on Mobile. there is no close button on Desktop.
		if (isMobile()) {
			HomePage.clickOnMiniCartCloseBtn();	
			sassert().assertTrue(HomePage.validateMiniCartIsClosed(), "Mini cart modal is not closed");
		}
		PDP.NavigateToPDP();
		PDP_cart.addProductsToCart();
		if (!isMobile()) {
			PDP_cart.clickAddToCartCloseBtn();
		}
		
		//Mini cart in iPAd cannot be validated as it redirects to cart page.
		if (isMobile()) {
			HomePage.clickOnMiniCart();
			sassert().assertTrue(HomePage.validateMiniCartProductIsDsiplayed(),"Mini cart items is not displayed");
			sassert().assertTrue(HomePage.validateMiniCartCheckoutBtnIsDisplayed(),"Mini cart checkout button is not displayed");
			HomePage.clickOnMiniCartCloseBtn();	
			sassert().assertTrue(HomePage.validateMiniCartIsClosed(), "Mini cart modal is not closed");
		}
		
		
		getCurrentFunctionName(false);
		
	}

}
