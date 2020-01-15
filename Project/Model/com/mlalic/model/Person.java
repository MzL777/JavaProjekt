package com.mlalic.model;

import java.time.LocalDate;

public class Person {
	Integer IDPerson;
	String FirstName;
	String MiddleName;
	String Surname;
	Character Sex;
	LocalDate DateOfBirth;
	String TelephoneHome;
	String TelephoneWork;
	String Mobile;
	String Pager;
	String Fax;
	String Email;
	String PresentAddress;
	String PermanentAddress;

	public Person(Integer iDPerson, String firstName, String middleName, String surname, Character sex, LocalDate dateOfBirth,
			String telephoneHome, String telephoneWork, String mobile, String pager, String fax, String email,
			String presentAddress, String permanentAddress) {
		super();
		IDPerson = iDPerson;
		FirstName = firstName;
		MiddleName = middleName;
		Surname = surname;
		Sex = sex;
		DateOfBirth = dateOfBirth;
		TelephoneHome = telephoneHome;
		TelephoneWork = telephoneWork;
		Mobile = mobile;
		Pager = pager;
		Fax = fax;
		Email = email;
		PresentAddress = presentAddress;
		PermanentAddress = permanentAddress;
	}

	public Integer getIDPerson() {
		return IDPerson;
	}

	public void setIDPerson(Integer iDPerson) {
		IDPerson = iDPerson;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getMiddleName() {
		return MiddleName;
	}

	public void setMiddleName(String middleName) {
		MiddleName = middleName;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public Character getSex() {
		return Sex;
	}

	public void setSex(Character sex) {
		Sex = sex;
	}

	public LocalDate getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public String getTelephoneHome() {
		return TelephoneHome;
	}

	public void setTelephoneHome(String telephoneHome) {
		TelephoneHome = telephoneHome;
	}

	public String getTelephoneWork() {
		return TelephoneWork;
	}

	public void setTelephoneWork(String telephoneWork) {
		TelephoneWork = telephoneWork;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getPager() {
		return Pager;
	}

	public void setPager(String pager) {
		Pager = pager;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		Fax = fax;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPresentAddress() {
		return PresentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		PresentAddress = presentAddress;
	}

	public String getPermanentAddress() {
		return PermanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		PermanentAddress = permanentAddress;
	}

	@Override
	public String toString() {
		return FirstName + (MiddleName.length() > 0 ? (" " + MiddleName + " ") : " ") + Surname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IDPerson == null) ? 0 : IDPerson.hashCode());
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
		Person other = (Person) obj;
		if (IDPerson == null) {
			if (other.IDPerson != null)
				return false;
		} else if (!IDPerson.equals(other.IDPerson))
			return false;
		return true;
	}

	
	
	
}
