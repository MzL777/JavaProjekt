package com.mlalic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mlalic.bl.PatientHandler;
import com.mlalic.bl.PatientsForDoctorHandler;
import com.mlalic.bl.PersonelHandler;
import com.mlalic.bl.ReportHandler;
import com.mlalic.model.EventType;
import com.mlalic.model.Outpatient;
import com.mlalic.model.PaymentType;
import com.mlalic.model.Person;
import com.mlalic.model.Personel;
import com.mlalic.model.PersonelOutpatientEvent;
import com.mlalic.model.PersonelType;
import com.mlalic.model.Sex;

import javafx.util.Pair;

public class ReceptionExecutiveFrame extends JPanel {
	private static final long serialVersionUID = -7165280300676263899L;
	private static final PatientsForDoctorHandler PATIENT_FOR_DOCTOR_HANDLER = new PatientsForDoctorHandler();
	private static final PatientHandler PATIENT_HANDLER = new PatientHandler();
	private static final PersonelHandler PERSONEL_HANDLER = new PersonelHandler();
	private static final ReportHandler REPORT_HANDLER = new ReportHandler();

	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	private JTextField txtName;
	private JTextField txtMiddleName;
	private JTextField txtSurname;
	private JTextField txtTelHome;
	private JTextField txtTelWork;
	private JTextField txtNoKName;
	private JTextField txtNoKMiddleName;
	private JTextField txtNoKSurname;
	private JTextField txtNoKRelationship;
	private JTextField txtStatementOfComplaint;
	private JComboBox<Sex> ddlSex;
	private JComboBox<Object> ddlPatients;
	private JDatePickerImpl datePicker;
	private JDatePickerImpl datePicker2;
	private JPanel detailsPane;
	
	private JTextField txtPerUsername;
	private JTextField txtPerFirstName;
	private JTextField txtPerMiddleName;
	private JTextField txtPerSurname;
	private JTextField txtPerTitle;
	private JTextField txtPerPassword;
	private JComboBox<Object> ddlPersonelTypes;
	private JTextField txtConfirmPassword;
	
	private JFormattedTextField txtPaymentAmount;
	private JComboBox<Object> ddlPaymentEvent;
	private JComboBox<PaymentType> ddlPaymentType;
	private JComboBox<Object> ddlPaymentPatient;
	private JButton btnCharge;
	private JPanel chargePanel;
	private JTextField txtComment;
	
	private JComboBox<Object> ddlAppointmentDoctor;
	private JComboBox<Object> ddlAppointmentPatients;
	
	private JPanel monthlyReportPanel;
	private JPanel dailyReportPanel;
	private LineChartEx2 chart;
	
	private void btnRegisterActionPerformed(ActionEvent evt) {
		try {
			PATIENT_HANDLER.fillMiniRegistrationForm(getOutpatient());
			clearPatientComponents();
			JOptionPane.showMessageDialog(null, (Object)("Outpatient successfully registered!"), "Info", 1);
			loadPatientsPersonelReports();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error registering outpatient!" + System.lineSeparator() + "Check input data and try again!"), "Error", 0);
		}
	}
	
	private void btnAddPersonelActionPerformed(ActionEvent evt) {
		try {
			PERSONEL_HANDLER.insertPersonel(getPersonel(), getPassword());
			clearPersonelComponents();
			JOptionPane.showMessageDialog(null, (Object)("Personel successfully added!"), "Info", 1);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error adding personel!" + System.lineSeparator() + "Check input data and try again!"), "Error", 0);
		}
	}
	

	private void ddlPaymentPatientActionPerformed(ActionEvent evt) {
		try {
			JComboBox<?> cb = (JComboBox<?>)evt.getSource();
			int patientId = ((Person)cb.getSelectedItem()).getIDPerson();

			btnCharge.setEnabled(false);
			
			ddlPaymentEvent.setModel(new DefaultComboBoxModel<>(PATIENT_FOR_DOCTOR_HANDLER.GetEventsForPatientForCharge(patientId).toArray()));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error fetching payment events!" + System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
		}
	}

