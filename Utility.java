
public class Utility {
	/**
	 * Method that checks to see if a string input is valid. checks for null value, and empty strings
	 * as well as empty spaces
	 * @param strValue
	 * @return
	 */
	public static boolean isStringDataValid(String strValue){
		boolean isValid = true;
		boolean isStrEmpty = true;
		//check for an null variable being passed in
		if(strValue == null){
			return false;
		}
		//check to see strValue is not an empty string
		if(strValue.length() > 0 ){
			//check to make sure strValue is not full of empty spaces.
			for(int i = 0; i < strValue.length(); i++){
				if(!(strValue.charAt(i) == ' ')){
					isStrEmpty = false;
					break;
				}
			}
			if(isStrEmpty){
				isValid = false;
			}
		}else{
			isValid = false;
		}
		return isValid;
	}
	/**
	 * Method to check if password is valid (not empty and at least 4 characters)
	 * @param password
	 * @return true or false
	 */
	public static boolean isPasswordValid(String password){
		return isStringDataValid(password) && password.length() >= 4;
	}
}
