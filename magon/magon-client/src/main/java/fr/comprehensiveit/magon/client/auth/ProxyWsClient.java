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

	/**
	 * Instancie un objet specifie en parametre.
	 * @param assetType Un objet instancie implementant les methodes d une interface
	 * @return l objet instancie
	 */
	public static Object newInstance(Object assetType) {
		//Vérifie si le handler instancié implémente au moins une interface
		Type[] types;
		try {
			//Class<?> assetTypeClass = Class.forName("fr.comprehensiveit.magon.client.dao."+assetType).getName().getClass();
			types = assetType.getClass().getGenericInterfaces();
			int numTypes = types.length;
			logger.debug("asset type name : "+assetType.getClass().getName());
			logger.debug("number of interfaces implemented :"+numTypes);
			if( numTypes == 0) {
				logger.error("La classe de l'objet instancié n'implémente aucune interface.");
				return null;
			}
			String interfaceName = "";
			//Retourne l objet demandé
			Class<?>[] interfacesClass = new Class<?>[numTypes];
			for(int indType=0;indType<numTypes;indType++) {
				String interfaceNameDirty = types[indType].getTypeName();
				String[] interfaceNameArr = interfaceNameDirty.split("<");
				interfaceName = interfaceNameArr[0];
				logger.debug("interface name: "+interfaceName);
				interfacesClass[indType] = Class.forName(interfaceName);
			}
			return Proxy.newProxyInstance(assetType.getClass().getClassLoader(), interfacesClass, new ProxyWsClientHandler(assetType));
		} catch (ClassNotFoundException e) {
			logger.error("La classe n'a pas été trouvée",e);
			return null;
		}
		
	}
}
