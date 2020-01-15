package com.mlalic.console;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.mlalic.bl.PatientHandler;
import com.mlalic.bl.PatientsForDoctorHandler;
import com.mlalic.bl.PersonelHandler;
import com.mlalic.bl.ReportHandler;
import com.mlalic.model.EventType;
import com.mlalic.model.Outpatient;
import com.mlalic.model.Person;
import com.mlalic.model.Personel;
import com.mlalic.model.PersonelOutpatientEvent;
import com.mlalic.model.PersonelType;

import javafx.util.Pair;

public class Main {

	private static final PatientsForDoctorHandler PATIENT_FOR_DOCTOR_HANDLER = new PatientsForDoctorHandler();
	private static final PersonelHandler PERSONEL_HANDLER = new PersonelHandler();
	private static final PatientHandler PATIENT_HANDLER = new PatientHandler();
	private static final ReportHandler REPORT_HANDLER = new ReportHandler();

	private static int idPersonel = -1;
	private static Scanner scanner = null;

	public Main() {}

	public static void start() {
		try (Scanner scanR = new Scanner(System.in)) {
			scanner = scanR;
			int loginTypeID = login();
			do {
				System.out.println();
				System.out.println();
				showOptions(loginTypeID);
				int op = readOption();
				System.out.println();

				performOption(loginTypeID, op);

			} while (true);
		} catch (Exception e) {
			System.out.println("Exception caught:");
			e.printStackTrace();
		}
	}

	private static int login() throws Exception {
		String username = null, password = null;
		boolean loggedIn = false;

		while (!loggedIn) {
			username = getString("Username:");
			password = getString("Password:");
			System.out.println();

			switch (PERSONEL_HANDLER.authorizePersonel(username, password)) {
			case 1:
				System.out.println("Login successfull!");
				loggedIn = true;
				break;
			case 2:
				System.out.println("Username does not exist!");
				continue;
			case 3:
				System.out.println("Invalid password!");
				continue;
			}
		}

		idPersonel = PERSONEL_HANDLER.getPersonelIDByUsernamePassword(username, password);
		Personel personel = PERSONEL_HANDLER.getPersonel(idPersonel);

		PersonelType pType = PERSONEL_HANDLER.getPersonelType(personel.getPersonelTypeID());
		System.out.println("Logged in as " + personel.getUsername() + " - " + pType.getName());

		return personel.getPersonelTypeID();
	}

	private static void showOptions(int loginTypeID) throws Exception {
		System.out.println("Options:");

		// logged in as reception executive
		if (loginTypeID == 1) {
			System.out.println("1 - View patient records");
			System.out.println("2 - Fill mini registration form");
			System.out.println("3 - Add personel");
			System.out.println("4 - Print daily report");
			System.out.println("5 - Exit");
		}
		// logged in as doctor/physician
		else if (loginTypeID == 2) {
			System.out.println("1 - View patient records");
			System.out.println("2 - Review events for patient");
			System.out.println("3 - Add event for patient");
			System.out.println("4 - Exit");
		}

	}

	private static int readOption() throws Exception {
		System.out.println();
		return getInt("Choice:");
	}

	private static void performOption(int loginTypeID, int option) throws Exception {
		switch (loginTypeID) {
		case 1: {
			// logged in as reception executive
			switch (option) {
			case 1:
				viewPatientRecords(false);
				break;
			case 2:
				fillMiniRegistrationForm();
				break;
			case 3:
				addPersonel();
				break;
			case 4:
				printDailyReport();
				break;
			case 5:
				exit();
				break;

			default:
				System.out.println("Option not valid! Try again!");
				break;
			}
		}
			break;

		case 2: {
			// logged in as doctor/physician
			switch (option) {
			case 1:
				viewPatientRecords(true);
				break;
			case 2:
				reviewEventsForPatient();
				break;
			case 3:
				addEventForPatient();
				break;
			case 4:
				exit();
				break;
			default:
				System.out.println("Option not valid! Try again!");
				break;
			}
		}
			break;
		}
	}

