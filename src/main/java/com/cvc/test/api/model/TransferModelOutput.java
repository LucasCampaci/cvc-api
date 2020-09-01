package com.cvc.test.api.model;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class TransferModelOutput {

	private Double value;
	private Double tax;
	private OffsetDateTime transferDate;
	private OffsetDateTime dateSchedule;
	private String type;
}
