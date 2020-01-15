package com.mlalic.dal.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mlalic.dal.datasource.DataSourceSingleton;
import com.mlalic.model.BloodType;
import com.mlalic.model.MaritalStatus;
import com.mlalic.model.Outpatient;
import com.mlalic.model.Person;
import com.mlalic.model.Personel;
import com.mlalic.model.PersonelOutpatientEvent;
import com.mlalic.model.PersonelType;

import javafx.util.Pair;

public class SqlRepository implements IRepository {

	private static final String GET_PERSONEL_BY_ID = "{ CALL GetPersonelByID (?) }";
	private static final String GET_PERSONEL = "{ CALL GetPersonel }";
	private static final String INSERT_PERSONEL = "{ CALL InsertPersonel (?,?,?,?,?,?,?,?) }";
	private static final String UPDATE_PERSONEL = "{ CALL UpdatePersonel (?,?,?,?,?,?) }";
	private static final String DELETE_PERSONEL = "{ CALL DeletePersonel (?) }";
	private static final String AUTH_PERSONEL = "{ CALL AuthPersonel (?,?,?) }";
	private static final String GET_PERSONEL_ID_BY_USERNAME_PASSWORD = "{ CALL GetPersonelIdByUsernamePassword (?,?,?) }";

	private static final String GET_PERSONEL_TYPE_BY_ID = "{ CALL GetPersonelType (?) }";
	private static final String GET_PERSONEL_TYPES = "{ CALL GetPersonelTypes }";

	private static final String GET_BLOOD_TYPE_BY_ID = "{ CALL GetBloodType (?) }";
	private static final String GET_BLOOD_TYPES = "{ CALL GetBloodTypes }";

	private static final String GET_MARITAL_STATUS_BY_ID = "{ CALL GetMaritalStatus (?) }";
	private static final String GET_MARITAL_STATUSES = "{ CALL GetMaritalStatuses }";

	private static final String GET_PATIENT_DETAILS = "{ CALL GetPatientsDetails (?) }";
	private static final String GET_PATIENT_LIST = "{ CALL GetPatientsList }";
	private static final String GET_PATIENT_FOR_DOCTOR = "{ CALL GetPatientsForDoctor (?) }";
	private static final String INSERT_PATIENT_FOR_DOCTOR = "{ CALL InsertPatientForDoctor (?,?) }";

	private static final String FILL_COMPREHENSIVE_REGISTRATION_FORM = "{ CALL FillComprehensiveRegistrationForm (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
	private static final String FILL_MINI_REGISTRATION_FORM = "{ CALL FillMiniRegistrationForm (?,?,?,?,?,?,?,?,?,?,?,?,?) }";
	
	private static final String GET_EVENTS_FOR_PATIENT_FOR_CHARGE = "{ CALL GetEventsForPatientForCharge (?) }";
	private static final String GET_EVENTS_FOR_PATIENT_DOCTOR = "{ CALL GetEventsForPatientDoctor (?,?) }";
	private static final String INSERT_EVENT = "{ CALL InsertEvent (?,?,?,?,?) }";
	private static final String CHARGE_EVENT = "{ CALL ChargeEvent (?,?,?) }";

	private static final String GET_FOLLOW_UP_PATIENTS = "{ CALL GetFollowUpPatients }";
	private static final String GET_NEW_PATIENTS = "{ CALL GetNewPatients }";
	private static final String GET_AVERAGE_PATIENTS_HANDLED = "{ CALL GetAveragePatientsHandledMonth }";
	private static final String GET_CHARGED_SUMMARY_TODAY = "{ CALL getChargedSummaryToday }";
	
	
	
	//personel
	@Override
	public int authorizePersonel(String username, String password) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(AUTH_PERSONEL)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.registerOutParameter(3, Types.INTEGER);
			stmt.executeUpdate();

