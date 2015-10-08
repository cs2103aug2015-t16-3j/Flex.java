import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.logging.*;

public class SortAndShow {

	private static final Logger logger = Logger.getLogger(Flex.class.getName());

	private static FlexWindow flexWindow;
	private static Scanner sc;
	private static String filename = new String("");

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
	private static final String STARTING_TIME_LATER_THAN_ENDING_TIME_MESSAGE = "The new starting time is later than the current ending time.";

	private static final String ENDING_TIME_MISSING_MESSAGE = "The ending time is missing. Do take note that the ending time follows the 4-digit twenty-four-hour format.";
	private static final String DASH_IN_ENDING_TIME_MESSAGE = "At least one dash is in the ending time. Do take note that negative numbers are not allowed as well. Also, do take note that the ending time follows the 4-digit twenty-four-hour format.";	
	private static final String ENDING_TIME_NOT_A_NUMBER_MESSAGE = "The ending time is not a number.";
	private static final String ENDING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE = "The ending time is not a 4-digit number. Do take note that the ending time follows the 4-digit twenty-four-hour format.";
	private static final String ENDING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE = "The ending time is a number which is greater than 2359 (11:59pm).";
	private static final String ENDING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE = "The hours of the ending time is greater than 23.";
	private static final String ENDING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE = "The minutes of the ending time is greater than 59.";
	private static final String ENDING_TIME_EARLIER_THAN_STARTING_TIME_MESSAGE = "The new ending time is earlier than the curent starting time.";


	private static final String NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE = "The number of days, according to the month and year in this date, more than expected.";	

	private static final String DISPLAY_SORTED_BY_STARTING_TIMES_MESSAGE = "The tasks, sorted by starting time, are displayed.";
	private static final String DISPLAY_SORTED_BY_ENDING_TIMES_MESSAGE = "The tasks, sorted by ending time, are displayed.";
	private static final String DISPLAY_SORTED_BY_TITLES_MESSAGE = "The tasks, sorted in alphabetical order by title, are displayed.";
	private static final String DISPLAY_SORTED_BY_DESCRIPTIONS_MESSAGE = "The tasks, sorted in alphabetical order by task description, are displayed.";
	private static final String DISPLAY_SORTED_BY_PRIORITY_LEVELS_MESSAGE = "The tasks, sorted in alphabetical order by priority level, is displayed.";
	private static final String DISPLAY_SORTED_BY_CATEGORIES_MESSAGE = "The tasks, sorted in alphabetical order by category, are displayed.";

	private static final String NOTHING_TO_UNDO_MESSAGE = "Nothing to undo as no valid 1) adding of a task, 2) deleting of a task, OR 3) Changing a task variable, has been carried out by the user during this program run.";
	private static final String DELETED_MESSAGE = "The specified task has been deleted.";
	private static String ADDED_MESSAGE = "The task has been successfully added.";

	private static final String VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE = "Valid input provided, but there are no matching tasks to have their information changed.";

	private static final String STARTING_DATE_REQUEST_MESSAGE = "Please enter the starting date (format: dd/mm/yyyy): " + "\n";

	private static final String DONE_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are done, are displayed.";
	private static final String PENDING_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are pending, are displayed.";
	private static final String BLOCKED_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are blocked, are displayed.";
	private static final String TASKS_NOT_DONE_DISPLAYED_MESSAGE = "The tasks which have not been marked as done are displayed.";
	private static final String ALL_TASKS_DISPLAYED_MESSAGE = "All the tasks in the schedule are displayed.";
	private static final String TASKS_FOR_WEEK_DISPLAYED_FRONT_MESSAGE = "The tasks for the whole week starting on ";
	private static final String TASKS_FOR_WEEK_DISLAYED_BACK_MESSAGE = " are displayed.";

