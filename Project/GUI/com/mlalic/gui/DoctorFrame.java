package com.mlalic.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mlalic.bl.PatientHandler;
import com.mlalic.bl.PatientsForDoctorHandler;
import com.mlalic.model.EventType;
import com.mlalic.model.Outpatient;
import com.mlalic.model.Person;
import com.mlalic.model.PersonelOutpatientEvent;

public class DoctorFrame extends JPanel {

	private static final long serialVersionUID = 1233611881025631269L;
	private static final PatientsForDoctorHandler PATIENTS_FOR_DOCTOR_HANDLER = new PatientsForDoctorHandler();
	private static final PatientHandler PATIENT_HANDLER = new PatientHandler();

	private final int doctorID;
	
	private int selectedPatientID = -1;
;
	private JPanel pnlPatientRecord;
	private JPanel pnlAppointments;
	private JPanel pnlLabTests;
	private JPanel pnlDiagnosisInfo;
	private JPanel pnlMedicationPrescribed;
	private JComboBox<Object> ddlPatientsForDoctor;
	private JButton btnScheduleAppointment;
	private JButton btnRecommendLabTest;
	private JButton btnNoteDiagnosisInfo;
	private JButton btnPrescribeMedication;

	private void btnAddPatientForDoctorActionPerformed(ActionEvent e) {
		try {
			List<Person> patients = PATIENT_HANDLER.getPatientList();
			List<Person> docPatients = PATIENTS_FOR_DOCTOR_HANDLER.getPatientsForDoctor(doctorID);

			patients.removeAll(docPatients);
			if (patients.isEmpty()) {
				JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("No patients found!"), "Info", 1);
				return;
			}
			Object[] options = patients.toArray();

			int selected = JOptionPane.showOptionDialog(null, "Select patient:", "Add patient for doctor",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (selected >= 0) {
				int outpatientId = patients.get(selected).getIDPerson();

				PATIENTS_FOR_DOCTOR_HANDLER.insertPatientForDoctor(doctorID, outpatientId);
				loadPatients();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Error adding patient for doctor!"
					+ System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
		}

	}

	public void ddlPatientsForDoctorChanged(int selectedPatientID) {
		try {
			Outpatient outpatient = PATIENT_HANDLER.getPatientDetails(selectedPatientID);
			
			List<PersonelOutpatientEvent> patientEvents = PATIENTS_FOR_DOCTOR_HANDLER.getEventsForPatientDoctor(outpatient.getIDOutpatient(), doctorID);
			
			reloadPanelWithButton(pnlAppointments, btnScheduleAppointment);
			reloadPanelWithButton(pnlDiagnosisInfo, btnNoteDiagnosisInfo);
			reloadPanelWithButton(pnlLabTests, btnRecommendLabTest);
			reloadPanelWithButton(pnlMedicationPrescribed, btnPrescribeMedication);
			
			showPatientDetails(outpatient);
			showPatientEventsByTypeInPanel(patientEvents, EventType.APPOINTMENT, pnlAppointments);
			showPatientEventsByTypeInPanel(patientEvents, EventType.LAB_TEST, pnlLabTests);
			showPatientEventsByTypeInPanel(patientEvents, EventType.DIAGNOSIS, pnlDiagnosisInfo);
			showPatientEventsByTypeInPanel(patientEvents, EventType.PRESCRIPTION, pnlMedicationPrescribed);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Error fetching patient details!"
					+ System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
		}
	}

	private void showPatientEventsByTypeInPanel(List<PersonelOutpatientEvent> events, EventType eventType, JPanel panel) {
		try {
			List<PersonelOutpatientEvent> filteredData = events.stream().filter(x -> x.getEventTypeID().equals(eventType.getId())).collect(Collectors.toList());
			panel.add(getTable(filteredData, new String[]{ "Date", "Comment" }));
		} catch (Exception e) {
			JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Error fetching patient " + eventType.getName() + "!"
					+ System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
		}
	}

	private PersonelOutpatientEvent showEventDialog(String title) {
		JPanel panel = new JPanel();
		
		// date
		JLabel labelDate = new JLabel("Appointed date:");
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		//datePicker.setBounds(126, 116, 145, 27);
		
		// comment/notes
		JLabel labelNote = new JLabel("Comment:");
		JTextField txtNote = new JTextField(15);
		
		
		panel.add(labelDate);
		panel.add(datePicker);
		panel.add(labelNote);
		panel.add(txtNote);

		String[] options = new String[] { "Cancel", "OK" };
		int option = JOptionPane.showOptionDialog(null, panel, title, JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

		if (option == 1) // pressing OK button
		{
			try {
				LocalDate date = ((Date)datePicker.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String note = txtNote.getText();
			
				if (note.length() == 0) {
					JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Error adding event!" + System.lineSeparator() + "Comment can not be empty!"), "Error", 0);
					return null;
				}
				
				return new PersonelOutpatientEvent(0, 0, 0, 0, date, note);
			} catch (Exception e) {
				JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Error adding event!" + System.lineSeparator() + "Date not selected!"), "Error", 0);
			}
		}
		return null;
	}
	
	private void btnAddEventActionPerform(EventType eventType) {
		if (selectedPatientID != -1) {
			PersonelOutpatientEvent event = showEventDialog("Add " + eventType.getName());
			if (event == null) {
				return;
			}
			
			if (eventType == EventType.APPOINTMENT && !LocalDate.now().isBefore(event.getAppointedDate())) {
				JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Error adding event for patient!"
						+ System.lineSeparator() + "Appointments can only be fixed for future dates!"), "Error", 0);
				return;
			}
			
			event.setEventTypeID(eventType.getId());
			event.setOutpatientID(selectedPatientID);
			event.setPersonelID(doctorID);
			
			try {
				PATIENTS_FOR_DOCTOR_HANDLER.insertEvent(event);
				ddlPatientsForDoctorChanged(selectedPatientID);
			} catch (Exception e) {
				JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Error adding event for patient!"
						+ System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
			}
		}
		else {
			JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Select patient and try again!"), "Error", 0);
		}
	}
	
	private void showPatientDetails(Outpatient outpatient) {
		try {
			pnlPatientRecord.removeAll();

			PatientDetails details = new PatientDetails(outpatient);
			details.setVisible(true);
			details.setBounds(new Rectangle(1060, 615));

			pnlPatientRecord.add(details);
			pnlPatientRecord.revalidate();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Error fetching patient details!"
					+ System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
		}

	}

	private void reloadPanelWithButton(JPanel panel, JButton button) {
		panel.removeAll();
		panel.add(button);
	}
	
	private void loadPatients() {
		try {
			ddlPatientsForDoctor.setModel(
					new DefaultComboBoxModel<>(PATIENTS_FOR_DOCTOR_HANDLER.getPatientsForDoctor(doctorID).toArray()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, (Object) ("Unable to fetch patients for doctor!"
					+ System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
		}
	}
	
	private void setColumnWidths(JTable table) {
		TableColumn column = null;
		for (int i = 0; i < 2; i++) {
		    column = table.getColumnModel().getColumn(i);
		    if (i == 1) {
		        column.setPreferredWidth(1000);
		    } else {
		        column.setPreferredWidth(200);
		    }
		}
	}
	
	private JScrollPane getTable(List<PersonelOutpatientEvent> data, String[] columnNames) {
		JScrollPane jScrollPane = new JScrollPane();
		JTable jTableAppointments = new JTable();
		jTableAppointments.setPreferredScrollableViewportSize(new Dimension(1040, 550));
		jTableAppointments.setModel(new MyTableModel(columnNames, data));
		setColumnWidths(jTableAppointments);
		
		jScrollPane.setViewportView(jTableAppointments);
		return jScrollPane;
	}

	
	public DoctorFrame(int doctorID) throws Exception {
		this.doctorID = doctorID;
		setPreferredSize(new Dimension(1060, 700));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblPatient = new JLabel("Patient:");
		panel.add(lblPatient);
		lblPatient.setHorizontalAlignment(SwingConstants.TRAILING);

		ddlPatientsForDoctor = new JComboBox<Object>();
		ddlPatientsForDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				selectedPatientID = ((Person) cb.getSelectedItem()).getIDPerson();
				ddlPatientsForDoctorChanged(selectedPatientID);
			}
		});
		loadPatients();
		ddlPatientsForDoctor.setBounds(new Rectangle(0, 0, 800, 20));
		ddlPatientsForDoctor.setPreferredSize(new Dimension(800, 20));
		panel.add(ddlPatientsForDoctor);

		JButton btnAddPatientForDoctor = new JButton("Add another");
		btnAddPatientForDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddPatientForDoctorActionPerformed(e);
			}
		});
		panel.add(btnAddPatientForDoctor);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane_1, BorderLayout.CENTER);

		pnlPatientRecord = new JPanel();
		tabbedPane_1.addTab("Patient record", null, pnlPatientRecord, null);

		
		pnlAppointments = new JPanel();
		tabbedPane_1.addTab("Appointments", null, pnlAppointments, null);
		btnScheduleAppointment = new JButton("Schedule appointment");
		btnScheduleAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddEventActionPerform(EventType.APPOINTMENT);
			}
		});
		pnlAppointments.add(btnScheduleAppointment);
		
		
		pnlLabTests = new JPanel();
		tabbedPane_1.addTab("Laboratory tests", null, pnlLabTests, null);
		btnRecommendLabTest = new JButton("Recommend laboratory test");
		btnRecommendLabTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddEventActionPerform(EventType.LAB_TEST);
			}
		});
		pnlLabTests.add(btnRecommendLabTest);
		
		
		pnlDiagnosisInfo = new JPanel();
		tabbedPane_1.addTab("Diagnosis information", null, pnlDiagnosisInfo, null);
		btnNoteDiagnosisInfo = new JButton("Note diagnosis information");
		btnNoteDiagnosisInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddEventActionPerform(EventType.DIAGNOSIS);
			}
		});
		pnlDiagnosisInfo.add(btnNoteDiagnosisInfo);
		
		
		pnlMedicationPrescribed = new JPanel();
		tabbedPane_1.addTab("Medication prescribed", null, pnlMedicationPrescribed, null);
		btnPrescribeMedication = new JButton("Prescribe medication");
		btnPrescribeMedication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddEventActionPerform(EventType.PRESCRIPTION);
			}
		});
		pnlMedicationPrescribed.add(btnPrescribeMedication);

	}

	public class MyTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 708354536388166072L;

		private List<PersonelOutpatientEvent> list = new ArrayList<PersonelOutpatientEvent>();
		private String[] columnNames;

		public MyTableModel(String[] columnNames, List<PersonelOutpatientEvent> list) {
			this.columnNames = columnNames;
			this.list = list;
		}
		

		@Override
		public String getColumnName(int columnIndex) {
			return columnNames[columnIndex];
		}

		@Override
		public int getRowCount() {
			return list.size();
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			PersonelOutpatientEvent event = list.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return event.getAppointedDate();
			case 1:
				return event.getComment();
			}
			return null;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return LocalDate.class;
			case 1:
				return String.class;
			}
			return null;
		}
	}
}
