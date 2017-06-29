package fr.comprehensiveit.magon.client.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.comprehensiveit.magon.client.book.Book;
import fr.comprehensiveit.magon.client.util.WsClientUtil;

public class BookDaoImpl implements BookDao{
	
	public static Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
	
	public List<Book> findAssetByName(Map<String,String> mapConnection, Map<String,String> mapParameter) {
		String function = "searchByTitle";
		WsClientUtil wcu = new WsClientUtil();
		String extendUrl = wcu.generateRestQuery(function, mapParameter);
		List<Book> list = (List<Book>) wcu.getWsResult(Book[].class, mapConnection, extendUrl);
		return list;
	}
	
	public void printResult(List<Book> list) {
		for(Book book: list) {
			System.out.printf("Book title:%s\n",book.getTitle());
		}
	}
}
