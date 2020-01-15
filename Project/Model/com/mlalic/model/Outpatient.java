package com.mlalic.model;

public class Outpatient {
	Integer IDOutpatient;
	Person Person;
	Person NextOfKin;
	String NextOfKinRelationship;
	Integer MaritalStatusID;
	Integer BloodTypeID;
	Boolean ComprehensiveFormFilled;
	Integer NumberOfDependants;
	Float Height;
	Float Weight;
	String Occupation;
	Integer GrossAnnualIncome;
	Boolean Vegetarian;
	Boolean Smoker;
	Integer AverageCigarettesPerDay;
	Boolean ConsumeAlcoholicBeverage;
	Integer AverageDrinksPerDay;
	Boolean UseStimulants;
	String Stimulants;
	Integer CoffeOrTeaPerDay;
	Integer SoftDrinksPerDay;
	Boolean HaveRegularMeals;
	Boolean PredominantlyEatsHome;
	String StatementOfComplaint;
	String HistoryOfPreviousTreatment;
	String PhysicianHospitalTreated;
	Boolean Diabetic;
	Boolean Hypertensive;
	Boolean CardiacCondition;
	Boolean RaspiratoryCondition;
	Boolean DigestiveCondition;
	Boolean OrthopedicCondition;
	Boolean MuscularCondition;
	Boolean NeurologicalCondition;
	String KnownAllergies;
	String KnownAdverseReactionToSpecificDrugs;
	String MayorSurgeriesHistory;

	

	public Outpatient(Integer iDOutpatient, Person person, Person nextOfKin,
			String nextOfKinRelationship, Integer maritalStatusID, Integer bloodTypeID, Boolean comprehensiveFormFilled,
			Integer numberOfDependants, Float height, Float weight, String occupation, Integer grossAnnualIncome,
			Boolean vegetarian, Boolean smoker, Integer averageCigarettesPerDay, Boolean consumeAlcoholicBeverage,
			Integer averageDrinksPerDay, Boolean useStimulants, String stimulants, Integer coffeOrTeaPerDay,
			Integer softDrinksPerDay, Boolean haveRegularMeals, Boolean predominantlyEatsHome,
			String statementOfComplaint, String historyOfPreviousTreatment, String physicianHospitalTreated,
			Boolean diabetic, Boolean hypertensive, Boolean cardiacCondition, Boolean raspiratoryCondition,
			Boolean digestiveCondition, Boolean orthopedicCondition, Boolean muscularCondition,
			Boolean neurologicalCondition, String knownAllergies, String knownAdverseReactionToSpecificDrugs,
			String mayorSurgeriesHistory) {
		super();
		IDOutpatient = iDOutpatient;
		Person = person;
		NextOfKin = nextOfKin;
		NextOfKinRelationship = nextOfKinRelationship;
		MaritalStatusID = maritalStatusID;
		BloodTypeID = bloodTypeID;
		ComprehensiveFormFilled = comprehensiveFormFilled;
		NumberOfDependants = numberOfDependants;
		Height = height;
		Weight = weight;
		Occupation = occupation;
		GrossAnnualIncome = grossAnnualIncome;
		Vegetarian = vegetarian;
		Smoker = smoker;
		AverageCigarettesPerDay = averageCigarettesPerDay;
		ConsumeAlcoholicBeverage = consumeAlcoholicBeverage;
		AverageDrinksPerDay = averageDrinksPerDay;
		UseStimulants = useStimulants;
		Stimulants = stimulants;
		CoffeOrTeaPerDay = coffeOrTeaPerDay;
		SoftDrinksPerDay = softDrinksPerDay;
		HaveRegularMeals = haveRegularMeals;
		PredominantlyEatsHome = predominantlyEatsHome;
		StatementOfComplaint = statementOfComplaint;
		HistoryOfPreviousTreatment = historyOfPreviousTreatment;
		PhysicianHospitalTreated = physicianHospitalTreated;
		Diabetic = diabetic;
		Hypertensive = hypertensive;
		CardiacCondition = cardiacCondition;
		RaspiratoryCondition = raspiratoryCondition;
		DigestiveCondition = digestiveCondition;
		OrthopedicCondition = orthopedicCondition;
		MuscularCondition = muscularCondition;
		NeurologicalCondition = neurologicalCondition;
		KnownAllergies = knownAllergies;
		KnownAdverseReactionToSpecificDrugs = knownAdverseReactionToSpecificDrugs;
		MayorSurgeriesHistory = mayorSurgeriesHistory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + IDOutpatient;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Outpatient other = (Outpatient) obj;
		if (IDOutpatient != other.IDOutpatient)
			return false;
		return true;
	}

	public String getNextOfKinRelationship() {
		return NextOfKinRelationship;
	}

	public void setNextOfKinRelationship(String nextOfKinRelationship) {
		NextOfKinRelationship = nextOfKinRelationship;
	}

	public Integer getIDOutpatient() {
		return IDOutpatient;
	}