	private void ddlPaymentEventActionPerformed(ActionEvent evt) {
		btnCharge.setEnabled(true);
	}
	

	private void btnChargeActionPerformed(ActionEvent evt) {
		try {
			int eventId = ((PersonelOutpatientEvent)ddlPaymentEvent.getSelectedItem()).getIDEvent();

			if (txtPaymentAmount.getText().length() == 0) {
				JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Payment amount is required!"), "Error", 0);
				return;
			}
			
			float amount = Float.parseFloat(txtPaymentAmount.getText());
			
			PATIENT_FOR_DOCTOR_HANDLER.chargeEvent(eventId, ddlPaymentType.getSelectedIndex() + 1, amount);
			
			String text = btnCharge.getText();
			btnCharge.setText(LocalDateTime.now().toString());
			printComponenet(chargePanel);
			btnCharge.setText(text);
			
			btnCharge.setEnabled(false);
			clearPaymentComponents();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error charging event!" + System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
		}
	}
	
	private void btnScheduleAppointment() {
		try { 
			LocalDate date = ((Date)datePicker2.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			String comment = txtComment.getText();
			int patientID = ((Person)ddlAppointmentPatients.getSelectedItem()).getIDPerson();
			int doctorID = ((Personel)ddlAppointmentDoctor.getSelectedItem()).getIDPersonel();
		
			if (date.isBefore(LocalDate.now())) {
				JOptionPane.showMessageDialog((Component) this.getParent(), (Object) ("Error adding event for patient!"
						+ System.lineSeparator() + "Appointments can only be fixed for future dates!"), "Error", 0);
				return;
			}
			PATIENT_FOR_DOCTOR_HANDLER.insertEvent(new PersonelOutpatientEvent(0, patientID, doctorID, EventType.APPOINTMENT.getId(), date, comment));
			clearAppointmentControls();
		} catch (Exception e) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error scheduling appointment!" + System.lineSeparator() + "Check inputs and connection and try again!"), "Error", 0);
		e.printStackTrace();
		}
	}
	
	private void clearPatientComponents() {
		txtName.setText("");
		txtMiddleName.setText("");
		txtSurname.setText("");
		txtTelHome.setText("");
		txtTelWork.setText("");
		txtNoKName.setText("");
		txtNoKMiddleName.setText("");
		txtNoKSurname.setText("");
		txtNoKRelationship.setText("");
		txtStatementOfComplaint.setText("");
		ddlSex.setSelectedIndex(0);
		datePicker.getModel().setValue(null);
	}
	
	private void clearPersonelComponents() {
		txtPerUsername.setText("");
		txtPerFirstName.setText("");
		txtPerMiddleName.setText("");
		txtPerSurname.setText("");
		txtPerTitle.setText("");
		txtPerPassword.setText("");
		txtConfirmPassword.setText("");
		ddlPersonelTypes.setSelectedIndex(0);
	}
	
	private void clearPaymentComponents() {
		txtPaymentAmount.setText("");
		ddlPaymentEvent.setSelectedIndex(0);
		ddlPaymentPatient.setSelectedIndex(0);
		ddlPaymentType.setSelectedIndex(0);
	}
	
	private void clearAppointmentControls() {
		ddlAppointmentDoctor.setSelectedIndex(0);
		ddlAppointmentPatients.setSelectedIndex(0);
		datePicker2.getModel().setValue(null);
		txtComment.setText("");
	}
	
