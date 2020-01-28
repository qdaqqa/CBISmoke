package com.generic.tests.GH.PDP;

import java.text.MessageFormat;
import java.util.Arrays;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.generic.page.PDP;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.SASLogger;
import com.generic.util.dataProviderUtils;

public class PDPBase extends SelTestCase {


	// possible scenarios
	public static final String singlePDP = "Validate PDP Single active elements";
	public static final String bundlePDP = "Validate PDP Bundle active elements";
	public static final String personalizedPDP = "Validate PDP Personalized active elements";
	public static final String singlePDPSearchTerm = "shoes";
	public static final String BundlePDPSearchTerm = "41589"; 
	public static final String personalizedPDPSearchTerm = "personalized";
	public static final String personalizedPDPSearchTermIpad = "Embroidered Felted-Wool Tree Skirt";

	//Embroidered Felted-Wool Tree Skirt

	public static final String wishListGuestValidation = "Wish List Guest Validation";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.PDPSheet;

	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>(); 

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
	}

	@DataProvider(name = "PDP_SC", parallel = true)
	// concurrency maintenance on sheet reading
	public static Object[][] loadTestData() throws Exception {
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@Test(dataProvider = "PDP_SC")
	public void PDPTest(String caseId, String runTest, String desc, String proprties)
			throws Exception {
		Testlogs.set(new SASLogger("PDP_SC " + getBrowserName()));
		// Important to add this for logging/reporting
		setTestCaseReportName(SheetVariables.PDPCaseId);
		String CaseDescription = MessageFormat.format(LoggingMsg.TEST_CASE_DESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc);
		initReportTime();
		Testlogs.get().debug("Case Browser: " + testObject.getParameter("browserName"));


		try {

			if (proprties.contains(singlePDP)) {
				PDP.NavigateToPDP(singlePDPSearchTerm);
				Thread.sleep(10000);
				PDPValidation.validate(false);
			}
			if (proprties.contains(bundlePDP)) {
				PDP.NavigateToPDP(BundlePDPSearchTerm);
				PDPValidation.validate(false);
			}
			if (proprties.contains(personalizedPDP)) {
				if(isiPad())
					PDP.NavigateToPDP(personalizedPDPSearchTermIpad);
				else {
					PDP.NavigateToPDP(personalizedPDPSearchTerm);

				}
				PDPValidation.validate(true);
			}

			if (proprties.contains(wishListGuestValidation)) {
				WistListGuestValidation.validate();
			}

			sassert().assertAll();
			Common.testPass(CaseDescription);
		} catch (Throwable t) {
			if ((getTestStatus() != null) && getTestStatus().equalsIgnoreCase("skip")) {
				throw new SkipException("Skipping this exception");
			} else {
				Common.testFail(t, CaseDescription, testDataSheet + "_" + caseId);
			}

		} // catch
	}// test
}
