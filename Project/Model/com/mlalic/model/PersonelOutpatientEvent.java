package com.mlalic.model;

import java.time.LocalDate;

public class PersonelOutpatientEvent {
	Integer IDEvent;
	Integer OutpatientID;
	Integer PersonelID;
	Integer EventTypeID;
	LocalDate appointedDate;
	String Comment;

	public PersonelOutpatientEvent(Integer iDEvent, Integer outpatientID, Integer personelID, Integer eventTypeID,
			LocalDate appointedDate, String comment) {
		super();
		IDEvent = iDEvent;
		OutpatientID = outpatientID;
		PersonelID = personelID;
		EventTypeID = eventTypeID;
		this.appointedDate = appointedDate;
		Comment = comment;
	}
	
	public Integer getIDEvent() {
		return IDEvent;
	}
	public void setIDEvent(Integer iDEvent) {
		IDEvent = iDEvent;
	}
	public Integer getOutpatientID() {
		return OutpatientID;
	}
	public void setOutpatientID(Integer outpatientID) {
		OutpatientID = outpatientID;
	}
	public Integer getPersonelID() {
		return PersonelID;
	}
	public void setPersonelID(Integer personelID) {
		PersonelID = personelID;
	}
	public Integer getEventTypeID() {
		return EventTypeID;
	}
	public void setEventTypeID(Integer eventTypeID) {
		EventTypeID = eventTypeID;
	}
	public LocalDate getAppointedDate() {
		return appointedDate;
	}
	public void setAppointedDate(LocalDate appointedDate) {
		this.appointedDate = appointedDate;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}

	@Override
	public String toString() {
		return appointedDate + ": " + EventType.values()[EventTypeID - 1] + " > " + Comment;
	}
	
	
}
