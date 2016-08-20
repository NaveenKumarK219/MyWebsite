package com.mywebsite;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean id = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LoginsDatabase","root","09tf1a0219");
			stmt = con.createStatement();
			//stmt.executeUpdate("create table if not exists userdetails(name varchar(30) not null,username varchar(30) not null,password varchar(40) not null, email varchar(40) not null,contact varchar(20) not null,primary key(username)");
			pstmt = con.prepareStatement("insert into userdetails values(?,?,?,?,?)");
			rs = stmt.executeQuery("select * from userdetails");
			while(rs.next())
			{
				if(username.equals(rs.getString(2))|| email.equals(rs.getString(4)))
				{
					id = true;
					request.getRequestDispatcher("MyWebIndex.html").include(request, response);
					out.print("<p style='text-align: center;background-color: yellow;'>Username or email id already exists</p>");
				}
			}
			if(id==false)
			{
				pstmt.setString(1, name);
				pstmt.setString(2, username);
				pstmt.setString(3, password);
				pstmt.setString(4, email);
				pstmt.setString(5, contact);
				pstmt.executeUpdate();
				request.getRequestDispatcher("MyWebIndex.html").include(request, response);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}
