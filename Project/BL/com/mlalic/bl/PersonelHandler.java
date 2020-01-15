package com.mlalic.bl;

import java.util.List;

import com.mlalic.model.*;

public class PersonelHandler extends HandlerBase {
	public int authorizePersonel(String username, String password) throws Exception {
		return repository.authorizePersonel(username, password);
	}
	
	public int getPersonelIDByUsernamePassword(String username, String password) throws Exception {
		return repository.getPersonelIDByUsernamePassword(username, password);
	}
	
	public int insertPersonel(Personel personel, String password) throws Exception {
		return repository.insertPersonel(personel, password);
	}
	
	public void updatePersonel(Personel personel, String password) throws Exception {
		repository.updatePersonel(personel);
	}
	
	public void deletePersonel(int idPersonel) throws Exception {
		repository.deletePersonel(idPersonel);
	}
	
	public List<Personel> getPersonel() throws Exception {
		return repository.getPersonel();
	}
	
	public Personel getPersonel(int idPersonel) throws Exception {
		return repository.getPersonelByID(idPersonel);
	}
	
	public PersonelType getPersonelType(int id) throws Exception {
		return repository.getPersonelType(id);
	}
	
	public List<PersonelType> getPersonelTypes() throws Exception {
		return repository.getPersonelTypes();
	}
}
