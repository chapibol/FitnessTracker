/*
 * Class WeightDatePair
 *
 * This class allows the program to track weight for the fitness user.
 * 
 * 
 * @author Daniel de Souza, Luis Velasco
 * Group Project Final Implementation
 * IT 306
 */
 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class WeightDatePair {
    private int userId;
    private double weight;
    private Date date;
    
   public WeightDatePair(){
       
   }
   
   public WeightDatePair(int userId, double weight, Date date){
      this.userId = userId;
      this.weight = weight;
      this.date = date;
   }
    
    public int getUserId(){
        return userId;
    }
    public double getWeight(){
        return weight;
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
            throw new InvalidInputException("UserId must be between 100 and 999.");
        }
        this.userId = userId;
    }
    /**
     * sets weight if input is valid. If invalid, then throw InvalidInputException.
     * @param weight
     * @throws InvalidInputException
     */
    public void setWeight(double weight) throws InvalidInputException{
        if(weight <= 0 || weight > 999){
            throw new InvalidInputException("Weight must be > 0 and <= 999.");
        }
        this.weight = weight;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
    public String stringWriter(){

      return userId + "," + weight + "," + date.getTime();
    }
    
   public String toString(){
      String stringDate = null;
      SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
      stringDate = sdfr.format(date);
      return "ID: " + userId + "\nWeight: " + weight + "\nDate: " + stringDate;
   }
}
