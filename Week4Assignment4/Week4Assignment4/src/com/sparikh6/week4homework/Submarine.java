package com.sparikh6.week4homework;

/*Sheetal Parikh
 * Week 4 Assignment
 * Submarine Class
 */

public class Submarine extends Ship {
	
	private int numberTorpedos;

	public Submarine(int length, int speed, String name, String type, int numberTorpedos){
		setLength(length);
		setSpeed(speed);
		setName(name);
		setType(type);
		setNumberTorpedos(numberTorpedos);
		
	}

	public int getNumberTorpedos() {
		return numberTorpedos;
	}

	//exception if the number of torpedos is negative converted to positive value
	public void setNumberTorpedos(int numberTorpedos){
		if (numberTorpedos < 0) {
			System.out.println("The number of torpedos cannot be a negative value: " + numberTorpedos);
			System.out.println("The number of torpedos is now: " + Math.abs(numberTorpedos));
			this.numberTorpedos = Math.abs(numberTorpedos);
		} else {
			this.numberTorpedos = numberTorpedos;
		}
	}
	//exception that if string in improper format to be converted to in than replaced with a value of 2
	public void setNumberTorpedos(String numberTorpedos){
		try {
			//convert string to int 
			int jj = Integer.parseInt(numberTorpedos);
			setNumberTorpedos(Integer.parseInt(numberTorpedos));
    		System.out.println("Parsing the string for number of torpedos " + "\"" + numberTorpedos + "\", it's value is " + jj);
	   	} catch (NumberFormatException nfe) {
    		System.err.println("Unable to parse the String: " + numberTorpedos);
    		// the number of torpedos is set to 2 if a string is used for number of torpedos
    		System.err.println("The number of torpedos will be set to 2");
    		setNumberTorpedos(2);
	   	}
		System.out.println();
	}
	
	//returning string implementation of output
	@Override
	public String toString(){
		System.out.print("[Ship-Submarine] ");
		return "Name: " + getName() + " | Type: " + getType() + " | Speed: " + getSpeed() + " kn" +
				" | Length: " + getLength() + " m" + " | No.Torpedos: " + getNumberTorpedos();
	}
	
	
}
