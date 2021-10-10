package co.com.mutant.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsResponse {

    @JsonProperty("countMutantDna")
    private int countMutantDna;
    @JsonProperty("countHumanDna")
    private int countHumanDna;
    @JsonProperty("ratio")
    private double ratio;


    @Builder
    public StatsResponse(int countMutantDna, int countHumanDna, double ratio) {
        this.countHumanDna = countHumanDna;
        this.countMutantDna = countMutantDna;
        this.ratio = ratio;
    }


}
