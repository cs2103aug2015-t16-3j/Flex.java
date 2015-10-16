// Checker.java contains the original checkTask() and checkDate() methods.
// Both methods return boolean values.
// checkTask() uses checkDate()

public class Checker {

	// that is, it is valid only if its starting time, or ending time, are NOT
	// between the starting
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

	private static final String DONE_STRING = "[done]";

	// checks a string containing the time, on whether it is in the required
	// format
	static boolean isValidTime(String time) {

		String tempString = new String("");
		tempString = time.trim();
		
		// ENDING TIME

		// e.g. 1100

		// checks if the time is missing
		if (tempString.length() == 0) {

			return false;
		}

		// checks if there is a dash in the time
		if (tempString.indexOf("-") >= 0) {

			return false;
		}

		// checks if the time is a number
		char[] tempCharArray5 = new char[tempString.length()];
		tempString.getChars(0, tempString.length(), tempCharArray5, 0);

		for (int m = 0; m < tempString.length(); m++) {
			if (!Character.isDigit(tempCharArray5[m])) {

				return false;
			}
		}

		// checks if the time has four digits
		if (tempString.length() != 4) {

			return false;
		}

		// checks if the hours for the time, is more than 23
		if (Integer.valueOf(tempString.substring(0, 2)) > 23) {

			return false;
		}

		// checks if the minutes for the time, is more than 59
		if (Integer.valueOf(tempString.substring(2, 4)) > 59) {

			return false;
		}

		// checks if the time is a number greater than 2359 (11:59pm)
		if (Integer.valueOf(tempString) > 2359) {

			return false;
		}

		return true;
	}

	/// checks the validity of the date,
	// not perfectly for the day,
	// but perfectly for the month and the year
	static boolean isValidDate(String dateString) {
		String tempDateString = dateString;

		int slashIndex1 = tempDateString.indexOf("/");

		// DAY IN DATE

		// e.g. 27

		// checks for the first slash in the date
		if (slashIndex1 < 0) {

			return false;
		}

		// e.g. tempDateString.substring(0, slashIndex1) is "27"

		// checks for any missing of the day in the date
		if (tempDateString.substring(0, slashIndex1).trim().length() == 0) {

			return false;
		}

		// checks for any "-" (dash) in the day of the date
		if (tempDateString.substring(0, slashIndex1).trim().indexOf("-") >= 0) {

			return false;
		}

		// checks if the day in the date is a number
		char[] tempCharArray1 = new char[tempDateString.substring(0, slashIndex1).trim().length()];
		tempDateString.substring(0, slashIndex1).getChars(0, tempDateString.substring(0, slashIndex1).trim().length(),
				tempCharArray1, 0);
		for (int i = 0; i < tempDateString.substring(0, slashIndex1).trim().length(); i++) {
			if (!Character.isDigit(tempCharArray1[i])) {

				return false;
			}
		}

		// checks if the day has more than two digits
		if (tempDateString.substring(0, slashIndex1).trim().length() > 2) {

			return false;
		}

		// checks whether the day in the date is more than 31
		if (Integer.valueOf(tempDateString.substring(0, slashIndex1).trim()) > 31) {

			return false;
		}

		// checks whether the day in the date is 0, or less than zero
		if (Integer.valueOf(tempDateString.substring(0, slashIndex1).trim()) <= 0) {

			return false;
		}

		int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());

		// e.g. tempDateString is "9/2015"

		tempDateString = tempDateString.substring(slashIndex1 + 1);

		int slashIndex2 = tempDateString.indexOf("/");

		// checks for the second slash in the date
		if (slashIndex2 < 0) {

			return false;
		}

		// MONTH IN DATE

		// e.g. tempDateString.substring(0, slashIndex2) is "9"

		// checks for any missing of the month in the date
		if (tempDateString.substring(0, slashIndex2).trim().length() == 0) {

			return false;
		}

		// checks for any "-" (dash) in the month of the date
		if (tempDateString.substring(0, slashIndex2).trim().indexOf("-") >= 0) {

			return false;
		}

		// checks if the month in the date is a number
		char[] tempCharArray2 = new char[tempDateString.substring(0, slashIndex2).trim().length()];
		tempDateString.substring(0, slashIndex2).getChars(0, tempDateString.substring(0, slashIndex2).trim().length(),
				tempCharArray2, 0);
		for (int j = 0; j < tempDateString.substring(0, slashIndex2).trim().length(); j++) {
			if (!Character.isDigit(tempCharArray2[j])) {

				return false;
			}
		}

