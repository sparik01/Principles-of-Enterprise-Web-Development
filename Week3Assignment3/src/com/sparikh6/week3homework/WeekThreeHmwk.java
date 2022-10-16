package com.sparikh6.week3homework;

import java.util.Scanner;
import java.lang.Math;

/* Sheetal Parikh
 * Week 3 Homework Assignment
 * Main Method
 */

public class WeekThreeHmwk {

	public static void main(String[] args) {
		
		int num1;
		int num2;
		int product;
		
		Scanner sc = new Scanner(System.in);
		
		//prompting the user to enter the first number
		System.out.print("Please enter the first number: ");
		num1 = sc.nextInt();
		
		//prompting the user to enter the second number
		System.out.print("Please enter the second number: ");
		num2 = sc.nextInt();
		
		//closing scanner class
		sc.close();
		
		//calling the method calcProductTwoInt to calculate product
		product = Product.calcProductTwoInt(num1, num2);
		
		//creating space for the final product output
		System.out.println("");
		
		//checking if product is negative
		if (product < 0) {
			
			//displaying negative product with parentheses
			System.out.println("The product of the two entered numbers equals: " + "(" + Math.abs(product) + ")");
			
		} else {
			
			//displaying positive product 
			System.out.println("The product of the two entered numbers equals: " + product);
			
		}
		

	}

}
