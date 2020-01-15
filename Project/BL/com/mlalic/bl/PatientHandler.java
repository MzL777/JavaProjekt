package com.mlalic.bl;

import java.util.List;

import com.mlalic.model.BloodType;
import com.mlalic.model.MaritalStatus;
import com.mlalic.model.Outpatient;
import com.mlalic.model.Person;

public class PatientHandler extends HandlerBase {
	public void fillComprehensiveRegistrationForm(Outpatient outpatient) throws Exception {
		repository.fillComprehensiveRegistrationForm(outpatient);
	}
	
	public void fillMiniRegistrationForm(Outpatient outpatient) throws Exception {
		repository.fillMiniRegistrationForm(outpatient);
	}
	
	public List<Person> getPatientList() throws Exception {
		return repository.getPatientList();
	}

	public Outpatient getPatientDetails(int idPatient) throws Exception {
		return repository.getPatientDetails(idPatient);
	}

	public BloodType getBloodType(int id) {
		return repository.getBloodType(id);
	}
	
	public List<BloodType> getBloodTypes() throws Exception {
		return repository.getBloodTypes();
	}
	
	public MaritalStatus getMaritalStatus(int id) {
		return repository.getMaritalStatus(id);
	}
	
	public List<MaritalStatus> getMaritalStatuses() throws Exception {
		return repository.getMaritalStatuses();
	}
	
}
