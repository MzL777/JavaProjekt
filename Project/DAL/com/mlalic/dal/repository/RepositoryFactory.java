package com.mlalic.dal.repository;

public class RepositoryFactory {
	public static IRepository getRepository() {
        return new SqlRepository();
    }
}
