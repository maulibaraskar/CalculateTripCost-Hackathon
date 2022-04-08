package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HolidayHomesPage {
	
	@FindBy (linkText = "Holiday Homes")
	public static WebElement hhomes;

	@FindBy (xpath = "//*[@id=\"taplc_trip_search_home_vacation_rentals_0\"]/div[2]/div[1]/div/span/input")
	public static WebElement destination;
	
	@FindBy (xpath = "//*[@id=\"taplc_trip_search_home_vacation_rentals_0\"]/div[2]/div[2]/div/span[1]/span[2]/span")
	public static WebElement checkInIcon;
	
	@FindBy (xpath = "//*[@id=\"SUBMIT_VACATION_RENTALS\"]/span[2]")
	public static WebElement findButton;
	
	@FindBy (xpath = "//*[contains(@class,'users picker-icon')]")
	public static WebElement peopleTab;
	
	@FindBy (xpath = "(//*[starts-with(@class,'ui_icon plus')])[1]")
	public static WebElement rooms;
	
	@FindBy (xpath = "(//*[starts-with(@class,'ui_icon plus')])[2]")
	public static WebElement guests;
	
	@FindBy (xpath = "(//*[contains(text(),'Apply')])[2]")
	public static WebElement apply;
	
	@FindBy (xpath = "(//span[contains(text(),'Tripadvisor Sort')])[1]")
	public static WebElement dropdown;
	
	@FindBy (xpath = "//*[contains(text(),'Traveller Rating')]")
	public static WebElement travellerRating;
	
	@FindBy (xpath = "//*[text()='Amenities']//following-sibling::div[5]")
	public static WebElement showmore;
	
	@FindBy (xpath = "(//*[contains(text(),'Elevator/Lift')])[1]")
	public static WebElement lift;
	
	@FindBy(xpath = "/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div[2]/header/div/nav/a/picture/img")
	public static WebElement returnHomepage;

}
