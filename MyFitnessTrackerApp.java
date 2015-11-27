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
		//final String FITNESS_USER_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "fitnessUsers.txt";
		final String NEXT_ID_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "nextID.txt";
		//array list to hold all fitness user accounts for the application. initial capacity of 10
		List<FitnessUser> fitnessUserList = new ArrayList<FitnessUser>(MAX_NUMBER_USERS);
		initializeApplication(fitnessUserList);
		int nextId = readNextId(NEXT_ID_PATH);


		        for(ExerciseActivity ex: fitnessUserList.get(0).getActivities()){
		        	System.out.println(ex.toString());
		        }
		        System.out.println();
		        for(WeightDatePair wd: fitnessUserList.get(0).getWeightDateList()){
		        	System.out.println(wd.toString());
		        }
		//        int firstMenuOption = 0;
		//        //display login screen
		//        
		////        Date d = new Date();
		////        
		////        System.out.println(d.getTime());
		//        
		//        
		//        do{
		//        	firstMenuOption = getFirstMenuOption();
		//        }while(firstMenuOption != 3);







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
	 * this data includes, fitness user personal info and all their info about activities performed
	 * @param list (list of fitness users)
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
		final String WEIGHT_DATE_PAIR_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "weight_dates.txt";//8

		//create files if needed for all of the text files needed by the application
		try {
			createFileIfNeeded(FITNESS_USER_PATH);
			createFileIfNeeded(NEXT_ID_PATH);
			createFileIfNeeded(WEIGHTS_PATH);
			createFileIfNeeded(PULLUPS_PATH);
			createFileIfNeeded(PUSHUPS_PATH);
			createFileIfNeeded(RUNNING_PATH);
			createFileIfNeeded(WALKING_PATH);
			createFileIfNeeded(YOGA_PATH);
			createFileIfNeeded(WEIGHT_DATE_PAIR_PATH);
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
			//load all user excersises from files
			loadUserExcercises(list,PULLUPS_PATH,PUSHUPS_PATH,RUNNING_PATH,WALKING_PATH,YOGA_PATH);
			
			loadWeightDatePairs(list,WEIGHTS_PATH);


		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Internal IO error, contact support.","My Fitness Tracker - JavaBeaners",JOptionPane.ERROR_MESSAGE);
		}   	

	}

	/**
	 * Method to load all of the activitie info for each user of the application
	 * @param list
	 * @param pullupsPath
	 * @param pushupsPath
	 * @param runningPath
	 * @param walkingPath
	 * @param yogaPath
	 */
	public static void loadUserExcercises(List<FitnessUser> list,String pullupsPath,String pushupsPath ,String runningPath,String walkingPath,String yogaPath){
		for(FitnessUser fit: list){
			loadPullupActivitiesFor(fit,pullupsPath);
			loadPushupActivitiesFor(fit,pushupsPath);
			loadRunningActivitiesFor(fit,runningPath);
			loadWalkingActivitiesFor(fit,walkingPath);
			loadYogaActivitiesFor(fit,yogaPath);
		}
	}
	/**
	 * Method to load weight date pairs for all users
	 * @param list
	 * @param weightDatePath
	 */
	public static void loadWeightDatePairs(List<FitnessUser> list, String weightDatePath){
		for(FitnessUser fit: list){
			loadWeightDatePairsFor(fit,weightDatePath);
		}
	}

	/**
	 * Method to load all of the running activities store in the files from aUser
	 * @param aUser
	 * @param runningPath
	 */
	public static void loadRunningActivitiesFor(FitnessUser aUser, String runningPath ){
		//100,1447276622832,1,145,35
		try {
			BufferedReader buff = new BufferedReader(new FileReader(new File(runningPath)));
			Scanner scan = null;
			String line = "";
			int userId = 0;
			long dateTime = 0;
			double distance = 0.0;
			double weight = 0;
			int duration = 0;
			//read lines
			while((line = buff.readLine()) != null){
				scan = new Scanner(line);
				scan.useDelimiter(",");
				userId = Integer.parseInt(scan.next().trim());
				//only load running activities that belong to aUser
				if(aUser.getUserId() == userId){
					//date in miliseconds
					dateTime = Long.parseLong(scan.next().trim());
					distance = Double.parseDouble(scan.next().trim());
					weight = Double.parseDouble(scan.next().trim());
					duration = Integer.parseInt(scan.next().trim());
					//create a running activity and add to aUser
					Running run = new Running(distance, weight, duration);
					run.setUserId(userId);
					run.setDate(new Date(dateTime));//
					run.setDistance(distance);
					run.setWeight(weight);
					run.setDuration(duration);
					//add activity to aUser
					aUser.addExerciseActivity(run);
				}			
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File could not be found.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO Exception contact suppport.");
		} catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Internal error, Id, height, weight error");
		} catch (InvalidInputException e){
			JOptionPane.showMessageDialog(null, "Internal error, invalid input.");
		}
	}
	/**
	 * Method to load walking activities for auser from specified path
	 * @param aUser
	 * @param runningPath
	 */
	public static void loadWalkingActivitiesFor(FitnessUser aUser, String runningPath ){
		//userid,date,distance,weight,duration(minutes)
		try {
			BufferedReader buff = new BufferedReader(new FileReader(new File(runningPath)));
			Scanner scan = null;
			String line = "";
			int userId = 0;
			long dateTime = 0;
			double distance = 0.0;
			double weight = 0;
			int duration = 0;
			//read lines
			while((line = buff.readLine()) != null){
				scan = new Scanner(line);
				scan.useDelimiter(",");
				userId = Integer.parseInt(scan.next().trim());
				//only load running activities that belong to aUser
				if(aUser.getUserId() == userId){
					//date in miliseconds
					dateTime = Long.parseLong(scan.next().trim());
					distance = Double.parseDouble(scan.next().trim());
					weight = Double.parseDouble(scan.next().trim());
					duration = Integer.parseInt(scan.next().trim());
					//create a running activity and add to aUser
					Walking walk = new Walking();
					walk.setUserId(userId);
					walk.setDate(new Date(dateTime));//
					walk.setDistance(distance);
					walk.setWeight(weight);
					walk.setDuration(duration);
					//add activity to aUser
					aUser.addExerciseActivity(walk);
				}			
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File could not be found.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO Exception contact suppport.");
		} catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Internal error, Id, height, weight error");
		} catch (InvalidInputException e){
			JOptionPane.showMessageDialog(null, "Internal error, invalid input.");
		}
	}
    /**
     * Metho to load pullups for aUser from specified path.
     * @param aUser
     * @param pullupPath
     */
    public static void loadPullupActivitiesFor(FitnessUser aUser, String pullupPath ){
    	//userid,date,reps										
    	try {
			BufferedReader buff = new BufferedReader(new FileReader(new File(pullupPath)));
			Scanner scan = null;
			String line = "";
			int userId = 0;
			long dateTime = 0;
			int quantity = 0;
			//read lines
			while((line = buff.readLine()) != null){
				scan = new Scanner(line);
				scan.useDelimiter(",");
				userId = Integer.parseInt(scan.next().trim());
				if(aUser.getUserId() == userId){
					dateTime = Long.parseLong(scan.next().trim());
					quantity = Integer.parseInt(scan.next().trim());
					//create a pullup activity and add to the 
					PullUp pullup = new PullUp();
					pullup.setUserId(userId);
					pullup.setDate(new Date(dateTime));
					pullup.setQuantity(quantity);					
					//add pullup to excercise activity list for aUser
					aUser.addExerciseActivity(pullup);
				}
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File could not be found.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO Exception contact suppport.");
		} catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Internal error, Id, height, weight error");
		} catch (InvalidInputException e){
         JOptionPane.showMessageDialog(null, "Internal error, invalid input");
      }
    }
    /**
     * Method to load push up activities for aUser from specified files
     * @param aUser
     * @param pushupPath
     */
    public static void loadPushupActivitiesFor(FitnessUser aUser, String pushupPath ){
    	//"100,1447276622832,20\n";  userid,date,reps
    	try {
			BufferedReader buff = new BufferedReader(new FileReader(new File(pushupPath)));
			Scanner scan = null;
			String line = "";
			int userId = 0;
			long dateTime = 0;
			int quantity = 0;
			//read lines
			while((line = buff.readLine()) != null){
				scan = new Scanner(line);
				scan.useDelimiter(",");
				userId = Integer.parseInt(scan.next().trim());
	            if(aUser.getUserId() == userId){
	            	   dateTime = Long.parseLong(scan.next().trim());
					   quantity = Integer.parseInt(scan.next().trim());
					   //create a pushup activity and add to the 
					   PushUp pushup = new PushUp();
					   pushup.setUserId(userId);
					   pushup.setDate(new Date(dateTime));
					   pushup.setQuantity(quantity);				   
					   //add pushup to excercise activity
					   aUser.addExerciseActivity(pushup);
	            }
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File could not be found.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO Exception contact suppport.");
		} catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Internal error, Id, height, weight error");
		} catch (InvalidInputException e){
         JOptionPane.showMessageDialog(null, "Internal error, invalid input");
      }
    }
    
    /**
     * Method to load yoga activities for aUser from the specified path
     * @param aUser
     * @param yogaPath
     */
    public static void loadYogaActivitiesFor(FitnessUser aUser, String yogaPath ){
    	//"100,1447276622832,145,30\n" userId,date,weight,duration(minutes)										
    	try {
			BufferedReader buff = new BufferedReader(new FileReader(new File(yogaPath)));

			Scanner scan = null;
			String line = "";
			int userId = 0;
			long dateTime = 0;
			double weight = 0;
			int duration = 0;

			//read lines
			while((line = buff.readLine()) != null){
				scan = new Scanner(line);
				scan.useDelimiter(",");
				userId = Integer.parseInt(scan.next().trim());
				if(userId == aUser.getUserId()){
					dateTime = Long.parseLong(scan.next().trim());
					weight = Double.parseDouble(scan.next().trim());
					duration = Integer.parseInt(scan.next().trim());				
					//create a yoga activity and add to the 
					Yoga yoga = new Yoga();
					yoga.setUserId(userId);
					yoga.setDate(new Date(dateTime));
					yoga.setWeight(weight);	
					yoga.setDuration(duration);				
					aUser.addExerciseActivity(yoga);
				}							
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File could not be found.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO Exception contact suppport.");
		} catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Internal error, Id, height, weight error");
		} catch (InvalidInputException e){
			JOptionPane.showMessageDialog(null, "Internal error, invalid input.");
		}
	}
    
    /**
     * Method to load the weight date pairs corresponding to aUser from weight date pair file
     * @param aUser
     * @param weightDatePath
     */
    public static void loadWeightDatePairsFor(FitnessUser aUser, String weightDatePath){
    	try {                                            //"100,145,1447276622832\n"
			BufferedReader buff = new BufferedReader(new FileReader(new File(weightDatePath)));
			Scanner scan = null;
			String line = "";
			int userId = 0;
			double weight = 0;
			long dateTime = 0;
			//read lines
			while((line = buff.readLine()) != null){
				scan = new Scanner(line);
				scan.useDelimiter(",");
				userId = Integer.parseInt(scan.next().trim());
	            if(aUser.getUserId() == userId){
	            	weight = Integer.parseInt(scan.next().trim());
	                dateTime = Long.parseLong(scan.next().trim());
					   
					//create a weight date pair object 
					WeightDatePair wd = new WeightDatePair();
			        wd.setUserId(userId);
					wd.setWeight(weight);
					wd.setDate(new Date(dateTime));
					 
	                aUser.addWeightDatePair(wd);
	            }
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File could not be found.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO Exception contact suppport.");
		} catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Internal error, Id, height, weight error");
		} catch (InvalidInputException e){
         JOptionPane.showMessageDialog(null, "Internal error, invalid input");
      }
    }
	
	/**
	 * Load sample dummy content for one user to the application files
	 * @param path (path of the file to be filled)
	 * @param type (the type of content to be filled)
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
		case 0:  writeSampleContent(path,FITNESS_USER_CONTENT);
				 break;
		case 1:  writeSampleContent(path,NEXT_ID_CONTENT);
				 break;
		case 2:	 writeSampleContent(path,WEIGHTS_CONTENT);
				 break;
		case 3:  writeSampleContent(path,PULLUPS_CONTENT);
				 break;
		case 4:  writeSampleContent(path,PUSHUPS_CONTENT);
				 break;
		case 5:  writeSampleContent(path,RUNNING_CONTENT);
				 break;
		case 6:  writeSampleContent(path,WALKING_CONTENT);
				 break;
		case 7:  writeSampleContent(path,YOGA_CONTENT);
				 break;
		default: JOptionPane.showMessageDialog(null, "No files were loaded with content.");
				 break;
		}
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
     * Writes sample content to files if needed.
     * @param path
     * @param content
     */
    public static void writeSampleContent(String path, String content){
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
	/**
	 * Method to check if a file is empty
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static boolean isFileEmpty(String path) throws IOException{
		BufferedReader buffer = new BufferedReader(new FileReader(path));     
		return buffer.readLine() == null;
	}

	/**
	 * Method to check if a file exists
	 * @param path
	 * @return
	 */
	public static boolean fileExists(String path){
		File aFile = new File(path);    	
		return aFile.exists() && !aFile.isDirectory();    	
	}
	/**
	 * Method to load fitness user info into application from appropriate files
	 * @param userList
	 * @param userDataPath
	 */
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
			while((line = buff.readLine()) != null){
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
			}//end while
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

	/**
	 * Method to get the user choice from the first menu displayed to her
	 * @return int
	 */
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

	/**
	 * Method to display the first menu
	 * @return
	 * @throws NumberFormatException
	 */
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