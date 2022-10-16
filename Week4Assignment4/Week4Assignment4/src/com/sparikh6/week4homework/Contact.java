package com.sparikh6.week4homework;

/* Sheetal Parikh
 * Week 4 Assignment
 * Creating interface Contact
 * 
 */

public interface Contact {
	
	// creating methods as specified by Step 1
	public int getLength();
	public void setLength(int length);
	
	public int getSpeed();
	public void setSpeed(int speed); 
	
    public void setSpeed(String speed); 
	
	public String getName();
	public void setName(String name);
	
	public String getType();
	public void setType(String type);
	
}
