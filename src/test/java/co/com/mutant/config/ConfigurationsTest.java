package co.com.mutant.config;

import co.com.mutant.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ConfigurationsTest {

    @InjectMocks
    private Configurations configurations;
    @Mock
    private Constants constants;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void httpHeadersTest() {
        //Arrange
        String data = "Content-Type:application/json;charset=utf-8,X-Permitted-Cross-Domain-Policies:none,X-Frame-Options:DENY,Cache-Control:no-store; max-age=0; must-revalidate,Content-Security-Policy:default-src 'self'; frame-ancestors 'none'; reflected-xss block,Pragma:no-cache,Strict-Transport-Security:max-age=31536000; includeSubDomains,X-XSS-Protection:1; mode=block,X-Content-Type-Options:nosniff";
        String[] mock = { data };
        when(constants.getHeaders()).thenReturn(mock);

        //Act
        HttpHeaders response = configurations.httpHeaders();

        //Assert
        assertThat(response).isNotNull();
    }


}