		// checks if the month has more than two digits
		if (tempDateString.substring(0, slashIndex2).trim().length() > 2) {

			return false;
		}

		// checks if the month is more than 12
		if (Integer.valueOf(tempDateString.substring(0, slashIndex2)) > 12) {

			return false;
		}

		// checks if the month is 0, or less than 0
		if (Integer.valueOf(tempDateString.substring(0, slashIndex2)) <= 0) {

			return false;
		}

		int month = Integer.valueOf(tempDateString.substring(0, slashIndex2));

		// YEAR IN DATE

		// e.g. tempDateString.substring(slashIndex2 + 1) is "2015"

		// checks for any missing of the month in the date
		if (tempDateString.substring(slashIndex2 + 1).trim().length() == 0) {

			return false;
		}

		// checks for any "-" (dash) in the month of the date
		if (tempDateString.substring(slashIndex2 + 1).trim().indexOf("-") >= 0) {

			return false;
		}

		// checks if the year in the date is a number
		char[] tempCharArray3 = new char[tempDateString.substring(slashIndex2 + 1).trim().length()];
		tempDateString.substring(slashIndex2 + 1).getChars(0, tempDateString.substring(slashIndex2 + 1).trim().length(),
				tempCharArray3, 0);
		for (int k = 0; k < tempDateString.substring(slashIndex2 + 1).trim().length(); k++) {
			if (!Character.isDigit(tempCharArray3[k])) {

				return false;
			}
		}

		// checks if the year is 0, or less than 0
		if (Integer.valueOf(tempDateString.substring(slashIndex2 + 1)) <= 0) {

			return false;
		}

