// Checker.java contains the original checkTask() and checkDate() methods.
// Both methods return boolean values.
// checkTask() uses checkDate()


public class Checker {

	// that is, it is valid only if its starting time, or ending time, are NOT between the starting
	// and ending times of existing tasks which are NOT DONE YET

	// number of days in each month for non-leap years
	private static final int JANUARY_DAYS = 31;
	private static final int FEBRUARY_DAYS = 28;
	private static final int MARCH_DAYS = 31;
	private static final int APRIL_DAYS = 30;
	private static final int MAY_DAYS = 31;
	private static final int JUNE_DAYS = 30;
	private static final int JULY_DAYS = 31;
	private static final int AUGUST_DAYS = 31;
	private static final int SEPTEMBER_DAYS = 30;
	private static final int OCTOBER_DAYS = 31;
	private static final int NOVEMBER_DAYS = 30;
	private static final int DECEMBER_DAYS = 31;

	
	// checks a string containing the time, on whether it is in the required format
	static boolean checkTime(String time){
		
		String tempString = new String("");
		tempString = time.trim();
		// ENDING TIME
		
		// e.g. 1100
		
		// checks if the time is missing
		if(tempString.length()==0){

			return false;				
		}
		
		// checks if there is a dash in the time
		if(tempString.indexOf("-")>=0){
	
			return false;							
		}
			
		// checks if the time is a number
		char[] tempCharArray5 = new char[tempString.length()];
		tempString.getChars(0, tempString.length(), tempCharArray5, 0);

		
		for(int m=0; m<tempString.length(); m++){
			if(!Character.isDigit(tempCharArray5[m])){			

				return false;				
			}
		}				
	
		// checks if the time has four digits
		if(tempString.length()!=4){

			return false;					
		}

		// checks if the hours for the time, is more than 23
		if(Integer.valueOf(tempString.substring(0, 2))>23){

			return false;	
		}
		
		// checks if the minutes for the time, is more than 59
		if(Integer.valueOf(tempString.substring(2, 4))>59){

			return false;	
		}
		
		// checks if the time is a number greater than 2359 (11:59pm)
		if(Integer.valueOf(tempString)>2359){

			return false;	
		}
		
		return true;
	}
	
	// checks the validity of the potential Task String for a floating task
	// which is a task without a date, starting time and ending time, and without a category
	// it will therefore only have a title, a description, and a priority (level)
	// That means 3 terms.
	// and prints out error messages for only the first mistake made by the user, 
	// for the Task String 
	// Do take note that there will be 2 ", " (comma and whitespace String) separators as there are 4 terms
	static boolean checkFloatingTaskInput(String taskString){
		String tempString = new String("");
		// checks the full String
		tempString = taskString.trim();
		
		// checks for the first separator
		int commaWhitespaceIndex1 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex1 < 0){
			return false;
		}
		
		// checks if the term before the first separator is empty
		if(tempString.substring(0, commaWhitespaceIndex1).length()<=0){
			return false;
		}
		
		// checks the rest of the String after the first separator				
		tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();
		
		// checks for the second separator
		int commaWhitespaceIndex2 = tempString.indexOf(", ");
		
		
		if(commaWhitespaceIndex2 < 0){
			return false;
		}
		
		// checks if the term before the second separator is empty
		if(tempString.substring(0, commaWhitespaceIndex2).length()<=0){
			return false;
		}		
		
		// checks the rest of the String after the second separator 
		tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();
		
		// checks for the third separator
		int commaWhitespaceIndex3 = tempString.indexOf(", ");
		
		
		if(commaWhitespaceIndex3 >= 0){
			return false;
		}
		
