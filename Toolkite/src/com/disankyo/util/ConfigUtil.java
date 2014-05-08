package com.disankyo.util;

//import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author xujianxin
 * @time Oct 14, 2013 5:28:03 PM
 */
public class ConfigUtil {

	static String fileName = 
//		System.getProperty("file.separator")
		 System.getProperty("user.dir")
		+ System.getProperty("file.separator") + "src"
		+ System.getProperty("file.separator")
		+ "log4j.properties";
	
	public static void initLog4j() {
		
		System.out.println(fileName);
		
//		PropertyConfigurator.configure(fileName);
	}
	
}
