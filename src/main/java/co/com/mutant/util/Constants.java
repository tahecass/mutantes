package co.com.mutant.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

	@Value("${http.headers}")
	private String[] headers;
	
	
	public String[] getHeaders() {
		return headers;
	}
}
