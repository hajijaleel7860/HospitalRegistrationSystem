package com.ihub.www;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterController extends HttpServlet

{
	protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		
		//reading form data
		String email=req.getParameter("email");
		String sphone=req.getParameter("phone");
		long phone=Long.parseLong(sphone);
		String name=req.getParameter("name");
		String address=req.getParameter("address");
		String dob=req.getParameter("dob");
		
		//create bean class object
		RegisterBean rb=new RegisterBean();
		rb.setEmail(email);
		rb.setPhone(phone);
		rb.setName(name);
		rb.setAddress(address);
		rb.setDob(dob);
		
		//insert into database 
		Connection con=null;
		PreparedStatement ps=null;
		int result=0;
		String qry=null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","admin");
			qry="insert into registration values(?,?,?,?,?)";
			ps=con.prepareStatement(qry);
			//set the values
			ps.setString(1,rb.getEmail());
			ps.setLong(2,rb.getPhone());
			ps.setString(3,rb.getName());
			ps.setString(4,rb.getAddress());
			ps.setString(5,rb.getDob());
			
			//execute
			result=ps.executeUpdate();
			
			if(result==0)
			{
				RequestDispatcher rd=req.getRequestDispatcher("error.jsp");
				rd.forward(req,res);
			}
			else
			{
				RequestDispatcher rd=req.getRequestDispatcher("view.jsp");
				rd.forward(req,res);
			}
			
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
			pw.println(e);
		}
	
		
		
	}
}

