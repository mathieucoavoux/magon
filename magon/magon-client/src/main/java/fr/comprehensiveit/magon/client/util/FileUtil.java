package fr.comprehensiveit.magon.client.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * Get property from the file
	 * @param filename: name of file where the property is stored
	 * @param variable: the name of the property
	 * @return String property value
	 */
	public static String getPropertyValue(String filename,String variable) {
		Properties prop = new Properties();
		InputStream input = null;
		String result;
		String home = System.getProperty("conf.dir");
		try {
			input = new FileInputStream(home+"/"+filename);
			prop.load(input);
			result=prop.getProperty(variable);
		} catch (IOException e) {
			logger.error("Error occured while getting properties: ",e);
			result=null;
		}
		return result;
	}
}
