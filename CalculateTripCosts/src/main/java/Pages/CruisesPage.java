package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CruisesPage {
	
	@FindBy (xpath = "//*[@id='lithium-root']/main/div[1]/div[2]/div/div/div[16]/div/button")
	public static WebElement moreTab;
	
	@FindBy (xpath = "(//*[contains(text(),'Cruises')])[2]")
	public static WebElement cruises;
	
	@FindBy (xpath = "//*[@id=\"component_1\"]/div/div[2]/div/div[1]/div/button/span")		////*[@id='component_1']/div/div[3]/div/div[1]/div
	public static WebElement cruiseLineDropDown;
	
	@FindBy (xpath = "//*[@id=\"component_1\"]/div/div[2]/div/div[2]/div/button/span")         ////*[@id='component_1']/div/div[3]/div/div[2]/div/button/span
	public static WebElement cruiseShipDropDown;
	
	@FindBy (xpath = "(//*[text()='Norwegian Epic'])[1]")
	public static WebElement norwegianEpic;
	
	@FindBy (xpath = "//*[@id=\"component_1\"]/div/div[2]/div/div[3]/span/button")																////*[@id='component_1']/div/div[3]/div/div[3]/span/button
	public static WebElement search;
	
	@FindBy (xpath = "//*[@id='HEADING']")
	public static WebElement shipName;
	
	@FindBy (xpath = "//*[@id='ship_overview']/div/div/h2")
	public static WebElement overview;
	
	@FindBy (xpath = "//*[@id='ship_overview']/div/div/div/div[1]/div[1]/div[2]")
	public static WebElement passengers;
	
	@FindBy (xpath = "//*[@id='ship_reviews']/div/div[2]/div/div[1]/div[1]/div[3]/div")
	public static WebElement languages;
	
	@FindBy (xpath = "//*[@id='ship_reviews']/div/div[2]/div/div[1]/div[1]/div[3]/ul/div/span")
	public static WebElement moreLanguages;
	
	@FindBy (xpath = "//*[@id='BODY_BLOCK_JQUERY_REFLOW']/div[16]/div/div[1]")
	public static WebElement allLanguages;
	


}
