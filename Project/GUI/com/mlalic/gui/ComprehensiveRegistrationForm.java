package com.mlalic.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mlalic.bl.PatientHandler;
import com.mlalic.model.BloodType;
import com.mlalic.model.MaritalStatus;
import com.mlalic.model.Outpatient;
import com.mlalic.model.Person;
import com.mlalic.model.Sex;


public class ComprehensiveRegistrationForm extends JPanel {
	
	private static final long serialVersionUID = 4149020109034292767L;
	
	private static final PatientHandler PATIENT_HANDLER = new PatientHandler();
	
	private JTextField txtName;
	private JTextField txtMiddleName;
	private JTextField txtSurname;
	private JTextField txtTelephoneHome;
	private JTextField txtTelephoneWork;
	private JTextField txtPresentAddress;
	private JTextField txtPermanentAddress;
	private JTextField txtMobile;
	private JTextField txtPager;
	private JTextField txtFax;
	private JTextField txtNoKSurname;
	private JTextField txtNoKMiddleName;
	private JTextField txtNoKName;
	private JTextField txtNoKContactAddress;
	private JTextField txtNoKTelephoneHome;
	private JTextField txtNoKTelephoneWork;
	private JTextField txtNoKMobile;
	private JTextField txtNoKPager;
	private JTextField txtNoKFax;
	private JTextField txtNoKEmail;
	private JTextField txtEmail;
	private JTextField txtOccupation;
	private JTextField txtGrossAnnualIncome;
	private JTextField txtWeight;
	private JTextField txtHeight;
	private JTextField txtNoOfDependents;
	private JTextField txtAvgCigarettesPerDay;
	private JTextField txtAvgDrinksPerDay;
	private JTextField txtStimulants;
	private JTextField txtSoftDrinksPerDay;
	private JTextField txtCoffeeTeaPerDay;
	private JTextField txtKnownAllergies;
	private JTextField txtKnownAdverseReactionToSpecificDrugs;
	private JTextField txtMajorSurgeries;
	private JTextField txtHistoryOfPreviousTreatment;
	private JTextField txtPhycianHospital;
	private JTextField txtStatementOfComplaint;
	private JTextField txtNoKRelationship;
	private JTabbedPane tabbedPane;
	private JComboBox<Sex> ddlSex;
	private JComboBox<Object> ddlMaritalStatus;
	private JComboBox<Object> ddlBloodType;
	private JCheckBox chckbxVegetarian;
	private JCheckBox chckbxSmoker;
	private JCheckBox chckbxConsumeAlcohol;
	private JCheckBox chckbxUseStimulants;
	private JCheckBox chckbxRegularMeals;
	private JCheckBox chckbxPredominantlyEatsHome;
	private JCheckBox chckbxDiabetic;
	private JCheckBox chckbxHypertensive;
	private JCheckBox chckbxCardiacCondition;
	private JCheckBox chckbxRaspiratoryCondition;
	private JCheckBox chckbxDigestiveCondition;
	private JCheckBox chckbxOrthopedicCondition;
	private JCheckBox chckbxMuscularCondition;
	private JCheckBox chckbxNeurologicalCondition;
	private JDatePickerImpl datePicker;
	
