package com.emptytomb.dbmanager.utility;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class ConnectionFactory {
  private Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
  private static ConnectionFactory instance = null;
  private static Connection connection = null;

  private static final String URL = "jdbc:mysql://localhost:3306/emptytomb";
  private static final String USER = "root";
  private static final String PASSWORD = "Tsa6tsbc!";
  private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
  

  private ConnectionFactory() {
  }

  public static ConnectionFactory getInstance() {
      if (instance == null) {      
          synchronized (ConnectionFactory.class) {
              if (instance == null) {
                  instance = new ConnectionFactory();
              } 
          }
      }
      return instance;
  }
     
  public Connection getConnection() {
      if (connection == null) {
          try {
        	// This will load the MySQL driver, each DB has its own driver
        	  Class.forName(DRIVER_CLASS);
              connection = DriverManager.getConnection(URL, USER, PASSWORD);
          } catch (SQLException | ClassNotFoundException e) {
              logger.error(this.getClass().getName() + ": getConnection()");
              e.printStackTrace();
              //throw new DAOException(e, e.getMessage());
          }
      }
      return connection;
  }   
}