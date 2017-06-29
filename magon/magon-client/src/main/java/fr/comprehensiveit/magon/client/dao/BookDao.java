package fr.comprehensiveit.magon.client.dao;

import java.util.List;
import java.util.Map;

import fr.comprehensiveit.magon.client.book.Book;

public interface BookDao {

	/**
	 * Find book by name
	 * @param mapConnection: for the connection to the web service
	 * @param mapParameter: filter
	 * @return List<Book>
	 */
	public List<Book> findAssetByName(Map<String,String> mapConnection, Map<String,String> mapParameter);
	
	public void printResult(List<Book> list);
	
}
