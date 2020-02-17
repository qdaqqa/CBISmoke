package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import com.generic.selector.HomePageSelectors;
import com.generic.selector.PDPSelectors;
import com.generic.selector.PLPSelectors;
import com.generic.setup.Common;
import com.generic.setup.ExceptionMsg;
import com.generic.setup.GlobalVariables;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.util.RandomUtilities;
import com.generic.util.SelectorUtil;

public class PDP extends SelTestCase {

	public static class keys {
		public static final String WLRandomName = "Wish list";

	}

	// done - SMK
	public static String NavigateToPDP(String SearchTerm) throws Exception {
		try {
			getCurrentFunctionName(true);
			if ((isFGGR() && !isiPad()) || ((isRY() && isMobile())) || ((isBD() && isMobile()))) {
				PLP.clickSearchicon();
			}
			String itemName = "";
			// This is to handle iPad behavior for search modal.
			// TODO: to use this process on all brands
			if (isGHRY() && isiPad() || isBD() && isiPad()) {
				PLP.clickSearch(SearchTerm);
				if (SelectorUtil.isElementExist(By.cssSelector(PLPSelectors.PLPPageSelector.get()))) {
					itemName = PLP.pickPLPFirstProduct();
				}
			} else {
				PLP.typeSearch(SearchTerm);
				itemName = PLP.pickRecommendedOption();
			}
			getCurrentFunctionName(false);
			return itemName;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "Navigation to PDP has failed, a selector was not found by selenuim", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	// done - SMK
	public static String getBrandRandomPLP() throws Exception {
		try {
			getCurrentFunctionName(true);
			String BrandURL;
			if (isFG())
				BrandURL = GlobalVariables.randomPLP.FG;
			else if (isGR())
				BrandURL = GlobalVariables.randomPLP.GR;
			else if (isGH())
				BrandURL = GlobalVariables.randomPLP.GH;
			else if (isRY())
				BrandURL = GlobalVariables.randomPLP.RY;
			else
				BrandURL = GlobalVariables.randomPLP.BD;
			getCurrentFunctionName(false);
			return BrandURL;
		} catch (Exception e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed + "Failed to get random PLP", new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	// done - SMK
	public static void NavigateToPDP() throws Exception {
		getCurrentFunctionName(true);
		getDriver().get(getURL() + getBrandRandomPLP());
		PLP.pickPLPRandomProduct();
		getCurrentFunctionName(false);
	}

	/**
	 * Click on add to cart button.
	 *
	 * @throws Exception
	 */
	public static void clickAddToCartButton() throws Exception {
		try {
			getCurrentFunctionName(true);
			String subStrArr = null;
			if (SelTestCase.isFGGR())
				subStrArr = PDPSelectors.addToCartBtn.get();
			if (SelTestCase.isGHRY())
				subStrArr = PDPSelectors.GHRYaddToCartBtn.get();
			if (SelTestCase.isBD())
				subStrArr = PDPSelectors.BDaddToCartBtn.get();

			// Bundle product selector.
			if (!isMobile() && (isBD() || isGH()) && bundleProduct()) {
				if (!isMobile() && bundleProduct()) {
					String ProductID = getProductID(0);
					subStrArr = MessageFormat.format(PDPSelectors.GHAddToCartBtnEnabledBundle, ProductID);
				}
			}
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Add to cart button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void clickAddToCartCloseBtn() throws Exception {
		try {
			getCurrentFunctionName(true);
			String subStrArr = PDPSelectors.addToCartCloseBtn.get();
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Add to cart close button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	/**
	 * Get the number options for GH & RY.
	 *
	 * @throws Exception
	 */
	public static int GHRYNumberOfOptions(Boolean bundle) throws Exception {
		getCurrentFunctionName(true);
		String subStrArr = PDPSelectors.avaibleOptions.get();

		// Bundle product selector.
		if (bundle) {
			String ProductID = getProductID(0);
			subStrArr = MessageFormat.format(PDPSelectors.GHAvailableOptionsBundle.get(), ProductID);
		}

		// Check if options is displayed.
		int numberOfAvaibleOptions = 0;
		if (!SelectorUtil.isNotDisplayed(subStrArr)) {
			numberOfAvaibleOptions = SelectorUtil.getAllElements(subStrArr).size();
		}
		logs.debug("number Of Avaible Options" + numberOfAvaibleOptions);
		getCurrentFunctionName(false);
		return numberOfAvaibleOptions;
	}

// done - SMK
	public static void selectNthListBoxFirstValue(int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str;
			String value;
			if (isBD()) {
				Str = PDPSelectors.BDallSizes.get();
				value = "FFF1";
			} else {
				Str = PDPSelectors.allSizes.get();
				value = "FFF1";
			}
			SelectorUtil.initializeSelectorsAndDoActions(Str, value);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Add to cart close button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void selectNthOptionFirstSwatch(int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String subStrArr;
			if (isBD())
				subStrArr = MessageFormat.format(PDPSelectors.BDfirstSwatchInOptions.get(), index);
			else
				subStrArr = MessageFormat.format(PDPSelectors.firstSwatchInOptions.get(), index);
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, subStrArr));
			String nthSel = subStrArr;
			// Clicking on the div on desktop and iPad does not select the options,
			// you need to click on the img if there is an img tag.
			if (!SelTestCase.isMobile()) {
				String nthSel2;
				if (isBD())
					nthSel2 = subStrArr + " img";
				else
					nthSel2 = subStrArr + ">img";
				if (!SelectorUtil.isNotDisplayed(nthSel2))
					if (isBD())
						nthSel = subStrArr + " img";
					else
						nthSel = subStrArr + ">img";
			}

			SelectorUtil.initializeSelectorsAndDoActions(nthSel);

			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Swatch selector was not found by selenium", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void addProductsToCart() throws Exception {
		try {
			getCurrentFunctionName(true);
			selectSwatches();
			Thread.sleep(2000);
			clickAddToCartButton();
			if (PDP.bundleProduct() && SelTestCase.isMobile()) {
				closeModalforBundleItem();
			}
			Thread.sleep(2000);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "Add products to cart has failed, a selector was not found by selenium", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// Done CBI
	public static void closeModalforBundleItem() throws Exception {
		try {
			getCurrentFunctionName(true);
			SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.closeBundleProductModal.get());
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Close icon for bundle modal selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static boolean validatePriceIsDisplayed(Boolean bundle, String ProductID) throws Exception {
		getCurrentFunctionName(true);
		try {
			boolean isDisplayed;
			logs.debug("Validate if top price exist");
			String selector = null;
			if (isFGGR()) {
				selector = PDPSelectors.topPriceSingle.get();
				if (!isMobile() && bundle) {
					logs.debug(PDPSelectors.topPriceBundle);
					selector = MessageFormat.format(PDPSelectors.topPriceBundle, ProductID);
				}
			} else if (isRY()) {
				selector = PDPSelectors.RYtopPriceSingle.get();
			} else if (isBD()) {
				selector = PDPSelectors.BDtopPriceSingle.get();
			} else {
				selector = PDPSelectors.GHtopPriceSingle.get();
			}
			isDisplayed = SelectorUtil.isDisplayed(selector);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat
					.format(ExceptionMsg.PageFunctionFailed + "Price selector was not found by selenium", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static boolean validateExpiredPDPMsgIsNotDisplayedSinglePDP() throws Exception {
		getCurrentFunctionName(true);
		try {
			boolean isNotDisplayed;
			logs.debug("Validate PDP is sold out");
			String selector = PDPSelectors.expiredPDP.get();
			isNotDisplayed = SelectorUtil.isNotDisplayed(selector);
			getCurrentFunctionName(false);
			return isNotDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PDP Sold out message selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static boolean validateExpiredPDPMsgIsNotDisplayedBundlePDP() throws Exception {
		getCurrentFunctionName(true);
		try {
			boolean isNotDisplayed;
			logs.debug("Validate PDP is sold out");
			String ProductID = getProductID(0);
			logs.debug(PDPSelectors.expiredPDPBundle);
			String selector = MessageFormat.format(PDPSelectors.topPriceBundle, ProductID);
			isNotDisplayed = SelectorUtil.isNotDisplayed(selector);
			getCurrentFunctionName(false);
			return isNotDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PDP Sold out message selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static boolean validateBundlePriceIsDisplayed() throws Exception {
		try {
			getCurrentFunctionName(true);

			boolean isDisplayed;
			logs.debug("Validate if top price exist for Bundle PDP");
			if (isBD())
				isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.BDtopPriceBundleDesktop.get());
			else
				isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.topPriceBundleDesktop.get());

			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PDP Price selector was not found by selenium", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	/**
	 * Check if the add to cart button is enabled.
	 *
	 * @throws Exception
	 */
	public static boolean validateAddToWLGRIsEnabled(Boolean Bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed;
			logs.debug("Validate if Add To WL/GR Is Displayed");
			// here it will pass if the button exist regardless if it is enabled or
			// disabled.
			// because there is no attribute to verify if it is enabled.
			String selectorEnabled;
			if (isFGGR())
				selectorEnabled = PDPSelectors.addToWLGRBtnEnabledSingle.get();
			else if (isRY())
				selectorEnabled = PDPSelectors.RYAddToWLGRBtnEnabledSingle.get();
			else if (isBD())
				selectorEnabled = PDPSelectors.BDaddToWLGRBtnEnabledSingle.get();
			else
				selectorEnabled = PDPSelectors.GHAddToWLGRBtnEnabledSingle.get();
			if (!isMobile() && Bundle) {
				if (isBD())
					selectorEnabled = PDPSelectors.BDaddToWLGRBtnEnabledSingle.get();
				else {
					selectorEnabled = MessageFormat.format(PDPSelectors.addToWLGRBtnEnabledBundle, ProductID);
				}
				logs.debug(PDPSelectors.addToCartBtnDisabledBundle);

				MessageFormat.format(PDPSelectors.addToCartBtnDisabledBundle, ProductID);

				String addToWLGRBtnEnabledBundleSelector;
				if (isGH()) {
					addToWLGRBtnEnabledBundleSelector = PDPSelectors.GHAddToWLGRBtnEnabledBundle;
				} else {
					addToWLGRBtnEnabledBundleSelector = PDPSelectors.addToWLGRBtnEnabledBundle;
				}
				logs.debug(addToWLGRBtnEnabledBundleSelector);
				selectorEnabled = MessageFormat.format(addToWLGRBtnEnabledBundleSelector, ProductID);
			}

			isDisplayed = SelectorUtil.isDisplayed(selectorEnabled);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Add to gift registrey button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static boolean validateAddToCartIsEnabled(Boolean Bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed;
			logs.debug("Validate if Add To Cart Is Displayed");
			// here it will pass if the button exist regardless if it is enabled or
			// disabled.
			// because there is no attribute to verify if it is enabled.
			String selectorEnabled;
			if (isBD())
				selectorEnabled = PDPSelectors.BDaddToCartBtnEnabledSingle.get();
			else if (isFGGR())
				selectorEnabled = PDPSelectors.addToCartBtnEnabledSingle.get();
			else if (isRY())
				selectorEnabled = PDPSelectors.RYAddToCartBtnEnabledSingle.get();
			else
				selectorEnabled = PDPSelectors.GHAddToCartBtnEnabledSingle.get();

			if (!isMobile() && Bundle) {

				if (isBD())
					selectorEnabled = MessageFormat.format(PDPSelectors.BDaddToCartBtnEnabledBundle, ProductID);
				else if (isFGGR())
					selectorEnabled = MessageFormat.format(PDPSelectors.addToCartBtnEnabledBundle, ProductID);
				else
					selectorEnabled = MessageFormat.format(PDPSelectors.addToCartBtnEnabledBundle, ProductID);
				logs.debug(PDPSelectors.addToCartBtnDisabledBundle);
			}
			isDisplayed = SelectorUtil.isDisplayed(selectorEnabled);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Add to cart registrey button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static String getBottomPrice() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("Validate if bottom price is updated after seleting options");
			String selector = PDPSelectors.bottomPriceSingle.get();
			if (isGHRY()) {
				selector = PDPSelectors.GHRYBottomPriceSingle.get();
			} else if (isBD())
				selector = PDPSelectors.BDbottomPriceSingle.get();
			if (!isMobile() && bundleProduct()) {
				String ProductID = getProductID(0);
				if (isBD())
					selector = MessageFormat.format(PDPSelectors.BDbottomPriceBundle, ProductID);
				else
					selector = MessageFormat.format(PDPSelectors.bottomPriceBundle, ProductID);
			}
			SelectorUtil.initializeSelectorsAndDoActions(selector);
			String price = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return price;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Bottom of PDP Price selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;

		}
	}

	// done - SMK

	public static String getBottomPrice(Boolean bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("Validate if bottom price is updated after seleting options");
			String selector;
			if (isBD())
				selector = PDPSelectors.BDbottomPriceSingle.get();
			else if (isGHRY())
				selector = PDPSelectors.GHRYBottomPriceSingle.get();
			else
				selector = PDPSelectors.bottomPriceSingle.get();

			if (bundle) {
				if (isBD()) {
					if (isMobile())
						selector = PDPSelectors.BDbottomPriceBundleMobile.get();
					else
						selector = MessageFormat.format(PDPSelectors.BDbottomPriceBundle, ProductID);

				} else if (isGH()) {
					selector = MessageFormat.format(PDPSelectors.GHBottomPriceBundle.get(), ProductID);
				} else if (!isMobile()) {
					selector = MessageFormat.format(PDPSelectors.bottomPriceBundle, ProductID);
				}
			}
			SelectorUtil.initializeSelectorsAndDoActions(selector);
			String price = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return price;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Bottom of PDP Price selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static boolean validateProductIsAddedToCart() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed;
			// Validate the add to cart modal is displayed for Desktop and iPad.
			// For Mobile, verify it from mini cart because there is no add to cart modal in
			// mobile.
			if (!isMobile()) {
				Thread.sleep(4000);
				isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.addToCartModal.get());
			} else if (isMobile() && (isGHRY() || isBD())) {
				isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.addToCartModal.get());
			} else {
				HomePage.clickOnMiniCart();
				isDisplayed = HomePage.validateMiniCartProductIsDsiplayed();
			}
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Add to cart modal selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void clickAddToWLGR() throws Exception {
		try {
			getCurrentFunctionName(true);
			if (isGH()) {
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.GHaddToWLGRBtnEnabled.get());
			} else if (isRY()) {
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.RYaddToWLGRBtnEnabled.get());
			} else if (isBD()) {
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.BDaddToWLGRBtnEnabledSingle.get());
			} else {
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.addToWLGRBtnEnabled.get());
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Add to wishlistGR button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static String getProductID(int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = PDPSelectors.itemsID.get();
			if (isGH()) {
				if (isMobile()) {
					Thread.sleep(2500);
				}
				Str = PDPSelectors.GHItemsID.get();
			}
			String ID = SelectorUtil.getAttrString(Str, "id", index);
			getCurrentFunctionName(false);
			return ID;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Product ID selector was not found by selenium", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static boolean bundleProduct() throws Exception {
		return bundleProduct(0);
	}

	// done - SMK
	public static boolean bundleProduct(int tries) throws Exception {
		getCurrentFunctionName(true);
		try {
			Thread.sleep(4500);
			String PDPChecker = "return gwtDynamic.coremetrics.isSingleProduct;";
			Boolean bundle = false;
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();

			String value = (String) jse.executeScript(PDPChecker);

			logs.debug("isSingleProduct: " + value);

			if (value.equals("N")) {
				bundle = true;
				logs.debug("This item is bundle");
			} else if (value.equals("Y")) {
				bundle = false;
				logs.debug("This item is not bundle");
			} else {
				if (tries < 10)
					bundleProduct(tries++);
			}

			return bundle;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Executing JavaScript code to know PDP type has failed",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void selectNthOptionFirstSwatchBundle(String Str) throws Exception {
		try {
			getCurrentFunctionName(true);
			// String StrBundle = MessageFormat.format(Str, index);
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, Str));
			SelectorUtil.initializeSelectorsAndDoActions(Str);
			// Clicking on the div on desktop and iPad does not select the options,
			// you need to click on the img if there is an img tag.
			if (!SelTestCase.getBrowserName().contains(GlobalVariables.browsers.iPhone)) {
				String nthSel = Str + ">img";
				if (!SelectorUtil.isNotDisplayed(nthSel))
					SelectorUtil.initializeSelectorsAndDoActions(nthSel);
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "swatch selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void selectNthListBoxFirstValueBundle(String Str, int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String value = "index," + index + ",FFF1";
			SelectorUtil.initializeSelectorsAndDoActions(Str, value);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "listbox item selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void selectSwatches(Boolean bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			if (isFGGR()) {
				if (!isMobile() && bundle) {
					FGGRselectSwatchesBundle(ProductID);

				} else {
					FGGRselectSwatchesSingle();
				}

			} else if (isGHRY()) {
				GHRYselectSwatches(bundle, ProductID);
			}
			else if (isBD()) {
				PDP_BD.BDselectSwatches(bundle, ProductID);
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed + "select swatch was failed", new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void selectSwatches() throws Exception {
		try {
			getCurrentFunctionName(true);
			Boolean bundle = PDP.bundleProduct();
			if (isFGGR() || isBD()) {
				String ProductID = null;
				if (!isMobile() && bundle)
					ProductID = PDP.getProductID(0);
				selectSwatches(bundle, ProductID);
			} else if (isGHRY()) {
				String ProductID = null;
				if (bundle)
					ProductID = PDP.getProductID(0);
				GHRYselectSwatches(bundle, ProductID);
			}

			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "swatch selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// Done SMK
	public static void clickBundleItems() throws Exception {
		try {
			getCurrentFunctionName(true);
			String selector = PDPSelectors.bundleItems.get();
			if (isGH()) {
				selector = PDPSelectors.GHBundleItems.get();
			}
			else if (isBD()) {
				selector = PDPSelectors.BDnumberOfBundleItems.get();
			}
			logs.debug("Clicking on any bundle item");
			if (!SelectorUtil.isNotDisplayed(selector)) {
				SelectorUtil.initializeSelectorsAndDoActions(selector);
			}
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Bundle items selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// Done SMK
	public static boolean validateMobileBundlePriceIsDisplayed() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed;
			logs.debug("Validate if top price exist");
			String selector = PDPSelectors.miniPDPPrice.get();
			isDisplayed = SelectorUtil.isDisplayed(selector);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Bundle price selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// Done SMK
	public static boolean validateSelectRegistryOrWishListModalIsDisplayed() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed;
			logs.debug("Validate select a registry or wish list modal exist");
			isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.SelectRegistryOrWishListModal.get());
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "Modal for selecting gift registrey selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickOnCreateNewWL() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("Click on create new wish list");
			if (isRY()) {
				List<WebElement> elements = SelectorUtil.getAllElements(PDPSelectors.RYcreateNewWL.get());
				SelectorUtil.clickOnWebElement(elements.get(elements.size() - 1));

			} else {
				WebElement element = SelectorUtil.getElement(PDPSelectors.giftRegistryListBox.get());
				WebElement option = element.findElement(By.cssSelector(PDPSelectors.createNewWL.get()));
				option.click();
				clickOnCreateNewWLConfirmationBtn();
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Create wishlsit selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean validateNameYourNewWLModalIsDisplayed() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed = true;
			logs.debug("Validate Name your new wish list modal exist");
			isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.nameYourNewWL.get());
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Name your wishlist mdoal selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void createNewWL(String WLName) throws Exception {
		try {
			getCurrentFunctionName(true);
			SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.WLName.get(), WLName);
			if (isBD())
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.BDnameYourNewWLconfirmationBtn.get());
			else
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.nameYourNewWLconfirmationBtn.get());

			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "create a new wishlist button selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static String getWishListName() {
		int random = (int) (Math.random() * 1000000 + 1);
		String WLName = keys.WLRandomName + random;
		return WLName;
	}

	public static boolean validateCreatedWLisSelectedByDefault(String createdWL) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isSelected = true;
			Select element = new Select(SelectorUtil.getElement(PDPSelectors.giftRegistryListBox.get()));
			WebElement option = element.getFirstSelectedOption();
			String defaultWL = option.getText();
			String selectedWL = defaultWL;
			selectedWL = selectedWL.replaceAll("\"", "");
			if (selectedWL.equals(createdWL)) {
				isSelected = true;
			} else {
				isSelected = false;
			}
			getCurrentFunctionName(false);
			return isSelected;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Gift registries list box selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickOnCreateNewWLConfirmationBtn() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("Click on create new wish list");
			if (isBD())
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.BDcreateNewWLConfirmationBtn.get());
			else
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.createNewWLConfirmationBtn.get());
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "Create a new wishlist button selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean validateConfirmationModalWithCorrectProductIsDisplayed(String selectedProductName)
			throws Exception {

		try {
			getCurrentFunctionName(true);

			boolean isDisplayed;
			logs.debug("Validate  if confirmation modal exists");

			try {
				Thread.sleep(2500);

				isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.confirmationModal.get());
				logs.debug("Validate confirmation modal exist menu" + isDisplayed + "   " + selectedProductName);

				WebElement addToCardProductElement = SelectorUtil.getElement(PDPSelectors.addToCardProductName.get());
				String viewListBtnSelector;
				if (isGH() || isRY() || isBD()) {
					viewListBtnSelector = PDPSelectors.GHRYviewListBtn.get();
				} else {
					viewListBtnSelector = PDPSelectors.viewListBtn.get();
				}

				if (addToCardProductElement.getText().equals(selectedProductName))
					logs.debug("Product is the right added one");

				SelectorUtil.initializeSelectorsAndDoActions(viewListBtnSelector);
				getCurrentFunctionName(false);

				return isDisplayed;

			} catch (Exception e) {
				logs.debug("Validate confirmation modal failed ");
				return false;

			}

		} catch (Exception e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Added to wishlist modal selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;

		}
	}

	public static boolean addedProductIsDisplayedInTheWL(String addedProductName) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed = true;
			Thread.sleep(2000);
			List<WebElement> products = SelectorUtil.getElementsList(PDPSelectors.addedProductName.get());
			List<WebElement> addToCartBtns = new ArrayList<WebElement>();
			if (isGH() || isBD()) {
				addToCartBtns = SelectorUtil.getElementsList(PDPSelectors.GHmyWLAddToCartBtn.get());
			} else if (isRY()) {
				addToCartBtns = SelectorUtil.getElementsList(PDPSelectors.RYmyWLAddToCartBtn.get());
			} else {
				addToCartBtns = SelectorUtil.getElementsList(PDPSelectors.myWLAddToCartBtn.get());
			}

			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).getText().toLowerCase().contains(addedProductName.toLowerCase())) {
					SelectorUtil.clickOnWebElement(addToCartBtns.get(i));
					return true;
				}
			}
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "added product to wishlist validation has faleid, a selector is missing", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickOnCheckout() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("Clicking on Checkout");
			SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.addToCartConfirmationModalCheckoutBtn.get());
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed
							+ "checkout button in added to cart confirmation selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean validateAddToCartModalIsDisplayed() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed;
			logs.debug("Validate add to cart confirmation modal displayed");
			isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.addToCartConfirmationModal.get());
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "checkout added to cart confirmation selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean addedProductIsDisplayedInShoppingCart(String addedProductName) throws Exception {
		try {
			getCurrentFunctionName(true);
			Thread.sleep(3500);
			boolean isDisplayed = true;
			List<WebElement> products = SelectorUtil.getElementsList(PDPSelectors.shoppingCartProductsName.get());

			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).getText().contains(addedProductName)) {
					return true;
				}
			}
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Product in shopping cart selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-CBI
	public static String getTitle() throws Exception {
		try {
			getCurrentFunctionName(true);
			if (isGHRY()) {
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.titleGH.get());

			} else {
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.title.get());
			}
			getCurrentFunctionName(false);
			return SelectorUtil.textValue.get();
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "PDP tittle selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done-CBI
	public static String getImageSrcID() throws Exception {
		try {
			getCurrentFunctionName(true);
			WebElement product = SelectorUtil.getElement(PDPSelectors.imgID.get());

			String imgString = product.getAttribute("src");
			String imgID = imgString.substring(imgString.indexOf("Ryllace") + 8, imgString.indexOf("Ryllace") + 13);

			getCurrentFunctionName(false);
			return imgID;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	@SuppressWarnings("deprecation")
	public static void hoverMiniCart() throws Exception {
		try {
			getCurrentFunctionName(true);
			Wait<WebDriver> wait = new FluentWait<WebDriver>(SelTestCase.getDriver()).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

			WebElement field = getDriver()
					.findElement(By.cssSelector(HomePageSelectors.miniCartBtn.get().replace("css,", "")));

			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView(false)", field);
			Thread.sleep(200);
			WebElement field2 = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(By.cssSelector(HomePageSelectors.miniCartBtn.get().replace("css,", "")));
				}
			});

			Actions HoverAction = new Actions(getDriver());
			HoverAction.moveToElement(field2).click().build().perform();
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Mini cart button selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// Done SMK
	public static boolean PersonalizedItem(Boolean Bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed = false;
			if (isGH()) {

				isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.personalizationIdentiferGH.get());
				getCurrentFunctionName(false);
				logs.debug("PDP is personalizable result is:" + isDisplayed);
				return isDisplayed;

			} else {
				String addPersonalizedButtonSelector = PDPSelectors.addPersonalizedButton.get();
				if (isBD())
					addPersonalizedButtonSelector = PDPSelectors.BDaddPersonalizedButton.get();

				if (!isMobile() && Bundle) {
					if (isBD())
						addPersonalizedButtonSelector = "css,#" + ProductID + " "
								+ PDPSelectors.BDaddPersonalizedButton.get().replace("css,", "");
					else {
						addPersonalizedButtonSelector = "css,#" + ProductID + ">"
								+ PDPSelectors.addPersonalizedButton.get().replace("css,", "");
						logs.debug("addPersonalizedButtonSelector:  " + addPersonalizedButtonSelector);
					}
				}
				isDisplayed = SelectorUtil.isDisplayed(addPersonalizedButtonSelector);
				getCurrentFunctionName(false);
				return isDisplayed;
			}
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void clickAddPersonalizationButton(Boolean Bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			String addPersonalizedButtonSelector = PDPSelectors.addPersonalizedButton.get();
			if (isGH())
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.addPersonalizedButtonGH.get());
			if (isBD())
				addPersonalizedButtonSelector = PDPSelectors.BDaddPersonalizedButton.get();

			if (!isMobile() && Bundle) {
				if (isBD())
					addPersonalizedButtonSelector = "css,#" + ProductID + " "
							+ PDPSelectors.BDaddPersonalizedButton.get().replace("css,", "");
				else {
					addPersonalizedButtonSelector = "css,#" + ProductID + ">"
							+ PDPSelectors.addPersonalizedButton.get().replace("css,", "");
					logs.debug("addPersonalizedButtonSelector:  " + addPersonalizedButtonSelector);
				}
			}

			SelectorUtil.initializeSelectorsAndDoActions(addPersonalizedButtonSelector);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Personalization button selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean isFreePersonalization(Boolean Bundle, String ProductID) throws Exception {// check if add
																									// personalization
																									// free or not
		try {
			getCurrentFunctionName(true);
			boolean isFree = true;
			String addPersonalizedButtonSelector = PDPSelectors.personlizedTitle.get();// for iPhone
			if (isBD())
				addPersonalizedButtonSelector = PDPSelectors.BDpersonlizedTitle.get();
			if (!isMobile()) {
				addPersonalizedButtonSelector = PDPSelectors.addPersonalizedButton.get();// for single PDP
				if (isBD())
					addPersonalizedButtonSelector = PDPSelectors.BDaddPersonalizedButton.get();// for single PDP
				if (Bundle) {// for bundle PDP
					if (isBD())
						addPersonalizedButtonSelector = "css,#" + ProductID + " "
								+ PDPSelectors.BDaddPersonalizedButton.get().replace("css,", "");
					else
						addPersonalizedButtonSelector = "css,#" + ProductID + ">"
								+ PDPSelectors.addPersonalizedButton.get().replace("css,", "");

				}
			}
			WebElement element = SelectorUtil.getElement(addPersonalizedButtonSelector);
			String personalizationText = element.getText().toLowerCase();
			logs.debug("personalizationText:  " + personalizationText);

			if (!personalizationText.contains("free")) {
				isFree = false;
			}
			logs.debug("isFreePersonalization: " + isFree);
			getCurrentFunctionName(false);
			return isFree;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Personalization Price check has failed, a selector is missing",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickPersonalizationSaveAndCloseButton() throws Exception {
		try {
			getCurrentFunctionName(true);
			String subStrArr = PDPSelectors.personalizationSaveAndCloseButton.get();
			if (isGH()) {
				subStrArr = PDPSelectors.GHPersonalizationSaveAndCloseButton.get();
			} else if (isBD()) {
				subStrArr = PDPSelectors.BDpersonalizationSaveAndCloseButton.get();
			}
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "Personalization  save button selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickPersonalizationSaveAndCloseButtonForiPhone() throws Exception {
		try {
			getCurrentFunctionName(true);
			List<WebElement> elementsList = SelectorUtil.getAllElements(PDPSelectors.personalizedItems.get());
			WebElement element = elementsList.get(elementsList.size() - 1);
			SelectorUtil.clickOnWebElement(element);
			clickPersonalizationSaveAndCloseButton();
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Personalization save button selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean isPersonalizedStyle() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.personlizedStyleItem.get());
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isPersonalizedInputSwatchesDisplayed(String value) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed = SelectorUtil.isDisplayed(value);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void selectPersonalizationModalSwatchesForiPhone() throws Exception {
		try {
			getCurrentFunctionName(true);
			closeOpendItem();
			List<WebElement> elementsList = SelectorUtil.getAllElements(PDPSelectors.personalizedItems.get());
			if (isBD())
				elementsList = SelectorUtil.getAllElements(PDPSelectors.BDpersonalizedItems.get());
			for (int i = 0; i < elementsList.size() - 1; i++) {
				WebElement element = elementsList.get(i);
				SelectorUtil.clickOnWebElement(element);
				String personalizedInputValue = PDPSelectors.personalizedInputValue.get();
				String personalizedItemColors1 = PDPSelectors.personalizedItemColors1.get();

				if (isBD()) {
					personalizedInputValue = PDPSelectors.BDpersonalizedInputValue.get();
				}
				if (isPersonalizedInputSwatchesDisplayed(personalizedInputValue)) {// input container
																					// like MONOGRAM
																					// or any value
					WebElement input = SelectorUtil.getElement(personalizedInputValue);
					input.sendKeys(RandomUtilities.getRandomStringWithLength(3));
				} else if (isPersonalizedInputSwatchesDisplayed(personalizedItemColors1)) { // like
																							// item
																							// color
					List<WebElement> itemColors = SelectorUtil.getAllElements(personalizedItemColors1);
					if (itemColors.size() > 0) {
						WebElement firstItemColor = itemColors.get(0);
						SelectorUtil.clickOnWebElement(firstItemColor);
					}
				} else if (isPersonalizedInputSwatchesDisplayed(PDPSelectors.personalizedItemMenu.get())) {// like item
																											// size
					WebElement menu = SelectorUtil.getElement(PDPSelectors.personalizedItemMenu.get());
					List<WebElement> options = menu
							.findElements(By.cssSelector(PDPSelectors.personalizedMenuOptions.get()));
					options.get(1).click();// the first item is selected size
				}
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed
							+ "Selecting swatches for personalized product selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void selectPersonalizationModalSwatches() throws Exception {
		try {
			getCurrentFunctionName(true);
			closeOpendItem();
			List<WebElement> elementsList = new ArrayList<WebElement>();
			if (isBD())
				elementsList = SelectorUtil.getAllElements(PDPSelectors.BDpersonalizedItems.get());
			else
				elementsList = SelectorUtil.getAllElements(PDPSelectors.personalizedItems.get());

			for (int i = 0; i < elementsList.size(); i++) {
				WebElement element = elementsList.get(i);
				if (!isBD())
					SelectorUtil.clickOnWebElement(element);

				String inputSwatches = PDPSelectors.personalizedInputValue.get();
				;
				String personalizedItemColor2 = PDPSelectors.personalizedItemColors2.get();
				;

				if (isBD()) {
					inputSwatches = PDPSelectors.BDpersonalizedInputValue.get();
					personalizedItemColor2 = PDPSelectors.BDpersonalizedItemColors2.get();
				}

				if (isPersonalizedInputSwatchesDisplayed(inputSwatches)) {// input container
																			// like MONOGRAM // or any value
					WebElement input = SelectorUtil.getElement(inputSwatches);
					input.sendKeys(RandomUtilities.getRandomStringWithLength(3));
				} else if (isPersonalizedInputSwatchesDisplayed(PDPSelectors.personalizedItemColors1.get())) { // like
																												// item
																												// color
					List<WebElement> itemColors = SelectorUtil
							.getAllElements(PDPSelectors.personalizedItemColors1.get());
					if (itemColors.size() > 0) {
						WebElement firstItemColor = itemColors.get(0);
						SelectorUtil.clickOnWebElement(firstItemColor);
					}
				} else if (isPersonalizedInputSwatchesDisplayed(personalizedItemColor2)) { // like
																							// thread
																							// color
					List<WebElement> itemColors = SelectorUtil.getAllElements(personalizedItemColor2);
					if (itemColors.size() > 0) {
						WebElement firstItemColor = itemColors.get(0);
						SelectorUtil.clickOnWebElement(firstItemColor);
					}

				} else if (isPersonalizedInputSwatchesDisplayed(PDPSelectors.personalizedTypeFaces.get())) {// like
																											// TypeFace
																											// , English
																											// style ,
																											// etching
																											// style
																											// (Roman
					List<WebElement> items = SelectorUtil.getAllElements(PDPSelectors.personalizedTypeFaces.get());
					if (items.size() > 0) {
						WebElement firstItemColor = items.get(0);
						SelectorUtil.clickOnWebElement(firstItemColor);
					}
				} else if (isPersonalizedInputSwatchesDisplayed(PDPSelectors.personalizedItemMenu.get())) {// like item
																											// size
					WebElement menu = SelectorUtil.getElement(PDPSelectors.personalizedItemMenu.get());
					List<WebElement> options = menu
							.findElements(By.cssSelector(PDPSelectors.personalizedMenuOptions.get()));
					options.get(1).click();// the first item is selected size
				}
				// Thread.sleep(1000);
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void closeOpendItem() throws Exception {
		try {
			getCurrentFunctionName(true);
			WebElement e = SelectorUtil.getElement(PDPSelectors.personalizedOpenItem.get());
			SelectorUtil.clickOnWebElement(e);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
		}

	}

	public static boolean validateTotalPriceAfterAddedPersonalized(String intialPrice, String finalPrice)
			throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isChanged = true;
			if (intialPrice.equals(finalPrice)) {
				isChanged = false;
			}
			getCurrentFunctionName(false);
			return isChanged;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean validateAddedPersonalizedDetails(Boolean Bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isAdded = true;
			String addedPersonlizedDetailsSelector = PDPSelectors.addedPersonlizedDetails.get();
			if (isGH()) {
				addedPersonlizedDetailsSelector = PDPSelectors.GHAddedPersonlizedDetails.get();
			} else if (isBD()) {
				addedPersonlizedDetailsSelector = PDPSelectors.BDaddedPersonlizedDetails.get();
			}
			if (!isMobile() && Bundle) {
				if (isBD())
					addedPersonlizedDetailsSelector = "css,#" + ProductID + " "
							+ addedPersonlizedDetailsSelector.replace("css,", "");
				else
					addedPersonlizedDetailsSelector = "css,#" + ProductID + ">"
							+ PDPSelectors.addedPersonlizedDetails.get().replace("css,", "");
			}
			List<WebElement> addedPersonlizedDetailsItems = SelectorUtil
					.getAllElements(addedPersonlizedDetailsSelector);
			if (addedPersonlizedDetailsItems.size() <= 0) {
				isAdded = false;
			}
			getCurrentFunctionName(false);

			return isAdded;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Personalization details selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean validatePersonalizedModal() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed = true;
			isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.personlizedModal.get());
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Personalization modal selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static String getTotalPriceAfterAddedPersonalized(Boolean Bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			String addedPersonlizedDetailsSelector = PDPSelectors.addedPersonlizedDetails.get();
			if (!isMobile() && Bundle) {
				addedPersonlizedDetailsSelector = "css,#" + ProductID + ">"
						+ PDPSelectors.addedPersonlizedDetails.get().replace("css,", "");
			}
			List<WebElement> addedPersonlizedDetailsItems = SelectorUtil
					.getAllElements(addedPersonlizedDetailsSelector);
			WebElement totalPriceElement = addedPersonlizedDetailsItems.get(addedPersonlizedDetailsItems.size() - 1);
			String totalPrice = totalPriceElement.getText();
			logs.debug("totalPriceElement:  " + totalPrice);
			getCurrentFunctionName(false);
			return totalPrice;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Personalization details selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void GHRYselectColor(Boolean bundle, String ProductID) throws Exception {

		try {
			getCurrentFunctionName(true);
			GHRYselectColorTemplate(bundle, ProductID);
			getCurrentFunctionName(false);
		} catch (Exception e) {
			if (!(e.getMessage() == null) && e.getMessage().contains("element click intercepted")) {
				logs.debug(MessageFormat.format(LoggingMsg.FORMATTED_ERROR_MSG, e.getMessage()));
				logs.debug("Refresh the browser to close the Intercepted windows");
				Common.refreshBrowser();
				clickBundleItems();
				// update the product id after the refresh
				if (bundle)
					ProductID = PDP.getProductID(0);
				GHRYselectColorTemplate(bundle, ProductID);
			} else {
				logs.debug(MessageFormat.format(
						ExceptionMsg.PageFunctionFailed + " Aplication was not able to select color swatch",
						new Object() {
						}.getClass().getEnclosingMethod().getName()));
				throw e;
			}
		}
	}

	// done - SMK
	public static void GHRYselectColorTemplate(Boolean bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);

			String subStrArr = (PDPSelectors.GHRYColorOptions.get());

			// Bundle product selector.
			if (bundle) {
				// ProductID = getProductID(0);
				subStrArr = MessageFormat.format(PDPSelectors.GHRYColorOptionsBundle.get(), ProductID);
			}
			List<WebElement> list = SelectorUtil.getAllElements(subStrArr);
			logs.debug("Number of color options:" + list.size());
			String classValue;
			for (int index = 0; index < list.size(); index++) {
				classValue = SelectorUtil.getAttrString(subStrArr, "class", index);
				if (!classValue.contains("no-available") && !classValue.contains("disabled")) {
					WebElement item;
					if (!isMobile()) {
						item = SelectorUtil.getAllElements(subStrArr + " .gwt-image-picker-option-image").get(index);
					} else {
						item = list.get(index);
					}
					JavascriptExecutor jse = (JavascriptExecutor) getDriver();
					jse.executeScript("arguments[0].scrollIntoView(false)", item);
					item.click();
					break;
				}
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Color option selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void GHRYselectSize(Boolean bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			String subStrArr = (PDPSelectors.GHRYSizeOptions.get());

			// Bundle product selector.
			if (bundle) {
				subStrArr = MessageFormat.format(PDPSelectors.GHRYSizeOptionsBundle.get(), ProductID);
			}
			List<WebElement> list = SelectorUtil.getAllElements(subStrArr);
			logs.debug("Number of size options:" + list.size());
			for (int index = 0; index < list.size(); index++) {
				String classValue = SelectorUtil.getAttrString(subStrArr, "class", index);
				if (!classValue.contains("no-available") && !classValue.contains("disabled")) {
					String nthSel = subStrArr + ">div";
					WebElement item = getDriver().findElements(By.cssSelector(nthSel)).get(index);
					JavascriptExecutor jse = (JavascriptExecutor) getDriver();
					jse.executeScript("arguments[0].scrollIntoView(false)", item);
					if (isMobile())
						Thread.sleep(1000);
					((JavascriptExecutor) getDriver()).executeScript("arguments[0].click()", item);
					break;
				}
			}
			getCurrentFunctionName(false);
		} catch (Exception e) {
			logs.debug("e.getMessage()" + e.getMessage());
			if (!(e.getMessage() == null) && e.getMessage().contains("element click intercepted")) {
				logs.debug(MessageFormat.format(LoggingMsg.FORMATTED_ERROR_MSG, e.getMessage()));
				logs.debug("Refresh the browser to close the Intercepted windows");
				Common.refreshBrowser();
				clickBundleItems();
				// update the product id after the refresh
				if (bundle)
					ProductID = PDP.getProductID(0);
				GHRYselectColor(bundle, ProductID);
				GHRYselectSize(bundle, ProductID);
			} else {
				logs.debug(MessageFormat.format(
						ExceptionMsg.PageFunctionFailed + "Size option selector was not found by seleniuem",
						new Object() {
						}.getClass().getEnclosingMethod().getName()));
				throw e;
			}
		}
	}

	/**
	 * Get the number of items from the header mini cart.
	 *
	 * @return integer.
	 * @throws Exception
	 */
	public static int getNumberOfCartItems() throws Exception {

		try {
			getCurrentFunctionName(true);
			WebElement miniCart = SelectorUtil.getElement(PDPSelectors.miniCartItems.get());
			String miniCartText = miniCart.getText();

			if (!isMobile()) {
				// Split the number of items from the text (Desktop & Tablet).
				String[] listString = miniCartText.split("\\(");
				miniCartText = listString[1];
				miniCartText = miniCartText.split("item")[0].trim();
			}

			int miniCartItems = Integer.parseInt(miniCartText);
			getCurrentFunctionName(false);
			return miniCartItems;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static String GHgetOptionClass(int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = PDPSelectors.GHfirstSwatchInOptions.get();
			String ID = SelectorUtil.getAttrString(Str, "class", index);
			getCurrentFunctionName(false);
			return ID;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Swatches in PDP selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	/**
	 * Get the selected quantity.
	 *
	 * @return integer.
	 * @throws Exception
	 */
	public static int getQuantity(boolean bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			String quantitySelector = PDPSelectors.quantity.get();

			// Bundle product selector.
			if (bundle) {

				if (ProductID.isEmpty()) {
					ProductID = getProductID(0);
				}

				quantitySelector = MessageFormat.format(PDPSelectors.quantityBundle.get(), ProductID);
				if (ProductID == null) {
					quantitySelector = PDPSelectors.quantityBundle.get();
				}
			}
			WebElement quantity = SelectorUtil.getElement(quantitySelector);

			String quantityText = quantity.getAttribute("value");
			if (isMobile() && !isBD()) {
				// The qunatity is a div not an input.
				quantityText = quantity.getText();
			}

			int quantityValue = Integer.parseInt(quantityText);
			getCurrentFunctionName(false);
			return quantityValue;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void selectQuantity(boolean bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			String incrementQuantitySelector = PDPSelectors.incrementQuantity.get();
			// Bundle product selector.
			if (bundle) {
				incrementQuantitySelector = MessageFormat.format(PDPSelectors.incrementQuantityBundle, ProductID);
				if (isBD() && isMobile()) {
					incrementQuantitySelector = PDPSelectors.incrementQuantity.get();
				}
			}
			SelectorUtil.initializeSelectorsAndDoActions(incrementQuantitySelector);
			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void GHRYselectSwatches(Boolean bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			GHRYselectColor(bundle, ProductID);
			int numberOfPanels = GHRYNumberOfOptions(bundle);
			logs.debug("numberOfPanels: " + numberOfPanels);

			if (numberOfPanels > 1)
				GHRYselectSize(bundle, ProductID);
			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static int getNumberofSwatchContainers() throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str;
			if (isBD())
				Str = PDPSelectors.BDSwatchesOptions.get();
			else
				Str = PDPSelectors.FGGRSwatchesOptions.get();
			int numberOfSwatchContainers = SelectorUtil.getAllElements(Str).size();
			logs.debug("Number of Swatch Containers: " + numberOfSwatchContainers);
			getCurrentFunctionName(false);
			return numberOfSwatchContainers;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "swatches selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static int getNumberofSwatchContainersBundle() throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = "";
			int numberOfSwatchContainers = 0;
			if (!isBD())
				Str = "css,#" + getProductID(0) + ">" + PDPSelectors.FGGRSwatchesOptions.get().replace("css,", "");
			else {
				if (isMobile()) {
					String optionsContainer = PDPSelectors.BDoptionsContainer.get();

					if (SelectorUtil.isNotDisplayed(optionsContainer)) {
						return numberOfSwatchContainers;
					} else {
						Str = PDPSelectors.BDBundleSwatchesOptions.get();
					}
				} else {
					Str = "css,#" + getProductID(0) + " "
							+ PDPSelectors.BDBundleSwatchesOptions.get().replace("css,", "");
				}
			}
			numberOfSwatchContainers = SelectorUtil.getAllElements(Str).size();
			logs.debug("Number of Swatch Containers: " + numberOfSwatchContainers);
			getCurrentFunctionName(false);
			return numberOfSwatchContainers;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "swatches selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static String getSwatchContainersdivClass(int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str;
			if (!isBD())
				Str = PDPSelectors.FGGRSwatchesOptions.get();
			else
				Str = PDPSelectors.BDSwatchesOptions.get();
			String SwatchContainerClass = SelectorUtil.getAttrString(Str, "class", index);
			logs.debug("SwatchContainerClass: " + SwatchContainerClass);
			getCurrentFunctionName(false);
			return SwatchContainerClass;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "swatches selector was not found by seleniuem", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static String getSwatchContainersdivClassBundle(int index, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str;
			if (!isBD())
				Str = "css,#" + ProductID + ">" + PDPSelectors.FGGRSwatchesOptions.get().replace("css,", "");
			else
				Str = "css,#" + ProductID + " " + PDPSelectors.BDSwatchesOptions.get().replace("css,", "");
			if (isBD() && isMobile()) {
				Str = PDPSelectors.BDSwatchesOptions.get();
			}

			String SwatchContainerClass = SelectorUtil.getAttrString(Str, "class", index);
			logs.debug("SwatchContainerClass: " + SwatchContainerClass);
			getCurrentFunctionName(false);
			return SwatchContainerClass;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Swatches button selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean getAddToCartClass() throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str;
			boolean isDisabled = true;
			if (isBD()) {
				Str = PDPSelectors.BDaddToCartBtn.get();
				String addToCartBtnDisabledAttribute = SelectorUtil.getAttrString(Str, "disabled");
				if (addToCartBtnDisabledAttribute == null) {
					logs.debug("addToCartBtnClass: no");
					isDisabled = false;
				} else {
					logs.debug("addToCartBtnClass: yes");
					isDisabled = true;
				}

			} else {
				Str = PDPSelectors.addToCartBtn.get();
				String addToCartBtnClass = SelectorUtil.getAttrString(Str, "class");
				logs.debug("addToCartBtnClass: " + addToCartBtnClass);
				isDisabled = addToCartBtnClass.contains("disabled");
			}
			getCurrentFunctionName(false);
			return isDisabled;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Add to cart button selector was not found by seleniuem",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void FGGRselectSwatchesSingle() throws Exception {
		try {
			getCurrentFunctionName(true);
			Boolean noOptions = true;
			if (SelTestCase.isMobile())
				noOptions = !getAddToCartClass();
			else
				noOptions = getSwatchContainersdivClass(0).contains("no-options");

			if (noOptions) {
				logs.debug("No options to select");
			} else if (!SelTestCase.isMobile()) {
				int numberOfSwatchContainers = getNumberofSwatchContainers();
				for (int i = 0; i < numberOfSwatchContainers; i++) {
					if (getSwatchContainersdivClass(i).contains("listbox")) {
						selectNthListBoxFirstValue(i);
					} else {
						selectNthOptionFirstSwatch(i + 1);
					}
				}
			} else {
				int numberOfSwatchContainers = getNumberofSwatchContainers();
				for (int i = 1; i < numberOfSwatchContainers; i += 2) {
					if (getSwatchContainersdivClass(i).contains("u-product-options")) {
						selectNthListBoxFirstValue((i - 1) / 2);
					} else {
						selectNthOptionFirstSwatch((i + 1) / 2);
					}
				}
			}
			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "Select swatches has falied, a selector was not found by selenium", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// Done SMK
	public static void FGGRselectSwatchesBundle(String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			int numberOfSwatchContainers = getNumberofSwatchContainersBundle();
			if (getSwatchContainersdivClassBundle(0, ProductID).contains("no-options")) {
				logs.debug("No options to select");
			} else {
				String ListSelector = MessageFormat.format(PDPSelectors.ListBoxBundle, ProductID);
				for (int i = 0; i < numberOfSwatchContainers; i++) {
					if (getSwatchContainersdivClassBundle(i, ProductID).contains("listbox")) {
						selectNthListBoxFirstValueBundle(ListSelector, i);

					} else {
						selectNthOptionFirstSwatchBundle("css,#" + ProductID + ">"
								+ MessageFormat.format(PDPSelectors.imageOption.get(), i + 1, 1).replace("css,", ""));
					}
				}

			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed
					+ "Select swatches has falied, a selector was not found by selenium", new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean selectWLByName(String createdWL) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isSelected = false;
			List<WebElement> elements = SelectorUtil.getAllElements(PDPSelectors.RYcreateNewWLName.get());
			for (int index = 0; index < elements.size(); index++) {
				if (elements.get(index).getText().toLowerCase().equalsIgnoreCase(createdWL)) {
					isSelected = true;
					List<WebElement> WLElements = SelectorUtil.getAllElements(PDPSelectors.RYcreateNewWL.get());
					SelectorUtil.clickOnWebElement(WLElements.get(index));
					break;
				}
			}
			getCurrentFunctionName(false);
			return isSelected;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Wihsllist name selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickAddToCartButtonNoBundle() throws Exception {
		try {
			getCurrentFunctionName(true);

			if (SelTestCase.isFGGR())
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.addToCartBtn.get());

			if (SelTestCase.isGHRY())
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.GHRYaddToCartBtn.get());

			if (SelTestCase.isBD())
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.addToCartBtnBD.get());

			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(
					ExceptionMsg.PageFunctionFailed + "Add to cart button selector was not found by selenium",
					new Object() {
					}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

}