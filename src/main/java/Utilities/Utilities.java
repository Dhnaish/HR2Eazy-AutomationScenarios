package Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utilities {
	public static Properties prop;

	public static Properties getProperty() throws IOException {

		prop = new Properties();
		FileInputStream file = new FileInputStream("src/test/resources/config.properties");
		prop.load(file);
		return prop;
	}
	
	
	
	

}
