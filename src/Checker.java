import java.util.logging.*;

// Checker.java contains the original checkTask() and checkDate() methods.
// Both methods return boolean values.
// checkTask() uses checkDate()


public class Checker {
	
	private static final Logger logger = Logger.getLogger(Flex.class.getName());

	private static final String FIRST_COMMA_SPACE_MISSING_MESSAGE = "First comma and space are missing.";
	private static final String SECOND_COMMA_SPACE_MISSING_MESSAGE = "Second comma and space are missing.";
	private static final String THIRD_COMMA_SPACE_MISSING_MESSAGE = "Third comma and space are missing.";
	private static final String FOURTH_COMMA_SPACE_MISSING_MESSAGE = "Fourth comma and space are missing.";
	private static final String FIFTH_COMMA_SPACE_MISSING_MESSAGE = "Fifth comma and space are missing.";
	private static final String SIXTH_COMMA_SPACE_MISSING_MESSAGE = "Sixth comma and space are missing.";

	private static final String DATE_FIRST_SLASH_MISSING_MESSAGE = "First slash of date is missing.";
	private static final String DATE_SECOND_SLASH_MISSING_MESSAGE = "Second slash of date is missing.";

	private static final String DASH_IN_DAY_OF_DATE_MESSAGE = "At least one dash is in the date's day. Do take note that negative numbers are not allowed as well.";
	private static final String DAY_IN_DATE_MISSING_MESSAGE = "The day in the date is missing.";
	private static final String DAY_IN_DATE_NOT_A_NUMBER_MESSAGE = "The day in the date is not a number.";
	private static final String DAY_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE = "The day in the date should not have more than two digits.";
	private static final String DAY_IN_DATE_MORE_THAN_THIRTY_ONE_MESSAGE = "The day in the date is more than 31.";
	private static final String DAY_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE = "The day in the date is 0 or less than zero.";

	private static String DASH_IN_MONTH_OF_DATE_MESSAGE = "At least one dash is in the date's month. Do take note that negative numbers are not allowed as well.";
	private static String MONTH_IN_DATE_MISSING_MESSAGE = "The month in the date is missing.";
	private static String MONTH_IN_DATE_NOT_A_NUMBER_MESSAGE = "The month in the date is not a number.";
	private static String MONTH_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE = "The month in the date should not have more than two digits.";
	private static String MONTH_IN_DATE_MORE_THAN_TWELVE_MESSAGE = "The day in the date is more than 12.";
	private static String MONTH_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE = "The month in the date is 0 or less than zero.";

	private static final String DASH_IN_YEAR_OF_DATE_MESSAGE = "At least one dash is in the date's year. Do take note that negative numbers are not allowed as well.";
	private static final String YEAR_IN_DATE_MISSING_MESSAGE = "The year in the date is missing.";
	private static final String YEAR_IN_DATE_NOT_A_NUMBER_MESSAGE = "The year in the date is not a number.";
	private static final String YEAR_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE = "The year in the date is 0 or less than zero.";

	private static final String STARTING_TIME_MISSING_MESSAGE = "The starting time is missing. Do take note that the starting time follows the 4-digit twenty-four-hour format.";
	private static final String DASH_IN_STARTING_TIME_MESSAGE = "At least one dash is in the starting time. Do take note that negative numbers are not allowed as well. Also, do take note that the starting time follows the 4-digit twenty-four-hour format.";		
	private static final String STARTING_TIME_NOT_A_NUMBER_MESSAGE = "The starting time is not a number.";
	private static final String STARTING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE = "The starting time is not a 4-digit number. Do take note that the starting time follows the 4-digit twenty-four-hour format.";
	private static final String STARTING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE = "The starting time is a number which is greater than 2359 (11:59pm).";
	private static final String STARTING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE = "The hours of the starting time is greater than 23.";
	private static final String STARTING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE = "The minutes of the starting time is greater than 59.";

	private static final String ENDING_TIME_MISSING_MESSAGE = "The ending time is missing. Do take note that the ending time follows the 4-digit twenty-four-hour format.";
	private static final String DASH_IN_ENDING_TIME_MESSAGE = "At least one dash is in the ending time. Do take note that negative numbers are not allowed as well. Also, do take note that the ending time follows the 4-digit twenty-four-hour format.";	
	private static final String ENDING_TIME_NOT_A_NUMBER_MESSAGE = "The ending time is not a number.";
	private static final String ENDING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE = "The ending time is not a 4-digit number. Do take note that the ending time follows the 4-digit twenty-four-hour format.";
	private static final String ENDING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE = "The ending time is a number which is greater than 2359 (11:59pm).";
	private static final String ENDING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE = "The hours of the ending time is greater than 23.";
	private static final String ENDING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE = "The minutes of the ending time is greater than 59.";

