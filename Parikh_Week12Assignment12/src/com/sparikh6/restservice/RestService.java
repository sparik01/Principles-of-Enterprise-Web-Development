package com.sparikh6.restservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("restservice")
public class RestService {
	
	//instance of RestService
    public RestService() {

    }
    
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/estimateRate/")
	public String estimateRate(@QueryParam("tour") String tour1,
			@QueryParam("month") String month1, @QueryParam("day") String day1,
			@QueryParam("year") String year1, @QueryParam("duration") String duration1,
			@QueryParam("partySize") String partySize1) {
		
	try {
		

		//printing data chosen by user on form to console
       	System.out.println("Selected Tour(hike_id): " + tour1);
    	System.out.println("Selected Year: " + year1);
    	System.out.println("Selected Month: " + month1);
    	System.out.println("Selected Day: " + day1);
    	System.out.println("Selected Duration: " + duration1);
    	System.out.println("Selected Party Size: " + partySize1);
    	
		//initializing variables
		int hike_id = 0;
		int beginMonth = 0;
		int beginDay = 0;
		int beginYear = 0;
		int durationDays = 0;
		int party = 0;
		double estimatedCost = 0;
		
		String validDuration = null;
		String validParty = null;
		String durationName = null;
		String monthName = null;
		String cost;
		
		//error messages
		String errorMessage = null;
		String partyError = "Invalid Party Size. Party size should be between 1 and 10";
		String tourError = "Invalid Tour: Choose between Beaten Path, Gardiner Lake, or Hellroaring Plateau";
		String dateError = "Invalid Day, Month, or Year";
		String durationError = "Invalid duration for selected tour";

        try {

        	beginYear = Integer.parseInt(year1);
			beginMonth = Integer.parseInt(month1);
			beginDay = Integer.parseInt(day1);
			hike_id = Integer.parseInt(tour1);
			durationDays = Integer.parseInt(duration1);
			party = Integer.parseInt(partySize1);
			
			//testing invalid inputs
        	//beginYear = 2005; //with this input get dateError
        	//beginYear = 0;   //with this input get dateError
			//beginMonth = 5;  //with this input get dateError
			//beginMonth = 0; //with this input get dateError
			//beginDay = 0;   //with this input get dateError
			//party = -1;   //with this input get partyError
			//hike_id = 7;   //with this input get tourError
			//durationDays = 10;  //results in durationError
			
			//Printing invalid inputs
//      System.out.println("Selected Tour(hike_id): " + hike_id);
//    	System.out.println("Selected Year: " + beginYear);
//    	System.out.println("Selected Month: " + beginMonth);
//    	System.out.println("Selected Day: " + beginDay);
//    	System.out.println("Selected Duration: " + durationDays);
//    	System.out.println("Selected Party Size: " + party);
			
			//setting the rate as per selected tour
   			Rates rate = checkRate(hike_id);
				
   			    //checking if valid tour selected
				if (rate != null) {
					System.out.println("Valid tour selected");
						} else {
							System.out.println("Hike_ID = " + tour1 + ": " + tourError);
							errorMessage = tourError;
				}

    			//checking if duration is valid
    			if ((hike_id==0) && ((durationDays==3)||(durationDays==5))) {
    				validDuration = "valid";
    				durationName = "Gardiner Path";
    			} else if ((hike_id==1) && ((durationDays==2)||(durationDays==3)||(durationDays==4))) {
    				validDuration = "valid";
    				durationName = "Hellroaring Plateau";
    			} else if ((hike_id==2) && ((durationDays==5)||(durationDays==7))) {
    				validDuration = "valid";
    				durationName = "Beaten Path";
    			} else {
    				validDuration = "invalid";
    				errorMessage = durationError;
    				System.out.println(errorMessage);
    			}

    			//checking if party size is between and including 1 and 10
    			if (party >= 0 && party < 11) {
    				System.out.println("Valid Party Size: " + party);
    				validParty = "valid";
    				} else {
    					System.out.println(partyError);
    					errorMessage = partyError;
    					validParty = "invalid";
    			}
    		
    				try {
    					//calling BookingDay from Rates to define a booking day from chosen date
    					BookingDay startBooking = new BookingDay(beginYear, beginMonth, beginDay);
    						// checking if the Start day of the booking is valid
    					if (startBooking.isValidDate()) {
    						//setting start date of the booking
    						rate.setBeginDate(startBooking);
    						//setting the rate of the reservation
							if(rate.setDuration(durationDays)) {
								//checking if reservation if valid
								if(rate.isValidDates()) {
									//calculating total cost 
									estimatedCost = rate.getCost()*party;
									System.out.println("Estimated Cost:" + estimatedCost);

								}else{
									//invalid input
									//System.out.println("Please select a date within the current season.");
									System.out.println("Other Details: " + rate.getDetails());
									//errorMessage = rate.getDetails();
									estimatedCost = rate.getCost();
									//errorMessage = "Invalid Duration";
									System.out.println("Estimated Cost: " + estimatedCost);
								}
					
							}else {
								//invalid duration
								System.out.println("Other Details: Invalid Duration");
								//errorMessage = rate.getDetails();
								estimatedCost = rate.getCost();
								System.out.println("Estimated Cost: " + estimatedCost);
							}
					
    					}else {
    						//invalid date
    						System.out.println("Other Details: Invalid Date");
    						//errorMessage = rate.getDetails();
    						estimatedCost = rate.getCost();
    						System.out.println("Estimated Cost: " + estimatedCost);
    					}
    					
    				} catch(Exception exe) {
    				System.out.println("Exception:" + tourError);
    				errorMessage = tourError;
    				}
    				
    		//checking for invalid input
			} catch(NumberFormatException nfe ) {
				errorMessage = "Invalid Input: Error when converting string to int.";
				System.out.println(errorMessage);
				//errorMessage = dateError;
			}

			if(estimatedCost > 0 && validDuration.equals("valid") && validParty.contentEquals("valid")) {
				//display Total Cost page if duration valid
				//displayTotalCost(response,cost,monthName,durationName,duration,year,day);
    			return "<html> " + "<title>" + "Estimated Cost"
				+ "</title>" + "<body><h1> Estimated Cost: " + estimatedCost + "0 " + "</h1>"
    			+ "<p>" + "Start Date: " + month1 + "/" + day1 + "/" + year1 + "<br/>" 
    			+ "Chosen Hike: " + durationName + "<br/>" 
    			+ "Chosen Duration: " + duration1 + " days" + "<br/>"
    			+ "Party Size: " + partySize1 + "<br/></body>"
				//+ "<br/><a href=\"http://localhost:8080/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
    			  + "<br/><a href=\"https://web7.jhuep.com:443/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
				
						} else if (estimatedCost > 0 && validDuration.equals("invalid") && validParty.contentEquals("valid")) {
							//display invalid duration page
			    			return "<html> " + "<title>" + "Invalid Data"
							+ "</title>" + "<body><p> Error: " + errorMessage
							//+ "</p></h1>" + "<a href=\"http://localhost:8080/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
			    			  + "</p></h1>" + "<a href=\"https://web7.jhuep.com:443/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
			    			
						} else if (estimatedCost < 0 && validDuration.equals("valid") && validParty.contentEquals("valid")) {
							//display invalid date page
							return "<html> " + "<title>" + "Invalid Data"
							+ "</title>" + "<body><p> Error: " + dateError
							//+ "</p></h1>" + "<a href=\"http://localhost:8080/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
							  + "</p></h1>" + "<a href=\"https://web7.jhuep.com:443/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
					
						} else if (estimatedCost < 0 && validDuration.equals("valid") && validParty.contentEquals("invalid")) {
							//display invalid party size
							return "<html> " + "<title>" + "Invalid Data"
							+ "</title>" + "<body><p> Error: " + partyError
							//+ "</p></h1>" + "<a href=\"http://localhost:8080/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
							  + "</p></h1>" + "<a href=\"https://web7.jhuep.com:443/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
						
						} else if (estimatedCost > 0 && validDuration.equals("valid") && validParty.contentEquals("invalid")) {
							//display invalid party size
							return "<html> " + "<title>" + "Invalid Data"
							+ "</title>" + "<body><p> Error: " + partyError
							//+ "</p></h1>" + "<a href=\"http://localhost:8080/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
							  + "</p></h1>" + "<a href=\"https://web7.jhuep.com:443/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
							
						} else {
							//display other invalid data
							return "<html> " + "<title>" + "Invalid Data"
							+ "</title>" + "<body><p> Error: " + errorMessage
							//+ "</p></h1>" + "<a href=\"http://localhost:8080/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
							  + "</p></h1>" + "<a href=\"https://web7.jhuep.com:443/Parikh_Week12Assignment12\">Get Another Estimate</a>" + "</html> ";
						}
				
	}finally { 
		
    }

	}
	
	//function to set rate as per selected tour
	 public static Rates checkRate(int hike) { 
		 Rates tourRate; 
		 	if (hike == 0) { 
		 		tourRate = new Rates(Rates.HIKE.GARDINER); 
		 	} else if (hike == 1) { 
		 		tourRate = new Rates(Rates.HIKE.HELLROARING); 
		 	} else if (hike == 2) { 
		 		tourRate = new Rates(Rates.HIKE.BEATEN); 
		 	} else { 
		 		tourRate = null; 
		 	} return tourRate; 
		 }
	
	//testing ReST Service
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/test")
	public String getText() {
		return "SUCCESSFUL OUTPUT FROM SERVICE";
	}
	
	//testing ReST Service
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/params/{id}")
	public String getParamText(@PathParam("id") String id, @QueryParam("zip") int zipCode) {
		return "The value of the path parameter is " + id + " with a zip code of " + zipCode;
	}

}
