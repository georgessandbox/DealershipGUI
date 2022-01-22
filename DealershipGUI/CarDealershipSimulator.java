// Name : George Saade
// Student ID : 500867644

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class CarDealershipSimulator  
{
  public static void main(String[] args) throws Exception
  {	  
      	  // Create a CarDealership object
	  CarDealership dealer = new CarDealership();
	   AccountingSystem sys = dealer.sys;
          
	  // Then create an (initially empty) array list of type Car
      // Then create some new car objects of different types
	  // See the cars file for car object details
	  // Add the car objects to the array list
      // The ADD command should hand this array list to CarDealership object via the addCars() method	
	  /**
	   * Create an array list of car type and add car objects to it.
	   */
	  ArrayList<Car> kar = new ArrayList<Car>();
	  
	  //I/O reader that reads from cars.txt file and adds cars automatically to the inventory arraylist.
	  try{
			File file = new File("cars.txt");
			Scanner in = new Scanner(file);
			//while the reader has next value reads it and assign it to its appropriate variable.
			while(in.hasNext()){
				String Mfr = in.next();
				String Color = in.next();
				String Model = in.next();
				String Power = in.next();
				double SafetyRating = in.nextDouble();
				int MaxRange = in.nextInt();
				String AWD = in.next();
				boolean tAWD = false;
				if(AWD.equals("AWD")){
					tAWD = true;
				} else{tAWD = false;}
				
				Double Price = in.nextDouble();
				if(Power.equals("ELECTRIC_MOTOR")){
				int RechargeTime = in.nextInt();
				Car Car = new ElectricCar(Mfr, Color, Model, Power, SafetyRating, MaxRange, tAWD, Price, RechargeTime);
				kar.add(Car);
			}
				else{
					Car Car = new Car(Mfr, Color, Model, Power, SafetyRating,MaxRange, tAWD, Price);
					kar.add(Car);
				}
			}
			in.close();
	}
	  catch(FileNotFoundException e){
			e.printStackTrace();
	}
	  
	  // Create a scanner object
 	  Scanner input = new Scanner(System.in);
	  // while the scanner has another line
	  //    read the input line
	  //    create another scanner object (call it "commandLine" or something) using the input line instead of System.in
	  //    read the next word from the commandLine scanner 
      //	check if the word (i.e. string) is equal to one of the commands and if so, call the appropriate method via the CarDealership object  


         // Takes input from user and calls the appropriate methods to execute.
 	     // saves last bought car into a variable in case returned.
 	  while(input.hasNextLine()){
 		 //takes next command and split it so its readable
 		 String txt = input.nextLine();
		 String[] tokens = txt.split("[ ]+");
    	 //display all cars
		 if (tokens[0].equals("L")) {
			 dealer.displayInventory();
 			}
		 //Check if index is in range and buy appropriate car.
		 //throws error if index incorrect
		 //updates last bought car variable.
		 if (tokens[0].equals("BUY")) {
		      try {
		         int s = Integer.parseInt(tokens[1]);
		           System.out.println(s);

		         System.out.println(dealer.buyCar(s));
		          } catch (Exception e)
		          {
		           System.out.println("not valid entry");
		          }
		 }
		//Add the cars to the inventory.
		if (tokens[0].equals("ADD")) {
			dealer.addCars(kar);
		}
		//Exit the program.
		if (tokens[0].equals("Q")) {
			System.out.print("Bye Bye!");
			break;
			}
		//Returns last bought car.
		
		if (tokens[0].equals("RET")) {
			if(!sys.history.isEmpty()) {
			         dealer.returnCar(sys.history.get(sys.history.size()-1).id);
			}
			else System.out.println("no cars bought yet");
		}
		
		//Sort by price.
		if (tokens[0].equals("SPR")) {
			dealer.sortByPrice();
			}
		//Sort by sort By Safety Rating
		if (tokens[0].equals("SSR")) {
			dealer.sortBySafetyRating();
			}
		// sort by max range
		if (tokens[0].equals("SMR")) {
			dealer.sortByMaxRange();
		}
		//filter by price. parse input into readable type. catches any invalid entry. 
		if (tokens[0].equals("FPR")) {
			if (tokens.length > 1) {
			      try {
				double minPrice = (double)  Integer.parseInt(tokens[1]);
				double maxPrice = (double) Integer.parseInt(tokens[2]);
			    dealer.filterByPrice(minPrice, maxPrice);
			          } catch (NumberFormatException e)
			      {
				   System.out.println("not valid entry");
			      }
			}
		}
		// filter By Electric
		if (tokens[0].equals("FEL")) {
			dealer.filterByElectric();
		}
		//filter By All wheels
		if (tokens[0].equals("FAW")) {
			dealer.filterByAWD();
		}
        //Clears filters.
		if (tokens[0].equals("FCL")) {
			dealer.FiltersClear();
		}
		
		//takes sales command and see what the user wants by looking at next command
		if (tokens[0].equals("SALES")) {
			if (tokens.length == 1) {
				for (Transaction x : sys.history) 
					System.out.println(x.display());
				}
			else if (tokens.length > 1) {
				if (tokens[1].equals("TOPSP")) {
					
					  Map<String, Integer> map = new HashMap<String, Integer>();
					   for (Transaction x : sys.history) {
						   if (x.sellType.equals("BUY")) {
						   map.put(x.seller, map.get(x.seller) == null ? 1 : map.get(x.seller) + 1 );
						   }
						   
					   }}
					  
				//shows the stats buy looking through the transactions history 
				else if (tokens[1].equals("STATS")) {
					
					 	 double total = 0;     
					 	 int totalsold = 0;
					 	 int totalreturn = 0 ;
			             for (Transaction x : sys.history) {
			            	 if (x.sellType.equals("BUY"))
			            		 total += x.price;
			            	 else  {total -= x.price;}
			            	 if(x.sellType.equals("BUY")) {
			            		 totalsold++;
			            	 }
			            	 if(x.sellType.equals("RET")) {
			            		 totalreturn++;
			            	 }
			             }
			               
							Map<Integer, Integer> map = new HashMap<Integer, Integer>();
							   for (Transaction x : sys.history) {
								   if (x.sellType.equals("BUY")) {
								   map.put(x.date.get(2), map.get(x.date.get(2)) == null ? 1 : map.get(x.date.get(2)) + 1 );
								   }
							   }
									
			             System.out.println("Totalvalue: " + total + " Averagevalue: " + String.format("%.2f", total/12) + " Totalsold:"  + totalsold + " Returned: " + totalreturn
			            		 ) ;

			             }
				
				//prints out sales team
				else if (tokens[1].equals("TEAM")) {
					SalesTeam team = new SalesTeam();
					for (String x : team.listt)
						System.out.print(x + " : ");
					}
				//takes care of displaying the sales of required month (SALES m)
				else {
					try {
						int s = Integer.parseInt(tokens[1]);
						for (Transaction x : sys.history) {
							if(  x.date.get(2) == s)
								System.out.println(x.display());
						}
					} catch (NumberFormatException e)
					{
						System.out.println("not valid entry");
					}}}
		}}}
				
}
			
		
		
 	  
  
 	 
