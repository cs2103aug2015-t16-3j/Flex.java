// Task.java
// Contains the variables (attributes) in a Task
// Flex.java should NOT allow the attribute "comparisonValue" to be edited

public class Task {

	// in this String array of 8 String elements
	// taskVariables[0] is the task name
	// taskVariables[1] is the date
	// taskVariables[2] is the day
	// taskVariables[3] is the starting time
	// taskVariables[4] is the ending time
	// taskVariables[5] is the priority
	// taskVariables[6] will be set to DONE_STRING if the task is done
	// taskVariables[7] will be used to store the form of the task String (for
	// user input command or file)
	// taskVariables[8] will be used to store the form of the task String (for
	// GUI display window "Flex")
	private String[] taskVariables = new String[9];
	// this is used for sorting the tasks in the schedule file
	private double comparisonValue = 0.0;

	private static final String DONE_STRING = "[done]";

	// format of the different types of tasks

	// floating task:
	// "task"

	// deadline task:
	// "task, date, ending time"

	// normal task:
	// "task, date, starting time, ending time, priority"

	private static final int YEAR_DAYS = 365;
	private static final int LEAP_YEAR_DAYS = 366;
	private static final int DAY_HOURS = 24;
	private static final int HOUR_MINUTES = 60;

	// number of days in each month
	private static final int JANUARY_DAYS = 31;
	private static final int FEBRUARY_DAYS = 28;
	private static final int MARCH_DAYS = 31;
	private static final int APRIL_DAYS = 30;
	private static final int MAY_DAYS = 31;
	private static final int JUNE_DAYS = 30;
	// private static final int JULY_DAYS = 31;
	private static final int AUGUST_DAYS = 31;
	private static final int SEPTEMBER_DAYS = 30;
	private static final int OCTOBER_DAYS = 31;
	private static final int NOVEMBER_DAYS = 30;
	private static final int DECEMBER_DAYS = 31;

	// accumulated number of days according to the month
	private static final int JANUARY_ACCUMULATED_DAYS = JANUARY_DAYS;
	private static final int FEBRUARY_ACCUMULATED_DAYS = JANUARY_DAYS + FEBRUARY_DAYS;
	private static final int MARCH_ACCUMULATED_DAYS = JANUARY_DAYS + FEBRUARY_DAYS + MARCH_DAYS;
	private static final int APRIL_ACCUMULATED_DAYS = JANUARY_DAYS + FEBRUARY_DAYS + MARCH_DAYS + APRIL_DAYS;
	private static final int MAY_ACCUMULATED_DAYS = JANUARY_DAYS + FEBRUARY_DAYS + MARCH_DAYS + APRIL_DAYS + MAY_DAYS;
	private static final int JUNE_ACCUMULATED_DAYS = JANUARY_DAYS + FEBRUARY_DAYS + MARCH_DAYS + APRIL_DAYS + MAY_DAYS
			+ JUNE_DAYS;
	private static final int JULY_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS - NOVEMBER_DAYS - OCTOBER_DAYS
			- SEPTEMBER_DAYS - AUGUST_DAYS;
	private static final int AUGUST_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS - NOVEMBER_DAYS - OCTOBER_DAYS
			- SEPTEMBER_DAYS;
	private static final int SEPTEMBER_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS - NOVEMBER_DAYS - OCTOBER_DAYS;
	private static final int OCTOBER_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS - NOVEMBER_DAYS;
	private static final int NOVEMBER_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS;
	private static final int DECEMBER_ACCUMULATED_DAYS = YEAR_DAYS;

	// 2129611680 is the positive comparison value of 1/1/4100
	private static final double FLOATING_TASK_COMPARISON_VALUE = (2129611680 - 0.5);

	private static final double RECURRING_TASK_COMPARISON_VALUE = 2129611680;

