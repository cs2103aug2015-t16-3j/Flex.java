// Task.java
// Contains the variables (attributes) in a Task
// Flex.java should NOT allow the attribute "comparisonValue" to be edited

public class Task {

	// in this String array of 5 String elements
	// taskVariables[0] is the task name
	// taskVariables[1] is the date
	// taskVariables[2] is the starting time
	// taskVariables[3] is the ending time
	// taskVariables[4] is the priority
	// taskVariables[5] is whether the task is done or not (also known as a done
	// task)
	String[] taskVariables = new String[6];
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
	private static final int FLOATING_TASK_COMPARISON_VALUE = 2129611680;

	public Task(String taskInformation) {

		String remainingString = new String("");
		remainingString = taskInformation.trim();

		if (Checker.isDoneFloatingTask(remainingString)) {
			
			String tempString = new String("");

			tempString = remainingString.trim();
			
			int doneStringIndex = tempString.indexOf(DONE_STRING);
			
			tempString = tempString.substring(0, doneStringIndex);
			
			// set the task name of the floating task
			this.taskVariables[0] = tempString;

			// set the date of the floating task to be null
			this.taskVariables[1] = null;

			// set the starting time of the floating task to be null;
			this.taskVariables[2] = null;

			// set the end time of the floating task to be null
			this.taskVariables[3] = null;

			// set the priority of the floating task to be null;
			this.taskVariables[4] = null;

			// marks this floating task as done
			this.taskVariables[5] = DONE_STRING;

			// calculates and sets the comparison value of the floating task
			calculateComparisonValue(remainingString);

		} else if (Checker.isDoneDeadlineTask(remainingString)) {
									
			String tempString = new String("");

			tempString = remainingString.trim();
			
			int doneStringIndex = tempString.indexOf(DONE_STRING);
			
			tempString = tempString.substring(0, doneStringIndex);						
			
			int commaWhitespaceIndex1 = tempString.indexOf(", ");

			// set the task name of the deadline task
			this.taskVariables[0] = tempString.substring(0, commaWhitespaceIndex1).trim();

			tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();

			int commaWhitespaceIndex2 = tempString.indexOf(", ");

			// set the date of the deadline task
			this.taskVariables[1] = tempString.substring(0, commaWhitespaceIndex2).trim();

			// set the starting time of the deadline task to be null;
			this.taskVariables[2] = null;

			// set the end time of the deadline task
			this.taskVariables[3] = tempString.substring(commaWhitespaceIndex2 + 2).trim();

			// set the priority of the deadline task to be null;
			this.taskVariables[4] = null;

			// marks this deadline task as done
			this.taskVariables[5] = DONE_STRING;

			// calculate and sets the comparison value of the deadline task
			calculateComparisonValue(remainingString);

		}
		if (Checker.isDoneEventTask(remainingString)) {
			
			String tempString = new String("");

			tempString = remainingString.trim();
			
			int doneStringIndex = tempString.indexOf(DONE_STRING);
			
			tempString = tempString.substring(0, doneStringIndex);

			int commaWhitespaceIndex1 = tempString.indexOf(", ");

			// set the task name of the event task
			this.taskVariables[0] = tempString.substring(0, commaWhitespaceIndex1).trim();

			tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();

			int commaWhitespaceIndex2 = tempString.indexOf(", ");

			// set the date of the normal task
			this.taskVariables[1] = tempString.substring(0, commaWhitespaceIndex2).trim();

			tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();

			int commaWhitespaceIndex3 = tempString.indexOf(", ");

			// set the starting time of the normal task
			this.taskVariables[2] = tempString.substring(0, commaWhitespaceIndex3).trim();

			tempString = tempString.substring(commaWhitespaceIndex3 + 2).trim();

			int commaWhitespaceIndex4 = tempString.indexOf(", ");

			// set the ending time of the normal task
			this.taskVariables[3] = tempString.substring(0, commaWhitespaceIndex4).trim();

			// set the priority of the normal task
			this.taskVariables[4] = tempString.substring(commaWhitespaceIndex4 + 2).trim();

			// marks this event task as done
			this.taskVariables[5] = DONE_STRING;

			// calculate the comparison value of the normal task
			calculateComparisonValue(remainingString);

		} else if (Checker.isFloatingTask(remainingString)) {
			// set the task name of the floating task
			this.taskVariables[0] = remainingString;

			// set the date of the floating task to be null
			this.taskVariables[1] = null;

			// set the starting time of the floating task to be null;
			this.taskVariables[2] = null;

			// set the end time of the floating task to be null
			this.taskVariables[3] = null;

			// set the priority of the floating task to be null;
			this.taskVariables[4] = null;

			// calculates and sets the comparison value of the floating task
			calculateComparisonValue(remainingString);

		} else if (Checker.isDeadlineTask(remainingString)) {
			String tempString = new String("");

			tempString = remainingString.trim();

			int commaWhitespaceIndex1 = tempString.indexOf(", ");

			// set the task name of the deadline task
			this.taskVariables[0] = tempString.substring(0, commaWhitespaceIndex1).trim();

			tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();

			int commaWhitespaceIndex2 = tempString.indexOf(", ");

			// set the date of the deadline task
			this.taskVariables[1] = tempString.substring(0, commaWhitespaceIndex2).trim();

			// set the starting time of the deadline task to be null;
			this.taskVariables[2] = null;

			// set the end time of the deadline task
			this.taskVariables[3] = tempString.substring(commaWhitespaceIndex2 + 2).trim();

			// set the priority of the deadline task to be null;
			this.taskVariables[4] = null;

			// calculate and sets the comparison value of the deadline task
			calculateComparisonValue(remainingString);

		} else if (Checker.isEventTask(remainingString)) {
			String tempString = new String("");

			tempString = remainingString.trim();

			int commaWhitespaceIndex1 = tempString.indexOf(", ");

			// set the task name of the event task
			this.taskVariables[0] = tempString.substring(0, commaWhitespaceIndex1).trim();

			tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();

			int commaWhitespaceIndex2 = tempString.indexOf(", ");

			// set the date of the normal task
			this.taskVariables[1] = tempString.substring(0, commaWhitespaceIndex2).trim();

			tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();

			int commaWhitespaceIndex3 = tempString.indexOf(", ");

			// set the starting time of the normal task
			this.taskVariables[2] = tempString.substring(0, commaWhitespaceIndex3).trim();

			tempString = tempString.substring(commaWhitespaceIndex3 + 2).trim();

			int commaWhitespaceIndex4 = tempString.indexOf(", ");

			// set the ending time of the normal task
			this.taskVariables[3] = tempString.substring(0, commaWhitespaceIndex4).trim();

			// set the priority of the normal task
			this.taskVariables[4] = tempString.substring(commaWhitespaceIndex4 + 2).trim();

			// calculate the comparison value of the normal task
			calculateComparisonValue(remainingString);
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

	public void calculateComparisonValue(String tempString) {
		if (Checker.isFloatingTask(tempString)) {
			// calculate the comparison value of the floating task
			this.comparisonValue = FLOATING_TASK_COMPARISON_VALUE;
		} else if (Checker.isDeadlineTask(tempString)) {
			// calculate the comparison value of the deadline task
			String tempDateString = new String("");

			// date of the deadline task
			tempDateString = this.taskVariables[1].trim();

			int slashIndex1 = tempDateString.indexOf("/");
			int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());

			tempDateString = tempDateString.substring(slashIndex1 + 1);

			int slashIndex2 = tempDateString.indexOf("/");

			int month = Integer.valueOf(tempDateString.substring(0, slashIndex2).trim());

			int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1).trim());

			int numberOfPastLeapYears = (year - 1) / 4;

			int numberOfPastNonLeapyears = (year - 1) - numberOfPastLeapYears;

			int leapYearFebruaryDay = 0;

			// note that the starting time is null, so it should be treated as 0

			if ((year % 4 == 0) && ((month - 1) >= 2)) {
				leapYearFebruaryDay = 1;
			}

			int numberOfAccumulatedPastDaysInCurrentYear = findNumberOfAccumulatedPastDaysInCurrentYear(month - 1,
					day - 1);

			// Note the need for "-0.5", for sorting deadline tasks to be in
			// front of normal tasks for any given date
			this.comparisonValue = -0.5 + (numberOfPastLeapYears * LEAP_YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ numberOfPastNonLeapyears * YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ (numberOfAccumulatedPastDaysInCurrentYear + leapYearFebruaryDay) * DAY_HOURS * HOUR_MINUTES);

		} else if (Checker.isEventTask(tempString)) {
			// calculate the comparison value of the normal task
			String tempDateString = new String("");

			// date of the event task
			tempDateString = this.taskVariables[1].trim();

			int slashIndex1 = tempDateString.indexOf("/");
			int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());

