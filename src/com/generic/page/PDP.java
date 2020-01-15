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
import com.generic.setup.Common;
import com.generic.selector.PLPSelectors;
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
			if ((!isiPad() && SelTestCase.isFGGR()) || (isRY() && isMobile()))
				PLP.clickSearchicon();

			String itemName = "";
			// This is to handle iPad behavior for search modal.
			// TODO: to use this process on all brands
			if (isGHRY() && isiPad()) {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}
	
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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

	// done - SMK
	public static void selectSize() throws Exception {
		try {
			getCurrentFunctionName(true);
			Thread.sleep(1000);
			String subStrArr = PDPSelectors.allSizes.get();
			String valuesArr = "FFF1";
			if (!SelectorUtil.isNotDisplayed(subStrArr)) {
				SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}

	}

	/** Click on add to cart button.
	*
	* @throws Exception
	*/
	public static void clickAddToCartButton() throws Exception {
		try {
			getCurrentFunctionName(true);
			String subStrArr = null;
			if (SelTestCase.isFGGR())
				subStrArr = PDPSelectors.addToCartBtn.get();
			if (SelTestCase.isGHRY()) {
				subStrArr = PDPSelectors.GHRYaddToCartBtn.get();

				// Bundle product selector.
				int numberOfItems = getNumberOfItems();
				if (numberOfItems > 1) {
					String ProductID = getProductID(0);
					subStrArr = MessageFormat.format(PDPSelectors.GHAddToCartBtnEnabledBundle.get(), ProductID);
				}
			}

			SelectorUtil.initializeSelectorsAndDoActions(subStrArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	// This method to return all available options except the sizes dropdown
	// Size drop-down has a different selector
	public static int getNumberOfOptions() throws Exception {
		getCurrentFunctionName(true);
		String subStrArr = PDPSelectors.avaibleOptions.get();
		int numberOfAvaibleOptions = 0;
		if (!SelectorUtil.isNotDisplayed(subStrArr)) {
			numberOfAvaibleOptions = SelectorUtil.getAllElements(subStrArr).size();
		}
		logs.debug("number Of Avaible Options" + numberOfAvaibleOptions);
		getCurrentFunctionName(false);
		return numberOfAvaibleOptions;
	}

	/** Get the number options for GH & RY.
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
	// This method to return all available List Boxes
	public static int getNumberListBoxes() throws Exception {
		getCurrentFunctionName(true);
		String Str = PDPSelectors.allSizes.get();
		int numberOfListBoxes = 0;
		if (!SelectorUtil.isNotDisplayed(Str)) {
			SelectorUtil.initializeSelectorsAndDoActions(Str);
			numberOfListBoxes = SelectorUtil.getAllElements(Str).size();
		}
		logs.debug("number Of Avaible List Boxes" + numberOfListBoxes);
		getCurrentFunctionName(false);
		return numberOfListBoxes;
	}

// done - SMK
	public static void selectNthListBoxFirstValue(int index) throws Exception {
		getCurrentFunctionName(true);
		String Str = PDPSelectors.allSizes.get();
		String value = "index," + index + ",FFF1";
		SelectorUtil.initializeSelectorsAndDoActions(Str, value);
		getCurrentFunctionName(false);
	}

	// done - SMK
	public static void selectNthOptionFirstSwatch(int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String subStrArr = MessageFormat.format(PDPSelectors.firstSwatchInOptions.get(), index);
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, subStrArr));
			String nthSel = subStrArr;
			// Clicking on the div on desktop and iPad does not select the options,
			// you need to click on the img if there is an img tag.
			if (!SelTestCase.isMobile()) {
				String nthSel2 = subStrArr + ">img";
				if (!SelectorUtil.isNotDisplayed(nthSel2))
					nthSel = subStrArr + ">img";
			}

			SelectorUtil.initializeSelectorsAndDoActions(nthSel);

			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void addProductsToCart() throws Exception {
		getCurrentFunctionName(true);
		selectSwatches();
		Thread.sleep(2000);
		clickAddToCartButton();
		if (PDP.bundleProduct() && SelTestCase.isMobile()) {
			closeModalforBundleItem();
		}
		Thread.sleep(1000);
		getCurrentFunctionName(false);
	}

	// Done CBI
	public static void closeModalforBundleItem() throws Exception {
		getCurrentFunctionName(true);
		SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.closeBundleProductModal.get());
		getCurrentFunctionName(false);

	}

	// done - SMK
	public static boolean validatePriceIsDisplayed() throws Exception {
		getCurrentFunctionName(true);
		try {
			boolean isDisplayed;
			logs.debug("Validate if top price exist");
			String selector = null;
			if (SelTestCase.isFGGR()) {
				selector = PDPSelectors.topPriceSingle.get();
				if (getNumberOfItems() > 1 && !SelTestCase.isMobile()) {
					String ProductID = getProductID(0);
					logs.debug(PDPSelectors.topPriceBundle);
					selector = MessageFormat.format(PDPSelectors.topPriceBundle, ProductID);
				}
			} else {
//				if(SelTestCase.isGH() || SelTestCase.isRY()) 
				selector = PDPSelectors.GHtopPriceSingle.get();
			}
			isDisplayed = SelectorUtil.isDisplayed(selector);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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

			} else {
//				if(SelTestCase.isGH() || SelTestCase.isRY()) 
				selector = PDPSelectors.GHtopPriceSingle.get();
			}
			isDisplayed = SelectorUtil.isDisplayed(selector);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static boolean validateBundlePriceIsDisplayed() throws Exception {
		getCurrentFunctionName(true);
		boolean isDisplayed;
		logs.debug("Validate if top price exist for Bundle PDP");
		isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.topPriceBundleDesktop.get());

		getCurrentFunctionName(false);
		return isDisplayed;
	}

	/** Check if the add to gift registry/wish list button is enabled.
	*
	* @throws Exception
	*/
	public static boolean validateAddToWLGRIsEnabled() throws Exception {
		getCurrentFunctionName(true);
		boolean isNotDisplayed;
		logs.debug("Validate if Add To WL/GR Is Displayed");
		// here it will pass if the button exist regardless if it is enabled or
		// disabled.
		// because there is no attribute to verify if it is enabled.
		String selectorEnabled = PDPSelectors.addToWLGRBtnEnabledSingle.get();
		if (isGHRY()) {
			selectorEnabled = PDPSelectors.GHAddToWLGRBtnEnabledSingle.get();
		}
		String selectorDisabled = PDPSelectors.addToCartBtnDisabledSingle.get();
		if (!SelTestCase.isMobile() && getNumberOfItems() > 1) {
			String ProductID = getProductID(0);
			String addToWLGRBtnEnabledBundleSelector = PDPSelectors.addToWLGRBtnEnabledBundle;
			String addToCartBtnDisabledBundle = PDPSelectors.addToCartBtnDisabledBundle;
			if (isGH()) {
				addToWLGRBtnEnabledBundleSelector = PDPSelectors.GHAddToWLGRBtnEnabledBundle.get();
			}
			logs.debug(addToWLGRBtnEnabledBundleSelector);
			selectorEnabled = MessageFormat.format(addToWLGRBtnEnabledBundleSelector, ProductID);
			logs.debug(addToCartBtnDisabledBundle);
			selectorDisabled = MessageFormat.format(addToCartBtnDisabledBundle, ProductID);
		}
		SelectorUtil.isDisplayed(selectorEnabled);
		logs.debug("Validate if Add To WL/GR Is not disabled");
		isNotDisplayed = SelectorUtil.isNotDisplayed(selectorDisabled);
		getCurrentFunctionName(false);
		return isNotDisplayed;
	}

	/** Check if the add to cart button is enabled.
	*
	* @throws Exception
	*/
	public static boolean validateAddToWLGRIsEnabled(Boolean Bundle, String ProductID) throws Exception {
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
		else
			selectorEnabled = PDPSelectors.GHAddToWLGRBtnEnabledSingle.get();
		
		if (!isMobile() && Bundle) {
			logs.debug(PDPSelectors.addToWLGRBtnEnabledBundle);
			selectorEnabled = MessageFormat.format(PDPSelectors.addToWLGRBtnEnabledBundle, ProductID);
			logs.debug(PDPSelectors.addToCartBtnDisabledBundle);
			MessageFormat.format(PDPSelectors.addToCartBtnDisabledBundle, ProductID);
		}
		isDisplayed = SelectorUtil.isDisplayed(selectorEnabled);
		getCurrentFunctionName(false);
		return isDisplayed;
	}

	// done - SMK
	public static boolean validateAddToCartIsEnabled() throws Exception {
		getCurrentFunctionName(true);
		boolean isNotDisplayed;
		logs.debug("Validate if Add To Cart Is Displayed");
		// here it will pass if the button exist regardless if it is enabled or
		// disabled.
		// because there is no attribute to verify if it is enabled.
		String selectorEnabled = PDPSelectors.addToCartBtnEnabledSingle.get();
		String selectorDisabled = PDPSelectors.addToCartBtnDisabledSingle.get();

		if(isGHRY()) {
			selectorEnabled = PDPSelectors.GHAddToCartBtnEnabledSingle.get();
		}
    	if (!SelTestCase.getBrowserName().contains(GlobalVariables.browsers.iPhone) && getNumberOfItems() > 1) {
			String ProductID = getProductID(0);
			String addToCartBtnEnabledBundle = PDPSelectors.addToCartBtnEnabledBundle;
			String addToCartBtnDisabledBundle = PDPSelectors.addToCartBtnDisabledBundle;
			if (isGH()) {
				addToCartBtnEnabledBundle = PDPSelectors.GHAddToWLGRBtnEnabledBundle.get();
			}
			logs.debug(addToCartBtnEnabledBundle);
			selectorEnabled= MessageFormat.format(addToCartBtnEnabledBundle, ProductID);	
			logs.debug(addToCartBtnDisabledBundle);
			selectorDisabled= MessageFormat.format(addToCartBtnDisabledBundle, ProductID);
		}
		SelectorUtil.isDisplayed(selectorEnabled);
		logs.debug("Validate if Add To Cart Is not disabled");
		isNotDisplayed = SelectorUtil.isNotDisplayed(selectorDisabled);
		getCurrentFunctionName(false);
		return isNotDisplayed;
	}

	// done - SMK
	public static boolean validateAddToCartIsEnabled(Boolean Bundle, String ProductID) throws Exception {
		getCurrentFunctionName(true);
		boolean isDisplayed;
		logs.debug("Validate if Add To Cart Is Displayed");
		// here it will pass if the button exist regardless if it is enabled or
		// disabled.
		// because there is no attribute to verify if it is enabled.
		String selectorEnabled;
		if (isFGGR())
			selectorEnabled = PDPSelectors.addToCartBtnEnabledSingle.get();
		else
			selectorEnabled = PDPSelectors.RYAddToCartBtnEnabledSingle.get();

		if (!isMobile() && Bundle) {
			logs.debug(PDPSelectors.addToCartBtnEnabledBundle);
			selectorEnabled = MessageFormat.format(PDPSelectors.addToCartBtnEnabledBundle, ProductID);
			logs.debug(PDPSelectors.addToCartBtnDisabledBundle);
			MessageFormat.format(PDPSelectors.addToCartBtnDisabledBundle, ProductID);
		}
		isDisplayed = SelectorUtil.isDisplayed(selectorEnabled);
		getCurrentFunctionName(false);
		return isDisplayed;
	}

	// done - SMK
	public static String getBottomPrice() throws Exception {
		getCurrentFunctionName(true);
		logs.debug("Validate if bottom price is updated after seleting options");
		String selector = PDPSelectors.bottomPriceSingle.get();
		if(isGHRY()) {
			selector = PDPSelectors.GHRYBottomPriceSingle.get();
		}
		if (!SelTestCase.getBrowserName().contains(GlobalVariables.browsers.iPhone) && getNumberOfItems() > 1 ) {
			String ProductID = getProductID(0);
			selector = MessageFormat.format(PDPSelectors.bottomPriceBundle, ProductID);
		}
		SelectorUtil.initializeSelectorsAndDoActions(selector);
		String price = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return price;
	}

	// done - SMK
		public static String getBottomPrice(Boolean bundle, String ProductID) throws Exception {
			getCurrentFunctionName(true);
			logs.debug("Validate if bottom price is updated after seleting options");
			String selector = PDPSelectors.bottomPriceSingle.get();
			if(isGHRY()) {
				selector = PDPSelectors.GHRYBottomPriceSingle.get();
			}
			if (bundle) {
				if (isGH()) {
					selector= MessageFormat.format(PDPSelectors.GHBottomPriceBundle.get(), ProductID);
				} else if(!isMobile()) {
					selector = MessageFormat.format(PDPSelectors.bottomPriceBundle, ProductID);
				}
			}
			SelectorUtil.initializeSelectorsAndDoActions(selector);
			String price = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return price;
		}

	// done - SMK
	public static boolean validateProductIsAddedToCart() throws Exception {
		getCurrentFunctionName(true);
		boolean isDisplayed;
		// Validate the add to cart modal is displayed for Desktop and iPad.
		// For Mobile, verify it from mini cart because there is no add to cart modal in
		// mobile.
		if (!isMobile()) {
			Thread.sleep(4000);
			isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.addToCartModal.get());
		} else if (isMobile() && isGHRY()) {
			isDisplayed = SelectorUtil.isDisplayed(PDPSelectors.addToCartModal.get());
		} else {
			HomePage.clickOnMiniCart();
			isDisplayed = HomePage.validateMiniCartProductIsDsiplayed();
		}
		getCurrentFunctionName(false);
		return isDisplayed;
	}

	// done - SMK
	public static void clickAddToWLGR() throws Exception {
		try {
			getCurrentFunctionName(true);
			if(isGH()) {
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.GHaddToWLGRBtnEnabled.get());
			}else if(isRY()){
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.RYaddToWLGRBtnEnabled.get());
			}else {
				SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.addToWLGRBtnEnabled.get());
			}
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static String getProductID(int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = PDPSelectors.itemsID.get();
			if(isGH()) {
				Str = PDPSelectors.GHItemsID.get();
			}
			String ID = SelectorUtil.getAttrString(Str, "id", index);
			getCurrentFunctionName(false);
			return ID;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static int getNumberOfItems() throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = PDPSelectors.numberOfBundleItems.get();
			if (isGH()) {
				Str = PDPSelectors.GHNumberOfBundleItems.get();
			}
			int numberOfItems = 1;
			// if (!SelectorUtil.isNotDisplayed(Str)) {
			numberOfItems = SelectorUtil.getAllElements(Str).size();
			// }
			logs.debug("Number of Items: " + numberOfItems);
			if (numberOfItems == 1 && bundleProduct()) {
				logs.debug("This is a bundle product with one item");
				numberOfItems = 2;
			}
			getCurrentFunctionName(false);
			return numberOfItems;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			} else if (value.equals("Y")) {
				bundle = false;
			} else {
				if (tries < 10)
					bundleProduct(tries++);
			}

			return bundle;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void selectNthOptionFirstSwatchBundle(String Str) throws Exception {
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

	}

	// done - SMK
	public static void selectNthListBoxFirstValueBundle(String Str, int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String value = "index," + index + ",FFF1";
			SelectorUtil.initializeSelectorsAndDoActions(Str, value);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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

			} else if (SelTestCase.isGHRY())
				GHRYselectSwatches(bundle);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static void selectSwatches() throws Exception {
		try {
			getCurrentFunctionName(true);
			Boolean bundle = PDP.bundleProduct();
			if (SelTestCase.isFG() || SelTestCase.isGR()) {
				String ProductID = null;
				if (!isMobile() && bundle)
					ProductID = PDP.getProductID(0);
				selectSwatches(bundle, ProductID);

			} else if (SelTestCase.isGHRY())
				GHRYselectSwatches(bundle);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug("Clicking on any bundle item");
			if (!SelectorUtil.isNotDisplayed(selector)) {
				SelectorUtil.initializeSelectorsAndDoActions(selector);
			}
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickOnCreateNewWL() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("Click on create new wish list");
			if(isRY()) {
				List <WebElement> elements = SelectorUtil.getAllElements(PDPSelectors.RYcreateNewWL.get());
				   SelectorUtil.clickOnWebElement( elements.get(elements.size() - 1));
	 
			 }else {
				 WebElement element = SelectorUtil.getElement(PDPSelectors.giftRegistryListBox.get());
				 WebElement option =  element.findElement(By.cssSelector(PDPSelectors.createNewWL.get()));
				 option.click();
				 clickOnCreateNewWLConfirmationBtn();	
			 }
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void createNewWL(String WLName) throws Exception {
		try {
			getCurrentFunctionName(true);
			SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.WLName.get(), WLName);
			SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.nameYourNewWLconfirmationBtn.get());
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickOnCreateNewWLConfirmationBtn() throws Exception {
		try {
			getCurrentFunctionName(true);
			logs.debug("Click on create new wish list");
			SelectorUtil.initializeSelectorsAndDoActions(PDPSelectors.createNewWLConfirmationBtn.get());
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
				if(isGH() || isRY()){
					viewListBtnSelector = PDPSelectors.GHRYviewListBtn.get();
				}else {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			if(isGH()) {
			  addToCartBtns = SelectorUtil.getElementsList(PDPSelectors.GHmyWLAddToCartBtn.get());
			}else if(isRY()){
			  addToCartBtns = SelectorUtil.getElementsList(PDPSelectors.RYmyWLAddToCartBtn.get());
			}else {
			   addToCartBtns = SelectorUtil.getElementsList(PDPSelectors.myWLAddToCartBtn.get());
			}
			
			for (int i = 0; i < products.size(); i++) {
	            if(products.get(i).getText().toLowerCase().contains(addedProductName.toLowerCase())) {
					addToCartBtns.get(i).click();
					return true;
				}
			}
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
	
	
	// done-CBI
	public static String getImageSrcID() throws Exception {
		try {
			getCurrentFunctionName(true);
			WebElement product =SelectorUtil.getElement(PDPSelectors.imgID.get());
			
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// Done SMK
	public static boolean PersonalizedItem() throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed = false;
			String addPersonalizedButtonSelector = PDPSelectors.addPersonalizedButton.get();
			if (isGH()) {
				addPersonalizedButtonSelector = PDPSelectors.GHAddPersonalizedButton.get();
			}
			if (getNumberOfItems() > 1 && !SelTestCase.getBrowserName().contains(GlobalVariables.browsers.iPhone)) {
				String ProductID = getProductID(0);
				addPersonalizedButtonSelector = "css,#" + ProductID + ">"
						+ PDPSelectors.addPersonalizedButton.get().replace("css,", "");
				logs.debug("addPersonalizedButtonSelector:  " + addPersonalizedButtonSelector);
			}
			isDisplayed = SelectorUtil.isDisplayed(addPersonalizedButtonSelector);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// Done SMK
	public static boolean PersonalizedItem(Boolean Bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			boolean isDisplayed = false;
			String addPersonalizedButtonSelector = PDPSelectors.addPersonalizedButton.get();
			if (!isMobile() && Bundle) {
				addPersonalizedButtonSelector = "css,#" + ProductID + ">"
						+ PDPSelectors.addPersonalizedButton.get().replace("css,", "");
				logs.debug("addPersonalizedButtonSelector:  " + addPersonalizedButtonSelector);
			}
			isDisplayed = SelectorUtil.isDisplayed(addPersonalizedButtonSelector);
			getCurrentFunctionName(false);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void clickAddPersonalizationButton() throws Exception {
		try {
			getCurrentFunctionName(true);
			String addPersonalizedButtonSelector = PDPSelectors.addPersonalizedButton.get(); 
			if (isGH()) {
				addPersonalizedButtonSelector = PDPSelectors.GHAddPersonalizedButton.get(); 
			}
			if (getNumberOfItems() > 1 && !SelTestCase.getBrowserName().contains(GlobalVariables.browsers.iPhone)) {
				String ProductID = getProductID(0);
				addPersonalizedButtonSelector = "css,#" + ProductID + ">"
						+ PDPSelectors.addPersonalizedButton.get().replace("css,", "");
				logs.debug("addPersonalizedButtonSelector:  " + addPersonalizedButtonSelector);
			}

			SelectorUtil.initializeSelectorsAndDoActions(addPersonalizedButtonSelector);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void clickAddPersonalizationButton(Boolean Bundle, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			String addPersonalizedButtonSelector = PDPSelectors.addPersonalizedButton.get();

			if (!isMobile() && Bundle) {
				addPersonalizedButtonSelector = "css,#" + ProductID + ">"
						+ PDPSelectors.addPersonalizedButton.get().replace("css,", "");
				logs.debug("addPersonalizedButtonSelector:  " + addPersonalizedButtonSelector);
			}

			SelectorUtil.initializeSelectorsAndDoActions(addPersonalizedButtonSelector);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			if (!isMobile()) {
				addPersonalizedButtonSelector = PDPSelectors.addPersonalizedButton.get();// for single PDP
				if (Bundle) {// for bundle PDP
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			}
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr);
			getCurrentFunctionName(false);
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			for (int i = 0; i < elementsList.size() - 1; i++) {
				WebElement element = elementsList.get(i);
				SelectorUtil.clickOnWebElement(element);
				if (isPersonalizedInputSwatchesDisplayed(PDPSelectors.personalizedInputValue.get())) {// input container
																										// like MONOGRAM
																										// or any value
					WebElement input = SelectorUtil.getElement(PDPSelectors.personalizedInputValue.get());
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void selectPersonalizationModalSwatches() throws Exception {
		try {
			getCurrentFunctionName(true);
			closeOpendItem();
			List<WebElement> elementsList = SelectorUtil.getAllElements(PDPSelectors.personalizedItems.get());

			for (int i = 0; i < elementsList.size(); i++) {
				WebElement element = elementsList.get(i);
				SelectorUtil.clickOnWebElement(element);
				if (isPersonalizedInputSwatchesDisplayed(PDPSelectors.personalizedInputValue.get())) {// input container
																										// like MONOGRAM
																										// or any value
					WebElement input = SelectorUtil.getElement(PDPSelectors.personalizedInputValue.get());
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
				} else if (isPersonalizedInputSwatchesDisplayed(PDPSelectors.personalizedItemColors2.get())) { // like
																												// thread
																												// color
					List<WebElement> itemColors = SelectorUtil
							.getAllElements(PDPSelectors.personalizedItemColors2.get());
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
			if(isGH()) {
				addedPersonlizedDetailsSelector  =  PDPSelectors.GHAddedPersonlizedDetails.get();
			}
			if (!isMobile() && Bundle) {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}
	public static void GHRYselectColor(Boolean bundle) throws Exception {
		
		try {
			getCurrentFunctionName(true);
			GHRYselectColorTemplate(bundle);
			getCurrentFunctionName(false);
		} catch (Exception e) {
			if (!(e.getMessage() == null) && e.getMessage().contains("element click intercepted")) {
				logs.debug(MessageFormat.format(LoggingMsg.FORMATTED_ERROR_MSG, e.getMessage()));
				logs.debug("Refresh the browser to close the Intercepted windows");
				Common.refreshBrowser();
				GHRYselectColorTemplate(bundle);
			} else {
				logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
				}.getClass().getEnclosingMethod().getName()));
				throw e;
			}
		}
	}
	// done - SMK
	public static void GHRYselectColorTemplate(Boolean bundle) throws Exception {
		try {
			getCurrentFunctionName(true);
			String subStrArr = (PDPSelectors.GHRYColorOptions.get());

			// Bundle product selector.
			if (bundle) {
				String ProductID = getProductID(0);
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
		} catch (Exception e) {
				logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
				}.getClass().getEnclosingMethod().getName() + "was failed, Selenium could not find the selector for the color option, please check the attached screenshot"));
				throw e;
		}
	}

	// done - SMK
	public static void GHRYselectSize(Boolean bundle) throws Exception {
		try {
			getCurrentFunctionName(true);
			String subStrArr = (PDPSelectors.GHRYSizeOptions.get());

			// Bundle product selector.
			if (bundle) {
				String ProductID = getProductID(0);
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
				GHRYselectSize(bundle);
			} else {
				logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
	public static int getQuantity(boolean bundle) throws Exception {
		try {
			getCurrentFunctionName(true);
			String quantitySelector = PDPSelectors.quantity.get();

			// Bundle product selector.
			if (bundle) {
				String ProductID = getProductID(0);
				quantitySelector = MessageFormat.format(PDPSelectors.quantityBundle.get(), ProductID);
			}
			WebElement quantity = SelectorUtil.getElement(quantitySelector);

			String quantityText = quantity.getAttribute("value");
			if (isMobile()) {
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

	public static void GHRYselectSwatches(Boolean bundle) throws Exception {
		try {
			getCurrentFunctionName(true);
			if (SelTestCase.isGHRY()) {
//				closeSignUpModalIfDisplayed();
			}

			GHRYselectColor(bundle);
			int numberOfPanels = GHRYNumberOfOptions(bundle);
			logs.debug("numberOfPanels: " + numberOfPanels);

			if (numberOfPanels > 1)
				GHRYselectSize(bundle);
			getCurrentFunctionName(false);

		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
//	public static void closeSignUpModalIfDisplayed() throws Exception {
//		try {
//			getCurrentFunctionName(true);
//			String subStrArr = PDPSelectors.offerControlClose.get();
//			logs.debug("Closing the offer modal");
//			if (SelTestCase.isGH())
//				getDriver().switchTo().frame(PDPSelectors.GHOfferControlClose.get());
//			if (SelTestCase.isRY())
//				getDriver().switchTo().frame(PDPSelectors.RYOfferControlClose.get());
//			SelectorUtil.initializeSelectorsAndDoActions(subStrArr);
//			// getDriver().switchTo().parentFrame();
//			getCurrentFunctionName(false);
//		} catch (NoSuchFrameException e) {
//			logs.debug("Sign up modal is not displayed");
//		}
//	}

	// done - SMK
	public static int getNumberofSwatchContainers() throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = PDPSelectors.FGGRSwatchesOptions.get();
			int numberOfSwatchContainers = SelectorUtil.getAllElements(Str).size();
			logs.debug("Number of Swatch Containers: " + numberOfSwatchContainers);
			getCurrentFunctionName(false);
			return numberOfSwatchContainers;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static int getNumberofSwatchContainersBundle() throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = "css,#" + getProductID(0) + ">" + PDPSelectors.FGGRSwatchesOptions.get().replace("css,", "");
			int numberOfSwatchContainers = SelectorUtil.getAllElements(Str).size();
			logs.debug("Number of Swatch Containers: " + numberOfSwatchContainers);
			getCurrentFunctionName(false);
			return numberOfSwatchContainers;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	// done - SMK
	public static String getSwatchContainersdivClass(int index) throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = PDPSelectors.FGGRSwatchesOptions.get();
			String SwatchContainerClass = SelectorUtil.getAttrString(Str, "class", index);
			logs.debug("SwatchContainerClass: " + SwatchContainerClass);
			getCurrentFunctionName(false);
			return SwatchContainerClass;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static String getSwatchContainersdivClassBundle(int index, String ProductID) throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = "css,#" + ProductID + ">" + PDPSelectors.FGGRSwatchesOptions.get().replace("css,", "");
			String SwatchContainerClass = SelectorUtil.getAttrString(Str, "class", index);
			logs.debug("SwatchContainerClass: " + SwatchContainerClass);
			getCurrentFunctionName(false);
			return SwatchContainerClass;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static String getAddToCartClass() throws Exception {
		try {
			getCurrentFunctionName(true);
			String Str = PDPSelectors.addToCartBtn.get();
			String addToCartBtnClass = SelectorUtil.getAttrString(Str, "class");
			logs.debug("addToCartBtnClass: " + addToCartBtnClass);
			getCurrentFunctionName(false);
			return addToCartBtnClass;
		} catch (NoSuchElementException e) {
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static void FGGRselectSwatchesSingle() throws Exception {
		try {
			getCurrentFunctionName(true);
			Boolean noOptions = true;
			if (SelTestCase.isMobile())
				noOptions = !getAddToCartClass().contains("disabled");
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
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
			logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
			}.getClass().getEnclosingMethod().getName()));
			throw e;
		}
	}

	public static boolean selectWLByName(String createdWL) throws Exception {
		try{
        getCurrentFunctionName(true);
        boolean isSelected = false;
        List<WebElement> elements = SelectorUtil.getAllElements(PDPSelectors.RYcreateNewWLName.get());
        for(int index=0;index<elements.size();index++) {
        	if(elements.get(index).getText().toLowerCase().equalsIgnoreCase(createdWL)) {
        		isSelected = true;
                List<WebElement> WLElements = SelectorUtil.getAllElements(PDPSelectors.RYcreateNewWL.get());
                SelectorUtil.clickOnWebElement(WLElements.get(index));
                 break;
        	}
        }
        getCurrentFunctionName(false);
		return isSelected;
	} catch (NoSuchElementException e) {
		logs.debug(MessageFormat.format(ExceptionMsg.PageFunctionFailed, new Object() {
		}.getClass().getEnclosingMethod().getName()));
		throw e;
	}
    }

}