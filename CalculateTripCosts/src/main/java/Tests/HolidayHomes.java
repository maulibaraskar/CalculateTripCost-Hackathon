package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import Pages.HolidayHomesPage;
import Utilities.DateFunction;
import Utilities.DriverSetup;
import Utilities.ExtentReport;
import Utilities.ReadProperties;
import Utilities.TakeScreenShot;
import Utilities.WriteExcelData;

public class HolidayHomes extends ExtentReport {

	
	
	
	
	
	@Parameters({"browser_name"})
	@BeforeTest(groups= {"smoke","regression"})
	public void getDriver(String browser) throws Exception
	{
		ExtentReport.createTest("Browser invoking", "Invoking the browser using Driver setup class");
		System.out.println("Opening in browser:"+browser);
		DriverSetup.getWebDriver(browser);		//Invoke browser
		
		ExtentReport.addTestInfo("Browser invoked successfully");
	}
	
	
	//Open tripadvisor website and move to Rentals page
	@Test(priority = 0, groups= {"smoke","regression"})
	public void holidayHomes() throws Exception {
		
		Actions action = new Actions(driver);
		
		ExtentReport.createTest("Holiday homes page","Open TripAdvisor homepage and move to holiday homes page");
		driver.get(ReadProperties.getUrl());	//open tripadvisor website
		ExtentReport.addTestInfo("TripAdvisor website opened");
		
		TakeScreenShot.CaptureScreenShot("TripAdvisor_HomePage"); 	//Take screenshot
		
		PageFactory.initElements(driver, HolidayHomesPage.class);  //Import PageFactory to get Web elements
		
		action.moveToElement(HolidayHomesPage.hhomes).click().perform();	//click on holiday homes tab
		ExtentReport.addTestInfo("HolidayHomes tab clicked");
		
		String url = driver.getCurrentUrl();
		if (url.equals(ReadProperties.getRentalURL()))
		{	
			//If the current page is required page, nothing happens 
			ExtentReport.addTestInfo("Moved to Holiday Homes page");
		} 
		else
		{
			//else redirected to the required page
			driver.get(ReadProperties.getRentalURL());
		}
		
	}

	
	//Enter destination,check-in & check-out date and click on search
	@Test(dependsOnMethods = { "holidayHomes" }, groups="regression")
	public void searchPage() throws Exception {
		
		ExtentReport.createTest("Entering Destination","Enter destination,check-in & check-out dates");
		// Enter Nairobi in search box
		HolidayHomesPage.destination.sendKeys(ReadProperties.getDestination()); 
		ExtentReport.addTestInfo("Destination added successfully");
		
		// click on "Check-in"
		HolidayHomesPage.checkInIcon.click(); // Enter CheckinDate
		
		
		String checkin = DateFunction.selectCheckinDate();
		driver.findElement(By.xpath("//*[(@data-date='"+checkin+"')]")).click();
		ExtentReport.addTestInfo("Check-in date clicked");
		
		String checkout = DateFunction.selectCheckoutDate();
		driver.findElement(By.xpath("//*[(@data-date='" + checkout + "')]")).click();
		ExtentReport.addTestInfo("Check-out date clicked");
		Thread.sleep(2000); // To wait for calender to load the date
		
		TakeScreenShot.CaptureScreenShot("Rentals_Page"); 
		
		// clicking "Find holiday rentals" button																					
		HolidayHomesPage.findButton.click();
		ExtentReport.addTestInfo("'Find holiday homes' button clicked successfully");
		
	}

