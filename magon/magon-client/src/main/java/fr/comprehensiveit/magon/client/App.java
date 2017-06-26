package fr.comprehensiveit.magon.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.comprehensiveit.magon.client.auth.ProxyWsClient;
import fr.comprehensiveit.magon.client.book.Book;


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
		ProxyWsClient pwc = new ProxyWsClient();
    	Class<?> clazz = getClassType(assetType);
    	if(clazz == null) {
    		System.out.println("Invalid type");
    	}
    	List<?> list = (List<?>) pwc.getWsResult(clazz,assetType, function, map);
    	printResult(list,assetType);
    }
    
    /**
     * Get the class of the asset type
     * @param assetType: type of the asset
     * @return Class<?>: class of the asset
     */
    protected static Class<?> getClassType(String assetType) {
    	switch(assetType) {
    	case "book":
    		return Book[].class;
    	default:
    		return null;
    	}
    }
    
    /**
     * Dispatch the result to print according to the asset type
     * @param list: result list
     * @param assetType: type of the asset
     */
    protected static void printResult(List<?> list, String assetType) {
    	switch(assetType) {
    	case "book":
    		List<Book> listBook = (List<Book>) list;
    		printBookResult(listBook);
    		break;
    	default:
    		System.out.println("Invalid type. How did I get there by the way?");
    	}
    }
    
    /**
     * Print the list of books
     * @param list
     */
    protected static void printBookResult(List<Book> list) {
    	for(Book book: list) {
    		System.out.printf("Book id: %s,title: %s\n",book.getId(),book.getTitle());
    	}
    }
}
