package fr.comprehensiveit.magon.client.auth;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import fr.comprehensiveit.magon.client.util.FileUtil;
import fr.comprehensiveit.magon.client.util.JsonUtil;

public class ProxyWsClient {
	
	public static Logger logger = LoggerFactory.getLogger(ProxyWsClient.class);

	private String baseUrl;
	
	private String assetMapping;
	
	private String basePath;
	
	private String authStringEnc;
	
	public String custVar = "hello";
	
	/**
	 * Set a web service connection based on its type. Configurations items are stored in a properties file
	 * @param assetType type of asset that the client will request
	 * @throws NullPointerException
	 */
	public void setWsConnection(String assetType) throws NullPointerException {
		String filename = "clientws/connection.properties";
		if(filename == null)
			throw new NullPointerException();
		baseUrl = FileUtil.getPropertyValue(filename, "baseUrl");
		if(baseUrl == null)
			throw new NullPointerException();
		assetMapping = FileUtil.getPropertyValue(filename, assetType+"Mapping");
		if(assetMapping == null)
			throw new NullPointerException();
		String username = FileUtil.getPropertyValue(filename, "username");
		if(username == null)
			throw new NullPointerException();
		String password = FileUtil.getPropertyValue(filename, "password");
		if(password == null)
			throw new NullPointerException();
		String authString = username+":"+password;
		authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
		
	}
	
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
	public List<?> getWsResult(Type clazz, String assetType, String function,Map<String,String> map) {
		setWsConnection(assetType);
		String extendUrl = generateRestQuery(function,map);
		String url =  baseUrl+"/"+assetMapping+"/"+extendUrl;
		HttpHeaders headers = new HttpHeaders();
		 headers.add("Authorization", "Basic " + authStringEnc);
		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 RestTemplate restTemplate = new RestTemplate();
		 HttpEntity<String> request = new HttpEntity<String>(headers);
		 ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, List.class);
		 
		 List<?> json = (List<?>) response.getBody();
		 Object[] objArr = JsonUtil.fromJsonArray(json.toString(), clazz);
		 List<?> result = Arrays.asList(objArr);

		 return result;
	}
	
	/**
	 * Instancie un objet specifie en parametre.
	 * @param assetType Un objet instancie implementant les methodes d une interface
	 * @return l objet instancie
	 */
	public Object newInstance(Object assetType) {
		logger.debug("new Instance");
		//Vérifie si le handler instancié implémente au moins une interface
		Type[] types = assetType.getClass().getGenericInterfaces();	
		int numTypes = types.length;
		logger.debug("asset type name : "+assetType.getClass().getName());
		logger.debug("number of interfaces implemented :"+numTypes);
		if( numTypes == 0) {
			logger.error("La classe de l'objet instancié n'implémente aucune interface.");
			return null;
		}
		String interfaceName = "";
		//Retourne l objet demandé
		try {
			Class<?>[] interfacesClass = new Class<?>[numTypes];
			for(int indType=0;indType<numTypes;indType++) {
				interfaceName = types[indType].getTypeName();
				logger.debug("interface name: "+interfaceName);
				interfacesClass[indType] = Class.forName(interfaceName);
			}
			return Proxy.newProxyInstance(assetType.getClass().getClassLoader(), interfacesClass, new ProxyWsClientHandler(assetType));
		} catch (ClassNotFoundException e) {
			logger.error("La classe "+interfaceName+" n'a pas été trouvée",e);
			return null;
		}
	}
}
