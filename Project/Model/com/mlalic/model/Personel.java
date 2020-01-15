package com.mlalic.model;

public class Personel {

	Integer IDPersonel;
	Integer personelTypeID;
	String Username;
	String FirstName;
	String MiddleName;
	String Surname;
	String Title;

	public Personel(Integer iDPersonel, Integer personelTypeID, String username, String firstName, String middleName, String surname, String title) {
		super();
		this.personelTypeID = personelTypeID;
		IDPersonel = iDPersonel;
		Username = username;
		FirstName = firstName;
		MiddleName = middleName;
		Surname = surname;
		Title = title;
	}

	public Integer getIDPersonel() {
		return IDPersonel;
	}

	public void setIDPersonel(Integer iDPersonel) {
		IDPersonel = iDPersonel;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}
	
	public Integer getPersonelTypeID() {
		return personelTypeID;
	}

	public void setPersonelTypeID(Integer personelTypeID) {
		this.personelTypeID = personelTypeID;
	}

	public String getMiddleName() {
		return MiddleName;
	}

	public void setMiddleName(String middleName) {
		MiddleName = middleName;
	}

	@Override
	public String toString() {
		return FirstName + (MiddleName.length() > 0 ? (" " + MiddleName + " ") : " ") + Surname;
	}

	
}