		int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1));

		if ((month == 1) && (day > JANUARY_DAYS)) {

			return false;
		} else if (month == 2) {
			boolean isLeapYear = (year % 4 == 0);

			if ((!isLeapYear) && (day > FEBRUARY_DAYS)) {

				return false;
			} else if ((isLeapYear) && (day > (FEBRUARY_DAYS + 1))) {

				return false;
			}
		} else if ((month == 3) && (day > MARCH_DAYS)) {

			return false;
		} else if ((month == 4) && (day > APRIL_DAYS)) {

			return false;
		} else if ((month == 5) && (day > MAY_DAYS)) {

			return false;
		} else if ((month == 6) && (day > JUNE_DAYS)) {

			return false;
		} else if ((month == 7) && (day > JULY_DAYS)) {

			return false;
		} else if ((month == 8) && (day > AUGUST_DAYS)) {

			return false;
		} else if ((month == 9) && (day > SEPTEMBER_DAYS)) {

			return false;
		} else if ((month == 10) && (day > OCTOBER_DAYS)) {

			return false;
		} else if ((month == 11) && (day > NOVEMBER_DAYS)) {

			return false;
		} else if ((month == 12) && (day > DECEMBER_DAYS)) {

			return false;
		}

		return true;
	}

	// makes sure that a floating task's input String, will not have ";"
	// (semicolons)
	// as they are used as separators (not needed by a floating task)
	// format: <taskname>
	static boolean isFloatingTaskInput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int semicolonIndex = tempString.indexOf(";");
		if (semicolonIndex >= 0) {
			return false;
		}
		
		return true;

	}

	// done floating task (user input command or file storage format)
	static boolean isDoneFloatingTaskInput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int doneStringIndex = tempString.indexOf(DONE_STRING);
		
		if(doneStringIndex < 0){
			return false;
		}
		
		tempString = tempString.substring(0, doneStringIndex).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(!Checker.isFloatingTaskInput(tempString)){
			return false;
		}
		
		return true;
	}
	
	// makes sure that a floating task's display(output on the GUI) String, will
	// not have ";" (semicolons)
	// as they are used as separators (not needed by a floating task)
	// format: <taskname>
	static boolean isFloatingTaskOutput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int semicolonIndex = tempString.indexOf(";");
		if (semicolonIndex >= 0) {
			return false;
		}
		return true;
	}

	// makes sure that a floating task is done (GUI Display form)
	// format: <taskname> [done]
	static boolean isDoneFloatingTaskOutput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int doneStringIndex = tempString.indexOf(DONE_STRING);
		
		if(doneStringIndex < 0){
			return false;
		}
		
		tempString = tempString.substring(0, doneStringIndex).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(!Checker.isFloatingTaskOutput(tempString)){
			return false;
		}
		
		return true;
	}
	
	//checks if the input of a deadline is acceptable
	// <taskname>; by <end> on <date>
	static boolean isDeadlineTaskInput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int semicolonWhitespaceIndex1 = tempString.indexOf("; ");
		
		if(semicolonWhitespaceIndex1 <= 0){
			return false;
		}
		
		String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();
		
		if(taskName.length()==0){
			return false;
		}
		
		tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int byWhitespaceIndex1 = tempString.toLowerCase().indexOf("by ");
		
		if(byWhitespaceIndex1 < 0){
			return false;
		}
		
		tempString = tempString.substring(byWhitespaceIndex1 + 3).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int whitespaceOnWhitespaceIndex1 = tempString.toLowerCase().indexOf(" on ");
		
		if(whitespaceOnWhitespaceIndex1 < 0){
			return false;
		}
		
		String endingTime = tempString.substring(0, whitespaceOnWhitespaceIndex1);
		
		if(!Checker.isValidTime(endingTime)){
			return false;
		}
		
		tempString = tempString.substring(whitespaceOnWhitespaceIndex1 + 4).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(tempString.length()==0){
			return false;
		}
		
		String date = tempString.trim();
		
		if(date.length()==0){
			return false;
		}
		
		if(!Checker.isValidDate(date)){
			return false;
		}
		
		return true;
		
	}
	
	// check the validity of file storage and/or user input version of a done deadline task
	static boolean isDoneDeadlineTaskInput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int doneStringIndex = tempString.indexOf(DONE_STRING);
		
		if(doneStringIndex < 0){
			return false;
		}
		
		tempString = tempString.substring(0, doneStringIndex).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(!Checker.isDeadlineTaskInput(tempString)){
			return false;
		}
		
		return true;
	}
    
	// <taskname>, by <end> on <date>
	static boolean isDeadlineTaskOutput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int commaWhitespaceIndex1 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex1 <= 0){
			return false;
		}
		
		String taskName = tempString.substring(0, commaWhitespaceIndex1).trim();
		
		if(taskName.length()==0){
			return false;
		}
		
		tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int byWhitespaceIndex1 = tempString.toLowerCase().indexOf("by ");
		
		if(byWhitespaceIndex1 < 0){
			return false;
		}
		
		tempString = tempString.substring(byWhitespaceIndex1 + 3).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int whitespaceOnWhitespaceIndex1 = tempString.toLowerCase().indexOf(" on ");
		
		if(whitespaceOnWhitespaceIndex1 < 0){
			return false;
		}
		
		String endingTime = tempString.substring(0, whitespaceOnWhitespaceIndex1);
		
		if(!Checker.isValidTime(endingTime)){
			return false;
		}
		
		tempString = tempString.substring(whitespaceOnWhitespaceIndex1 + 4).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(tempString.length()==0){
			return false;
		}
		
		String date = tempString.trim();
		
		if(date.length()==0){
			return false;
		}
		
		if(!Checker.isValidDate(date)){
			return false;
		}
		
		return true;
	}
	
	// check the validity of the GUI display of a done deadline task
	// <taskname>, by <end> on <date> [done]
	static boolean isDoneDeadlineTaskOutput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int doneStringIndex = tempString.indexOf(DONE_STRING);
		
		if(doneStringIndex < 0){
			return false;
		}
		
		tempString = tempString.substring(0, doneStringIndex).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(!Checker.isDeadlineTaskOutput(tempString)){
			return false;
		}
		
		return true;
	}

	// recurring task user input command (not done)
	// <taskname>; <start>-<end> every <day>; priority
	static boolean isRecurringTaskInput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int semicolonWhitespaceIndex1 = tempString.indexOf("; ");
		
		if(semicolonWhitespaceIndex1 <= 0){
			return false;
		}

		String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();
		
		if(taskName.length()==0){
			return false;
		}
		
		tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();
		
		if(tempString.length()==0){
			return false;
		}

		int dashIndex1 = tempString.indexOf("-");
		
		if(dashIndex1 < 0){
			return false;
		}
		
		String startingTime = tempString.substring(0, dashIndex1).trim();
		
		if(startingTime.length()!=4){
			return false;
		}
		
		if(!isValidTime(startingTime)){
			return false;
		}
		
		tempString = tempString.substring(dashIndex1 + 1).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int whitespaceEveryIndex = tempString.toLowerCase().indexOf(" every");
		
		if(whitespaceEveryIndex < 0){
			return false;
		}
		
		String endingTime = tempString.substring(0, whitespaceEveryIndex);

		if(endingTime.length() !=  4){
			return false;
		}
		
		if(!isValidTime(endingTime)){
			return false;
		}
		
		tempString = tempString.substring(whitespaceEveryIndex + 6).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int semicolonWhitespaceIndex2 = tempString.indexOf("; ");
		
		if(semicolonWhitespaceIndex2 < 0){
			return false;
		}
		
		String day = tempString.substring(0, semicolonWhitespaceIndex2).trim();

		// monday, tuesday, wednesday, thursday, friday, saturday, sunday
		if(day.length() == 0){
			return false;
		}
		
		if(!Checker.isValidDay(day)){
			return false;
		}
		
		tempString = tempString.substring(semicolonWhitespaceIndex2 + 2);
		
		if(tempString.length() == 0){
			return false;
		}
		
		String priority = tempString;

		if(priority.length()==0){
			return false;
		}
		
		return true;		
		
	}
	
	// done recurring task (user input command or file storage format)
	static boolean isDoneRecurringTaskInput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int doneStringIndex = tempString.indexOf(DONE_STRING);
		
		if(doneStringIndex < 0){
			return false;
		}
		
		tempString = tempString.substring(0, doneStringIndex).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(!Checker.isRecurringTaskInput(tempString)){
			return false;
		}
		
		return true;
	}

	// recurring task (not done) (GUI Display format)
	// <taskname>, <start>-<end> every <day>, priority
	static boolean isRecurringTaskOutput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int commaWhitespaceIndex1 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex1 <= 0){
			return false;
		}

		String taskName = tempString.substring(0, commaWhitespaceIndex1).trim();

		if(taskName.length()==0){
			return false;
		}
		
		tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();
		
		if(tempString.length()==0){
			return false;
		}

		int dashIndex1 = tempString.indexOf("-");
		
		if(dashIndex1 <0){
			return false;
		}
		
		String startingTime = tempString.substring(0, dashIndex1).trim();

		if(startingTime.length()!=4){
			return false;
		}
		
		if(!isValidTime(startingTime)){
			return false;
		}
		
		tempString = tempString.substring(dashIndex1 + 1).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int whitespaceEveryIndex = tempString.toLowerCase().indexOf(" every");
		
		if(whitespaceEveryIndex < 0){
			return false;
		}
		
		String endingTime = tempString.substring(0, whitespaceEveryIndex).trim();
		
		if(endingTime.length() !=4){
			return false;
		}
		
		if(!isValidTime(endingTime)){
			return false;
		}
		
		tempString = tempString.substring(whitespaceEveryIndex + 6).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int commaWhitespaceIndex2 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex2 < 0){
			return false;
		}
		
		String day = tempString.substring(0, commaWhitespaceIndex2).trim();
		
		// monday, tuesday, wednesday, thursday, friday, saturday, sunday
		if(day.length() == 0){
			return false;
		}
		
		if(!Checker.isValidDay(day)){
			return false;
		}
		
		tempString = tempString.substring(commaWhitespaceIndex2 + 2);
		
		if(tempString.length() == 0){
			return false;
		}
		
		String priority = tempString;
		
		if(priority.length()==0){
			return false;
		}
		
		return true;	
	}

	// done recurring task (GUI Display) format
	// <taskname>, <start>-<end> every <day>, priority [done]
	static boolean isDoneRecurringTaskOutput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int doneStringIndex = tempString.indexOf(DONE_STRING);
		
		if(doneStringIndex < 0){
			return false;
		}
		
		tempString = tempString.substring(0, doneStringIndex).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(!Checker.isRecurringTaskOutput(tempString)){
			return false;
		}
		
		return true;
	}

	// <taskname>; <start>-<end> on <date>; <priority>
	static boolean isEventTaskInput(String string) {

		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int semicolonWhitespaceIndex1 = tempString.indexOf("; ");
		
		if(semicolonWhitespaceIndex1 <= 0){
			return false;
		}

		String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();
		
		if(taskName.length()==0){
			return false;
		}
		
		tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();
		
		if(tempString.length()==0){
			return false;
		}

		int dashIndex1 = tempString.indexOf("-");
		
		if(dashIndex1 < 0){
			return false;
		}
		
		String startingTime = tempString.substring(0, dashIndex1).trim();
		
		if(startingTime.length() != 4){
			return false;
		}
		
		if(!isValidTime(startingTime)){
			return false;
		}
		
		tempString = tempString.substring(dashIndex1 + 1).trim();

		if(tempString.length() == 0){
			return false;
		}
		
		int whitespaceOnWhitespaceIndex1 = tempString.toLowerCase().indexOf(" on ");
		
		if(whitespaceOnWhitespaceIndex1 < 0){
			return false;
		}
		
		String endingTime = tempString.substring(0, whitespaceOnWhitespaceIndex1).trim();

		if(endingTime.length() != 4){
			return false;
		}

		if(!Checker.isValidTime(endingTime)){
			return false;
		}
		
		tempString = tempString.substring(whitespaceOnWhitespaceIndex1 + 4).trim();
		
		if(tempString.length() == 0){
			return false;
		}
		
		int semicolonWhitespaceIndex2 = tempString.indexOf("; ");
		
		if(semicolonWhitespaceIndex2 < 0){
			return false;
		}
		
		String date = tempString.substring(0, semicolonWhitespaceIndex2).trim();

		if(date.length() == 0){
			return false;
		}
		
		if(!Checker.isValidDate(date)){
			return false;
		}

		tempString = tempString.substring(semicolonWhitespaceIndex2 + 2).trim();
		
		if(tempString.length() == 0){
			return false;
		}
		
		String priority = tempString.trim();

		if(priority.length() == 0){
			return false;
		}
		
		return true;
	}
	
	static boolean isDoneEventTaskInput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int doneStringIndex = tempString.indexOf(DONE_STRING);
		
		if(doneStringIndex < 0){
			return false;
		}
		
		tempString = tempString.substring(0, doneStringIndex).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(!Checker.isEventTaskInput(tempString)){
			return false;
		}
		
		return true;
	}
	

	// <taskname>, <start>-<end> on <date>, <priority>
	static boolean isEventTaskOutput(String string) {

		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int commaWhitespaceIndex1 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex1 <= 0){
			return false;
		}

		String taskName = tempString.substring(0, commaWhitespaceIndex1).trim();
		
		if(taskName.length()==0){
			return false;
		}
		
		tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();
		
		if(tempString.length()==0){
			return false;
		}

		int dashIndex1 = tempString.indexOf("-");
		
		if(dashIndex1 < 0){
			return false;
		}
		
		String startingTime = tempString.substring(0, dashIndex1).trim();
		
		if(startingTime.length()!=4){
			return false;
		}
		
		if(!isValidTime(startingTime)){
			return false;
		}
		
		tempString = tempString.substring(dashIndex1 + 1).trim();
		
		if(tempString.length() == 0){
			return false;
		}
		
		int whitespaceOnWhitespaceIndex1 = tempString.toLowerCase().indexOf(" on ");
		
		if(whitespaceOnWhitespaceIndex1 < 0){
			return false;
		}
		
		String endingTime = tempString.substring(0, whitespaceOnWhitespaceIndex1).trim();
		
		if(endingTime.length() != 4){
			return false;
		}

		if(!Checker.isValidTime(endingTime)){
			return false;
		}
		
		tempString = tempString.substring(whitespaceOnWhitespaceIndex1 + 4).trim();
		
		if(tempString.length() == 0){
			return false;
		}
		
		int commaWhitespaceIndex2 = tempString.indexOf(", ");
		
		if(commaWhitespaceIndex2 < 0){
			return false;
		}
		
		String date = tempString.substring(0, commaWhitespaceIndex2).trim();
		
		if(date.length() == 0){
			return false;
		}
		
		if(!Checker.isValidDate(date)){
			return false;
		}

		tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();
		
		if(tempString.length() == 0){
			return false;
		}
		
		String priority = tempString.trim();
		
		if(priority.length() == 0){
			return false;
		}
		
		return true;
	}
	
	// <taskname>, <start>-<end> on <date>, <priority> [done]
	static boolean isDoneEventTaskOutput(String string) {
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		int doneStringIndex = tempString.indexOf(DONE_STRING);
		
		if(doneStringIndex < 0){
			return false;
		}
		
		tempString = tempString.substring(0, doneStringIndex).trim();
		
		if(tempString.length()==0){
			return false;
		}
		
		if(!Checker.isEventTaskOutput(tempString)){
			return false;
		}
		
		return true;
	}

	// checks if a string is the full name of a day in a week, ignoring case
	static boolean isValidDay(String string){
		String tempString = new String("");
		tempString = string.trim();
		
		if(tempString.length() <= 5){
			return false;
		}

		if(tempString.toLowerCase().equalsIgnoreCase("monday")||tempString.toLowerCase().equalsIgnoreCase("tuesday")||tempString.toLowerCase().equalsIgnoreCase("wednesday")||tempString.toLowerCase().equalsIgnoreCase("thursday")||tempString.toLowerCase().equalsIgnoreCase("friday")||tempString.toLowerCase().equalsIgnoreCase("saturday")||tempString.toLowerCase().equalsIgnoreCase("sunday")){
			return true;
		}
		
		return false;
	}
		
}
