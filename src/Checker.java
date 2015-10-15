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

	// checks if a task is floating task
	// format for a floating task
	// task
	// e.g.
	// task
	// NOTE: There are no ", " Strings
	static boolean isFloatingTask(String remainingString) {
		String tempString = new String("");
		tempString = remainingString.trim();

		int commaWhitespaceIndex1 = tempString.indexOf(", ");

		if (commaWhitespaceIndex1 < 0) {
			return true;
		}

		return false;
	}

	// checks if a task is a deadline task
	// format for a deadline task
	// task, date, ending time
	// e.g.
	// task, 1/1/1, 0000
	// NOTE: There are 2 ", " Strings
	static boolean isDeadlineTask(String remainingString) {
		String tempString = new String("");
		tempString = remainingString.trim();
		String[] taskVariables = new String[3];

		int commaWhitespaceIndex1 = tempString.indexOf(", ");

		if (commaWhitespaceIndex1 < 0) {
			return false;
		}

		// the task of the deadline task
		taskVariables[0] = tempString.substring(0, commaWhitespaceIndex1).trim();

		tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();

		int commaWhitespaceIndex2 = tempString.indexOf(", ");

		if (commaWhitespaceIndex2 < 0) {
			return false;
		}

		// the date of the deadline task
		taskVariables[1] = tempString.substring(0, commaWhitespaceIndex2).trim();

		// checks if the date is in the correct format
		if (!Checker.isValidDate(taskVariables[1])) {
			return false;
		}

		tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();

		int commaWhitespaceIndex3 = tempString.indexOf(", ");

		// there are only 2 ", " strings
		if (commaWhitespaceIndex3 >= 0) {
			return false;
		}

		// the end time of the deadline task
		taskVariables[2] = tempString.trim();

		// checks if the end time is in the correct format
		if (!Checker.isValidTime(taskVariables[2])) {
			return false;
		}

		return true;
	}

	// checks if a task is a normal task
	// format for a normal task
	// task, date, starting time, ending time, priority
	// e.g.
	// task, 1/1/1, 0000, 0001, priority
	// NOTE: There are 4 ", " Strings
	static boolean isNormalTask(String remainingString) {
		String tempString = new String("");
		tempString = remainingString.trim();
		String[] taskVariables = new String[5];

		int commaWhitespaceIndex1 = tempString.indexOf(", ");

		if (commaWhitespaceIndex1 < 0) {
			return false;
		}

		// the task of the normal task
		taskVariables[0] = tempString.substring(0, commaWhitespaceIndex1).trim();

		System.out.println("taskVariables[0] : " + taskVariables[0]);

		tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();

		int commaWhitespaceIndex2 = tempString.indexOf(", ");

		if (commaWhitespaceIndex2 < 0) {
			return false;
		}

		// the date of the normal task
		taskVariables[1] = tempString.substring(0, commaWhitespaceIndex2).trim();
		System.out.println("taskVariables[1] : " + taskVariables[1]);

		// checks if the date is in the correct format
		if (!Checker.isValidDate(taskVariables[1])) {
			return false;
		}

		tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();

		int commaWhitespaceIndex3 = tempString.indexOf(", ");

		if (commaWhitespaceIndex3 < 0) {
			return false;
		}

		// the starting time of the normal task
		taskVariables[2] = tempString.substring(0, commaWhitespaceIndex3).trim();

		System.out.println("taskVariables[2] : " + taskVariables[2]);

		// checks if the starting time is in the correct format
		if (!Checker.isValidTime(taskVariables[2])) {
			return false;
		}

		tempString = tempString.substring(commaWhitespaceIndex3 + 2).trim();

		int commaWhitespaceIndex4 = tempString.indexOf(", ");

		if (commaWhitespaceIndex4 < 0) {
			return false;
		}

		// the ending time of the normal task
		taskVariables[3] = tempString.substring(0, commaWhitespaceIndex4).trim();

		System.out.println("taskVariables[3] : " + taskVariables[3]);

		// checks if the ending time is in the correct format
		if (!Checker.isValidTime(taskVariables[3])) {
			return false;
		}

		// the priority of the normal task
		taskVariables[4] = tempString.substring(commaWhitespaceIndex4 + 2).trim();

		System.out.println("taskVariables[4] : " + taskVariables[4]);

		return true;
	}

}
