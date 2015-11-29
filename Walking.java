/*
 * Class Walking
 *
 * This class extends ExerciseActivity.
 * It contains a calculation for calories burned during the activity.
 * 
 * @author Daniel de Souza, Luis Velasco
   100,1447276622832,2,145,45
 */

public class Walking extends ExerciseActivity{
    private double distance;
    private double weight;
    private int duration;

    
    public Walking(){
        
    }
    public Walking(double distance){
        super();
        this.distance = distance;
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
         throw new InvalidInputException("Invalid entry.");
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
         return (weight * .53) * distance;
    }
    
    public String stringWriter(){
      return super.getUserId() + "," + super.getDate() + "," + distance  + "," + Double.toString(weight) + "," + duration;
    }
    
    public String toString(){
        return super.toString() + "\nWalking Distance: " + String.format("%.2f", distance) + " mile(s)\n" 
                                 + "Weight: " + weight+ " lbs\n" + "Duration: " + duration + " minute(s)" 
                                 + "\nCalories Burned: " + String.format("%.2f", calculateCaloriesBurned());
    }
}
