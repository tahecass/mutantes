package co.com.mutant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import co.com.mutant.util.Constants;

@Component
public class Configurations {

	@Autowired
	private Constants constants;
	
	@Bean
	public HttpHeaders httpHeaders() {
		HttpHeaders mapHeaders = new HttpHeaders();
		for (int i = 0; i < constants.getHeaders().length; i++) {
			String [] data = constants.getHeaders()[i].split(":");
			mapHeaders.set(data[0], data[1]);
		}
		return mapHeaders;
	}
	

}
