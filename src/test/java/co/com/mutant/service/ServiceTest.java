package co.com.mutant.service;

import co.com.mutant.entity.Humans;
import co.com.mutant.model.*;
import co.com.mutant.repository.Repository;
import co.com.mutant.util.ArrayUtilities;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

    @InjectMocks
    private Service service;
    @Mock
    Repository repository;
    @Mock
    ArrayUtilities arrayUtilities;
    @Mock
    private HttpHeaders httpHeaders;


    @Test
    public void VerifyMutantTrueSuccessfulTest() throws BusinessException {
        //Arrange
        Human human = buildMutant();
        when(arrayUtilities.transformArray(human.getDna())).thenReturn(getMutantDna());
        when(arrayUtilities.getAmountSequenceFound()).thenReturn(3);

        //Act
        ResponseEntity response = service.verifyMutant(human);

        //Assert
        assertThat(response).isNotNull();
        assertThat(((Response) response.getBody()).getSequences()).isNotNull();
        assertThat(((Response) response.getBody()).isMutant()).isEqualTo(true);
        Assert.assertThat(response.getStatusCode(), Matchers.is(HttpStatus.OK));
    }

    @Test
    public void VerifyMutantFalseSuccessfulTest() throws BusinessException {
        //Arrange
        Human human = buildHuman();
        when(arrayUtilities.transformArray(human.getDna())).thenReturn(getHumanDna());
        when(arrayUtilities.getAmountSequenceFound()).thenReturn(0);

        //Act
        ResponseEntity response = service.verifyMutant(human);

        //Assert
        assertThat(response).isNotNull();
        assertThat(((Response) response.getBody()).getSequences()).isNotNull();
        assertThat(((Response) response.getBody()).isMutant()).isEqualTo(false);
        Assert.assertThat(response.getStatusCode(), Matchers.is(HttpStatus.FORBIDDEN));
    }


    @Test
    public void statsSuccessfulTest() throws BusinessException {
        //Arrange
        when(repository.findByCategory("Mutant")).thenReturn(selectHumans(true,true));
        when(repository.findByCategory("Human")).thenReturn(selectHumans(true,true));

        //Act
        ResponseEntity response = service.getStats();

        //Assert
        assertThat(response).isNotNull();
        assertThat(((StatsResponse) response.getBody()).getCountHumanDna()).isNotNull();
        assertThat(((StatsResponse) response.getBody()).getCountMutantDna()).isNotNull();
        ;
        Assert.assertThat(response.getStatusCode(), Matchers.is(HttpStatus.OK));
    }


    @Test
    public void statsWhenHumansIsZeroSuccessfulTest() throws BusinessException {
        //Arrange
        when(repository.findByCategory("Mutant")).thenReturn(selectHumans(false,false));
        when(repository.findByCategory("Human")).thenReturn(selectHumans( false,false));


        try {
            //Act
            ResponseEntity response = service.getStats();

        } catch (BusinessException e) {

            //Assert
            assertThat(e).isNotNull();
            assertThat(e.getMessage()).isNotNull();

        }
    }


    private String[][] getMutantDna() {
        String[][] dna = {{"A", "T", "G", "C", "G", "A"},
                {"C", "A", "G", "T", "G", "C"}, {"T", "T", "A", "T", "G", "T"},
                {"A", "G", "A", "A", "G", "G"}, {"C", "C", "C", "C", "T", "A"},
                {"T", "C", "A", "C", "T", "G"}};

        return dna;

    }

    private String[][] getHumanDna() {
        String[][] dna = {{"A", "T", "G", "C", "G", "A"},
                {"C", "A", "G", "T", "G", "C"}, {"T", "T", "A", "T", "T", "T"}, {"A", "G", "A", "C", "G", "G"},
                {"G", "C", "G", "T", "C", "A"}, {"T", "C", "A", "C", "T", "G"}};

        return dna;

    }

    private void addHumans(List<Humans> listHumans, String dna, String category) {
        listHumans.add(Humans.builder().category(category)
                .dna(dna).build());

    }

    private List<Humans> selectHumans(boolean withMutant, boolean withHumans) {
        List<Humans> listHumans = new ArrayList<>();
        Gson gson = new Gson();

        if (withMutant) {
            String mutantDna = gson.toJson("{\n" +
                    "  \"dna\": [\n" +
                    "    \"ATGCGA\",\n" +
                    "    \"CAGTGC\",\n" +
                    "    \"TTATGT\",\n" +
                    "    \"AGAAGG\",\n" +
                    "    \"CCCCTA\",\n" +
                    "    \"TCACTG\"\n" +
                    "  ]\n" +
                    "}");

            addHumans(listHumans, mutantDna, "Mutant");
            addHumans(listHumans, mutantDna, "Mutant");

        }
        if (withHumans) {

            String humanDna = gson.toJson("{\n" +
                    "  \"dna\": [\n" +
                    "    \"ATGCGA\",\n" +
                    "    \"CGGTGC\",\n" +
                    "    \"TTATAT\",\n" +
                    "    \"AGAAGG\",\n" +
                    "    \"GCCCTA\",\n" +
                    "    \"TCACTG\"\n" +
                    "  ]\n" +
                    "}");
            addHumans(listHumans, humanDna, "Human");

        }
        return listHumans;
    }

    private void addData(ArrayList<Data> ListDna, String dna) {
        ListDna.add(Data.builder().dna(dna).build());

    }

    private Human buildMutant() {
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


    private Human buildHuman() {
        ArrayList<Data> ListDna = new ArrayList<>();
        addData(ListDna, "ATGCGA");
        addData(ListDna, "CAGTGC");
        addData(ListDna, "TTATTT");
        addData(ListDna, "AGACGG");
        addData(ListDna, "GCGTCA");
        addData(ListDna, "TCACTG");

        return Human.builder()
                .dna(ListDna).build();

    }

}
