package com.mlalic.dal.repository;

import java.util.List;

import com.mlalic.model.BloodType;
import com.mlalic.model.MaritalStatus;
import com.mlalic.model.Outpatient;
import com.mlalic.model.Person;
import com.mlalic.model.Personel;
import com.mlalic.model.PersonelOutpatientEvent;
import com.mlalic.model.PersonelType;

import javafx.util.Pair;

public interface IRepository {
	
	//Personel
	int authorizePersonel(String username, String password) throws Exception;
	int insertPersonel(Personel personel, String password) throws Exception;
	void updatePersonel(Personel personel) throws Exception;
	void deletePersonel(int idPersonel) throws Exception;
	Personel getPersonelByID(int idPersonel) throws Exception;
	List<Personel> getPersonel() throws Exception;
	int getPersonelIDByUsernamePassword(String username, String password) throws Exception;
	
	
	//Patients
	int fillMiniRegistrationForm(Outpatient outpatient) throws Exception;
	int fillComprehensiveRegistrationForm(Outpatient outpatient) throws Exception;
	List<Person> getPatientList() throws Exception;
	Outpatient getPatientDetails(int idPatient) throws Exception;
	
	
	//Patients for doctor
	List<Person> getPatientsForDoctor(int idDoctor) throws Exception;
	void insertPatientForDoctor(int idDoctor, int idOutpatient) throws Exception;

	
	//Events
	void insertEvent(PersonelOutpatientEvent event) throws Exception;
	void chargeEvent(int idEvent, int idPaymentType, float amount) throws Exception;
	List<PersonelOutpatientEvent> getEventsForPatientDoctor(int idPatient, int idDoctor) throws Exception;
	List<PersonelOutpatientEvent> GetEventsForPatientForCharge(int idPatient) throws Exception;

	//Personel type
	PersonelType getPersonelType(int id) throws Exception;
	List<PersonelType> getPersonelTypes() throws Exception;

	
	//Blood types
	BloodType getBloodType(int id);
	List<BloodType> getBloodTypes() throws Exception;

	
	//Marital statuses
	MaritalStatus getMaritalStatus(int id);
	List<MaritalStatus> getMaritalStatuses() throws Exception;

	
	//Reports data
	List<Pair<Integer, Integer>> getFollowUpPatients();
	List<Pair<Integer, Integer>> getNewPatients();
	float getAveragePatientsHandled();
	List<Pair<Integer, Float>> getChargedSummaryToday();
	
}

