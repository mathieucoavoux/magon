package fr.comprehensiveit.magon.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.comprehensiveit.magon.client.auth.ProxyWsClient;
import fr.comprehensiveit.magon.client.book.Book;
import fr.comprehensiveit.magon.client.dao.BookDao;
import fr.comprehensiveit.magon.client.dao.BookDaoImpl;
import fr.comprehensiveit.magon.client.util.ObjectLookup;


public class App 
{
	public static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
    	BasicConfigurator.configure();
    	logger.debug("Starting magon-client");
    	if(System.getProperty("conf.dir") == null) {
    		logger.error("Please set conf.dir variable");
    		return;
    	}
    	String assetType = args[0];
    	String function =  args[1];
    	String filterName = args[2];
    	String filterValue = args[3];
    	Map<String,String> map = new HashMap<String,String>();
		map.put(filterName,filterValue);
		ObjectLookup ol = new ObjectLookup();
		//Ex binding: bookHandler="BookDaoImpl"
		Object assetTypeObject = ol.objectResolver(assetType+"Handler");
		Object objectProxy = ProxyWsClient.newInstance(assetTypeObject);
		//Ex binding: book="BookDao"
		Class<?> objectFinalClass = ol.objectClassResolver(assetType);
		//Get list of books
		Object listAssets = getAsset(objectProxy,objectFinalClass,map);
		//Display the list
		printAssetList(listAssets,objectFinalClass,assetTypeObject);
    }
   
   /**
    * Get requested asset from a web service
    * @param object: Instantiate class where the method findAssetByName is implemented
    * @param args: arguments requested by findAssetByName method
    * @return a list of assets
    */
   protected static Object getAsset(Object objectProxy,Class<?> objectFinalClass, Map<String,String> map) {
	   Class<?>[] methodObjectType = new Class[]{Map.class,Map.class};
		Object[] argsObj = new Object[]{null,map};
		try {
			//Method method = resultType.getMethod("printResult", methodObjectType);
			//Method to get the book
			Method method = objectFinalClass.getMethod("findAssetByName", methodObjectType);
			//Get the book, return a list of book
			return method.invoke(objectProxy,argsObj );
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getCause();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
   }
   
   /**
    * Print list of assets
    * @param listAssets
    * @param objectFinalClass: Interface which is using the handler
    * @param objectHandler: Class implementing the interface
    */
   public static void printAssetList(Object listAssets,Class<?> objectFinalClass,Object objectHandler) {
	 //Define the argument type that the printResult method expect:
	 Class<?>[] printResArgType = new Class[]{List.class};
	 try {
		Method methodPrint = objectFinalClass.getMethod("printResult", printResArgType);
		methodPrint.invoke(objectHandler,listAssets);
	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 	
   }
}
