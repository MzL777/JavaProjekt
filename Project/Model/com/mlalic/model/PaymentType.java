package com.mlalic.model;

public enum PaymentType {
	CASH(1, "Cash"),
	CREDIT_CARD(2, "Credit card");

	private final Integer id;
	private final String name;
	
	PaymentType(Integer id, String name) {
	   this.id=id;
	   this.name=name;
   }

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
