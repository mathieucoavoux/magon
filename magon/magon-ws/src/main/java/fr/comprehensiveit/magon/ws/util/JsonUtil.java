package fr.comprehensiveit.magon.ws.util;

import java.util.List;

import com.google.gson.Gson;

public class JsonUtil {

	public static String toJson(List<?> list) {
		String json = new Gson().toJson(list);
		return json;
	}
}
