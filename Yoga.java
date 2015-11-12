/*
 * Class Yoga
 *
 * This class extends ExerciseActivity.
 * It contains a calculation for calories burned during the activity.
 * 
 * @author Daniel de Souza, Luis Velasco
 */

public class Yoga extends ExerciseActivity{
    private int duration;
    private double weight;
    
    public Yoga(){
        
    }
    public Yoga(int duration, double weight){
      this.duration = duration;
      this.weight = weight;
    }   
    
    public int getDuration(){
      return duration;
    }
    public double getWeight(){
      return weight;
    }
    
    /**
     * sets duration if input is valid. If invalid, then throw InvalidInputException.
     * @param duration
     * @throws InvalidInputException
     */
    public void setDuration(int duration) throws InvalidInputException{
      if(duration <= 0 || duration > 999){
         throw new InvalidInputException("Error: Duration must be between 1 and 999.");
      }
      this.duration = duration;
    }
    
    /**
     * sets weight if input is valid. If invalid, then throw InvalidInputException.
     * @param weight
     * @throws InvalidInputException
     */
    public void setWeight(double weight) throws InvalidInputException{
      if(weight <= 0 || weight > 999){
         throw new InvalidInputException("Error: Weight must be between 1 and 999.");
      }
      this.weight = weight;
    }
        
    public double calculateCaloriesBurned(){
         return (weight * .45359237) * 3 * (duration / 60);
    }
    
    public String toString(){
    	return "Duration: " + getDuration();
    }
}
