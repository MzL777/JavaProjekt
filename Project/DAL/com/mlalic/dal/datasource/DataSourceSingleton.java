package com.mlalic.dal.datasource;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.sql.DataSource;

public class DataSourceSingleton {

  private static final String SERVER_NAME = "localhost";
  private static final String INSTANCE_NAME = ".";
  private static final String DATABASE_NAME = "JAVAProjektMLalic";
  private static final int PORT = 1433;
  private static final String USER = "sa";
  private static final String PASSWORD = "SQL";
  
  

  private DataSourceSingleton() {}

  private static DataSource instance;

  public static DataSource getInstance() {
      if (instance == null) {
          instance = createInstance();
      }
      return instance;
  }
  private static DataSource createInstance() {
      SQLServerDataSource dataSource = new SQLServerDataSource();
      dataSource.setServerName(SERVER_NAME);
      dataSource.setInstanceName(INSTANCE_NAME);
      dataSource.setDatabaseName(DATABASE_NAME);
      dataSource.setPortNumber(PORT);
      dataSource.setUser(USER);
      dataSource.setPassword(PASSWORD);
      return dataSource;
  }   
}
