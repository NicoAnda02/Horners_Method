package mainPK;

import java.io.*;
import java.util.*;

public class Main {
	
	/** Nicolas Andaluz CS 384 Spring Semester 2024
	 * 
	 * The purpose of this project is to become more acquainted with using
	 * Horner's method and Newton's method to calculate the solution to
	 * polynomials.
	 * 
	 */
	public static void main(String[] args) throws IOException 
	{
		
		ArrayList <Expression> polynomial = new ArrayList <Expression>();
		float initPoint = 0;
		float actualValue = 0;
		float answer = 0;
		//count represents number of coefficients exponents
		int count = 0;
		//false if next digit is coefficient, true if it is exponent
		boolean tf = false;
		
		
		
		
		System.out.println("Format: 2 4 -3 2 3 1 -4 0 -2"
				+ "\nResult: 2x^4 - 3x^2 + 3x -4  x0 = -2"
				+ "\nInput your polynomial that follows format: ");
		
		BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
		String[] input;
		
		input = bi.readLine().split(" ");
		
		if ((input.length % 2) == 0)
		{
			//handle input error exception
			System.out.println("Input error 1");
			System.exit(0);
		}	
		
		//read input and store into temporary array
		for (int j = 0; j < input.length; j++)
		{
			if (j == (input.length - 1)) //final entry is initial point
			{
				initPoint = Float.parseFloat(input[j]);
//				System.out.println("Initial Point: " + initPoint + "\n");
			}
			
			else if (tf == false) //even index inputs are the coefficients
			{
				if (Float.parseFloat(input[j]) == 0) //skip coefficients equaling 0
				{
					j++;
					continue;
				}
				polynomial.add(new Expression());
				polynomial.get(count).setCoefficient(Float.parseFloat(input[j]));
				tf = true;
//				System.out.println("Coefficient: " + polynomial.get(count).getCoefficient());
			}
			
			else if (tf == true) //odd index inputs are the exponents
			{
				polynomial.get(count).setExponent(Integer.parseInt(input[j]));
				tf = false;
//				System.out.println("Exponent: " + polynomial.get(count).getExponent() + "\n");
				count++;
			}
		}
		
		//now print out the resulting polynomial from the digits in the array
		
		ArrayList <Expression> temp = new ArrayList <Expression>(); //newly sorted array list
		
		for (int i = 0; i < count; i++) //copy from polynomial to temporary in order to sort
		{
			
			temp.add(new Expression(polynomial.get(i)));
			
			for (int k = 0; k < temp.size(); k++) 
			{
				
				if (temp.get(i).compare(temp.get(k)) == 0) //if newly added exponent is larger, insert
				{
					Collections.swap(temp, i, k);
					k--;

				}
				
			}
			
		}
		
		for (int z = 0; z < temp.size(); z++) //display polynomial
		{
			
			System.out.print(temp.get(z).display() + " ");
			try
			{
				if (temp.get(z + 1) != null)
				{
					if (temp.get(z + 1).getCoefficient() >= 0)
						System.out.print("+ ");
				}
				
			} catch (IndexOutOfBoundsException e)
			{
					System.out.print("x0 = " + initPoint + "\n");;
			}
			
		}
		
		System.out.println("\n//////////////////Calculations///////////////////////////////\n");
		
		//iterate up to ten times until relative error is less than 10^-4
		for (int k = 0; k < 10; k++) 
		{
			answer = horner(temp, initPoint);
			
			System.out.println("\nIteration " + (k + 1) + ": " + answer
					+ "\n\n//////////////////////////////////////////////////////////////\n");
			
			if(Math.abs(initPoint - answer) < Math.pow(10, -4))
				break;
			
			initPoint = answer;
		}
		System.exit(0);

	}
	
	static float horner(ArrayList <Expression> function, float initPoint) 
	{
		ArrayList <Expression> temp = new ArrayList <Expression>();
		ArrayList <Expression> temp2 = new ArrayList <Expression>();
		
		//highest exponent of next iteration
		int newExp = function.get(0).getExponent() - 1;
		float result = 0, result2 = 0, finalResult = 0;
		
		for(int i = 0; i <= function.get(0).getExponent(); i++)
		{
			//base case i = 0
			if (i == 0)
			{
				result = function.get(i).getCoefficient();
				temp.add(new Expression(result, newExp));
				newExp--;
			}
			//check if next exponent has a coefficient
			else
			{
				//condition where coefficient != 0
				if (function.get(i).getExponent() == newExp + 1)
				{
					result = function.get(i).getCoefficient() + (result * initPoint);
					//final result, break the loop
					if (newExp < 0) 
					{
						break;
					}
					temp.add(new Expression(result, newExp));
					newExp--;
				}
				//condition where coefficient == 0
				else if (function.get(i).getExponent() != newExp + 1)
				{
					result = (result * initPoint);
					temp.add(new Expression(result, newExp));
					newExp--;
					i--;
				}
			}
			
		}
		
		//Print out the results of Horner's method
		System.out.println("\nHorner Result 1:\n");
		
		for (int z = 0; z < temp.size(); z++) //display polynomial
		{
			
			System.out.print(temp.get(z).display() + " ");
			try
			{
				if (temp.get(z + 1) != null)
				{
					if (temp.get(z + 1).getCoefficient() >= 0)
						System.out.print("+ ");
				}
				
			} catch (IndexOutOfBoundsException e)
			{
					System.out.print("P(" + initPoint + ") = " + result + "\n");;
			}
			
		}
		
		/////////////////////////////////////////////////////////////////////////////////////
		//////////////////            Iteration 2             ///////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////
		
		newExp = temp.get(0).getExponent() - 1;
		for(int i = 0; i <= temp.get(0).getExponent(); i++)
		{
			//base case i = 0
			if (i == 0)
			{
				result2 = temp.get(i).getCoefficient();
				temp2.add(new Expression(result2, newExp));
				newExp--;
			}
			//check if next exponent has a coefficient
			else
			{
				//condition where coefficient != 0
				if (temp.get(i).getExponent() == newExp + 1)
				{
					result2 = temp.get(i).getCoefficient() + (result2 * initPoint);
					//final result, break the loop
					if (newExp < 0) 
					{
						break;
					}
					temp2.add(new Expression(result2, newExp));
					newExp--;
				}
				//condition where coefficient == 0
				else if (temp.get(i).getExponent() != newExp + 1)
				{
					result2 = (result2 * initPoint);
					temp2.add(new Expression(result2, newExp));
					newExp--;
					i--;
				}
			}
			
		}
		
		//Print out the results of 2nd Horner's method
		System.out.println("\nHorner Result 2:\n");
		
		for (int z = 0; z < temp2.size(); z++) //display polynomial
		{
			
			System.out.print(temp2.get(z).display() + " ");
			try
			{
				if (temp2.get(z + 1) != null)
				{
					if (temp2.get(z + 1).getCoefficient() >= 0)
						System.out.print("+ ");
				}
				
			} catch (IndexOutOfBoundsException e)
			{
					System.out.print("P'(" + initPoint + ") = " + result2 + "\n");;
			}
			
		}
		
		finalResult = initPoint - (result/result2);
		
		return finalResult;
	}
	
	static float error(float measured, float real) //relative error calculation
	{
		float result = (Math.abs(measured - real) / real);
		return result;
	}
	
	static float actual(ArrayList <Expression> function, float initPoint)
	{
		float answer = 0;
		
		for (int i = 0; i < function.size(); i++)
		{
			answer = (float) (answer + (function.get(i).getCoefficient() 
					* Math.pow(initPoint, function.get(i).getExponent())));
		}
		
		return answer;
	}

}