	private static void viewPatientRecords(boolean isViewedByDoctor) throws Exception {
		System.out.println("View patient records:");
		List<Person> patients = isViewedByDoctor ? PATIENT_FOR_DOCTOR_HANDLER.getPatientsForDoctor(idPersonel) : PATIENT_HANDLER.getPatientList();
		patients.forEach(x -> System.out.println(x.getIDPerson() + " - " + x));

		System.out.println();
		int patientID = getInt("Patient ID:");

		System.out.println();
		System.out.println("Patient details:");
		System.out.println(PATIENT_HANDLER.getPatientDetails(patientID));
	}

	private static void fillMiniRegistrationForm() throws Exception {
		System.out.println("Fill mini registration form:");
		String firstName = getString("First name:");
		String middleName = getString("Middle name:");
		String surname = getString("Surname:");
		String sex;
		do {
			sex = getString("Sex (valid values: [M, F]):");
		} while (!sex.equals("M") && !sex.equals("F"));
		LocalDate date = getLocalDate("Date (yyyy-MM-dd):", "yyyy-MM-dd");
		String telHome = getString("Telephone home:");
		String telWork = getString("Telephone work:");
		String nokFirstName = getString("Next-of-kin first name:");
		String nokMiddleName = getString("Next-of-kin middle name:");
		String nokSurname = getString("Next-of-kin surname:");
		String nokRelationship = getString("Next-of-kin relationship with patient:");
		String statementOfComplaint = getString("Brief statement of complaint:");

		Outpatient outpatient = new Outpatient(0,
				new Person(0, firstName, middleName, surname, sex.charAt(0), date, telHome, telWork, null, null, null, null, null, null),
				new Person(0, nokFirstName, nokMiddleName, nokSurname, null, null, null, null, null, null, null, null, null, null),
				nokRelationship,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				statementOfComplaint,
				null, null, null, null, null, null, null, null, null, null, null, null, null);
		
		PATIENT_HANDLER.fillMiniRegistrationForm(outpatient);

		System.out.println("Registration form filled!");
	}

	private static void addPersonel() throws Exception {
		System.out.println("Add personel:");
		String personelTypeString = "";
		List<PersonelType> personelTypes = PERSONEL_HANDLER.getPersonelTypes();
		for (PersonelType type : personelTypes) {
			personelTypeString = personelTypeString.concat("{" + type.getId() + " - " + type.getName() + "}");
			if (type != personelTypes.get(personelTypes.size() - 1)) {
				personelTypeString = personelTypeString.concat(", ");
			}
		}
		int personelTypeID;
		do {
			personelTypeID = getInt("Personel type id (valid values:[" + personelTypeString + "]):");
		} while (personelTypeID > personelTypes.size() || personelTypeID <= 0);
		String username = getString("Username:");
		String firstName = getString("First name:");
		String middleName = getString("Middle name:");
		String surname = getString("Surname:");
		String title = getString("Title:");
		String password = getString("Password:");
		
		Personel personel = new Personel(0, personelTypeID, username, firstName, middleName, surname, title);
		
		PERSONEL_HANDLER.insertPersonel(personel, password);
		
		System.out.println("Personel added!");
	}

