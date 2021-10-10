package co.com.mutant.service;

import co.com.mutant.model.Human;
import co.com.mutant.model.Response;
import co.com.mutant.model.StatsResponse;
import co.com.mutant.util.ArrayUtilities;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mongodb.MongoCommandException;

import co.com.mutant.entity.Humans;
import co.com.mutant.model.BusinessException;
import co.com.mutant.repository.Repository;

import java.util.List;
import java.util.logging.Logger;


@org.springframework.stereotype.Service
public class Service {
    Logger logger = Logger.getLogger(Service.class.getName());

    @Autowired
    Repository repository;
    @Autowired
    ArrayUtilities arrayUtilities;

    @Autowired
    private HttpHeaders httpHeaders;

    private final String[] genes = {"A", "T", "C", "G"};
    private static final String MESSAGE_RATIO_EXCEPTION = "An error occurred while calculating the ratio, divisor is zero, please try again";


    public ResponseEntity<Response> verifyMutant(Human human) throws MongoCommandException {
        String[][] array = arrayUtilities.transformArray(human.getDna());
        setCategory(isMutant(array), human);
        saveDna(human);
        return buildResponse(httpHeaders);
    }


    private void setCategory(boolean mutant, Human human) {
        if (mutant) {
            human.setCategory("Mutant");
        } else {
            human.setCategory("Human");
        }
    }

    private boolean isMutant(String[][] dnaArray) {

        arrayUtilities.setAmountSequenceFound(0);

        for (int i = 0; i < genes.length; i++) {
            arrayUtilities.horizontalGeneExists(genes[i], dnaArray);
            arrayUtilities.verticalGeneExists(genes[i], dnaArray);
            arrayUtilities.obliqueGeneExists(genes[i], dnaArray);
        }

        logger.info("Secuencia " + arrayUtilities.getAmountSequenceFound());
        return arrayUtilities.getAmountSequenceFound() > 1 ? true : false;
    }

    private void saveDna(Human human) {
        Gson jsonFormat = new Gson();
        repository.save(Humans.builder()
                .dna(jsonFormat.toJson(human.getDna()).toString())
                .category(human.getCategory())
                .build());
    }


    private ResponseEntity<Response> buildResponse(HttpHeaders httpHeaders) {
        ResponseEntity<Response> responseResponseEntity;
        boolean isMutant = arrayUtilities.getAmountSequenceFound() > 1 ? true : false;
        Response response = Response.builder()
                .isMutant(isMutant)
                .sequences(arrayUtilities.getAmountSequenceFound())
                .build();

        if (isMutant) {
            responseResponseEntity = new ResponseEntity<Response>(response,
                    httpHeaders, HttpStatus.OK);
        } else {
            responseResponseEntity = new ResponseEntity<Response>(response,
                    httpHeaders, HttpStatus.FORBIDDEN);
        }
        return responseResponseEntity;
    }

    public ResponseEntity<StatsResponse> getStats() throws MongoCommandException, BusinessException {
        List<Humans> listMutants = repository.findByCategory("Mutant");
        List<Humans> listHumans = repository.findByCategory("Human");

        StatsResponse response = StatsResponse.builder()
                .countHumanDna(listHumans.size())
                .countMutantDna(listMutants.size())
                .ratio(getRatio(listMutants, listHumans)).build();
        return new ResponseEntity<StatsResponse>(response, httpHeaders, HttpStatus.OK);
    }


    private double getRatio(List<Humans> listMutants, List<Humans> listHumans) throws BusinessException {

        if (listHumans.size() == 0) {
            throw BusinessException.builder()
                    .message(MESSAGE_RATIO_EXCEPTION)
                    .build();
           }

        return (Double.parseDouble(String.valueOf(listMutants.size()))/
                Double.parseDouble(String.valueOf(listHumans.size())));
    }

}
