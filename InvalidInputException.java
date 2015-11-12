/*
 * Class InvalidInputException
 *
 * This class provides an exception for invalid input.
 * 
 * @author Daniel de Souza, Luis Velasco
 */
 
public class InvalidInputException extends Exception {

	public InvalidInputException(){
		
	}
	
	public InvalidInputException(String message){
		super(message);
	}
}
