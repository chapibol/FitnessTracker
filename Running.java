/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Daniel
 */
public class Running {
    private double distance;
    private double weight;
    private int duration;

    
    public Running(){
        
    }
    public Running(double distance, double weight, int duration){
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
    
    public void setWeight(double weight){
         this.weight = weight;
    }
    public void setDistance(double distance){
        this.distance = distance;
    }
    
    public void setDuration(int duration) throws InvalidInputException{
      if(duration <= 0 || duration > 999){
         throw new InvalidInputException("Error: Invalid entry.");
      }
      this.duration = duration;
    }
    
    public double calculateCaloriesBurned(){
         return (weight * .75) * distance;
    }
    
    public String toString(){
        return "";
    }
}
