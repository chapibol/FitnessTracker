/*
 * Class FitnessUser
 *
 * This class represents each user of the application.
 * It stores user:
 * name         age
 * userName     height    
 * gender       currentWeight
 * userId       targetWeight
 * activities   weightDateList
 * 
 * 
 * @author Daniel de Souza, Luis Velasco
 * Group Project Final Implementation
 * IT 306
 */

import java.util.ArrayList;
import java.util.List;

public class FitnessUser {
    private String name;
    private String userName;
    private String password;
    private String gender;
    private int userId;
    private int age;
    private int height;
    private double currentWeight;
    private double targetWeight;
    private List<ExerciseActivity> activities;
    private List<WeightDatePair> weightDateList;
    public static int numberOfUsers = 0;

    public FitnessUser(){
            numberOfUsers++;
            activities = new ArrayList<ExerciseActivity>(10);
            weightDateList = new ArrayList<WeightDatePair>(10);
    }

    public FitnessUser(String name, String userName, String password, String gender){
            this();
            this.name = name;
            this.userName = userName;
            this.password = password;
            this.gender = gender;
    }

    public String getName(){
            return name;
    }
    public String getUsername(){
            return userName;
    }
    public String getPassword(){
            return password;
    }
    public String getGender(){
            return gender;
    }
    public int getUserId(){
            return userId;
    }
    public int getAge(){
            return age;
    }
    public int getHeight(){
            return height;
    }
    public double getCurrentWeight(){
            return currentWeight;
    }
    public double getTargetWeight(){
            return targetWeight;
    }
   public List<ExerciseActivity> getActivities(){
           return activities;
   }
   public List<WeightDatePair> getWeightDateList(){
           return weightDateList;
   }
    public static int getNumberOfUsers(){
            return numberOfUsers;
    }

    
    /*setName throws exception if string input is empty*/
    public void setName(String name) throws InvalidInputException{
            if(name == null || name.isEmpty()){
                throw new InvalidInputException("No name was entered.");
            }
            this.name = name;
    }
    
    /**
     * sets userName if input is valid. If invalid, then throw InvalidInputException.
     * @param username
     * @throws InvalidInputException
     */
    public void setUsername(String username) throws InvalidInputException{
            if(username == null || username.isEmpty()){
                throw new InvalidInputException("No username was entered.");
            }
            userName = username;
    }
    
    /**
     * sets password if input is valid. If invalid, then throw InvalidInputException.
     * @param password
     * @throws InvalidInputException
     */
    public void setPassword(String password) throws InvalidInputException{
            if(password == null || password.isEmpty()){
                throw new InvalidInputException("No password was entered.");
            }
            this.password = password;
    }
    
    /**
     * sets gender if input is valid. If invalid, then throw InvalidInputException.
     * @param gender
     * @throws InvalidInputException
     */
    public void setGender(String gender) throws InvalidInputException{

        if(gender == null || gender.isEmpty()){
            throw new InvalidInputException("No gender was entered.");
        }
        else if(!gender.equalsIgnoreCase("M") & !gender.equalsIgnoreCase("F")){
            throw new InvalidInputException("Invalid entry.");
        }
        this.gender = gender.toUpperCase();
    }
    
    /**
     * sets userId
     * @param userId
     */
    public void setUserId(int userId){
        this.userId = userId;
    }
    
    /**
     * sets userName if input is valid. If invalid, then throw InvalidInputException.
     * @param username
     * @throws InvalidInputException
     */
    public void setAge(int age) throws InvalidInputException{
            if(age < 18 || age > 120){
               throw new InvalidInputException("Enter a valid age between 18 and 120.");
            }
            this.age = age;
    }
    
    /**
     * sets height if input is valid. If invalid, then throw InvalidInputException.
     * @param height
     * @throws InvalidInputException
     */
    public void setHeight(int height) throws InvalidInputException{
            if(height < 10 || height > 100){
               throw new InvalidInputException("Enter a height between 10 and 100 inches.");
            }
            this.height = height;
    }
    
    /**
     * sets currentWeight if input is valid. If invalid, then throw InvalidInputException.
     * @param currentWeight
     * @throws InvalidInputException
     */
    public void setCurrentWeight(double currentWeight) throws InvalidInputException{
         if(currentWeight <= 0 || currentWeight > 999){
               throw new InvalidInputException("Weight must be between 1 and 999.");
         }    
         this.currentWeight = currentWeight;
    }
    
    /**
     * sets targetWeight if input is valid. If invalid, then throw InvalidInputException.
     * @param targetWeight
     * @throws InvalidInputException
     */
    public void setTargetWeight(double targetWeight) throws InvalidInputException{
        if(targetWeight <= 0 || targetWeight > 999){
               throw new InvalidInputException("Weight must be between 1 and 999.");
         }    
         this.targetWeight = targetWeight;
    }

   public void addExerciseActivity(ExerciseActivity exercise){
           activities.add(exercise);
   }

   public void addWeightDatePair(WeightDatePair weightDate){
       weightDateList.add(weightDate);
   }

   public void setNumberOfUsers(int numberOfUsers){
           this.numberOfUsers = numberOfUsers;
   }
   
   //userid,name,username,password,gender,age,height,currentWeight,targetWeight
   //used for writing to the fitness user text file
   public String stringWriter(){
      return userId + "," + name + "," + userName + "," + password + "," + gender + "," + age + "," + height + "," + currentWeight + "," + targetWeight;
   }

   public String toString(){
           return "UserID: " + userId + "\nName: " + name
                     + "\nUsername: " + userName
                     + "\nPassword: " + password
                     + "\nGender: " + gender
                     + "\nAge: " + age + "\nHeight: " + height
                     + "\nCurrent Weight: " + currentWeight
                     + "\nTarget Weight: " + targetWeight + "\n";
   }
}