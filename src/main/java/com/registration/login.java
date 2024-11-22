package com.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail = request.getParameter("username");
		String upass = request.getParameter("password");
		
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/company?useSSL=false","root","");
			PreparedStatement pstmt = con.prepareStatement("select * from users where uemail = ? and upass= ?");
			
			
			pstmt.setString(1, uemail);
			pstmt.setString(2, upass);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				 session.setAttribute("name", rs.getString("uname"));
				 rd = request.getRequestDispatcher("index.jsp");
			}else
			{
				request.setAttribute("status","failed");
				rd = request.getRequestDispatcher("login.jsp");
				
			}
			rd.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
