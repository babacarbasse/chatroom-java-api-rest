package com.esp.chatroom;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertyValue {
	InputStream inputStream;
 
	public Properties getPropValues() throws IOException {
		Properties prop = new Properties();
		try {
			String propFileName = "config.properties";
 
			inputStream = this.getClass().getClassLoader().getResourceAsStream("com/esp/chatroom/resources/"+propFileName);
	
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			// get the property value and print it out
			String namingUserRmi = prop.getProperty("namingUserRmi");
			System.out.println(namingUserRmi + " property");
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return prop;
	}
}