	private Outpatient getOutpatient() {
		return new Outpatient(
				(Integer)0,
				new Person(0,
						txtName.getText(),
						txtMiddleName.getText(),
						txtSurname.getText(),
						(Character)ddlSex.getSelectedItem().toString().charAt(0),
						((Date)datePicker.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
						txtTelHome.getText(),
						txtTelWork.getText(),
						null, null, null, null, null, null),
				new Person(0,
						txtNoKName.getText(),
						txtNoKMiddleName.getText(),
						txtNoKSurname.getText(),
						null,
						LocalDate.MIN,
						null, null, null, null, null, null, null, null),
				txtNoKRelationship.getText(),
				null, null,
				Boolean.FALSE,
				null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
				txtStatementOfComplaint.getText(),
				null,null,null,null,null,null,null,null,null,null,null,null,null);
	}
	
	private Personel getPersonel() throws Exception {
		if (txtPerUsername.getText().length() == 0 ||
			txtPerFirstName.getText().length() == 0 ||
			txtPerSurname.getText().length() == 0) {
			throw new Exception();
		}
		return new Personel(0,
				((PersonelType)ddlPersonelTypes.getSelectedItem()).getId(),
				txtPerUsername.getText(),
				txtPerFirstName.getText(),
				txtPerMiddleName.getText(),
				txtPerSurname.getText(),
				txtPerTitle.getText());
	}
	
	private String getPassword() throws Exception {
		if (txtPerPassword.getText().length() == 0 ||
			!txtPerPassword.getText().equals(txtConfirmPassword.getText())) {
				throw new Exception();
		}
		return txtPerPassword.getText();
	}
	
	public void loadPatientsPersonelReports() {
		try {
			Object[] dataPatients = PATIENT_HANDLER.getPatientList().toArray();
			ddlPatients.setModel(new DefaultComboBoxModel<>(dataPatients));
			ddlPaymentPatient.setModel(new DefaultComboBoxModel<>(dataPatients));
			ddlAppointmentPatients.setModel(new DefaultComboBoxModel<>(dataPatients));
			
			ddlAppointmentDoctor.setModel(new DefaultComboBoxModel<>(PERSONEL_HANDLER.getPersonel().stream().filter(x -> x.getPersonelTypeID() == 2).toArray()));

			printMonthlyReport();
			printDailyReport();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error fetching patients and/or personel!" + System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
		}
	}

	private void printMonthlyReport() throws Exception {
		monthlyReportPanel.removeAll();	
		chart = new LineChartEx2();
		monthlyReportPanel.add(chart);
		JLabel lbl = new JLabel("Average number of patients handled by each doctor during the month: " + REPORT_HANDLER.getAveragePatientsHandled());
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 17));
		monthlyReportPanel.add(lbl);
	}
	
	private void printDailyReport() throws Exception {
		dailyReportPanel.removeAll();
		Font font = new Font("Tahoma", Font.PLAIN, 17);

		int newPatients = REPORT_HANDLER.getNewPatients().stream().filter(x -> x.getKey() == 0).mapToInt(x -> x.getValue()).sum();
		int followUpPatients = REPORT_HANDLER.getFollowUpPatients().stream().filter(x -> x.getKey() == 0).mapToInt(x -> x.getValue()).sum();
		List<Pair<Integer, Float>> chargeSummary = REPORT_HANDLER.getChargedSummaryToday();

		JLabel lblEmpty1 = new JLabel("=======================================================");
		JLabel lblEmpty2 = new JLabel("-------------------------------------------------------");
		JLabel lblEmpty3 = new JLabel("=======================================================");
		JLabel lbl = new JLabel("DAILY REPORT - " + LocalDate.now().toString());
		JLabel lbl1 = new JLabel("Patients handled by various doctors: " + (newPatients + followUpPatients));
		JLabel lbl2 = new JLabel("Number of new patients: " + newPatients);
		JLabel lbl3 = new JLabel("Number of follow-up patients: " + followUpPatients);
		JLabel lbl4 = new JLabel("CHARGE SUMMARY - " + LocalDate.now().toString());
		JLabel lbl5 = new JLabel("Consulting fees charged: " + chargeSummary.stream().filter(x -> x.getKey() == EventType.DIAGNOSIS.getId()).mapToDouble(x -> x.getValue()).sum());
		JLabel lbl6 = new JLabel("Tests ordered: " + chargeSummary.stream().filter(x -> x.getKey() == EventType.LAB_TEST.getId()).mapToDouble(x -> x.getValue()).sum());
		JLabel lbl7 = new JLabel("Medicines prescribed: " + chargeSummary.stream().filter(x -> x.getKey() == EventType.PRESCRIPTION.getId()).mapToDouble(x -> x.getValue()).sum());

		lblEmpty1.setFont(font);
		lblEmpty2.setFont(font);
		lblEmpty3.setFont(font);
		lbl.setFont(font);
		lbl1.setFont(font);
		lbl2.setFont(font);
		lbl3.setFont(font);
		lbl4.setFont(font);
		lbl5.setFont(font);
		lbl6.setFont(font);
		lbl7.setFont(font);

		lblEmpty1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblEmpty2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblEmpty3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl4.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl5.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl6.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl7.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		dailyReportPanel.setLayout(new BoxLayout(dailyReportPanel, BoxLayout.PAGE_AXIS));

		dailyReportPanel.add(lblEmpty1);
		dailyReportPanel.add(lbl);
		dailyReportPanel.add(lbl1);
		dailyReportPanel.add(lbl2);
		dailyReportPanel.add(lbl3);
		dailyReportPanel.add(lblEmpty2);
		dailyReportPanel.add(lbl4);
		dailyReportPanel.add(lbl5);
		dailyReportPanel.add(lbl6);
		dailyReportPanel.add(lbl7);
		dailyReportPanel.add(lblEmpty3);
	}
	
	private void showPatientDetails(ActionEvent evt) {
		try {
			JComboBox<?> cb = (JComboBox<?>)evt.getSource();
	        Outpatient outpatient = PATIENT_HANDLER.getPatientDetails(((Person)cb.getSelectedItem()).getIDPerson());
			
			detailsPane.removeAll();

	        PatientDetails details = new PatientDetails(outpatient);
	        details.setVisible(true);
	        details.setBounds(new Rectangle(1060, 615));
	        
	        detailsPane.add(details);
	        detailsPane.revalidate();
	        
		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error fetching patient details!" + System.lineSeparator() + "Check your connection and try again!"), "Error", 0);
		}

	}

	private void printComponenet(Component component){
		  PrinterJob pj = PrinterJob.getPrinterJob();
		  pj.setJobName(" Print Component ");

		  pj.setPrintable (new Printable() {    
		    public int print(Graphics pg, PageFormat pf, int pageNum){
		      if (pageNum > 0){
		      return Printable.NO_SUCH_PAGE;
		      }

		      Graphics2D g2 = (Graphics2D) pg;
		      g2.translate(pf.getImageableX(), pf.getImageableY());
		      g2.scale(0.7, 0.7);
		      component.paint(g2);
		      return Printable.PAGE_EXISTS;
		    }
		  });
		  if (pj.printDialog() == false)
		  return;

		  try {
		        pj.print();
		  } catch (PrinterException ex) {
			  JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error printing bill!"), "Error", 0);
			  ex.printStackTrace();
		  }
		}
	
	public ReceptionExecutiveFrame() {
		setPreferredSize(new Dimension(1060, 700));
		setLayout(new GridLayout(0, 1, 0, 0));
		tabbedPane.setMaximumSize(new Dimension(1060, 700));
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (detailsPane != null) {
					detailsPane.removeAll();
					loadPatientsPersonelReports();
				}
			}
		});
		add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Registration form", null, panel, null);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane_1);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Patient records", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		ddlPatients = new JComboBox<Object>();
		panel_3.add(ddlPatients, BorderLayout.NORTH);
		ddlPatients.setMaximumSize(new Dimension(1060, 32767));
		
		detailsPane = new JPanel();
		panel_3.add(detailsPane, BorderLayout.CENTER);
		ddlPatients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPatientDetails(e);
			}
		});
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("Mini form", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("First name:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 14, 106, 14);
		panel_2.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Surname:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(10, 66, 106, 14);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Middle name:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setBounds(10, 39, 106, 14);
		panel_2.add(lblNewLabel_2);
		
		ddlSex = new JComboBox<Sex>();
		ddlSex.setBounds(126, 88, 145, 20);
		ddlSex.setModel(new DefaultComboBoxModel<>(Sex.values()));
		panel_2.add(ddlSex);
		
		txtName = new JTextField();
		txtName.setBounds(126, 11, 145, 20);
		panel_2.add(txtName);
		txtName.setColumns(10);
		
		txtMiddleName = new JTextField();
		txtMiddleName.setColumns(10);
		txtMiddleName.setBounds(126, 36, 145, 20);
		panel_2.add(txtMiddleName);
		
		txtSurname = new JTextField();
		txtSurname.setColumns(10);
		txtSurname.setBounds(126, 63, 145, 20);
		panel_2.add(txtSurname);
		
		JLabel lblNewLabel_3 = new JLabel("Sex:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setBounds(10, 91, 106, 14);
		panel_2.add(lblNewLabel_3);
		
		JLabel lblDateOfBirth = new JLabel("Date of birth:");
		lblDateOfBirth.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDateOfBirth.setBounds(10, 120, 106, 14);
		panel_2.add(lblDateOfBirth);
		
		UtilDateModel model = new UtilDateModel();
		//model.setDate(20,04,2014);
		// Need this...
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(126, 116, 145, 27);
		panel_2.add(datePicker);
		

		JLabel lblTelephoneHome = new JLabel("Telephone home:");
		lblTelephoneHome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTelephoneHome.setBounds(10, 147, 106, 14);
		panel_2.add(lblTelephoneHome);
		
		JLabel lblTelephoneWork = new JLabel("Telephone work:");
		lblTelephoneWork.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTelephoneWork.setBounds(10, 172, 106, 14);
		panel_2.add(lblTelephoneWork);
		
		txtTelHome = new JTextField();
		txtTelHome.setColumns(10);
		txtTelHome.setBounds(126, 144, 145, 20);
		panel_2.add(txtTelHome);
		
		txtTelWork = new JTextField();
		txtTelWork.setColumns(10);
		txtTelWork.setBounds(126, 169, 145, 20);
		panel_2.add(txtTelWork);
		
		JLabel lblNextOfKin = new JLabel("NEXT OF KIN:");
		lblNextOfKin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNextOfKin.setBounds(298, 14, 106, 14);
		panel_2.add(lblNextOfKin);
		
		JLabel lblFirstAme = new JLabel("First name:");
		lblFirstAme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstAme.setBounds(298, 39, 106, 14);
		panel_2.add(lblFirstAme);
		
		JLabel label_1 = new JLabel("Middle name:");
		label_1.setHorizontalAlignment(SwingConstants.TRAILING);
		label_1.setBounds(298, 64, 106, 14);
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel("Surname:");
		label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		label_2.setBounds(298, 91, 106, 14);
		panel_2.add(label_2);
		
		JLabel lblRelationship = new JLabel("Relationship:");
		lblRelationship.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblRelationship.setBounds(298, 120, 106, 14);
		panel_2.add(lblRelationship);
		
		txtNoKName = new JTextField();
		txtNoKName.setColumns(10);
		txtNoKName.setBounds(414, 36, 145, 20);
		panel_2.add(txtNoKName);
		
		txtNoKMiddleName = new JTextField();
		txtNoKMiddleName.setColumns(10);
		txtNoKMiddleName.setBounds(414, 63, 145, 20);
		panel_2.add(txtNoKMiddleName);
		
		txtNoKSurname = new JTextField();
		txtNoKSurname.setColumns(10);
		txtNoKSurname.setBounds(414, 88, 145, 20);
		panel_2.add(txtNoKSurname);
		
		JLabel lblBriefStatementOf = new JLabel("Brief statement of complaint:");
		lblBriefStatementOf.setBounds(10, 197, 261, 14);
		panel_2.add(lblBriefStatementOf);
		
		txtStatementOfComplaint = new JTextField();
		txtStatementOfComplaint.setBorder(new LineBorder(Color.GRAY));
		txtStatementOfComplaint.setBounds(10, 222, 549, 20);
		panel_2.add(txtStatementOfComplaint);
		
		txtNoKRelationship = new JTextField();
		txtNoKRelationship.setColumns(10);
		txtNoKRelationship.setBounds(298, 144, 261, 20);
		panel_2.add(txtNoKRelationship);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRegisterActionPerformed(e);
			}
		});
		btnRegister.setBounds(470, 253, 89, 23);
		panel_2.add(btnRegister);
		
		
		JPanel crf = new ComprehensiveRegistrationForm();
		tabbedPane_1.addTab("Comprehensive form", null, crf, null);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Add personel", null, panel_4, null);
		panel_4.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsername.setBounds(70, 21, 139, 14);
		panel_4.add(lblUsername);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstName.setBounds(70, 46, 139, 14);
		panel_4.add(lblFirstName);
		
		JLabel lblMiddleName = new JLabel("Middle name:");
		lblMiddleName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMiddleName.setBounds(70, 71, 139, 14);
		panel_4.add(lblMiddleName);
		
		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSurname.setBounds(70, 96, 139, 14);
		panel_4.add(lblSurname);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTitle.setBounds(70, 121, 139, 14);
		panel_4.add(lblTitle);
		
		JLabel lblPersonelType = new JLabel("Personel type:");
		lblPersonelType.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPersonelType.setBounds(70, 146, 139, 14);
		panel_4.add(lblPersonelType);
		
		txtPerUsername = new JTextField();
		txtPerUsername.setBounds(219, 18, 192, 20);
		panel_4.add(txtPerUsername);
		txtPerUsername.setColumns(10);
		
		txtPerFirstName = new JTextField();
		txtPerFirstName.setColumns(10);
		txtPerFirstName.setBounds(219, 43, 192, 20);
		panel_4.add(txtPerFirstName);
		
		txtPerMiddleName = new JTextField();
		txtPerMiddleName.setColumns(10);
		txtPerMiddleName.setBounds(219, 68, 192, 20);
		panel_4.add(txtPerMiddleName);
		
		txtPerSurname = new JTextField();
		txtPerSurname.setColumns(10);
		txtPerSurname.setBounds(219, 93, 192, 20);
		panel_4.add(txtPerSurname);
		
		txtPerTitle = new JTextField();
		txtPerTitle.setColumns(10);
		txtPerTitle.setBounds(219, 118, 192, 20);
		panel_4.add(txtPerTitle);
		
		txtPerPassword = new JPasswordField();
		txtPerPassword.setColumns(10);
		txtPerPassword.setBounds(219, 168, 192, 20);
		panel_4.add(txtPerPassword);
		
		ddlPersonelTypes = new JComboBox<Object>();
		ddlPersonelTypes.setBounds(219, 143, 192, 20);
		try {
			ddlPersonelTypes.setModel(new DefaultComboBoxModel<>(PERSONEL_HANDLER.getPersonelTypes().toArray()));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error fething personel types!" + System.lineSeparator() + "Check your connection and try again!" + System.lineSeparator() + System.lineSeparator() + "Program will exit now!"), "Error", 0);
			System.exit(0);
		}
		panel_4.add(ddlPersonelTypes);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setBounds(70, 171, 139, 14);
		panel_4.add(lblPassword);
		
		JButton btnAddPersonel = new JButton("Add personel");
		btnAddPersonel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddPersonelActionPerformed(e);
			}
		});
		btnAddPersonel.setBounds(219, 243, 192, 23);
		panel_4.add(btnAddPersonel);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password:");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblConfirmPassword.setBounds(70, 196, 139, 14);
		panel_4.add(lblConfirmPassword);
		
		txtConfirmPassword = new JPasswordField();
		txtConfirmPassword.setColumns(10);
		txtConfirmPassword.setBounds(219, 193, 192, 20);
		panel_4.add(txtConfirmPassword);
		
		chargePanel = new JPanel();
		tabbedPane.addTab("Charge facility", null, chargePanel, null);
		chargePanel.setLayout(null);
		
		
		JLabel lblPatient = new JLabel("Patient:");
		lblPatient.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPatient.setBounds(87, 14, 150, 14);
		chargePanel.add(lblPatient);
		ddlPaymentPatient = new JComboBox<Object>();
		ddlPaymentPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ddlPaymentPatientActionPerformed(e);
			}
		});
		ddlPaymentPatient.setBounds(247, 11, 478, 20);
		chargePanel.add(ddlPaymentPatient);
		
		
		JLabel lblEvent = new JLabel("Event:");
		lblEvent.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEvent.setBounds(87, 39, 150, 14);
		chargePanel.add(lblEvent);
		ddlPaymentEvent = new JComboBox<Object>();
		ddlPaymentEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ddlPaymentEventActionPerformed(e);
			}
		});
		ddlPaymentEvent.setBounds(247, 36, 478, 20);
		chargePanel.add(ddlPaymentEvent);
		
		
		JLabel lblPaymentType = new JLabel("Payment type:");
		lblPaymentType.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPaymentType.setBounds(87, 64, 150, 14);
		chargePanel.add(lblPaymentType);
		ddlPaymentType = new JComboBox<PaymentType>();
		ddlPaymentType.setModel(new DefaultComboBoxModel<>(PaymentType.values()));
		ddlPaymentType.setBounds(247, 61, 478, 20);
		chargePanel.add(ddlPaymentType);
		
		
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAmount.setBounds(87, 89, 150, 14);
		chargePanel.add(lblAmount);
		txtPaymentAmount = new JFormattedTextField(NumberFormat.getNumberInstance());
		txtPaymentAmount.setBounds(247, 86, 478, 20);
		chargePanel.add(txtPaymentAmount);
	
		
		btnCharge = new JButton("Charge");
		btnCharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnChargeActionPerformed(e);
			}
		});
		btnCharge.setEnabled(false);
		btnCharge.setBounds(247, 117, 478, 23);
		chargePanel.add(btnCharge);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Schedule appointment", null, panel_5, null);
		panel_5.setLayout(null);
		
		JButton btnScheduleAppointment = new JButton("Schedule appointment");
		btnScheduleAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnScheduleAppointment();
			}
		});
		btnScheduleAppointment.setBounds(313, 131, 198, 20);
		panel_5.add(btnScheduleAppointment);

		ddlAppointmentPatients = new JComboBox<Object>();
		ddlAppointmentPatients.setMaximumSize(new Dimension(1060, 32767));
		ddlAppointmentPatients.setBounds(313, 11, 361, 20);
		panel_5.add(ddlAppointmentPatients);
		
		ddlAppointmentDoctor = new JComboBox<Object>();
		ddlAppointmentDoctor.setMaximumSize(new Dimension(1060, 32767));
		ddlAppointmentDoctor.setBounds(313, 36, 361, 20);
		panel_5.add(ddlAppointmentDoctor);
		
		JLabel lblPatient_1 = new JLabel("Patient:");
		lblPatient_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPatient_1.setBounds(167, 14, 136, 14);
		panel_5.add(lblPatient_1);
		
		JLabel lblDoctorphysician = new JLabel("Doctor/physician:");
		lblDoctorphysician.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDoctorphysician.setBounds(167, 39, 136, 14);
		panel_5.add(lblDoctorphysician);
		UtilDateModel model2 = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
		datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		datePicker2.setBounds(313, 62, 145, 27);
		panel_5.add(datePicker2);
		
		JLabel lblAppointedDate = new JLabel("Appointed date:");
		lblAppointedDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAppointedDate.setBounds(167, 64, 136, 14);
		panel_5.add(lblAppointedDate);
		
		JLabel lblComment = new JLabel("Comment:");
		lblComment.setHorizontalAlignment(SwingConstants.TRAILING);
		lblComment.setBounds(167, 103, 136, 14);
		panel_5.add(lblComment);
		
		txtComment = new JTextField();
		txtComment.setBounds(313, 100, 361, 20);
		panel_5.add(txtComment);
		txtComment.setColumns(10);
		
		dailyReportPanel = new JPanel();
		tabbedPane.addTab("Daily report", null, dailyReportPanel, null);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Monthly report", null, panel_6, null);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		monthlyReportPanel = new JPanel();
		panel_6.add(monthlyReportPanel);

		
		loadPatientsPersonelReports();
	}
}
