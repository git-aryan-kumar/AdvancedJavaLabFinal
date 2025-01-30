package mypackage;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.annotation.*;

@WebServlet("/Subjects")
public class Subjects extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Faculty";
    private static final String USER = "root";
    private static final String PASS = "";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		Integer Faculty_ID = Integer.parseInt(request.getParameter("Faculty_ID"));
		String Name = request.getParameter("Name");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");			

			conn = DriverManager.getConnection(SERVER_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS Faculty";
			pstmt = conn.prepareStatement(createDatabaseSQL);
			pstmt.executeUpdate();
			System.out.println("Database Created");
			
			conn.close();
			stmt.close();
			pstmt.close();
			
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			String createTableSQL = "CREATE TABLE IF NOT EXISTS Subjects (" +
					"Sub_ID INT PRIMARY KEY, " +
					"Name VARCHAR(100), " +
					"Faculty_ID INT)";
			pstmt = conn.prepareStatement(createTableSQL);
			pstmt.executeUpdate();
			System.out.println("Table Created");
			
			String insertTableSQL = "INSERT INTO Subjects (Sub_ID, Name, Faculty_ID) VALUES " +
					"(1,'SEM',1), " +
					"(2,'AIML',2), " +
					"(3,'DBS',3), " +
					"(4,'DBS LAB',3), " +
					"(5,'OS',4), " +
					"(6,'UNIX',4), " +
					"(7,'DSC LAB',3)";
			pstmt = conn.prepareStatement(insertTableSQL);
			pstmt.executeUpdate();
			System.out.println("Inserted 7 records into the table");
			
			String displaySQL = "SELECT * FROM Subjects";
			pstmt = conn.prepareStatement(displaySQL);
			ResultSet rs = pstmt.executeQuery();
			out.println("<table border = 1><tr><th>Subject ID</th><th>Name</th><th>Faculty ID</th></tr>");
			while (rs.next()) {
				out.println("<tr><td>" + rs.getInt("Sub_ID") + "</td><td>" + rs.getString("Name") + "</td><td>" + rs.getInt("Faculty_ID") + "</td></tr>");
			}
			out.println("</table>");
			
			String updateSQL = "UPDATE Subjects SET Name = ? WHERE Faculty_ID = ?";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, Name);
			pstmt.setInt(2, Faculty_ID);
			int rows = pstmt.executeUpdate();
			System.out.println("Updated Records");
			
			if (rows > 0) {
				out.println("Number of rows updated: " + rows);
			}
			else {
				out.println("No rows updated");
			}
			
			String displayUpdated = "SELECT * FROM Subjects";
			pstmt = conn.prepareStatement(displayUpdated);
			ResultSet rsUpdated = pstmt.executeQuery();
			out.println("<table border = 1><tr><th>Subject ID</th><th>Name</th><th>Faculty ID</th></tr>");
			while (rsUpdated.next()) {
				out.println("<tr><td>" + rsUpdated.getInt("Sub_ID") + "</td><td>" + rsUpdated.getString("Name") + "</td><td>" + rsUpdated.getInt("Faculty_ID") + "</td></tr>");
			}
			out.println("</table>");
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
