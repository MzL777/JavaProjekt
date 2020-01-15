package com.mlalic.bl;

import java.util.List;

import com.mlalic.model.EventType;
import com.mlalic.model.Person;
import com.mlalic.model.PersonelOutpatientEvent;

public class PatientsForDoctorHandler extends HandlerBase {
	public List<Person> getPatientsForDoctor(int idDoctor) throws Exception {
		return repository.getPatientsForDoctor(idDoctor);
	}
	
	public void insertPatientForDoctor(int idDoctor, int idOutpatient) throws Exception {
		repository.insertPatientForDoctor(idDoctor, idOutpatient);
	}
	
	public List<PersonelOutpatientEvent> getEventsForPatientDoctor(int idPatient, int idDoctor) throws Exception {
		return repository.getEventsForPatientDoctor(idPatient, idDoctor);
	}
	
	public List<PersonelOutpatientEvent> GetEventsForPatientForCharge(int idPatient) throws Exception {
		return repository.GetEventsForPatientForCharge(idPatient);
	}
	
	public void insertEvent(PersonelOutpatientEvent event) throws Exception {
		repository.insertEvent(event);
	}

	public void chargeEvent(int idEvent, int idPaymentType, float amount) throws Exception {
		repository.chargeEvent(idEvent, idPaymentType, amount);
	}
	
	public EventType getEventType(int id) throws Exception {
		return EventType.values()[id - 1];
	}
	
	public EventType[] getEventTypes() {
		return EventType.values();
	}
}
