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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class MyFitnessTrackerApp {


	public static void main(String[] args){
		final int MAX_NUMBER_USERS = 50;
		final String FITNESS_USER_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "fitnessUsers.txt";
		final String NEXT_ID_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "nextID.txt";
		final String WEIGHTS_PATH = "C:" + File.separator + "MyFitnessTrackerData" + File.separator + "weights.txt";//2
		final String[] activityOptions = { "Pullups", "Pushups", "Running", "Walking", "Yoga" };
		//array list to hold all fitness user accounts for the application. initial capacity of 10
		List<FitnessUser> fitnessUserList = new ArrayList<FitnessUser>(MAX_NUMBER_USERS);
		initializeApplication(fitnessUserList);
		int nextId = readNextId(NEXT_ID_PATH);
		int firstMenuOption = 0;
		int secondMenuOption = 0;
		FitnessUser currentUser = null;//variable to hold the user currently logged in

		do{
			firstMenuOption = getFirstMenuOption();
			if(firstMenuOption == 1){
				currentUser = login(fitnessUserList);
				//only procede with user operations if log in was successful
				if(currentUser != null){

					do{
						secondMenuOption = getSecondMenuOption();
						switch(secondMenuOption){
						case 1:	viewProfile(currentUser);
						break;
						case 2: String activityChoice = chooseActivity(activityOptions);
         						addExerciseActivity(activityOptions, activityChoice, currentUser);
							break;
						case 3: JOptionPane.showMessageDialog(null, "Good Bye " + currentUser.getUsername() + "!");
								//logoff the current user
								currentUser = null;
								break;
						default:
							break;
						}
					}while(secondMenuOption != 3);					

				}				
			}else if (firstMenuOption == 2){
				FitnessUser newUser = createNewAccount(fitnessUserList,NEXT_ID_PATH,FITNESS_USER_PATH,WEIGHTS_PATH);

			}else if(firstMenuOption == 3){
				JOptionPane.showMessageDialog(null, "GoodBye!");
			}
		}while(firstMenuOption != 3);


	}
	/**
	 * The purpose of this method is to display the fitUser's profile
	 * @param fitUser
	 */
	public static void viewProfile(FitnessUser fitUser){
		//get activities for the last 7 days
		List<ExerciseActivity> pastActivities = getActivitiesInThePast(fitUser,7);
		String fitUserInfo = "";
		String weeklyReportTop = "\nProgress Report For Last 7 Days:\n"
				+ "Activities                       Calories Burned\n"
				+ "---------------------------------------------------\n";
		String activitiesBody = "";
		String totalCalories = "";
		String message = "";
		String output = "";
		//build fitUserInfo
		fitUserInfo = buildUserProfile(fitUser);
		//build weekly report (last 7 days)
		activitiesBody = buildReportBody(pastActivities);
		totalCalories = buildTotalCaloriesReport(pastActivities);
		message = buildEncouragingMessage(fitUser);


		output += fitUserInfo + weeklyReportTop + activitiesBody + totalCalories + message;
		//display the whole thing
		JOptionPane.showMessageDialog(null,output ,"My Fitness Tracker - JavaBeaners",JOptionPane.INFORMATION_MESSAGE);
	}


	public static String buildEncouragingMessage(FitnessUser fit){
		//get second to last weight in the weights list
		WeightDatePair lastWeight = fit.getWeightDateList().get(fit.getWeightDateList().size()-1);
		double currentWeight = fit.getCurrentWeight();
		double weightDiff = currentWeight - lastWeight.getWeight();
		final String WEIGHT_LOSS_MESSAGE = "Look at you slim human being!, Keep it up! you have lost ";
		final String SAME_WEIGHT_MESSAGE = "Good work keeping up with your health!, Your weight has stayed the same ";
		final String WEIGHT_GAIN_MESSAGE = "Keep up the good work. You have gained some weight ";
		String finalMessage = "";

		if(weightDiff > 0){
			finalMessage = WEIGHT_GAIN_MESSAGE + String.format("%.2f", Math.abs(weightDiff)) + " lbs";
		}else if(weightDiff < 0){
			finalMessage = WEIGHT_LOSS_MESSAGE + String.format("%.2f", Math.abs(weightDiff)) + " lbs";
		}else{
			finalMessage = SAME_WEIGHT_MESSAGE;
		}


		return finalMessage + "\n";
	}
	/**
	 * Builds a string with the total calories burned 
	 * @param fit
	 * @return
	 */
	public static String buildTotalCaloriesReport(List<ExerciseActivity> exercises){
		double totalCalories = 0;
		for(ExerciseActivity e: exercises){
			totalCalories += e.calculateCaloriesBurned();
		}
		String totalCaloriesStr = String.format("%.2f",totalCalories);
		String output = "Total Calories Burned!    	        "+ totalCaloriesStr + "\n";
		String separator = "------------------------------------------------------\n";

		return output + separator;

	}
	/**
	 * Method to build the body of the report from exercises list in the fitnessUser
	 * @param exercises
	 * @return
	 */
	public static String buildReportBody(List<ExerciseActivity> exercises){
		String reportBody = "No activities found this past week.\n";
		String separator = "---------------------------------------------------\n";
		if(!exercises.isEmpty()){
			//first sort from newest to oldest. using comparator
			Collections.sort(exercises, new Comparator<ExerciseActivity>() {
				public int compare(ExerciseActivity o1, ExerciseActivity o2) {
					//check for any nulls
					if (o1.getDate() == null || o2.getDate() == null)
						return 0;
					return o1.getDate().compareTo(o2.getDate());
				}
			});
			reportBody = "";
			//build the report's body
			for(ExerciseActivity e: exercises){
				reportBody += e.reportStringWriter();
			}
			reportBody += separator;
		}		
		return reportBody;
	}
	/**
	 * Gets all of the activities saved by the user  in the last daysAgo parameter.
	 * @param fit
	 * @return list of ExerciseActivity instances
	 */
	public static List<ExerciseActivity> getActivitiesInThePast(FitnessUser fit, int daysAgo){
		long today = (new Date()).getTime();//get todays date
		final long MILLISECONDS_IN_A_DAY = 86400000 ;
		long timeAgo = today - (MILLISECONDS_IN_A_DAY * daysAgo);//time in miliseconds however many days 	
		//get all activities from a week ago until today.
		List<ExerciseActivity> pastExercises = new ArrayList<ExerciseActivity>(10);
		for(ExerciseActivity ex: fit.getActivities()){
			if(ex.getDate().getTime() >= timeAgo){
				pastExercises.add(ex);
			}
		}
		//return those activities
		return pastExercises;
	}

	/**
	 * Method to build a basic profile info about the user
	 * @param user
	 * @return profile string
	 */
	public static String buildUserProfile(FitnessUser user){
		String profile = "Name: " + user.getName() + "\n"
				+ "Age: " + user.getAge() + "\n"
				+ "Weight: " + user.getCurrentWeight() + " lbs\n"
				+ "Target Weight: " + user.getTargetWeight() + " lbs\n";
		return profile;					   
	}
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
			JOptionPane.showMessageDialog(null, "Internal IO error (Initialize Application), contact support.","My Fitness Tracker - JavaBeaners",JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "Internal error (running) Number Format Exception, Id, height, weight error");
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
			JOptionPane.showMessageDialog(null, "Internal error (walking) Number Format Exception, Id, height, weight error");
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
			JOptionPane.showMessageDialog(null, "Internal error (pullups) Number Format Exception, Id, height, weight error");
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
			JOptionPane.showMessageDialog(null, "Internal error (pushups) Number format Exception, Id, height, weight error");
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
			JOptionPane.showMessageDialog(null, "Internal error (yoga) Number format Exception, Id, height, weight error");
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
					weight = Double.parseDouble(scan.next().trim());
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
			JOptionPane.showMessageDialog(null, "Internal error (weightDate Pair) Number Format, Id, height, weight error");
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
		final String FITNESS_USER_CONTENT = "100,Jhon Doe,jdoe,1234,M,50,70,145,140";//userid,name,username,password,gender,age,height,currentWeight,targetWeight
		final String NEXT_ID_CONTENT = "101\n";
		final String WEIGHTS_CONTENT = "100,145,1447276622832";//userid,currentWeight,date
		final String PULLUPS_CONTENT = "100,1447276622832,10";//userid,date,reps
		final String PUSHUPS_CONTENT = "100,1447276622832,20";//userid,date,reps
		final String RUNNING_CONTENT = "100,1447276622832,1,145,35";//userid,date,distance,weight,duration(minutes)
		final String WALKING_CONTENT = "100,1447276622832,2,145,45";//userid,date,distance,weight,duration(minutes)
		final String YOGA_CONTENT = "100,1447276622832,145,30";//userId,date,weight,duration(minutes)


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
			out.println(content);
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
			String password = "";
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
				password = scan.next().trim();//New
				gender = scan.next();
				age = Integer.parseInt(scan.next().trim());
				height = Integer.parseInt(scan.next().trim());;
				currentWeight = Double.parseDouble(scan.next().trim());
				targetWeight = Double.parseDouble(scan.next().trim());
				//create and populate a FitnessUser object for the current line
				FitnessUser aUser = new FitnessUser(name, username, password, gender);//NEW
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
				userChoice = displayMenus(1);
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
	 * Method to get the users option for the second menu. once user is logged in.
	 * @return
	 */
	public static int getSecondMenuOption(){
		int userChoice = 0;
		do{
			try{
				userChoice = displayMenus(2);
				if (userChoice < 1 || userChoice > 4){
					JOptionPane.showMessageDialog(null, "Invalid choice! Please enter a valid menu option.","My Fitness Tracker - JavaBeaners",JOptionPane.ERROR_MESSAGE);
				}
			}catch(NumberFormatException e){
				//reset userChoice to 0 so that while condition can be checked again properly
				userChoice = 0;
				JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid menu option.","My Fitness Tracker - JavaBeaners",JOptionPane.ERROR_MESSAGE);
			}			
		}while(userChoice < 1 || userChoice > 4);
		return userChoice;		
	}

	/**
	 * Method to display the menus for fitness tracker. what type of menu depends on parameter type
	 * @param type 1 for Main Menu, 2 for Logged in menu (view profile, add activity, etc.)
	 * @return userChoice
	 * @throws NumberFormatException
	 */
	public static int displayMenus(int type) throws NumberFormatException{
		String menu = "";
		if(type == 1){
			menu = "My Fitness Tracker \n"
					+ "(1) Login (existing users)\n"
					+ "(2) Create new account\n"
					+ "(3) Exit Application";
		}else if (type == 2){
			menu = "My Fitness Tracker \n"
					+ "(1) View Profile\n"
					+ "(2) Add Activity\n"					
					+ "(3) Exit";
			
			//+ "(3) Update Profile\n"
		}
		int userChoice = Integer.parseInt(JOptionPane.showInputDialog(null,menu,"My Fitness Tracker - JavaBeaners", JOptionPane.INFORMATION_MESSAGE));

		return userChoice;
	}

	/**
	 * Method to create a new account once created account is saved both to the application and the file
	 * @param fitnessUserList
	 * @param userIdPath
	 * @param fitnessUserPath
	 * @return new user created reference
	 */
	public static FitnessUser createNewAccount(List<FitnessUser> fitnessUserList,String userIdPath, String fitnessUserPath,String weightDatePath){
		FitnessUser newUser = new FitnessUser();

		boolean usernameCheck = false;
		String username = "";
		do{
			try{
				username = getUsername(2);
				usernameCheck = usernameExists(username, fitnessUserList);
				newUser.setUsername(username);
				if(usernameCheck){
					JOptionPane.showMessageDialog(null, "Username already exists.");         
				}
			}catch(InvalidInputException e){
				JOptionPane.showMessageDialog(null, "Invalid Input: Empty");
			}
		}while(usernameCheck);

		String password = getPassword(2);
		try{
			newUser.setPassword(password);
		}catch(InvalidInputException e){
			JOptionPane.showMessageDialog(null, "Invalid Input: Empty");
		}

		boolean nameCheck = false;
		String name = "";
		do{
			try{
				name = JOptionPane.showInputDialog("Enter your name");
				newUser.setName(name);
				nameCheck = true;
			}catch(InvalidInputException e){
				JOptionPane.showMessageDialog(null, "Invalid Input: Empty");
			}
		}while(!nameCheck);

		Object[] genders = {"Male","Female"};

		// 0 = male, 1 = female
		int gender = JOptionPane.showOptionDialog(null,"Choose a gender","choose an option",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null,
				genders,
				genders[0]);
		try{
			if(gender == 0){
				newUser.setGender("M");
			}
			else{
				newUser.setGender("F");
			}
		}catch(InvalidInputException e){
			JOptionPane.showMessageDialog(null, "Invalid Input: Must be 'M' or 'F'");
		}

		boolean ageCheck = false;
		int age = 0;
		do{
			try{
				age = Integer.parseInt(JOptionPane.showInputDialog("Enter your age"));
				newUser.setAge(age);
				ageCheck = true;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Invalid Input: Must be a number");
			}catch(InvalidInputException e){
				JOptionPane.showMessageDialog(null, e);
			}
		}while(!ageCheck);

		boolean heightCheck = false;
		int height = 0;
		do{
			try{
				height = Integer.parseInt(JOptionPane.showInputDialog("Enter your height in inches"));
				newUser.setHeight(height);
				heightCheck = true;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Invalid Input: Must be a number");
			}catch(InvalidInputException e){
				JOptionPane.showMessageDialog(null, e);
			}
		}while(!heightCheck);

		boolean curWeightCheck = false;
		double currWeight = 0;
		do{
			try{
				currWeight = Double.parseDouble(JOptionPane.showInputDialog("What is your current weight?"));
				newUser.setCurrentWeight(currWeight);
				curWeightCheck = true;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Invalid Input: Must be a number");
			}catch(InvalidInputException e){
				JOptionPane.showMessageDialog(null, e);
			}
		}while(!curWeightCheck);

		boolean tarWeightCheck = false;
		double tarWeight = 0;
		do{
			try{
				tarWeight = Double.parseDouble(JOptionPane.showInputDialog("What is your target weight?"));
				newUser.setTargetWeight(tarWeight);
				tarWeightCheck = true;
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Invalid Input: Must be a number");
			}catch(InvalidInputException e){
				JOptionPane.showMessageDialog(null, e);
			}
		}while(!tarWeightCheck);

		int userId = readNextId(userIdPath);
		newUser.setUserId(userId);
		//save the current weight to weight date pair list in newUser
		WeightDatePair weightDate = new WeightDatePair(newUser.getUserId(),currWeight,new Date());
		newUser.addWeightDatePair(weightDate);
		userId++;
		saveNextId(userIdPath,userId);
		//save newly created user to applicaiton
		fitnessUserList.add(newUser);
		//save newly create user to file
		appendToFile(fitnessUserPath, newUser.stringWriter());
		//save newly created weightDatePair to file also
		appendToFile(weightDatePath,weightDate.stringWriter());

		return newUser;
	}

	/**
	 * Method to append content to a file at specified path
	 * @param path
	 * @param content
	 */
	public static void appendToFile(String path, String content) {

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(path, true));
			out.write(content);
			out.newLine();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to create a new user account
	 * @return FitnessUser
	 *
	 *
	 */
	public static boolean usernameExists(String username, List<FitnessUser> list){
		boolean exists = false;
		for(FitnessUser fit: list){
			if(fit.getUsername().equals(username)){
				exists = true;
			}
		}
		return exists;
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

	/**
	 * Method to login a user to the application, returns the logged in user once done or null if unsuccessful
	 * @param userList
	 * @return
	 */
	public static FitnessUser login(List<FitnessUser> userList){
		String username = getUsername(1);//1 for existing users
		String password = getPassword(1);

		FitnessUser aUser = authenticateUsernameAndPassword(userList, username, password);
		if(aUser != null){
			JOptionPane.showMessageDialog(null, "Log in Success! \nWelcome " + aUser.getUsername() + "!","My Fitness Tracker - JavaBeaners", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "Log in Unsuccessful! \nEither password or username did not match.","My Fitness Tracker - JavaBeaners", JOptionPane.ERROR_MESSAGE);
		}

		return aUser;
	} 

	/**
	 * Method to authenticate username and password, returns the matching fitness user or null if no match.
	 * @param userList
	 * @param username
	 * @param password
	 * @return
	 */
	public static FitnessUser authenticateUsernameAndPassword(List<FitnessUser>userList, String username, String password){

		for(FitnessUser fit: userList){
			if(fit.getUsername().equals(username) && fit.getPassword().equals(password)){
				return fit;
			}
		}

		return null;
	}
	/**
	 * Method to get a valid username from the user
	 * @param type 1 for existing user prompt 2 for new user prompt
	 * @return valid username
	 */
	public static String getUsername(int type){
		String prompt = "";
		String username = "";
		boolean isUsernameValid = false;
		//determine what type of prompt
		if(type == 1){
			prompt = "Enter your username";
		}else if(type == 2){
			prompt = "Create a username";
		}else{
			prompt = "Enter a username";
		}

		//prompt for username until a valid one is entered
		do{
			username = JOptionPane.showInputDialog(null,prompt,"My Fitness Tracker - JavaBeaners", JOptionPane.INFORMATION_MESSAGE);
			isUsernameValid = Utility.isStringDataValid(username);

			if(!isUsernameValid){
				JOptionPane.showMessageDialog(null, "Error please enter a valid username","My Fitness Tracker - JavaBeaners", JOptionPane.INFORMATION_MESSAGE);
			}
		}while(!isUsernameValid);

		return username;
	}
	/**
	 * Method to get a valid password from the user.
	 * @param type
	 * @return
	 */
	public static String getPassword(int type){
		String prompt = "";
		String password = "";
		boolean isPasswordValid = false;
		//determine what type of prompt
		if(type == 1){
			prompt = "Enter your password";
		}else if(type == 2){
			prompt = "Create a password (password must be at least 4 characters and is case sensitive)";
		}else{
			prompt = "Enter a password";
		}

		//prompt for username until a valid one is entered
		do{
			password = JOptionPane.showInputDialog(null,prompt,"My Fitness Tracker - JavaBeaners", JOptionPane.INFORMATION_MESSAGE);
			isPasswordValid = Utility.isPasswordValid(password);

			if(!isPasswordValid){
				JOptionPane.showMessageDialog(null, "Error please enter a valid password","My Fitness Tracker - JavaBeaners", JOptionPane.INFORMATION_MESSAGE);
			}
		}while(!isPasswordValid);

		return password;
	}

	public static String chooseActivity(String[] activityOptions){
		boolean validChoice = false;
		String choice = "";
		do{
			try{
				choice = (String) JOptionPane.showInputDialog(null, "Choose an activity",
						"Choose an activity", JOptionPane.QUESTION_MESSAGE, null,
						activityOptions,
						activityOptions[1]);
				validChoice = true;
				for(String i: activityOptions){
					if(choice.equals(i)){
						validChoice = true;
					}
				}
			}catch(NullPointerException e){
				validChoice = false;
				JOptionPane.showMessageDialog(null, "You must choose an activity");
			}
		}while(validChoice == false);
		return choice;
	}

	public static void addExerciseActivity(String[] activityOptions, String activity, FitnessUser currentUser){
		if(activity.equals(activityOptions[0])){//Pullups
			addPullUpActivity(currentUser);
		}
		else if(activity.equals(activityOptions[1])){//Pushups
			addPushUpActivity(currentUser);
		}
		else if(activity.equals(activityOptions[2])){//Running
			addRunningActivity(currentUser);
		}
		else if(activity.equals(activityOptions[3])){//Walking
			addWalkingActivity(currentUser);
		}
		else if(activity.equals(activityOptions[4])){//Yoga
			addYogaActivity(currentUser);
		}
	}

	public static void addPullUpActivity(FitnessUser currentUser){
		boolean validInput = true;
		int reps = 0;
		PullUp pull = new PullUp();
		do{
			validInput = true;
			try{
				reps = Integer.parseInt(JOptionPane.showInputDialog("Reps(quantity):"));
				pull.setUserId(currentUser.getUserId());
				pull.setDate(new Date());
				pull.setQuantity(reps);
			}catch(NumberFormatException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, "Must be a number");
			}catch(InvalidInputException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, e);
			}
		}while(validInput == false);
		currentUser.addExerciseActivity(pull);
		appendToFile("C:" + File.separator + "MyFitnessTrackerData" + File.separator + "pullups.txt", pull.stringWriter());
	}

	public static void addPushUpActivity(FitnessUser currentUser){
		boolean validInput = true;
		int reps = 0;
		PushUp push = new PushUp();
		do{
			validInput = true;
			try{
				reps = Integer.parseInt(JOptionPane.showInputDialog("Reps(quantity):"));
				push.setUserId(currentUser.getUserId());
				push.setDate(new Date());
				push.setQuantity(reps);
			}catch(NumberFormatException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, "Must be a number");
			}catch(InvalidInputException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, e);
			}
		}while(validInput == false);
		currentUser.addExerciseActivity(push);
		appendToFile("C:" + File.separator + "MyFitnessTrackerData" + File.separator + "pushups.txt", push.stringWriter());
	}

	public static void addRunningActivity(FitnessUser currentUser){
		boolean validInput = true;
		double distance = 0;
		int duration = 0;
		Running run = new Running();
		do{
			validInput = true;
			try{
				distance = Double.parseDouble(JOptionPane.showInputDialog("Distance(miles):"));
				run.setDistance(distance);
			}catch(NumberFormatException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, "Must be a number");
			}catch(InvalidInputException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, e);
			}
		}while(validInput == false);
		do{
			validInput = true;
			try{
				duration = Integer.parseInt(JOptionPane.showInputDialog("Time(minutes):"));
				run.setDuration(duration);
			}catch(NumberFormatException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, "Must be a number");
			}catch(InvalidInputException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, e);
			}
		}while(validInput == false);
		run.setUserId(currentUser.getUserId());
		run.setDate(new Date());
		try{
			run.setWeight(currentUser.getCurrentWeight());
		}catch(InvalidInputException e){
			JOptionPane.showMessageDialog(null, e);
		}
		currentUser.addExerciseActivity(run);
		appendToFile("C:" + File.separator + "MyFitnessTrackerData" + File.separator + "running.txt", run.stringWriter());
	}

	public static void addWalkingActivity(FitnessUser currentUser){
		boolean validInput = true;
		double distance = 0;
		int duration = 0;
		Walking walk = new Walking();
		do{
			validInput = true;
			try{
				distance = Double.parseDouble(JOptionPane.showInputDialog("Distance(miles):"));
				walk.setDistance(distance);
			}catch(NumberFormatException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, "Must be a number");
			}catch(InvalidInputException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, e);
			}
		}while(validInput == false);
		do{
			validInput = true;
			try{
				duration = Integer.parseInt(JOptionPane.showInputDialog("Time(minutes):"));
				walk.setDuration(duration);
			}catch(NumberFormatException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, "Must be a number");
			}catch(InvalidInputException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, e);
			}
		}while(validInput == false);
		walk.setUserId(currentUser.getUserId());
		walk.setDate(new Date());
		try{
			walk.setWeight(currentUser.getCurrentWeight());
		}catch(InvalidInputException e){
			JOptionPane.showMessageDialog(null, e);
		}
		currentUser.addExerciseActivity(walk);
		appendToFile("C:" + File.separator + "MyFitnessTrackerData" + File.separator + "walking.txt", walk.stringWriter());
	}

	public static void addYogaActivity(FitnessUser currentUser){
		boolean validInput = true;
		int duration = 0;
		Yoga yog = new Yoga();
		do{
			validInput = true;
			try{
				duration = Integer.parseInt(JOptionPane.showInputDialog("Time(minutes):"));
				yog.setDuration(duration);
			}catch(NumberFormatException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, "Must be a number");
			}catch(InvalidInputException e){
				validInput = false;
				JOptionPane.showMessageDialog(null, e);
			}
		}while(validInput == false);
		yog.setUserId(currentUser.getUserId());
		yog.setDate(new Date());
		try{
			yog.setWeight(currentUser.getCurrentWeight());
		}catch(InvalidInputException e){
			JOptionPane.showMessageDialog(null, e);
		}
		currentUser.addExerciseActivity(yog);
		appendToFile("C:" + File.separator + "MyFitnessTrackerData" + File.separator + "yoga.txt", yog.stringWriter());
	}
}