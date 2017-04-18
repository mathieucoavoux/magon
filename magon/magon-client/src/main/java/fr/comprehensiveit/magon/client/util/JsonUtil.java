package fr.comprehensiveit.magon.client.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtil {
	

	/**
	 * Return a single object of a class specified in parameter. The value of this object will be set according to the JSON string.
	 * @param json String of keys and values. Those fields must exists in the Class
	 * @param clazz Type of object to return
	 * @return Generic Object which should be casted
	 */
	public static Object fromJson(String json,Class<?> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json,clazz);
	}
	
	/**
	 * Return an array of objects of a class specified in parameter. The value of this object will be set according to the JSON string.
	 * @param json String of keys and values. Those fields must exists in the Class
	 * @param clazz Type of object to return
	 * @return Generic array of objects which should be casted
	 */
	public static Object[] fromJsonArray(String json,Type clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json,clazz);
	}
}
