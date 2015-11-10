/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Daniel
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
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
    	final int MAX_NUMBER_USERS = 20;
    	int nextId = 0;
 
    	nextId = readNextId(nextIdPath);
    	nextId++;
    	saveNextId(nextIdPath,nextId);
    	
    	System.out.println("nextId is: " + nextId);
    	//array list to hold all fitness user accounts for the application. initial capacity of 10
        List<FitnessUser> fitnessUserList = new ArrayList<FitnessUser>(MAX_NUMBER_USERS);
             
        
    }
    
    public static void loadFitnessUserPersonalInfo(){
    	
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
			JOptionPane.showMessageDialog(null, "Sorry a fatal error has ocurred contact support.");
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
