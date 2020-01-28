package com.generic.page;

import java.text.MessageFormat;
import java.util.NoSuchElementException;
import com.generic.selector.PayPalSelectors;
import com.generic.setup.SelTestCase;
import com.generic.setup.ExceptionMsg;
import com.generic.util.SelectorUtil;

public class PayPal extends SelTestCase {

	// CBI
	public static void signIn(String userName, String Password) throws Exception {
		try {
			getCurrentFunctionName(true);
			Thread.sleep(3000);
			typeUserName(userName);
			Thread.sleep(3000);
			clickNext();
			Thread.sleep(3000);
			typePassword(Password);
			Thread.sleep(3000);
			clickLogin();
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Sign in to paypal failed, a selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// CBI
	private static void clickNext() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("clickign on next btn");
			SelectorUtil.initializeSelectorsAndDoActions(PayPalSelectors.nextBtn);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PayPal next button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));

		}

	}

	// CBI
	private static void clickLogin() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("clickign on login btn");
			SelectorUtil.initializeSelectorsAndDoActions(PayPalSelectors.loginBtn);

			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PayPal login button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	// CBI
	public static void clickOnContinue() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("clickign on continue btn");
				SelectorUtil.initializeSelectorsAndDoActions(PayPalSelectors.continueBtn);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PayPal continue button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	/*public static void GuestclickOnContinue() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("clickign on continue btn");
				SelectorUtil.initializeSelectorsAndDoActions(PayPalSelectors.GuestcontinueBtn);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PayPal continue button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}*/
	public static boolean isPayPalShipToPageDisplayed() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean	isDisplayed = SelectorUtil.isDisplayed(PayPalSelectors.continueBtn);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PayPal shipping address selector was not found by selenium ",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
/*	public static boolean GuestisPayPalShipToPageDisplayed() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean	isDisplayed = SelectorUtil.isDisplayed(PayPalSelectors.GuestcontinueBtn);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PayPal shipping address selector was not found by selenium ",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}*/

	// CBI
	private static void typePassword(String Password) throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("type password");
			SelectorUtil.initializeSelectorsAndDoActions(PayPalSelectors.password, Password);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PayPal continue button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	// CBI
	private static void typeUserName(String userName) throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("type username");
			SelectorUtil.initializeSelectorsAndDoActions(PayPalSelectors.userName, userName);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PayPal username field selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

}
