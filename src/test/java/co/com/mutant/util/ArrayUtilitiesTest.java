package co.com.mutant.util;

import co.com.mutant.model.Data;
import co.com.mutant.model.Human;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ArrayUtilitiesTest {

    @InjectMocks
    private ArrayUtilities arrayUtilities;

    @Mock
    private ArrayUtilities arrayUtilitiesMock;

    @Test
    public void transformArrayTest(){

        //Act
        String[][] array = arrayUtilities.transformArray(buildHuman().getDna());

        //Assert
        assertThat(array).isNotNull();
        assertThat(array.length).isGreaterThan(0);

    }


    @Test
    public void horizontalGeneExistsTest(){

        //Act
        arrayUtilities.horizontalGeneExists("C", getMutantDna());

        //Assert
        Mockito.verify(arrayUtilitiesMock,
                Mockito.atMost(1)).horizontalGeneExists("C", getMutantDna());


    }


    @Test
    public void verticalGeneExistsTest(){

        //Act
        arrayUtilities.verticalGeneExists("C", getMutantDna());

        //Assert
        Mockito.verify(arrayUtilitiesMock,
                Mockito.atMost(1)).verticalGeneExists("C", getMutantDna());


    }


    @Test
    public void obliqueGeneExistsTest(){

        //Act
        arrayUtilities.obliqueGeneExists("C", getMutantDna());

        //Assert
        Mockito.verify(arrayUtilitiesMock,
                Mockito.atMost(1)).obliqueGeneExists("C", getMutantDna());


    }


    @Test
    public void printArrayTest(){

        //Act
        arrayUtilities.printArray(getMutantDna());

        //Assert
        Mockito.verify(arrayUtilitiesMock,
                Mockito.atMost(1)).printArray(getMutantDna());


    }
    private String[][] getMutantDna() {
        String[][] dna = {{"A", "T", "G", "C", "G", "A"},
                {"C", "A", "G", "T", "G", "C"}, {"T", "T", "A", "T", "G", "T"},
                {"A", "G", "A", "A", "G", "G"}, {"C", "C", "C", "C", "T", "A"},
                {"T", "C", "A", "C", "T", "G"}};

        return dna;

    }


    private void addData(ArrayList<Data> ListDna, String dna) {
        ListDna.add(Data.builder().dna(dna).build());

    }

    private Human buildHuman() {
        Gson gson = new Gson();
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