	//Add no. of Bedrooms,guests and click on apply
	@Test(dependsOnMethods = { "searchPage" }, retryAnalyzer = Failures.Retry.class, groups="regression")
	public void resultsPage() throws InterruptedException {
	
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		Actions action = new Actions(driver);
		
		ExtentReport.createTest("Guest and Rooms","Add the no. of guests and rooms needed");
		// Adding guests tab
		js.executeScript("arguments[0].click();", HolidayHomesPage.peopleTab);
		ExtentReport.addTestInfo("Guests button clicked");
		Thread.sleep(1000); // Wait until pop-up window is opened 
		
		// add Bed rooms
		action.moveToElement(HolidayHomesPage.rooms).click().perform(); 
		ExtentReport.addTestInfo("Rooms added");
		
		// add Guests
		action.moveToElement(HolidayHomesPage.guests).doubleClick().perform();
		ExtentReport.addTestInfo("No. of guests added");
		
		TakeScreenShot.CaptureScreenShot("Add_Guests");
		
		// click on apply
		HolidayHomesPage.apply.click();
		ExtentReport.addTestInfo("Apply button clicked");
		}
	
	
// Sort by traveler rating
	@Test(dependsOnMethods = { "resultsPage" }, retryAnalyzer = Failures.Retry.class, groups="regression")
	public void SortBy(){
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		ExtentReport.createTest("Traveller rating sort","Sort the result with highest traveller rating");
		// click drop down
		js.executeScript("arguments[0].click();", HolidayHomesPage.dropdown);
		ExtentReport.addTestInfo("Sorting dropdown clicked");
		
		// select traveler rating
		HolidayHomesPage.travellerRating.click();
		ExtentReport.addTestInfo("Traveller rating value is clicked and list is sorted");
		
		TakeScreenShot.CaptureScreenShot("Travellers_Rating");
		
	}

	
	//Add the filter Lift/elevator access
	@Test(dependsOnMethods = { "resultsPage" }, groups="regression")
	public void liftAccess() 
	{

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		Actions action = new Actions(driver);
		
		ExtentReport.createTest("Elevator/Lift access","To select the check-box 'Elevator/Lift Access under Amenites");
		// Clicking showmore
		js.executeScript("arguments[0].scrollIntoView(true);", HolidayHomesPage.showmore);
		js.executeScript("arguments[0].click();", HolidayHomesPage.showmore); //click on showmore under amenities
		action.sendKeys(Keys.HOME).build().perform(); //Move to top of the page
		ExtentReport.addTestInfo( "'Show more' under Amenities clicked");
		
		// To click elevator/lift access
		js.executeScript("arguments[0].scrollIntoView(true);", HolidayHomesPage.lift);
		js.executeScript("arguments[0].click();", HolidayHomesPage.lift); //click on Elevator/lift access check-box
		action.sendKeys(Keys.HOME).build().perform(); //Move to top of the page
		ExtentReport.addTestInfo("Elevator/Lift Access check-box under Amenities is clicked");

		TakeScreenShot.CaptureScreenShot("Lift_Access");

	}

//Get top 3 Hotel name,Price per night and total cost
	@Test(dependsOnMethods = { "liftAccess" }, groups="regression")
	public void holidayHomePrice() throws Exception {
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		ExtentReport.createTest("Result page","Print the Hotel name,price/night and total cost");
		
		js.executeScript("document.body.style.zoom = '50%'");
		
		TakeScreenShot.CaptureScreenShot("Result_Page");
		
		System.out.printf("\n\n");
		System.out.printf("---------------------------------Holiday Homes List---------------------------------\n\n");
		
		for (int i = 3; i < 5; i++) {
			System.out.printf("Holiday Home No:" + (i - 2) + "\n");
			ExtentReport.addTestInfo("Holiday Home No:" + (i - 2));
//---------------------------------------------------------------------------------------------------------
			String houseName = driver.findElement(By.xpath("//*[@id='component_2']/div/div[3]/div/div[1]/div[2]/div[" + i + "]/div[1]/div[1]/div[2]/h2/a")).getText();
			System.out.println("Hotel name: " + houseName); 	 //print hotel name
			ExtentReport.addTestInfo("Hotel name: " + houseName);
//----------------------------------------------------------------------------------------------------------			
			String price = driver.findElement(By.xpath("//*[@id='component_2']/div/div[3]/div/div[1]/div[2]/div[" + i + "]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]")).getText();
			System.out.println("Price Per Night: " + price);	//print price per night
			ExtentReport.addTestInfo("Price Per Night: " + price);
			
			String totalCost = driver.findElement(By.xpath("//*[@id='component_2']/div/div[3]/div/div[1]/div[2]/div[" + i + "]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[2]")).getText();
			System.out.println("Total Price : " + totalCost);	//print Total cost
			ExtentReport.addTestInfo("Total Price : " + totalCost);
//--------------------------------------------------------------------------------------------------------------		
			System.out.printf("\n");
			int r=i-3;
			String output = "Holiday Home No:" + (i - 2)+"| Hotel name: " + houseName+"| Price Per Night: " + price+"| Total Price : " + totalCost;
			WriteExcelData.write(r, 0, "Hotel no.:"+(i-2));
			WriteExcelData.write(r, 1, output);
		}
		js.executeScript("document.body.style.zoom = '100%'");
	}

	
	@Test(dependsOnMethods = { "holidayHomePrice" }, groups="regression")
	public static void returnHomePage() {
		ExtentReport.createTest("Return homepage","Return to homepage by clicking on 'TripAdvisor.in' logo");
		HolidayHomesPage.returnHomepage.click();		//return to homepage by clicking logo of tripadvisor
		System.out.println("Successfully returned to the Homepage");
		ExtentReport.addTestInfo("Successfully returned to the homepage");
	}
}
