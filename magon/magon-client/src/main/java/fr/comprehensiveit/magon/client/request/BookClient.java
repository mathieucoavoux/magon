package fr.comprehensiveit.magon.client.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.comprehensiveit.magon.client.auth.ProxyWsClient;
import fr.comprehensiveit.magon.client.book.Book;
import fr.comprehensiveit.magon.client.dao.BookDao;
import fr.comprehensiveit.magon.client.dao.BookDaoImpl;

public class BookClient {

	public static void printList(List<Book> list) {
		System.out.println("     Id          Title");
		for(Book book : list) {
			System.out.println("      "+book.getId()+"        "+book.getTitle());
		}
	}
	
	public static void main(String args[]) {
		System.out.println("Begin...");
		BookDao bd = (BookDao) new ProxyWsClient().newInstance(new BookDaoImpl()); 
		Map<String,String> mapParameter = new HashMap<String,String>();
		mapParameter.put("title", "cambodia");
		List<Book> list = (List<Book>) bd.findBookByName(null, mapParameter);
		printList(list);
		System.out.println("Completed...");
	}
}
