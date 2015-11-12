/*
 * Class PullUp
 *
 * This class extends ExerciseActivity.
 * It contains a calculation for calories burned during the activity.
 * 
 * @author Daniel de Souza, Luis Velasco
 */

public class PullUp extends ExerciseActivity{
   private int quantity;
   public final double CAL_PER_PULLUP = 4;
   
   public PullUp(){
        
   }
   public PullUp(int quantity){
         super();
        this.quantity = quantity;
   }
   
   public int getQuantity(){
      return quantity;
   }
   
   /**
     * sets quantity if input is valid. If invalid, then throw InvalidInputException.
     * @param quantity
     * @throws InvalidInputException
     */
   public void setQuantity(int quantity) throws InvalidInputException{
      if(quantity < 0 || quantity > 999){
         throw new InvalidInputException("Error: Entry should be between 0 and 999");
      }
      this.quantity = quantity;
   }
   
   public double calculateCaloriesBurned(){
      return quantity * CAL_PER_PULLUP;
    }
    
    public String toString(){
        return "Pullup Reps: " + quantity + "\nCalories Burned: " + calculateCaloriesBurned();
    }
}
