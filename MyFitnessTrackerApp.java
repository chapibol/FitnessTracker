/*
 * MyFitnessTracker
 * 11/11/2015
 * 
 * 
 * This application was designed to help users keep track of weight loss
 * through counting calories of several activities.
 * 
 * The program keeps track of up to 50 different users through the use of
 * text files.
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

    
    public static void main(String[] args){
    	final int MAX_NUMBER_USERS = 50;
    	final String FITNESS_USER_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "fitnessUsers.txt";
    	final String NEXT_ID_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "nextID.txt";
    	//array list to hold all fitness user accounts for the application. initial capacity of 10
        List<FitnessUser> fitnessUserList = new ArrayList<FitnessUser>(MAX_NUMBER_USERS);
        initializeApplication(fitnessUserList);
        int nextId = readNextId(NEXT_ID_PATH);
        
        
        PullUp pu = new PullUp();
    	System.out.print(pu instanceof PullUp );
        int firstMenuOption = 0;
        //display login screen
        
//        Date d = new Date();
//        
//        System.out.println(d.getTime());
        
        
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
//    aFile.getParentFile().mkdirs(); 
//	aFile.createNewFile();
    /**
     * The purpose of this method is to load fitness users's data into application
     * @return
     */
    public static void initializeApplication(List<FitnessUser> list){
    	//default path locations for all files applications will need.
    	final String FITNESS_USER_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "fitnessUsers.txt";//0
    	final String NEXT_ID_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "nextID.txt";//1
    	final String WEIGHTS_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "weights.txt";//2
    	final String PULLUPS_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "pullups.txt";//3
    	final String PUSHUPS_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "pushups.txt";//4
    	final String RUNNING_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "running.txt";//5
    	final String WALKING_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "walking.txt";//6
    	final String YOGA_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "yoga.txt";//7
    	
    	//create files if needed
    	try {
			createFileIfNeeded(FITNESS_USER_PATH);
			createFileIfNeeded(NEXT_ID_PATH);
			createFileIfNeeded(WEIGHTS_PATH);
			createFileIfNeeded(PULLUPS_PATH);
			createFileIfNeeded(PUSHUPS_PATH);
			createFileIfNeeded(RUNNING_PATH);
			createFileIfNeeded(WALKING_PATH);
			createFileIfNeeded(YOGA_PATH);
			//LOAD DUMMY CONTENT TO FILES ONLY IF there is no user at all.
			if(isFileEmpty(FITNESS_USER_PATH)){
				loadSampleContent(FITNESS_USER_PATH,0);
				loadSampleContent(NEXT_ID_PATH,1);				
				loadSampleContent(WEIGHTS_PATH,2);			
				loadSampleContent(PULLUPS_PATH,3);			
				loadSampleContent(PUSHUPS_PATH,4);			
				loadSampleContent(RUNNING_PATH,5);			
				loadSampleContent(WALKING_PATH,6);			
				loadSampleContent(YOGA_PATH,7);
			}
			//load fitness user info into application.
			loadFitnessUserInfo(list,FITNESS_USER_PATH);
			loadUserExcercises(list,PULLUPS_PATH,PUSHUPS_PATH,RUNNING_PATH,WALKING_PATH,YOGA_PATH);
			
					
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Internal IO error, contact support.","My Fitness Tracker - JavaBeaners",JOptionPane.ERROR_MESSAGE);
		}   	
    	
    }
    
    
    public static void loadUserExcercises(List<FitnessUser> list,String pullupsPath,String pushupsPath ,String runningPath,String walkingPath,String yogaPath){
    	
    }
    
    public static void loadRunningActivitiesFor(FitnessUser user, String runningPath ){
    	
    }
    /**
     * checks to see if file does not exists if so creates a file at the specified path
     * @param path
     * @throws IOException
     */
    public static void createFileIfNeeded(String path) throws IOException{
    	if(!fileExists(path)){
    		File file = new File(path);
    		// Works for both Windows and Linux
    		file.getParentFile().mkdirs(); 
    		file.createNewFile();    		
    	}    	
    }
    /**
     * Load sample dummy content to the application files
     * @param path
     * @param type
     */
    public static void loadSampleContent(String path, int type){
    	final String FITNESS_USER_CONTENT = "100,Jhon Doe,jdoe,M,50,70,145,140\n";//userid,name,username,gender,age,height,currentWeight,targetWeight
    	final String NEXT_ID_CONTENT = "101\n";
    	final String WEIGHTS_CONTENT = "100,145,1447276622832\n";//userid,currentWeight,date
    	final String PULLUPS_CONTENT = "100,1447276622832,10\n";//userid,date,reps
    	final String PUSHUPS_CONTENT = "100,1447276622832,20\n";//userid,date,reps
    	final String RUNNING_CONTENT = "100,1447276622832,1,145,35\n";//userid,date,distance,weight,duration(minutes)
    	final String WALKING_CONTENT = "100,1447276622832,2,145,45\n";//userid,date,distance,weight,duration(minutes)
    	final String YOGA_CONTENT = "100,1447276622832,145,30\n";//userId,date,weight,duration(minutes)
    	
    	switch (type) {
        case 0:  writeContent(path,FITNESS_USER_CONTENT);
                 break;
        case 1:  writeContent(path,NEXT_ID_CONTENT);
        		 break;
        case 2:
        		 writeContent(path,WEIGHTS_CONTENT);
        		 break;
        case 3:  writeContent(path,PULLUPS_CONTENT);
        		 break;
        case 4:  writeContent(path,PUSHUPS_CONTENT);
        		 break;
        case 5:  writeContent(path,RUNNING_CONTENT);
        		 break;
        case 6:  writeContent(path,WALKING_CONTENT);
		 		 break;
        case 7:  writeContent(path,YOGA_CONTENT);
		 		 break;
        default: JOptionPane.showMessageDialog(null, "No files were loaded with content.");
        		break;
    	}
    }
    /**
     * Writes contents to specified file and particular content.
     * @param path
     * @param content
     */
    public static void writeContent(String path, String content){
    	PrintWriter out = null;
		try {
			out = new PrintWriter(new FileOutputStream(new File(path)));
			out.print(content);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Internal error, please contact support file not found.");
		}finally{
			out.close();
		}    	
    }
    public static boolean isFileEmpty(String path) throws IOException{
    	BufferedReader buffer = new BufferedReader(new FileReader(path));     
    	return buffer.readLine() == null;
    }
    
    public static boolean fileExists(String path){
    	File aFile = new File(path);    	
    	return aFile.exists() && !aFile.isDirectory();    	
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
			JOptionPane.showMessageDialog(null, "Internal error, please contact support file not found.");
		}finally{
			out.close();
		}    	
    }
    
}
