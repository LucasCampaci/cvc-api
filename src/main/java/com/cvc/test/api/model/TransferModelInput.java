package com.cvc.test.api.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TransferModelInput {

	@NotBlank
	@Size(max=6, min=6)
	private String originAccount;
	
	@NotBlank
	@Size(max=6, min=6)
	private String accountDestination;
	
	@DecimalMin(value = "0.0", inclusive = false)
	private Double value;
	
	@FutureOrPresent
	private OffsetDateTime transferDate;
}
