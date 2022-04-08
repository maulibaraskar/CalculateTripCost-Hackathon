package Utilities;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport extends DriverSetup {
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	@Parameters("browser_name")
	@BeforeTest(groups= {"smoke","regression"})
	public void extentReport(String browser) {
		htmlReporter = new ExtentSparkReporter("./Extent_report/"+browser+"Report.html");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("Extent Report");
		htmlReporter.config().setReportName("Calculate Trip Cost Report");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.setSystemInfo("Organization", "CTS");
		extent.setSystemInfo("Browser", browser);
		extent.setSystemInfo("Platform", "Windows 10");
		extent.attachReporter(htmlReporter);

	}

	@AfterMethod(groups= {"smoke","regression"})
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
					ExtentColor.RED));
			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
		} else {
			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
	}

	@AfterTest(groups= {"smoke","regression"})
	public void flushReport() {
		extent.flush();
	}
	
	public static void createTest(String testName,String description)
	{
		test = extent.createTest(testName,description);
	}
	
	public static void addTestInfo(String info)
	{
		test.log(Status.INFO, info);
	}
	
}