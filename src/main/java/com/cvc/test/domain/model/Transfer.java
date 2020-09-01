package com.cvc.test.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@NamedQuery(
        name="findTransferType",
        query="select t from Transfer t where date(t.transferDate) = date(:transferDate) and date(t.dateSchedule) = date(:dateSchedule) and t.value = :value"
)

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Transfer {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max=6, min=6)
	private String originAccount;
	
	@NotBlank
	@Size(max=6, min=6)
	private String accountDestination;
	
	@DecimalMin(value = "0.0", inclusive = false)
	private Double value;

	private Double tax;
	
	@FutureOrPresent
	private OffsetDateTime transferDate;

	private OffsetDateTime dateSchedule;
}
