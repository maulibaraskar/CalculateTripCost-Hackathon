package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadProperties {

	public static Properties useProperties() throws Exception {
		
		FileInputStream file = new FileInputStream("./src/main/resources/Properties/Page.properties");
		Properties property = new Properties();
		property.load(file);
		return property;

	}
	
	public static String getUrl() throws Exception
	{
		Properties property = ReadProperties.useProperties();
		String url = property.getProperty("URL");
		return url;
	}
	
	public static String getRentalURL() throws Exception
	{
		Properties property = ReadProperties.useProperties();
		String url2 = property.getProperty("URL2");
		return url2;		
	}
	public static String getDestination()throws Exception
	{
		Properties property = ReadProperties.useProperties();
		String dest = property.getProperty("Destination");
		return dest;
	}
}
