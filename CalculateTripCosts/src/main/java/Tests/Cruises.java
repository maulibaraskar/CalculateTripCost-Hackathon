package Tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.CruisesPage;
import Utilities.ExtentReport;
import Utilities.TakeScreenShot;
import Utilities.WriteExcelData;

public class Cruises extends ExtentReport{
	

	//Click more and select cruises from drop down
	@Test(priority = 0, groups="regression")
	public void cruisesHome() throws InterruptedException {
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		ExtentReport.createTest("Cruises page","Move to Cruises page from home page");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
		
		PageFactory.initElements(driver, CruisesPage.class);	//Import PageFactory to get Web elements
		
		js.executeScript("arguments[0].click();", CruisesPage.moreTab);		// To click on more
		ExtentReport.addTestInfo("Clicked on 'More' tab");
		Thread.sleep(1000);
		
		TakeScreenShot.CaptureScreenShot("Cruises_Tab");
		 
		js.executeScript("arguments[0].click();", CruisesPage.cruises);		// click on cruises
		ExtentReport.addTestInfo("Clicked the 'Cruises' from drop-down of 'More' tab");
		
	}

	//Select cruise line cruise ship and search it
	@Test(dependsOnMethods = { "cruisesHome" }, groups="regression")
	public static void selectCruises() throws Exception {
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		Actions action = new Actions(driver);
		
		ExtentReport.createTest("Select Cruise details", "Select Cruise line,Cruise Ship and click search");																									
		CruisesPage.cruiseLineDropDown.click();		// click on cruise line drop down	
		ExtentReport.addTestInfo("Clicked on 'Cruise line' drop-down");
		
		//scroll and select Cruise line from drop-down
		for (int i = 1; i < 90; i++) {
			WebElement allCruises = driver.findElement(By.xpath("//*[@id=\"component_1\"]/div/div[2]/div/div[1]"
					+ "/div/div/ul/li[" + i + "]"));			 // iterate over curiseline's //*[@id=\"component_1\"]/div/div[3]/div/div[1]
			js.executeScript("arguments[0].scrollIntoView(true);", allCruises);
			
			//if the required cruise line found click on that and stop scrolling
			if ((allCruises.getText()).equalsIgnoreCase("Norwegian Cruise Line")) {
				allCruises.click();
				ExtentReport.addTestInfo("Clicked on 'Norwegian Cruise Line' from dropdown");
				action.sendKeys(Keys.HOME).build().perform(); //Move to top of the page
				Thread.sleep(2000);
				break;
			}
		}
		
		//Click on respective Cruise ship
		CruisesPage.cruiseShipDropDown.click(); // click on Cruise ship drop-down	
		ExtentReport.addTestInfo("Clicked on 'Cruise ship' drop-down");
		
		CruisesPage.norwegianEpic.click();	 // find "Norwegian epic" from list
		ExtentReport.addTestInfo("'Norwegian Epic' ship is selected");
		
		action.sendKeys(Keys.HOME).build().perform();
		Thread.sleep(1000); 		// must use to wait until the ship is selected
		
		TakeScreenShot.CaptureScreenShot("Select_Cruise");
		
		js.executeScript("arguments[0].click();", CruisesPage.search);	// click on "search"  button
		ExtentReport.addTestInfo("Clicked on 'Search' button");
	}

	//Get ship details and required fields
	@Test(dependsOnMethods = { "selectCruises" }, groups="regression")
	public void shipDetails() throws Exception {
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		Actions action = new Actions(driver);
		
		ExtentReport.createTest("Ship details", "Get Ship name,ship crew details and available languages");
	
		String parentWin = driver.getWindowHandle(); 	// get parent window
		Set<String> allWindows = driver.getWindowHandles(); // get all windows and store in a set
		
		int winCount = allWindows.size(); 		// get total no. of windows
		System.out.println("Total windows count=" + winCount);
		
		for (String child : allWindows) {
			if (!parentWin.equalsIgnoreCase(child)) {
				driver.switchTo().window(child); 	// switch to child window
				Thread.sleep(1000);
				ExtentReport.addTestInfo("driver control is switched to 'Norwegian Epic' page");
			}
		}
		
		String shipname =CruisesPage.shipName.getText(); // get ship name
		System.out.println("Ship Name is:" + shipname);
		
		WriteExcelData.write(3, 0, "ShipName");
		WriteExcelData.write(3, 1, shipname);
		
		Assert.assertEquals(shipname, "Norwegian Epic");	//verify whether ship name is equals to selected name
		
		TakeScreenShot.CaptureScreenShot("Cruises_Page");
		
		//Get passenger,crew, launched year and display it
		js.executeScript("arguments[0].scrollIntoView(true);", CruisesPage.overview);	//scroll to overview of the ship
		
		System.out.printf("\nPassengers, Crew and Launched Year:\n");
		System.out.println(CruisesPage.passengers.getText());					//get the text from overview and print it
		System.out.println();
		ExtentReport.addTestInfo("Passengers, Crew and Launched Year:"+CruisesPage.passengers.getText());
		ExtentReport.addTestInfo("Passengers,Crew and Year is printed");
		
		WriteExcelData.write(4, 0, "About Ship");
		WriteExcelData.write(4, 1, CruisesPage.passengers.getText());
		
		TakeScreenShot.CaptureScreenShot("Ship_Crew");
		
		//Move to languages and click on more languages
		js.executeScript("arguments[0].scrollIntoView(true);", CruisesPage.languages);
		ExtentReport.addTestInfo("Scrolled into view of languages");
		 
		js.executeScript("arguments[0].click();", CruisesPage.moreLanguages);	// click on more
		ExtentReport.addTestInfo("Clicked on 'more languages' to view all languages");
		
		
		//Move to languages pop-up window , get all languages and store it in a list
		action.moveToElement(CruisesPage.allLanguages).perform();
		
		TakeScreenShot.CaptureScreenShot("All_Languages");
		
		//add languages to list
		List<String> langlist = new ArrayList<String>(); // create a list
		for (int i = 2; i < 7; i++) {
			WebElement addToList = driver.findElement(By.xpath("//*[@id=\"BODY_BLOCK_JQUERY_REFLOW\"]/div[16]/div/div[1]/ul/li[" + i + "]"));
			langlist.add(addToList.getText()); // add available languages one by one to list
		}
		
		//Display languages from list
		System.out.println("Available languages:");
		System.out.println();
		
		WriteExcelData.write(5, 0, "Available Languages");
		for (int j = 0; j < langlist.size(); j++) {
			System.out.println(langlist.get(j)); // Iterate over list and print the elements in list
			ExtentReport.addTestInfo("Language"+(j+1)+": "+langlist.get(j));
			WriteExcelData.write(5, (j+1), langlist.get(j));
		}
		System.out.println();
		driver.close();		//close the child window
		ExtentReport.addTestInfo("Ship details tab is closed");
		
		driver.switchTo().window(parentWin);
		HolidayHomes.returnHomePage(); 		//return to homepage of tripadvisor
		ExtentReport.addTestInfo("Returned to the homepage");
	}

	@Test(priority=1, groups= {"smoke","regression"})
	public void close() throws Exception {
		ExtentReport.createTest("Close browser", "Close the browser");
		Thread.sleep(1000);
		driver.quit();		//close the browser
		ExtentReport.addTestInfo("Browser closed successfully");
		
	}

}

