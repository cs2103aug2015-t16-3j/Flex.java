// Flex.java
// Uses Task objects from Task.java
// Able to
// 1. Add a task - clashes with existing tasks, on the same date, which have not been marked as done
// are prevented from being added (invalid case)
// 2. Delete a task - tasks which do not exist, cannot be deleted (invalid case)
// 3. Edit a Task's variable (attribute), EXCEPT for its comparisonValue 
// 4. Automatically sort tasks by date and starting time after valid input for points 1. , 2. , and 3.
// 5. Show tasks by "numerical" priorityLevel (where "1" is the highest), but not alter the schedule file
// This method works only if the user has chosen to use positive integers as priority levels
// i.e. The user can choose to use alphabets instead, for the priority levels, for 1. and 2.
// 6. Search for tasks by one of the variables(attributes) for tasks, EXCEPT by the tasks' "comparisonValue" s
// 7. Undo the very last VALID action done for 1. , 2. and 3. - searching and showing(displaying) commands will not have their last VALID action saved 
// 8. Able to show tasks which has priority levels not being numbers (not all characters in the priority level string are numerical digits)

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class Flex{

	
	
	private static Scanner sc;
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	// that is, it is valid only if its starting time, or ending time, are NOT between the starting
	// and ending times of existing tasks which are NOT DONE YET
	private static final String NO_SEARCH_RESULTS_MESSSAGE = "Valid input, but with no search results."; 
	private static final String TASK_DOES_NOT_EXIST_MESSAGE = "Task does not exist, so no such task can be deleted.";
	private static final String EXIT_MESSAGE = "Exiting the program.";
	private static final String BLOCKED_MESSAGE = "Unable to add the new task, because the new task clashes with existing tasks (on the same date) which have not been marked as tasks which have been done.";
	
	private static final int HOUR_MINUTES = 60;
	
	// number of days in each month 
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
	
	
	public static void main(String[]args) throws IOException{
		// whereby the user's input is
		// for example:
		// java Flex.java schedule.txt
		String filename = args[0];
		
		// for checking
		// System.out.println("filename: " + filename);
		
		// this method takes care of the manipulation to be done, as well as the operation for exiting the program
		readAndExecuteCommand(filename, null, null, null);
	}
	
	static void readAndExecuteCommand(String filename, String lastChangeTerm, String lastAction, Task lastTask) throws IOException{
		String previousChangeTerm = new String();
		previousChangeTerm = lastChangeTerm;
		
		String previousAction = new String("");
		previousAction = lastAction;
		
		Task previousTask = new Task();
		previousTask = lastTask;

		
		sc = new Scanner(System.in);
		
		String command = sc.nextLine();
		
		command.trim();
		
		// for checking
		// System.out.println("command: " + command);
		
		String firstWord = new String("");
		
		int whitespaceIndex = 0;
		
		whitespaceIndex = command.indexOf(" ");
		
		if(whitespaceIndex < 0){
			// for checking
			// System.out.println("no whitespaces");
			
			firstWord = command;
			
			// Case 1: The program Flex.java will exit itself in Command Line Prompt (cmd).
			if(firstWord.equalsIgnoreCase("exit")){		
				System.out.println(EXIT_MESSAGE);
			   	return;
			}	
			// Case 2: undo the last action
			else if(firstWord.equalsIgnoreCase("undo")){
				// Note: This method will call readAndExecuteCommand again
				undo(filename, previousChangeTerm, previousAction, previousTask);
			}
			// Case 3: invalid input
			else{
				System.out.println(INVALID_INPUT_MESSAGE);
				
				readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
			}
		}
		else{
			//  for checking
			// System.out.println("at least 1 whitespace in user input command");
			
			// first word in the user's input, if there is a whitespace character in  it
			firstWord = command.substring(0, whitespaceIndex);
			firstWord.trim();
			
			// for checking
			// System.out.println("firstWord: " + firstWord);
			
			// Case 4: invalid input
			if(firstWord.substring(0, 1).equals("")){
				
				System.out.println(INVALID_INPUT_MESSAGE);
												
				readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
			}
			// Case 5: adding a task
			else if(firstWord.equalsIgnoreCase("add")){
				
				String remainingCommandString = command.substring(whitespaceIndex+1).trim();
				remainingCommandString.trim();
				
				if(remainingCommandString.equals("")){
	
					System.out.println(INVALID_INPUT_MESSAGE);
					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);						
				}				
				
				// check validity of input
				String remainingCommandStringCheck = new String("");
				remainingCommandStringCheck = remainingCommandString;
				remainingCommandStringCheck.trim();
				
				int commaWhitespaceIndex1 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex1 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex1 + 2).trim();
				
				int commaWhitespaceIndex2 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex2 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex2 + 2).trim();
				
				int commaWhitespaceIndex3 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex3 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex3 + 2).trim();
				
				int commaWhitespaceIndex4 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex4 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex4 + 2).trim();
				
				int commaWhitespaceIndex5 = remainingCommandStringCheck.indexOf(", ");			
				if(commaWhitespaceIndex5 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex5 + 2).trim();	
				
				int commaWhitespaceIndex6 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex6 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex6 + 2).trim();
				
				// only if input is valid
				// Note: This method will call readAndExecuteCommand again
				addTask(filename, remainingCommandString, previousChangeTerm, previousAction, previousTask);											
								
			}
			// Case 6: Deleting a task
			else if(firstWord.equalsIgnoreCase("delete")){
						
				String remainingCommandString = command.substring(whitespaceIndex+1);
				remainingCommandString.trim();
				
				if(remainingCommandString.equals("")){
					
					System.out.println(INVALID_INPUT_MESSAGE);
					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
					
				}				
								
				int whitespaceIndex1 = remainingCommandString.indexOf(" ");
				
				if(whitespaceIndex1 < 0){
					System.out.println(INVALID_INPUT_MESSAGE);
					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);						
				}
				
				// extract the date and task title, of the task to be deleted
				String date = new String("");
				date = remainingCommandString.substring(0, whitespaceIndex1).trim();
				String taskTitle = new String("");
				taskTitle = remainingCommandString.substring(whitespaceIndex1+1).trim();
				
				// only if input is valid
				deleteTask(filename, date, taskTitle, previousChangeTerm, previousAction, previousTask);							
													
			}
			// Case 7: changing a task's variable
			else if(firstWord.equalsIgnoreCase("change")){
				String remainingString = command.substring(whitespaceIndex+1).trim();		
				remainingString.trim();
				
				if(remainingString.equals("")){
					
					System.out.println(INVALID_INPUT_MESSAGE);
					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
					
				}			
				
				// only if input is valid
				// Note: This method will call readAndExecuteCommand again
				changeTaskVariable(filename, remainingString, previousChangeTerm,previousAction, previousTask);
				
			}		
			// Case 8: Search for tasks 
			// (ignoring upper and lower cases),
			// and displaying the search results
			else if(firstWord.equalsIgnoreCase("search")){
				String remainingString = command.substring(whitespaceIndex+1).trim();		
				remainingString.trim();
				
				if(remainingString.equals("")){
					
					System.out.println(INVALID_INPUT_MESSAGE);
					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
					
				}				
				
				// only if the input is valid
				// Note: This method will call readAndExecuteCommand again
				searchTask(filename, remainingString, previousChangeTerm, previousAction, previousTask);
				
				
			}
			// Case 9:
			// Show tasks organized in groups
			// or show all tasks
			// in the schedule file
			// without altering/editing/overwriting the schedule file
			else if(firstWord.equalsIgnoreCase("show")){
				String remainingString = command.substring(whitespaceIndex+1).trim();		
				remainingString.trim();

				// Note: The schedule file is already sorted by date and starting time 
				
				// Case 1: Show tasks by date, or show all tasks
				// By displaying all tasks in the schedule list
				if((remainingString.equalsIgnoreCase("by date"))||(remainingString.equalsIgnoreCase("all"))){					
					readAndDisplayAll(filename, previousChangeTerm, previousAction, previousTask);
				}
				// Case 2: Show tasks by starting time (in order of minutes)
				else if((remainingString.equalsIgnoreCase("by starting time"))||(remainingString.equalsIgnoreCase("by start"))||(remainingString.equalsIgnoreCase("by start time"))){
					sortAndShowByStartingTime(filename, previousChangeTerm, previousAction, previousTask);
				}
				// Case 3: Show tasks by ending time (in order of minutes)
				else if(remainingString.equalsIgnoreCase(("by ending time"))||(remainingString.equalsIgnoreCase("by end"))||(remainingString.equalsIgnoreCase("by end time"))){
					sortAndShowByEndingTime(filename, previousChangeTerm, previousAction, previousTask);
				}
				// Case 4: Show tasks by title (in alphabetical order)
				else if(remainingString.equalsIgnoreCase("by title")){
					sortAndShowByTaskTitle(filename, previousChangeTerm, previousAction, previousTask);
				}
				// Case 5: Show tasks by description (in alphabetical order)
				else if(remainingString.equalsIgnoreCase("by description")){
					sortAndShowByTaskDescription(filename, previousChangeTerm, previousAction, previousTask);
				}
				// Case 6: shows tasks sorted by priority,
				// or rather, tasks with the same priority will be grouped together
				// and only the tasks with the same number priorityLevels will be in numberical order
				// from the smallest to the biggest number for their priorityLevels
				else if(remainingString.equalsIgnoreCase("by priority")){
				sortAndShowByPriority(filename, previousChangeTerm, previousAction, previousTask);				
				}
				// Case 7: show tasks by category (in alphabetical order)
				// Meaning in this order - "blocked", "done", "pending"
				else if(remainingString.equalsIgnoreCase("by category")){
					sortAndShowByCategory(filename, previousChangeTerm, previousAction, previousTask);
				}
				// Case 8: show tasks which are done
				else if(remainingString.equalsIgnoreCase("done")){
					searchTask(filename, "category" + " " + "done", previousChangeTerm, previousAction, previousTask);
				}
				// Case 9: show tasks which are still pending
				else if(remainingString.equalsIgnoreCase("pending")){
					searchTask(filename, "category" + " " + "pending", previousChangeTerm, previousAction, previousTask);
				}
				// Case 10: show tasks which are still blocked
				else if(remainingString.equalsIgnoreCase("blocked")){
					searchTask(filename, "category" + " " + "blocked", previousChangeTerm, previousAction, previousTask);
				}				
				// Case 11: show tasks which are still pending or still blocked
				// that is, tasks which are not marked as done yet
				else if((remainingString.equalsIgnoreCase("blocked and pending"))||(remainingString.equalsIgnoreCase("blocked & pending"))||(remainingString.equalsIgnoreCase("pending and blocked"))||(remainingString.equalsIgnoreCase("pending & blocked"))||(remainingString.equalsIgnoreCase("not done"))){
					sortAndShowByNotDoneTasks(filename, previousChangeTerm, previousAction, previousTask);
				}		
				// Case 12: show the week starting on the date given by the user
				// the week includes the starting date of the week as one of the seven days in it
				else if(remainingString.equalsIgnoreCase("week")){
					showWeek(filename, previousChangeTerm, previousAction, previousTask);
				}	
				// Case 13: invalid input for user input command String starting with the word "show"
				else{
					System.out.println(INVALID_INPUT_MESSAGE);
					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
				}
				// Case 13: show the week starting on the date given by the user
				// the week includes the starting date of the week as one of the seven days in it
				
			}
			// case 10: If the user's command is invalid
			else{
				System.out.println(INVALID_INPUT_MESSAGE);
				readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
			}
		}
	}
	
	private static void showWeek(String filename, String previousChangeTerm, String previousAction, Task previousTask) throws IOException {
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
	
		ArrayList<Task> allTasksList = new ArrayList<Task>();
		
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		System.out.println("Please enter the starting date (format: dd/mm/yyyy): ");
		
		// day 1
		
		String date1 = sc.nextLine();
		
		// check if this input by the user is valid
		String tempDate = date1;
		
		int slashIndex1 = tempDate.indexOf("/");
		
		if(slashIndex1 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
		}
		tempDate = tempDate.substring(slashIndex1 + 1).trim();
		
		int slashIndex2 = tempDate.indexOf("/");	
		
		if(slashIndex2 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
		}
		
		date1.trim();
		
		searchAndShowTask(filename, "date " + date1, previousChangeTerm, previousAction, previousTask);
		
		// day 2
		String date2 = generateNextDate(date1);
		
		date2.trim();
		
		searchAndShowTask(filename, "date " + date2, previousChangeTerm, previousAction, previousTask);
		
		// day 3
		String date3 = generateNextDate(date2);
		
		date3.trim();
		
		searchAndShowTask(filename, "date " + date3, previousChangeTerm, previousAction, previousTask);
		
		// day 4
		
		String date4 = generateNextDate(date3);
		
		date4.trim();
		
		searchAndShowTask(filename, "date " + date4, previousChangeTerm, previousAction, previousTask);
		
		// day 5
		String date5 = generateNextDate(date4);
		
		date5.trim();
		
		searchAndShowTask(filename, "date " + date5, previousChangeTerm, previousAction, previousTask);
		
		// day 6
		String date6 = generateNextDate(date5);
		
		date6.trim();
		
		searchAndShowTask(filename, "date " + date6, previousChangeTerm, previousAction, previousTask);
		
		
		// day 7
		String date7 = generateNextDate(date6);
		
		date7.trim();
		
		searchAndShowTask(filename, "date " + date7, previousChangeTerm, previousAction, previousTask);
				
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
	}

	// the form of searching for tasks without executing readAndExecuteCommand() recursively
	// related to the user input command String "show week"
	private static void searchAndShowTask(String filename, String remainingCommandString, String previousChangeTerm,
			String previousAction, Task previousTask) throws IOException {
		// for checking
		// System.out.println("remainingCommandString for searchTask(): " + remainingCommandString);
								
		int whitespaceIndex1 = remainingCommandString.indexOf(" ");
			
		if(whitespaceIndex1 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
					
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
				
		String searchVariableType = new String("");
		searchVariableType = remainingCommandString.substring(0, whitespaceIndex1).trim();

		// for checking
		// System.out.println("searchVariableType: " + searchVariableType);
				
		String searchTerm = new String("");
		searchTerm = remainingCommandString.substring(whitespaceIndex1 + 1).trim();
				
		// for checking
		// System.out.println("searchTerm: " + searchTerm);
				
		// reads in the file, line by line
		BufferedReader reader = null;
				
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;		
		ArrayList<Task> allTasksList = new ArrayList<Task>();
								
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				// for checking
				// System.out.println("task added for searchTask()");
	
				allTasksList.add(new Task(currentLine));
			}
		}while(currentLine!=null);			
			
		if(reader!=null){
			reader.close();
		}				
				
		if(searchVariableType.equalsIgnoreCase("date")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getDate().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("start")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getStartingTime().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("end")){		
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getEndingTime().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("title")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getTaskTitle().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("description")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getTaskDescription().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("priority")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getPriorityLevel().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("category")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getCategory().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		// invalid input case
		else{
			System.out.println(INVALID_INPUT_MESSAGE);
		}			
	}

	// generates the next date, given the day, month and year of a date
	// assumed to be in the format dd/mm/yyyy
	private static String generateNextDate(String date) {
		
		String tempDate = date;
		
		// the three variables of the current date
		int slashIndex1 = tempDate.indexOf("/");
		int currentDay = Integer.valueOf(tempDate.substring(0, slashIndex1));
		tempDate = tempDate.substring(slashIndex1 + 1).trim();
		
		int slashIndex2 = tempDate.indexOf("/");		
		int currentMonth = Integer.valueOf(tempDate.substring(0, slashIndex2));
		
		int currentYear = Integer.valueOf(tempDate.substring(slashIndex2 + 1));		
		
		// the three variables of the next date
		int newDay = -1;
		int newMonth = -1;
		int newYear = -1;
				
		boolean isLeapYear = false;
		boolean isLastDayOfMonth = false;
		boolean isLastDayOfYear = false;

		if(currentYear%4==0){
			isLeapYear = true;
		}
		
		if(currentMonth==1){
			if(currentDay==JANUARY_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==2){
			// if it is a leap year, the last day of February is 29th of February for that year
			// Note: FEBRUARY_DAYS = 28;
			if(isLeapYear){
				if( currentDay == (FEBRUARY_DAYS + 1) ){
					isLastDayOfMonth = true;
				}
			}
			else{
				if( currentDay == (FEBRUARY_DAYS) ){
					isLastDayOfMonth = true;
				}
			}
		}
		else if(currentMonth==3){
			if(currentDay==MARCH_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==4){
			if(currentDay==APRIL_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==5){
			if(currentDay==MAY_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==6){
			if(currentDay==JUNE_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==7){
			if(currentDay==JULY_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==8){
			if(currentDay==AUGUST_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==9){
			if(currentDay==SEPTEMBER_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==10){
			if(currentDay==OCTOBER_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==11){
			if(currentDay==NOVEMBER_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==12){
			if(currentDay==DECEMBER_DAYS){
				isLastDayOfMonth = true;
				isLastDayOfYear = true;
			}
		}
		
		// Case 1: if the date given is the last day of the year, that is
		// 31st December of that year
		if(isLastDayOfYear){
			newDay = 1;
			newMonth = 1;
			newYear = currentYear + 1;
		}
		// Case 2: The given date is not the last day of the year,
		// but it is the last day of the month
		else if((!isLastDayOfYear)&&(isLastDayOfMonth)){
			newDay = 1;
			newMonth = currentMonth + 1; 
			newYear = currentYear;
		}
		// Case 3: The given date is not the last day of the year,
		// and also not the last day of the month
		else if((!isLastDayOfYear)&&(!isLastDayOfMonth)){
			newDay = currentDay + 1;
			newMonth = currentMonth;
			newYear = currentYear;
		}
		
		
		// for testing
		// System.out.println("current date: " + currentDate + ", next date: " + newDay +  "/" + newMonth + "/" + newYear);
		
		return newDay +  "/" + newMonth + "/" + newYear;
		
	}

	private static void sortAndShowByCategory(String filename, String previousChangeTerm, String previousAction,
			Task previousTask) throws IOException {
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
	
		ArrayList<Task> allTasksList = new ArrayList<Task>();
		
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		int size = allTasksList.size(); 
		int i, start, min_index;
				
		for(start=0; start<size-1; start++){
			min_index = start;
			
			for(i=start+1; i<size; i++){
				if(allTasksList.get(i).getCategory().compareToIgnoreCase(allTasksList.get(min_index).getCategory()) < 0) {		
					min_index = i;					
				}
			}
			
			Task temp1 = allTasksList.get(start);
			Task temp2 = allTasksList.get(min_index);
			allTasksList.set(start, temp2);
			allTasksList.set(min_index, temp1);
		}
		
		for(int j=0; j<allTasksList.size(); j++){
			System.out.println(allTasksList.get(j).printTaskString());
		}
		
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
	}

	private static void sortAndShowByTaskDescription(String filename, String previousChangeTerm, String previousAction,
			Task previousTask) throws IOException {
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
	
		ArrayList<Task> allTasksList = new ArrayList<Task>();
		
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		int size = allTasksList.size(); 
		int i, start, min_index;
				
		for(start=0; start<size-1; start++){
			min_index = start;
			
			for(i=start+1; i<size; i++){
				if(allTasksList.get(i).getTaskDescription().compareToIgnoreCase(allTasksList.get(min_index).getTaskDescription()) < 0) {		
					min_index = i;					
				}
			}
			
			Task temp1 = allTasksList.get(start);
			Task temp2 = allTasksList.get(min_index);
			allTasksList.set(start, temp2);
			allTasksList.set(min_index, temp1);
		}
		
		for(int j=0; j<allTasksList.size(); j++){
			System.out.println(allTasksList.get(j).printTaskString());
		}
		
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
	}

	private static void sortAndShowByTaskTitle(String filename, String previousChangeTerm, String previousAction,
			Task previousTask) throws IOException {
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
	
		ArrayList<Task> allTasksList = new ArrayList<Task>();
		
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		int size = allTasksList.size(); 
		int i, start, min_index;
				
		for(start=0; start<size-1; start++){
			min_index = start;
			
			for(i=start+1; i<size; i++){
				if(allTasksList.get(i).getTaskTitle().compareToIgnoreCase(allTasksList.get(min_index).getTaskTitle()) < 0) {		
					min_index = i;					
				}
			}
			
			Task temp1 = allTasksList.get(start);
			Task temp2 = allTasksList.get(min_index);
			allTasksList.set(start, temp2);
			allTasksList.set(min_index, temp1);
		}
		
		for(int j=0; j<allTasksList.size(); j++){
			System.out.println(allTasksList.get(j).printTaskString());
		}
		
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
	}

	private static void sortAndShowByEndingTime(String filename, String previousChangeTerm, String previousAction,
			Task previousTask) throws IOException {
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
	
		ArrayList<Task> allTasksList = new ArrayList<Task>();
		
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		int size = allTasksList.size(); 
		int i, start, min_index;
				
		for(start=0; start<size-1; start++){
			min_index = start;
			
			for(i=start+1; i<size; i++){
				int iEndingTimeValue = Integer.valueOf(allTasksList.get(i).getEndingTime().substring(0, 2)) * HOUR_MINUTES + Integer.valueOf(allTasksList.get(i).getEndingTime().substring(2, 4));
				int min_IndexEndingTimeValue = Integer.valueOf(allTasksList.get(min_index).getEndingTime().substring(0, 2)) * HOUR_MINUTES + Integer.valueOf(allTasksList.get(min_index).getEndingTime().substring(2, 4));
				
				if(iEndingTimeValue<min_IndexEndingTimeValue){
					min_index = i;	
				}
			}
			
			Task temp1 = allTasksList.get(start);
			Task temp2 = allTasksList.get(min_index);
			allTasksList.set(start, temp2);
			allTasksList.set(min_index, temp1);
		}
		
		for(int j=0; j<allTasksList.size(); j++){
			System.out.println(allTasksList.get(j).printTaskString());
		}
		
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
	}

	private static void sortAndShowByStartingTime(String filename, String previousChangeTerm, String previousAction,
			Task previousTask) throws IOException {
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
	
		ArrayList<Task> allTasksList = new ArrayList<Task>();
		
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}						
		
		int size = allTasksList.size(); 
		int i, start, min_index;
		
		for(start=0; start<size-1; start++){
			min_index = start;
			
			for(i=start+1; i<size; i++){
				int iEndingTimeValue = Integer.valueOf(allTasksList.get(i).getStartingTime().substring(0, 2)) * HOUR_MINUTES + Integer.valueOf(allTasksList.get(i).getStartingTime().substring(2, 4));
				int min_IndexEndingTimeValue = Integer.valueOf(allTasksList.get(min_index).getStartingTime().substring(0, 2)) * HOUR_MINUTES + Integer.valueOf(allTasksList.get(min_index).getStartingTime().substring(2, 4));
				
				if(iEndingTimeValue<min_IndexEndingTimeValue){
					min_index = i;	
				}
			}
			
			Task temp1 = allTasksList.get(start);
			Task temp2 = allTasksList.get(min_index);
			allTasksList.set(start, temp2);
			allTasksList.set(min_index, temp1);
		}
		
		for(int j=0; j<allTasksList.size(); j++){
			System.out.println(allTasksList.get(j).printTaskString());
		}
		
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
	}

	// sorts and displays all tasks in the schedule file, which are not done
	// without editing or overwriting the schedule file
	private static void sortAndShowByNotDoneTasks(String filename, String previousChangeTerm, String previousAction,
			Task previousTask) throws IOException {
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
	
		ArrayList<Task> allTasksList = new ArrayList<Task>();
		ArrayList<Task> notDoneList = new ArrayList<Task>();
		
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		for(int i=0; i<allTasksList.size(); i++){
			if(!allTasksList.get(i).getCategory().equalsIgnoreCase("done")){
				notDoneList.add(allTasksList.get(i));
			}
		}
		
		
		for(int j=0; j<notDoneList.size(); j++){
			System.out.println(notDoneList.get(j).printTaskString());
		}
		
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
	}
	
	// sorts and displays all tasks in the schedule file
	// without editing or overwriting the schedule file
	private static void readAndDisplayAll(String filename, String previousChangeTerm, String previousAction,
			Task previousTask) throws IOException {
			BufferedReader reader = null;
		
			reader = new BufferedReader(new FileReader(filename));
			String currentLine = null;
		
			ArrayList<Task> allTasksList = new ArrayList<Task>();;
						
			do{
				currentLine = reader.readLine();
				if(currentLine!=null){
					
					allTasksList.add(new Task(currentLine));				
				}
			}while(currentLine!=null);			
			
			if(reader!=null){
			reader.close();
			}				
		
			for(int j=0; j<allTasksList.size(); j++){
				System.out.println(allTasksList.get(j).printTaskString());
			}
		
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
	}

	// shows tasks sorted by priority
	// do take note that tasks (the lines) have already been sorted by date and starting time
	// when the file goes through add, delete or change
	// without overwriting or editing the schedule file
	// As tested,
	// if the priorityLevel is a number
	// it will be in order
	// But if the priorityLevel is not a number,
	// They will be grouped in different random groups together for such non-number priorityLevels
	// Upper and lower cases are ignored
	private static void sortAndShowByPriority(String filename, String previousChangeTerm, String previousAction,
			Task previousTask) throws IOException {
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
		
		ArrayList<Task> allTasksList = new ArrayList<Task>();
						
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
					
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		int size = allTasksList.size(); 
		int i, start, min_index;
				
		for(start=0; start<size-1; start++){
			min_index = start;
			
			for(i=start+1; i<size; i++){
				if(allTasksList.get(i).getPriorityLevel().compareToIgnoreCase(allTasksList.get(min_index).getPriorityLevel()) < 0) {		
					min_index = i;					
				}
			}
			
			Task temp1 = allTasksList.get(start);
			Task temp2 = allTasksList.get(min_index);
			allTasksList.set(start, temp2);
			allTasksList.set(min_index, temp1);
		}
		
		for(int j=0; j<allTasksList.size(); j++){
			System.out.println(allTasksList.get(j).printTaskString());
		}
	
		
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
	}

	// undo the previous VALID action, only if the previous action was adding a task,
	// deleting a task, 
	// or changing a task's variable
	// This is because there is no need to undo a search task
	private static void undo(String filename, String previousChangeTerm, String previousAction, Task previousTask) throws IOException {
		int whitespaceIndex1 = previousAction.trim().indexOf(" ");
		
		if(whitespaceIndex1 < 0 ){
		
			if(previousAction.equalsIgnoreCase("add")){
				// for checking
				// System.out.println("previousChangeTerm: " + previousChangeTerm);
				// System.out.println("previousAction: " + previousAction);
				// System.out.println("previousTask: " + previousTask.printTaskString());
				deleteTask(filename, previousTask.getDate(), previousTask.getTaskTitle(), previousChangeTerm, previousAction, previousTask);
			}
			else if(previousAction.equalsIgnoreCase("delete")){
				// for checking
				// System.out.println("previousChangeTerm: " + previousChangeTerm);
				// System.out.println("previousAction: " + previousAction);
				// System.out.println("previousTask: " + previousTask.printTaskString());
				addTask(filename, previousTask.printTaskString(), previousChangeTerm, previousAction, previousTask);
			}
		}
		else{
			
			if(previousAction.substring(0, whitespaceIndex1).trim().equalsIgnoreCase("change")){
				// for checking
				// System.out.println("previousChangeTerm: " + previousChangeTerm);
				// System.out.println("previousAction: " + previousAction);
				// System.out.println("previousTask: " + previousTask.printTaskString());	
				int whitespaceIndex2 = previousAction.indexOf(" ");			
				changeTaskVariable(filename, previousAction.substring(whitespaceIndex2 + 1).trim() + " " + previousTask.getDate() + ", " + previousTask.getTaskTitle() + ", " + previousChangeTerm, previousChangeTerm, previousAction, previousTask);
			}
		}
	}

	// adds a task
	private static void addTask(String filename, String remainingCommandString, String previousChangeTerm, String previousAction, Task previousTask) throws IOException {
		String remainingCommandString1 = remainingCommandString.trim();
		
		// for checking
		// System.out.println("remainingCommandString1 for addTask(): " + remainingCommandString1);
		
		// reads in the file, line by line
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
		
		ArrayList<Task> allTasksList = new ArrayList<Task>();
						
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				// for checking
				// System.out.println("task added for addTask()");
					
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		// for checking
		// System.out.println("BufferedReader closed");
		
		// for checking
		// System.out.println("remainingCommandString1 for addTask(), before allTasksList.add(new Task(remainingCommandString1)): " + remainingCommandString1);
		
		
		
		// for example
		// 14/9/2015, 1000, 1159, title two, description two, 1, blocked

		String remainingCommandString1StartingWithDate = new String("");
		remainingCommandString1StartingWithDate = remainingCommandString1;
		int commaWhitespaceIndex1 = remainingCommandString1StartingWithDate.indexOf(", ");
		
		// for checking
		// System.out.println("remainingCommandString1StartingWithDate: "+ remainingCommandString1StartingWithDate);
		String tempDate = new String("");
		tempDate = remainingCommandString1StartingWithDate.substring(0, commaWhitespaceIndex1);
		
		String remainingCommandString1StartingWithStartingTime = new String("");
		remainingCommandString1StartingWithStartingTime = remainingCommandString1StartingWithDate.substring(commaWhitespaceIndex1 + 2).trim();
		int commaWhitespaceIndex2 = remainingCommandString1StartingWithStartingTime.indexOf(", ");	
		String tempStartingTime = new String("");
		
		//for checking
		// System.out.println("remainingCommandString1StartingWithStartingTime: " + remainingCommandString1StartingWithStartingTime);
		
		tempStartingTime = remainingCommandString1StartingWithStartingTime.substring(0, commaWhitespaceIndex2);

		// for checking
		// System.out.println("tempStartingTime: " + tempStartingTime);
		
		String remainingCommandString1StartingWithEndingTime = new String("");
		remainingCommandString1StartingWithEndingTime = remainingCommandString1StartingWithStartingTime.substring(commaWhitespaceIndex2 +2).trim();
		int commaWhitespaceIndex3 = remainingCommandString1StartingWithEndingTime.indexOf(", ");	
		String tempEndingTime = new String("");
		
		// for checking
		// System.out.println("remainingCommandString1StartingWithEndingTime: " + remainingCommandString1StartingWithEndingTime);
		
		tempEndingTime = remainingCommandString1StartingWithEndingTime.substring(0, commaWhitespaceIndex3);
				
		// for checking
		// System.out.println("tempEndingTime: " + tempEndingTime);
		
		for (int i=0; i<allTasksList.size(); i++){
			if((allTasksList.get(i).getDate().equals(tempDate))&&(!allTasksList.get(i).getCategory().equalsIgnoreCase("done"))&&(((Integer.valueOf(allTasksList.get(i).getStartingTime()) <= Integer.valueOf(tempStartingTime))&&(Integer.valueOf(allTasksList.get(i).getEndingTime()) >= Integer.valueOf(tempStartingTime)))||((Integer.valueOf(allTasksList.get(i).getStartingTime()) <= Integer.valueOf(tempEndingTime))&&(Integer.valueOf(allTasksList.get(i).getEndingTime()) >= Integer.valueOf(tempEndingTime))))){
				
				System.out.println(BLOCKED_MESSAGE);					
				readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);												
			}
		}
		
		
		Task tempTask = new Task();
		
		allTasksList.add(new Task(remainingCommandString1));	
		
		tempTask = allTasksList.get(allTasksList.size()-1);
		
		// for checking
		// System.out.println("new task successfully added");
		
		// sort all tasks by date and starting time
		sortAllTasksByDateAndStartingTime(allTasksList);
		
		// overwrites to the file, line by line
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		for(int i=0; i<allTasksList.size(); i++){
			writer.write(allTasksList.get(i).printTaskString());
			writer.newLine();
		}
									
		writer.close();		
		
		// for checking
		// System.out.println("previousChangeTerm: "+ previousChangeTerm);
		// System.out.println("previousAction is set to add");		
		// System.out.println("previousTask(tempTask): " + tempTask.printTaskString());
		readAndExecuteCommand(filename, previousChangeTerm, "add", tempTask);	
				
	}	
	
		
	// deletes a task
	private static void deleteTask(String filename, String date, String taskTitle, String previousChangeTerm, String previousAction, Task previousTask) throws IOException {
		// reads in the file, line by line
		boolean taskExists = false;
		
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;		
		ArrayList<Task> allTasksList = new ArrayList<Task>();
						
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				// for checking
				// System.out.println("task added for deleteTask()");
				
				allTasksList.add(new Task(currentLine));
			}
			
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		Task tempTask = new Task();
		
		for(int i=0; i<allTasksList.size(); i++){
			if((allTasksList.get(i).getDate().equals(date))&&(allTasksList.get(i).getTaskTitle().equals(taskTitle))){
				tempTask = allTasksList.get(i);
				allTasksList.remove(i);
				taskExists = true;
				// for checking
				// System.out.println("matching task successfully deleted");
			}
		}
		
		// sort all tasks by date and starting time
		sortAllTasksByDateAndStartingTime(allTasksList);
		
		// overwrites to the file, line by line
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		for(int i=0; i<allTasksList.size(); i++){
			writer.write(allTasksList.get(i).printTaskString());
			writer.newLine();
		}

		if(taskExists == false){
			System.out.println(TASK_DOES_NOT_EXIST_MESSAGE);
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
		}
		
		writer.close();
		
		// for checking
		// System.out.println("previousChangeTerm: "+ previousChangeTerm);
		// System.out.println("previousAction is set to delete");		
		// System.out.println("previousTask(tempTask): " + tempTask.printTaskString());
		readAndExecuteCommand(filename, previousChangeTerm, "delete", tempTask);	
	}
	
	// search for tasks
	private static void searchTask(String filename, String remainingCommandString, String previousChangeTerm, String previousAction, Task previousTask) throws IOException{
		// for checking
		// System.out.println("remainingCommandString for searchTask(): " + remainingCommandString);
		
		boolean hasResultWithValidInput = false;
						
		int whitespaceIndex1 = remainingCommandString.indexOf(" ");
		
		if(whitespaceIndex1 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		String searchVariableType = new String("");
		searchVariableType = remainingCommandString.substring(0, whitespaceIndex1).trim();

		// for checking
		// System.out.println("searchVariableType: " + searchVariableType);
		
		String searchTerm = new String("");
		searchTerm = remainingCommandString.substring(whitespaceIndex1 + 1).trim();
		
		// for checking
		// System.out.println("searchTerm: " + searchTerm);
		
		// reads in the file, line by line
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;		
		ArrayList<Task> allTasksList = new ArrayList<Task>();
						
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				// for checking
				// System.out.println("task added for searchTask()");
			
				allTasksList.add(new Task(currentLine));
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		if(searchVariableType.equalsIgnoreCase("date")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getDate().equals(searchTerm)){
					allTasksList.get(i).printTask();
					hasResultWithValidInput = true;
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("start")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getStartingTime().equals(searchTerm)){
					allTasksList.get(i).printTask();
					hasResultWithValidInput = true;
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("end")){		
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getEndingTime().equals(searchTerm)){
					allTasksList.get(i).printTask();
					hasResultWithValidInput = true;
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("title")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getTaskTitle().equals(searchTerm)){
					allTasksList.get(i).printTask();
					hasResultWithValidInput = true;
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("description")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getTaskDescription().equals(searchTerm)){
					allTasksList.get(i).printTask();
					hasResultWithValidInput = true;
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("priority")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getPriorityLevel().equals(searchTerm)){
					allTasksList.get(i).printTask();
					hasResultWithValidInput = true;
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("category")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getCategory().equals(searchTerm)){
					allTasksList.get(i).printTask();
					hasResultWithValidInput = true;
				}
			}
		}
		// invalid input case
		else{
			System.out.println(INVALID_INPUT_MESSAGE);
		}		
		
		if(hasResultWithValidInput == false){
			System.out.println(NO_SEARCH_RESULTS_MESSSAGE);
		}
			
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	

	}
	
	// changes one of the variables in a task, EXCEPT for the comparison value
	// for sorting all tasks by date and starting time
	private static void changeTaskVariable(String filename, String remainingCommandString, String previousChangeTerm, String previousAction, Task previousTask) throws IOException {		
		// for checking
		// System.out.println("remainingCommandString for changeTaskVariable(): " + remainingCommandString);
		
		int whitespaceIndex1 = remainingCommandString.indexOf(" ");
		
		if(whitespaceIndex1 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		String changeVariableType = new String("");
		changeVariableType = remainingCommandString.substring(0, whitespaceIndex1).trim();
		
		// for checking
		// System.out.println("changeVariableType: " + changeVariableType);
		
		String changeRemainingString = new String("");
		changeRemainingString = remainingCommandString.substring(whitespaceIndex1 + 1).trim();
		changeRemainingString.trim();
		
		// for checking
		// System.out.println("changeRemainingString: " + changeRemainingString);
		
		int commaWhitespaceIndex1 = changeRemainingString.indexOf(", ");
		if(commaWhitespaceIndex1 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		String currentDate = new String("");
		currentDate = changeRemainingString.substring(0, commaWhitespaceIndex1);
		
		// for checking
		// System.out.println("currentDate: " + currentDate);
		
		changeRemainingString = changeRemainingString.substring(commaWhitespaceIndex1 + 2).trim();
		changeRemainingString.trim();
		
		// for checking
		// System.out.println("changeRemainingString: " + changeRemainingString);
		
		int commaWhitespaceIndex2 = changeRemainingString.indexOf(", ");
		if(commaWhitespaceIndex2 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		String currentTaskTitle = new String("");
		currentTaskTitle = changeRemainingString.substring(0, commaWhitespaceIndex2).trim();
		changeRemainingString.trim();
		
		// for checking
		// System.out.println("changeRemainingString: " + changeRemainingString);
		
		// for checking
		// System.out.println("currentTaskTitle: " + currentTaskTitle);
		
		changeRemainingString = changeRemainingString.substring(commaWhitespaceIndex2 + 2).trim();
		changeRemainingString.trim();
		
		// the original form of the changed variable, before it was changed
		String changedTerm = new String("");	
		
		// the new form of the changed variable
		String newTerm = new String("");
		newTerm = changeRemainingString;
		
		// for checking
		// System.out.println("newTerm: " + newTerm);
				
		// reads in the file, line by line
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;		
		ArrayList<Task> allTasksList = new ArrayList<Task>();
						
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				// for checking
				// System.out.println("task added for changeTaskVariable()");
				
				allTasksList.add(new Task(currentLine));
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
				
		Task tempTask = new Task();
		
		// last action done
		String lastAction = new String("");
		
		if(changeVariableType.equalsIgnoreCase("date")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getDate();
					allTasksList.get(i).setDate(newTerm);
					tempTask = allTasksList.get(i);
					lastAction = "change date";
				}
			}			
		}
		else if(changeVariableType.equalsIgnoreCase("start")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getStartingTime();
					allTasksList.get(i).setStartingTime(newTerm);
					tempTask = allTasksList.get(i);
					lastAction = "change start";
				}
			}		
		}
		else if(changeVariableType.equalsIgnoreCase("end")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getEndingTime();
					allTasksList.get(i).setEndingTime(newTerm);
					tempTask = allTasksList.get(i);
					lastAction = "change end";
				}
			}		
		}
		else if(changeVariableType.equalsIgnoreCase("title")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getTaskTitle();
					allTasksList.get(i).setTaskTitle(newTerm);
					tempTask = allTasksList.get(i);
					lastAction = "change title";
				}
			}		
		}
		else if(changeVariableType.equalsIgnoreCase("description")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getTaskDescription();
					allTasksList.get(i).setTaskDescription(newTerm);
					tempTask = allTasksList.get(i);
					lastAction = "change description";
				}
			}		
		}
		else if(changeVariableType.equalsIgnoreCase("priority")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getPriorityLevel().toString();
					allTasksList.get(i).setPriorityLevel(newTerm);
					tempTask = allTasksList.get(i);
					lastAction = "change priority";
				}
			}		
		}
		else if(changeVariableType.equalsIgnoreCase("category")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getCategory();
					allTasksList.get(i).setCategory(newTerm);
					tempTask = allTasksList.get(i);
					lastAction = "change category";
				}
			}		
		}
		// invalid input case
		else{
			System.out.println(INVALID_INPUT_MESSAGE);
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		// sort all tasks by date and starting time 
		sortAllTasksByDateAndStartingTime(allTasksList);
		
		// overwrites to the file, line by line
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		for(int i=0; i<allTasksList.size(); i++){
			writer.write(allTasksList.get(i).printTaskString());
			writer.newLine();
		}
									
		writer.close();
		
		
		// for valid input cases
		
		// for checking
		// System.out.println("previousChangeTerm(changedTerm): " + changedTerm);
		// System.out.println("previousAction is set to: " + lastAction);		
		// System.out.println("previousTask(tempTask): " + tempTask.printTaskString());
		readAndExecuteCommand(filename, changedTerm, lastAction, tempTask);	
		
		
	}

	
	// used to sort tasks by starting date and starting time
	private static void sortAllTasksByDateAndStartingTime(ArrayList<Task> allTasksList){
		int size = allTasksList.size(); 
		int i, start, min_index;
				
		for(start=0; start<size-1; start++){
			min_index = start;
			
			for(i=start+1; i<size; i++){
				if(allTasksList.get(i).getComparisonValue()<allTasksList.get(min_index).getComparisonValue()){		
					min_index = i;					
				}
			}
			
			Task temp1 = allTasksList.get(start);
			Task temp2 = allTasksList.get(min_index);
			allTasksList.set(start, temp2);
			allTasksList.set(min_index, temp1);
		}
	}
}			