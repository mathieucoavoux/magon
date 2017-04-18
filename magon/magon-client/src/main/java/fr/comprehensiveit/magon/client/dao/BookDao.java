package fr.comprehensiveit.magon.client.dao;

import java.util.List;
import java.util.Map;

import fr.comprehensiveit.magon.client.book.Book;

public interface BookDao {

	public List<Book> findBookByName(Map<String,String> mapConnection, Map<String,String> mapParameter);
}
