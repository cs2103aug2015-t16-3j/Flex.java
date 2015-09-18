// Task.java
// Contains the variables (attributes) in a Task
// Flex.java should NOT allow the attribute "comparisonValue" to be edited


public class Task {
	// private String date = null;
	// private String startingTime = null;
	// private String endingTime = null;
	// private String taskTitle = null;
	// private String taskDescription = null; 
	// private String priorityLevel = null; 
	// [1 for the most important, higher numbers
	// for less  important tasks. The number should not be 0 or less than 0.]
	// private String category = null;  
	private int comparisonValue = -1;
	// [this means the status: “blocked”, “pending”(which means not done), or “done”]
	String[] taskVariables = new String[7];
	
	// example of user input for adding a task
	// add 7/9/2015, 0800, 0959, buying groceries, buying fruits and vegetables, 1, pending
	// the constructor for Task, takes in everything excluding "add " (this String taken in will be trimmed before
	// it is taken in).
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
	private static final int JUNE_ACCUMULATED_DAYS = JANUARY_DAYS + FEBRUARY_DAYS + MARCH_DAYS + APRIL_DAYS + MAY_DAYS + JUNE_DAYS;
	private static final int JULY_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS - NOVEMBER_DAYS - OCTOBER_DAYS - SEPTEMBER_DAYS - AUGUST_DAYS;
	private static final int AUGUST_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS - NOVEMBER_DAYS - OCTOBER_DAYS - SEPTEMBER_DAYS;
	private static final int SEPTEMBER_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS - NOVEMBER_DAYS - OCTOBER_DAYS;
	private static final int OCTOBER_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS - NOVEMBER_DAYS;
	private static final int NOVEMBER_ACCUMULATED_DAYS = YEAR_DAYS - DECEMBER_DAYS;
	private static final int DECEMBER_ACCUMULATED_DAYS = YEAR_DAYS;
	
	// default constructor required by Flex.java
	public Task() {
	}
	
	public Task(String taskInformation){
		// for checking
		// System.out.println("taskInformation for Task() constructor: " + taskInformation);
		
		String remainingString = new String("");
		remainingString = taskInformation;
		
		// for checking
		// System.out.println("remainingString for Task() constructor before trim(): " + remainingString);
		
		remainingString.trim();
		
		// for checking
		//  System.out.println("remainingString for Task() constructor after trim(): " + remainingString);
		
		// extracts the date
		int commaWhitespaceIndex1 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex1: " + commaWhitespaceIndex1);
		
		this.taskVariables[0] = remainingString.substring(0, commaWhitespaceIndex1).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex1 + 2);
		
		// for checking:
		// System.out.println("this.date: " + this.date);
		
		// extracts the starting time
		int commaWhitespaceIndex2 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex2: " + commaWhitespaceIndex2);
		