	private static final String DATE_GENERATED_MESSAGE = "The starting date provided by the user for displaying the specified week's tasks is valid. The next valid date generated is ";
	private static final String CHANGED_MESSAGE = "The change to the task information is valid and processed.";
	private static final String CHANGE_UNDONE_MESSAGE= "The last valid change action has been undone.";	
	private static final String DELETE_UNDONE_MESSAGE = "The last valid delete action has been undone.";
	private static final String ADD_UNDONE_MESSAGE = "The last valid add action has been undone.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	// that is, it is valid only if its starting time, or ending time, are NOT between the starting
	// and ending times of existing tasks which are NOT DONE YET
	private static final String NO_SEARCH_RESULTS_MESSSAGE = "Valid input, but with no search results."; 
	private static final String TASK_DOES_NOT_EXIST_MESSAGE = "Task does not exist, so no such task can be deleted.";
	private static final String EXIT_MESSAGE = "Exiting the program.";
	private static final String BLOCKED_MESSAGE = "Unable to add the new task, because the new task clashes with existing tasks (on the same date) which have not been marked as tasks which have been done.";
	private static final String FILENAME_ACCEPTED_MESSAGE = "The filename is accepted.";
	private static final String PROCEED_MESSAGE = "Please proceed with the user input commands.";
	private static final String FILENAME_INPUT_MESSAGE = "Please enter the full path name of the .txt schedule file, including its name. For example: C:" + "\\" + "Users" + "\\" + "Owner" + "\\" + "Documents" + "\\" + "Flex" + "." + "java" + "\\" + "src" + "\\" + "FlexTest" + "." + "txt";
	private static final int HOUR_MINUTES = 60;

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
	
