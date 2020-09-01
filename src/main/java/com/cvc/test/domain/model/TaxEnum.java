package com.cvc.test.domain.model;

public enum TaxEnum {

	ZERO_DAYS(0, 3, 0.03, "A"),
	ONE_TO_TEN_DAYS(1, 12, 0, "B"),
	OVER_10(11, 0, 0.08, "C"),
	OVER_20(21, 0, 0.06, "C"),
	OVER_30(31, 0, 0.04, "C"),
	OVER_40(41, 0, 0.02,"C");
	
	private int intervalDays;
	private double tax;
	private double taxPercent;
	private String type;
	
	TaxEnum(int intervalDays, double tax, double taxPercent, String type) {
		this.intervalDays = intervalDays;
		this.tax = tax;
		this.taxPercent = taxPercent;
		this.type = type;
	}

	public static TaxEnum getValue(int intervalDays) {
		if(intervalDays > 40) {
			return TaxEnum.OVER_40;
		}
			
		TaxEnum previous = null;
		
		//It is checked whether the range of days is equal to or greater than the constant. 
		//The value is stored in the PREVIOUS variable. If the next constant does not satisfy the condition, 
		//PREVIOUS is returned
		for (TaxEnum taxEnum : TaxEnum.values()) {
			if(intervalDays >= taxEnum.getIntervalDays()) {
				previous = taxEnum;
			} else {
				if(previous != null) {
					return previous;
				} else {
					return TaxEnum.ZERO_DAYS;
				}
			}
		}
		
		return TaxEnum.ZERO_DAYS;
	}

	public int getIntervalDays() {
		return intervalDays;
	}

	public void setIntervalDays(int intervalDays) {
		this.intervalDays = intervalDays;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(double taxPercent) {
		this.taxPercent = taxPercent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
