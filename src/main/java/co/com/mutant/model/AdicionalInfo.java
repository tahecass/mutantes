package co.com.mutant.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class AdicionalInfo {

    @JsonProperty("fields")
   static List<String> fields = new ArrayList<>();

    public  AdicionalInfo(){}

    public AdicionalInfo(List<String> list){
        fields = list;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public List<String> getFields() {
        return fields;
    }

    public static void setFields(List<String> list) {
        fields = list;
    }

}
