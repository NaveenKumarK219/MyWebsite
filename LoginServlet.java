package com.mywebsite;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Connection con = null;
		Statement stmt = null;
		ResultSet rs =null;
		boolean id = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LoginsDatabase","root","09tf1a0219");
			stmt = con.createStatement();
			//stmt.executeUpdate("create table if not exists userdetails(name varchar(30) not null,username varchar(30) not null,password varchar(40) not null, email varchar(40) not null,contact varchar(20) not null,primary key(username)");
			rs = stmt.executeQuery("select * from userdetails");
			while(rs.next())
			{
				if(username.equals(rs.getString(2)) && password.equals(rs.getString(3)))
				{
					id = true;
					request.getRequestDispatcher("MainPage").forward(request, response);
					out.println("Logged-in successfully!!!");
				}
				else if(username.equals(rs.getString(2)) || password.equals(rs.getString(3))){
					out.print("<p id='p1'>Incorrect Username or Password</p>");
				}
			}
			if(id==false)
			{
				request.getRequestDispatcher("MyWebIndex.html").include(request, response);
				out.print("<p style='text-align: center;background-color: yellow;'>No record with the Username, Please Signup!!</p>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
