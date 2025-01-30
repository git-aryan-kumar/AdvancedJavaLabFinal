package mypackage;

import java.sql.*;

public class Employee {
	private static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/Employee";
	private static final String USER = "root";
	private static final String PASS = "";
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			conn = DriverManager.getConnection(SERVER_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS Employee";
			pstmt = conn.prepareStatement(createDatabaseSQL);
			pstmt.executeUpdate();
			System.out.println("Database Created If It Doesnt Already Exist");
			
			conn.close();
			pstmt.close();
			stmt.close();
			
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String createTableSQL = "CREATE TABLE IF NOT EXISTS EmployeeTable (" +
					"Ename VARCHAR(100), " +
					"Designation VARCHAR(100), " +
					"Dept VARCHAR(100), " +
					"Salary INT)";
			pstmt = conn.prepareStatement(createTableSQL);
			pstmt.executeUpdate();
			System.out.println("Table Created If It Doesnt Already Exist");
			
			String insertTableSQL = "INSERT INTO EmployeeTable (Ename, Designation, Dept, Salary) VALUES " +
					"('Aryan','Assistant Professor','CSE',20000000), " +
					"('Aniket','Associate Professor','ISE',15000000), " +
					"('Basavaraj','Assistant Professor','CY',10000000)";
			pstmt = conn.prepareStatement(insertTableSQL);
			pstmt.executeUpdate();
			System.out.println("Inserted 3 records into the table");
			
			System.out.println("--------------------------------------------------------------------------------------------------------");
			System.out.println("Employee Name\tDesignation\tDepartment\tSalary");
			String displaySQL = "SELECT * FROM EmployeeTable";
			pstmt = conn.prepareStatement(displaySQL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("Ename") + "\t" + rs.getString("Designation") + "\t" + rs.getString("Dept") + "\t" + rs.getInt("Salary"));
			}
			
			String updateSQL = "UPDATE EmployeeTable SET Designation = 'Associate Professor' WHERE Designation = 'Assistant Professor' AND Ename = 'Aryan'";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.executeUpdate();
			System.out.println("Updated Record");
			
			System.out.println("--------------------------------------------------------------------------------------------------------");
			System.out.println("Employee Name\tDesignation\tDepartment\tSalary");
			String displayUpdate = "SELECT * FROM EmployeeTable";
			pstmt = conn.prepareStatement(displayUpdate);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString("Ename") + "\t" + resultSet.getString("Designation") + "\t" + resultSet.getString("Dept") + "\t" + resultSet.getInt("Salary"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
