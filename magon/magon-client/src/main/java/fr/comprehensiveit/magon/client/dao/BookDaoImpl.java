package fr.comprehensiveit.magon.client.dao;

import java.util.List;
import java.util.Map;

import fr.comprehensiveit.magon.client.book.Book;
import fr.comprehensiveit.magon.client.util.WsClientUtil;

public class BookDaoImpl implements BookDao{
	
	public List<Book> findBookByName(Map<String,String> mapConnection, Map<String,String> mapParameter) {
		String function = "findBookByName";
		WsClientUtil wcu = new WsClientUtil();
		String extendUrl = wcu.generateRestQuery(function, mapParameter);
		List<Book> list = (List<Book>) wcu.getWsResult(Book[].class, mapConnection, extendUrl);
		return list;
	}
}