	public Task(String taskInformation) {
		String string = new String("");

		string = taskInformation.trim();

		if (Checker.isEventTaskInput(string)) {
			// format: <taskname>; <start>-<end> on <date>; <priority>

			String tempString = new String("");
			tempString = string.trim();

			int semicolonWhitespaceIndex1 = tempString.indexOf("; ");

			String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();

			int dashIndex1 = tempString.indexOf("-");

			String startingTime = tempString.substring(0, dashIndex1).trim();

			tempString = tempString.substring(dashIndex1 + 1).trim();

			int whitespaceOnWhitespaceIndex1 = tempString.toLowerCase().indexOf(" on ");

			String endingTime = tempString.substring(0, whitespaceOnWhitespaceIndex1).trim();

			tempString = tempString.substring(whitespaceOnWhitespaceIndex1 + 4).trim();

			int semicolonWhitespaceIndex2 = tempString.indexOf("; ");

			String date = tempString.substring(0, semicolonWhitespaceIndex2).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex2 + 2).trim();

			String priority = tempString.trim();

			// set the non-null attributes
			this.taskVariables[0] = taskName;
			this.taskVariables[1] = date;
			this.taskVariables[3] = startingTime;
			this.taskVariables[4] = endingTime;
			this.taskVariables[5] = priority;
			this.taskVariables[7] = string;
			// <taskname>; <start>-<end> on <date>, <priority>
			this.taskVariables[8] = taskName + ", " + startingTime + "-" + endingTime + " on " + date + ", " + priority;
			
			// set the null variables
			this.taskVariables[2] = null;
			this.taskVariables[6] = null;

			// set the comparison value
			calculateAndSetComparisonValue(string);

	
		} else if (Checker.isDoneEventTaskInput(string)) {
			// format: <taskname>; <start>-<end> on <date>; <priority> [done]
			String tempString = new String("");
			tempString = string.trim();

			int whitespaceDoneStringIndex = tempString.indexOf(" [done]");

			tempString = tempString.substring(0, whitespaceDoneStringIndex).trim();

			int semicolonWhitespaceIndex1 = tempString.indexOf("; ");

			String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();

			int dashIndex1 = tempString.indexOf("-");

			String startingTime = tempString.substring(0, dashIndex1).trim();

			tempString = tempString.substring(dashIndex1 + 1).trim();

			int whitespaceOnWhitespaceIndex1 = tempString.toLowerCase().indexOf(" on ");

			String endingTime = tempString.substring(0, whitespaceOnWhitespaceIndex1).trim();

			tempString = tempString.substring(whitespaceOnWhitespaceIndex1 + 4).trim();

			int semicolonWhitespaceIndex2 = tempString.indexOf("; ");

			String date = tempString.substring(0, semicolonWhitespaceIndex2).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex2 + 2).trim();

			String priority = tempString.trim();

			// set the non-null attributes
			this.taskVariables[0] = taskName;
			this.taskVariables[1] = date;
			this.taskVariables[3] = startingTime;
			this.taskVariables[4] = endingTime;
			this.taskVariables[5] = priority;
			this.taskVariables[6] = DONE_STRING;
			this.taskVariables[7] = string;
			// <taskname>; <start>-<end> on <date>, <priority>
			this.taskVariables[8] = taskName + ", " + startingTime + "-" + endingTime + " on " + date + ", " + priority
					+ " " + DONE_STRING;
			
			// set the null variables
			this.taskVariables[2] = null;

