package com.mlalic.gui;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mlalic.bl.PatientHandler;
import com.mlalic.model.Outpatient;

public class PatientDetails extends JPanel {

	private static final long serialVersionUID = 6660154281897523024L;

	private static final PatientHandler PATIENT_HANDLER = new PatientHandler();
	
	public PatientDetails(Outpatient outpatient) {
		setMaximumSize(new Dimension(1060, 615));
		setMinimumSize(new Dimension(1060, 615));
		setBounds(new Rectangle(0, 0, 1060, 615));
		//setMaximumSize(new Dimension(950, 650));
		//setBounds(new Rectangle(0, 0, 950, 650));
		setPreferredSize(new Dimension(1060, 615));
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("First name:");
		lblNewLabel.setBounds(10, 11, 125, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblNewLabel);
		
		JLabel lblFirstName = new JLabel("");
		lblFirstName.setBounds(145, 11, 125, 14);
		lblFirstName.setText(outpatient.getPerson().getFirstName());
		add(lblFirstName);
		
		JLabel lblsn = new JLabel("Surname:");
		lblsn.setBounds(10, 61, 125, 14);
		lblsn.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblsn);
		
		JLabel lblMiddleName = new JLabel("");
		lblMiddleName.setBounds(145, 36, 125, 14);
		lblMiddleName.setText(outpatient.getPerson().getMiddleName());
		add(lblMiddleName);
		
		JLabel label_mn = new JLabel("Middle name:");
		label_mn.setBounds(10, 36, 125, 14);
		label_mn.setHorizontalAlignment(SwingConstants.TRAILING);
		add(label_mn);
		
		JLabel lblSurname = new JLabel("");
		lblSurname.setBounds(145, 61, 125, 14);
		lblSurname.setText(outpatient.getPerson().getSurname());
		add(lblSurname);
		
