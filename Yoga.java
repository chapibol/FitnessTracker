/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
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
    
    public void setDuration(int duration) throws InvalidInputException{
      if(duration <= 0 || duration > 999){
         throw new InvalidInputException("Error: Duration must be between 1 and 999.");
      }
      this.duration = duration;
    }
    public void setWeight() throws InvalidInputException{
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
