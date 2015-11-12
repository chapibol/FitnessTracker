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
    
    public ExerciseActivity(){
        
    }
    
    public ExerciseActivity(int userId, Date date){
        this.userId = userId;
        this.date = date;
    }
    
    public int getUserId(){
        return userId;
    }
    public Date getDate(){
        return date;
    }
    
    /**
     * sets userId if input is valid. If invalid, then throw InvalidInputException.
     * @param userId
     * @throws InvalidInputException
     */
    public void setUserId(int userId) throws InvalidInputException{
        if(userId < 100 || userId > 999){
            throw new InvalidInputException("Error: UserId must be between 100 and 999.");
        }
        this.userId = userId;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
    abstract double calculateCaloriesBurned();
    
    public String toString(){
        return "";
    }
}
