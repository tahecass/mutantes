package co.com.mutant.controller;

import co.com.mutant.model.*;
import co.com.mutant.service.Service;
import com.mongodb.MongoCommandException;
import com.mongodb.ServerAddress;
import org.bson.BsonDocument;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {


    @InjectMocks
    private Controller controller;
    @Mock
    Service service;


    @Test
    public void verifyMutantSuccessfulTest() {
        //Arrange
        Human human = buildHuman();
        when(service.verifyMutant(human)).thenReturn(
                ResponseEntity.ok(Response.builder()
                        .sequences(3)
                        .isMutant(true)
                        .build())
        );
        //Act
        ResponseEntity response = controller.verifyMutant(human);

        //Assert
        assertThat(response).isNotNull();
        assertThat(((Response) response.getBody()).getSequences()).isNotNull();
        Assert.assertThat(response.getStatusCode(), Matchers.is(HttpStatus.OK));
    }


    @Test
    public void getStatsSuccessfulTest() throws BusinessException {
        //Arrange
        when(service.getStats()).thenReturn(
                ResponseEntity.ok(StatsResponse.builder()
                        .ratio(0.4)
                        .countMutantDna(40)
                        .countHumanDna(100)
                        .build())
        );
        //Act
        ResponseEntity response = controller.stats();

        //Assert
        assertThat(response).isNotNull();
        assertThat(((StatsResponse) response.getBody()).getCountHumanDna()).isNotNull();
        Assert.assertThat(response.getStatusCode(), Matchers.is(HttpStatus.OK));
    }



    @Test
    public void VerifyMutantMongoCommandExceptionTest() {
        //Arrange
        Human human = buildHuman();

        MongoCommandException exception = new MongoCommandException(new BsonDocument(), new ServerAddress());
        Mockito.doThrow(exception)
                .when(service).verifyMutant(Mockito.any());

        try {


            //Act
            ResponseEntity response = controller.verifyMutant(human);

        } catch (MongoCommandException e) {

            //Assert
            assertThat(e).isNotNull();
            assertThat(e.getErrorMessage()).isNotNull();

        }


    }



    @Test
    public void statsMongoCommandExceptionTest() throws BusinessException{
        //Arrange

        MongoCommandException exception = new MongoCommandException(new BsonDocument(), new ServerAddress());
        Mockito.doThrow(exception)
                .when(service).getStats();

        try {


            //Act
            ResponseEntity response = controller.stats();

        } catch (MongoCommandException e) {

            //Assert
            assertThat(e).isNotNull();
            assertThat(e.getErrorMessage()).isNotNull();

        }


    }


    @Test
    public void verifyMutantExceptionTest() {
        //Arrange
        Human human = buildHuman();

        NullPointerException exception = new NullPointerException();
        Mockito.doThrow(exception)
                .when(service).verifyMutant(Mockito.any());

        try {


            //Act
            ResponseEntity response = controller.verifyMutant(human);

        } catch (Exception e) {

            //Assert
            assertThat(e).isNotNull();
            assertThat(e.getMessage()).isNotNull();

        }

    }





    @Test
    public void statsExceptionTest() throws BusinessException {
        //Arrange

        NullPointerException exception = new NullPointerException();
        Mockito.doThrow(exception)
                .when(service).getStats();

        try {


            //Act
            ResponseEntity response = controller.stats();

        } catch (Exception e) {

            //Assert
            assertThat(e).isNotNull();
            assertThat(e.getMessage()).isNotNull();

        }

    }


    @Test
    public void statsBusinessExceptionTest() {
        //Arrange

        BusinessException exception = BusinessException.builder().message("An error occurred while calculating the ratio," +
                " divisor is zero, please try again").build();

        try {
            Mockito.doThrow(exception)
                    .when(service).getStats();


            //Act
            ResponseEntity response = controller.stats();

        } catch (BusinessException e) {

            //Assert
            assertThat(e).isNotNull();
            assertThat(e.getMessage()).isNotNull();
            assertThat(e.getMessage()).isNotNull();

        }

    }

    private void addData(ArrayList<Data> ListDna, String dna) {
        ListDna.add(Data.builder().dna(dna).build());

    }

    private Human buildHuman() {
        ArrayList<Data> ListDna = new ArrayList<>();
        addData(ListDna, "ATGCGG");
        addData(ListDna, "CAGTGC");
        addData(ListDna, "TTATGT");
        addData(ListDna, "AGAAGG");
        addData(ListDna, "CCCCTA");
        addData(ListDna, "TCACTG");

        return Human.builder()
                .dna(ListDna).build();

    }
}
