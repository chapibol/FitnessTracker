/*
 * Class Running
 *
 * This class extends ExerciseActivity.
 * It contains a calculation for calories burned during the activity.
 * 
 * @author Daniel de Souza, Luis Velasco
 * Group Project Final Implementation
 * IT 306
 */

public class Running extends ExerciseActivity{
    private double distance;
    private double weight;
    private int duration;
    
    public Running(){
        
    }
    
    public Running(double distance, double weight, int duration){
        super();
        this.distance = distance;
        this.weight = weight;
        this.duration = duration;
    }
    
    public double getDistance(){
        return distance;
    }
    
    public int getDuration(){
      return duration;
    }
    
    public double getWeight(){
      return weight;
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
    
    /**
     * sets distance if input is valid. If invalid, then throw InvalidInputException.
     * @param distance
     * @throws InvalidInputException
     */
    public void setDistance(double distance) throws InvalidInputException{
        if(distance <= 0 || distance > 999){
            throw new InvalidInputException("Distance must be between 1 and 999.");
        }
        this.distance = distance;
    }
    /**
     * sets duration if input is valid. If invalid, then throw InvalidInputException.
     * @param duration
     * @throws InvalidInputException
     */
    public void setDuration(int duration) throws InvalidInputException{
      if(duration <= 0 || duration > 999){
         throw new InvalidInputException("Distance must be between 1 and 999.");
      }
      this.duration = duration;
    }
    
    public double calculateCaloriesBurned(){
         return (weight * .75) * distance;
    }
    
    public String stringWriter(){
      return super.getUserId() + "," + super.getDate().getTime() + "," + distance  + "," + Double.toString(weight) + "," + duration;
    }
    
    public String reportStringWriter(){
    	String name = "Running";
    	String spaceOffset = "                                      ";//name + spaces = 45
    	String caloriesStr = String.format("%.2f", this.calculateCaloriesBurned());
    	return name + spaceOffset + caloriesStr + "\n";
    }
    
    public String toString(){
        return super.toString() + "\nRunning Distance: " + String.format("%.2f", distance) + " mile(s)\n" 
                                 + "Weight: " + weight+ " lbs\n" + "Duration: " + duration + " minute(s)" 
                                 + "\nCalories Burned: " + String.format("%.2f", calculateCaloriesBurned());
    }
    
}
