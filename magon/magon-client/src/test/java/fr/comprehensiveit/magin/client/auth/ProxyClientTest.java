package fr.comprehensiveit.magin.client.auth;

import org.junit.Test;

import fr.comprehensiveit.magon.client.auth.ProxyClient;

public class ProxyClientTest {

	@Test
	public void testGetMyvar() {
		ProxyClient pc = new ProxyClient();
		System.out.println(pc.getMyVar());
	}
}