		JLabel lbls = new JLabel("Sex:");
		lbls.setBounds(10, 86, 125, 14);
		lbls.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lbls);
		
		JLabel lblSex = new JLabel("");
		lblSex.setBounds(145, 86, 125, 14);
		lblSex.setText(outpatient.getPerson().getSex().toString());
		add(lblSex);
		
		JLabel lbldob = new JLabel("Date of birth");
		lbldob.setBounds(10, 111, 125, 14);
		lbldob.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lbldob);
		
		JLabel lblDateOfBirth = new JLabel("");
		lblDateOfBirth.setBounds(145, 111, 125, 14);
		lblDateOfBirth.setText(outpatient.getPerson().getDateOfBirth().toString());
		add(lblDateOfBirth);
		
		JLabel lblth = new JLabel("Telephone (home):");
		lblth.setBounds(10, 136, 125, 14);
		lblth.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblth);
		
		JLabel lblTelephonehome = new JLabel("");
		lblTelephonehome.setBounds(145, 136, 125, 14);
		lblTelephonehome.setText(outpatient.getPerson().getTelephoneHome());
		add(lblTelephonehome);
		
		JLabel lbltw = new JLabel("Telephone (work):");
		lbltw.setBounds(10, 161, 125, 14);
		lbltw.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lbltw);
		
		JLabel lblTelephonework = new JLabel("");
		lblTelephonework.setBounds(145, 161, 125, 14);
		lblTelephonework.setText(outpatient.getPerson().getTelephoneWork());
		add(lblTelephonework);
		
		JLabel lblm = new JLabel("Mobile:");
		lblm.setBounds(10, 186, 125, 14);
		lblm.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblm);
		
		JLabel lblMobile = new JLabel("");
		lblMobile.setBounds(145, 186, 125, 14);
		lblMobile.setText(outpatient.getPerson().getMobile());
		add(lblMobile);
		
		JLabel lblpg = new JLabel("Pager:");
		lblpg.setBounds(280, 136, 59, 14);
		lblpg.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblpg);
		
		JLabel lblPager = new JLabel("");
		lblPager.setBounds(349, 136, 159, 14);
		lblPager.setText(outpatient.getPerson().getPager());
		add(lblPager);
		
		JLabel lblfx = new JLabel("Fax:");
		lblfx.setBounds(280, 161, 59, 14);
		lblfx.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblfx);
		
		JLabel lblFax = new JLabel("");
		lblFax.setBounds(349, 161, 159, 14);
		lblFax.setText(outpatient.getPerson().getFax());
		add(lblFax);
		
		JLabel lblem = new JLabel("Email:");
		lblem.setBounds(280, 186, 59, 14);
		lblem.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblem);
		
		JLabel lblEmail = new JLabel("");
		lblEmail.setBounds(349, 186, 159, 14);
		lblEmail.setText(outpatient.getPerson().getEmail());
		add(lblEmail);
		
		JLabel lblpra = new JLabel("Present address:");
		lblpra.setBounds(10, 211, 125, 14);
		lblpra.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblpra);
		
		JLabel lblPresentAddress = new JLabel("");
		lblPresentAddress.setBounds(145, 211, 395, 14);
		lblPresentAddress.setText(outpatient.getPerson().getPresentAddress());
		add(lblPresentAddress);
		
		JLabel lblper = new JLabel("Permanent address:");
		lblper.setBounds(10, 236, 125, 14);
		lblper.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblper);
		
		JLabel lblPermanentAddress = new JLabel("");
		lblPermanentAddress.setBounds(145, 236, 395, 14);
		lblPermanentAddress.setText(outpatient.getPerson().getPermanentAddress());
		add(lblPermanentAddress);
		
		JLabel lblFirstName_1 = new JLabel("First name:");
		lblFirstName_1.setBounds(10, 286, 125, 14);
		lblFirstName_1.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblFirstName_1);
		
		JLabel lblnok = new JLabel("NEXT OF KIN:");
		lblnok.setBounds(10, 261, 125, 14);
		add(lblnok);
		
		JLabel lblMiddleName_1 = new JLabel("Middle name:");
		lblMiddleName_1.setBounds(10, 311, 125, 14);
		lblMiddleName_1.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblMiddleName_1);
		
		JLabel lblnokMiddleName = new JLabel("");
		lblnokMiddleName.setBounds(145, 311, 125, 14);
		lblnokMiddleName.setText(outpatient.getNextOfKin().getMiddleName());
		add(lblnokMiddleName);
		
		JLabel lblSurname_1 = new JLabel("Surname:");
		lblSurname_1.setBounds(10, 336, 125, 14);
		lblSurname_1.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblSurname_1);
		
		JLabel lblnokSurname = new JLabel("");
		lblnokSurname.setBounds(145, 336, 125, 14);
		lblnokSurname.setText(outpatient.getNextOfKin().getSurname());
		add(lblnokSurname);
		
		JLabel lblnokFirstName = new JLabel("");
		lblnokFirstName.setBounds(145, 286, 125, 14);
		lblnokFirstName.setText(outpatient.getNextOfKin().getFirstName());
		add(lblnokFirstName);
		
		JLabel label = new JLabel("Relationship:");
		label.setBounds(280, 286, 106, 14);
		label.setHorizontalTextPosition(SwingConstants.RIGHT);
		add(label);
		
		JLabel label_8 = new JLabel("Telephone (home):");
		label_8.setBounds(10, 361, 125, 14);
		label_8.setHorizontalAlignment(SwingConstants.TRAILING);
		add(label_8);
		
		JLabel label_9 = new JLabel("Telephone (work):");
		label_9.setBounds(10, 386, 125, 14);
		label_9.setHorizontalAlignment(SwingConstants.TRAILING);
		add(label_9);
		
		JLabel label_10 = new JLabel("Mobile:");
		label_10.setBounds(10, 411, 125, 14);
		label_10.setHorizontalAlignment(SwingConstants.TRAILING);
		add(label_10);
		
		JLabel label_11 = new JLabel("Email:");
		label_11.setBounds(280, 411, 59, 14);
		label_11.setHorizontalAlignment(SwingConstants.TRAILING);
		add(label_11);
		
		JLabel label_12 = new JLabel("Fax:");
		label_12.setBounds(280, 386, 59, 14);
		label_12.setHorizontalAlignment(SwingConstants.TRAILING);
		add(label_12);
		
		JLabel label_13 = new JLabel("Pager:");
		label_13.setBounds(280, 361, 59, 14);
		label_13.setHorizontalAlignment(SwingConstants.TRAILING);
		add(label_13);
		
		JLabel lblnoka = new JLabel("Address:");
		lblnoka.setBounds(10, 436, 125, 14);
		lblnoka.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblnoka);
		
		JLabel lblnokAddress = new JLabel("");
		lblnokAddress.setBounds(145, 436, 395, 14);
		lblnokAddress.setText(outpatient.getNextOfKin().getPresentAddress());
		add(lblnokAddress);
		
		JLabel lblDetails = new JLabel("DETAILS:");
		lblDetails.setBounds(10, 461, 125, 14);
		add(lblDetails);
		
		JLabel lblnokTelephoneHome = new JLabel("");
		lblnokTelephoneHome.setBounds(145, 361, 125, 14);
		lblnokTelephoneHome.setText(outpatient.getNextOfKin().getTelephoneHome());
		add(lblnokTelephoneHome);
		
		JLabel lblnokTelephoneWork = new JLabel("");
		lblnokTelephoneWork.setBounds(145, 386, 125, 14);
		lblnokTelephoneWork.setText(outpatient.getNextOfKin().getTelephoneWork());
		add(lblnokTelephoneWork);
		
		JLabel lblnokMobile = new JLabel("");
		lblnokMobile.setBounds(145, 411, 125, 14);
		lblnokMobile.setText(outpatient.getNextOfKin().getMobile());
		add(lblnokMobile);
		
		JLabel lblnokPager = new JLabel("");
		lblnokPager.setBounds(349, 361, 159, 14);
		lblnokPager.setText(outpatient.getNextOfKin().getPager());
		add(lblnokPager);
		
		JLabel lblnokFax = new JLabel("");
		lblnokFax.setBounds(349, 386, 159, 14);
		lblnokFax.setText(outpatient.getNextOfKin().getFax());
		add(lblnokFax);
		
		JLabel lblnokEmail = new JLabel("");
		lblnokEmail.setBounds(349, 411, 159, 14);
		lblnokEmail.setText(outpatient.getNextOfKin().getEmail());
		add(lblnokEmail);
		
		JLabel lblms = new JLabel("Marital status:");
		lblms.setBounds(10, 486, 125, 14);
		lblms.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblms);
		
		JLabel lblMaritalStatus = new JLabel("");
		lblMaritalStatus.setBounds(145, 486, 125, 14);
		lblMaritalStatus.setText(outpatient.getMaritalStatusID() > 0 ? PATIENT_HANDLER.getMaritalStatus(outpatient.getMaritalStatusID()).getName() : "NA");
		add(lblMaritalStatus);
		
		JLabel lblBloodType = new JLabel("");
		lblBloodType.setBounds(145, 511, 125, 14);
		lblBloodType.setText(outpatient.getBloodTypeID() > 0 ? PATIENT_HANDLER.getBloodType(outpatient.getBloodTypeID()).getName() : "NA");
		add(lblBloodType);
		
		JLabel lblbt = new JLabel("Blood type:");
		lblbt.setBounds(10, 511, 125, 14);
		lblbt.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblbt);
		
		JLabel lblhe = new JLabel("Height");
		lblhe.setBounds(280, 486, 59, 14);
		lblhe.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblhe);
		
		JLabel lblHeight = new JLabel("");
		lblHeight.setBounds(349, 486, 159, 14);
		lblHeight.setText(outpatient.getHeight().toString());
		add(lblHeight);
		
		JLabel lblnod = new JLabel("Number of dependants:");
		lblnod.setBounds(10, 536, 125, 14);
		lblnod.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblnod);
		
		JLabel lblNumberOfDependants = new JLabel("");
		lblNumberOfDependants.setBounds(145, 536, 125, 14);
		lblNumberOfDependants.setText(outpatient.getNumberOfDependants().toString());
		add(lblNumberOfDependants);
		
		JLabel lbloc = new JLabel("Occupation:");
		lbloc.setBounds(10, 561, 125, 14);
		lbloc.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lbloc);
		
		JLabel lblOccupation = new JLabel("");
		lblOccupation.setBounds(145, 561, 395, 14);
		lblOccupation.setText(outpatient.getOccupation());
		add(lblOccupation);
		
		JLabel lblwe = new JLabel("Weight:");
		lblwe.setBounds(280, 511, 59, 14);
		lblwe.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblwe);
		
		JLabel lblWeight = new JLabel("");
		lblWeight.setBounds(349, 511, 159, 14);
		lblWeight.setText(outpatient.getWeight().toString());
		add(lblWeight);
		
		JLabel lblveg = new JLabel("Vegetarian:");
		lblveg.setBounds(520, 36, 125, 14);
		lblveg.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblveg);
		
		JLabel lblVegetarian = new JLabel("");
		lblVegetarian.setBounds(655, 36, 89, 14);
		lblVegetarian.setText(outpatient.isVegetarian().toString());
		add(lblVegetarian);
		
		JLabel lblgai = new JLabel("Gross annual income:");
		lblgai.setBounds(10, 586, 125, 14);
		lblgai.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblgai);
		
		JLabel lblGrossAnnualIncome = new JLabel("");
		lblGrossAnnualIncome.setBounds(145, 586, 125, 14);
		lblGrossAnnualIncome.setText(outpatient.getGrossAnnualIncome().toString());
		add(lblGrossAnnualIncome);
		
		JLabel lblacpd = new JLabel("Avg. cigarettes per day:");
		lblacpd.setBounds(754, 61, 161, 14);
		lblacpd.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblacpd);
		
		JLabel lblAvgCigarettesPerDay = new JLabel("");
		lblAvgCigarettesPerDay.setBounds(925, 61, 125, 14);
		lblAvgCigarettesPerDay.setText(outpatient.getAverageCigarettesPerDay().toString());
		add(lblAvgCigarettesPerDay);
		
		JLabel lblsm = new JLabel("Smoker:");
		lblsm.setBounds(520, 61, 125, 14);
		lblsm.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblsm);
		
		JLabel lblSmoker = new JLabel("");
		lblSmoker.setBounds(655, 61, 89, 14);
		lblSmoker.setText(outpatient.isSmoker().toString());
		add(lblSmoker);
		
		JLabel lbladpd = new JLabel("Avg. drinks per day:");
		lbladpd.setBounds(754, 86, 161, 14);
		lbladpd.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lbladpd);
		
		JLabel lblAvgDrinksPerDay = new JLabel("");
		lblAvgDrinksPerDay.setBounds(925, 86, 125, 14);
		lblAvgDrinksPerDay.setText(outpatient.getAverageDrinksPerDay().toString());
		add(lblAvgDrinksPerDay);
		
		JLabel lblca = new JLabel("Consume alcohol:");
		lblca.setBounds(520, 86, 125, 14);
		lblca.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblca);
		
		JLabel lblConsumeAlcohol = new JLabel("");
		lblConsumeAlcohol.setBounds(655, 86, 89, 14);
		lblConsumeAlcohol.setText(outpatient.isConsumeAlcoholicBeverage().toString());
		add(lblConsumeAlcohol);
		
		JLabel lblnokRelationship = new JLabel("");
		lblnokRelationship.setBounds(280, 311, 260, 14);
		lblnokRelationship.setText(outpatient.getNextOfKinRelationship());
		add(lblnokRelationship);
		
		JLabel lblLifestyle = new JLabel("IMPORTANT MEDICAL COMPLAINTS");
		lblLifestyle.setBounds(520, 186, 260, 14);
		add(lblLifestyle);
		
		JLabel lblus = new JLabel("Use stimulants:");
		lblus.setBounds(520, 111, 125, 14);
		lblus.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblus);
		
		JLabel lblUseStimulants = new JLabel("");
		lblUseStimulants.setBounds(655, 111, 89, 14);
		lblUseStimulants.setText(outpatient.isUseStimulants().toString());
		add(lblUseStimulants);
		
		JLabel lblStimulants = new JLabel("");
		lblStimulants.setBounds(754, 111, 296, 14);
		lblStimulants.setText(outpatient.getStimulants());
		add(lblStimulants);
		
		JLabel lblctpd = new JLabel("Coffee or tea per day:");
		lblctpd.setBounds(520, 136, 125, 14);
		lblctpd.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblctpd);
		
		JLabel lblCoffeeOrTeaPerDay = new JLabel("");
		lblCoffeeOrTeaPerDay.setBounds(655, 136, 89, 14);
		lblCoffeeOrTeaPerDay.setText(outpatient.getCoffeOrTeaPerDay().toString());
		add(lblCoffeeOrTeaPerDay);
		
		JLabel lblSoftDrinksPerDay = new JLabel("");
		lblSoftDrinksPerDay.setBounds(925, 136, 125, 14);
		lblSoftDrinksPerDay.setText(outpatient.getSoftDrinksPerDay().toString());
		add(lblSoftDrinksPerDay);
		
		JLabel lblsdpd = new JLabel("Soft drinks per day:");
		lblsdpd.setBounds(754, 136, 161, 14);
		lblsdpd.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblsdpd);
		
		JLabel lblhrm = new JLabel("Have regular meals:");
		lblhrm.setBounds(520, 161, 125, 14);
		lblhrm.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblhrm);
		
		JLabel lblHaveRegularMeals = new JLabel("");
		lblHaveRegularMeals.setBounds(655, 161, 89, 14);
		lblHaveRegularMeals.setText(outpatient.isHaveRegularMeals().toString());
		add(lblHaveRegularMeals);
		
		JLabel lblpeh = new JLabel("Predominantly eats home:");
		lblpeh.setBounds(754, 161, 161, 14);
		lblpeh.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblpeh);
		
		JLabel lblPredominantlyEatsHome = new JLabel("");
		lblPredominantlyEatsHome.setBounds(925, 161, 125, 14);
		lblPredominantlyEatsHome.setText(outpatient.isPredominantlyEatsHome().toString());
		add(lblPredominantlyEatsHome);
		
		JLabel lblhopt = new JLabel("History of previous treatment:");
		lblhopt.setBounds(520, 211, 260, 14);
		add(lblhopt);
		
		JLabel lblHistoryOfPreviousTreatment = new JLabel("");
		lblHistoryOfPreviousTreatment.setBounds(520, 236, 530, 14);
		lblHistoryOfPreviousTreatment.setText(outpatient.getHistoryOfPreviousTreatment());
		add(lblHistoryOfPreviousTreatment);
		
		JLabel lblPhysicianHospitalTreated = new JLabel("");
		lblPhysicianHospitalTreated.setBounds(520, 286, 530, 14);
		lblPhysicianHospitalTreated.setText(outpatient.getPhysicianHospitalTreated());
		add(lblPhysicianHospitalTreated);
		
		JLabel lblpht = new JLabel("Physician/hospital treated:");
		lblpht.setBounds(520, 261, 260, 14);
		add(lblpht);
		
		JLabel lbldia = new JLabel("Diabetic:");
		lbldia.setBounds(520, 311, 125, 14);
		lbldia.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lbldia);
		
		JLabel lblhy = new JLabel("Hypertensive:");
		lblhy.setBounds(520, 336, 125, 14);
		lblhy.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblhy);
		
		JLabel lblrc = new JLabel("Raspiratory condition:");
		lblrc.setBounds(520, 386, 125, 14);
		lblrc.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblrc);
		
		JLabel lblcc = new JLabel("Cardiac condition:");
		lblcc.setBounds(520, 361, 125, 14);
		lblcc.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblcc);
		
		JLabel lblRaspiratoryCondition = new JLabel("");
		lblRaspiratoryCondition.setBounds(655, 386, 125, 14);
		lblRaspiratoryCondition.setText(outpatient.isRaspiratoryCondition().toString());
		add(lblRaspiratoryCondition);
		
		JLabel lblCardiacCondition = new JLabel("");
		lblCardiacCondition.setBounds(655, 361, 125, 14);
		lblCardiacCondition.setText(outpatient.isCardiacCondition().toString());
		add(lblCardiacCondition);
		
		JLabel lblDiabetic = new JLabel("");
		lblDiabetic.setBounds(655, 311, 125, 14);
		lblDiabetic.setText(outpatient.isDiabetic().toString());
		add(lblDiabetic);
		
		JLabel lblHypertensive = new JLabel("");
		lblHypertensive.setBounds(655, 336, 125, 14);
		lblHypertensive.setText(outpatient.isHypertensive().toString());
		add(lblHypertensive);
		
		JLabel lbldc = new JLabel("Digestive condition:");
		lbldc.setBounds(790, 311, 125, 14);
		lbldc.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lbldc);
		
		JLabel lblocc = new JLabel("Orthopedic condition:");
		lblocc.setBounds(790, 336, 125, 14);
		lblocc.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblocc);
		
		JLabel lblnc = new JLabel("Neurological condition:");
		lblnc.setBounds(790, 386, 125, 14);
		lblnc.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblnc);
		
		JLabel lblmc = new JLabel("Muscular condition:");
		lblmc.setBounds(790, 361, 125, 14);
		lblmc.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblmc);
		
		JLabel lblNeurologicalCondition = new JLabel("");
		lblNeurologicalCondition.setBounds(925, 386, 125, 14);
		lblNeurologicalCondition.setText(outpatient.isNeurologicalCondition().toString());
		add(lblNeurologicalCondition);
		
		JLabel lblMuscularCondition = new JLabel("");
		lblMuscularCondition.setBounds(925, 361, 125, 14);
		lblMuscularCondition.setText(outpatient.isMuscularCondition().toString());
		add(lblMuscularCondition);
		
		JLabel lblDigestiveCondition = new JLabel("");
		lblDigestiveCondition.setBounds(925, 311, 125, 14);
		lblDigestiveCondition.setText(outpatient.isDigestiveCondition().toString());
		add(lblDigestiveCondition);
		
		JLabel lblOrthopedicCondition = new JLabel("");
		lblOrthopedicCondition.setBounds(925, 336, 125, 14);
		lblOrthopedicCondition.setText(outpatient.isOrthopedicCondition().toString());
		add(lblOrthopedicCondition);
		
		JLabel lblKnownAllergies = new JLabel("");
		lblKnownAllergies.setBounds(520, 436, 530, 14);
		lblKnownAllergies.setText(outpatient.getKnownAllergies());
		add(lblKnownAllergies);
		
		JLabel lblka = new JLabel("Known allergies:");
		lblka.setBounds(520, 411, 260, 14);
		add(lblka);
		
		JLabel lblKnownAdverseReaction = new JLabel("");
		lblKnownAdverseReaction.setBounds(520, 486, 530, 14);
		lblKnownAdverseReaction.setText(outpatient.getKnownAdverseReactionToSpecificDrugs());
		add(lblKnownAdverseReaction);
		
		JLabel lblkar = new JLabel("Known adverse reaction to specific drugs:");
		lblkar.setBounds(520, 461, 260, 14);
		add(lblkar);
		
		JLabel lblMayorSurgeriesHistory = new JLabel("");
		lblMayorSurgeriesHistory.setBounds(520, 536, 530, 14);
		lblMayorSurgeriesHistory.setText(outpatient.getMayorSurgeriesHistory());
		add(lblMayorSurgeriesHistory);
		
		JLabel lblmsh = new JLabel("Mayor surgeries history:");
		lblmsh.setBounds(520, 511, 260, 14);
		add(lblmsh);
		
		JLabel label_3 = new JLabel("LIFESTYLE:");
		label_3.setBounds(520, 11, 125, 14);
		add(label_3);
		
	}
}
