package com.example.ExtensionProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertMethods {
	
	public PreparedStatement insertData() throws SQLException {
		Connection con= DBconnection.createNewDBconnection();
		String q= "insert into test_to_source(m_methodname, m_classname, m_testcase) values(?,?,?)";
		PreparedStatement st= con.prepareStatement(q);
		return st;
		
	}

}
