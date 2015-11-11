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
    private Date date;
    private double caloriesBurned;
    
    public ExerciseActivity(){
        
    }
    
    public ExerciseActivity(int userId, Date date){
        this.userId = userId;
        this.date = date;
    }
    
    //Accessors below
    public int getUserId(){
        return userId;
    }
    public Date getDate(){
        return date;
    }
    
    //Mutators below
    public void setUserId(int id){
        userId = id;
    }
    public void setDate(Date date){
        this.date = date;
    }
    
    abstract double calculateCaloriesBurned();
    
    public String toString(){
        return "";
    }
}
