/*
 * Class ExerciseActivity
 *
 * This abstract class allows each subclass to inherit its calculateCaloriesBurned method.
 * 
 * @author Daniel de Souza, Luis Velasco
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
     * sets userId
     * @param userId
     */
    public void setUserId(int userId){
        this.userId = userId;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
    abstract double calculateCaloriesBurned();
    
    public String toString(){
        return "UserID: " + userId;
    }
}
