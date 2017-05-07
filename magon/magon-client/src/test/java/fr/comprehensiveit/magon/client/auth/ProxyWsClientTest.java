package fr.comprehensiveit.magon.client.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.comprehensiveit.magon.client.book.Book;
import fr.comprehensiveit.magon.client.book.Book4Test;
import fr.comprehensiveit.magon.client.book.Book4TestImpl;
import fr.comprehensiveit.magon.client.dao.BookDao;
import fr.comprehensiveit.magon.client.dao.BookDaoImpl;

public class ProxyWsClientTest {
	
	@Ignore
	@Test
	public void testSetWsConnection() {
		ProxyWsClient pwc = new ProxyWsClient();
		try {
			pwc.setWsConnection("book");	
			assertTrue(true);
		}catch(NullPointerException e) {
			fail("NullPointerException catched");
		}
	}
	
	@Ignore
	@Test
	public void testGenerateRestQuery() {
		ProxyWsClient pwc = new ProxyWsClient();
		Map<String,String> map = new HashMap<String,String>();
		map.put("title", "cambodia");
		String function = "searchByTitle";
		String result = pwc.generateRestQuery(function, map);
		String resultExpected = "searchByTitle?title=cambodia";
		assertEquals(resultExpected,result);
	}
	
	@Ignore
	@Test
	public void testGetWsResult() {
		String assetType = "book";
		String function = "searchByTitle";
		Map<String,String> map = new HashMap<String,String>();
		map.put("title", "cambodia");
		ProxyWsClient pwc = new ProxyWsClient();
		List<Book> list = (List<Book>) pwc.getWsResult(Book[].class,assetType, function, map);
		for(Book book : list) {
			System.out.println(book.getTitle());
		}
	}
	
	/*
	@Test
	public void testGetProxyWs() {
		try {
			BookDao bdi =  (BookDao) new ProxyWsClient().newInstance(new BookDaoImpl());
			Map map = new HashMap();
			map.put("entry1", "lol");
			System.out.println(bdi.getFavoriteBook(map));
		}catch(Exception e) {
			e.printStackTrace();
			//e.getCause();
		}
		
		//bdi.getFavoriteBook();
		
	}
	*/
	
	@Test
	public void testConnection() {
		Book4Test bft = (Book4Test) new ProxyWsClient().newInstance(new Book4TestImpl());
		Map<String,String> mapParameters = new HashMap<String,String>();
		mapParameters.put("parameter1","value1");
		Map<String,String> mapCon = bft.getMapConnection(null, mapParameters);
		String passEncExpected = "dGVzdDp0ZXN0";
		String urlExpected = "http://localhost:8080/magon-ws";
		String mappingExpected = "api/book";
		assertEquals(passEncExpected,mapCon.get("ws.auth.enc"));
		assertEquals(urlExpected,mapCon.get("ws.url"));
		assertEquals(mappingExpected,mapCon.get("ws.mapping"));
	}
}
