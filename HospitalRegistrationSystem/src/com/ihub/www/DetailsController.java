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

public class DetailsController extends HttpServlet
{
	protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		//reading form data
				String bloodgroup=req.getParameter("bloodgroup");
				String sheight=req.getParameter("height");
				float height=Float.parseFloat(sheight);
				String sweight=req.getParameter("weight");
				int weight=Integer.parseInt(sweight);
				String sbloodpressure=req.getParameter("bloodpressure");
				int bloodpressure=Integer.parseInt(sbloodpressure);
				String sPulse=req.getParameter("pulse");
				int pulse=Integer.parseInt(sPulse);
				String sSpo2=req.getParameter("spo2");
				float sspo2=Float.parseFloat(sSpo2);
				String sTemperature=req.getParameter("temperature");
				double temperature=Double.parseDouble(sTemperature);
	
				//create bean class object
				PatientsDetail pd=new PatientsDetail();
	             pd.setBloodgroup(bloodgroup);
	             pd.setHeight(height);
	             pd.setWeight(weight);
	             pd.setBloodpressure(bloodpressure);
				 pd.setPulse(pulse);
				 pd.setSpo2(sspo2);
				 pd.setTemperature(temperature);
				 
				//insert into database 
				Connection con=null;
				PreparedStatement ps=null;
				int result=0;
				String qry=null;
				try
				{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","admin");
					qry="insert into medical values(?,?,?,?,?,?,?,?)";
					ps=con.prepareStatement(qry);
					//set the values
					ps.setString(1,pd.getBloodgroup());
					ps.setFloat(2,pd.getHeight());
					ps.setInt(3,pd.getWeight());
					ps.setInt(4,pd.getBloodpressure());
					ps.setInt(5,pd.getPulse());
					ps.setFloat(6,pd.getSpo2());
					ps.setDouble(7,pd.getTemperature());
					ps.setInt(8, 10);
					
					//execute
					result=ps.executeUpdate();
					
					if(result==0)
					{
						RequestDispatcher rd=req.getRequestDispatcher("error.jsp");
					rd.forward(req,res);					}
					else
					{
						RequestDispatcher rd=req.getRequestDispatcher("sucess.jsp");
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



