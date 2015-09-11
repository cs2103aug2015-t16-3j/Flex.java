public class Task {
	private String date = null;
	private String startingTime = null;
	private String endingTime = null;
	private String taskTitle = null;
	private String taskDescription = null; 
	private String priorityLevel = null; 
	// [1 for the most important, higher numbers
	// for less  important tasks. The number should not be 0 or less than 0.]
	private String category = null;  
	private int comparisonValue = -1;
	// [this means the status: “blocked”, “pending”(which means not done), or “done”]
	
	// example of user input for adding a task
	// add 7/9/2015, 0800, 0959, buying groceries, buying fruits and vegetables, 1, pending
	// the constructor for Task, takes in everything excluding "add " (this String taken in will be trimmed before
	// it is taken in).
	private static final int YEAR_DAYS = 365;
	private static final int MONTH_DAYS = 30;
	private static final int DAY_HOURS = 24;
	private static final int HOUR_MINUTES = 60;
	
	// default constructor
	public Task(){		
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
		
		this.date = remainingString.substring(0, commaWhitespaceIndex1);
		remainingString = remainingString.substring(commaWhitespaceIndex1 + 2);
		
		// for checking:
		// System.out.println("this.date: " + this.date);
		
		// extracts the starting time
		int commaWhitespaceIndex2 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex2: " + commaWhitespaceIndex2);
		
		this.startingTime = remainingString.substring(0, commaWhitespaceIndex2);
		remainingString = remainingString.substring(commaWhitespaceIndex2 + 2);
		
		// for checking:
		// System.out.println("this.startingTime: " + this.startingTime);
		
		// extracts the ending time
		int commaWhitespaceIndex3 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex3: " + commaWhitespaceIndex3);
		
		this.endingTime = remainingString.substring(0, commaWhitespaceIndex3);
		remainingString = remainingString.substring(commaWhitespaceIndex3 + 2);
		
		// for checking:
		// System.out.println("this.endingTime: " + this.endingTime);
		
		// extracts the task title
		int commaWhitespaceIndex4 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex4: " + commaWhitespaceIndex4);
		
		this.taskTitle = remainingString.substring(0, commaWhitespaceIndex4);
		remainingString = remainingString.substring(commaWhitespaceIndex4 + 2);
		
		// for checking:
		//  System.out.println("this.taskTitle : " + this.taskTitle);
		
		// extracts the task description
		int commaWhitespaceIndex5 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex5: " + commaWhitespaceIndex5);
		
		this.taskDescription = remainingString.substring(0, commaWhitespaceIndex5);
		remainingString = remainingString.substring(commaWhitespaceIndex5 + 2);
		
		// for checking:
		// System.out.println("this.taskDescription : " + this.taskDescription);

		// extracts the priority level, and the category of the task
		int commaWhitespaceIndex6 = remainingString.indexOf(", ");
		
		// for checking
		// System.out.println("commaWhitespaceIndex6: " + commaWhitespaceIndex6);
		
		this.priorityLevel = remainingString.substring(0, commaWhitespaceIndex6);
		remainingString = remainingString.substring(commaWhitespaceIndex6 + 2);
		category = remainingString;		
		
		// for checking:
		// System.out.println("this.priorityLevel : " + this.priorityLevel);
		
		// for checking:
		// System.out.println("this.category : " + this.category);
		
		String temp = this.date;
		int slashIndex1 = temp.indexOf("/");
		int day = Integer.valueOf(temp.substring(0, slashIndex1));
		temp = temp.substring(slashIndex1 + 1);
		int slashIndex2 = temp.indexOf("/");
		int month = Integer.valueOf(temp.substring(0, slashIndex2));
		int year = Integer.valueOf(temp.substring(slashIndex2 + 1));
		
		int startingTimeHours = Integer.valueOf(this.startingTime.substring(0, 2));
		int startingTimeMinutes = Integer.valueOf(this.startingTime.substring(2));
		
		// used for sorting all tasks by date and starting time (taking year 0, 0000 hours as reference)
		comparisonValue = (day - 1) * DAY_HOURS * HOUR_MINUTES + (month - 1) * MONTH_DAYS * DAY_HOURS * HOUR_MINUTES + (year - 1) * YEAR_DAYS * MONTH_DAYS * DAY_HOURS * HOUR_MINUTES + startingTimeHours * HOUR_MINUTES + startingTimeMinutes;
		
		// for checking
		// System.out.println("comparisonValue= " + comparisonValue);
		
	}

	// variable accessors
	
	// retrieves the comparisonValue for sorting
	public int getComparisonValue(){
		return this.comparisonValue;
	}

	// retrieves the date
	public String getDate(){
		return this.date;
	}
	
	// retrieves the starting time
	public String getStartingTime(){
		return this.startingTime;
	}
	
	// retrieves the ending time
	public String getEndingTime(){
		return this.endingTime;
	}
	
	// retrieves the task title
	public String getTaskTitle(){
		return this.taskTitle;
	}
	
	// retrieves the task description
	public String getTaskDescription(){
		return this.taskDescription;
	}
	
	// retrieves the priority level
	public String getPriorityLevel(){
		return this.priorityLevel;
	}
	
	// retrieves the category
	public String getCategory(){
		return this.category;
	}
	
	// variable modifiers
	
	// changes the date
	public void setDate(String newDate){
		this.date = newDate;
	}
	
	// changes the starting Time
	public void setStartingTime(String newStartingTime){
		this.startingTime = newStartingTime;
	}
	
	// changes the ending time
	public void setEndingTime(String newEndingTime){
		this.endingTime = newEndingTime;
	}
	
	// changes the task title
	public void setTaskTitle(String newTaskTitle){
		this.taskTitle = newTaskTitle;
	}
	
	// changes the task description
	public void setTaskDescription(String newTaskDescription){
		this.taskDescription = newTaskDescription;
	}
	
	// changes the priority level
	public void setPriorityLevel(String newPriorityLevel){
		this.priorityLevel = newPriorityLevel;
	}
	
	// changes the category
	public void setCategory(String newCategory){
		this.category = newCategory;
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