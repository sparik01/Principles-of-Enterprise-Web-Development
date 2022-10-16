package com.sparikh6.week9homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread extends Thread {

    private final Socket socket;
    public ClientThread(Socket clientSocket) {
        this.socket = clientSocket;
    }
    
    //set the rate depending on the selected duration and tour
	 public static Rates checkRate(String inputString) { 
		 String[] inputArray = inputString.split(":"); 
		 int tourLength = inputArray[0].length();
		 int tour = Integer.parseInt(inputArray[0]); 
		 Rates tourRate; 
		 	if (tourLength == 1 && tour == 0) { 
		 		tourRate = new Rates(Rates.HIKE.GARDINER); 
		 	} else if (tourLength == 1 && tour == 1) { 
		 		tourRate = new Rates(Rates.HIKE.HELLROARING); 
		 	} else if (tourLength == 1 && tour == 2) { 
		 		tourRate = new Rates(Rates.HIKE.BEATEN); 
		 	} else { 
		 		tourRate = null; 
		 	} return tourRate; 
		 }
    
    public void run () {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputString = null;
    		Rates rate;
            inputString = in.readLine();
            
            //checking that input string contains :
            if (inputString.contains(":")) {
            	String[] inputArray = inputString.split(":");
            	
            	//checking that none of the inputs are missing
            	if(inputArray.length==5) {
            		
            	try {
            		rate = checkRate(inputString);
     
            		int hike = Integer.parseInt(inputArray[0]);
                	int begYear = Integer.parseInt(inputArray[1]);
                	int begMonth = Integer.parseInt(inputArray[2]);
                	int begDay = Integer.parseInt(inputArray[3]);
            		int duration = Integer.parseInt(inputArray[4]);
            		
                	//checking that correct input stream used for hike
                	if (hike==1 || hike==2 || hike ==0) {
                			
                		//checking that correct input stream used for duration
                		if(duration==2 || duration == 3 || duration == 4 || duration == 5 || duration ==7) {
                			//setting the first day of the booking
                			BookingDay startBooking = new BookingDay(begYear, begMonth, begDay);
                				// checking if the Start day of the booking is valid
                					if (startBooking.isValidDate()) {
                						//setting start date of the booking
                							rate.setBeginDate(startBooking);
                								//setting the rate of the reservation
                								if(rate.setDuration(duration)) {
                									//checking if reservation if valid
                									if(rate.isValidDates()) {
                										//displaying the total cost to the label
                										out.println(rate.getCost() + ":" +"Quoted Rate");
                											
                									}else{
                										out.println("-0.01:Please select date within the current season.");
                									}
            				
                								} else {
                									out.println("-0.01:Please select a duration or tour.");
                								}
				
                					}else {
                						out.println("-0.01:Please select date within the current season.");
                					}
            			
                		} else {
                			out.println("-0.01:Invalid duration input. Check input string.");
                		}
            	
                    } else {
                    	out.println("-0.01:Invalid hike_id input. Check input string.");
                    	}

            	}catch (NumberFormatException nfe) {
            		out.println("-0.01:Invalid type of input. Check input string.");
            	}
            	
            	} else {
            		out.println("-0.01:Invalid Number of Arguments for input.");
            	}
            	
            } else {
            	out.println("-0.01:Input does not contain ':' ");
            }

        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

