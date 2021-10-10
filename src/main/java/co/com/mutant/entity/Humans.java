package co.com.mutant.entity;

import java.io.Serializable;
import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import co.com.mutant.model.Data;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModelProperty;

@Validated
@Getter
@Setter
public class Humans implements Serializable{

	private static final long serialVersionUID = 8209525583865422469L;
	
	@Id
	private String id;
	private String dna;
	private String category;

	@Builder
	public Humans( String dna, String category) {
		this.dna = dna;
		this.category = category;


	}

	

}
