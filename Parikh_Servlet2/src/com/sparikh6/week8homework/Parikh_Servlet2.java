package com.sparikh6.week8homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Parikh_Servlet2
 * 
 * Sheetal Parikh
 * Week 8 - Homework 8
 * 
 */
@WebServlet("/Parikh_Servlet2")
public class Parikh_Servlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Parikh_Servlet2() {
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
		String month = null;
		String day = null;
		String year = null;
		String duration = null;
		String tour = null;
		String partySize = null;
		
		int hike_id = 0;
		int beginMonth = 0;
		int beginDay = 0;
		int beginYear = 0;
		int durationDays = 0;
		int numPeople = 0;
		
		// initializing socket
		Socket socket = null;
		PrintWriter out2 = null;
		BufferedReader in = null;
		String host = "web7.jhuep.com";
		int port = 20025;
		String getRate = null;
		double serverCost = 0;
		String cost;
		String costParty;
		String serverText;
		String errorMessage;
		String validDuration = null;
		String durationName = null;
		String monthName = null;
		
        try {
            month = request.getParameter("month");

            day = request.getParameter("day");

            year = request.getParameter("year");

            duration = request.getParameter("duration");

            tour = request.getParameter("tour");
            
            partySize = request.getParameter("partySize");      

    		hike_id = Integer.parseInt(tour);
    		beginYear = Integer.parseInt(year);
    		beginMonth = Integer.parseInt(month);
    		beginDay = Integer.parseInt(day);
    		durationDays = Integer.parseInt(duration);
    		numPeople = Integer.parseInt(partySize);
    		
    			try {
    				
					socket = new Socket(host,port);
					//System.out.println("Connected");
					
					//output stream sending data to socket
					out2 = new PrintWriter(socket.getOutputStream(), true);
					
					//input stream reading the server response
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					//creating output stream to server
					String serverInput = hike_id + ":" + beginYear + ":" + beginMonth + ":" + beginDay + ":" + durationDays;
					
					//sending output stream to server
					out2.println(serverInput);
					
					//reading in server output
					getRate = in.readLine();
					
					//closing connection		
					out2.close();
					in.close();
					socket.close();
										
				//process return string into an array
				String[] serverOutput = getRate.split(":");
				//1st index contains the total reservation cost
				serverCost = Double.parseDouble(serverOutput[0]);
				cost = serverOutput[0];
				costParty = String.valueOf(numPeople*serverCost);
				//2nd index contains the additional text included in the return string
				serverText = serverOutput[1];
				
				//printing total cost and the text from the server for testing
				//System.out.println(serverCost);
				//System.out.println(serverText);
				
				//determining if a duration is valid and getting name of tour
				if (tour.equals("0") && (duration.equals("3")||duration.equals("5"))) {
					validDuration = "valid";
					durationName = "Gardiner Path";
				} else if (tour.equals("1") && (duration.equals("2")||duration.equals("3")||duration.equals("4"))) {
					validDuration = "valid";
					durationName = "Hellroaring Plateau";
				} else if (tour.equals("2") && (duration.equals("5")||duration.equals("7"))) {
					validDuration = "valid";
					durationName = "Beaten Path";
				} else {
					validDuration = "invalid";
				}
				
				//getting name of Month
				if (month.equals("6")) {
					monthName = "June";
				} else if (month.equals("7")) {
					monthName = "July";
				} else if (month.equals("8")) {
					monthName = "August";
				} else if (month.equals("9")){
					monthName = "September";
				}
				
				
				if(serverCost > 0 && validDuration.equals("valid")) {
					//display Total Cost page if duration valid
					displayTotalCost(response,cost,monthName,durationName,duration,year,day,partySize,costParty);
				
						} else if (serverCost > 0 && validDuration.equals("invalid")) {
							
							//display invalid duration page
			                errorMessage = "***Duration not available for selected tour.  Please choose another duration***";
			                displayDurationError(response,errorMessage);
					
						} else {
							
							//display invalid date page
							errorMessage = "***Please choose a date within the current season***";
							displayDateError(response,errorMessage);
							
						}
                
				} catch (UnknownHostException eu) {
					System.err.println("Dont know about the host: web7.jhuep.com");
					System.exit(1);
					
				} catch (IOException eu) {
					System.err.println("Couldn't get the I/O for the connection to:"
							+ "web7.jhuep.com");
					System.exit(1);
					
				}
            
        } finally { 
            out.close();
        }
		
	}
	
	//displaying page for the estimated total tour cost
    private void displayTotalCost(HttpServletResponse response, String individualCost, String monthOutput, String tourOutput, 
    		String tourDuration, String yearOutput, String dayOutput, String peopleOutput, String totalCost) 
        	throws IOException {
        	
        	try (PrintWriter out = response.getWriter()) {
        		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        		out.println("<html>");
        		out.println("<head>");
        		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
        		out.println("<title>Beartooth Hiking Company</title>");
        		out.println("<style type=\"text/css\"></style>");
        		out.println("<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\">");
        		out.println("<script type = \"text/javascript\" src=\"dynamicSelect.js\"></script>");
        		out.println("<script type = \"text/javascript\" src=\"validations.js\"></script>");
        		out.println("</head>");
        		out.println("<body>");
        		out.println("<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\"\r\n" + 
        				"cellspacing=\"2\">");
        		out.println("<tbody>");
        		out.println("<tr>");
        		out.println("<td\r\n" + 
        				"	style=\"vertical-align: bottom; width: 362px; text-align: right;\"><img\r\n" + 
        				"	src=\"Hikers.PNG\" alt=\"\"></td>");
        		out.println("<td style=\"vertical-align: top; text-align: center; width: 620px;\"><img\r\n" + 
        				"src=\"Logo.PNG\" alt=\"\"></td>");
        		out.println("<td style=\"vertical-align: bottom; width: 359px;\"><img\r\n" + 
        				"src=\"Hikers.PNG\" alt=\"\"></td>");
        		out.println("</tr>");
        		out.println("</tbody>");
        		out.println("</table>");
        		out.println("<br>");
        		out.println("<br>");
        		out.println("<big>Welcome to <span style=\"font-family: Elephant;\">Beartooth Hiking");
        		out.println("Company</span>!&nbsp; Experience the beauty");
        		out.println("of the Beartooth Mountains spanning south central Montana and");
        		out.println("northwest Wyoming.&nbsp; Please see below for the hiking tours that are");
        		out.println("currently available.&nbsp; The tours are updated frequently based on");
        		out.println("the season and tour guide availability.<br>");
        		out.println("</big><br style=\"font-family: Elephant;\">");
        		out.println("<table\r\n" + 
        				"	style=\"text-align: left; width: 1000px; margin-left: auto; margin-right: auto; height: 165px;\"\r\n" + 
        				"	border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
        		out.println("<tbody>");
        		out.println("<tr>");
        		out.println("<td\r\n" + 
        				"	style=\"vertical-align: top; width: 450px; text-align: right; font-family: Elephant;\"><img\r\n" + 
        				"	src=\"Trail3.PNG\" alt=\"\"></td>");
        		out.println("<td style=\"vertical-align: top; width: 354px; text-align: center;\">");
        		out.println("<h3>Current Hiking Tours</h3>");
        		out.println("<ul>");
        		out.println("<li style=\"text-align: left;\"><big>Hellroaring Plateau</big></li>");
        		out.println("<li style=\"text-align: left;\"><big>Gardiner Lake</big></li>");
        		out.println("<li style=\"text-align: left;\"><big>Beaten Path</big><br>");
        		out.println("</li>");
        		out.println("</ul>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top; width: 503px; text-align: left;\"><img\r\n" + 
        				"src=\"Trail2.PNG\" alt=\"\"></td>");
        		out.println("</tr>");
        		out.println("</tbody>");
        		out.println("</table>");
        		out.println("<br>");
        		out.println("<br>");
        		out.println("<table\r\n" + 
        				"style=\"text-align: left; width: 903px; height: 160px; margin-left: auto; margin-right: auto;\"\r\n" + 
        				"border=\"1\" cellpadding=\"2\" cellspacing=\"2\">");
        		out.println("<tbody>");
        		out.println("<tr>");
        		out.println("<td colspan=\"4\" rowspan=\"1\" style=\"vertical-align: top;\">");
        		out.println("<h3>Hike Rates and Information</h3>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top;\"");
        		out.println("<h4>Tour</h4>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">");
        		out.println("<h4>Duration (days)</h4>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">");
        		out.println("<h4>Difficulty</h4>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">");
        		out.println("<h4>Rate*</h4>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top;\">Hellroaring Plateau<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">2, 3 or 4 <br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">Easy<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">$35/day<br>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top;\">Gardiner Lake<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">3 or 5<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">Intermediate<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">$40/day<br>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top;\">Beaten Path<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">5 or 7<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">Difficult<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top;\">$45/day<br>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<td colspan=\"4\" rowspan=\"1\" style=\"vertical-align: top;\">");
        		out.println("<h6>* Please note, all listed rates have a 50% surcharge for\r\n" + 
        				"Saturday or Sunday hikes.</h6>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("</tbody>");
        		out.println("</table>");
        		out.println("<br>");
        		out.println("<br>");
        		out.println("<table\r\n" + 
        				"style=\"text-align: left; width: 380px; margin-left: auto; margin-right: auto;\"\r\n" + 
        				"border=\"1\" cellpadding=\"2\" cellspacing=\"2\">");
        		out.println("<tbody>");
        		out.println("<tr align=\"center\">");
        		out.println("<td colspan=\"2\" rowspan=\"1\"\r\n" + 
        				"style=\"vertical-align: top; width: 229px;\">");
        		out.println("<h3>Estimate the cost of your tour</h3>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		//out.println("<form action=\"http://localhost:8080/Parikh_Servlet2/Parikh_Servlet2\" method=GET");
        		//out.println("onsubmit = \"return !!(validateMonth() & validateDay() & validateYear() & validateParty() & validateTour() & validateDuration());\">");
        		out.println("<form action=\"https://web7.jhuep.com:443/Parikh_Servlet2/Parikh_Servlet2\" method=GET");
        		out.println("onsubmit = \"return !!(validateMonth() & validateDay() & validateYear() & validateParty() & validateTour() & validateDuration());\">");
        		out.println("<td style=\"vertical-align: top; width: 110px;\">Select Date**:<br>");
        		out.println("</td>");
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
        		out.println("<option value=\"2020\">2020</option>");
        		out.println("<option value=\"2019\">2019</option>");
        		out.println("<option value=\"2018\">2018</option>");
        		out.println("<option value=\"2017\">2017</option>");
        		out.println("<option value=\"2016\">2016</option>");
        		out.println("<option value=\"2015\">2015</option>");
        		out.println("<option value=\"2014\">2014</option>");
        		out.println("<option value=\"2013\">2013</option>");
        		out.println("<option value=\"2012\">2012</option>");
        		out.println("<option value=\"2011\">2011</option>");
        		out.println("<option value=\"2010\">2010</option>");
        		out.println("<option value=\"2009\">2009</option>");
        		out.println("<option value=\"2008\">2008</option>");
        		out.println("<option value=\"2007\">2007</option>");
        		out.println("</select>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top; width: 110px;\">Size of Party:<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top; width: 229px;\">");
        		out.println("<input type = \"text\" name = \"partySize\" id = \"partySize\" maxlength = \"2\" size =\"15\" onchange=\"validateParty()\" >");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top; width: 110px;\">Available Tours:<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top; width: 229px;\">");
        		out.println("<select name = \"tour\" id = \"tour\" onchange=\"populate('tour','duration')\" onblur = \"return validateTour()\" >");
        		out.println("<option value=\"\">Choose Tour...</option>");
        		out.println("<option value=\"1\">Hellroaring Plateau</option>");
        		out.println("<option value=\"0\">Gardiner Lake</option>");
        		out.println("<option value=\"2\">Beaten Path</option>");
        		out.println("</select>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top; width: 110px;\">Duration:<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top; width: 229px;\">");
        		out.println("<select name = \"duration\" id = \"duration\" onblur = \"return validateDuration()\"></select>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top; width: 110px;\"><br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top; width: 229px;\"><input\r\n" + 
        				"type =\"SUBMIT\" name = \"submit\" value = \"Get Estimate\"> </td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top; width: 110px;\">Estimated Cost:<br>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top; width: 229px;\"><input \r\n" + 
        				"type = \"text\"  name = \"cost\" maxlength=\"15\" size=\"15\" value=$" + totalCost + "></td>");
        		out.println("</tr>");
        		out.println("<tr>");
        		out.println("<td colspan=\"2\" rowspan=\"1\" style=\"vertical-align: top;\">");
        		out.println("<h6>&nbsp;**Current season runs from 6/1/2007 to 9/30/2020.<br>");
        		out.println("</h6>");
        		out.println("</td>");
        		out.println("</tr>");
        		out.println("</tbody>");
        		out.println("</table>");
        		out.println("<br>");
        		out.println("<div style =\"text-align: center;\"><span\r\n" +
        				 "style=\"font-weight: bold;\"></span>*** The estimated cost of\r\n" + 
        				 "the "+ tourOutput +" for " +  tourDuration + " days starting on " + monthOutput + " " + dayOutput  + ", " + yearOutput +" is: $ " + individualCost + "0 *** <br\r\n" + 
        				 "style=\"color: red;\">");
        		out.println("<div style =\"text-align: center;\"><span\r\n" +
       				 "style=\"font-weight: bold;\"></span>***The estimated cost of\r\n" + 
       				 "a party of "+ peopleOutput +" is: $ " + totalCost + "0***<br\r\n" + 
       				 "style=\"color: red;\">");
        		out.println("</div>");
        		out.println("<br>");
        		out.println("<table\r\n" + 
        				"style=\"text-align: left; width: 1201px; height: 170px; margin-left: auto; margin-right: auto;\"\r\n" + 
        				"border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
        		out.println("<tbody>");
        		out.println("<tr>");
        		out.println("<td style=\"vertical-align: top; text-align: right; width: 300px;\"><img\r\n" + 
        				"style=\"width: 219px; height: 134px;\" src=\"WalkingTour.PNG\" alt=\"\"></td>");
        		out.println("<td style=\"vertical-align: top; width: 420px;\">");
        		out.println("<div>");
        		out.println("<h5>Have any questions or want to book a hike?<br>");
        		out.println("</h5>");
        		out.println("</div>");
        		out.println("<div>");
        		out.println("<p>Please contact us at: <br>\r\n" + 
        				"fakeemail@beartoothhiking.com<br>\r\n" + 
        				"(555) 555-5555</p>");
        		out.println("</div>");
        		out.println("</td>");
        		out.println("<td style=\"vertical-align: top; width: 300px;\"><img\r\n" + 
        				"style=\"width: 231px; height: 135px;\" src=\"TentLarge.PNG\" alt=\"\"></td>");
        		out.println("</tr>");
        		out.println("</tbody>");
        		out.println("</table>");
        		out.println("<br>");
        		out.println("<br>");
        		out.println("<div style=\"font-weight: bold;\">");
        		out.println("<address>For more information about the Beartooth Mountains please\r\n" + 
        				"visit: <a\r\n" + 
        				"href=\"https://www.fs.usda.gov/recarea/shoshone/recarea/?recid=80899\"\r\n" + 
        				"target=\"_blank\">https://www.fs.usda.gov/recarea/shoshone/recarea/?recid=80899</a></address>");
        		out.println("</div>");
        		out.println("<br>");
        		out.println("<br>");
        		out.println("<body>");
        		out.println("<html>");
        		
        	}
        
        }
	
    //displaying the error page if the wrong duration was selected
    private void displayDurationError(HttpServletResponse response, String durationError) 
        	throws IOException {
        	
    	try (PrintWriter out = response.getWriter()) {
    		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
    		out.println("<html>");
    		out.println("<head>");
    		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
    		out.println("<title>Beartooth Hiking Company</title>");
    		out.println("<style type=\"text/css\"></style>");
    		out.println("<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\">");
    		out.println("<script type = \"text/javascript\" src=\"dynamicSelect.js\"></script>");
    		out.println("<script type = \"text/javascript\" src=\"validations.js\"></script>");
    		out.println("</head>");
    		out.println("<body>");
    		out.println("<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\"\r\n" + 
    				"cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr>");
    		out.println("<td\r\n" + 
    				"	style=\"vertical-align: bottom; width: 362px; text-align: right;\"><img\r\n" + 
    				"	src=\"Hikers.PNG\" alt=\"\"></td>");
    		out.println("<td style=\"vertical-align: top; text-align: center; width: 620px;\"><img\r\n" + 
    				"src=\"Logo.PNG\" alt=\"\"></td>");
    		out.println("<td style=\"vertical-align: bottom; width: 359px;\"><img\r\n" + 
    				"src=\"Hikers.PNG\" alt=\"\"></td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<big>Welcome to <span style=\"font-family: Elephant;\">Beartooth Hiking");
    		out.println("Company</span>!&nbsp; Experience the beauty");
    		out.println("of the Beartooth Mountains spanning south central Montana and");
    		out.println("northwest Wyoming.&nbsp; Please see below for the hiking tours that are");
    		out.println("currently available.&nbsp; The tours are updated frequently based on");
    		out.println("the season and tour guide availability.<br>");
    		out.println("</big><br style=\"font-family: Elephant;\">");
    		out.println("<table\r\n" + 
    				"	style=\"text-align: left; width: 1000px; margin-left: auto; margin-right: auto; height: 165px;\"\r\n" + 
    				"	border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr>");
    		out.println("<td\r\n" + 
    				"	style=\"vertical-align: top; width: 450px; text-align: right; font-family: Elephant;\"><img\r\n" + 
    				"	src=\"Trail3.PNG\" alt=\"\"></td>");
    		out.println("<td style=\"vertical-align: top; width: 354px; text-align: center;\">");
    		out.println("<h3>Current Hiking Tours</h3>");
    		out.println("<ul>");
    		out.println("<li style=\"text-align: left;\"><big>Hellroaring Plateau</big></li>");
    		out.println("<li style=\"text-align: left;\"><big>Gardiner Lake</big></li>");
    		out.println("<li style=\"text-align: left;\"><big>Beaten Path</big><br>");
    		out.println("</li>");
    		out.println("</ul>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 503px; text-align: left;\"><img\r\n" + 
    				"src=\"Trail2.PNG\" alt=\"\"></td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<table\r\n" + 
    				"style=\"text-align: left; width: 903px; height: 160px; margin-left: auto; margin-right: auto;\"\r\n" + 
    				"border=\"1\" cellpadding=\"2\" cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr>");
    		out.println("<td colspan=\"4\" rowspan=\"1\" style=\"vertical-align: top;\">");
    		out.println("<h3>Hike Rates and Information</h3>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top;\"");
    		out.println("<h4>Tour</h4>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">");
    		out.println("<h4>Duration (days)</h4>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">");
    		out.println("<h4>Difficulty</h4>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">");
    		out.println("<h4>Rate*</h4>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top;\">Hellroaring Plateau<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">2, 3 or 4 <br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">Easy<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">$35/day<br>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top;\">Gardiner Lake<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">3 or 5<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">Intermediate<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">$40/day<br>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top;\">Beaten Path<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">5 or 7<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">Difficult<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">$45/day<br>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td colspan=\"4\" rowspan=\"1\" style=\"vertical-align: top;\">");
    		out.println("<h6>* Please note, all listed rates have a 50% surcharge for\r\n" + 
    				"Saturday or Sunday hikes.</h6>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<table\r\n" + 
    				"style=\"text-align: left; width: 380px; margin-left: auto; margin-right: auto;\"\r\n" + 
    				"border=\"1\" cellpadding=\"2\" cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr align=\"center\">");
    		out.println("<td colspan=\"2\" rowspan=\"1\"\r\n" + 
    				"style=\"vertical-align: top; width: 229px;\">");
    		out.println("<h3>Estimate the cost of your tour</h3>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		//out.println("<form action=\"http://localhost:8080/Parikh_Servlet2/Parikh_Servlet2\" method=GET");
    		//out.println("onsubmit = \"return !!(validateMonth() & validateDay() & validateYear() & validateParty() & validateTour() & validateDuration());\">");
    		out.println("<form action=\"https://web7.jhuep.com:443/Parikh_Servlet2/Parikh_Servlet2\" method=GET");
    		out.println("onsubmit = \"return !!(validateMonth() & validateDay() & validateYear() & validateParty() & validateTour() & validateDuration());\">");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Select Date**:<br>");
    		out.println("</td>");
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
    		out.println("<option value=\"2020\">2020</option>");
    		out.println("<option value=\"2019\">2019</option>");
    		out.println("<option value=\"2018\">2018</option>");
    		out.println("<option value=\"2017\">2017</option>");
    		out.println("<option value=\"2016\">2016</option>");
    		out.println("<option value=\"2015\">2015</option>");
    		out.println("<option value=\"2014\">2014</option>");
    		out.println("<option value=\"2013\">2013</option>");
    		out.println("<option value=\"2012\">2012</option>");
    		out.println("<option value=\"2011\">2011</option>");
    		out.println("<option value=\"2010\">2010</option>");
    		out.println("<option value=\"2009\">2009</option>");
    		out.println("<option value=\"2008\">2008</option>");
    		out.println("<option value=\"2007\">2007</option>");
    		out.println("</select>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Size of Party:<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\">");
    		out.println("<input type = \"text\" name = \"partySize\" id = \"partySize\" maxlength = \"2\" size =\"15\" onchange=\"validateParty()\" >");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Available Tours:<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\">");
    		out.println("<select name = \"tour\" id = \"tour\" onchange=\"populate('tour','duration')\" onblur = \"return validateTour()\" >");
    		out.println("<option value=\"\">Choose Tour...</option>");
    		out.println("<option value=\"1\">Hellroaring Plateau</option>");
    		out.println("<option value=\"0\">Gardiner Lake</option>");
    		out.println("<option value=\"2\">Beaten Path</option>");
    		out.println("</select>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Duration:<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\">");
    		out.println("<select name = \"duration\" id = \"duration\" onblur = \"return validateDuration()\"></select>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\"><br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\"><input\r\n" + 
    				"type =\"SUBMIT\" name = \"submit\" value = \"Get Estimate\"> </td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Estimated Cost:<br>");
    		out.println("</td>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\"><input \r\n" + 
    				"type = \"text\"  name = \"cost\" maxlength=\"15\" size=\"15\" value=\"$ 0.00\"></td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td colspan=\"2\" rowspan=\"1\" style=\"vertical-align: top;\">");
    		out.println("<h6>&nbsp;**Current season runs from 6/1/2007 to 9/30/2020.<br>");
    		out.println("</h6>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<div style =\"text-align: center;\"><span\r\n" +
   				 "style = \"front-weight: bold; color: red;\">" + durationError + "</span><br>");
    		out.println("</div>");
    		out.println("<br>");
    		out.println("<table\r\n" + 
    				"style=\"text-align: left; width: 1201px; height: 170px; margin-left: auto; margin-right: auto;\"\r\n" + 
    				"border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; text-align: right; width: 300px;\"><img\r\n" + 
    				"style=\"width: 219px; height: 134px;\" src=\"WalkingTour.PNG\" alt=\"\"></td>");
    		out.println("<td style=\"vertical-align: top; width: 420px;\">");
    		out.println("<div>");
    		out.println("<h5>Have any questions or want to book a hike?<br>");
    		out.println("</h5>");
    		out.println("</div>");
    		out.println("<div>");
    		out.println("<p>Please contact us at: <br>\r\n" + 
    				"fakeemail@beartoothhiking.com<br>\r\n" + 
    				"(555) 555-5555</p>");
    		out.println("</div>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 300px;\"><img\r\n" + 
    				"style=\"width: 231px; height: 135px;\" src=\"TentLarge.PNG\" alt=\"\"></td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<div style=\"font-weight: bold;\">");
    		out.println("<address>For more information about the Beartooth Mountains please\r\n" + 
    				"visit: <a\r\n" + 
    				"href=\"https://www.fs.usda.gov/recarea/shoshone/recarea/?recid=80899\"\r\n" + 
    				"target=\"_blank\">https://www.fs.usda.gov/recarea/shoshone/recarea/?recid=80899</a></address>");
    		out.println("</div>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<body>");
    		out.println("<html>");
        		     		
        	}
    }
    
    //displaying the error page if invalid date selected
    private void displayDateError(HttpServletResponse response, String dateError) 
        	throws IOException {
        	
    	try (PrintWriter out = response.getWriter()) {
    		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
    		out.println("<html>");
    		out.println("<head>");
    		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
    		out.println("<title>Beartooth Hiking Company</title>");
    		out.println("<style type=\"text/css\"></style>");
    		out.println("<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\">");
    		out.println("<script type = \"text/javascript\" src=\"dynamicSelect.js\"></script>");
    		out.println("<script type = \"text/javascript\" src=\"validations.js\"></script>");
    		out.println("</head>");
    		out.println("<body>");
    		out.println("<table style=\"text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\"\r\n" + 
    				"cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr>");
    		out.println("<td\r\n" + 
    				"	style=\"vertical-align: bottom; width: 362px; text-align: right;\"><img\r\n" + 
    				"	src=\"Hikers.PNG\" alt=\"\"></td>");
    		out.println("<td style=\"vertical-align: top; text-align: center; width: 620px;\"><img\r\n" + 
    				"src=\"Logo.PNG\" alt=\"\"></td>");
    		out.println("<td style=\"vertical-align: bottom; width: 359px;\"><img\r\n" + 
    				"src=\"Hikers.PNG\" alt=\"\"></td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<big>Welcome to <span style=\"font-family: Elephant;\">Beartooth Hiking");
    		out.println("Company</span>!&nbsp; Experience the beauty");
    		out.println("of the Beartooth Mountains spanning south central Montana and");
    		out.println("northwest Wyoming.&nbsp; Please see below for the hiking tours that are");
    		out.println("currently available.&nbsp; The tours are updated frequently based on");
    		out.println("the season and tour guide availability.<br>");
    		out.println("</big><br style=\"font-family: Elephant;\">");
    		out.println("<table\r\n" + 
    				"	style=\"text-align: left; width: 1000px; margin-left: auto; margin-right: auto; height: 165px;\"\r\n" + 
    				"	border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr>");
    		out.println("<td\r\n" + 
    				"	style=\"vertical-align: top; width: 450px; text-align: right; font-family: Elephant;\"><img\r\n" + 
    				"	src=\"Trail3.PNG\" alt=\"\"></td>");
    		out.println("<td style=\"vertical-align: top; width: 354px; text-align: center;\">");
    		out.println("<h3>Current Hiking Tours</h3>");
    		out.println("<ul>");
    		out.println("<li style=\"text-align: left;\"><big>Hellroaring Plateau</big></li>");
    		out.println("<li style=\"text-align: left;\"><big>Gardiner Lake</big></li>");
    		out.println("<li style=\"text-align: left;\"><big>Beaten Path</big><br>");
    		out.println("</li>");
    		out.println("</ul>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 503px; text-align: left;\"><img\r\n" + 
    				"src=\"Trail2.PNG\" alt=\"\"></td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<table\r\n" + 
    				"style=\"text-align: left; width: 903px; height: 160px; margin-left: auto; margin-right: auto;\"\r\n" + 
    				"border=\"1\" cellpadding=\"2\" cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr>");
    		out.println("<td colspan=\"4\" rowspan=\"1\" style=\"vertical-align: top;\">");
    		out.println("<h3>Hike Rates and Information</h3>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top;\"");
    		out.println("<h4>Tour</h4>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">");
    		out.println("<h4>Duration (days)</h4>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">");
    		out.println("<h4>Difficulty</h4>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">");
    		out.println("<h4>Rate*</h4>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top;\">Hellroaring Plateau<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">2, 3 or 4 <br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">Easy<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">$35/day<br>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top;\">Gardiner Lake<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">3 or 5<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">Intermediate<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">$40/day<br>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top;\">Beaten Path<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">5 or 7<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">Difficult<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top;\">$45/day<br>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td colspan=\"4\" rowspan=\"1\" style=\"vertical-align: top;\">");
    		out.println("<h6>* Please note, all listed rates have a 50% surcharge for\r\n" + 
    				"Saturday or Sunday hikes.</h6>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<table\r\n" + 
    				"style=\"text-align: left; width: 380px; margin-left: auto; margin-right: auto;\"\r\n" + 
    				"border=\"1\" cellpadding=\"2\" cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr align=\"center\">");
    		out.println("<td colspan=\"2\" rowspan=\"1\"\r\n" + 
    				"style=\"vertical-align: top; width: 229px;\">");
    		out.println("<h3>Estimate the cost of your tour</h3>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		//out.println("<form action=\"http://localhost:8080/Parikh_Servlet2/Parikh_Servlet2\" method=GET");
    		//out.println("onsubmit = \"return !!(validateMonth() & validateDay() & validateYear() & validateParty() & validateTour() & validateDuration());\">");
    		out.println("<form action=\"https://web7.jhuep.com:443/Parikh_Servlet2/Parikh_Servlet2\" method=GET");
    		out.println("onsubmit = \"return !!(validateMonth() & validateDay() & validateYear() & validateParty() & validateTour() & validateDuration());\">");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Select Date**:<br>");
    		out.println("</td>");
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
    		out.println("<option value=\"2020\">2020</option>");
    		out.println("<option value=\"2019\">2019</option>");
    		out.println("<option value=\"2018\">2018</option>");
    		out.println("<option value=\"2017\">2017</option>");
    		out.println("<option value=\"2016\">2016</option>");
    		out.println("<option value=\"2015\">2015</option>");
    		out.println("<option value=\"2014\">2014</option>");
    		out.println("<option value=\"2013\">2013</option>");
    		out.println("<option value=\"2012\">2012</option>");
    		out.println("<option value=\"2011\">2011</option>");
    		out.println("<option value=\"2010\">2010</option>");
    		out.println("<option value=\"2009\">2009</option>");
    		out.println("<option value=\"2008\">2008</option>");
    		out.println("<option value=\"2007\">2007</option>");
    		out.println("</select>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Size of Party:<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\">");
    		out.println("<input type = \"text\" name = \"partySize\" id = \"partySize\" maxlength = \"2\" size =\"15\" onchange=\"validateParty()\" >");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Available Tours:<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\">");
    		out.println("<select name = \"tour\" id = \"tour\" onchange=\"populate('tour','duration')\" onblur = \"return validateTour()\" >");
    		out.println("<option value=\"\">Choose Tour...</option>");
    		out.println("<option value=\"1\">Hellroaring Plateau</option>");
    		out.println("<option value=\"0\">Gardiner Lake</option>");
    		out.println("<option value=\"2\">Beaten Path</option>");
    		out.println("</select>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Duration:<br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\">");
    		out.println("<select name = \"duration\" id = \"duration\" onblur = \"return validateDuration()\"></select>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\"><br>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\"><input\r\n" + 
    				"type =\"SUBMIT\" name = \"submit\" value = \"Get Estimate\"> </td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; width: 110px;\">Estimated Cost:<br>");
    		out.println("</td>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 229px;\"><input \r\n" + 
    				"type = \"text\"  name = \"cost\" maxlength=\"15\" size=\"15\" value=\"$ 0.00\"></td>");
    		out.println("</tr>");
    		out.println("<tr>");
    		out.println("<td colspan=\"2\" rowspan=\"1\" style=\"vertical-align: top;\">");
    		out.println("<h6>&nbsp;**Current season runs from 6/1/2007 to 9/30/2020.<br>");
    		out.println("</h6>");
    		out.println("</td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<div style =\"text-align: center;\"><span\r\n" +
   				 "style = \"front-weight: bold; color: red;\">" + dateError + "</span><br>");
    		out.println("</div>");
    		out.println("<br>");
    		out.println("<table\r\n" + 
    				"style=\"text-align: left; width: 1201px; height: 170px; margin-left: auto; margin-right: auto;\"\r\n" + 
    				"border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
    		out.println("<tbody>");
    		out.println("<tr>");
    		out.println("<td style=\"vertical-align: top; text-align: right; width: 300px;\"><img\r\n" + 
    				"style=\"width: 219px; height: 134px;\" src=\"WalkingTour.PNG\" alt=\"\"></td>");
    		out.println("<td style=\"vertical-align: top; width: 420px;\">");
    		out.println("<div>");
    		out.println("<h5>Have any questions or want to book a hike?<br>");
    		out.println("</h5>");
    		out.println("</div>");
    		out.println("<div>");
    		out.println("<p>Please contact us at: <br>\r\n" + 
    				"fakeemail@beartoothhiking.com<br>\r\n" + 
    				"(555) 555-5555</p>");
    		out.println("</div>");
    		out.println("</td>");
    		out.println("<td style=\"vertical-align: top; width: 300px;\"><img\r\n" + 
    				"style=\"width: 231px; height: 135px;\" src=\"TentLarge.PNG\" alt=\"\"></td>");
    		out.println("</tr>");
    		out.println("</tbody>");
    		out.println("</table>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<div style=\"font-weight: bold;\">");
    		out.println("<address>For more information about the Beartooth Mountains please\r\n" + 
    				"visit: <a\r\n" + 
    				"href=\"https://www.fs.usda.gov/recarea/shoshone/recarea/?recid=80899\"\r\n" + 
    				"target=\"_blank\">https://www.fs.usda.gov/recarea/shoshone/recarea/?recid=80899</a></address>");
    		out.println("</div>");
    		out.println("<br>");
    		out.println("<br>");
    		out.println("<body>");
    		out.println("<html>");
        		     		
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
