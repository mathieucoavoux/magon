package fr.comprehensiveit.magon.client.util;

import org.junit.Test;

public class LoadPropertiesTest {

	@Test
	public void testGetValue() {
		System.out.println(LoadProperties.getValue("baseUrl"));
	}
}
