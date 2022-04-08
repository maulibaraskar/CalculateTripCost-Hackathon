package Utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.google.common.io.Files;

public class TakeScreenShot extends DriverSetup {

	public static void CaptureScreenShot(String name) {
		try {
			Thread.sleep(2000);
			TakesScreenshot ScrObj = (TakesScreenshot) driver;
			File CaptureImg = ScrObj.getScreenshotAs(OutputType.FILE);
			String TimeStamp = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").format(new Date());
			Files.copy(CaptureImg, new File("./ScreenShots/" + TimeStamp + "_"+name+".png"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occured while taking screenshot");
		}
	}
}
