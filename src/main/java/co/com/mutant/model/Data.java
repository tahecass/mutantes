package co.com.mutant.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import co.com.mutant.util.RegexConstants;


@Getter
@Setter
@NoArgsConstructor
@Validated
public class Data {
    @Pattern(regexp = RegexConstants.DNA_VALUE_REGEX, message = RegexConstants.FAIL_MESSAGE)
    private String dna;

    @Builder
    public Data(String dna) {
        this.dna = dna;
    }
}
