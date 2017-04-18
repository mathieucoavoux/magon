package fr.comprehensiveit.magon.client.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadProperties {

	
	public static Logger logger = LoggerFactory.getLogger(LoadProperties.class);
	
	public static Properties loadFile() throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("classpath:web-server.properties");
		prop.load(input);
		return prop;
	
		
	}
	
	public static String getValue(String key) {
		String result = null;
		try {
			Properties prop = loadFile();
			return prop.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Can not load file property :",e);
			e.printStackTrace();
		}
		return result;
	}
}
