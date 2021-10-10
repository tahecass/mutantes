package co.com.mutant.util;

import co.com.mutant.model.Errors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ResponseCreatorTest {

    @InjectMocks
    private ResponseCreator responseCreator;


    @Test
    public void httpHeadersTest() {

        //Act
        ResponseEntity<Errors> errorResponse = responseCreator.createErrorResponse("/mutant",
                "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, new ArithmeticException());

        //Assert
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
