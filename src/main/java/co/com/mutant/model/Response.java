package co.com.mutant.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.com.mutant.entity.Humans;

@Getter
@Setter
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    @JsonProperty("isMutant")
    private boolean isMutant;
    @JsonProperty("sequences")
    private int sequences;

    @Builder
    public Response(boolean isMutant, int sequences) {
        this.isMutant = isMutant;
        this.sequences = sequences;
    }


}
