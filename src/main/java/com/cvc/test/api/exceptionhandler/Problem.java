package com.cvc.test.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Problem {

	private Integer status;
	private OffsetDateTime dateTime;
	private String title;
	private List<Field> fields;

	@Data
	public static class Field {
		private String name;
		private String message;
		
		public Field() {}

		public Field(String name, String message) {
			this.name = name;
			this.message = message;
		}
	}
}