	private static void printDailyReport() throws Exception {
		int newPatients = REPORT_HANDLER.getNewPatients().stream().filter(x -> x.getKey() == 0).mapToInt(x -> x.getValue()).sum();
		int followUpPatients = REPORT_HANDLER.getFollowUpPatients().stream().filter(x -> x.getKey() == 0).mapToInt(x -> x.getValue()).sum();
		List<Pair<Integer, Float>> chargeSummary = REPORT_HANDLER.getChargedSummaryToday();

		System.out.println("DAILY REPORT - " + LocalDate.now().toString());
		System.out.println("Patients handled by various doctors: " + (newPatients + followUpPatients));
		System.out.println("Number of new patients: " + newPatients);
		System.out.println("Number of follow-up patients: " + followUpPatients);
		System.out.println("CHARGE SUMMARY - " + LocalDate.now().toString());
		System.out.println("Consulting fees charged: " + chargeSummary.stream().filter(x -> x.getKey() == EventType.DIAGNOSIS.getId()).mapToDouble(x -> x.getValue()).sum());
		System.out.println("Tests ordered: " + chargeSummary.stream().filter(x -> x.getKey() == EventType.LAB_TEST.getId()).mapToDouble(x -> x.getValue()).sum());
		System.out.println("Medicines prescribed: " + chargeSummary.stream().filter(x -> x.getKey() == EventType.PRESCRIPTION.getId()).mapToDouble(x -> x.getValue()).sum());
	}

	private static void reviewEventsForPatient() throws Exception {
		System.out.println("View patient events:");
		List<Person> patients = PATIENT_FOR_DOCTOR_HANDLER.getPatientsForDoctor(idPersonel);
		patients.forEach(x -> System.out.println(x.getIDPerson() + " - " + x));

		System.out.println();
		int patientID = getInt("Patient ID:");

		System.out.println();
		System.out.println("Patient event details:");
		List<PersonelOutpatientEvent> events = PATIENT_FOR_DOCTOR_HANDLER.getEventsForPatientDoctor(patientID, idPersonel);
		events.forEach(System.out::println);
	}

	private static void addEventForPatient() throws Exception {
		System.out.println("Add event for patient:");
		List<Person> patients = PATIENT_FOR_DOCTOR_HANDLER.getPatientsForDoctor(idPersonel);
		patients.forEach(x -> System.out.println(x.getIDPerson() + " - " + x));
		
		String eventTypeString = "";
		EventType[] eventTypes = EventType.values();
		for (EventType type : eventTypes) {
			eventTypeString = eventTypeString.concat("{" + type.getId() + " - " + type.getName() + "}");
			if (type != eventTypes[eventTypes.length - 1]) {
				eventTypeString = eventTypeString.concat(", ");
			}
		}
		
		System.out.println();
		int patientID = getInt("Patient ID:");
		LocalDate date = getLocalDate("Appointed date (yyyy-MM-dd):", "yyyy-MM-dd");
		int eventTypeID;
		do {
			eventTypeID = getInt("Event type ID (valid values:[" + eventTypeString + "]):");
		} while (eventTypeID > eventTypes.length || eventTypeID <= 0);
		
		String comment = getString("Comment/note:");

		PersonelOutpatientEvent event = new PersonelOutpatientEvent(0, patientID, idPersonel, eventTypeID, date, comment);
		
		PATIENT_FOR_DOCTOR_HANDLER.insertEvent(event);
		
		System.out.println("Patient event added!");
	}

	private static void exit() {
		System.out.println("Exiting!");
		System.exit(0);
	}

	private static String getString(String message) {
		String string = "";
		do {
			System.out.println(message);
			string = scanner.nextLine();
		} while (isNullEmptyOrWhitespace(string));
		return string;
	}

	private static int getInt(String message) {
		String string = "";
		do {
			System.out.println(message);
			string = scanner.nextLine();
			try {
				return Integer.parseInt(string);
			} catch (NumberFormatException e) {
			}
		} while (true);
	}

	private static LocalDate getLocalDate(String message, String pattern) {
		String string = "";
		do {
			System.out.println(message);
			string = scanner.nextLine();
			try {
				return LocalDate.parse((CharSequence) string, DateTimeFormatter.ofPattern(pattern));
			} catch (Exception e) {}
		} while (true);
	}

	private static boolean isNullEmptyOrWhitespace(String string) {
		if (string != null && !string.trim().isEmpty()) {
			return false;
		}
		return true;
	}
}
