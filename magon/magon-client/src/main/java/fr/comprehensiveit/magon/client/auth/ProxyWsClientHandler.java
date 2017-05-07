package fr.comprehensiveit.magon.client.auth;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.comprehensiveit.magon.client.util.FileUtil;

public class ProxyWsClientHandler implements InvocationHandler {

	private static String filename = "clientws/connection.properties";
	
	public static Logger logger = LoggerFactory.getLogger(ProxyWsClientHandler.class);
	
	private Object classInstance;
	private Map mapParams;
	
	public ProxyWsClientHandler(Object classInstance) {
		logger.debug("new instance of proxywsclient handler");
		this.classInstance = classInstance;
	}

	private Map<String,String> addConnectionDetails(String classType) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("ws.url", FileUtil.getPropertyValue(filename, "baseUrl"));
		map.put("ws.mapping",FileUtil.getPropertyValue(filename, classType+"Mapping"));
		String authString = FileUtil.getPropertyValue(filename, "username")+":"+FileUtil.getPropertyValue(filename, "password");
		map.put("ws.auth.enc", new String(Base64.encodeBase64(authString.getBytes())));
		return map;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = new Object();
		
		logger.debug("method "+method.getName()+" belongs to "+method.getDeclaringClass().getSimpleName());
		//Set WS connection details
		mapParams = addConnectionDetails(method.getDeclaringClass().getSimpleName());
		
		//Add existing params
		int maxObj = args.length;
		List parameters = new ArrayList();
		//The first parameter is not used through the proxy
		//Add new Map in the
		parameters.add(mapParams);
		for(int indObj = 1;indObj < maxObj; indObj++) {
			parameters.add(args[indObj]);
		}
		
		try{
			result = method.invoke(classInstance, parameters.toArray());
		}catch(Exception e) {
			logger.error("error",e);
		}
		return result;
	}
}
