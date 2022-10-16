package com.sparikh6.week4homework;

/*Sheetal Parikh
 * Week 4 Assignment
 * P3 Class
 */

public class P3 extends Aircraft {

	private int numberEngines;

	public P3(int length, int speed, String name, String type, int altitude, int numberEngines) {
		setLength(length);
		setSpeed(speed);
		setName(name);
		setType(type);
		setAltitude(altitude);
		setNumberEngines(numberEngines);
		
	}

	public int getNumberEngines() {
		return numberEngines;
	}

	//exception if number of engines is negative than converted to positive value
	public void setNumberEngines(int numberEngines){
		if (numberEngines < 0) {
			System.out.println("The number of engines cannot be a negative value: " + numberEngines);
			System.out.println("It number of engines is now: " + Math.abs(numberEngines));
			this.numberEngines = Math.abs(numberEngines);
		} else {
			this.numberEngines = numberEngines;
		}
	}
	//returning string implementation of output
	@Override
	public String toString(){
		System.out.print("[Aircraft-P3] ");
		return "   Name: " + getName() + " | Type: " + getType() + " | Speed: " + getSpeed() + " km/h" +
				" | Length: " + getLength() + " m" + " | Altitude: " + getAltitude() + " km" + " | No.Engines: " + getNumberEngines();
	}
	
	
}