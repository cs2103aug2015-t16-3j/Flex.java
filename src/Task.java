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
	private int deadlineOrEventTimeValue = -3;

	private double recurringTaskValue = 0.0;

	private int deadlineEndingTime = -3;
	private int eventStartingTime = -3;
	private int actualDay = -3;
	private int actualMonth = -3;
	private int actualYear = -3;

	private static final String DONE_STRING = "[done]";
	private static final int DAY_HOURS = 24;
	private static final int HOUR_MINUTES = 60;
	private static final int EVENT_TASK_STARTING_TIME_OFFSET_VALUE = DAY_HOURS * HOUR_MINUTES;

	// format of the different types of tasks

	// floating task:
	// "task"

	// deadline task:
	// "task, date, ending time"

	// normal task:
	// "task, date, starting time, ending time, priority"

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
					+ this.taskVariables[1] + " (deadline) " + DONE_STRING;

			// set the null variables
			this.taskVariables[2] = null;
			this.taskVariables[3] = null;
			this.taskVariables[5] = null;

			calculateAndSetComparisonValue(string);

		} else if (Checker.isRecurringTaskInput(string)) {
			// format: <taskname>; <start>-<end> every <day>
			String tempString = new String("");
			tempString = string.trim();

			int semicolonWhitespaceIndex1 = tempString.indexOf("; ");

			String taskName = tempString.substring(0, semicolonWhitespaceIndex1).trim();

			tempString = tempString.substring(semicolonWhitespaceIndex1 + 2).trim();

			int dashIndex1 = tempString.indexOf("-");

			String startingTime = tempString.substring(0, dashIndex1).trim();

			tempString = tempString.substring(dashIndex1 + 1).trim();

			int whitespaceEveryWhitespaceIndex = tempString.toLowerCase().indexOf(" every ");

			String endingTime = tempString.substring(0, whitespaceEveryWhitespaceIndex);

			tempString = tempString.substring(whitespaceEveryWhitespaceIndex + 7).trim();

			String day = tempString.trim();

			// format: <taskname>; <start>-<end> every <day>
			// set the non-null attributes
			this.taskVariables[0] = taskName;
			this.taskVariables[2] = day.toLowerCase();
			this.taskVariables[3] = startingTime;
			this.taskVariables[4] = endingTime;
			this.taskVariables[7] = taskName + "; " + startingTime + "-" + endingTime + " every " + day.toLowerCase();
			this.taskVariables[8] = taskName + ", " + startingTime + "-" + endingTime + " every " + day.toLowerCase();

			// set the null variables
			this.taskVariables[1] = null;
			this.taskVariables[5] = null;
			this.taskVariables[6] = null;

			calculateAndSetComparisonValue(string);

			calculateAndSetRecurringTaskValue(this.taskVariables[7]);

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

			calculateAndSetComparisonValue(string);

		}
	}

	// default Task constructor
	public Task() {
	}

	// calculates and sets comparisonValue for sorting tasks by date and
	// starting time
	// the String parameter "string" is to be the schedule (file) input String
	// of the
	// task
	private void calculateAndSetComparisonValue(String string) {
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

			this.eventStartingTime = startingTimeHours * HOUR_MINUTES + startingTimeMinutes;
			this.deadlineOrEventTimeValue = EVENT_TASK_STARTING_TIME_OFFSET_VALUE + startingTimeHours * HOUR_MINUTES
					+ startingTimeMinutes;
			this.actualDay = day;
			this.actualMonth = month;
			this.actualYear = year;

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

			this.deadlineOrEventTimeValue = EVENT_TASK_STARTING_TIME_OFFSET_VALUE + startingTimeHours * HOUR_MINUTES
					+ startingTimeMinutes;
			this.eventStartingTime = startingTimeHours * HOUR_MINUTES + startingTimeMinutes;
			this.actualDay = day;
			this.actualMonth = month;
			this.actualYear = year;

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

			int endingTimeTotal = Integer.valueOf(this.taskVariables[4].substring(0, 2)) * HOUR_MINUTES
					+ Integer.valueOf(this.taskVariables[4].substring(2, 4));

			this.deadlineOrEventTimeValue = endingTimeTotal;
			this.deadlineEndingTime = endingTimeTotal;
			this.actualDay = day;
			this.actualMonth = month;
			this.actualYear = year;

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

			int endingTimeTotal = Integer.valueOf(this.taskVariables[4].substring(0, 2)) * HOUR_MINUTES
					+ Integer.valueOf(this.taskVariables[4].substring(2, 4));

			this.deadlineOrEventTimeValue = endingTimeTotal;
			this.deadlineEndingTime = endingTimeTotal;
			this.actualDay = day;
			this.actualMonth = month;
			this.actualYear = year;

		} else if (Checker.isRecurringTaskInput(string)) {

		} else if (Checker.isFloatingTaskInput(string)) {

		} else if (Checker.isDoneFloatingTaskInput(string)) {

		}

	}

	// calculates and sets the recurring task's value for sorting only
	// recurring tasks by day and starting time
	// the String parameter "recurringTaskInputString" is to be the schedule
	// (file) String of the recurring task
	public void calculateAndSetRecurringTaskValue(String recurringTaskInputString) {
		String tempString = new String("");
		tempString = recurringTaskInputString.trim();

		if (!(Checker.isRecurringTaskInput(tempString))) {
			return;
		}

		String day = this.taskVariables[2].trim();

		double dayValue = 0.0;

		if (day.equalsIgnoreCase("monday")) {
			dayValue = 1;
		} else if (day.equalsIgnoreCase("tuesday")) {
			dayValue = 2;
		} else if (day.equalsIgnoreCase("wednesday")) {
			dayValue = 3;
		} else if (day.equalsIgnoreCase("thursday")) {
			dayValue = 4;
		} else if (day.equalsIgnoreCase("friday")) {
			dayValue = 5;
		} else if (day.equalsIgnoreCase("saturday")) {
			dayValue = 6;
		} else if (day.equalsIgnoreCase("sunday")) {
			dayValue = 7;
		}

		String startingTime = this.taskVariables[3].trim();

		double startingTimeHours = Double.valueOf(startingTime.substring(0, 2).trim());

		double startingTimeMinutes = Double.valueOf(startingTime.substring(2, 4).trim());

		this.recurringTaskValue = dayValue * DAY_HOURS * HOUR_MINUTES + startingTimeHours * HOUR_MINUTES
				+ startingTimeMinutes;
	}

	// retrieves the comparisonValue for sorting only recurring tasks by day and
	// starting time
	public double getRecurringTaskValue() {
		return this.recurringTaskValue;
	}

	// retrieves the deadlineOrEventTimeValue
	public int getDeadlineOrEventTimeValue() {
		return this.deadlineOrEventTimeValue;
	}

	// retrieves the task
	public String getTaskName() {
		return this.taskVariables[0];
	}

	// changes the task
	public void setTaskName(String newTaskName) {
		this.taskVariables[0] = newTaskName;

		// event task
		if (Checker.isEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5];

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5];

		} else if (Checker.isDoneEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5] + " " + DONE_STRING;

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5] + " " + DONE_STRING;

		} else if (Checker.isDeadlineTaskInput(this.taskVariables[7])) {
			// deadline task

			this.taskVariables[7] = this.taskVariables[0] + "; by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1];

			this.taskVariables[8] = this.taskVariables[0] + ", by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " (deadline)";

		} else if (Checker.isDoneDeadlineTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " " + DONE_STRING;

			this.taskVariables[8] = this.taskVariables[0] + ", by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " (deadline)" + " " + DONE_STRING;

		} else if (Checker.isRecurringTaskInput(this.taskVariables[7])) {
			// recurring task
			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " every " + this.taskVariables[2].toLowerCase();

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " every " + this.taskVariables[2].toLowerCase();

		} else if (Checker.isFloatingTaskInput(this.taskVariables[7])) {
			// floating task
			this.taskVariables[7] = this.taskVariables[0];
			this.taskVariables[8] = this.taskVariables[0];

		} else if (Checker.isDoneFloatingTaskInput(this.taskVariables[7])) {
			this.taskVariables[7] = this.taskVariables[0] + " " + DONE_STRING;
			this.taskVariables[7] = this.taskVariables[0] + " " + DONE_STRING;

		}

		calculateAndSetComparisonValue(this.taskVariables[7]);
		calculateAndSetRecurringTaskValue(this.taskVariables[7]);

	}

	// retrieves the date
	public String getDate() {
		return this.taskVariables[1];
	}

	// edits the date
	public void setDate(String newDate) {

		this.taskVariables[1] = newDate;

		// event task
		if (Checker.isEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5];

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5];

		} else if (Checker.isDoneEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5] + " " + DONE_STRING;

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5] + " " + DONE_STRING;

		} else if (Checker.isDeadlineTaskInput(this.taskVariables[7])) {
			// deadline task

			this.taskVariables[7] = this.taskVariables[0] + "; by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1];

			this.taskVariables[8] = this.taskVariables[0] + ", by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " (deadline)";

		} else if (Checker.isDoneDeadlineTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " " + DONE_STRING;

			this.taskVariables[8] = this.taskVariables[0] + ", by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " (deadline)" + " " + DONE_STRING;

		}

		calculateAndSetComparisonValue(this.taskVariables[7]);
		calculateAndSetRecurringTaskValue(this.taskVariables[7]);

	}

	// retrieves the day
	public String getDay() {
		return this.taskVariables[2];
	}

	// sets the day
	public void setDay(String newDay) {

		this.taskVariables[2] = newDay.toLowerCase();

		if (Checker.isRecurringTaskInput(this.taskVariables[7])) {
			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " every " + this.taskVariables[2].toLowerCase();

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " every " + this.taskVariables[2].toLowerCase();

		}

		calculateAndSetComparisonValue(this.taskVariables[7]);
		calculateAndSetRecurringTaskValue(this.taskVariables[7]);

	}

	// retrieves the starting time
	public String getStart() {
		return this.taskVariables[3];
	}

	// edits the starting time
	public void setStart(String newStart) {

		this.taskVariables[3] = newStart;

		// event task
		if (Checker.isEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5];
			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5];

		} else if (Checker.isDoneEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5] + " " + DONE_STRING;
			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5] + " " + DONE_STRING;

		} else if (Checker.isRecurringTaskInput(this.taskVariables[7])) {
			// recurring task
			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " every " + this.taskVariables[2].toLowerCase();

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " every " + this.taskVariables[2].toLowerCase();

		}
		calculateAndSetComparisonValue(this.taskVariables[7]);
		calculateAndSetRecurringTaskValue(this.taskVariables[7]);

	}

	// retrieves the ending time
	public String getEnd() {
		return this.taskVariables[4];

	}

	// edits the ending time
	public void setEnd(String newEnd) {
		this.taskVariables[4] = newEnd;

		// event task
		if (Checker.isEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5];

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5];

		} else if (Checker.isDoneEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5] + " " + DONE_STRING;

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5] + " " + DONE_STRING;

		} else if (Checker.isRecurringTaskInput(this.taskVariables[7])) {
			// recurring task

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " every " + this.taskVariables[2].toLowerCase();

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " every " + this.taskVariables[2].toLowerCase();

		} else if (Checker.isDeadlineTaskInput(this.taskVariables[7])) {
			// deadline task

			this.taskVariables[7] = this.taskVariables[0] + "; by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1];

			this.taskVariables[8] = this.taskVariables[0] + ", by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " (deadline)";

		} else if (Checker.isDoneDeadlineTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " " + DONE_STRING;

			this.taskVariables[8] = this.taskVariables[0] + ", by " + this.taskVariables[4] + " on "
					+ this.taskVariables[1] + " (deadline)" + " " + DONE_STRING;

		}

		calculateAndSetComparisonValue(this.taskVariables[7]);
		calculateAndSetRecurringTaskValue(this.taskVariables[7]);
	}

	// retrieves the priority
	public String getPriority() {
		return this.taskVariables[5];
	}

	// sets the priority
	public void setPriority(String newPriority) {
		this.taskVariables[5] = newPriority;

		// event task
		if (Checker.isEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5];

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5];

		} else if (Checker.isDoneEventTaskInput(this.taskVariables[7])) {

			this.taskVariables[7] = this.taskVariables[0] + "; " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + "; " + this.taskVariables[5] + " " + DONE_STRING;

			this.taskVariables[8] = this.taskVariables[0] + ", " + this.taskVariables[3] + "-" + this.taskVariables[4]
					+ " on " + this.taskVariables[1] + ", " + this.taskVariables[5] + " " + DONE_STRING;

		}

		calculateAndSetComparisonValue(this.taskVariables[7]);
		calculateAndSetRecurringTaskValue(this.taskVariables[7]);
	}

	// "DONE" GETTER
	public String getDone() {
		return this.taskVariables[6];
	}

	// "DONE" MODIFIERS

	// marks a task as done
	public void setDone() {

		if (this.taskVariables[6] == null) {
			this.taskVariables[6] = DONE_STRING;
			this.taskVariables[7] = this.taskVariables[7] + " " + DONE_STRING;
			this.taskVariables[8] = this.taskVariables[8] + " " + DONE_STRING;

		}
		calculateAndSetComparisonValue(this.taskVariables[7]);
		calculateAndSetRecurringTaskValue(this.taskVariables[7]);
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
	public void setNotDone() {

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
		calculateAndSetComparisonValue(this.taskVariables[7]);
		calculateAndSetRecurringTaskValue(this.taskVariables[7]);

	}

	public int getActualDay() {
		return this.actualDay;
	}

	public int getActualMonth() {
		return this.actualMonth;
	}

	public int getActualYear() {
		return this.actualYear;
	}

	public int getDeadlineEndingTime() {
		return this.deadlineEndingTime;
	}

	public int getEventStartingTime() {
		return this.eventStartingTime;
	}

}