			tempDateString = tempDateString.substring(slashIndex1 + 1);

			int slashIndex2 = tempDateString.indexOf("/");

			int month = Integer.valueOf(tempDateString.substring(0, slashIndex2).trim());

			int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1).trim());

			// starting time of the normal task
			int startingTimeHours = Integer.valueOf(this.taskVariables[2].substring(0, 2).trim());

			int startingTimeMinutes = Integer.valueOf(this.taskVariables[2].substring(2).trim());

			int numberOfPastLeapYears = (year - 1) / 4;

			int numberOfPastNonLeapyears = (year - 1) - numberOfPastLeapYears;

			int leapYearFebruaryDay = 0;

			if ((year % 4 == 0) && ((month - 1) >= 2)) {
				leapYearFebruaryDay = 1;
			}

			int numberOfAccumulatedPastDaysInCurrentYear = findNumberOfAccumulatedPastDaysInCurrentYear(month - 1,
					day - 1);
			int numberOfPastHours = startingTimeHours;
			int numberOfPastMinutes = startingTimeMinutes;

			this.comparisonValue = numberOfPastLeapYears * LEAP_YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ numberOfPastNonLeapyears * YEAR_DAYS * DAY_HOURS * HOUR_MINUTES
					+ (numberOfAccumulatedPastDaysInCurrentYear + leapYearFebruaryDay) * DAY_HOURS * HOUR_MINUTES
					+ numberOfPastHours * HOUR_MINUTES + numberOfPastMinutes;

		}

	}

	// retrieves the comparisonValue for sorting
	public double getComparisonValue() {
		return this.comparisonValue;
	}

	// String form of the display on the screen (Graphical User Interface's
	// display) for the task
	public String getDisplayString() {
		if (this.taskVariables[1] == null && this.taskVariables[2] == null && this.taskVariables[3] == null

				&& this.taskVariables[4] == null && this.taskVariables[5] == null) {
			// for a floating task
			// return the task name

			return this.taskVariables[0];
			
		} else if (this.taskVariables[2] == null && this.taskVariables[4] == null && this.taskVariables[5] == null) {

			// for a deadline task
			// return the task name and ending time

			return this.taskVariables[0] + ", " + this.taskVariables[3] + " (deadline)";

		} else if (this.taskVariables[5] == null) {

			// for a normal task
			// return the task name, starting time, ending time, priority

			return this.taskVariables[0] + ", " + this.taskVariables[2] + ", " + this.taskVariables[3] + ", "
					+ this.taskVariables[4];

		} else if (this.taskVariables[1] == null && this.taskVariables[2] == null && this.taskVariables[3] == null
				&& this.taskVariables[4] == null) {

			// for a floating task which is done

			return this.taskVariables[0] + " DONE_STRING";

		} else if (this.taskVariables[2] == null && this.taskVariables[4] == null) {

			// for a deadline task which is done

			return this.taskVariables[0] + ", " + this.taskVariables[3] + "DONE_STRING";

		} else if (this.taskVariables != null && this.taskVariables[1] != null && this.taskVariables[2] != null

				&& this.taskVariables[3] != null && this.taskVariables[4] != null && this.taskVariables[5] != null) {
			// for an event task which is done

			return this.taskVariables[0] + ", " + this.taskVariables[2] + ", " + this.taskVariables[3] + ", "
					+ this.taskVariables[4] + " DONE_STRING";
		}

		return "";
	}

	// String form of each task in the .txt schedule file
	public String getScheduleString() {
		if (this.taskVariables[1] == null && this.taskVariables[2] == null && this.taskVariables[3] == null
				&& this.taskVariables[4] == null) {
			// for a floating task
			// return the task

			return this.taskVariables[0];

		} else if (this.taskVariables[2] == null && this.taskVariables[4] == null) {
			// for a deadline task
			// return the task, date and ending time

			return this.taskVariables[0] + ", " + this.taskVariables[1] + ", " + this.taskVariables[3];

		} else if (this.taskVariables[5] == null) {

			// for an event task
			// return the task, date, starting time, ending time,priority

			return this.taskVariables[0] + ", " + this.taskVariables[1] + ", " + this.taskVariables[2] + ", "
					+ this.taskVariables[3] + ", " + this.taskVariables[4];

		} else if (this.taskVariables[1] == null && this.taskVariables[2] == null && this.taskVariables[3] == null
				&& this.taskVariables[4] == null) {

			// for a floating task which is done

			return this.taskVariables[0] + " DONE_STRING";

		} else if (this.taskVariables[2] == null && this.taskVariables[4] == null) {

			// for a deadline task which is done

			return this.taskVariables[0] + ", " + this.taskVariables[1] + ", " + this.taskVariables[3] + "DONE_STRING";

		} else if (this.taskVariables != null && this.taskVariables[1] != null && this.taskVariables[2] != null

				&& this.taskVariables[3] != null && this.taskVariables[4] != null && this.taskVariables[5] != null) {

			// for an event task which is done

			return this.taskVariables[0] + ", " + this.taskVariables[1] + ", " + this.taskVariables[2] + ", "
					+ this.taskVariables[3] + ", " + this.taskVariables[4] + " DONE_STRING";
		}

		return "";
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

	// retrieves the starting time
	public String getStart() {
		return this.taskVariables[2];
	}

	// edits the starting time
	public void setStart(String newStart) {
		this.taskVariables[2] = newStart;
	}

	// retrieves the ending time
	public String getEnd() {
		return this.taskVariables[3];
	}

	// edits the ending time
	public void setEnd(String newEnd) {
		this.taskVariables[3] = newEnd;
	}

	// retrieves the priority
	public String getPriority() {
		return this.taskVariables[4];
	}

	// sets the priority
	public void setPriority(String newPriority) {
		this.taskVariables[4] = newPriority;
	}

	// marks a task as done
	public void setTaskDone() {
		this.taskVariables[5] = DONE_STRING;
	}

	// marks a task as not done
	public void setTaskNotDone() {
		this.taskVariables[5] = null;
	}

}