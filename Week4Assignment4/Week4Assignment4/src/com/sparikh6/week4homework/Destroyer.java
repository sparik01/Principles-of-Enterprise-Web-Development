package com.sparikh6.week4homework;

/*Sheetal Parikh
 * Week 4 Assignment
 * Destroyer Class
 */

public class Destroyer extends Ship {
	
	private int numberMissiles;
	
	public Destroyer(int length, int speed, String name, String type, int numberMissile) {
		setLength(length);
		setSpeed(speed);
		setName(name);
		setType(type);
		setNumberMissiles(numberMissile);
		
	}

	public int getNumberMissiles() {
		return numberMissiles;
	}

    //exception if the number of missiles is negative then convert to positive value
	public void setNumberMissiles(int numberMissiles) {
		if (numberMissiles < 0) {
			System.out.println("The number of missiles cannot be a negative value: " + numberMissiles);
			System.out.println("It number of missiles is now: " + Math.abs(numberMissiles));
			this.numberMissiles = Math.abs(numberMissiles);
		} else {
			this.numberMissiles = numberMissiles;
		}
	}
	
	//exception that if string in improper format to be converted to in than replaced with a value of 2
	public void setNumberMissiles(String numberMissiles) {
		try {
			// convert string to int
			int xx = Integer.parseInt(numberMissiles);
			setNumberMissiles(Integer.parseInt(numberMissiles));
    		System.out.println("Parsing the string for number of missiles " + "\"" + numberMissiles + "\", it's value is " + xx);
	   	} catch (NumberFormatException nfe) {
    		System.err.println("Unable to parse the String: " + numberMissiles);
    		// the number of missiles is set to 2 if a string is used for number of missiles
    		System.err.println("Number of missiles will be set to 2");
    		setNumberMissiles(2);
	   	}
		System.out.println();
	}
	
	//returning string implementation of output
	@Override
	public String toString(){
		System.out.print("[Ship-Destroyer] ");
		return "Name: " + getName() + " | Type: " + getType() + " | Speed: " + getSpeed() + " kn" +
				" | Length: " + getLength() + " m" + " | No.Missiles: " + getNumberMissiles();
	}
}
