/*
 * Class Yoga
 *
 * This class extends ExerciseActivity.
 * It contains a calculation for calories burned during the activity.
 * 
 * @author Daniel de Souza, Luis Velasco
 * Group Project Final Implementation
 * IT 306
 */

public class Yoga extends ExerciseActivity{
    private int duration;
    private double weight;
    
    public Yoga(){
        
    }
    public Yoga(int duration, double weight){
      super();
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
         throw new InvalidInputException("Duration must be between 1 and 999.");
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
         throw new InvalidInputException("Weight must be between 1 and 999.");
      }
      this.weight = weight;
    }
        
    public double calculateCaloriesBurned(){
         return ((weight * 0.45359237) * 3) * ((double)duration / 60);
    }
    
    public String stringWriter(){
      return super.getUserId() + "," + super.getDate().getTime() + "," + Double.toString(weight) + "," + duration;
    }
    
    public String reportStringWriter(){
    	String name = "Yoga";
    	String spaceOffset = "                                            ";
    	String caloriesStr = String.format("%.2f", this.calculateCaloriesBurned());
    	return name + spaceOffset + caloriesStr + "\n";
    }
    
    public String toString(){
        return super.toString() + "\nYoga Duration: " + duration + " minutes(s)\nWeight: " + weight + " lbs\nCalories Burned: " + String.format("%.2f", calculateCaloriesBurned());
    }
}