			// return code: 1 - success, 2 - username does not exist, 3 - invalid password
			return stmt.getInt(3);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public int insertPersonel(Personel personel, String password) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(INSERT_PERSONEL)) {
			stmt.setInt(1, personel.getPersonelTypeID());
			stmt.setString(2, personel.getUsername());
			stmt.setString(3, personel.getFirstName());
			stmt.setString(4, personel.getMiddleName());
			stmt.setString(5, personel.getSurname());
			stmt.setString(6, personel.getTitle());
			stmt.setString(7, password);
			stmt.registerOutParameter(8, Types.INTEGER);
			stmt.executeUpdate();

			return stmt.getInt(8);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public void updatePersonel(Personel personel) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_PERSONEL)) {
			stmt.setInt(1, personel.getIDPersonel());
			stmt.setString(2, personel.getUsername());
			stmt.setString(3, personel.getFirstName());
			stmt.setString(4, personel.getMiddleName());
			stmt.setString(5, personel.getSurname());
			stmt.setString(6, personel.getTitle());
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public void deletePersonel(int idPersonel) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_PERSONEL)) {
			stmt.setInt(1, idPersonel);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public Personel getPersonelByID(int idPersonel) throws Exception {
		Personel personel = null;
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_PERSONEL_BY_ID)) {
			stmt.setInt(1, idPersonel);
			ResultSet resultSet = stmt.executeQuery();
					
			if (resultSet.next()) {
				personel = new Personel(resultSet.getInt("IDPersonel"), resultSet.getInt("PersonelTypeID"),
						resultSet.getString("Username"), resultSet.getString("FirstName"),
						resultSet.getString("MiddleName"), resultSet.getString("Surname"),
						resultSet.getString("Title"));
			}
			return personel;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<Personel> getPersonel() throws Exception {
		List<Personel> personel = new ArrayList<>();
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_PERSONEL);
				ResultSet resultSet = stmt.executeQuery()) {
			while (resultSet.next()) {
				personel.add(new Personel(resultSet.getInt("IDPersonel"), resultSet.getInt("PersonelTypeID"),
						resultSet.getString("Username"), resultSet.getString("FirstName"),
						resultSet.getString("MiddleName"), resultSet.getString("Surname"),
						resultSet.getString("Title")));
			}
			return personel;
		} catch (Exception e) {
			throw e;
		}
	}
	public int getPersonelIDByUsernamePassword(String username, String password) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(GET_PERSONEL_ID_BY_USERNAME_PASSWORD)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.registerOutParameter(3, Types.INTEGER);
			stmt.executeUpdate();

			return stmt.getInt(3);
		} catch (Exception e) {
			throw e;
		}
	}

	
	//patients
	@Override
	public int fillMiniRegistrationForm(Outpatient outpatient) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(FILL_MINI_REGISTRATION_FORM)) {
			stmt.setString(1, outpatient.getPerson().getFirstName());
			stmt.setString(2, outpatient.getPerson().getMiddleName());
			stmt.setString(3, outpatient.getPerson().getSurname());
			stmt.setString(4, outpatient.getPerson().getSex().toString());
			stmt.setDate(5, java.sql.Date.valueOf(outpatient.getPerson().getDateOfBirth()));
			stmt.setString(6, outpatient.getPerson().getTelephoneHome());
			stmt.setString(7, outpatient.getPerson().getTelephoneWork());
			stmt.setString(8, outpatient.getNextOfKin().getFirstName());
			stmt.setString(9, outpatient.getNextOfKin().getMiddleName());
			stmt.setString(10, outpatient.getNextOfKin().getSurname());
			stmt.setString(11, outpatient.getNextOfKinRelationship());
			stmt.setString(12, outpatient.getStatementOfComplaint());
			stmt.registerOutParameter(13, Types.INTEGER);
			stmt.executeUpdate();
			
			return stmt.getInt(13);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public int fillComprehensiveRegistrationForm(Outpatient outpatient) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(FILL_COMPREHENSIVE_REGISTRATION_FORM)) {
			stmt.setString(1, outpatient.getPerson().getFirstName());
			stmt.setString(2, outpatient.getPerson().getMiddleName());
			stmt.setString(3, outpatient.getPerson().getSurname());
			stmt.setString(4, outpatient.getPerson().getSex().toString());
			stmt.setDate(5, java.sql.Date.valueOf(outpatient.getPerson().getDateOfBirth()));
			stmt.setString(6, outpatient.getPerson().getTelephoneHome());
			stmt.setString(7, outpatient.getPerson().getTelephoneWork());
			stmt.setString(8, outpatient.getPerson().getMobile());
			stmt.setString(9, outpatient.getPerson().getPager());
			stmt.setString(10, outpatient.getPerson().getFax());
			stmt.setString(11, outpatient.getPerson().getEmail());
			stmt.setString(12, outpatient.getPerson().getPresentAddress());
			stmt.setString(13, outpatient.getPerson().getPermanentAddress());
			stmt.setString(14, outpatient.getNextOfKin().getFirstName());
			stmt.setString(15, outpatient.getNextOfKin().getMiddleName());
			stmt.setString(16, outpatient.getNextOfKin().getSurname());
			stmt.setString(17, outpatient.getNextOfKinRelationship());
			stmt.setString(18, outpatient.getNextOfKin().getPresentAddress());
			stmt.setString(19, outpatient.getNextOfKin().getTelephoneHome());
			stmt.setString(20, outpatient.getNextOfKin().getTelephoneWork());
			stmt.setString(21, outpatient.getNextOfKin().getMobile());
			stmt.setString(22, outpatient.getNextOfKin().getPager());
			stmt.setString(23, outpatient.getNextOfKin().getFax());
			stmt.setString(24, outpatient.getNextOfKin().getEmail());
			stmt.setInt(25, outpatient.getMaritalStatusID());
			stmt.setInt(26, outpatient.getBloodTypeID());
			stmt.setBoolean(27, outpatient.isComprehensiveFormFilled());
			stmt.setInt(28, outpatient.getNumberOfDependants());
			stmt.setFloat(29, outpatient.getHeight());
			stmt.setFloat(30, outpatient.getWeight());
			stmt.setString(31, outpatient.getOccupation());
			stmt.setInt(32, outpatient.getGrossAnnualIncome());
			stmt.setBoolean(33, outpatient.isVegetarian());
			stmt.setBoolean(34, outpatient.isSmoker());
			stmt.setInt(35, outpatient.getAverageCigarettesPerDay());
			stmt.setBoolean(36, outpatient.isConsumeAlcoholicBeverage());
			stmt.setInt(37, outpatient.getAverageDrinksPerDay());
			stmt.setBoolean(38, outpatient.isUseStimulants());
			stmt.setString(39, outpatient.getStimulants());
			stmt.setInt(40, outpatient.getCoffeOrTeaPerDay());
			stmt.setInt(41, outpatient.getSoftDrinksPerDay());
			stmt.setBoolean(42, outpatient.isHaveRegularMeals());
			stmt.setBoolean(43, outpatient.isPredominantlyEatsHome());
			stmt.setString(44, outpatient.getStatementOfComplaint());
			stmt.setString(45, outpatient.getHistoryOfPreviousTreatment());
			stmt.setString(46, outpatient.getPhysicianHospitalTreated());
			stmt.setBoolean(47, outpatient.isDiabetic());
			stmt.setBoolean(48, outpatient.isHypertensive());
			stmt.setBoolean(49, outpatient.isCardiacCondition());
			stmt.setBoolean(50, outpatient.isRaspiratoryCondition());
			stmt.setBoolean(51, outpatient.isDigestiveCondition());
			stmt.setBoolean(52, outpatient.isOrthopedicCondition());
			stmt.setBoolean(53, outpatient.isMuscularCondition());
			stmt.setBoolean(54, outpatient.isNeurologicalCondition());
			stmt.setString(55, outpatient.getKnownAllergies());
			stmt.setString(56, outpatient.getKnownAdverseReactionToSpecificDrugs());
			stmt.setString(57, outpatient.getMayorSurgeriesHistory());
			stmt.registerOutParameter(58, Types.INTEGER);
			stmt.executeUpdate();
			
			return stmt.getInt(58);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<Person> getPatientList() throws Exception {
		List<Person> patients = new ArrayList<>();
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_PATIENT_LIST);
				ResultSet resultSet = stmt.executeQuery()) {
			while (resultSet.next()) {
				patients.add(
					new Person(
						(Integer)resultSet.getInt("IDOutpatient"),
						resultSet.getString("FirstName"),
						resultSet.getString("MiddleName"),
						resultSet.getString("Surname"),
						(Character)resultSet.getString("Sex").charAt(0),
						LocalDate.parse((CharSequence) resultSet.getDate("DateOfBirth").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						resultSet.getString("TelephoneHome"),
						resultSet.getString("TelephoneWork"),
						resultSet.getString("Mobile"),
						resultSet.getString("Pager"),
						resultSet.getString("Fax"),
						resultSet.getString("Email"),
						resultSet.getString("PresentAddress"),
						resultSet.getString("PermanentAddress")));
			}
			return patients;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public Outpatient getPatientDetails(int idPatient) throws Exception {
		Outpatient outpatient = null;
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_PATIENT_DETAILS)) {
			stmt.setInt(1, idPatient);
			ResultSet resultSet = stmt.executeQuery();
			
			if (resultSet.next()) {
				outpatient =
						new Outpatient(
							(Integer) resultSet.getInt("IDOutpatient"),
								new Person(
									(Integer) resultSet.getInt("IDPerson"),
									resultSet.getString("FirstName"),
									resultSet.getString("MiddleName"),
									resultSet.getString("Surname"),
									(Character) resultSet.getString("Sex").charAt(0),
									LocalDate.parse((CharSequence) resultSet.getDate("DateOfBirth").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
									resultSet.getString("TelephoneHome"),
									resultSet.getString("TelephoneWork"),
									resultSet.getString("Mobile"),
									resultSet.getString("Pager"),
									resultSet.getString("Fax"),
									resultSet.getString("Email"),
									resultSet.getString("PresentAddress"),
									resultSet.getString("PermanentAddress")),
								new Person((Integer) resultSet.getInt("nokIDPerson"),
										resultSet.getString("nokFirstName"),
									resultSet.getString("nokMiddleName"),
									resultSet.getString("nokSurname"),
									(Character) '/',
									LocalDate.MIN,
									resultSet.getString("nokTelephoneHome"),
									resultSet.getString("nokTelephoneWork"),
									resultSet.getString("nokMobile"),
									resultSet.getString("nokPager"),
									resultSet.getString("nokFax"),
									resultSet.getString("nokEmail"),
									resultSet.getString("nokAddress"),
									resultSet.getString("nokAddress")),
							resultSet.getString("nokRelationship"),
							(Integer) resultSet.getInt("MaritalStatus"),
							(Integer) resultSet.getInt("BloodTypeID"),
							(Boolean) resultSet.getBoolean("ComprehensiveFormFilled"),
							(Integer) resultSet.getInt("NumberOfDependants"),
							(Float) resultSet.getFloat("Height"),
							(Float) resultSet.getFloat("Weight"),
							resultSet.getString("Occupation"),
							(Integer) resultSet.getInt("GrossAnnualIncome"),
							(Boolean) resultSet.getBoolean("Vegetarian"),
							(Boolean) resultSet.getBoolean("Smoker"),
							(Integer) resultSet.getInt("AverageCigarettesPerDay"),
							(Boolean) resultSet.getBoolean("ConsumeAlcoholicBeverage"),
							(Integer) resultSet.getInt("AverageDrinksPerDay"),
							(Boolean) resultSet.getBoolean("UseStimulants"),
							resultSet.getString("Stimulants"),
							(Integer) resultSet.getInt("CoffeOrTeaPerDay"),
							(Integer) resultSet.getInt("SoftDrinksPerDay"),
							(Boolean) resultSet.getBoolean("HaveRegularMeals"),
							(Boolean) resultSet.getBoolean("PredominantlyEatsHome"),
							resultSet.getString("StatementOfComplaint"),
							resultSet.getString("HistoryOfPreviousTreatment"),
							resultSet.getString("PhysicianHospitalTreated"),
							(Boolean) resultSet.getBoolean("Diabetic"),
							(Boolean) resultSet.getBoolean("Hypertensive"),
							(Boolean) resultSet.getBoolean("CardiacCondition"),
							(Boolean) resultSet.getBoolean("RaspiratoryCondition"),
							(Boolean) resultSet.getBoolean("DigestiveCondition"),
							(Boolean) resultSet.getBoolean("OrthopedicCondition"),
							(Boolean) resultSet.getBoolean("MuscularCondition"),
							(Boolean) resultSet.getBoolean("NeurologicalCondition"),
							resultSet.getString("KnownAllergies"),
							resultSet.getString("KnownAdverseReactionToSpecificDrugs"),
							resultSet.getString("MayorSurgeriesHistory"));
			}
			return outpatient;
		} catch (Exception e) {
			throw e;
		}
	}

	
	//patients for doctor
	@Override
	public List<Person> getPatientsForDoctor(int idDoctor) throws Exception {
		List<Person> patients = new ArrayList<>();
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_PATIENT_FOR_DOCTOR)) {
			stmt.setInt(1, idDoctor);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				patients.add(
					new Person(
						(Integer)resultSet.getInt("IDOutpatient"),
						resultSet.getString("FirstName"),
						resultSet.getString("MiddleName"),
						resultSet.getString("Surname"),
						(Character)resultSet.getString("Sex").charAt(0),
						LocalDate.parse((CharSequence) resultSet.getDate("DateOfBirth").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						resultSet.getString("TelephoneHome"),
						resultSet.getString("TelephoneWork"),
						resultSet.getString("Mobile"),
						resultSet.getString("Pager"),
						resultSet.getString("Fax"),
						resultSet.getString("Email"),
						resultSet.getString("PresentAddress"),
						resultSet.getString("PermanentAddress")));
			}
			return patients;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public void insertPatientForDoctor(int idDoctor, int idOutpatient) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(INSERT_PATIENT_FOR_DOCTOR)) {
			stmt.setInt(1, idOutpatient);
			stmt.setInt(2, idDoctor);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	//appointments
	@Override
	public void insertEvent(PersonelOutpatientEvent event) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(INSERT_EVENT)) {
			stmt.setInt(1, event.getPersonelID());
			stmt.setInt(2, event.getOutpatientID());
			stmt.setDate(3, java.sql.Date.valueOf(event.getAppointedDate()));
			stmt.setInt(4, event.getEventTypeID());
			stmt.setString(5, event.getComment());
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public void chargeEvent(int idEvent, int idPaymentType, float amount) throws Exception {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CHARGE_EVENT)) {
			stmt.setInt(1, idEvent);
			stmt.setInt(2, idPaymentType);
			stmt.setFloat(3, amount);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<PersonelOutpatientEvent> getEventsForPatientDoctor(int idPatient, int idDoctor) throws Exception {
		List<PersonelOutpatientEvent> events = new ArrayList<>();
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_EVENTS_FOR_PATIENT_DOCTOR)) {
			stmt.setInt(1, idPatient);
			stmt.setInt(2, idDoctor);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				events.add(
					new PersonelOutpatientEvent(
						(Integer)resultSet.getInt("IDEvent"),
						(Integer)resultSet.getInt("OutpatientID"),
						(Integer)resultSet.getInt("PersonelID"),
						(Integer)resultSet.getInt("EventTypeID"),
						LocalDate.parse((CharSequence) resultSet.getDate("AppointedDate").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						resultSet.getString("Comment")));
			}
			return events;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<PersonelOutpatientEvent> GetEventsForPatientForCharge(int idPatient) throws Exception {
		List<PersonelOutpatientEvent> events = new ArrayList<>();
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_EVENTS_FOR_PATIENT_FOR_CHARGE)) {
			stmt.setInt(1, idPatient);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				events.add(
					new PersonelOutpatientEvent(
						(Integer)resultSet.getInt("IDEvent"),
						(Integer)resultSet.getInt("OutpatientID"),
						(Integer)resultSet.getInt("PersonelID"),
						(Integer)resultSet.getInt("EventTypeID"),
						LocalDate.parse((CharSequence) resultSet.getDate("AppointedDate").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						resultSet.getString("Comment")));
			}
			return events;
		} catch (Exception e) {
			throw e;
		}
	}

	
	//personel types
	@Override
	public PersonelType getPersonelType(int id) throws Exception {
		PersonelType personelType = null;
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_PERSONEL_TYPE_BY_ID)) {
			stmt.setInt(1, id);
			ResultSet resultSet = stmt.executeQuery();
			
			if (resultSet.next()) {
				personelType = new PersonelType(resultSet.getInt("IDPersonnelType"), resultSet.getString("Name"));
			}
			return personelType;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<PersonelType> getPersonelTypes() throws Exception {
		List<PersonelType> personelTypes = new ArrayList<>();
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_PERSONEL_TYPES);
				ResultSet resultSet = stmt.executeQuery()) {
			while (resultSet.next()) {
				personelTypes.add(new PersonelType(resultSet.getInt("IDPersonnelType"), resultSet.getString("Name")));
			}
			return personelTypes;
		} catch (Exception e) {
			throw e;
		}
	}

	
	//blood types
	@Override
	public BloodType getBloodType(int id) {
		BloodType bloodType = null;
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_BLOOD_TYPE_BY_ID)) {
			stmt.setInt(1, id);
			ResultSet resultSet = stmt.executeQuery();
			
			if (resultSet.next()) {
				bloodType = new BloodType(resultSet.getInt("IDBloodType"), resultSet.getString("Name"));
			}
			return bloodType;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<BloodType> getBloodTypes() throws Exception {
		List<BloodType> bloodTypes = new ArrayList<>();
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_BLOOD_TYPES);
				ResultSet resultSet = stmt.executeQuery()) {
			while (resultSet.next()) {
				bloodTypes.add(new BloodType(resultSet.getInt("IDBloodType"), resultSet.getString("Name")));
			}
			return bloodTypes;
		} catch (Exception e) {
			throw e;
		}
	}

	
	//marital statuses
	@Override
	public MaritalStatus getMaritalStatus(int id) {
		MaritalStatus maritalStatus = null;
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_MARITAL_STATUS_BY_ID)) {
			stmt.setInt(1, id);
			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				maritalStatus = new MaritalStatus(resultSet.getInt("IDMaritalStatus"), resultSet.getString("Name"));
			}
			return maritalStatus;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<MaritalStatus> getMaritalStatuses() throws Exception {
		List<MaritalStatus> maritalStatuses = new ArrayList<>();
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_MARITAL_STATUSES);
				ResultSet resultSet = stmt.executeQuery()) {
			while (resultSet.next()) {
				maritalStatuses
						.add(new MaritalStatus(resultSet.getInt("IDMaritalStatus"), resultSet.getString("Name")));
			}
			return maritalStatuses;
		} catch (Exception e) {
			throw e;
		}
	}

	
	//Reports data
	@Override
	public List<Pair<Integer, Integer>> getFollowUpPatients() {
		List<Pair<Integer, Integer>> fromDB = new ArrayList<>();
		for (int i = 0; i < 31; i++) {
			fromDB.add(new Pair<>(30 - i, 0)); 
		}
		
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_FOLLOW_UP_PATIENTS);
				ResultSet resultSet = stmt.executeQuery()) {
			while (resultSet.next()) {
				fromDB.set(30 - resultSet.getInt("Date"), new Pair<Integer, Integer>(resultSet.getInt("Date"), resultSet.getInt("count")));
				//fromDB.add(new Pair<Integer, Integer>(resultSet.getInt("Date"), resultSet.getInt("count")));
			}
			
			return fromDB;
		} catch (Exception e) {
			//throw e;
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Pair<Integer, Integer>> getNewPatients(){
		List<Pair<Integer, Integer>> fromDB = new ArrayList<>();
		for (int i = 0; i < 31; i++) {
			fromDB.add(new Pair<>(30 - i, 0)); 
		}
		
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_NEW_PATIENTS);
				ResultSet resultSet = stmt.executeQuery()) {
			while (resultSet.next()) {
				fromDB.set(30 - resultSet.getInt("Date"), new Pair<Integer, Integer>(resultSet.getInt("Date"), resultSet.getInt("count")));
				//fromDB.add(new Pair<Integer, Integer>(resultSet.getInt("Date"), resultSet.getInt("count")));
			}
			
			return fromDB;
		} catch (Exception e) {
			//throw e;
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public float getAveragePatientsHandled() {
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_AVERAGE_PATIENTS_HANDLED);
				ResultSet resultSet = stmt.executeQuery()) {
			
			if (resultSet.next()) {
				return resultSet.getFloat("Average");
			}
			
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	@Override
	public List<Pair<Integer, Float>> getChargedSummaryToday() {
		List<Pair<Integer, Float>> data = new ArrayList<Pair<Integer,Float>>();
		DataSource dataSource = DataSourceSingleton.getInstance();
		try (Connection con = dataSource.getConnection();
				CallableStatement stmt = con.prepareCall(GET_CHARGED_SUMMARY_TODAY);
				ResultSet resultSet = stmt.executeQuery()) {
			while (resultSet.next()) {
				data.add(new Pair<Integer, Float>(resultSet.getInt("EventTypeID"), resultSet.getFloat("Amount")));
			}
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
