/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Daniel
 */
import java.util.Date;
public abstract class ExerciseActivity {
    private int userId;
    private int duration;
    private Date date;
    private double caloriesBurned;
    
    public ExerciseActivity(){
        
    }
    
    public ExerciseActivity(int userId, int duration, Date date){
        this.userId = userId;
        this.duration = duration;
        this.date = date;
    }
    
    //Accessors below
    public int getUserId(){
        return userId;
    }
    public int getDuration(){
        return duration;
    }
    public Date getDate(){
        return date;
    }
    
    //Mutators below
    public void setUserId(int id){
        userId = id;
    }
    public void setDuration(int duration) throws InvalidInputException{
      if(duration <= 0 || duration > 999){
         throw new InvalidInputException("Error: Invalid entry.");
      }
      this.duration = duration;
    }
    public void setDate(Date date){
        this.date = date;
    }
    
    abstract double calculateCaloriesBurned();
    
    public String toString(){
        return "";
    }
}
