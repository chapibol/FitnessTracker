     


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
    
    public void setUserId(int id){
        userId = id;
    }
    
    public void setWeight(double weight) throws InvalidInputException{
        if(weight <= 0 || weight > 999){
            throw new InvalidInputException("Error: Weight must be > 0 and <= 999.");
        }
        this.weight = weight;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
   public String toString(){
       return "ID: " + userId + " Weight: " + weight + " Date: " + date;
   }
}