		this.taskVariables[1] = remainingString.substring(0, commaWhitespaceIndex2).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex2 + 2).trim();
		
		// for checking:
		// System.out.println("this.startingTime: " + this.startingTime);
		
		// extracts the ending time
		int commaWhitespaceIndex3 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex3: " + commaWhitespaceIndex3);
		
		this.taskVariables[2] = remainingString.substring(0, commaWhitespaceIndex3).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex3 + 2).trim();
		
		// for checking:
		// System.out.println("this.endingTime: " + this.endingTime);
		
		// extracts the task title
		int commaWhitespaceIndex4 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex4: " + commaWhitespaceIndex4);
		
		this.taskVariables[3] = remainingString.substring(0, commaWhitespaceIndex4).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex4 + 2).trim();
		
		// for checking:
		//  System.out.println("this.taskTitle : " + this.taskTitle);
		
		// extracts the task description
		int commaWhitespaceIndex5 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex5: " + commaWhitespaceIndex5);
		
		this.taskVariables[4] = remainingString.substring(0, commaWhitespaceIndex5).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex5 + 2).trim();
		
		// for checking:
		// System.out.println("this.taskDescription : " + this.taskDescription);

		// extracts the priority level, and the category of the task
		int commaWhitespaceIndex6 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex6: " + commaWhitespaceIndex6);
		
		this.taskVariables[5] = remainingString.substring(0, commaWhitespaceIndex6).trim();
		remainingString = remainingString.substring(commaWhitespaceIndex6 + 2).trim();
		this.taskVariables[6] = remainingString.trim();		
		
		// for checking:
		// System.out.println("this.priorityLevel : " + this.priorityLevel);
		
		// for checking:
		// System.out.println("this.category : " + this.category);
		
		// e.g. 7/9/2015
		String tempDateString = this.taskVariables[0];
		
		// for checking
		// System.out.println("tempDateString: " + ttempDateString);
		
		int slashIndex1 = tempDateString.indexOf("/");
		int day = Integer.valueOf(tempDateString.substring(0, slashIndex1).trim());
		
		// for checking
		// System.out.println("day: " + day);
		
		tempDateString = tempDateString.substring(slashIndex1 + 1);
		
		// for checking
		// System.out.println("temp: " + temp);
		
		// e.g. 31/12/2014 with starting time 0859
		int slashIndex2 = tempDateString.indexOf("/");
		
		// the month December (the 12th month of each year)
		int month = Integer.valueOf(tempDateString.substring(0, slashIndex2).trim());
		// for checking
		// System.out.println("month: " + month);
		
		// The year 2014
		int year = Integer.valueOf(tempDateString.substring(slashIndex2 + 1).trim());
		// for checking
		// System.out.println("year: " + year);
		
		// 8 hours
		int startingTimeHours = Integer.valueOf(this.taskVariables[1].substring(0, 2).trim());
		// for checking
		// System.out.println("startingTimeHours: " + startingTimeHours);
		
		// 59 minutes
		int startingTimeMinutes = Integer.valueOf(this.taskVariables[1].substring(2).trim());
		// for checking
		// System.out.println("startingTimeMinutes: " + startingTimeMinutes);
				
		// used for sorting all tasks by date and starting time (taking year 0, 0000 hours as reference)
		// (2014 - 1) / 4 = 2013 / 4 = 503 (not considering the remainder)
		int numberOfPastLeapYears = (year - 1) / 4;
		// (2014 - 1) - 503 = 2013 - 503 = 1500
		int numberOfPastNonLeapyears = (year - 1) - numberOfPastLeapYears;
		
		int leapYearFebruaryDay = 0;
		
		// 2014%4 = 1
		if((year%4==0) && ((month -1)>=2)){
			leapYearFebruaryDay = 1;
		}
		
		int numberOfAccumulatedPastDaysInCurrentYear = findNumberOfAccumulatedPastDaysInCurrentYear(month - 1, day - 1);
		int numberOfPastHours = startingTimeHours;
		int numberOfPastMinutes = startingTimeMinutes;
		
		// e.g. this.comparisonValue = 503 * 366 + 1500 * 365 + (NOVEMBER_ACCUMULATED_DAYS + 0) * 24 * 60 + 8 * 60 + 59;
		this.comparisonValue = numberOfPastLeapYears * LEAP_YEAR_DAYS + numberOfPastNonLeapyears * YEAR_DAYS * DAY_HOURS * HOUR_MINUTES + (numberOfAccumulatedPastDaysInCurrentYear + leapYearFebruaryDay) * DAY_HOURS * HOUR_MINUTES + numberOfPastHours * HOUR_MINUTES + numberOfPastMinutes;
		
		// for checking
		// System.out.println("comparisonValue= " + comparisonValue);
		
	}
	
	// e.g. 31/12/2014 with starting time 0859 gives numberOfPastMonths = 12 - 1 = 11, and numberOfPastDays = 31 - 1 = 30
	private int findNumberOfAccumulatedPastDaysInCurrentYear(int numberOfPastMonths, int numberOfPastDays) {
		int accumulatedNumberOfDaysInPastMonths = 0;
		
		if(numberOfPastMonths==1){			
			accumulatedNumberOfDaysInPastMonths = JANUARY_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==2){
			accumulatedNumberOfDaysInPastMonths = FEBRUARY_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==3){
			accumulatedNumberOfDaysInPastMonths = MARCH_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==4){
			accumulatedNumberOfDaysInPastMonths = APRIL_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==5){
			accumulatedNumberOfDaysInPastMonths = MAY_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==6){
			accumulatedNumberOfDaysInPastMonths = JUNE_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==7){
			accumulatedNumberOfDaysInPastMonths = JULY_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==8){
			accumulatedNumberOfDaysInPastMonths = AUGUST_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==9){
			accumulatedNumberOfDaysInPastMonths = SEPTEMBER_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==10){
			accumulatedNumberOfDaysInPastMonths = OCTOBER_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==11){
			accumulatedNumberOfDaysInPastMonths = NOVEMBER_ACCUMULATED_DAYS;
		}
		else if(numberOfPastMonths==12){
			accumulatedNumberOfDaysInPastMonths = DECEMBER_ACCUMULATED_DAYS;
		}		
		
		return accumulatedNumberOfDaysInPastMonths + numberOfPastDays;
	}

	// retrieves the comparisonValue for sorting
	public int getComparisonValue(){
		return this.comparisonValue;
	}

	// retrieves the date
	public String getDate(){
		return this.taskVariables[0];
	}
	
	// retrieves the starting time
	public String getStartingTime(){
		return this.taskVariables[1];
	}
	
	// retrieves the ending time
	public String getEndingTime(){
		return this.taskVariables[2];
	}
	
	// retrieves the task title
	public String getTaskTitle(){
		return this.taskVariables[3];
	}
	
	// retrieves the task description
	public String getTaskDescription(){
		return this.taskVariables[4];
	}
	
	// retrieves the priority level
	public String getPriorityLevel(){
		return this.taskVariables[5];
	}
	
	// retrieves the category
	public String getCategory(){
		return this.taskVariables[6];
	}
	
	// variable modifiers
	
	// changes the date
	public void setDate(String newDate){
		this.taskVariables[0] = newDate;
	}
	
	// changes the starting Time
	public void setStartingTime(String newStartingTime){
		this.taskVariables[1] = newStartingTime;
	}
	
	// changes the ending time
	public void setEndingTime(String newEndingTime){
		this.taskVariables[2] = newEndingTime;
	}
	
	// changes the task title
	public void setTaskTitle(String newTaskTitle){
		this.taskVariables[3] = newTaskTitle;
	}
	
	// changes the task description
	public void setTaskDescription(String newTaskDescription){
		this.taskVariables[4] = newTaskDescription;
	}
	
	// changes the priority level
	public void setPriorityLevel(String newPriorityLevel){
		this.taskVariables[5] = newPriorityLevel;
	}
	
	// changes the category
	public void setCategory(String newCategory){
		this.taskVariables[6] = newCategory;
	}	
	
	// String form of the display on the screen for the task
	public String printTaskString(){
		return this.getDate() + ", " + this.getStartingTime() + ", " + this.getEndingTime() + ", " + this.getTaskTitle() + ", " + this.getTaskDescription() + ", " + this.getPriorityLevel() + ", " + this.getCategory();
		// return "date: " + this.getDate() + ", " + this.getStartingTime() + " to " + this.getEndingTime() + ", task title: " + this.getTaskTitle() + ", task description: " + this.getTaskDescription() + ", priority level: " + this.getPriorityLevel() + ", category: " + this.getCategory();	
	}
	// displays the task on the screen
	public void printTask(){
		System.out.println(this.getDate() + ", " + this.getStartingTime() + ", " + this.getEndingTime() + ", " + this.getTaskTitle() + ", " + this.getTaskDescription() + ", " + this.getPriorityLevel() + ", " + this.getCategory());
		// System.out.println("date: " + this.getDate() + ", " + this.getStartingTime() + " to " + this.getEndingTime() + ", task title: " + this.getTaskTitle() + ", task description: " + this.getTaskDescription() + ", priority level: " + this.getPriorityLevel() + ", category: " + this.getCategory());	
	}
}