package com.billprint.start;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.billprint.dao.Dao;
import com.billprint.model.Databaseconn;
import com.jilaba.fileresource.Register;
import com.jilaba.fileresource.Server;

public class Dbconnection {
private String driver,connectionString;
public static Connection connection;
private static List<Databaseconn>lstDbname=null;
public Dbconnection() {
    try {
		driver="net.sourceforge.jtds.jdbc.Driver";
		Class.forName(driver).newInstance();
		String compydb=Register.getCompanyId()+"compydb";
		getconnection(compydb);
		Dao dao=new Dao();
		lstDbname=new ArrayList<Databaseconn>();
		lstDbname=dao.dbname();
		if(lstDbname.size()==0) {
		JOptionPane.showMessageDialog(null,"Operator Login Failed!!.");
		System.exit(0);
		}
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null,e.getMessage());
	}
  }
private void getconnection(String compydb) throws Exception {
	  connectionString="jdbc:jtds:sqlserver://"+Server.getServerName()+":"+Server.getPortNo()+"/"+compydb;
      connection=DriverManager.getConnection(connectionString, Server.getUserName(),Server.getPassword()); 
	  //JOptionPane.showMessageDialog(null, compydb+"Serverconnected!!");
 }
public static List<Databaseconn> getLstDbname() {
	return lstDbname;
}
public static void setLstDbname(List<Databaseconn> lstDbname) {
	Dbconnection.lstDbname = lstDbname;
}
}