	public void setIDOutpatient(Integer iDOutpatient) {
		IDOutpatient = iDOutpatient;
	}

	public Person getPerson() {
		return Person;
	}

	public void setPerson(Person person) {
		Person = person;
	}

	public Person getNextOfKin() {
		return NextOfKin;
	}

	public void setNextOfKin(Person nextOfKin) {
		NextOfKin = nextOfKin;
	}

	public Integer getMaritalStatusID() {
		return MaritalStatusID;
	}

	public void setMaritalStatusID(Integer maritalStatus) {
		MaritalStatusID = maritalStatus;
	}

	public Integer getBloodTypeID() {
		return BloodTypeID;
	}

	public void setBloodTypeID(Integer bloodTypeID) {
		BloodTypeID = bloodTypeID;
	}

	public Boolean isComprehensiveFormFilled() {
		return ComprehensiveFormFilled;
	}

	public void setComprehensiveFormFilled(Boolean comprehensiveFormFilled) {
		ComprehensiveFormFilled = comprehensiveFormFilled;
	}

	public Integer getNumberOfDependants() {
		return NumberOfDependants;
	}

	public void setNumberOfDependants(Integer numberOfDependants) {
		NumberOfDependants = numberOfDependants;
	}

	public Float getHeight() {
		return Height;
	}

	public void setHeight(Float height) {
		Height = height;
	}

	public Float getWeight() {
		return Weight;
	}

	public void setWeight(Float weight) {
		Weight = weight;
	}

	public String getOccupation() {
		return Occupation;
	}

	public void setOccupation(String occupation) {
		Occupation = occupation;
	}

	public Integer getGrossAnnualIncome() {
		return GrossAnnualIncome;
	}

	public void setGrossAnnualIncome(Integer grossAnnualIncome) {
		GrossAnnualIncome = grossAnnualIncome;
	}

	public Boolean isVegetarian() {
		return Vegetarian;
	}

	public void setVegetarian(Boolean vegetarian) {
		Vegetarian = vegetarian;
	}

	public Boolean isSmoker() {
		return Smoker;
	}

	public void setSmoker(Boolean smoker) {
		Smoker = smoker;
	}

	public Integer getAverageCigarettesPerDay() {
		return AverageCigarettesPerDay;
	}

	public void setAverageCigarettesPerDay(Integer averageCigarettesPerDay) {
		AverageCigarettesPerDay = averageCigarettesPerDay;
	}

	public Boolean isConsumeAlcoholicBeverage() {
		return ConsumeAlcoholicBeverage;
	}

	public void setConsumeAlcoholicBeverage(Boolean consumeAlcoholicBeverage) {
		ConsumeAlcoholicBeverage = consumeAlcoholicBeverage;
	}

	public Integer getAverageDrinksPerDay() {
		return AverageDrinksPerDay;
	}

	public void setAverageDrinksPerDay(Integer averageDrinksPerDay) {
		AverageDrinksPerDay = averageDrinksPerDay;
	}

	public Boolean isUseStimulants() {
		return UseStimulants;
	}

	public void setUseStimulants(Boolean useStimulants) {
		UseStimulants = useStimulants;
	}

	public String getStimulants() {
		return Stimulants;
	}

	public void setStimulants(String stimulants) {
		Stimulants = stimulants;
	}

	public Integer getCoffeOrTeaPerDay() {
		return CoffeOrTeaPerDay;
	}

	public void setCoffeOrTeaPerDay(Integer coffeOrTeaPerDay) {
		CoffeOrTeaPerDay = coffeOrTeaPerDay;
	}

	public Integer getSoftDrinksPerDay() {
		return SoftDrinksPerDay;
	}

	public void setSoftDrinksPerDay(Integer softDrinksPerDay) {
		SoftDrinksPerDay = softDrinksPerDay;
	}

	public Boolean isHaveRegularMeals() {
		return HaveRegularMeals;
	}

	public void setHaveRegularMeals(Boolean haveRegularMeals) {
		HaveRegularMeals = haveRegularMeals;
	}

	public Boolean isPredominantlyEatsHome() {
		return PredominantlyEatsHome;
	}

	public void setPredominantlyEatsHome(Boolean predominantlyEatsHome) {
		PredominantlyEatsHome = predominantlyEatsHome;
	}

	public String getStatementOfComplaint() {
		return StatementOfComplaint;
	}

	public void setStatementOfComplaint(String statementOfComplaInteger) {
		StatementOfComplaint = statementOfComplaInteger;
	}

	public String getHistoryOfPreviousTreatment() {
		return HistoryOfPreviousTreatment;
	}

	public void setHistoryOfPreviousTreatment(String historyOfPreviousTreatment) {
		HistoryOfPreviousTreatment = historyOfPreviousTreatment;
	}

	public String getPhysicianHospitalTreated() {
		return PhysicianHospitalTreated;
	}

