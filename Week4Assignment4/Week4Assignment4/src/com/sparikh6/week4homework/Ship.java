package com.sparikh6.week4homework;

/*Sheetal Parikh
 * Week 4 Assignment
 * Ship Class
 */

public abstract class Ship implements Contact {
	private int length;
	private int speed;
	private String name;
	private String type;
	
	//overriding methods defined in Contact to allow Ship to provide implementation 
	@Override
	public int getLength() {
		return length;
	}
	
	// exception if length is a negative value than converted to positive value
	@Override
	public void setLength(int length) {
		if (length < 0) {
			System.out.println("The length cannot be a negative value: " + length);
			System.out.println("The length is now: " + Math.abs(length));
			this.length = Math.abs(length);
		} else {
			this.length = length;
		}
	}
	
	
	@Override
	public int getSpeed() {
		return speed;
	}
	
	// exception if speed is a negative value than converted to positive value
	@Override
	public void setSpeed(int speed){
		if (speed < 0) {
			System.out.println("The speed cannot be a negative value: " + speed);
			System.out.println("The speed is now: " + Math.abs(speed));
			this.speed = Math.abs(speed);
		} else {
			this.speed = speed;
		}
	}
	
	//exception that if string in improper format to be converted to in than replaced with a value of 30
	@Override
    public void setSpeed(String speed){
	  	try {
    		//convert string to int
    		int zz = Integer.parseInt(speed);
    		setSpeed(Integer.parseInt(speed));
    		System.out.println("Parsing the string for speed " + "\"" + speed + "\", it's value is " + zz);
    	} catch (NumberFormatException nfe) {
    		System.err.println("Unable to parse the String: " + speed);
    		System.err.println("Speed will be set to " + 30);
    		setSpeed(30);
    	}
    	System.out.println();
    }
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}

}
