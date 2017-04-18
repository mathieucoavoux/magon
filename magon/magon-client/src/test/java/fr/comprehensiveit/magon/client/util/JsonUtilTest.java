package fr.comprehensiveit.magon.client.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.comprehensiveit.magon.client.book.Book;

public class JsonUtilTest {

	@Test
	public void testFromJsonUtil() {
		String json = "{\"id\": 1, \"title\": \"cambodia\"}";
		Book book = (Book) JsonUtil.fromJson(json, Book.class);
		String expectedTitle = "cambodia";
		assertEquals(expectedTitle,book.getTitle());
	}
	
	@Test
	public void testFromJsonUtilArray() {
		String json = "[{\"id\": 1, \"title\": \"cambodia\"}]";
		Book[] books = (Book[]) JsonUtil.fromJsonArray(json, Book[].class);
		String expectedTitle = "cambodia";
		for(Book book : books) {
			assertEquals(expectedTitle,book.getTitle());
		}
		
	}
}
