package co.com.mutant.util;

import jdk.internal.reflect.Reflection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ConstantsTest {

    @InjectMocks
    private Constants constants;



    @Before
    public void init() {
        //Arrange
        String data = "Content-Type:application/json;charset=utf-8,X-Permitted-Cross-Domain-Policies:none,X-Frame-Options:DENY,Cache-Control:no-store; max-age=0; must-revalidate,Content-Security-Policy:default-src 'self'; frame-ancestors 'none'; reflected-xss block,Pragma:no-cache,Strict-Transport-Security:max-age=31536000; includeSubDomains,X-XSS-Protection:1; mode=block,X-Content-Type-Options:nosniff";
        String[] mock = { data };
        ReflectionTestUtils.setField(constants, "headers", mock);
    }


    @Test
    public void transformArrayTest(){

        //Act
        String[] header = constants.getHeaders();

        //Assert
        assertThat(header).isNotNull();

    }

}
