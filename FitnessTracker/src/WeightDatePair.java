


import java.util.Date;
class WeightDatePair {
    private int userId;
    private double weight;
    private Date date;
    
//    public WeightDatePair(){
//        
//    }
    
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
    public void setWeight(double weight){
        this.weight = weight;
    }
    public void setDate(Date date){
        this.date = date;
    }
    
//    public String toString(){
//        return "ID: " + userId + " Weight: " + weight + " Date: " + date;
//    }
}
