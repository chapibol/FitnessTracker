/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Daniel de Souza, Luis Velasco
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class MyFitnessTrackerApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
    	String nextIdPath = "./src/nextID.txt";
    	String fitnessUsersPath = "./src/fitnessUsers.txt";
    	String weightsPath = ".src/weights.txt";
    	final int MAX_NUMBER_USERS = 50;
    	int nextId = readNextId(nextIdPath);
    	//array list to hold all fitness user accounts for the application. initial capacity of 10
        List<FitnessUser> fitnessUserList = new ArrayList<FitnessUser>(MAX_NUMBER_USERS);
        //load users fitness user list from file if any.
        loadFitnessUserInfo(fitnessUserList,fitnessUsersPath);
        int firstMenuOption = 0;
        //display login screen
        do{
        	firstMenuOption = getFirstMenuOption();
        }while(firstMenuOption != 3);
        
        
        
        
        
        
        
//        for(FitnessUser fit: fitnessUserList){
//        	System.out.println("Userid: " + fit.getUserId() + " Name: " + fit.getName() + "\n" +
//        					"username: " + fit.getUsername() + " gender: " + fit.getGender() + "\n"
//        					+ "age: " + fit.getAge() + " height: " + fit.getHeight() + " currentWeight: " + fit.getCurrentWeight() +
//        					" target Weight: " + fit.getTargetWeight());
//        	System.out.println("--------------------------------------------");
//        }
             
        
    } 
    
    public static void loadFitnessUserInfo(List<FitnessUser> userList, String userDataPath){
    	try {
			BufferedReader buff = new BufferedReader(new FileReader(new File(userDataPath)));
			Scanner scan = null;
			String line = "";
			int userId = 0;
			String name = "";
			String username = "";
			String gender = "";
			int age = 0;
			int height = 0;
			double currentWeight = 0;
			double targetWeight = 0;
			//read lines
			while((line = buff.readLine()) !=null){
				scan = new Scanner(line);
				scan.useDelimiter(",");
				userId = Integer.parseInt(scan.next().trim());
				name = scan.next();
				username = scan.next();
				gender = scan.next();
				age = Integer.parseInt(scan.next().trim());
				height = Integer.parseInt(scan.next().trim());;
				currentWeight = Double.parseDouble(scan.next().trim());
				targetWeight = Double.parseDouble(scan.next().trim());
				//create and populate a FitnessUser object for the current line
				FitnessUser aUser = new FitnessUser(name, username, gender);
				aUser.setUserId(userId);
				aUser.setAge(age);
				aUser.setHeight(height);
				aUser.setCurrentWeight(currentWeight);
				aUser.setTargetWeight(targetWeight);
				
				//add user to user list
				userList.add(aUser);				
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File could not be found.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO Exception contact suppport.");
		} catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Internal error, Id, height, weight error");
		} catch (InvalidInputException e) {
			JOptionPane.showMessageDialog(null, "Internal error userid out of range");
		}
    }
    
    
    public static int getFirstMenuOption(){
		int userChoice = 0;
		do{
			try{
				userChoice = displayFirstMenu();
				if (userChoice < 1 || userChoice > 3){
					JOptionPane.showMessageDialog(null, "Invalid choice! Please enter a valid menu option.","My Fitness Tracker - JavaBeaners",JOptionPane.ERROR_MESSAGE);
				}
			}catch(NumberFormatException e){
				//reset userChoice to 0 so that while condition can be checked again properly
				userChoice = 0;
				JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid menu option.","My Fitness Tracker - JavaBeaners",JOptionPane.ERROR_MESSAGE);
			}			
		}while(userChoice < 1 || userChoice > 3);
		return userChoice;		
	}
    
    public static int displayFirstMenu() throws NumberFormatException{
		String menu  = "My Fitness Tracker \n"
				+ "(1) Login (existing users)\n"
				+ "(2) Create new account\n"
				+ "(3) Exit Application";
		int userChoice = Integer.parseInt(JOptionPane.showInputDialog(null,menu,"My Fitness Tracker - JavaBeaners", JOptionPane.INFORMATION_MESSAGE));
		
		return userChoice;
	}
    /**
     * Application method that reads in the next available id for use.
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public static int readNextId(String path){
    	int id = -1;
    	try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			id = Integer.parseInt(reader.readLine());
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Sorry an internal error has ocurred contact support.");
			System.exit(0);
		}catch(NumberFormatException x){
			x.printStackTrace();
		} catch (IOException c) {
			c.printStackTrace();
		}
    	return id;
    }
    /**
     * Saves the nextid value to a text file to be used by application when needed.
     * @param path
     * @param idToSave
     */
    public static void saveNextId(String path, int idToSave){
    	PrintWriter out = null;
		try {
			out = new PrintWriter(new FileOutputStream(new File(path)));
			out.println(""+idToSave);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Internal error, please contact support");
		}finally{
			out.close();
		}    	
    }
    
}
