package co.com.mutant.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;





@NoArgsConstructor
@Getter
@Setter
@Validated
public class Human {
    @Valid
    private  ArrayList<Data> dna;
    private String category;

    @Builder
    public  Human (ArrayList<Data> dna, String category){
        this.dna = dna;
        this.category= category;
    }
}