	// the form of searching for tasks without executing readAndExecuteCommand() recursively
	// related to the user input command String "show week"
	static void searchAndShowTask(String filename, String remainingCommandString, FlexWindow flexWindow) throws IOException {
								
		int whitespaceIndex1 = remainingCommandString.indexOf(" ");
				
		if(whitespaceIndex1 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");		
			
			System.out.println();
			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			return;	
		}
					
		String searchVariableType = new String("");
		searchVariableType = remainingCommandString.substring(0, whitespaceIndex1).trim();
					
		String searchTerm = new String("");
		searchTerm = remainingCommandString.substring(whitespaceIndex1 + 1).trim();
					
		// reads in the file, line by line
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
					
		if(searchVariableType.equalsIgnoreCase("date")){
			// check if this input by the user is valid
			String tempDate = searchTerm;
				
			if(!Checker.checkDate(tempDate, flexWindow)){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
					
				return;
			}
				
			assert(Checker.checkDate(tempDate, flexWindow));
				
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getDate().equalsIgnoreCase(searchTerm)){
					flexWindow.getTextArea().append(allTasksList.get(i).getPrintTaskString() + "\n");
					flexWindow.getTextArea().append("\n");
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("start")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getStartingTime().equalsIgnoreCase(searchTerm)){
					flexWindow.getTextArea().append(allTasksList.get(i).getPrintTaskString() + "\n");
					flexWindow.getTextArea().append("\n");
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("end")){		
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getEndingTime().equalsIgnoreCase(searchTerm)){
					flexWindow.getTextArea().append(allTasksList.get(i).getPrintTaskString() + "\n");
					flexWindow.getTextArea().append("\n");
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("title")){
			for(int i=0; i<allTasksList.size(); i++){
				String tempSearchTerm = searchTerm.toLowerCase();
				String tempTaskTitle = allTasksList.get(i).getTaskTitle().toLowerCase();
				if(tempTaskTitle.indexOf(tempSearchTerm) >= 0){
					flexWindow.getTextArea().append(allTasksList.get(i).getPrintTaskString() + "\n");
					flexWindow.getTextArea().append("\n");
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("description")){
			for(int i=0; i<allTasksList.size(); i++){
				String tempSearchTerm = searchTerm.toLowerCase();
				String tempTaskDescription = allTasksList.get(i).getTaskDescription().toLowerCase();
				if(tempTaskDescription.indexOf(tempSearchTerm) >= 0){
					flexWindow.getTextArea().append(allTasksList.get(i).getPrintTaskString() + "\n");
					flexWindow.getTextArea().append("\n");
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("priority")){
			for(int i=0; i<allTasksList.size(); i++){
				String tempSearchTerm = searchTerm.toLowerCase();
				String tempPriority = allTasksList.get(i).getPriorityLevel().toLowerCase();
				if(tempPriority.indexOf(tempSearchTerm) >= 0){
					flexWindow.getTextArea().append(allTasksList.get(i).getPrintTaskString() + "\n");
					flexWindow.getTextArea().append("\n");
				}
			}
		}
		else if(searchVariableType.equalsIgnoreCase("category")){
			for(int i=0; i<allTasksList.size(); i++){
				String tempSearchTerm = searchTerm.toLowerCase();
				String tempCategory = allTasksList.get(i).getCategory().toLowerCase();
				if(tempCategory.indexOf(tempSearchTerm) >= 0){
					flexWindow.getTextArea().append(allTasksList.get(i).getPrintTaskString() + "\n");
					flexWindow.getTextArea().append("\n");
				}
			}
		}
		// invalid input case
		else{
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
				
			return;
		}			
	}	
	
	// sort and show tasks alphabetically by task title
	// without editing the schedule file
	static void sortAndShowByTaskTitle(String filename, String previousChangeTerm, String previousAction,
			Task previousTask, FlexWindow flexWindow) throws IOException {
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
			flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(DISPLAY_SORTED_BY_TITLES_MESSAGE);
		System.out.println(DISPLAY_SORTED_BY_TITLES_MESSAGE);
		System.out.println();
		
		flexWindow.getTextArea().append("\n");
	}
	// sort and show tasks by ending time
	// without editing the schedule file
	static void sortAndShowByEndingTime(String filename, String previousChangeTerm, String previousAction,
			Task previousTask, FlexWindow flexWindow) throws IOException {
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
			flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(DISPLAY_SORTED_BY_ENDING_TIMES_MESSAGE);
		System.out.println(DISPLAY_SORTED_BY_ENDING_TIMES_MESSAGE);
		System.out.println();
		
		flexWindow.getTextArea().append("\n");
	}

	
	// sort and show tasks alphabetically by starting time
	// without editing the schedule file
	static void sortAndShowByStartingTime(String filename, String previousChangeTerm, String previousAction,
		Task previousTask, FlexWindow flexWindow) throws IOException {
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
			flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(DISPLAY_SORTED_BY_STARTING_TIMES_MESSAGE);
		System.out.println(DISPLAY_SORTED_BY_STARTING_TIMES_MESSAGE);
		System.out.println();
		
		flexWindow.getTextArea().append("\n");
	}

	// sorts and displays all tasks in the schedule file, which are not done
	// without editing or overwriting the schedule file
	static void sortAndShowByNotDoneTasks(String filename, String previousChangeTerm, String previousAction,
			Task previousTask, FlexWindow flexWindow) throws IOException {
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
			flexWindow.getTextArea().append(notDoneList.get(j).getPrintTaskString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(TASKS_NOT_DONE_DISPLAYED_MESSAGE);
		System.out.println(TASKS_NOT_DONE_DISPLAYED_MESSAGE);
		System.out.println();
		
		flexWindow.getTextArea().append("\n");
	}
	
	// sorts and displays all tasks in the schedule file
	// without editing or overwriting the schedule file
	static void readAndDisplayAll(String filename, String previousChangeTerm, String previousAction,
			Task previousTask, FlexWindow flexWindow) throws IOException {
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
				flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
				flexWindow.getTextArea().append("\n");
			}

			logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
			System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append("\n");
	}

	// used to sort tasks by starting date and starting time
	static void sortAllTasksByDateAndStartingTime(ArrayList<Task> allTasksList){
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
	
	// sort and show tasks alphabetically by task description
	// without editing the schedule file
	static void sortAndShowByTaskDescription(String filename, String previousChangeTerm, String previousAction,
			Task previousTask, FlexWindow flexWindow) throws IOException {
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
			flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(DISPLAY_SORTED_BY_DESCRIPTIONS_MESSAGE);
		System.out.println(DISPLAY_SORTED_BY_DESCRIPTIONS_MESSAGE);
		System.out.println();
		
		flexWindow.getTextArea().append("\n");
	}
	
	// shows tasks sorted by priority level
	// without editing the schedule file
	static void sortAndShowByPriority(String filename, String previousChangeTerm, String previousAction,
			Task previousTask, FlexWindow flexWindow) throws IOException {
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
			flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(DISPLAY_SORTED_BY_PRIORITY_LEVELS_MESSAGE);
		System.out.println(DISPLAY_SORTED_BY_PRIORITY_LEVELS_MESSAGE);
		System.out.println();
		
		flexWindow.getTextArea().append("\n");
	}	
	
	// sort and show tasks by category
	// without editing the schedule file
	static void sortAndShowByCategory(String filename, String previousChangeTerm, String previousAction,
			Task previousTask, FlexWindow flexWindow) throws IOException {
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
			flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(DISPLAY_SORTED_BY_CATEGORIES_MESSAGE);
		System.out.println(DISPLAY_SORTED_BY_CATEGORIES_MESSAGE);
		System.out.println();
		
		flexWindow.getTextArea().append("\n");
	}	
}