	private void btnActionPerformed(ActionEvent evt) {
		tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() + 1);
	}
	
	private void btnFinishActionPerformed(ActionEvent e) {
		try {
			PATIENT_HANDLER.fillComprehensiveRegistrationForm(getOutpatient());
			clearComponents();
			JOptionPane.showMessageDialog(null, (Object)("Outpatient successfully registered!"), "Info", 1);
			((ReceptionExecutiveFrame)getParent()).loadPatientsPersonelReports();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, (Object)("Error registering outpatient" + System.lineSeparator() + "Check input data and try again!"), "Error", 0);
		}
	}
	
	private Outpatient getOutpatient() {
		return new Outpatient((Integer)0,
				new Person(0, txtName.getText(), txtMiddleName.getText(), txtSurname.getText(), (Character)ddlSex.getSelectedItem().toString().charAt(0), ((Date)datePicker.getModel().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), txtTelephoneHome.getText(), txtTelephoneWork.getText(), txtMobile.getText(), txtPager.getText(), txtFax.getText(), txtEmail.getText(), txtPresentAddress.getText(), txtPermanentAddress.getText()),
				new Person(0, txtNoKName.getText(), txtNoKMiddleName.getText(), txtNoKSurname.getText(), null, LocalDate.MIN, txtNoKTelephoneHome.getText(), txtNoKTelephoneWork.getText(), txtNoKMobile.getText(), txtNoKPager.getText(), txtNoKFax.getText(), txtNoKEmail.getText(), txtNoKContactAddress.getText(), txtNoKContactAddress.getText()),
				txtNoKRelationship.getText(),
				((MaritalStatus)ddlMaritalStatus.getSelectedItem()).getId(),
				((BloodType)(ddlBloodType.getSelectedItem())).getId(),
				Boolean.TRUE,
				(Integer)Integer.parseInt(txtNoOfDependents.getText()),
				(Float)Float.parseFloat(txtHeight.getText()),
				(Float)Float.parseFloat(txtWeight.getText()),
				txtOccupation.getText(),
				(Integer)Integer.parseInt(txtGrossAnnualIncome.getText()),
				(Boolean)chckbxVegetarian.isSelected(),
				(Boolean)chckbxSmoker.isSelected(),
				(Integer)(chckbxSmoker.isSelected() ? Integer.parseInt(txtAvgCigarettesPerDay.getText()) : 0),
				(Boolean)chckbxConsumeAlcohol.isSelected(),
				(Integer)(chckbxConsumeAlcohol.isSelected() ? Integer.parseInt(txtAvgDrinksPerDay.getText()) : 0),
				(Boolean)chckbxUseStimulants.isSelected(),
				chckbxUseStimulants.isSelected() ? txtStimulants.getText() : "",
				(Integer)Integer.parseInt(txtCoffeeTeaPerDay.getText()),
				(Integer)Integer.parseInt(txtSoftDrinksPerDay.getText()),
				(Boolean)chckbxRegularMeals.isSelected(),
				(Boolean)chckbxPredominantlyEatsHome.isSelected(),
				txtStatementOfComplaint.getText(),
				txtHistoryOfPreviousTreatment.getText(),
				txtPhycianHospital.getText(),
				(Boolean)chckbxDiabetic.isSelected(),
				(Boolean)chckbxHypertensive.isSelected(),
				(Boolean)chckbxCardiacCondition.isSelected(),
				(Boolean)chckbxRaspiratoryCondition.isSelected(),
				(Boolean)chckbxDigestiveCondition.isSelected(),
				(Boolean)chckbxOrthopedicCondition.isSelected(),
				(Boolean)chckbxMuscularCondition.isSelected(),
				(Boolean)chckbxNeurologicalCondition.isSelected(),
				txtKnownAllergies.getText(),
				txtKnownAdverseReactionToSpecificDrugs.getText(),
				txtMajorSurgeries.getText());
	}

	private void clearComponents() {
		txtName.setText("");
		txtMiddleName.setText("");
		txtSurname.setText("");
		txtTelephoneHome.setText("");
		txtTelephoneWork.setText("");
		txtPresentAddress.setText("");
		txtPermanentAddress.setText("");
		txtMobile.setText("");
		txtPager.setText("");
		txtFax.setText("");
		txtNoKSurname.setText("");
		txtNoKMiddleName.setText("");
		txtNoKName.setText("");
		txtNoKContactAddress.setText("");
		txtNoKTelephoneHome.setText("");
		txtNoKTelephoneWork.setText("");
		txtNoKMobile.setText("");
		txtNoKPager.setText("");
		txtNoKFax.setText("");
		txtNoKEmail.setText("");
		txtEmail.setText("");
		txtOccupation.setText("");
		txtGrossAnnualIncome.setText("");
		txtWeight.setText("");
		txtHeight.setText("");
		txtNoOfDependents.setText("");
		txtAvgCigarettesPerDay.setText("");
		txtAvgDrinksPerDay.setText("");
		txtStimulants.setText("");
		txtSoftDrinksPerDay.setText("");
		txtCoffeeTeaPerDay.setText("");
		txtKnownAllergies.setText("");
		txtKnownAdverseReactionToSpecificDrugs.setText("");
		txtMajorSurgeries.setText("");
		txtHistoryOfPreviousTreatment.setText("");
		txtPhycianHospital.setText("");
		txtStatementOfComplaint.setText("");
		txtNoKRelationship.setText("");
		ddlSex.setSelectedIndex(0);
		ddlMaritalStatus.setSelectedIndex(0);
		ddlBloodType.setSelectedIndex(0);
		chckbxVegetarian.setSelected(false);
		chckbxSmoker.setSelected(false);
		chckbxConsumeAlcohol.setSelected(false);
		chckbxUseStimulants.setSelected(false);
		chckbxRegularMeals.setSelected(false);
		chckbxPredominantlyEatsHome.setSelected(false);
		chckbxDiabetic.setSelected(false);
		chckbxHypertensive.setSelected(false);
		chckbxCardiacCondition.setSelected(false);
		chckbxRaspiratoryCondition.setSelected(false);
		chckbxDigestiveCondition.setSelected(false);
		chckbxOrthopedicCondition.setSelected(false);
		chckbxMuscularCondition.setSelected(false);
		chckbxNeurologicalCondition.setSelected(false);
		datePicker.getModel().setValue(null);
		
	}
	
	public ComprehensiveRegistrationForm() {
		setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1050, 644);
		add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		tabbedPane.addTab("Basic", null, panel, null);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setBounds(34, 32, 185, 14);
		panel.add(lblFirstName);
		
		JLabel label_1 = new JLabel("Middle name:");
		label_1.setHorizontalAlignment(SwingConstants.TRAILING);
		label_1.setBounds(34, 57, 185, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Surname:");
		label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		label_2.setBounds(34, 84, 185, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Sex:");
		label_3.setHorizontalAlignment(SwingConstants.TRAILING);
		label_3.setBounds(34, 106, 185, 14);
		panel.add(label_3);
		
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
		datePicker.setBounds(229, 129, 145, 27);
		panel.add(datePicker);
		
		
		JLabel label_4 = new JLabel("Date of birth:");
		label_4.setHorizontalAlignment(SwingConstants.TRAILING);
		label_4.setBounds(34, 138, 185, 14);
		panel.add(label_4);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(229, 26, 145, 20);
		panel.add(txtName);
		
		txtMiddleName = new JTextField();
		txtMiddleName.setColumns(10);
		txtMiddleName.setBounds(229, 51, 145, 20);
		panel.add(txtMiddleName);
		
		txtSurname = new JTextField();
		txtSurname.setColumns(10);
		txtSurname.setBounds(229, 78, 145, 20);
		panel.add(txtSurname);
		
		ddlSex = new JComboBox<Sex>();
		ddlSex.setBounds(229, 103, 145, 20);
		ddlSex.setModel(new DefaultComboBoxModel<>(Sex.values()));
		panel.add(ddlSex);
		
		JButton btnNewButton = new JButton("Next page");
		btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnActionPerformed(evt);
            }
        });
		
		btnNewButton.setBounds(264, 167, 110, 23);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Contact", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label_5 = new JLabel("Telephone home:");
		label_5.setHorizontalAlignment(SwingConstants.TRAILING);
		label_5.setBounds(61, 130, 163, 14);
		panel_1.add(label_5);
		
		txtTelephoneHome = new JTextField();
		txtTelephoneHome.setColumns(10);
		txtTelephoneHome.setBounds(234, 124, 145, 20);
		panel_1.add(txtTelephoneHome);
		
		txtTelephoneWork = new JTextField();
		txtTelephoneWork.setColumns(10);
		txtTelephoneWork.setBounds(234, 149, 145, 20);
		panel_1.add(txtTelephoneWork);
		
		JLabel label_6 = new JLabel("Telephone work:");
		label_6.setHorizontalAlignment(SwingConstants.TRAILING);
		label_6.setBounds(61, 155, 163, 14);
		panel_1.add(label_6);
		
		JLabel lblPresentAddress = new JLabel("Present address (Door No, Street, Area, City, State, Pincode):");
		lblPresentAddress.setBounds(10, 17, 579, 14);
		panel_1.add(lblPresentAddress);
		
		txtPresentAddress = new JTextField();
		txtPresentAddress.setColumns(10);
		txtPresentAddress.setBounds(10, 36, 579, 20);
		panel_1.add(txtPresentAddress);
		
		txtPermanentAddress = new JTextField();
		txtPermanentAddress.setColumns(10);
		txtPermanentAddress.setBounds(10, 93, 579, 20);
		panel_1.add(txtPermanentAddress);
		
		JLabel lblPermanentAddressdoor = new JLabel("Permanent address (Door No, Street, Area, City, State, Pincode):");
		lblPermanentAddressdoor.setBounds(10, 67, 579, 14);
		panel_1.add(lblPermanentAddressdoor);
		
		JLabel lblMobile = new JLabel("Mobile:");
		lblMobile.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMobile.setBounds(61, 180, 163, 14);
		panel_1.add(lblMobile);
		
		txtMobile = new JTextField();
		txtMobile.setColumns(10);
		txtMobile.setBounds(234, 177, 145, 20);
		panel_1.add(txtMobile);
		
		JLabel lblPager = new JLabel("Pager:");
		lblPager.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPager.setBounds(61, 205, 163, 14);
		panel_1.add(lblPager);
		
		JLabel lblFax = new JLabel("Fax:");
		lblFax.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFax.setBounds(61, 230, 163, 14);
		panel_1.add(lblFax);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setBounds(61, 255, 163, 14);
		panel_1.add(lblEmail);
		
		txtPager = new JTextField();
		txtPager.setColumns(10);
		txtPager.setBounds(234, 202, 145, 20);
		panel_1.add(txtPager);
		
		txtFax = new JTextField();
		txtFax.setColumns(10);
		txtFax.setBounds(234, 227, 145, 20);
		panel_1.add(txtFax);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(234, 252, 145, 20);
		panel_1.add(txtEmail);
		
		JButton button = new JButton("Next page");
		button.setBounds(479, 283, 110, 23);
		button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnActionPerformed(evt);
            }
        });
		panel_1.add(button);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		tabbedPane.addTab("Next of kin", null, panel_2, null);
		
		txtNoKRelationship = new JTextField();
		txtNoKRelationship.setBorder(new LineBorder(Color.GRAY));
		txtNoKRelationship.setBounds(126, 91, 463, 20);
		panel_2.add(txtNoKRelationship);
		
		JLabel label_9 = new JLabel("Relationship:");
		label_9.setHorizontalTextPosition(SwingConstants.RIGHT);
		label_9.setHorizontalAlignment(SwingConstants.TRAILING);
		label_9.setBounds(10, 91, 106, 14);
		panel_2.add(label_9);
		
		JLabel label_15 = new JLabel("Surname:");
		label_15.setHorizontalAlignment(SwingConstants.TRAILING);
		label_15.setBounds(53, 66, 189, 14);
		panel_2.add(label_15);
		
		txtNoKSurname = new JTextField();
		txtNoKSurname.setColumns(10);
		txtNoKSurname.setBounds(252, 63, 145, 20);
		panel_2.add(txtNoKSurname);
		
		txtNoKMiddleName = new JTextField();
		txtNoKMiddleName.setColumns(10);
		txtNoKMiddleName.setBounds(252, 38, 145, 20);
		panel_2.add(txtNoKMiddleName);
		
		txtNoKName = new JTextField();
		txtNoKName.setColumns(10);
		txtNoKName.setBounds(252, 11, 145, 20);
		panel_2.add(txtNoKName);
		
		JLabel lblFirstName_1 = new JLabel("First name:");
		lblFirstName_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName_1.setBounds(53, 14, 189, 14);
		panel_2.add(lblFirstName_1);
		
		JLabel label_17 = new JLabel("Middle name:");
		label_17.setHorizontalAlignment(SwingConstants.TRAILING);
		label_17.setBounds(53, 39, 189, 14);
		panel_2.add(label_17);
		
		JLabel lblContactAddressdoor = new JLabel("Contact address (Door No, Street, Area, City, State, Pincode):");
		lblContactAddressdoor.setBounds(10, 122, 579, 14);
		panel_2.add(lblContactAddressdoor);
		
		txtNoKContactAddress = new JTextField();
		txtNoKContactAddress.setColumns(10);
		txtNoKContactAddress.setBounds(10, 148, 579, 20);
		panel_2.add(txtNoKContactAddress);
		
		JLabel label_8 = new JLabel("Telephone home:");
		label_8.setHorizontalAlignment(SwingConstants.TRAILING);
		label_8.setBounds(53, 185, 189, 14);
		panel_2.add(label_8);
		
		txtNoKTelephoneHome = new JTextField();
		txtNoKTelephoneHome.setColumns(10);
		txtNoKTelephoneHome.setBounds(252, 179, 145, 20);
		panel_2.add(txtNoKTelephoneHome);
		
		txtNoKTelephoneWork = new JTextField();
		txtNoKTelephoneWork.setColumns(10);
		txtNoKTelephoneWork.setBounds(252, 204, 145, 20);
		panel_2.add(txtNoKTelephoneWork);
		
		JLabel label_10 = new JLabel("Telephone work:");
		label_10.setHorizontalAlignment(SwingConstants.TRAILING);
		label_10.setBounds(53, 210, 189, 14);
		panel_2.add(label_10);
		
		JLabel label_11 = new JLabel("Mobile:");
		label_11.setHorizontalAlignment(SwingConstants.TRAILING);
		label_11.setBounds(53, 235, 189, 14);
		panel_2.add(label_11);
		
		txtNoKMobile = new JTextField();
		txtNoKMobile.setColumns(10);
		txtNoKMobile.setBounds(252, 232, 145, 20);
		panel_2.add(txtNoKMobile);
		
		txtNoKPager = new JTextField();
		txtNoKPager.setColumns(10);
		txtNoKPager.setBounds(252, 257, 145, 20);
		panel_2.add(txtNoKPager);
		
		JLabel label_12 = new JLabel("Pager:");
		label_12.setHorizontalAlignment(SwingConstants.TRAILING);
		label_12.setBounds(53, 260, 189, 14);
		panel_2.add(label_12);
		
		JLabel label_13 = new JLabel("Fax:");
		label_13.setHorizontalAlignment(SwingConstants.TRAILING);
		label_13.setBounds(53, 285, 189, 14);
		panel_2.add(label_13);
		
		txtNoKFax = new JTextField();
		txtNoKFax.setColumns(10);
		txtNoKFax.setBounds(252, 282, 145, 20);
		panel_2.add(txtNoKFax);
		
		txtNoKEmail = new JTextField();
		txtNoKEmail.setColumns(10);
		txtNoKEmail.setBounds(252, 307, 145, 20);
		panel_2.add(txtNoKEmail);
		
		JLabel label_14 = new JLabel("Email:");
		label_14.setHorizontalAlignment(SwingConstants.TRAILING);
		label_14.setBounds(53, 310, 189, 14);
		panel_2.add(label_14);
		
		JButton button_1 = new JButton("Next page");
		button_1.setBounds(479, 338, 110, 23);
		button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnActionPerformed(evt);
            }
        });
		panel_2.add(button_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		tabbedPane.addTab("Details", null, panel_3, null);
		
		JLabel lblPersonalDetails = new JLabel("Personal details:");
		lblPersonalDetails.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPersonalDetails.setBounds(46, 11, 186, 14);
		panel_3.add(lblPersonalDetails);
		
		JLabel lblOccupation = new JLabel("Occupation:");
		lblOccupation.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOccupation.setBounds(46, 203, 186, 14);
		panel_3.add(lblOccupation);
		
		txtOccupation = new JTextField();
		txtOccupation.setColumns(10);
		txtOccupation.setBounds(242, 200, 145, 20);
		panel_3.add(txtOccupation);
		
		txtGrossAnnualIncome = new JTextField();
		txtGrossAnnualIncome.setColumns(10);
		txtGrossAnnualIncome.setBounds(242, 225, 145, 20);
		panel_3.add(txtGrossAnnualIncome);
		
		JLabel lblGrossAnnualIncome = new JLabel("Gross annual income:");
		lblGrossAnnualIncome.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGrossAnnualIncome.setBounds(46, 228, 186, 14);
		panel_3.add(lblGrossAnnualIncome);
		
		ddlBloodType = new JComboBox<Object>();
		ddlBloodType.setBounds(242, 138, 145, 20);
		try {
			ddlBloodType.setModel(new DefaultComboBoxModel<>(PATIENT_HANDLER.getBloodTypes().toArray()));
		} catch (Exception e1) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error fething blood types!" + System.lineSeparator() + "Check your connection and try again!" + System.lineSeparator() + System.lineSeparator() + "Program will exit now!"), "Error", 0);
			System.exit(0);
		}
		panel_3.add(ddlBloodType);
		
		JLabel label_7 = new JLabel("Marital status:");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setBounds(46, 39, 186, 14);
		panel_3.add(label_7);
		
		ddlMaritalStatus = new JComboBox<Object>();
		ddlMaritalStatus.setBounds(242, 36, 145, 20);
		try {
			ddlMaritalStatus.setModel(new DefaultComboBoxModel<>(PATIENT_HANDLER.getMaritalStatuses().toArray()));
		} catch (Exception e1) {
			JOptionPane.showMessageDialog((Component)this.getParent(), (Object)("Error fething marital statuses!" + System.lineSeparator() + "Check your connection and try again!" + System.lineSeparator() + System.lineSeparator() + "Program will exit now!"), "Error", 0);
			System.exit(0);
		}
		panel_3.add(ddlMaritalStatus);
		
		JLabel label_18 = new JLabel("Blood type:");
		label_18.setHorizontalAlignment(SwingConstants.TRAILING);
		label_18.setBounds(46, 141, 186, 14);
		panel_3.add(label_18);
		
		txtWeight = new JTextField();
		txtWeight.setColumns(10);
		txtWeight.setBounds(242, 113, 145, 20);
		panel_3.add(txtWeight);
		
		JLabel label_19 = new JLabel("Weight:");
		label_19.setHorizontalTextPosition(SwingConstants.RIGHT);
		label_19.setHorizontalAlignment(SwingConstants.TRAILING);
		label_19.setBounds(46, 116, 186, 14);
		panel_3.add(label_19);
		
		JLabel label_20 = new JLabel("Height:");
		label_20.setHorizontalAlignment(SwingConstants.TRAILING);
		label_20.setBounds(46, 91, 186, 14);
		panel_3.add(label_20);
		
		txtHeight = new JTextField();
		txtHeight.setColumns(10);
		txtHeight.setBounds(242, 88, 145, 20);
		panel_3.add(txtHeight);
		
		txtNoOfDependents = new JTextField();
		txtNoOfDependents.setColumns(10);
		txtNoOfDependents.setBounds(242, 63, 145, 20);
		panel_3.add(txtNoOfDependents);
		
		JLabel label_21 = new JLabel("No. of dependents:");
		label_21.setHorizontalAlignment(SwingConstants.TRAILING);
		label_21.setBounds(46, 64, 186, 14);
		panel_3.add(label_21);
		
		JLabel lblProfessionDetails = new JLabel("Profession details:");
		lblProfessionDetails.setHorizontalAlignment(SwingConstants.TRAILING);
		lblProfessionDetails.setBounds(46, 178, 186, 14);
		panel_3.add(lblProfessionDetails);
		
		JButton button_2 = new JButton("Next page");
		button_2.setBounds(277, 256, 110, 23);
		button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnActionPerformed(evt);
            }
        });
		panel_3.add(button_2);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		tabbedPane.addTab("Lifestyle", null, panel_4, null);
		
		chckbxVegetarian = new JCheckBox("Vegetarian");
		chckbxVegetarian.setBounds(19, 9, 194, 23);
		panel_4.add(chckbxVegetarian);
		
		chckbxSmoker = new JCheckBox("Smoker");
		chckbxSmoker.setBounds(19, 35, 194, 23);
		panel_4.add(chckbxSmoker);
		
		txtAvgCigarettesPerDay = new JTextField();
		txtAvgCigarettesPerDay.setColumns(10);
		txtAvgCigarettesPerDay.setBounds(394, 36, 145, 20);
		panel_4.add(txtAvgCigarettesPerDay);
		
		JLabel lblAvgNoOf = new JLabel("Avg. no. of cigarettes/day:");
		lblAvgNoOf.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAvgNoOf.setBounds(235, 39, 149, 14);
		panel_4.add(lblAvgNoOf);
		
		chckbxConsumeAlcohol = new JCheckBox("Consume alcohol");
		chckbxConsumeAlcohol.setBounds(19, 61, 194, 23);
		panel_4.add(chckbxConsumeAlcohol);
		
		JLabel lblAvgNoOf_1 = new JLabel("Avg. no. of drinks/day:");
		lblAvgNoOf_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAvgNoOf_1.setBounds(235, 65, 149, 14);
		panel_4.add(lblAvgNoOf_1);
		
		txtAvgDrinksPerDay = new JTextField();
		txtAvgDrinksPerDay.setColumns(10);
		txtAvgDrinksPerDay.setBounds(394, 62, 145, 20);
		panel_4.add(txtAvgDrinksPerDay);
		
		chckbxUseStimulants = new JCheckBox("Use stimulants");
		chckbxUseStimulants.setBounds(19, 87, 194, 23);
		panel_4.add(chckbxUseStimulants);
		
		JLabel lblSpecify = new JLabel("Specify stimulants:");
		lblSpecify.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSpecify.setBounds(19, 117, 113, 14);
		panel_4.add(lblSpecify);
		
		txtStimulants = new JTextField();
		txtStimulants.setColumns(10);
		txtStimulants.setBounds(19, 142, 520, 20);
		panel_4.add(txtStimulants);
		
		JLabel label_22 = new JLabel("Consumption of coffee-tea/day:");
		label_22.setHorizontalAlignment(SwingConstants.TRAILING);
		label_22.setBounds(190, 179, 194, 14);
		panel_4.add(label_22);
		
		JLabel lblConsumptionOfSoft = new JLabel("Consumption of soft drinks/day:");
		lblConsumptionOfSoft.setHorizontalAlignment(SwingConstants.TRAILING);
		lblConsumptionOfSoft.setBounds(190, 204, 194, 14);
		panel_4.add(lblConsumptionOfSoft);
		
		txtSoftDrinksPerDay = new JTextField();
		txtSoftDrinksPerDay.setColumns(10);
		txtSoftDrinksPerDay.setBounds(394, 201, 145, 20);
		panel_4.add(txtSoftDrinksPerDay);
		
		txtCoffeeTeaPerDay = new JTextField();
		txtCoffeeTeaPerDay.setColumns(10);
		txtCoffeeTeaPerDay.setBounds(394, 173, 145, 20);
		panel_4.add(txtCoffeeTeaPerDay);
		
		chckbxRegularMeals = new JCheckBox("Have regular meals (breakfast, lunch, dinner)");
		chckbxRegularMeals.setBounds(19, 225, 349, 23);
		panel_4.add(chckbxRegularMeals);
		
		chckbxPredominantlyEatsHome = new JCheckBox("Predominantly eats home");
		chckbxPredominantlyEatsHome.setBounds(19, 251, 349, 23);
		panel_4.add(chckbxPredominantlyEatsHome);
		
		JButton button_3 = new JButton("Next page");
		button_3.setBounds(429, 281, 110, 23);
		button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnActionPerformed(evt);
            }
        });
		panel_4.add(button_3);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		tabbedPane.addTab("Important medical complaints", null, panel_5, null);
		
		chckbxDiabetic = new JCheckBox("Diabetic");
		chckbxDiabetic.setBounds(19, 9, 160, 23);
		panel_5.add(chckbxDiabetic);
		
		chckbxHypertensive = new JCheckBox("Hypertensive");
		chckbxHypertensive.setBounds(19, 35, 160, 23);
		panel_5.add(chckbxHypertensive);
		
		chckbxCardiacCondition = new JCheckBox("Cardiac condition");
		chckbxCardiacCondition.setBounds(19, 61, 160, 23);
		panel_5.add(chckbxCardiacCondition);
		
		chckbxRaspiratoryCondition = new JCheckBox("Raspiratory condition");
		chckbxRaspiratoryCondition.setBounds(19, 87, 160, 23);
		panel_5.add(chckbxRaspiratoryCondition);
		
		chckbxDigestiveCondition = new JCheckBox("Digestive condition");
		chckbxDigestiveCondition.setBounds(181, 9, 160, 23);
		panel_5.add(chckbxDigestiveCondition);
		
		chckbxOrthopedicCondition = new JCheckBox("Orthopedic condition");
		chckbxOrthopedicCondition.setBounds(181, 35, 160, 23);
		panel_5.add(chckbxOrthopedicCondition);
		
		chckbxMuscularCondition = new JCheckBox("Muscular condition");
		chckbxMuscularCondition.setBounds(181, 61, 160, 23);
		panel_5.add(chckbxMuscularCondition);
		
		chckbxNeurologicalCondition = new JCheckBox("Neurological condition");
		chckbxNeurologicalCondition.setBounds(181, 87, 160, 23);
		panel_5.add(chckbxNeurologicalCondition);
		
		JLabel lblKnownAllergies = new JLabel("Known allergies:");
		lblKnownAllergies.setBounds(19, 117, 579, 14);
		panel_5.add(lblKnownAllergies);
		
		txtKnownAllergies = new JTextField();
		txtKnownAllergies.setColumns(10);
		txtKnownAllergies.setBounds(19, 143, 579, 20);
		panel_5.add(txtKnownAllergies);
		
		JLabel lblKnownAdverseReaction = new JLabel("Known adverse reaction to specific drugs:");
		lblKnownAdverseReaction.setBounds(19, 174, 579, 14);
		panel_5.add(lblKnownAdverseReaction);
		
		txtKnownAdverseReactionToSpecificDrugs = new JTextField();
		txtKnownAdverseReactionToSpecificDrugs.setColumns(10);
		txtKnownAdverseReactionToSpecificDrugs.setBounds(19, 200, 579, 20);
		panel_5.add(txtKnownAdverseReactionToSpecificDrugs);
		
		JLabel lblMajorSurgeries = new JLabel("Major surgeries:");
		lblMajorSurgeries.setBounds(19, 231, 579, 14);
		panel_5.add(lblMajorSurgeries);
		
		txtMajorSurgeries = new JTextField();
		txtMajorSurgeries.setColumns(10);
		txtMajorSurgeries.setBounds(19, 257, 579, 20);
		panel_5.add(txtMajorSurgeries);
		
		txtHistoryOfPreviousTreatment = new JTextField();
		txtHistoryOfPreviousTreatment.setColumns(10);
		txtHistoryOfPreviousTreatment.setBounds(19, 314, 579, 20);
		panel_5.add(txtHistoryOfPreviousTreatment);
		
		JLabel lblHistoryOfPrevious = new JLabel("History of previous treatment:");
		lblHistoryOfPrevious.setBounds(19, 288, 579, 14);
		panel_5.add(lblHistoryOfPrevious);
		
		JLabel lblPhisicianhospitalTreated = new JLabel("Physician/hospital treated:");
		lblPhisicianhospitalTreated.setBounds(19, 345, 579, 14);
		panel_5.add(lblPhisicianhospitalTreated);
		
		txtPhycianHospital = new JTextField();
		txtPhycianHospital.setColumns(10);
		txtPhycianHospital.setBounds(19, 371, 579, 20);
		panel_5.add(txtPhycianHospital);
		
		JButton button_4 = new JButton("Next page");
		button_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	btnActionPerformed(evt);
            }
        });
		button_4.setBounds(488, 402, 110, 23);
		panel_5.add(button_4);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Finish", null, panel_6, null);
		panel_6.setLayout(null);
		
		txtStatementOfComplaint = new JTextField();
		txtStatementOfComplaint.setColumns(10);
		txtStatementOfComplaint.setBounds(10, 49, 579, 20);
		panel_6.add(txtStatementOfComplaint);
		
		JLabel label = new JLabel("Statement of complaint:");
		label.setBounds(10, 23, 579, 14);
		panel_6.add(label);
		
		JButton btnFinish = new JButton("Finish");
		btnFinish.setBounds(479, 92, 110, 23);
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFinishActionPerformed(e);
			}
		});
		panel_6.add(btnFinish);

	}
}
