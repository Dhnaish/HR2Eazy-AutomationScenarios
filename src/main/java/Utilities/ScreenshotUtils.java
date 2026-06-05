package Utilities;

import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotUtils {
	
	
	public static String saveScreenshot(byte[] screenshotBytes, String fileName) throws IOException {

	    String path = System.getProperty("user.dir") + "/screenshots/" + fileName + ".png";

	    FileOutputStream fos = new FileOutputStream(path);
	    fos.write(screenshotBytes);
	    fos.close();

	    return path;
	}

}