	public void setPhysicianHospitalTreated(String physicianHospitalTreated) {
		PhysicianHospitalTreated = physicianHospitalTreated;
	}

	public Boolean isDiabetic() {
		return Diabetic;
	}

	public void setDiabetic(Boolean diabetic) {
		Diabetic = diabetic;
	}

	public Boolean isHypertensive() {
		return Hypertensive;
	}

	public void setHypertensive(Boolean hypertensive) {
		Hypertensive = hypertensive;
	}

	public Boolean isCardiacCondition() {
		return CardiacCondition;
	}

	public void setCardiacCondition(Boolean cardiacCondition) {
		CardiacCondition = cardiacCondition;
	}

	public Boolean isRaspiratoryCondition() {
		return RaspiratoryCondition;
	}

	public void setRaspiratoryCondition(Boolean raspiratoryCondition) {
		RaspiratoryCondition = raspiratoryCondition;
	}

	public Boolean isDigestiveCondition() {
		return DigestiveCondition;
	}

	public void setDigestiveCondition(Boolean digestiveCondition) {
		DigestiveCondition = digestiveCondition;
	}

	public Boolean isOrthopedicCondition() {
		return OrthopedicCondition;
	}

	public void setOrthopedicCondition(Boolean orthopedicCondition) {
		OrthopedicCondition = orthopedicCondition;
	}

	public Boolean isMuscularCondition() {
		return MuscularCondition;
	}

	public void setMuscularCondition(Boolean muscularCondition) {
		MuscularCondition = muscularCondition;
	}

	public Boolean isNeurologicalCondition() {
		return NeurologicalCondition;
	}

	public void setNeurologicalCondition(Boolean neurologicalCondition) {
		NeurologicalCondition = neurologicalCondition;
	}

	public String getKnownAllergies() {
		return KnownAllergies;
	}

	public void setKnownAllergies(String knownAllergies) {
		KnownAllergies = knownAllergies;
	}

	public String getKnownAdverseReactionToSpecificDrugs() {
		return KnownAdverseReactionToSpecificDrugs;
	}

	public void setKnownAdverseReactionToSpecificDrugs(String knownAdverseReactionToSpecificDrugs) {
		KnownAdverseReactionToSpecificDrugs = knownAdverseReactionToSpecificDrugs;
	}

	public String getMayorSurgeriesHistory() {
		return MayorSurgeriesHistory;
	}

	public void setMayorSurgeriesHistory(String mayorSurgeriesHistory) {
		MayorSurgeriesHistory = mayorSurgeriesHistory;
	}

	@Override
	public String toString() {
		return "IDOutpatient=" + IDOutpatient + "\nPerson=" + Person + "\nNextOfKin=" + NextOfKin
				+ "\nNextOfKinRelationship=" + NextOfKinRelationship + "\nMaritalStatusID=" + MaritalStatusID
				+ "\nBloodTypeID=" + BloodTypeID + "\nComprehensiveFormFilled=" + ComprehensiveFormFilled
				+ "\nNumberOfDependants=" + NumberOfDependants + "\nHeight=" + Height + "\nWeight=" + Weight
				+ "\nOccupation=" + Occupation + "\nGrossAnnualIncome=" + GrossAnnualIncome + "\nVegetarian="
				+ Vegetarian + "\nSmoker=" + Smoker + "\nAverageCigarettesPerDay=" + AverageCigarettesPerDay
				+ "\nConsumeAlcoholicBeverage=" + ConsumeAlcoholicBeverage + "\nAverageDrinksPerDay="
				+ AverageDrinksPerDay + "\nUseStimulants=" + UseStimulants + "\nStimulants=" + Stimulants
				+ "\nCoffeOrTeaPerDay=" + CoffeOrTeaPerDay + "\nSoftDrinksPerDay=" + SoftDrinksPerDay
				+ "\nHaveRegularMeals=" + HaveRegularMeals + "\nPredominantlyEatsHome=" + PredominantlyEatsHome
				+ "\nStatementOfComplaint=" + StatementOfComplaint + "\nHistoryOfPreviousTreatment="
				+ HistoryOfPreviousTreatment + "\nPhysicianHospitalTreated=" + PhysicianHospitalTreated + "\nDiabetic="
				+ Diabetic + "\nHypertensive=" + Hypertensive + "\nCardiacCondition=" + CardiacCondition
				+ "\nRaspiratoryCondition=" + RaspiratoryCondition + "\nDigestiveCondition=" + DigestiveCondition
				+ "\nOrthopedicCondition=" + OrthopedicCondition + "\nMuscularCondition=" + MuscularCondition
				+ "\nNeurologicalCondition=" + NeurologicalCondition + "\nKnownAllergies=" + KnownAllergies
				+ "\nKnownAdverseReactionToSpecificDrugs=" + KnownAdverseReactionToSpecificDrugs
				+ "\nMayorSurgeriesHistory=" + MayorSurgeriesHistory;
	}

	
	
}
