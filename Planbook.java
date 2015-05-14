// Author:		Greg Melillo
// Created:		05/05/15

/**
TO DO:
	- Add checkCourses() method
	- Add more course data to addCourse()
	- Profit???
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;


public class Planbook{
	public static void main(String[] args){
		int choice = 1;
		ArrayList<Course> myCrs = new ArrayList<Course>();
		populateList(myCrs); // gather information from the txt file and fill
							 // the ArrayList with it
							 
		System.out.println("\n********** Welcome to Alpha Planbook **********");
		while(choice != 0){
			printMenu(); // show user available features
			choice = handleChoice(myCrs); // and handles their choice
		}
		writeData(myCrs); // write any changes made to the ArrayList to file
		System.out.println("\n***********************************************");
	}
	
	// retrieves courses from txt file and add it to the ArrayList 
	public static void populateList(ArrayList<Course> array){
		File file = new File("courses.txt");
		
		try{
			FileReader reader = new FileReader(file);
			BufferedReader buf = new BufferedReader(reader);
			
			String line = buf.readLine(); // read the blank line
			while(line != null ){ // read courses until end-of-file
				String[][] assignments = new String[10][2];
				
				// read course name/time 
				line = buf.readLine(); // name
				String courseTitle = line;
				line = buf.readLine();	// time
				String courseTime = line;
				
				// read assignments
				for(int i = 0 ; ; i++){
					line = buf.readLine(); // name
					if(line != null){
						if(! line.equals("")){ 
							assignments[i][0] = line;
						}else{break;} // stop reading this course's assignments
					}else{break;} 	  // if blank line or null is read
					line = buf.readLine(); // due date
					if(line != null){
						if(! line.equals("")){
							assignments[i][1] = line;
						}else{break;}
					}else{break;}
				}	
				Course x = new Course(courseTitle, courseTime, assignments);
				array.add(x); // add the course instance to ArrayList
			}
			buf.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();	
		}catch(IOException e){
			e.printStackTrace();	
		}
	}
	
	public static void printMenu(){
		System.out.println("\nPlease enter a choice...");
		System.out.println("0 - Exit");
		System.out.println("1 - Check your assignments");
		System.out.println("2 - Add an assignment");
		System.out.println("3 - Delete an assignment");
		System.out.println("4 - Add a course");
		System.out.println("5 - Delete a course\n");
	}
	
	public static int handleChoice(ArrayList<Course> array){
		Scanner inData = new Scanner(System.in);
		boolean invalid = true;
		int choice = 0;
		
		// handles invalid choice from menu, prompts until input is within range
		while(invalid){
			System.out.print("-> "); 
			choice = inData.nextInt();
			switch(choice){ 
			case 0:
				invalid = false;
				break;
			case 1:
				invalid = false;
				checkAssignments(array);
				break;
			case 2:
				invalid = false;
				addAssignment(array);
				break;
			case 3: 
				invalid = false;
				delAssignment(array);
				break;
			case 4: 
				invalid = false;
				addCourse(array);
				break;
			case 5: 
				invalid = false;
				delCourse(array);
				break;
			default:
				invalid = true;
				System.out.println("Invalid selection, try again...");
			}
		}
		return choice;
	}
	
	public static void checkAssignments(ArrayList<Course> array){
		Scanner inData = new Scanner(System.in);
		String assg, dd;
		int i, j;
		
		System.out.println("Enter course title for an single course or " +
								"'all' for all assignments.");
		System.out.print("-> ");
		String courseTitle = inData.nextLine();
		
		if(courseTitle.equals("all")){
			// display assignments for all courses	
			for(i = 0; i < array.size(); i++){
				System.out.println(array.get(i).getName());
				for(j = 0; j < array.get(i).getAssignments().length; j++){
					assg = array.get(i).getAssignments(j, 0);
					dd = array.get(i).getAssignments(j, 1);
					if(assg != null && dd != null)
						System.out.println("\t" + (j+1) + ") " + 
						assg  + " due: " + dd);	
				}	
			}
		}else{
			for(i = 0; i < array.size(); i++){
				if(courseTitle.equals(array.get(i).getName())){
					// display assignments course with matching name
					for(j = 0; j < array.get(i).getAssignments().length; j++){
						assg = array.get(i).getAssignments(j, 0);
						dd = array.get(i).getAssignments(j, 1);
						if(assg != null && dd != null)
							System.out.println("\t" + (j+1) + ") " + 
							assg  + " due: " + dd);	
					}
				}
			}
		}
	}
	
	public static void addAssignment(ArrayList<Course> array){
		Scanner inData = new Scanner(System.in);
		
		System.out.println("\nEnter course title: ");
		System.out.print("-> ");
		String courseTitle = inData.nextLine();
		System.out.println("Enter assignment name: ");
		System.out.print("-> ");
		String assignName = inData.nextLine();
		System.out.println("Enter due date: ");
		System.out.print("-> ");
		String dueDate = inData.nextLine();
		
		for(int i = 0; i < array.size(); i++){
			if(array.get(i).getName().equals(courseTitle)){
				array.get(i).setAssignments(assignName, dueDate);
			}
		}
	}
	
	public static void delAssignment(ArrayList<Course> array){
		Scanner inData = new Scanner(System.in);
		
		System.out.println("Enter course title of assignment: ");
		System.out.print("-> ");
		String courseTitle = inData.nextLine();
		System.out.println("Enter assignment name to delete: ");
		System.out.print("-> ");
		String assignmentName = inData.nextLine();
		
		for(int i = 0; i < array.size(); i++){
			if(courseTitle.equals(array.get(i).getName())){
				for(int j = 0; j < array.get(i).getAssignments().length; j++){
					String match = array.get(i).getAssignments(j,0);
					if(assignmentName.equals(match)){
						// if the assignment isn't last in the array
						if(j != (array.get(i).getAssignments().length - 1)){
							// replace it with the next element 
							array.get(i).setAssignments(j, 
								array.get(i).getAssignments(j+1,0), 
								array.get(i).getAssignments(j+1,1));
							// and set the old space to null
							array.get(i).setAssignments(j+1, null, null);
						}else{
							array.get(i).setAssignments(j, null, null);	
						}
					}
				}
			}
		}	
	}
	

	public static void addCourse(ArrayList<Course> array){
		Scanner inData = new Scanner(System.in);
		
		// collect relevant course info
		System.out.println("\nEnter course title: ");
		System.out.print("-> ");
		String courseTitle = inData.nextLine();
		System.out.println("Enter meeting time(s): ");
		System.out.print("-> ");
		String meetingTime = inData.nextLine();
		
		// add it to the ArrayList
		Course x = new Course(courseTitle, meetingTime);
		array.add(x);
	}
	
	public static void delCourse(ArrayList<Course> array){
		Scanner inData = new Scanner(System.in);
		
		System.out.println("\nEnter course title to delete: ");
		System.out.print("-> ");
		String courseTitle = inData.nextLine();
		
		for(int i = 0; i < array.size(); i++){
			if(courseTitle.equals(array.get(i).getName())){
				System.out.println("Course " + "\"" + array.get(i).getName() + 
									 "\"" + " successfully removed");
				array.remove(i);
			}
		}
	}
	
	public static void writeData(ArrayList<Course> array){
		File file = new File("courses.txt");
		BufferedWriter writer = null;
		
		try{
			// Overwrite data and write the ArrayList
			// because the original List data is copied at the beginning
			writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < array.size(); i++){
				writer.write("\n");
				writer.write(array.get(i).getName() + "\n");
				writer.write(array.get(i).getTime() + "\n");
				
				for(int j = 0; j< array.get(i).getAssignments().length; j++){
					if(array.get(i).getAssignments(j,0) != null)
						writer.write(array.get(i).getAssignments(j, 0) + "\n");
					if(array.get(i).getAssignments(j,1) != null)
						writer.write(array.get(i).getAssignments(j, 1) + "\n");
				}					
			}
			writer.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}