		return true;
	}
	
	// checks the validity of a floating task AFTER it is added
	// i.e. check whether the Task object is a floating task
	static boolean checkCategoryFloatingTask(Task task){
		if(task.getCategory().equalsIgnoreCase("floating")){
			return true;
		}
		
		return false;
	}
	
	// checks the validity of the potential Task String for a deadline task
	// which is a task without a starting time and ending time, and without a category
	// it will therefore only have a date, an ending time, a title, a description, and a priority 
	// that means 5 terms
	// and prints out error messages for only the first mistake made by the user, 
	// for the Task String 
	// Do take note that there will be 4 ", " (comma and whitespace String) separators as there are 6 terms
	static boolean checkDeadlineTaskInput(String taskString){
		
		String tempString = new String("");
		tempString = taskString.trim();
		
		String[] tempTaskVariables = new String[5];

		// format e.g. 10/10/2015, 0000, title, description, priority
		
		// extracts the date
		int commaWhitespaceIndex1 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex1 < 0){
			
			return false;
		}
		
	
		// tempTaskVariables[0] is the extracted date
		tempTaskVariables[0] = tempString.substring(0, commaWhitespaceIndex1).trim();
		
		// e.g. tempDateString is "27/9/2015"
		
		String tempDateString = tempTaskVariables[0];
		
		if(!checkDate(tempDateString)){
			return false;
		}
		
		assert(checkDate(tempDateString));
		
		// tempString.substring(commaWhitespaceIndex1 + 2) is 1100, title title1, description description2, priorityLevel 1
		
		tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();
		
		
		int commaWhitespaceIndex2 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex2 < 0){

			return false;
		}
		
		
		// tempTaskVariables[1] is the ending time
		tempTaskVariables[1] = tempString.substring(0, commaWhitespaceIndex2).trim();	
		
		// ENDING TIME
		
		if(!Checker.checkTime(tempTaskVariables[1])){
			return false;
		}
		
		assert(Checker.checkTime(tempTaskVariables[1]));
								
		// tempString.substring(commaWhitespaceIndex2 + 2) is title title1, description description2, priorityLevel 1
		tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();
	
		// extracts the task title
		int commaWhitespaceIndex3 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex3 < 0){

			return false;
		}
		
		// tempTaskVariables[2] is the task title
		tempTaskVariables[2] = tempString.substring(0, commaWhitespaceIndex3).trim();
	
		
		// tempString.substring(commaWhitespaceIndex4 + 2) is description description2, priorityLevel 1, category unknown

		tempString = tempString.substring(commaWhitespaceIndex3 + 2).trim();
		
		// extracts the task description
		int commaWhitespaceIndex4 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex4 < 0){

			return false;
		}
		
		// tempTaskVariables[3] is the extracted task description
		tempTaskVariables[3] = tempString.substring(0, commaWhitespaceIndex4).trim();	
				
		if(tempString.substring(commaWhitespaceIndex4+2).length()<=0){
			return false;
		}
		
		tempString = tempString.substring(commaWhitespaceIndex4+2).trim();
		
		int commaWhitespaceIndex5 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex5 >= 0){
			return false;
		}
		
		// tempTaskVariables[4] is the priority
		tempTaskVariables[4] = tempString;
				
		return true;
		
	}
	
	
	// checks the validity of a floating task AFTER it is added
	// i.e. check whether the Task object is a floating task
	static boolean checkCategoryDeadlineTask(Task task){
		if(task.getCategory().equalsIgnoreCase("deadline")){
			return true;
		}
		
		return false;
	}

	
	// checks the validity of the potential Task String	for a USUAL (NORMAL) task
	// and prints out error messages for only the first mistake made by the user, 
	// for the Task String
	// This includes a date, a starting time, an ending time, a title, a description, a priority and a category
	// Do take note that there will be 6 ", " (comma and whitespace String) separators as there are 7 terms
	// This method can check for the Task's String form ONLY after it is created
	static boolean checkTask(String taskString) {
			
		String tempString = new String("");
		tempString = taskString.trim();
			
		String[] tempTaskVariables = new String[7];

		// for example
		// 27/9/2015, 0011, 1100, title title1, description description2, priorityLevel 1, category unknown
		// in the format
		// date, startingTime, endingTime, taskTitle, taskDescription, priorityLevel, category
		
		// 1) taskTitle, taskDescription, priorityLevel and category have any number of "words", and can have alphabets or digits
			
		// 2) date must have 2 slashes, and at least 1 digit for the day, month and year. Date is a String variable.
			
		// 3) the startingTime and endingTime must follow the 4-digit "24-hour" format. Both are String variables.
			
		// extracts the date
		int commaWhitespaceIndex1 = tempString.indexOf(", ");
			
		if(commaWhitespaceIndex1 < 0){

			return false;
		}
			
		
		// tempTaskVariables[0] is the extracted date
		tempTaskVariables[0] = tempString.substring(0, commaWhitespaceIndex1).trim();
			
		// e.g. tempDateString is "27/9/2015"
			
		String tempDateString = tempTaskVariables[0];
			
		if(!checkDate(tempDateString)){
			return false;
		}
			
		assert(checkDate(tempDateString));
									
		// tempString.substring(commaWhitespaceIndex1 + 2) is 0011, 1100, title title1, description description2, priorityLevel 1, category unknown		
			
		tempString = tempString.substring(commaWhitespaceIndex1 + 2);

		// extracts the starting time
		int commaWhitespaceIndex2 = tempString.indexOf(", ");
							
		if(commaWhitespaceIndex2 < 0){

			return false;
		}

		// tempTaskVariables[1] is the extracted starting time
		tempTaskVariables[1] = tempString.substring(0, commaWhitespaceIndex2).trim();
		
		// STARTING TIME
		
		if(!Checker.checkTime(tempTaskVariables[1])){
			return false;
		}
		
		assert(Checker.checkTime(tempTaskVariables[1]));
		
		tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();
		
		// extracts the ending time
		int commaWhitespaceIndex3 = tempString.indexOf(", ");
			
		if(commaWhitespaceIndex3 < 0){

			return false;
		}
			
		// tempTaskVariables[2] is the ending time
		tempTaskVariables[2] = tempString.substring(0, commaWhitespaceIndex3).trim();	

		// ENDING TIME
		
		if(!Checker.checkTime(tempTaskVariables[2])){
			return false;
		}
		
		assert(Checker.checkTime(tempTaskVariables[2]));
			
		// tempString.substring(commaWhitespaceIndex3 + 2) is title title1, description description2, priorityLevel 1, category unknown
		tempString = tempString.substring(commaWhitespaceIndex3 + 2).trim();
		
		// extracts the task title
		int commaWhitespaceIndex4 = tempString.indexOf(", ");
			
		if(commaWhitespaceIndex4 < 0){

			return false;
		}
			
		// tempTaskVariables[3] is the task title
		tempTaskVariables[3] = tempString.substring(0, commaWhitespaceIndex4).trim();
		
		// tempString.substring(commaWhitespaceIndex4 + 2) is description description2, priorityLevel 1, category unknown

		tempString = tempString.substring(commaWhitespaceIndex4 + 2).trim();
			
		// extracts the task description
		int commaWhitespaceIndex5 = tempString.indexOf(", ");
			
		if(commaWhitespaceIndex5 < 0){
				
			return false;
		}
			
		// tempTaskVariables[4] is the extracted task description
		tempTaskVariables[4] = tempString.substring(0, commaWhitespaceIndex5).trim();	
		
		// tempString.substring(commaWhitespaceIndex5 + 2) is priorityLevel 1, category unknown
		tempString = tempString.substring(commaWhitespaceIndex5 + 2).trim();

		// extracts the priority level, and the category of the task
		int commaWhitespaceIndex6 = tempString.indexOf(", ");
			
		if(commaWhitespaceIndex6 < 0){

			return false;
		}
			
		// tempTaskVariables[5] is the extracted priority level
		tempTaskVariables[5] = tempString.substring(0, commaWhitespaceIndex6).trim();
			
		// tempString.substring(commaWhitespaceIndex6 + 2) category unknown
		tempString = tempString.substring(commaWhitespaceIndex6 + 2).trim();
			
		
			
		// tempTaskVariables[6] is the extracted category
		tempTaskVariables[6] = tempString.trim();	
		
		return true;
	}
	
	
	/// checks the validity of the date,
	// not perfectly for the day,
	// but perfectly for the month and the year
	static boolean checkDate(String dateString){
		String tempDateString = dateString;
		
		int slashIndex1 = tempDateString.indexOf("/");
		
		// DAY IN DATE
		
		// e.g. 27
		
		// checks for the first slash in the date
		if(slashIndex1 < 0){
			
			return false;
		}
		
		// e.g. tempDateString.substring(0, slashIndex1) is "27"
		
		// checks for any missing of the day in the date	
		if(tempDateString.substring(0, slashIndex1).trim().length()==0){

			return false;			
		}
		
		// checks for any "-" (dash) in the day of the date
		if(tempDateString.substring(0, slashIndex1).trim().indexOf("-")>=0){

			return false;
		}

		// checks if the day in the date is a number
		char[] tempCharArray1 = new char[tempDateString.substring(0, slashIndex1).trim().length()];
		tempDateString.substring(0, slashIndex1).getChars(0, tempDateString.substring(0, slashIndex1).trim().length(), tempCharArray1, 0);
		for(int i=0; i<tempDateString.substring(0, slashIndex1).trim().length(); i++){
			if(!Character.isDigit(tempCharArray1[i])){
				
				return false;				
			}
		}
		
		// checks if the day has more than two digits
		if(tempDateString.substring(0, slashIndex1).trim().length()>2){

			return false;				
		}
		
		// checks whether the day in the date is more than 31
		if(Integer.valueOf(tempDateString.substring(0, slashIndex1).trim())>31){

			return false;				
		}
		
		// checks whether the day in the date is 0, or less than zero
		if(Integer.valueOf(tempDateString.substring(0, slashIndex1).trim())<=0){

			return false;				
		}
		
		int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());
		
		// e.g. tempDateString is "9/2015"
		
		tempDateString = tempDateString.substring(slashIndex1 + 1);		
						
		int slashIndex2 = tempDateString.indexOf("/");
		
		// checks for the second slash in the date
		if(slashIndex2 < 0){

			return false;
		}
							
		
		// MONTH IN DATE
		
		// e.g. tempDateString.substring(0, slashIndex2) is "9"		
		
		// checks for any missing of the month in the date	
		if(tempDateString.substring(0, slashIndex2).trim().length()==0){

			return false;			
		}
		
		// checks for any "-" (dash) in the month of the date
		if(tempDateString.substring(0, slashIndex2).trim().indexOf("-")>=0){

			return false;
		}

		// checks if the month in the date is a number
		char[] tempCharArray2 = new char[tempDateString.substring(0, slashIndex2).trim().length()];
		tempDateString.substring(0, slashIndex2).getChars(0, tempDateString.substring(0, slashIndex2).trim().length(), tempCharArray2, 0);
		for(int j=0; j<tempDateString.substring(0, slashIndex2).trim().length(); j++){
			if(!Character.isDigit(tempCharArray2[j])){

				return false;				
			}
		}
		
		// checks if the month has more than two digits
		if(tempDateString.substring(0, slashIndex2).trim().length()>2){
			
			return false;				
		}
		
		// checks if the month is more than 12
		if(Integer.valueOf(tempDateString.substring(0, slashIndex2))>12){

			return false;				
		}
		
		// checks if the month is 0, or less than 0
		if(Integer.valueOf(tempDateString.substring(0, slashIndex2))<=0){

			return false;				
		}
		
		int month = Integer.valueOf(tempDateString.substring(0, slashIndex2));
				
		// YEAR IN DATE
		
		// e.g. tempDateString.substring(slashIndex2 + 1) is "2015"
		
		// checks for any missing of the month in the date			
		if(tempDateString.substring(slashIndex2 + 1).trim().length()==0){

			return false;			
		}
		
		// checks for any "-" (dash) in the month of the date
		if(tempDateString.substring(slashIndex2 + 1).trim().indexOf("-")>=0){

			return false;
		}

		// checks if the year in the date is a number
		char[] tempCharArray3 = new char[tempDateString.substring(slashIndex2 + 1).trim().length()];
		tempDateString.substring(slashIndex2 + 1).getChars(0, tempDateString.substring(slashIndex2 + 1).trim().length(), tempCharArray3, 0);
		for(int k=0; k<tempDateString.substring(slashIndex2 + 1).trim().length(); k++){
			if(!Character.isDigit(tempCharArray3[k])){
				
				return false;				
			}
		}							
						
		// checks if the year is 0, or less than 0
		if(Integer.valueOf(tempDateString.substring(slashIndex2 + 1))<=0){

			return false;				
		}
		
		int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1));
		
		if((month==1)&&(day>JANUARY_DAYS)){
			
			return false;
		}
		else if(month==2){
			boolean isLeapYear = (year%4==0);
			
			if((!isLeapYear)&&(day>FEBRUARY_DAYS)){
				
				return false;				
			}
			else if((isLeapYear)&&(day>(FEBRUARY_DAYS + 1))){

				return false;
			}
		}
		else if((month==3)&&(day>MARCH_DAYS)){
			
			return false;			
		}
		else if((month==4)&&(day>APRIL_DAYS)){
			
			return false;			
		}
		else if((month==5)&&(day>MAY_DAYS)){
			
			return false;
		}
		else if((month==6)&&(day>JUNE_DAYS)){
			
			return false;
		}
		else if((month==7)&&(day>JULY_DAYS)){
			
			return false;
		}
		else if((month==8)&&(day>AUGUST_DAYS)){
			
			return false;
		}
		else if((month==9)&&(day>SEPTEMBER_DAYS)){
			
			return false;
		}
		else if((month==10)&&(day>OCTOBER_DAYS)){
			
			return false;
		}
		else if((month==11)&&(day>NOVEMBER_DAYS)){

			return false;
		}
		else if((month==12)&&(day>DECEMBER_DAYS)){
			
			return false;
		}
		
		
		return true;
	}

	// user input command for adding a floating task is
	// add title, description, priority
	// the new task string, when a floating task is initially added, is:
	// undefined, undefined, undefined, title, description, priority, floating
	static boolean checkFloatingTaskOutput(String taskInformation) {
		String remainingString = new String("");
		remainingString = taskInformation.trim();
		
		String[] taskVariables = new String[7];
		
		// extracts the date
		int commaWhitespaceIndex1 = remainingString.indexOf(", ");
		
		if(commaWhitespaceIndex1 < 0){
			return false;
		}
		
		taskVariables[0] = remainingString.substring(0, commaWhitespaceIndex1).trim();
		
		// check if the date is "undefined"
		if(taskVariables[0].equalsIgnoreCase("undefined")){
			return true;
		}
		
		remainingString = remainingString.substring(commaWhitespaceIndex1 + 2).trim();

		// extracts the starting time
		int commaWhitespaceIndex2 = remainingString.indexOf(", ");

		if(commaWhitespaceIndex2 < 0){
			return false;
		}
		
		taskVariables[1] = remainingString.substring(0, commaWhitespaceIndex2).trim();		
		
		remainingString = remainingString.substring(commaWhitespaceIndex2 + 2).trim();

		// extracts the ending time
		int commaWhitespaceIndex3 = remainingString.indexOf(", ");
		
		if(commaWhitespaceIndex3 < 0){
			return false;
		}
		
		taskVariables[2] = remainingString.substring(0, commaWhitespaceIndex3).trim();
		
		// check if the ending time is "undefined"
		if(taskVariables[2].equalsIgnoreCase("undefined")){
			return true;
		}
		
		remainingString = remainingString.substring(commaWhitespaceIndex3 + 2).trim();

		// extracts the task title
		int commaWhitespaceIndex4 = remainingString.indexOf(", ");
		
		if(commaWhitespaceIndex4 < 0){
			return false;
		}
	
		taskVariables[3] = remainingString.substring(0, commaWhitespaceIndex4).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex4 + 2).trim();
	
		// extracts the task description
		int commaWhitespaceIndex5 = remainingString.indexOf(", ");
		
		if(commaWhitespaceIndex5 < 0){
			return false;
		}
	
		taskVariables[4] = remainingString.substring(0, commaWhitespaceIndex5).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex5 + 2).trim();

		// extracts the priority level, and the category of the task
		int commaWhitespaceIndex6 = remainingString.indexOf(", ");
		
		if(commaWhitespaceIndex6 < 0){
			return false;
		}
	
		taskVariables[5] = remainingString.substring(0, commaWhitespaceIndex6).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex6 + 2).trim();
		taskVariables[6] = remainingString.trim();		
		
		return false;

	}

	// user input command for adding a deadline task is
	// add 11/1/1111, 0021, title, description, priority
	// the new task string, when a floating task is initially added, is:
	// 11/1/1111, undefined, 0021, title, description, priority, deadline
	static boolean checkDeadlineTaskOutput(String taskInformation) {
		String remainingString = new String("");
		remainingString = taskInformation.trim();
		
		String[] taskVariables = new String[7];
		
		// extracts the date
		int commaWhitespaceIndex1 = remainingString.indexOf(", ");
		
		if(commaWhitespaceIndex1 < 0){
			return false;
		}

		taskVariables[0] = remainingString.substring(0, commaWhitespaceIndex1).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex1 + 2).trim();

		// extracts the starting time
		int commaWhitespaceIndex2 = remainingString.indexOf(", ");
		
		if(commaWhitespaceIndex2 < 0){
			return false;
		}
		
		taskVariables[1] = remainingString.substring(0, commaWhitespaceIndex2).trim();
		
		// check if the starting time is "undefined"
		if(taskVariables[1].equalsIgnoreCase("undefined")){
			return true;
		}
		
		remainingString = remainingString.substring(commaWhitespaceIndex2 + 2).trim();

		int commaWhitespaceIndex3 = remainingString.indexOf(", ");

		if(commaWhitespaceIndex3 < 0){
			return false;
		}
		// extracts the ending time
		taskVariables[2] = remainingString.substring(0, commaWhitespaceIndex3).trim();
		
		// check the format of the ending time
		if(!checkTime(taskVariables[2])){
			return false;
		}
		
		assert(checkTime(taskVariables[2]));
		
		remainingString = remainingString.substring(commaWhitespaceIndex3 + 2).trim();

		// extracts the task title
		int commaWhitespaceIndex4 = remainingString.indexOf(", ");
	
		if(commaWhitespaceIndex4 < 0){
			return false;
		}
		
		taskVariables[3] = remainingString.substring(0, commaWhitespaceIndex4).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex4 + 2).trim();
	
		// extracts the task description
		int commaWhitespaceIndex5 = remainingString.indexOf(", ");
	
		if(commaWhitespaceIndex5 < 0){
			return false;
		}
		
		taskVariables[4] = remainingString.substring(0, commaWhitespaceIndex5).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex5 + 2).trim();

		// extracts the priority level, and the category of the task
		int commaWhitespaceIndex6 = remainingString.indexOf(", ");
	
		if(commaWhitespaceIndex6 < 0){
			return false;
		}
		
		taskVariables[5] = remainingString.substring(0, commaWhitespaceIndex6).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex6 + 2).trim();
		taskVariables[6] = remainingString.trim();		
		
		return false;
	}

}
