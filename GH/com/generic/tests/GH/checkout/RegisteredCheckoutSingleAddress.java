package com.generic.tests.GH.checkout;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import com.generic.page.CheckOut;
import com.generic.page.Registration;
import com.generic.setup.ExceptionMsg;
import com.generic.setup.GlobalVariables;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.util.RandomUtilities;

public class RegisteredCheckoutSingleAddress extends SelTestCase {
	
	
	public static void startTest(int productsCount, LinkedHashMap<String, String> addressDetails,
			LinkedHashMap<String, String> paymentDetails, LinkedHashMap<String, String> userDetalis) throws Exception {

		try {
			getCurrentFunctionName(true);

			String orderSubTotal;
			String orderTax;
			String orderShipping;
			
			String fName = "FirstVisa";
			String lName = "LastVisa";
			String userMail = RandomUtilities.getRandomEmail();
			String userPassword = "TestITG226";

			int productsCountStepTWO=0;

			//Perform Registration
			Registration.registerFreshUser(userMail, userPassword, fName, lName);
			
			// Add products to cart
			CheckOut.addRandomProductTocart(productsCount);

			// Navigating to Cart by URL
			CheckOut.navigatetoCart();
			
			// Clicking begin secure checkout
			CheckOut.clickBeginSecureCheckoutButton();
			

			if (!CheckOut.checkIfInStepTwo()) {
				// Proceed to step 2
				CheckOut.proceedToStepTwo();
			}

			// Check number of products in step 2
			sassert().assertTrue(CheckOut.checkProductsinStepTwo() == productsCount, "Some products are missing in step 2 ");
			
			productsCountStepTWO =CheckOut.checkProductsinStepTwo();
			
			// Proceed to step 3
			CheckOut.proceedToStepThree();

			// Proceed to step 4
			CheckOut.proceedToStepFour();
			
			Thread.sleep(3500);

			// Fill payment details in the last step
			CheckOut.fillPayment(paymentDetails);

			// Saving tax and shipping costs to compare them in the confirmation page
			orderShipping = CheckOut.getShippingCosts();
			orderTax = CheckOut.getTaxCosts(GlobalVariables.FG_TAX_CART);
			orderSubTotal = CheckOut.getSubTotal();

			logs.debug(MessageFormat.format(LoggingMsg.SEL_TEXT, "Shippping cost is: " + orderShipping + " ---- Tax cost is:" + orderTax + " ---- Subtotal is:" + orderSubTotal));
			// Click place order button
			Thread.sleep(1500);

			CheckOut.placeOrder();
			
			Thread.sleep(1500);
			
			CheckOut.closePromotionalModal();

			CheckOut.checkOrderValues(productsCountStepTWO, orderShipping, orderTax, orderSubTotal);

			CheckOut.printOrderIDtoLogs();

			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

}
