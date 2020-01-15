package com.mlalic.bl;

import com.mlalic.dal.repository.IRepository;
import com.mlalic.dal.repository.RepositoryFactory;

public abstract class HandlerBase {
	final IRepository repository;

	public HandlerBase() {
		repository = RepositoryFactory.getRepository();
	}
}
