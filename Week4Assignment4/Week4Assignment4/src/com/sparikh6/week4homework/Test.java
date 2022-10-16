package com.sparikh6.week4homework;

import java.util.ArrayList;

/*Sheetal Parikh
 * Week 4 Assignment
 * Test Class and printing output
 */

public class Test {

	public static void main(String[] args) {
		// creating 2 Destroyers
		Destroyer destroyer1 = new Destroyer(160,30,"Gearing","Gyatt",90);
		Destroyer destroyer2 = new Destroyer(140,40,"Ticonderoga","Yorktown",75);
		//checking if exception thrown if negative input for speed, length, and missiles 
		//Destroyer destroyer2 = new Destroyer(-140,40,"Ticonderoga","Yorktown",75);
		//Destroyer destroyer2 = new Destroyer(140,-40,"Ticonderoga","Yorktown",75);
		//Destroyer destroyer2 = new Destroyer(140,40,"Ticonderoga","Yorktown",-75);
		
		//setting the number of missiles to string values
		//destroyer1.setNumberMissiles("Foo");
		//destroyer1.setNumberMissiles("5");
		
		//setting the speed to string values
		//destroyer1.setSpeed("Five");
		//destroyer1.setSpeed("5");
		
		// creating 2 Submarines
		Submarine submarine1 = new Submarine(145,33,"Alligator","Attack",25);
		Submarine submarine2 = new Submarine(165,45,"Nemo","Cruise Missile",50);
		//checking if exception thrown if negative input for speed, length, and torpedos
		//Submarine submarine2 = new Submarine(-165,45,"Nemo","Cruise Missile",50);
		//Submarine submarine2 = new Submarine(165,-45,"Nemo","Cruise Missile",50);
		//Submarine submarine2 = new Submarine(165,45,"Nemo","Cruise Missile",-50);
		
		//setting the number of torpedoes to string values
		submarine1.setNumberTorpedos("Foo");
		//submarine1.setNumberTorpedos("5");
		
		//setting the speed to string values
		//submarine1.setSpeed("Five");
		//submarine1.setSpeed("5");
		
		
		// creating 2 P3s
		P3 p31 = new P3(20,650,"Fury","Fighter",18255,2);
		P3 p32 = new P3(15,383,"Predator","Drone",15240,2);
		//checking if exception thrown if negative input for speed, length, altitude, and engines
		//P3 p32 = new P3(-15,383,"Predator","Drone",15240,2);
		//P3 p32 = new P3(15,-383,"Predator","Drone",15240,2);
		//P3 p32 = new P3(15,383,"Predator","Drone",-1540,2);
		//P3 p32 = new P3(15,383,"Predator","Drone",15240,-2);
		
		//setting the speed to string values
		//p31.setSpeed("Five");
		//p31.setSpeed("5");
		
		// creating collection of Destroyers of type ArrayList
		ArrayList<Destroyer> destroyerCollection = new ArrayList<Destroyer>();
		destroyerCollection.add(destroyer1);
		destroyerCollection.add(destroyer2);
		
		// creating collection of Submarines of type ArrayList
		ArrayList<Submarine> submarineCollection = new ArrayList<Submarine>();
		submarineCollection.add(submarine1);
		submarineCollection.add(submarine2);
		
		// creating collection of all Ships of type ArrayList
		ArrayList<Ship> shipsCollection = new ArrayList<Ship>();
		shipsCollection.add(submarine1);
		shipsCollection.add(submarine2);
		shipsCollection.add(destroyer1);
		shipsCollection.add(destroyer2);
		
		// creating collection of all Contacts of type ArrayList
		ArrayList<Contact> contactsCollection = new ArrayList<Contact>();
		contactsCollection.add(destroyer1);
		contactsCollection.add(destroyer2);
		contactsCollection.add(submarine1);
		contactsCollection.add(submarine2);
		contactsCollection.add(p31);
		contactsCollection.add(p32);

		// printing all Contacts
		System.out.println();
		System.out.println("Collection of all Contacts:");
		contactsCollection.forEach(contact -> System.out.println(contact));
	}

}
