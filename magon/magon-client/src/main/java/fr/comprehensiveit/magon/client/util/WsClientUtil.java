package fr.comprehensiveit.magon.client.util;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WsClientUtil {

	public static Logger logger = LoggerFactory.getLogger(WsClientUtil.class);
	/**
	 * Generate the REST call 
	 * @param function Function to call
	 * @param map Map of parameters
	 * @return end of url
	 * @throws NullPointerException
	 */
	public String generateRestQuery(String function,Map<String,String> map) throws NullPointerException {
		if(function == null || map.size() == 0)
			throw new NullPointerException();
		String restQuery = function+"?";
		for(String key : map.keySet()) {
			restQuery = restQuery.concat(key+"="+map.get(key));
		}
		return restQuery;
	}
	
	/**
	 * Connect to a secure web service and request some values. Return a list of objects
	 * @param clazz type of class used for the response
	 * @param assetType type of asset
	 * @param function Function to request
	 * @param map parameters
	 * @return list of objects
	 */
	public List<?> getWsResult(Type clazz, Map<String,String> mapConnection,String extendUrl) {
		
		String url =  mapConnection.get("ws.url")+"/"+mapConnection.get("ws.mapping")+"/"+extendUrl;
		logger.debug("Request url:"+url);
		HttpHeaders headers = new HttpHeaders();
		 headers.add("Authorization", "Basic " + mapConnection.get("ws.auth.enc"));
		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 RestTemplate restTemplate = new RestTemplate();
		 HttpEntity<String> request = new HttpEntity<String>(headers);
		 ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, List.class);
		 
		 List<?> json = (List<?>) response.getBody();
		 Object[] objArr = JsonUtil.fromJsonArray(json.toString(), clazz);
		 List<?> result = Arrays.asList(objArr);

		 return result;
	}
}
