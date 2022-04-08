package Utilities;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup {

	public static WebDriver driver;

	public static WebDriver getWebDriver(String browserName) throws InterruptedException {
		if (browserName.equalsIgnoreCase("chrome")) // To use Chrome driver
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browserName.equalsIgnoreCase("MSEdge")) // To use Microsoft Edge Driver
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

		} else {
			driver = null;
			System.out.println("Please provide the browser Name as Chrome/MSEdge");
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
		driver.manage().deleteAllCookies();
		return driver;
	}
}
