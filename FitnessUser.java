
import java.util.ArrayList;

/**
 *
 * @author Daniel de Souza
 */

public class FitnessUser {
    private String name;
    private String userName;
    private char gender;
    private int userId;
    private int age;
    private int height;
    private double currentWeight;
    private double targetWeight;
    private ArrayList<Activity> activities;
    private ArrayList<WeightDatePair> weightDateList;
    public static int numberOfUsers = 0;

    public FitnessUser(){
            numberOfUsers++;
    }

    public FitnessUser(String name, String userName, char gender){
            this();
            this.name = name;
            this.userName = userName;
            this.gender = gender;
    }

    //Accessor methods are written below
    public String getName(){
            return name;
    }
    public String getUsername(){
            return userName;
    }
    public char getGender(){
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
//    public Activity[] getActivities(){
//            return activities;
//    }
//    public WeightDatePair[] getWeightDateList(){
//            return weightDateList;
//    }
    public static int getNumberOfUsers(){
            return numberOfUsers;
    }

    //Mutator methods are written below
    public void setName(String name){
            this.name = name;
    }
    public void setUsername(String username){
            userName = username;
    }
    public void setGender(char gender){
            this.gender = gender;
    }
    public void setUserId(int userId){
            this.userId = userId;
    }
    public void setAge(int age){
            this.age = age;
    }
    public void setHeight(int height){
            this.height = height;
    }
    public void setCurrentWeight(double currentWeight){
            this.currentWeight = currentWeight;
    }
    public void setTargetWeight(double targetWeight){
            this.targetWeight = targetWeight;
    }

//    public void addExerciseActivity(Activity exercise){
//            activities[i] = exercise;
//    }

//    public void addWeightDatePair(WeightDatePair weightDate){
//            weightDateList[i] = weightDate;
//    }

//    public void setNumberOfUsers(int numberOfUsers){
//            this.numberOfUsers = numberOfUsers;
//    }

//    public String toString(){
//            return "Username: " + userName;
//    }
}