	private static final String NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE = "The number of days, according to the month and year in this date, more than expected.";	



	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
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

	// checks the validity of the potential Task String	
	// and prints out error messages for only the first mistake made by the user, 
	// for the Task String
	static boolean checkTask(String taskString, FlexWindow flexWindow) {
			
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
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(FIRST_COMMA_SPACE_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(FIRST_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println(FIRST_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println();
				
			return false;
		}
			
		
		// tempTaskVariables[6] is the extracted date
		tempTaskVariables[0] = tempString.substring(0, commaWhitespaceIndex1).trim();
			
		// e.g. tempDateString is "27/9/2015"
			
		String tempDateString = tempTaskVariables[0];
			
		if(!checkDate(tempDateString, flexWindow)){
			return false;
		}
			
		assert(checkDate(tempDateString, flexWindow));
									
		// tempString.substring(commaWhitespaceIndex1 + 2) is 0011, 1100, title title1, description description2, priorityLevel 1, category unknown		
			
		tempString = tempString.substring(commaWhitespaceIndex1 + 2);

		// extracts the starting time
		int commaWhitespaceIndex2 = tempString.indexOf(", ");
							
		if(commaWhitespaceIndex2 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(SECOND_COMMA_SPACE_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(SECOND_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println(SECOND_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println();
				
			return false;
		}

		// tempTaskVariables[1] is the extracted starting time
		tempTaskVariables[1] = tempString.substring(0, commaWhitespaceIndex2).trim();
			
		// STARTING TIME
			
		// e.g. 0011
			
		// checks if the starting time is missing
		if(tempTaskVariables[1].length()==0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(STARTING_TIME_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");
				
			logger.finest(STARTING_TIME_MISSING_MESSAGE);
			System.out.println(STARTING_TIME_MISSING_MESSAGE);
			System.out.println();
				
			return false;				
		}
			
		// checks if there is a dash in the starting time
		if(tempTaskVariables[1].indexOf("-")>=0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DASH_IN_STARTING_TIME_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DASH_IN_STARTING_TIME_MESSAGE);
			System.out.println(DASH_IN_STARTING_TIME_MESSAGE);
			System.out.println();
				
			return false;							
		}
			
		// checks if the starting time is a number
		char[] tempCharArray4 = new char[tempTaskVariables[1].trim().length()];
		tempTaskVariables[1].trim().getChars(0, tempTaskVariables[1].trim().length(), tempCharArray4, 0);
		for(int l=0; l<tempTaskVariables[1].trim().length(); l++){
			if(!Character.isDigit(tempCharArray4[l])){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
					
				flexWindow.getTextArea().append(STARTING_TIME_NOT_A_NUMBER_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(STARTING_TIME_NOT_A_NUMBER_MESSAGE);
				System.out.println(STARTING_TIME_NOT_A_NUMBER_MESSAGE);
				System.out.println();
					
				return false;				
			}
		}				
		
		// checks if the starting time has four digits
		if(tempTaskVariables[1].length()!=4){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(STARTING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");
		
			logger.finest(STARTING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE);
			System.out.println(STARTING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE);
			System.out.println();
				
			return false;					
		}
			
		// checks if the hours for the starting time, is more than 23
		if(Integer.valueOf(tempTaskVariables[1].substring(0, 2))>23){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(STARTING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(STARTING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE);
			System.out.println(STARTING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE);
			System.out.println();
			
			return false;	
		}
		
		// checks if the minutes for the starting time, is more than 59
		if(Integer.valueOf(tempTaskVariables[1].substring(2, 4))>59){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");
				
			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(STARTING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(STARTING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE);
			System.out.println(STARTING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE);
			System.out.println();
				
			return false;	
		}
			
			
		// checks if the starting time is a number greater than 2359 (11:59pm)
		if(Integer.valueOf(tempTaskVariables[1])>2359){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(STARTING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(STARTING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE);
			System.out.println(STARTING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE);
			System.out.println();
				
			return false;	
		}
			
		// tempString.substring(commaWhitespaceIndex2 + 2) is 1100, title title1, description description2, priorityLevel 1, category unknown
			
		tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();
		
		// extracts the ending time
		int commaWhitespaceIndex3 = tempString.indexOf(", ");
			
		if(commaWhitespaceIndex3 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(THIRD_COMMA_SPACE_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(THIRD_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println(THIRD_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println();
			
			return false;
		}
			
		// tempTaskVariables[2] is the ending time
		tempTaskVariables[2] = tempString.substring(0, commaWhitespaceIndex3).trim();	
			
		// ENDING TIME
			
		// e.g. 1100
			
		// checks if the ending time is missing
		if(tempTaskVariables[2].length()==0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(ENDING_TIME_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");
				
			logger.finest(ENDING_TIME_MISSING_MESSAGE);
			System.out.println(ENDING_TIME_MISSING_MESSAGE);
			System.out.println();
				
			return false;				
		}
			
		// checks if there is a dash in the ending time
		if(tempTaskVariables[2].indexOf("-")>=0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DASH_IN_ENDING_TIME_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DASH_IN_ENDING_TIME_MESSAGE);
			System.out.println(DASH_IN_ENDING_TIME_MESSAGE);
			System.out.println();
				
			return false;							
		}
			
		// checks if the starting time is a number
		char[] tempCharArray5 = new char[tempTaskVariables[2].trim().length()];
		tempTaskVariables[2].trim().getChars(0, tempTaskVariables[2].trim().length(), tempCharArray5, 0);
		for(int m=0; m<tempTaskVariables[2].trim().length(); m++){
			if(!Character.isDigit(tempCharArray5[m])){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
					
				flexWindow.getTextArea().append(ENDING_TIME_NOT_A_NUMBER_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(ENDING_TIME_NOT_A_NUMBER_MESSAGE);
				System.out.println(ENDING_TIME_NOT_A_NUMBER_MESSAGE);
				System.out.println();
				
				return false;				
			}
		}				
		
		// checks if the ending time has four digits
		if(tempTaskVariables[2].length()!=4){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(ENDING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(ENDING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE);
			System.out.println(ENDING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE);
			System.out.println();
				
			return false;					
		}

		// checks if the hours for the ending time, is more than 23
		if(Integer.valueOf(tempTaskVariables[2].substring(0, 2))>23){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(ENDING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");
				
			logger.finest(ENDING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE);
			System.out.println(ENDING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE);
			System.out.println();
				
			return false;	
		}
			
		// checks if the minutes for the ending time, is more than 59
		if(Integer.valueOf(tempTaskVariables[2].substring(2, 4))>59){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(ENDING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(ENDING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE);
			System.out.println(ENDING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE);
			System.out.println();
				
			return false;	
		}
			
		// checks if the ending time is a number greater than 2359 (11:59pm)
		if(Integer.valueOf(tempTaskVariables[2])>2359){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(ENDING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(ENDING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE);
			System.out.println(ENDING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE);
			System.out.println();
			
			return false;	
		}
							
		// tempString.substring(commaWhitespaceIndex3 + 2) is title title1, description description2, priorityLevel 1, category unknown
		tempString = tempString.substring(commaWhitespaceIndex3 + 2).trim();
		
		// extracts the task title
		int commaWhitespaceIndex4 = tempString.indexOf(", ");
			
		if(commaWhitespaceIndex4 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");
				
			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(FOURTH_COMMA_SPACE_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(FOURTH_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println(FOURTH_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println();
				
			return false;
		}
			
		// tempTaskVariables[3] is the task title
		tempTaskVariables[3] = tempString.substring(0, commaWhitespaceIndex4).trim();
			
		// tempString.substring(commaWhitespaceIndex4 + 2) is description description2, priorityLevel 1, category unknown

		tempString = tempString.substring(commaWhitespaceIndex4 + 2).trim();
			
		// extracts the task description
		int commaWhitespaceIndex5 = tempString.indexOf(", ");
			
		if(commaWhitespaceIndex5 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(FIFTH_COMMA_SPACE_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(FIFTH_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println(FIFTH_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println();
				
			return false;
		}
			
		// tempTaskVariables[4] is the extracted task description
		tempTaskVariables[4] = tempString.substring(0, commaWhitespaceIndex5).trim();	
			
		// tempString.substring(commaWhitespaceIndex5 + 2) is priorityLevel 1, category unknown

		// extracts the priority level, and the category of the task
		int commaWhitespaceIndex6 = tempString.indexOf(", ");
			
		if(commaWhitespaceIndex6 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			flexWindow.getTextArea().append(SIXTH_COMMA_SPACE_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(SIXTH_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println(SIXTH_COMMA_SPACE_MISSING_MESSAGE);
			System.out.println();
				
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
	static boolean checkDate(String dateString, FlexWindow flexWindow){
		String tempDateString = dateString;
		
		int slashIndex1 = tempDateString.indexOf("/");
		
		// DAY IN DATE
		
		// e.g. 27
		
		// checks for the first slash in the date
		if(slashIndex1 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DATE_FIRST_SLASH_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DATE_FIRST_SLASH_MISSING_MESSAGE);
			System.out.println(DATE_FIRST_SLASH_MISSING_MESSAGE);
			System.out.println();
			
			return false;
		}
		
		// e.g. tempDateString.substring(0, slashIndex1) is "27"
		
		// checks for any missing of the day in the date	
		if(tempDateString.substring(0, slashIndex1).trim().length()==0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DAY_IN_DATE_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DAY_IN_DATE_MISSING_MESSAGE);
			System.out.println(DAY_IN_DATE_MISSING_MESSAGE);
			System.out.println();
			
			return false;			
		}
		
		// checks for any "-" (dash) in the day of the date
		if(tempDateString.substring(0, slashIndex1).trim().indexOf("-")>=0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DASH_IN_DAY_OF_DATE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DASH_IN_DAY_OF_DATE_MESSAGE);
			System.out.println(DASH_IN_DAY_OF_DATE_MESSAGE);
			System.out.println();
			
			return false;
		}

		// checks if the day in the date is a number
		char[] tempCharArray1 = new char[tempDateString.substring(0, slashIndex1).trim().length()];
		tempDateString.substring(0, slashIndex1).getChars(0, tempDateString.substring(0, slashIndex1).trim().length(), tempCharArray1, 0);
		for(int i=0; i<tempDateString.substring(0, slashIndex1).trim().length(); i++){
			if(!Character.isDigit(tempCharArray1[i])){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(DAY_IN_DATE_NOT_A_NUMBER_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(DAY_IN_DATE_NOT_A_NUMBER_MESSAGE);
				System.out.println(DAY_IN_DATE_NOT_A_NUMBER_MESSAGE);
				System.out.println();
				
				return false;				
			}
		}
		
		// checks if the day has more than two digits
		if(tempDateString.substring(0, slashIndex1).trim().length()>2){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DAY_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DAY_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE);
			System.out.println(DAY_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE);
			System.out.println();
			
			return false;				
		}
		
		// checks whether the day in the date is more than 31
		if(Integer.valueOf(tempDateString.substring(0, slashIndex1).trim())>31){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DAY_IN_DATE_MORE_THAN_THIRTY_ONE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DAY_IN_DATE_MORE_THAN_THIRTY_ONE_MESSAGE);
			System.out.println(DAY_IN_DATE_MORE_THAN_THIRTY_ONE_MESSAGE);
			System.out.println();
			
			return false;				
		}
		
		// checks whether the day in the date is 0, or less than zero
		if(Integer.valueOf(tempDateString.substring(0, slashIndex1).trim())<=0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DAY_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DAY_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE);
			System.out.println(DAY_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE);
			System.out.println();
			
			return false;				
		}
		
		int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());
		
		// e.g. tempDateString is "9/2015"
		
		tempDateString = tempDateString.substring(slashIndex1 + 1);		
						
		int slashIndex2 = tempDateString.indexOf("/");
		
		// checks for the second slash in the date
		if(slashIndex2 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DATE_SECOND_SLASH_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DATE_SECOND_SLASH_MISSING_MESSAGE);
			System.out.println(DATE_SECOND_SLASH_MISSING_MESSAGE);
			System.out.println();
			
			return false;
		}
							
		
		// MONTH IN DATE
		
		// e.g. tempDateString.substring(0, slashIndex2) is "9"		
		
		// checks for any missing of the month in the date	
		if(tempDateString.substring(0, slashIndex2).trim().length()==0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(MONTH_IN_DATE_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(MONTH_IN_DATE_MISSING_MESSAGE);
			System.out.println(MONTH_IN_DATE_MISSING_MESSAGE);
			System.out.println();
			
			return false;			
		}
		
		// checks for any "-" (dash) in the month of the date
		if(tempDateString.substring(0, slashIndex2).trim().indexOf("-")>=0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DASH_IN_MONTH_OF_DATE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DASH_IN_MONTH_OF_DATE_MESSAGE);
			System.out.println(DASH_IN_MONTH_OF_DATE_MESSAGE);
			System.out.println();
			
			return false;
		}

		// checks if the month in the date is a number
		char[] tempCharArray2 = new char[tempDateString.substring(0, slashIndex2).trim().length()];
		tempDateString.substring(0, slashIndex2).getChars(0, tempDateString.substring(0, slashIndex2).trim().length(), tempCharArray2, 0);
		for(int j=0; j<tempDateString.substring(0, slashIndex2).trim().length(); j++){
			if(!Character.isDigit(tempCharArray2[j])){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");
				
				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(MONTH_IN_DATE_NOT_A_NUMBER_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(MONTH_IN_DATE_NOT_A_NUMBER_MESSAGE);
				System.out.println(MONTH_IN_DATE_NOT_A_NUMBER_MESSAGE);
				System.out.println();
				
				return false;				
			}
		}
		
		// checks if the month has more than two digits
		if(tempDateString.substring(0, slashIndex2).trim().length()>2){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(MONTH_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(MONTH_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE);
			System.out.println(MONTH_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE);
			System.out.println();
			
			return false;				
		}
		
		// checks if the month is more than 12
		if(Integer.valueOf(tempDateString.substring(0, slashIndex2))>12){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(MONTH_IN_DATE_MORE_THAN_TWELVE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(MONTH_IN_DATE_MORE_THAN_TWELVE_MESSAGE);
			System.out.println(MONTH_IN_DATE_MORE_THAN_TWELVE_MESSAGE);
			System.out.println();
			
			return false;				
		}
		
		// checks if the month is 0, or less than 0
		if(Integer.valueOf(tempDateString.substring(0, slashIndex2))<=0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(MONTH_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(MONTH_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE);
			System.out.println(MONTH_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE);
			System.out.println();
			
			return false;				
		}
		
		int month = Integer.valueOf(tempDateString.substring(0, slashIndex2));
				
		// YEAR IN DATE
		
		// e.g. tempDateString.substring(slashIndex2 + 1) is "2015"
		
		// checks for any missing of the month in the date			
		if(tempDateString.substring(slashIndex2 + 1).trim().length()==0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(YEAR_IN_DATE_MISSING_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(YEAR_IN_DATE_MISSING_MESSAGE);
			System.out.println(YEAR_IN_DATE_MISSING_MESSAGE);
			System.out.println();
			
			return false;			
		}
		
		// checks for any "-" (dash) in the month of the date
		if(tempDateString.substring(slashIndex2 + 1).trim().indexOf("-")>=0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(DASH_IN_YEAR_OF_DATE_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(DASH_IN_YEAR_OF_DATE_MESSAGE);
			System.out.println(DASH_IN_YEAR_OF_DATE_MESSAGE);
			System.out.println();
			
			return false;
		}

		// checks if the year in the date is a number
		char[] tempCharArray3 = new char[tempDateString.substring(slashIndex2 + 1).trim().length()];
		tempDateString.substring(slashIndex2 + 1).getChars(0, tempDateString.substring(slashIndex2 + 1).trim().length(), tempCharArray3, 0);
		for(int k=0; k<tempDateString.substring(slashIndex2 + 1).trim().length(); k++){
			if(!Character.isDigit(tempCharArray3[k])){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(YEAR_IN_DATE_NOT_A_NUMBER_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(YEAR_IN_DATE_NOT_A_NUMBER_MESSAGE);
				System.out.println(YEAR_IN_DATE_NOT_A_NUMBER_MESSAGE);
				System.out.println();
				
				return false;				
			}
		}							
						
		// checks if the year is 0, or less than 0
		if(Integer.valueOf(tempDateString.substring(slashIndex2 + 1))<=0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(YEAR_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(YEAR_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE);
			System.out.println(YEAR_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE);
			System.out.println();
			
			return false;				
		}
		
		int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1));
		
		if((month==1)&&(day>JANUARY_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;
		}
		else if(month==2){
			boolean isLeapYear = (year%4==0);
			
			if((!isLeapYear)&&(day>FEBRUARY_DAYS)){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
				System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
				System.out.println();
				
				return false;				
			}
			else if((isLeapYear)&&(day>(FEBRUARY_DAYS + 1))){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
				System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
				System.out.println();
				
				return false;
			}
		}
		else if((month==3)&&(day>MARCH_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;			
		}
		else if((month==4)&&(day>APRIL_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;			
		}
		else if((month==5)&&(day>MAY_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;
		}
		else if((month==6)&&(day>JUNE_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;
		}
		else if((month==7)&&(day>JULY_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;
		}
		else if((month==8)&&(day>AUGUST_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;
		}
		else if((month==9)&&(day>SEPTEMBER_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;
		}
		else if((month==10)&&(day>OCTOBER_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;
		}
		else if((month==11)&&(day>NOVEMBER_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;
		}
		else if((month==12)&&(day>DECEMBER_DAYS)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println(NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE);
			System.out.println();
			
			return false;
		}
		
		
		return true;
	}

}