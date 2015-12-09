/*
 * Class InvalidInputException
 *
 * This class provides a custom exception for invalid input.
 * 
 * @author Daniel de Souza, Luis Velasco
 * Group Project Final Implementation
 * IT 306
 */
 
public class InvalidInputException extends Exception {

	public InvalidInputException(){
		
	}
	
	public InvalidInputException(String message){
		super(message);
	}
}
