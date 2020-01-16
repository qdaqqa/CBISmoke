
package com.generic.tests.GH.Search_PLP;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;
import com.generic.page.PDP;
import com.generic.page.PLP;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.SASLogger;
import com.generic.util.dataProviderUtils;

public class PLP_Base extends SelTestCase {

	// used sheet in test
	public static final String testDataSheet = SheetVariables.plpSheet;
	public static final String plpTest = "Full PLP";
	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();
	private static LinkedHashMap<String, Object> users;

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
		users = Common.readUsers();
	}

	@DataProvider(name = "PLP", parallel = true)
	// concurrency maintenance on sheet reading
	public static Object[][] loadTestData() throws Exception {
		getBrowserWait(testObject.getParameter("browserName"));

		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@Test(dataProvider = "PLP")
	public void verifyPLP(String caseId, String runTest, String Proprties, String desc) throws Exception {

		Testlogs.set(new SASLogger("PLP " + getBrowserName()));
		// Important to add this for logging/reporting
		setTestCaseReportName("PLP Case");
		String CaseDescription = MessageFormat.format(LoggingMsg.TEST_CASE_DESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc.replace("\n", "<br>--"));
		initReportTime();

		try {

			if (Proprties.contains(plpTest)) {
				if (isMobile())
					PLP.navigateToRandomPLPMobileIpad();

				else if(isiPad())
					PLP.navigateToRandomPLPIpadGH();
				else
					PLP.navigateToRandomPLPDesktop();
				
				Thread.sleep(2500);
				PDP.closeSignUpModalIfDisplayed();
				sassert().assertTrue(PLP.VerifyPLP(), "PLP Validation failed");
			}
			sassert().assertAll();
			Common.testPass();

		} catch (Throwable t) {
			if ((getTestStatus() != null) && getTestStatus().equalsIgnoreCase("skip")) {
				throw new SkipException("Skipping this exception");
			} else {
				Common.testFail(t, CaseDescription, testDataSheet + "_" + caseId);
			}
		}

	}

}