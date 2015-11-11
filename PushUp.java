/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Daniel
 */
public class PushUp extends ExerciseActivity{
   private int quantity;
   public final double CAL_PER_PUSHUP = .825;
   
   
    public PushUp(){
        
    }
    public PushUp(int quantity){
         this.quantity = quantity;
    }
    
    public int getQuantity(){
      return quantity;
   }
    
    public void setQuantity(int quantity) throws InvalidInputException{
      if(quantity < 0 || quantity > 999){
         throw new InvalidInputException("Error: Entry should be between 0 and 999");
      }
      this.quantity = quantity;
   }
   
    public double calculateCaloriesBurned(){
         
         return quantity * CAL_PER_PUSHUP;
    }
    
    public String toString(){
        return "";
    }
    
}
