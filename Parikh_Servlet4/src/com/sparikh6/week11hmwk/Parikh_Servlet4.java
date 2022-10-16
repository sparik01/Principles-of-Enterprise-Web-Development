package com.sparikh6.week11hmwk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Parikh_Servlet4
 * 
  * Sheetal Parikh
 * Week 11 - Homework 11
 * 
 * Reference for determining row count of result set:
 * https://www.javamadesoeasy.com/2015/11/how-to-get-lengthsize-of-resultset-in.html
 * 
 * Reference for printing result set:
 * https://www.splessons.com/lesson/servlet-jdbc-resultset/
 * 
 * Reference for secure mySQL connection: https://web7.jhuep.com/~evansrb1/en605681/Security/SecureMySQL.html
 * 
 */
@WebServlet("/Parikh_Servlet4")
public class Parikh_Servlet4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String url = null;
	private final static String driver = "com.mysql.cj.jdbc.Driver";
	private final static String db = "class";
	
	 public static final String DATABASE_USER = "user";
	 public static final String DATABASE_PASSWORD = "password";
	 public static final String MYSQL_AUTO_RECONNECT = "autoReconnect";
	 public static final String MYSQL_MAX_RECONNECTS = "maxReconnects";
	 private static final Properties connProperties = new Properties();
	 	private static final char[] JKS_PASSWORD = null;
	 	private static final char[] KEY_PASSWORD = null;
	 	private static final String HOME="C:/Users/Sheetal/eclipse-workspace/Parikh_Servlet4/src/com/sparikh6/week11hmwk";
	   // things to do when we first start up this class
	 	static {
	   // default is to use the development database, only if we are on the production server will we change this
	 	String hostname = "web9.jhuep.com";
	   
	   // and the URL we connect to the database with
	 	url = "jdbc:mysql://" + hostname + ":3306/";
	   connProperties.put(DATABASE_USER, "johncolter");
	   connProperties.put(DATABASE_PASSWORD, "LetMeIn!");
	   
	   // set additional connection properti?serverTimees:
	   // if connection stales, then make automatically
	   // reconnect; make it alive again;
	   // if connection stales, then try for reconnection;
	   connProperties.put(MYSQL_AUTO_RECONNECT, "true");
	   connProperties.put(MYSQL_MAX_RECONNECTS, "4");
//			 now the ssl stuff
	   connProperties.put("useSSL", "true");
	   connProperties.put("verifyServerCertificate", "false");
	   // Setting the truststore
	   connProperties.put("trustStore", HOME + "truststore");
	   connProperties.put("trustCertificateKeyStoreUrl", "file:/" + HOME + "truststore");
	   connProperties.put("trustCertificateKeyStorePassword", "mypassword");
	   connProperties.put("serverTimezone", "US/Eastern");
	 	}
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Parikh_Servlet4() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		int rowCount = 0;
		String month = null;
		String day = null;
		String year = null;
		String monthQuery = null;
		String queryDate = null;
		String query = "SELECT reservation.StartDay, CONCAT(' ', reservation.First ,' ', reservation.Last), "
				+ "locations.location,CONCAT(' ', guides.First ,' ', guides.Last) FROM reservation "
				+ "INNER JOIN locations ON reservation.location = locations.idlocations "
				+ "INNER JOIN guides ON reservation.guide = guides.idguides WHERE reservation.StartDay >= ?"; 
		String queryOrder = "ORDER BY reservation.StartDay";
		
		String noReservationMsg = "**NO RESERVATIONS ON OR AFTER THE SELECTED DATE**";
		
        Connection connection = null;

        //getting month, day , and year from the entered date
        month = request.getParameter("month");
        day = request.getParameter("day");
        year = request.getParameter("year");
        
        //setting up the month string for mySQL format for query
        monthQuery = "0"+ month;
        
		try {
		    Class.forName(driver);
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
		try {
			queryDate = year + "-" + monthQuery + "-" + day;
		    //queryDate = "'2019-07-03'";
		    System.out.println(queryDate);
		    
			connection = DriverManager.getConnection(url + db, connProperties);
		    PreparedStatement stmt = connection.prepareStatement(query + queryOrder);
		    stmt.setString(1, queryDate);
		    		    
		    ResultSet rs = stmt.executeQuery();

		      //counting the number of rows
		      //pointer point to the last row of the result set
	           if (rs.last()) {
	               rowCount = rs.getRow();
	             //pointer moves to the 1st row of the result set
	               rs.beforeFirst();
	           }
		      
		      System.out.println(rowCount);
		   
		      if (rowCount > 0) {
		    	  
		    	// Printing Result Page
		       out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		       out.println("<html>");
		       out.println("<head>");
		       out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		       out.println("<title>Beartooth Hiking Company</title>");
		       out.println("<style type=\"text/css\"></style>");
		       out.println("<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\"/>");
		       out.println("<script type = \"text/javascript\" src=\"dynamicSelect.js\"></script>");
		       out.println("<script type = \"text/javascript\" src=\"validations.js\"></script>");
		       out.println("</head>");
		       out.println("<body>");
		       out.println("<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\"");
		       out.println("cellspacing=\"2\">");
		       out.println("<tbody>");
		       out.println("<tr>");
		       out.println("<td");
		       out.println("style=\"vertical-align: bottom; width: 362px; text-align: right;\"><img");
		       out.println("src=\"Hikers.PNG\" alt=\"\"></td>");
		       out.println("<td style=\"vertical-align: top; text-align: center; width: 620px;\"><img");
		       out.println("src=\"Logo.PNG\" alt=\"\"></td>");
		       out.println("<td style=\"vertical-align: bottom; width: 359px;\"><img");
		       out.println("src=\"Hikers.PNG\" alt=\"\"></td>");
		       out.println("</tr>");
		       out.println("</tbody>");
		       out.println("</table>");
		       out.println("<br>");
		       out.println("<center>");
		       out.println("<p>** Please select a date to view current reservations. **</p>");
		       out.println("<br>");
		       //out.println("<form action = \"./Parikh_Servlet4\" method = GET onsubmit = \"return !!(validateMonth() & validateDay() & validateYear());\">");
		       out.println("<form action = \"https://web7.jhuep.com:443/Parikh_Servlet4/Parikh_Servlet4\" method = GET onsubmit = \"return !!(validateMonth() & validateDay() & validateYear());\">");
		       out.println("<td style=\"vertical-align: top; width: 229px;\">&nbsp;");
		       out.println("<select name = \"month\" id = \"month\" onchange=\"generateDay('month','day')\" onblur = \"return validateMonth()\">");
		       out.println("<option value=\"\">Month</option>");
		       out.println("<option value=\"6\">June</option>");
		       out.println("<option value=\"7\">July</option>");
		       out.println("<option value=\"8\">August</option>");
		       out.println("<option value=\"9\">September</option>");
		       out.println("</select>");
		       out.println("<select name = \"day\" id = \"day\" onblur = \"return validateDay()\"></select>");
		       out.println("<select name = \"year\" id = \"year\" onblur = \"return validateYear()\">");
		       out.println("<option value=\"\">Year</option>");
		       out.println("<option value=\"2007\">2007</option>");
		       out.println("<option value=\"2008\">2008</option>");
		       out.println("<option value=\"2009\">2009</option>");
		       out.println("<option value=\"2010\">2010</option>");
		       out.println("<option value=\"2011\">2011</option>");
		       out.println("<option value=\"2012\">2012</option>");
		       out.println("<option value=\"2013\">2013</option>");
		       out.println("<option value=\"2014\">2014</option>");
		       out.println("<option value=\"2015\">2015</option>");
		       out.println("<option value=\"2016\">2016</option>");
		       out.println("<option value=\"2017\">2017</option>");
		       out.println("<option value=\"2018\">2018</option>");
		       out.println("<option value=\"2019\">2019</option>");
		       out.println("<option value=\"2020\">2020</option>");
		       out.println("<option value=\"2021\">2021</option>");
		       out.println("<option value=\"2022\">2022</option>");
		       out.println("<option value=\"2023\">2023</option>");
		       out.println("<option value=\"2024\">2024</option>");
		       out.println("<option value=\"2025\">2025</option>");
		       out.println("<option value=\"2026\">2026</option>");
		       out.println("<option value=\"2027\">2027</option>");
		       out.println("<option value=\"2028\">2028</option>");
		       out.println("<option value=\"2029\">2029</option>");
		       out.println("<option value=\"2030\">2030</option>");
		       out.println("<option value=\"2031\">2031</option>");
		       out.println("<option value=\"2032\">2032</option>");
		       out.println("<option value=\"2033\">2033</option>");
		       out.println("<option value=\"2034\">2034</option>");
		       out.println("<option value=\"2035\">2035</option>");
		       out.println("<option value=\"2036\">2036</option>");
		       out.println("<option value=\"2037\">2037</option>");
		       out.println("<option value=\"2038\">2038</option>");
		       out.println("<option value=\"2039\">2039</option>");
		       out.println("<option value=\"2040\">2040</option>");
		       out.println("</select>");
		       out.println("<br>");
		       out.println("<br>");
		       out.println("<input type = \"submit\" value = \"Click to retrieve listing\">");
		       out.println("</td>");
		       out.println("</form>");
		       out.println("<br>");
			   out.println("<p>**Reservation Listing**</p>");
			   out.println("<table border = 1 bgcolor='#e6e6e6'><tr><th>Start Day</th><th>Reservation Name</th><th>Location</th><th>Guide Name</th></tr>");
	 
	            // Printing Table Output//

	            while (rs.next()) {
	            	
	                out.println("<tr><td>");
	                out.println(rs.getString(1));
	                out.println("</td>");
	                out.println("<td>");
	                out.println(rs.getString(2));
	                out.println("</td>");
	                out.println("<td>");
	                out.println(rs.getString(3));
	                out.println("</td>");
	                out.println("<td>");
	                out.println(rs.getString(4));
	                out.println("</td>");
	                out.println("</tr>");  
	            	}
	 
	                out.println("</table>");
	    		    out.println("</center>");
	                out.println("</body>");
	                out.println("</html>");
			   
			   
			} else {
				
				// Printing "No Reservation" Page
			       out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
			       out.println("<html>");
			       out.println("<head>");
			       out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
			       out.println("<title>Beartooth Hiking Company</title>");
			       out.println("<style type=\"text/css\"></style>");
			       out.println("<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\"/>");
			       out.println("<script type = \"text/javascript\" src=\"dynamicSelect.js\"></script>");
			       out.println("<script type = \"text/javascript\" src=\"validations.js\"></script>");
			       out.println("</head>");
			       out.println("<body>");
			       out.println("<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\"");
			       out.println("cellspacing=\"2\">");
			       out.println("<tbody>");
			       out.println("<tr>");
			       out.println("<td");
			       out.println("style=\"vertical-align: bottom; width: 362px; text-align: right;\"><img");
			       out.println("src=\"Hikers.PNG\" alt=\"\"></td>");
			       out.println("<td style=\"vertical-align: top; text-align: center; width: 620px;\"><img");
			       out.println("src=\"Logo.PNG\" alt=\"\"></td>");
			       out.println("<td style=\"vertical-align: bottom; width: 359px;\"><img");
			       out.println("src=\"Hikers.PNG\" alt=\"\"></td>");
			       out.println("</tr>");
			       out.println("</tbody>");
			       out.println("</table>");
			       out.println("<br>");
			       out.println("<center>");
			       out.println("<p>** Please select a date to view current reservations. **</p>");
			       out.println("<br>");
			       //out.println("<form action = \"./Parikh_Servlet4\" method = GET onsubmit = \"return !!(validateMonth() & validateDay() & validateYear());\">");
			       out.println("<form action = \"https://web7.jhuep.com:443/Parikh_Servlet4/Parikh_Servlet4\" method = GET onsubmit = \"return !!(validateMonth() & validateDay() & validateYear());\">");
			       out.println("<td style=\"vertical-align: top; width: 229px;\">&nbsp;");
			       out.println("<select name = \"month\" id = \"month\" onchange=\"generateDay('month','day')\" onblur = \"return validateMonth()\">");
			       out.println("<option value=\"\">Month</option>");
			       out.println("<option value=\"6\">June</option>");
			       out.println("<option value=\"7\">July</option>");
			       out.println("<option value=\"8\">August</option>");
			       out.println("<option value=\"9\">September</option>");
			       out.println("</select>");
			       out.println("<select name = \"day\" id = \"day\" onblur = \"return validateDay()\"></select>");
			       out.println("<select name = \"year\" id = \"year\" onblur = \"return validateYear()\">");
			       out.println("<option value=\"\">Year</option>");
			       out.println("<option value=\"2007\">2007</option>");
			       out.println("<option value=\"2008\">2008</option>");
			       out.println("<option value=\"2009\">2009</option>");
			       out.println("<option value=\"2010\">2010</option>");
			       out.println("<option value=\"2011\">2011</option>");
			       out.println("<option value=\"2012\">2012</option>");
			       out.println("<option value=\"2013\">2013</option>");
			       out.println("<option value=\"2014\">2014</option>");
			       out.println("<option value=\"2015\">2015</option>");
			       out.println("<option value=\"2016\">2016</option>");
			       out.println("<option value=\"2017\">2017</option>");
			       out.println("<option value=\"2018\">2018</option>");
			       out.println("<option value=\"2019\">2019</option>");
			       out.println("<option value=\"2020\">2020</option>");
			       out.println("<option value=\"2021\">2021</option>");
			       out.println("<option value=\"2022\">2022</option>");
			       out.println("<option value=\"2023\">2023</option>");
			       out.println("<option value=\"2024\">2024</option>");
			       out.println("<option value=\"2025\">2025</option>");
			       out.println("<option value=\"2026\">2026</option>");
			       out.println("<option value=\"2027\">2027</option>");
			       out.println("<option value=\"2028\">2028</option>");
			       out.println("<option value=\"2029\">2029</option>");
			       out.println("<option value=\"2030\">2030</option>");
			       out.println("<option value=\"2031\">2031</option>");
			       out.println("<option value=\"2032\">2032</option>");
			       out.println("<option value=\"2033\">2033</option>");
			       out.println("<option value=\"2034\">2034</option>");
			       out.println("<option value=\"2035\">2035</option>");
			       out.println("<option value=\"2036\">2036</option>");
			       out.println("<option value=\"2037\">2037</option>");
			       out.println("<option value=\"2038\">2038</option>");
			       out.println("<option value=\"2039\">2039</option>");
			       out.println("<option value=\"2040\">2040</option>");
			       out.println("</select>");
			       out.println("<br>");
			       out.println("<br>");
			       out.println("<input type = \"submit\" value = \"Click to retrieve listing\">");
			       out.println("</td>");
			       out.println("</form>");
			       out.println("<br>");
			       //out.println("<p>**NO RESERVATIONS ON OR AFTER THE SELECTED DATE**</p>");
		    	   out.println("<div style =\"text-align: center;\"><span\r\n" +
		      				 "style = \"front-weight: bold; color: red;\">" + noReservationMsg + "</span><br>");
		       	   out.println("</div>");
	    		   out.println("</center>");
	               out.println("</body>");
	               out.println("</html>");
		   }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				 connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
			}
			out.close();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
