package com.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();	
		
		
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upass = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		
		Connection con = null;
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/company?useSSL=false","root","");
				PreparedStatement pstmt = con.prepareStatement("insert into users(uname,upass,uemail,umobile) values(?,?,?,?)");
				pstmt.setString(1, uname);
				pstmt.setString(2, upass);
				pstmt.setString(3, uemail);
				pstmt.setString(4, umobile);
				
				int rs = pstmt.executeUpdate();
				RequestDispatcher dis = request.getRequestDispatcher("registration.jsp");
				if(rs>0)
					{
					  request.setAttribute("status", "success");
					  
					}
				else
					{
					   request.setAttribute("status","Failed");
				
					}
			dis.forward(request, response);
			}
			catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}finally {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
	}

}
