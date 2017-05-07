package fr.comprehensiveit.magon.client.dao;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import fr.comprehensiveit.magon.client.book.Book;

public class BookDaoImplTest {

	@Test
	public void testFindBookByName() {
		BookDaoImpl bdi = new BookDaoImpl();
		Map<String,String> mapConnection = new HashMap<String,String>();
		mapConnection.put("ws.url","http://localhost:8080/magon-ws");
		mapConnection.put("ws.mapping", "api/book");
		mapConnection.put("ws.auth.enc","dGVzdDp0ZXN0");
		Map<String,String> mapParameters = new HashMap<String,String>();
		String titleExpected = "cambodia";
		mapParameters.put("title", titleExpected);
		//try catch HttpServerErrorException (500)
		List<Book> list = bdi.findBookByName(mapConnection, mapParameters);
		assertEquals(1,list.size());
		for(Book book : list) {
			assertEquals(titleExpected,book.getTitle());
		}
	}
}