			// set the comparison value
			calculateAndSetComparisonValue(string);
			
		} else if (Checker.isDeadlineTaskInput(string)) {
			// format: <taskname>; by <end> on <date>

			String tempString = new String("");
			tempString = string.trim();

			int semicolonWhitespaceIndex1 = tempString.indexOf("; ");

			String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();

			int byWhitespaceIndex1 = tempString.toLowerCase().indexOf("by ");

			tempString = tempString.substring(byWhitespaceIndex1 + 3).trim();

			int whitespaceOnWhitespaceIndex1 = tempString.toLowerCase().indexOf(" on ");

			String endingTime = tempString.substring(0, whitespaceOnWhitespaceIndex1);

			tempString = tempString.substring(whitespaceOnWhitespaceIndex1 + 4).trim();

			String date = tempString.trim();

			// set the non-null attributes
			this.taskVariables[0] = taskName;
			this.taskVariables[1] = date;
			this.taskVariables[4] = endingTime;
			this.taskVariables[7] = string;
			this.taskVariables[8] = this.taskVariables[0] + ", by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " (deadline)";

			// set the null variables
			this.taskVariables[2] = null;
			this.taskVariables[3] = null;
			this.taskVariables[5] = null;
			this.taskVariables[6] = null;

			calculateAndSetComparisonValue(string);

		} else if (Checker.isDoneDeadlineTaskInput(string)) {
			// format: <taskname>; by <end> on <date> [done]
			String tempString = new String("");
			tempString = string.trim();

			int whitespaceDoneStringIndex = tempString.indexOf(" [done]");

			tempString = tempString.substring(0, whitespaceDoneStringIndex).trim();

			int semicolonWhitespaceIndex1 = tempString.indexOf("; ");

			String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();

			int byWhitespaceIndex1 = tempString.toLowerCase().indexOf("by ");

			tempString = tempString.substring(byWhitespaceIndex1 + 3).trim();

			int whitespaceOnWhitespaceIndex1 = tempString.toLowerCase().indexOf(" on ");

			String endingTime = tempString.substring(0, whitespaceOnWhitespaceIndex1);

			tempString = tempString.substring(whitespaceOnWhitespaceIndex1 + 4).trim();

			String date = tempString.trim();

			// set the non-null attributes
			this.taskVariables[0] = taskName;
			this.taskVariables[1] = date;
			this.taskVariables[4] = endingTime;
			this.taskVariables[6] = DONE_STRING;
			this.taskVariables[7] = string;
			this.taskVariables[8] = this.taskVariables[0] + ", by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " " + DONE_STRING + " (deadline)";

			// set the null variables
			this.taskVariables[2] = null;
			this.taskVariables[3] = null;
			this.taskVariables[5] = null;

			calculateAndSetComparisonValue(string);

	
		} else if (Checker.isRecurringTaskInput(string)) {
			// format: <taskname>; <start>-<end> every <day>; <priority>
			String tempString = new String("");
			tempString = string.trim();

			int semicolonWhitespaceIndex1 = tempString.indexOf("; ");

			String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();

			int dashIndex1 = tempString.indexOf("-");

			String startingTime = tempString.substring(0, dashIndex1).trim();

			tempString = tempString.substring(dashIndex1 + 1).trim();

			int whitespaceEveryIndex = tempString.toLowerCase().indexOf(" every");

			String endingTime = tempString.substring(0, whitespaceEveryIndex);

			tempString = tempString.substring(whitespaceEveryIndex + 6).trim();

			int semicolonWhitespaceIndex2 = tempString.indexOf("; ");

			String day = tempString.substring(0, semicolonWhitespaceIndex2).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex2 + 2);

			String priority = tempString;

			// format: <taskname>; <start>-<end> every <day>; <priority>
			// set the non-null attributes
			this.taskVariables[0] = taskName;
			this.taskVariables[2] = day.toLowerCase();
			this.taskVariables[3] = startingTime;
			this.taskVariables[4] = endingTime;
			this.taskVariables[5] = priority;
			this.taskVariables[7] = taskName + "; " + startingTime + "-" + endingTime + " every " + day.toLowerCase() + "; "
					+ priority;
			
			this.taskVariables[8] = taskName + ", " + startingTime + "-" + endingTime + " every " + day.toLowerCase() + ", "
					+ priority;

			// set the null variables
			this.taskVariables[1] = null;
			this.taskVariables[6] = null;

			calculateAndSetComparisonValue(string);

		} else if (Checker.isDoneRecurringTaskInput(string)) {
			// format: <taskname>; <start>-<end> every <day>; priority [done]
			String tempString = new String("");
			tempString = string.trim();

			int whitespaceDoneStringIndex = tempString.indexOf(" [done]");

			tempString = tempString.substring(0, whitespaceDoneStringIndex).trim();

			int semicolonWhitespaceIndex1 = tempString.indexOf("; ");

			String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();

			int dashIndex1 = tempString.indexOf("-");

			String startingTime = tempString.substring(0, dashIndex1).trim();

			tempString = tempString.substring(dashIndex1 + 1).trim();

			int whitespaceEveryIndex = tempString.toLowerCase().indexOf(" every");

			String endingTime = tempString.substring(0, whitespaceEveryIndex);

			tempString = tempString.substring(whitespaceEveryIndex + 6).trim();

			int semicolonWhitespaceIndex2 = tempString.indexOf("; ");

			String day = tempString.substring(0, semicolonWhitespaceIndex2).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex2 + 2);

			String priority = tempString;
			
			// set the non-null attributes
			this.taskVariables[0] = taskName;
			this.taskVariables[2] = day.toLowerCase();
			this.taskVariables[3] = startingTime;
			this.taskVariables[4] = endingTime;
			this.taskVariables[5] = priority;
			this.taskVariables[6] = DONE_STRING;
			this.taskVariables[7] = taskName + "; " + startingTime + "-" + endingTime + " every " + day.toLowerCase() + "; " 
					+ priority + " " + DONE_STRING;;
			this.taskVariables[8] = taskName + ", " + startingTime + "-" + endingTime + " every " + day.toLowerCase() + ", " 
					+ priority + " " + DONE_STRING;

			// set the null variables
			this.taskVariables[1] = null;

			calculateAndSetComparisonValue(string);


		} else if (Checker.isFloatingTaskInput(string)) {
			// format: <taskname>

			String tempString = new String("");
			tempString = string.trim();

			String taskName = tempString.trim();

			// set the non-null attributes
			this.taskVariables[0] = taskName.trim();
			this.taskVariables[7] = taskName.trim();
			this.taskVariables[8] = taskName.trim();

			// set the null variables
			this.taskVariables[1] = null;
			this.taskVariables[2] = null;
			this.taskVariables[3] = null;
			this.taskVariables[4] = null;
			this.taskVariables[5] = null;
			this.taskVariables[6] = null;

			calculateAndSetComparisonValue(string);

		} else if (Checker.isDoneFloatingTaskInput(string)) {
			// format: <taskname> [done]
			String tempString = new String("");
			tempString = string.trim();

			int whitespaceDoneStringIndex = tempString.indexOf(" [done]");

			String taskName = tempString.substring(0, whitespaceDoneStringIndex).trim();

			// set the non-null attributes
			this.taskVariables[0] = taskName.trim();
			this.taskVariables[6] = DONE_STRING;
			this.taskVariables[7] = string;
			this.taskVariables[8] = string.trim();

			// set the null variables
			this.taskVariables[1] = null;
			this.taskVariables[2] = null;
			this.taskVariables[3] = null;
			this.taskVariables[4] = null;
			this.taskVariables[5] = null;

			// set the comparison value
			this.comparisonValue = FLOATING_TASK_COMPARISON_VALUE;

			calculateAndSetComparisonValue(string);

	
		}
	}

	// default Task constructor
	public Task() {
	}

	// e.g. 31/12/2014 with starting time 0859 gives numberOfPastMonths = 12 - 1
	// = 11, and numberOfPastDays = 31 - 1 = 30
	private int findNumberOfAccumulatedPastDaysInCurrentYear(int numberOfPastMonths, int numberOfPastDays) {
		int accumulatedNumberOfDaysInPastMonths = 0;

		if (numberOfPastMonths == 1) {
			accumulatedNumberOfDaysInPastMonths = JANUARY_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 2) {
			accumulatedNumberOfDaysInPastMonths = FEBRUARY_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 3) {
			accumulatedNumberOfDaysInPastMonths = MARCH_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 4) {
			accumulatedNumberOfDaysInPastMonths = APRIL_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 5) {
			accumulatedNumberOfDaysInPastMonths = MAY_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 6) {
			accumulatedNumberOfDaysInPastMonths = JUNE_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 7) {
			accumulatedNumberOfDaysInPastMonths = JULY_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 8) {
			accumulatedNumberOfDaysInPastMonths = AUGUST_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 9) {
			accumulatedNumberOfDaysInPastMonths = SEPTEMBER_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 10) {
			accumulatedNumberOfDaysInPastMonths = OCTOBER_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 11) {
			accumulatedNumberOfDaysInPastMonths = NOVEMBER_ACCUMULATED_DAYS;
		} else if (numberOfPastMonths == 12) {
			accumulatedNumberOfDaysInPastMonths = DECEMBER_ACCUMULATED_DAYS;
		}

		return accumulatedNumberOfDaysInPastMonths + numberOfPastDays;
	}

	public void calculateAndSetComparisonValue(String string) {
		if (Checker.isEventTaskInput(string)) {
			// e.g. 7/9/2015
			String tempDateString = this.taskVariables[1];

			int slashIndex1 = tempDateString.indexOf("/");
			int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());

			tempDateString = tempDateString.substring(slashIndex1 + 1);

			// e.g. 31/12/2014 with starting time 0859
			int slashIndex2 = tempDateString.indexOf("/");

			// the month December (the 12th month of each year)
			int month = Integer.valueOf(tempDateString.substring(0, slashIndex2).trim());

			// The year 2014
			int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1).trim());

			// 8 hours
			int startingTimeHours = Integer.valueOf(this.taskVariables[3].substring(0, 2).trim());

			// 59 minutes
			int startingTimeMinutes = Integer.valueOf(this.taskVariables[3].substring(2).trim());

			// used for sorting all tasks by date and starting time (taking year
			// 0, 0000 hours as reference)
			// (2014 - 1) / 4 = 2013 / 4 = 503 (not considering the remainder)
			int numberOfPastLeapYears = (year - 1) / 4;
			// (2014 - 1) - 503 = 2013 - 503 = 1500
			int numberOfPastNonLeapyears = (year - 1) - numberOfPastLeapYears;

			int leapYearFebruaryDay = 0;

			// 2014%4 = 1
			if ((year % 4 == 0) && ((month - 1) >= 2)) {
				leapYearFebruaryDay = 1;
			}

			int numberOfAccumulatedPastDaysInCurrentYear = findNumberOfAccumulatedPastDaysInCurrentYear(month - 1,
					day - 1);
			int numberOfPastHours = startingTimeHours;
			int numberOfPastMinutes = startingTimeMinutes;

			// e.g. this.comparisonValue = 503 * 366 + 1500 * 365 +
			// (NOVEMBER_ACCUMULATED_DAYS + 0) * 24 * 60 + 8 * 60 + 59;
			this.comparisonValue = numberOfPastLeapYears * LEAP_YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ numberOfPastNonLeapyears * YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ (numberOfAccumulatedPastDaysInCurrentYear + leapYearFebruaryDay) * DAY_HOURS * HOUR_MINUTES
					+ numberOfPastHours * HOUR_MINUTES + numberOfPastMinutes;

		} else if (Checker.isDoneEventTaskInput(string)) {
			// e.g. 7/9/2015
			String tempDateString = this.taskVariables[1];
			int slashIndex1 = tempDateString.indexOf("/");
			int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());

			tempDateString = tempDateString.substring(slashIndex1 + 1);

			// e.g. 31/12/2014 with starting time 0859
			int slashIndex2 = tempDateString.indexOf("/");

			// the month December (the 12th month of each year)
			int month = Integer.valueOf(tempDateString.substring(0, slashIndex2).trim());

			// The year 2014
			int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1).trim());

			// 8 hours
			int startingTimeHours = Integer.valueOf(this.taskVariables[3].substring(0, 2).trim());

			// 59 minutes
			int startingTimeMinutes = Integer.valueOf(this.taskVariables[3].substring(2).trim());

			// used for sorting all tasks by date and starting time (taking year
			// 0, 0000 hours as reference)
			// (2014 - 1) / 4 = 2013 / 4 = 503 (not considering the remainder)
			int numberOfPastLeapYears = (year - 1) / 4;
			// (2014 - 1) - 503 = 2013 - 503 = 1500
			int numberOfPastNonLeapyears = (year - 1) - numberOfPastLeapYears;

			int leapYearFebruaryDay = 0;

			// 2014%4 = 1
			if ((year % 4 == 0) && ((month - 1) >= 2)) {
				leapYearFebruaryDay = 1;
			}

			int numberOfAccumulatedPastDaysInCurrentYear = findNumberOfAccumulatedPastDaysInCurrentYear(month - 1,
					day - 1);
			int numberOfPastHours = startingTimeHours;
			int numberOfPastMinutes = startingTimeMinutes;

			// e.g. this.comparisonValue = 503 * 366 + 1500 * 365 +
			// (NOVEMBER_ACCUMULATED_DAYS + 0) * 24 * 60 + 8 * 60 + 59;
			this.comparisonValue = numberOfPastLeapYears * LEAP_YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ numberOfPastNonLeapyears * YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ (numberOfAccumulatedPastDaysInCurrentYear + leapYearFebruaryDay) * DAY_HOURS * HOUR_MINUTES
					+ numberOfPastHours * HOUR_MINUTES + numberOfPastMinutes;

		}
		if (Checker.isDeadlineTaskInput(string)) {
			// e.g. 7/9/2015
			String tempDateString = this.taskVariables[1];

			int slashIndex1 = tempDateString.indexOf("/");
			int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());

			tempDateString = tempDateString.substring(slashIndex1 + 1);

			// e.g. 31/12/2014 with starting time 0859
			int slashIndex2 = tempDateString.indexOf("/");

			// the month December (the 12th month of each year)
			int month = Integer.valueOf(tempDateString.substring(0, slashIndex2).trim());

			// The year 2014
			int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1).trim());

			// used for sorting all tasks by date and starting time (taking year
			// 0, 0000 hours as reference)
			// (2014 - 1) / 4 = 2013 / 4 = 503 (not considering the remainder)
			int numberOfPastLeapYears = (year - 1) / 4;
			// (2014 - 1) - 503 = 2013 - 503 = 1500
			int numberOfPastNonLeapyears = (year - 1) - numberOfPastLeapYears;

			int leapYearFebruaryDay = 0;

			// 2014%4 = 1
			if ((year % 4 == 0) && ((month - 1) >= 2)) {
				leapYearFebruaryDay = 1;
			}

			int numberOfAccumulatedPastDaysInCurrentYear = findNumberOfAccumulatedPastDaysInCurrentYear(month - 1,
					day - 1);
			;

			// e.g. this.comparisonValue = 503 * 366 + 1500 * 365 +
			// (NOVEMBER_ACCUMULATED_DAYS + 0) * 24 * 60;
			this.comparisonValue = -0.5 + numberOfPastLeapYears * LEAP_YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ numberOfPastNonLeapyears * YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ (numberOfAccumulatedPastDaysInCurrentYear + leapYearFebruaryDay) * DAY_HOURS * HOUR_MINUTES;

		} else if (Checker.isDoneDeadlineTaskInput(string)) {
			// e.g. 7/9/2015
			String tempDateString = this.taskVariables[1];

			int slashIndex1 = tempDateString.indexOf("/");
			int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());

			tempDateString = tempDateString.substring(slashIndex1 + 1);

			// e.g. 31/12/2014 with starting time 0859
			int slashIndex2 = tempDateString.indexOf("/");

			// the month December (the 12th month of each year)
			int month = Integer.valueOf(tempDateString.substring(0, slashIndex2).trim());

			// The year 2014
			int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1).trim());

			// used for sorting all tasks by date and starting time (taking year
			// 0, 0000 hours as reference)
			// (2014 - 1) / 4 = 2013 / 4 = 503 (not considering the remainder)
			int numberOfPastLeapYears = (year - 1) / 4;
			// (2014 - 1) - 503 = 2013 - 503 = 1500
			int numberOfPastNonLeapyears = (year - 1) - numberOfPastLeapYears;

			int leapYearFebruaryDay = 0;

			// 2014%4 = 1
			if ((year % 4 == 0) && ((month - 1) >= 2)) {
				leapYearFebruaryDay = 1;
			}

			int numberOfAccumulatedPastDaysInCurrentYear = findNumberOfAccumulatedPastDaysInCurrentYear(month - 1,
					day - 1);
			;

			// e.g. this.comparisonValue = 503 * 366 + 1500 * 365 +
			// (NOVEMBER_ACCUMULATED_DAYS + 0) * 24 * 60;
			this.comparisonValue = -0.5 + numberOfPastLeapYears * LEAP_YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ numberOfPastNonLeapyears * YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ (numberOfAccumulatedPastDaysInCurrentYear + leapYearFebruaryDay) * DAY_HOURS * HOUR_MINUTES;

		} else if (Checker.isRecurringTaskInput(string)) {
			this.comparisonValue = RECURRING_TASK_COMPARISON_VALUE;
		} else if (Checker.isDoneRecurringTaskInput(string)) {
			this.comparisonValue = RECURRING_TASK_COMPARISON_VALUE;
		} else if (Checker.isFloatingTaskInput(string)) {
			this.comparisonValue = FLOATING_TASK_COMPARISON_VALUE;
		} else if (Checker.isDoneFloatingTaskInput(string)) {
			this.comparisonValue = FLOATING_TASK_COMPARISON_VALUE;
		}

	}

	// retrieves the comparisonValue for sorting
	public double getComparisonValue() {
		return this.comparisonValue;
	}

	// retrieves the task
	public String getTaskName() {
		return this.taskVariables[0];
	}

	// changes the task
	public void setTaskName(String newTaskName) {
		this.taskVariables[0] = newTaskName;
	}

	// retrieves the date
	public String getDate() {
		return this.taskVariables[1];
	}

	// edits the date
	public void setDate(String newDate) {
		this.taskVariables[1] = newDate;
	}

	// retrieves the day
	public String getDay() {
		return this.taskVariables[2];
	}

	// sets the day
	public void setDay(String newDay) {
		this.taskVariables[2] = newDay.toLowerCase();
	}

	// retrieves the starting time
	public String getStart() {
		return this.taskVariables[3];
	}

	// edits the starting time
	public void setStart(String newStart) {
		this.taskVariables[3] = newStart;
	}

	// retrieves the ending time
	public String getEnd() {
		return this.taskVariables[4];
	}

	// edits the ending time
	public void setEnd(String newEnd) {
		this.taskVariables[4] = newEnd;
	}

	// retrieves the priority
	public String getPriority() {
		return this.taskVariables[4];
	}

	// sets the priority
	public void setPriority(String newPriority) {
		this.taskVariables[4] = newPriority;
	}

	// "DONE" MODIFIERS

	// marks a task as done
	public void setTaskDone() {
		if (this.taskVariables[6] == null) {
			this.taskVariables[6] = DONE_STRING;
			this.taskVariables[7] = this.taskVariables[6] + " " + DONE_STRING;
			this.taskVariables[8] = this.taskVariables[7] + " " + DONE_STRING;
		}
	}

	// String form of each task in the .txt schedule file
	public String getScheduleString() {
		return this.taskVariables[7];
	}

	// String form of the display on the screen (Graphical User Interface's
	// display) for the task
	public String getDisplayString() {
		return this.taskVariables[8];
	}

	// marks a task as not done
	public void setTaskNotDone() {
		if (this.taskVariables[6] != null) {
			this.taskVariables[6] = null;

			int scheduleStringDoneIndex = this.taskVariables[7].indexOf(DONE_STRING);

			if (scheduleStringDoneIndex >= 0) {
				this.taskVariables[7] = this.taskVariables[7].substring(0, scheduleStringDoneIndex).trim();
			}

			int displayStringDoneIndex = this.taskVariables[8].indexOf(DONE_STRING);

			if (displayStringDoneIndex >= 0) {
				this.taskVariables[8] = this.taskVariables[8].substring(0, displayStringDoneIndex).trim();
			}

		}
	}

}