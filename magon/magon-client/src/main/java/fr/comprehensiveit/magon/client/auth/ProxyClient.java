package fr.comprehensiveit.magon.client.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

public class ProxyClient {

	@Value("myvar")
	public String myVar;
	
	public String getMyVar() {
		return myVar;
	}
}
