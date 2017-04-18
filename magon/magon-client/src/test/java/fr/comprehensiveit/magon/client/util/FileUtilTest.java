package fr.comprehensiveit.magon.client.util;

import org.junit.Test;

public class FileUtilTest {

	@Test
	public void testGetPropertyValues() {
		System.out.println("test :"+FileUtil.getPropertyValue("clientws/connection.properties", "baseUrl"));
	}
}
