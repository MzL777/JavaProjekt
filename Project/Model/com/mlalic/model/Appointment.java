package com.mlalic.model;

import java.time.LocalDate;

public class Appointment {
	Integer IDAppointment;
	Integer OutpatientID;
	Integer PersonelID;
	LocalDate AppointedDate;

	public Appointment(Integer iDAppointment, Integer outpatientID, Integer personelID, LocalDate appointedDate) {
		super();
		IDAppointment = iDAppointment;
		OutpatientID = outpatientID;
		PersonelID = personelID;
		AppointedDate = appointedDate;
	}

	public Integer getIDAppointment() {
		return IDAppointment;
	}

	public void setIDAppointment(Integer iDAppointment) {
		IDAppointment = iDAppointment;
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

	public LocalDate getAppointedDate() {
		return AppointedDate;
	}

	public void setAppointedDate(LocalDate appointedDate) {
		AppointedDate = appointedDate;
	}

}
