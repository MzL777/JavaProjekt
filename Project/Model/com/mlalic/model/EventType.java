package com.mlalic.model;

public enum EventType {
	APPOINTMENT(1, "Appointment"),
	LAB_TEST(2, "Lab test"),
	PRESCRIPTION(3, "Prescription"),
	DIAGNOSIS(4, "Diagnosis");

	private final Integer id;
	private final String name;
	
	EventType(Integer id, String name) {
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
