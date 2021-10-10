package co.com.mutant.util;

import co.com.mutant.model.Data;
import co.com.mutant.service.Service;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.logging.Logger;

@Component
@Getter
@Setter
public class ArrayUtilities {

    Logger logger = Logger.getLogger(ArrayUtilities.class.getName());

    private int amountSequenceFound;

    public ArrayUtilities() {
    }

    public String[][] transformArray(ArrayList<Data> dna) {
        String[][] array = new String[dna.size()][dna.size()];

        for (int i = 0; i < dna.size(); i++) {
            String chain = dna.get(i).getDna();
            for (int j = 0; j < chain.length(); j++) {
                array[i][j] = String.valueOf(chain.charAt(j));
            }
        }
        return array;
    }


    public void horizontalGeneExists(String gene, String[][] dnaArray) {
        int row = 0, counter = 0;
        while (row < dnaArray.length) {
            for (int column = 0; column < dnaArray.length; column++) {
                if (dnaArray[row][column].equals(gene)) {
                    counter++;
                    if (counter == 4) {
                        amountSequenceFound++;
                        logger.info("GEN: " + gene + " Encontrado: " + counter + " veces en la fila: " + row);
                    }
                } else {
                    counter = 0;
                }
            }
            row++;
            counter = 0;

        }
    }


    public void verticalGeneExists(String gene, String[][] dnaArray) {
        int column = 0, counter = 0;
        while (column < dnaArray.length) {
            for (int row = 0; row < dnaArray.length; row++) {
                if (dnaArray[row][column].equals(gene)) {
                    counter++;
                    if (counter == 4) {
                        amountSequenceFound++;
                        logger.info("GEN: " + gene + " Encontrado: " + counter +
                                " veces en la columna: " + column);
                    }
                } else {
                    counter = 0;
                }
            }
            column++;
            counter = 0;
        }
    }


    public void obliqueGeneExists(String gene, String[][] dnaArray) {
        int row = 0, column = 0, counter = 0;
        while (row < dnaArray.length) {
            while (column < dnaArray.length) {
                if (dnaArray[row][column].equals(gene)) {
                    counter++;
                    row++;
                    if (counter == 4) {
                        amountSequenceFound++;
                        logger.info("GEN: " + gene + " Encontrado: " + counter +
                                " veces en la intercepcion fila: " + row + " columna " + column);
                    }
                } else {
                    counter = 0;
                }

                column++;
            }
            row++;
            counter = 0;
        }
    }

    public void printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                System.out.print(array[i][j].toString());
            }
            System.out.println("\n");
        }
    }

}
