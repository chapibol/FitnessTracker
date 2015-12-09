/*
 * Class ExerciseActivity
 *
 * Abstract Class To serve as superclass to all the other more specific activities.
 * 
 * @author Daniel de Souza, Luis Velasco
 * Group Project Final Implementation
 * IT 306
 */
 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    abstract String stringWriter();
    abstract String reportStringWriter();
    
    public String toString(){
        String stringDate = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
        stringDate = sdfr.format(date);
        return "ID: " + userId + "\nDate: " + stringDate;
    }
}
