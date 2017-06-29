package fr.comprehensiveit.magon.client.util;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectLookup {

	public static Logger logger = LoggerFactory.getLogger(ObjectLookup.class);
	
	protected String packageName = "fr.comprehensiveit.magon.client.dao.";
	
	private static String filename = "connection.properties";
	
	public Object objectResolver(String objectName) {
		try {
			Class<?> clazz = Class.forName(packageName+simpleObjectBinding(objectName));
			return clazz.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Class<?> objectClassResolver(String objectName) {
		try {
			return Class.forName(packageName+simpleObjectBinding(objectName));
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected String simpleObjectBinding(String simpleName) {
		return FileUtil.getPropertyValue(filename,simpleName);
	}
}
