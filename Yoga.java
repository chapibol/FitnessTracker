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
    public void setDuration(int duration) throws InvalidInputException{
      if(duration <= 0 || duration > 999){
         throw new InvalidInputException("Error: Invalid entry.");
      }
      this.duration = duration;
    }
        
    public double calculateCaloriesBurned(){
         return 0.0;
    }
    
    public String toString(){
    	return "Duration: " + super.getDuration();
    }
}
