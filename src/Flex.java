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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.logging.*;

public class Flex{
	
	private static final Logger logger = Logger.getLogger(Flex.class.getName());
		
	private static FlexWindow flexWindow;
	private static Scanner sc;
	private static String filename = new String("");
	
	private static final String STARTING_TIME_LATER_THAN_ENDING_TIME_MESSAGE = "The new starting time is later than the current ending time.";
	
	private static final String ENDING_TIME_EARLIER_THAN_STARTING_TIME_MESSAGE = "The new ending time is earlier than the curent starting time.";
	
	private static final String NOTHING_TO_UNDO_MESSAGE = "Nothing to undo as no valid 1) adding of a task, 2) deleting of a task, OR 3) Changing a task variable, has been carried out by the user during this program run.";
	private static final String DELETED_MESSAGE = "The specified task has been deleted.";
	private static String ADDED_MESSAGE = "The task has been successfully added.";

	private static final String VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE = "Valid input provided, but there are no matching tasks to have their information changed.";

	private static final String DONE_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are done, are displayed.";
	private static final String PENDING_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are pending, are displayed.";
	private static final String BLOCKED_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are blocked, are displayed.";

	private static final String CHANGED_MESSAGE = "The change to the task information is valid and processed.";
	private static final String CHANGE_UNDONE_MESSAGE= "The last valid change action has been undone.";	
	private static final String DELETE_UNDONE_MESSAGE = "The last valid delete action has been undone.";
	private static final String ADD_UNDONE_MESSAGE = "The last valid add action has been undone.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	// that is, it is valid only if its starting time, or ending time, are NOT between the starting
	// and ending times of existing tasks which are NOT DONE YET
	private static final String TASK_DOES_NOT_EXIST_MESSAGE = "Task does not exist, so no such task can be deleted.";
	private static final String EXIT_MESSAGE = "Exiting the program.";
	private static final String BLOCKED_MESSAGE = "Unable to add the new task, because the new task clashes with existing tasks (on the same date) which have not been marked as tasks which have been done.";
	private static final String FILENAME_ACCEPTED_MESSAGE = "The filename is accepted.";
	private static final String PROCEED_MESSAGE = "Please proceed with the user input commands.";
	private static final String FILENAME_INPUT_MESSAGE = "Please enter the full path name of the .txt schedule file, including its name. For example: C:" + "\\" + "Users" + "\\" + "Owner" + "\\" + "Documents" + "\\" + "Flex" + "." + "java" + "\\" + "src" + "\\" + "FlexTest" + "." + "txt";
	private static final int HOUR_MINUTES = 60;

	// Note: The programs starts by typing "java Flex" in command line prompt.
	
	public static void main(String[]args) throws IOException{
		flexWindow = new FlexWindow();
		flexWindow.setTitle("Flex Display");
		flexWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		flexWindow.setBounds(100, 100, 450, 300);
		flexWindow.setVisible(true);
		
		logger.finest(FILENAME_INPUT_MESSAGE);
		System.out.println(FILENAME_INPUT_MESSAGE);
		System.out.println();
		
		flexWindow.getTextArea().setText(FILENAME_INPUT_MESSAGE + "\n");
		flexWindow.getTextArea().append("\n");
			
		filename = new String("");				
		sc = new Scanner(System.in);		
		filename = sc.nextLine();
		filename.trim();

		flexWindow.getTextArea().setText("");
		
		File tempFile = new File(filename);
		
		
		while((!tempFile.exists())||(filename.length()<=4)||(!filename.substring(filename.length()-4, filename.length()).equalsIgnoreCase(".txt"))){
			System.out.println();
			
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");
			
			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			flexWindow.getTextArea().append(FILENAME_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");
			
			logger.finest(FILENAME_INPUT_MESSAGE);
			System.out.println(FILENAME_INPUT_MESSAGE);
			System.out.println();
			
			filename = sc.nextLine();
			tempFile = new File(filename);
			
			flexWindow.getTextArea().setText("");
			
		}
		
		System.out.println();
		logger.finest(FILENAME_ACCEPTED_MESSAGE);
		System.out.println(FILENAME_ACCEPTED_MESSAGE);
		System.out.println();
		
		flexWindow.getTextArea().append(FILENAME_ACCEPTED_MESSAGE + "\n");
		flexWindow.getTextArea().append("\n");
	
		logger.finest(PROCEED_MESSAGE);
		System.out.println(PROCEED_MESSAGE);
		System.out.println();

		flexWindow.getTextArea().append(PROCEED_MESSAGE + "\n");
		flexWindow.getTextArea().append("\n");
			
		LastAction lastAction = new LastAction();
		
		// this method takes care of the manipulation to be done, as well as the operation for exiting the program	
		readAndExecuteCommand(filename, lastAction, flexWindow);
	}
	
	
	static void readAndExecuteCommand(String filename, LastAction lastAction, FlexWindow flexWindow) throws IOException{
		System.out.println();
		
		String previousChangeTerm = new String();
		
		previousChangeTerm = lastAction.getPreviousChangedTerm();
		
		String previousAction = new String("");
		previousAction = lastAction.getPreviousAction();
		
		Task previousTask = new Task();
		previousTask = lastAction.getPreviousTask();
		
		sc = new Scanner(System.in);
		
		String command = sc.nextLine();
		
		command.trim();
		
		System.out.println();
		
		String firstWord = new String("");
		
		int whitespaceIndex = 0;
		
		whitespaceIndex = command.indexOf(" ");
		
		// Note: clear the output display area after the user input command line has been entered
		flexWindow.getTextArea().setText("");
		
		if(whitespaceIndex < 0){
			
			firstWord = command;
			
			// Case 1: The program Flex.java will exit itself in Command Line Prompt (cmd).
			if(firstWord.equalsIgnoreCase("exit")){		
				flexWindow.getTextArea().append(EXIT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");
				
				logger.finest(EXIT_MESSAGE);
				System.out.println(EXIT_MESSAGE);
				System.out.println();
				
				System.exit(1);
			}	
			// Case 2: undo the last action
			else if(firstWord.equalsIgnoreCase("undo")){
				// Note: This method will call readAndExecuteCommand again
				undo(filename, lastAction, flexWindow);
			}
			// Case 3: invalid input
			else{
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");
				
				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				readAndExecuteCommand(filename, lastAction, flexWindow);
			}
		}
		else{
			
			// first word in the user's input, if there is a whitespace character in  it
			firstWord = command.substring(0, whitespaceIndex);
			firstWord.trim();
			
			// Case 4: invalid input
			if(firstWord.substring(0, 1).equalsIgnoreCase("")){
				
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");		
				
				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				readAndExecuteCommand(filename, lastAction, flexWindow);	
			}
			// Case 5: adding a task
			else if(firstWord.equalsIgnoreCase("add")){
				
				String remainingCommandString = command.substring(whitespaceIndex+1).trim();
				remainingCommandString.trim();
				
				if(remainingCommandString.equalsIgnoreCase("")){
	
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);						
				}				
				
				// check validity of input
				String remainingCommandStringCheck = new String("");
				remainingCommandStringCheck = remainingCommandString;
				remainingCommandStringCheck.trim();
				
				int commaWhitespaceIndex1 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex1 < 0){				
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");		
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex1 + 2).trim();
				
				int commaWhitespaceIndex2 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex2 < 0){				
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex2 + 2).trim();
				
				int commaWhitespaceIndex3 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex3 < 0){				
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex3 + 2).trim();
				
				int commaWhitespaceIndex4 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex4 < 0){				
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex4 + 2).trim();
				
				int commaWhitespaceIndex5 = remainingCommandStringCheck.indexOf(", ");			
				if(commaWhitespaceIndex5 < 0){				
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex5 + 2).trim();	
				
				int commaWhitespaceIndex6 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex6 < 0){				
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex6 + 2).trim();
				
				// only if input is valid
				// Note: This method will call readAndExecuteCommand again
				addTask(filename, remainingCommandString, lastAction, flexWindow);											
								
			}
			// Case 6: Deleting a task
			else if(firstWord.equalsIgnoreCase("delete")){
						
				String remainingCommandString = command.substring(whitespaceIndex+1);
				remainingCommandString.trim();
				
				if(remainingCommandString.equalsIgnoreCase("")){
					
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);						
				}				
								
				int whitespaceIndex1 = remainingCommandString.indexOf(" ");
				
				if(whitespaceIndex1 < 0){
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);						
				}
				
				
				// extract the date and task title, of the task to be deleted
				String date = new String("");
				
				date = remainingCommandString.substring(0, whitespaceIndex1).trim();
				
				if(!Checker.checkDate(date, flexWindow)){			
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");	
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);	
				}	
				
				assert(Checker.checkDate(date, flexWindow));
				
				String taskTitle = new String("");
				taskTitle = remainingCommandString.substring(whitespaceIndex1+1).trim();
				
				// only if input is valid
				deleteTask(filename, date, taskTitle, lastAction);							
													
			}
			// Case 7: changing a task's variable
			else if(firstWord.equalsIgnoreCase("change")){
				String remainingString = command.substring(whitespaceIndex+1).trim();		
				remainingString.trim();
				
				if(remainingString.equalsIgnoreCase("")){
					
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);	
					
				}			
				
				// only if input is valid
				// Note: This method will call readAndExecuteCommand again
				changeTaskVariable(filename, remainingString, lastAction, flexWindow);
				
			}		
			// Case 8: Search for tasks 
			// (ignoring upper and lower cases),
			// and displaying the search results
			else if(firstWord.equalsIgnoreCase("search")){
				String remainingString = command.substring(whitespaceIndex+1).trim();		
				remainingString.trim();
				
				if(remainingString.equalsIgnoreCase("")){
					
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);	
					
				}				
				
				// only if the input is valid
				// Note: This method will call readAndExecuteCommand again
				SortAndShow.searchAndShowTask(filename, remainingString, flexWindow);	
				
			}
			// Case 9:
			// Show tasks organized in groups
			// or show all tasks
			// in the schedule file
			// without altering/editing/overwriting the schedule file
			else if((firstWord.equalsIgnoreCase("show"))||(firstWord.equalsIgnoreCase("display"))){
				String remainingString = command.substring(whitespaceIndex+1).trim();		
				remainingString.trim();

				// Note: The schedule file is already sorted by date and starting time 
				
				// Case 9-[1]: Show tasks by date, or show all tasks
				// By displaying all tasks in the schedule list
				if((remainingString.equalsIgnoreCase("by date"))||(remainingString.equalsIgnoreCase("by day")||(remainingString.equalsIgnoreCase("all")))){					
					SortAndShow.readAndDisplayAll(filename, previousChangeTerm, previousAction, previousTask, flexWindow);
				}
				// Case 9-[2]: Show tasks by starting time (in order of minutes)
				else if((remainingString.equalsIgnoreCase("by starting time"))||(remainingString.equalsIgnoreCase("by start"))||(remainingString.equalsIgnoreCase("by start time"))){
					SortAndShow.sortAndShowByStartingTime(filename, previousChangeTerm, previousAction, previousTask, flexWindow);
				}
				// Case 9-[3]: Show tasks by ending time (in order of minutes)
				else if(remainingString.equalsIgnoreCase(("by ending time"))||(remainingString.equalsIgnoreCase("by end"))||(remainingString.equalsIgnoreCase("by end time"))){
					SortAndShow.sortAndShowByEndingTime(filename, previousChangeTerm, previousAction, previousTask, flexWindow);
				}
				// Case 9-[4]: Show tasks by title (in alphabetical order)
				else if((remainingString.equalsIgnoreCase("by title"))||(remainingString.equalsIgnoreCase("by task title"))){
					SortAndShow.sortAndShowByTaskTitle(filename, previousChangeTerm, previousAction, previousTask, flexWindow);
				}
				// Case 9-[5]: Show tasks by description (in alphabetical order)
				else if((remainingString.equalsIgnoreCase("by description"))||(remainingString.equalsIgnoreCase("by task description"))){
					SortAndShow.sortAndShowByTaskDescription(filename, previousChangeTerm, previousAction, previousTask, flexWindow);
				}
				// Case 9-[6]: shows tasks sorted by priority,
				// or rather, tasks with the same priority will be grouped together
				// and only the tasks with the same number priorityLevels will be in numerical order
				// from the smallest to the biggest number for their priorityLevels
				else if(remainingString.equalsIgnoreCase("by priority")){
					SortAndShow.sortAndShowByPriority(filename, previousChangeTerm, previousAction, previousTask, flexWindow);				
				}
				// Case 9-[7]: show tasks by category (in alphabetical order)
				// Meaning in this order - "blocked", "done", "pending"
				else if(remainingString.equalsIgnoreCase("by category")){
					SortAndShow.sortAndShowByCategory(filename, previousChangeTerm, previousAction, previousTask, flexWindow);
				}
				// Case 9-[8]: show tasks which are done
				else if(remainingString.equalsIgnoreCase("done")){

					logger.finest(DONE_TASKS_DISPLAYED_MESSAGE);
					System.out.println(DONE_TASKS_DISPLAYED_MESSAGE);
					System.out.println();
					
					SortAndShow.searchAndShowTask(filename, "category" + " " + "done", flexWindow);
					
				}
				// Case 9-[9]: show tasks which are still pending
				else if(remainingString.equalsIgnoreCase("pending")){
					
					logger.finest(PENDING_TASKS_DISPLAYED_MESSAGE);
					System.out.println(PENDING_TASKS_DISPLAYED_MESSAGE);
					System.out.println();
					
					SortAndShow.searchAndShowTask(filename, "category" + " " + "pending", flexWindow);		
				}
				// Case 9-[10]: show tasks which are still blocked
				else if(remainingString.equalsIgnoreCase("blocked")){
					
					logger.finest(BLOCKED_TASKS_DISPLAYED_MESSAGE);
					System.out.println(BLOCKED_TASKS_DISPLAYED_MESSAGE);
					System.out.println();
					
					SortAndShow.searchAndShowTask(filename, "category" + " " + "blocked", flexWindow);				
				}				
				// Case 9-[11]: show tasks which are still pending or still blocked
				// that is, tasks which are not marked as done yet
				else if((remainingString.equalsIgnoreCase("blocked and pending"))||(remainingString.equalsIgnoreCase("blocked & pending"))||(remainingString.equalsIgnoreCase("pending and blocked"))||(remainingString.equalsIgnoreCase("pending & blocked"))||(remainingString.equalsIgnoreCase("not done"))){
					SortAndShow.sortAndShowByNotDoneTasks(filename, previousChangeTerm, previousAction, previousTask, flexWindow);
				}		
				// Case 9-[12]: show the week starting on the date given by the user
				// the week includes the starting date of the week as one of the seven days in it
				else if(remainingString.equalsIgnoreCase("week")){
					ShowDays.showWeek(filename, previousChangeTerm, previousAction, previousTask, flexWindow);
				}	
				// Case 9-[13]: invalid input for user input command String starting with the word "show"
				else{
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
				}			
			}
			// case 10: If the user's command is invalid
			else{
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				readAndExecuteCommand(filename, lastAction, flexWindow);
			}
		}
		readAndExecuteCommand(filename, lastAction, flexWindow);
	}

	// undo the previous VALID action, only if the previous action was adding a task,
	// deleting a task, 
	// or changing a task's variable
	// This is because there is no need to undo a search task
	private static void undo(String filename, LastAction lastAction, FlexWindow flexWindow) throws IOException, NullPointerException {
		
		if(lastAction.getPreviousAction()==null || lastAction.getPreviousTask() ==null){
			flexWindow.getTextArea().append(NOTHING_TO_UNDO_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");	

			logger.finest(NOTHING_TO_UNDO_MESSAGE);
			System.out.println(NOTHING_TO_UNDO_MESSAGE);
			System.out.println();
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
		}
		
		int whitespaceIndex1 = lastAction.getPreviousAction().trim().indexOf(" ");
		

		
		if(whitespaceIndex1 < 0 ){
		
			if(lastAction.getPreviousAction().equalsIgnoreCase("add")){

				logger.finest(ADD_UNDONE_MESSAGE);
				System.out.println(ADD_UNDONE_MESSAGE);
				System.out.println();
				
				deleteTask(filename, lastAction.getPreviousTask().getDate(), lastAction.getPreviousTask().getTaskTitle(), lastAction);
				
			}
			else if(lastAction.getPreviousAction().equalsIgnoreCase("delete")){

				logger.finest(DELETE_UNDONE_MESSAGE);
				System.out.println(DELETE_UNDONE_MESSAGE);
				System.out.println();
				
				addTask(filename, lastAction.getPreviousTask().getPrintTaskString(), lastAction, flexWindow);
				
			}
		}
		else{
			
			if(lastAction.getPreviousAction().substring(0, whitespaceIndex1).trim().equalsIgnoreCase("change")){

				logger.finest(CHANGE_UNDONE_MESSAGE);
				System.out.println(CHANGE_UNDONE_MESSAGE);
				System.out.println();
				
				int whitespaceIndex2 = lastAction.getPreviousAction().indexOf(" ");			
				
				changeTaskVariable(filename, lastAction.getPreviousAction().substring(whitespaceIndex2 + 1).trim() + " " + lastAction.getPreviousTask().getDate() + ", " + lastAction.getPreviousTask().getTaskTitle() + ", " + lastAction.getPreviousChangedTerm(), lastAction, flexWindow);

			}
		}
	}
	
	// adds a task
	private static void addTask(String filename, String remainingCommandString, LastAction lastAction, FlexWindow flexWindow) throws IOException {
		String remainingCommandString1 = remainingCommandString.trim();
		
		boolean isTaskValid = true;
		
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
		
		// check for the validity of the potential Task's variables,
		// and print out error messages for only the first mistake made by the user,
		// for the Task String
		isTaskValid = Checker.checkTask(remainingCommandString1, flexWindow);
		
		// if the task is not valid, do not continue the process of adding a task
		if(!isTaskValid){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");	

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
		}
		
		
		// for example
		// 14/9/2015, 1000, 1159, title two, description two, 1, blocked

		String remainingCommandString1StartingWithDate = new String("");
		remainingCommandString1StartingWithDate = remainingCommandString1;
		int commaWhitespaceIndex1 = remainingCommandString1StartingWithDate.indexOf(", ");

		String tempDate = new String("");
		tempDate = remainingCommandString1StartingWithDate.substring(0, commaWhitespaceIndex1);
		
		String remainingCommandString1StartingWithStartingTime = new String("");
		remainingCommandString1StartingWithStartingTime = remainingCommandString1StartingWithDate.substring(commaWhitespaceIndex1 + 2).trim();
		int commaWhitespaceIndex2 = remainingCommandString1StartingWithStartingTime.indexOf(", ");	
		String tempStartingTime = new String("");
		
		tempStartingTime = remainingCommandString1StartingWithStartingTime.substring(0, commaWhitespaceIndex2);
		
		String remainingCommandString1StartingWithEndingTime = new String("");
		remainingCommandString1StartingWithEndingTime = remainingCommandString1StartingWithStartingTime.substring(commaWhitespaceIndex2 +2).trim();
		int commaWhitespaceIndex3 = remainingCommandString1StartingWithEndingTime.indexOf(", ");	
		String tempEndingTime = new String("");
	
		tempEndingTime = remainingCommandString1StartingWithEndingTime.substring(0, commaWhitespaceIndex3);
		
		for (int i=0; i<allTasksList.size(); i++){
			if((allTasksList.get(i).getDate().equalsIgnoreCase(tempDate))&&(!allTasksList.get(i).getCategory().equalsIgnoreCase("done"))&&(((Integer.valueOf(allTasksList.get(i).getStartingTime()) <= Integer.valueOf(tempStartingTime))&&(Integer.valueOf(allTasksList.get(i).getEndingTime()) >= Integer.valueOf(tempStartingTime)))||((Integer.valueOf(allTasksList.get(i).getStartingTime()) <= Integer.valueOf(tempEndingTime))&&(Integer.valueOf(allTasksList.get(i).getEndingTime()) >= Integer.valueOf(tempEndingTime))))){
				
				flexWindow.getTextArea().append(BLOCKED_MESSAGE + "\n");	
				flexWindow.getTextArea().append("\n");

				logger.finest(BLOCKED_MESSAGE);
				System.out.println(BLOCKED_MESSAGE);
				System.out.println();
				
				readAndExecuteCommand(filename, lastAction, flexWindow);												
			}
		}
		
		
		Task tempTask = new Task();
		
		allTasksList.add(new Task(remainingCommandString1));	
		
		tempTask = allTasksList.get(allTasksList.size()-1);
		
		// sort all tasks by date and starting time
		SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
		
		// overwrites to the file, line by line
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
				
		for(int i=0; i<allTasksList.size(); i++){
			writer.write(allTasksList.get(i).getPrintTaskString());
			flexWindow.getTextArea().append(allTasksList.get(i).getPrintTaskString() + "\n");
			flexWindow.getTextArea().append("\n");
			writer.newLine();
		}
									
		writer.close();		

		logger.finest(ADDED_MESSAGE);
		System.out.println(ADDED_MESSAGE);
		System.out.println();
		
		
		lastAction.setPreviousAction("add");
		lastAction.setPreviousTask(tempTask);
		
		flexWindow.getTextArea().append("\n");
		readAndExecuteCommand(filename, lastAction, flexWindow);	
				
	}	

	// deletes a task
	private static void deleteTask(String filename, String date, String taskTitle, LastAction lastAction) throws IOException {
		// reads in the file, line by line
		boolean taskExists = false;
		
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
		
		Task tempTask = new Task();
		
		for(int i=0; i<allTasksList.size(); i++){
			if((allTasksList.get(i).getDate().equalsIgnoreCase(date))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(taskTitle))){
				tempTask = allTasksList.get(i);
				allTasksList.remove(i);
				taskExists = true;
			}
		}
		
		if(taskExists == false){
			flexWindow.getTextArea().append(TASK_DOES_NOT_EXIST_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(TASK_DOES_NOT_EXIST_MESSAGE);
			System.out.println(TASK_DOES_NOT_EXIST_MESSAGE);
			System.out.println();
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
		}
		
		// sort all tasks by date and starting time
		SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
		
		// overwrites to the file, line by line
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		for(int i=0; i<allTasksList.size(); i++){
			writer.write(allTasksList.get(i).getPrintTaskString());
			flexWindow.getTextArea().append(allTasksList.get(i).getPrintTaskString() + "\n");
			flexWindow.getTextArea().append("\n");
			writer.newLine();
		}
		
		writer.close();

		logger.finest(DELETED_MESSAGE);
		System.out.println(DELETED_MESSAGE);
		System.out.println();
		
		lastAction.setPreviousAction("delete");
		lastAction.setPreviousTask(tempTask);
		
		flexWindow.getTextArea().append("\n");
		readAndExecuteCommand(filename, lastAction, flexWindow);	
	}
	
	// changes one of the variables in a task, EXCEPT for the comparison value
	// for sorting all tasks by date and starting time
	private static void changeTaskVariable(String filename, String remainingCommandString, LastAction lastAction, FlexWindow flexWindow) throws IOException {		
		boolean atLeastOneTaskChanged = false;
		int whitespaceIndex1 = remainingCommandString.indexOf(" ");
		
		if(whitespaceIndex1 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			readAndExecuteCommand(filename, lastAction, flexWindow);	
		}
		
		String changeVariableType = new String("");
		changeVariableType = remainingCommandString.substring(0, whitespaceIndex1).trim();
		
		String changeRemainingString = new String("");
		changeRemainingString = remainingCommandString.substring(whitespaceIndex1 + 1).trim();
		changeRemainingString.trim();
		
		int commaWhitespaceIndex1 = changeRemainingString.indexOf(", ");
		
		if(commaWhitespaceIndex1 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			readAndExecuteCommand(filename, lastAction, flexWindow);	
		}
		
		String tempDateString = new String("");
	
		String currentDate = new String("");
		
		currentDate = changeRemainingString.substring(0, commaWhitespaceIndex1);
				
		tempDateString = changeRemainingString.substring(0, commaWhitespaceIndex1);
		
		if(!Checker.checkDate(tempDateString, flexWindow)){			
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");	

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			readAndExecuteCommand(filename, lastAction, flexWindow);	
		}		
		
		assert(Checker.checkDate(tempDateString, flexWindow));
						
		changeRemainingString = changeRemainingString.substring(commaWhitespaceIndex1 + 2).trim();
		changeRemainingString.trim();
		
		int commaWhitespaceIndex2 = changeRemainingString.indexOf(", ");
		
		if(commaWhitespaceIndex2 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			readAndExecuteCommand(filename, lastAction, flexWindow);	
		}
		
		String currentTaskTitle = new String("");
		currentTaskTitle = changeRemainingString.substring(0, commaWhitespaceIndex2).trim();
		changeRemainingString.trim();
		
		changeRemainingString = changeRemainingString.substring(commaWhitespaceIndex2 + 2).trim();
		changeRemainingString.trim();
		
		// the original form of the changed variable, before it was changed
		String tempChangedTerm = new String("");	
		
		// the new form of the changed variable
		String newTerm = new String("");
		newTerm = changeRemainingString;
				
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
				
		Task tempTask = new Task();
		
		// last action done
		String previousAction = new String("");
		
		if(changeVariableType.equalsIgnoreCase("date")){
			
			// check if this input by the user is valid
			String newDate = newTerm;
						
			if(!Checker.checkDate(newDate, flexWindow)){			
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");	

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				readAndExecuteCommand(filename, lastAction, flexWindow);	
			}	
			
			assert(Checker.checkDate(newDate, flexWindow));
			
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getDate();
					allTasksList.get(i).setDate(newTerm);
					allTasksList.get(i).recalculateComparisonValue();
					
					// if the new change term(date) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow)){
						allTasksList.get(i).setDate(tempChangedTerm);
						allTasksList.get(i).recalculateComparisonValue();
						readAndExecuteCommand(filename, lastAction, flexWindow);
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow));
					
					atLeastOneTaskChanged = true;
					
					tempTask = allTasksList.get(i);
					previousAction = "change date";
					
					lastAction.setPreviousChangedTerm(tempChangedTerm);
					lastAction.setPreviousAction(previousAction);
					lastAction.setPreviousTask(tempTask);
				}
			}	
			
			// if no changes have been made
			if(!atLeastOneTaskChanged){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println();
						
				readAndExecuteCommand(filename, lastAction, flexWindow);	
			}
			
			
			// sort all tasks by date and starting time 
			SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
			
			// overwrites to the file, line by line
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			
			for(int j=0; j<allTasksList.size(); j++){
				writer.write(allTasksList.get(j).getPrintTaskString());
				writer.newLine();
				flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
										
			writer.close();

			logger.finest(CHANGED_MESSAGE);
			System.out.println(CHANGED_MESSAGE);
			System.out.println();	
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
			
		}
		else if(changeVariableType.equalsIgnoreCase("start")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getStartingTime();
					
					allTasksList.get(i).setStartingTime(newTerm);
					allTasksList.get(i).recalculateComparisonValue();
					
					// if the new change term(starting time) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow)){
						allTasksList.get(i).setStartingTime(tempChangedTerm);
						allTasksList.get(i).recalculateComparisonValue();
						readAndExecuteCommand(filename, lastAction, flexWindow);
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow));
					
					if( ( Integer.valueOf(allTasksList.get(i).getStartingTime().substring(0, 2)) * HOUR_MINUTES + Integer.valueOf(allTasksList.get(i).getStartingTime().substring(2, 4)) ) > ( Integer.valueOf(allTasksList.get(i).getEndingTime().substring(0, 2)) * HOUR_MINUTES + Integer.valueOf(allTasksList.get(i).getEndingTime().substring(2, 4)) ) ){
						allTasksList.get(i).setStartingTime(tempChangedTerm);
						System.out.println();
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");	
						
						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						
						flexWindow.getTextArea().append(STARTING_TIME_LATER_THAN_ENDING_TIME_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");	
						
						logger.finest(STARTING_TIME_LATER_THAN_ENDING_TIME_MESSAGE);
						System.out.print(STARTING_TIME_LATER_THAN_ENDING_TIME_MESSAGE);
						System.out.println();
						
						System.out.println();
						readAndExecuteCommand(filename, lastAction, flexWindow);
					}
					
					atLeastOneTaskChanged = true;
					
					tempTask = allTasksList.get(i);
					previousAction = "change start";
					
					lastAction.setPreviousChangedTerm(tempChangedTerm);
					lastAction.setPreviousAction(previousAction);
					lastAction.setPreviousTask(tempTask);
				}
			}		
			
			// if no changes have been made
			if(!atLeastOneTaskChanged){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println();
				

				
				readAndExecuteCommand(filename, lastAction, flexWindow);	
			}
			
			// sort all tasks by date and starting time 
			SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
			
			// overwrites to the file, line by line
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			
			for(int j=0; j<allTasksList.size(); j++){
				writer.write(allTasksList.get(j).getPrintTaskString());
				writer.newLine();
				flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
										
			writer.close();

			logger.finest(CHANGED_MESSAGE);
			System.out.println(CHANGED_MESSAGE);
			System.out.println();	
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
		}
		else if(changeVariableType.equalsIgnoreCase("end")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getEndingTime();
					allTasksList.get(i).setEndingTime(newTerm);
					
					// if the new change term(ending time) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow)){
						allTasksList.get(i).setEndingTime(tempChangedTerm);
						readAndExecuteCommand(filename, lastAction, flexWindow);
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow));
					
					if( ( Integer.valueOf(allTasksList.get(i).getStartingTime().substring(0, 2)) * HOUR_MINUTES + Integer.valueOf(allTasksList.get(i).getStartingTime().substring(2, 4)) ) > ( Integer.valueOf(allTasksList.get(i).getEndingTime().substring(0, 2)) * HOUR_MINUTES + Integer.valueOf(allTasksList.get(i).getEndingTime().substring(2, 4)) ) ){
						allTasksList.get(i).setEndingTime(tempChangedTerm);
						System.out.println();
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");
						
						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						
						flexWindow.getTextArea().append(ENDING_TIME_EARLIER_THAN_STARTING_TIME_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");
						
						logger.finest(ENDING_TIME_EARLIER_THAN_STARTING_TIME_MESSAGE);
						System.out.print(ENDING_TIME_EARLIER_THAN_STARTING_TIME_MESSAGE);
						System.out.println();
						
						System.out.println();
						readAndExecuteCommand(filename, lastAction, flexWindow);
					}
					
					atLeastOneTaskChanged = true;
					
					tempTask = allTasksList.get(i);
					previousAction = "change end";
					
					lastAction.setPreviousChangedTerm(tempChangedTerm);
					lastAction.setPreviousAction(previousAction);
					lastAction.setPreviousTask(tempTask);
				}
			}	
			
			// if no changes have been made
			if(!atLeastOneTaskChanged){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println();
				

				
				readAndExecuteCommand(filename, lastAction, flexWindow);	
			}
			
			// sort all tasks by date and starting time 
			SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
			
			// overwrites to the file, line by line
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			
			for(int j=0; j<allTasksList.size(); j++){
				writer.write(allTasksList.get(j).getPrintTaskString());
				writer.newLine();
				flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
										
			writer.close();

			logger.finest(CHANGED_MESSAGE);
			System.out.println(CHANGED_MESSAGE);
			System.out.println();	
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
		}
		else if(changeVariableType.equalsIgnoreCase("title")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getTaskTitle();
					allTasksList.get(i).setTaskTitle(newTerm);
					
					// if the new change term(task title) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow)){
						allTasksList.get(i).setTaskTitle(tempChangedTerm);
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");
						
						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						
						readAndExecuteCommand(filename, lastAction, flexWindow);
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow));
					
					atLeastOneTaskChanged = true;
					
					tempTask = allTasksList.get(i);
					previousAction = "change title";
					
					lastAction.setPreviousChangedTerm(tempChangedTerm);
					lastAction.setPreviousAction(previousAction);
					lastAction.setPreviousTask(tempTask);
				}
			
			}		
			
			// if no changes have been made
			if(!atLeastOneTaskChanged){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println();
				

				
				readAndExecuteCommand(filename, lastAction, flexWindow);	
			}
			
			// sort all tasks by date and starting time 
			SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
			
			// overwrites to the file, line by line
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			
			for(int j=0; j<allTasksList.size(); j++){
				writer.write(allTasksList.get(j).getPrintTaskString());
				writer.newLine();
				flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
										
			writer.close();

			logger.finest(CHANGED_MESSAGE);
			System.out.println(CHANGED_MESSAGE);
			System.out.println();	
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
		}
		else if(changeVariableType.equalsIgnoreCase("description")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getTaskDescription();
					allTasksList.get(i).setTaskDescription(newTerm);
					
					// if the new change term(task description) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow)){
						allTasksList.get(i).setTaskDescription(tempChangedTerm);
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");
						
						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						
						readAndExecuteCommand(filename, lastAction, flexWindow);
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow));
					
					atLeastOneTaskChanged = true;
					
					tempTask = allTasksList.get(i);
					previousAction = "change description";
					
					lastAction.setPreviousChangedTerm(tempChangedTerm);
					lastAction.setPreviousAction(previousAction);
					lastAction.setPreviousTask(tempTask);
				}
			}		
			
			// if no changes have been made
			if(!atLeastOneTaskChanged){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println();
				

				
				readAndExecuteCommand(filename, lastAction, flexWindow);	
			}
			
			// sort all tasks by date and starting time 
			SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
			
			// overwrites to the file, line by line
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			
			for(int j=0; j<allTasksList.size(); j++){
				writer.write(allTasksList.get(j).getPrintTaskString());
				writer.newLine();
				flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
										
			writer.close();

			logger.finest(CHANGED_MESSAGE);
			System.out.println(CHANGED_MESSAGE);
			System.out.println();	
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
		}
		else if(changeVariableType.equalsIgnoreCase("priority")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getPriorityLevel().toString();
					allTasksList.get(i).setPriorityLevel(newTerm);
					
					// if the new change term(priority level) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow)){
						allTasksList.get(i).setPriorityLevel(tempChangedTerm);
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");
						
						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						
						readAndExecuteCommand(filename, lastAction, flexWindow);
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow));
								
					atLeastOneTaskChanged = true;
					
					tempTask = allTasksList.get(i);
					previousAction = "change priority";
					
					lastAction.setPreviousChangedTerm(tempChangedTerm);
					lastAction.setPreviousAction(previousAction);
					lastAction.setPreviousTask(tempTask);
				}
			}		
			
			// if no changes have been made
			if(!atLeastOneTaskChanged){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println();
				
				readAndExecuteCommand(filename, lastAction, flexWindow);	
			}
			
			// sort all tasks by date and starting time 
			SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
			
			// overwrites to the file, line by line
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			
			for(int j=0; j<allTasksList.size(); j++){
				writer.write(allTasksList.get(j).getPrintTaskString());
				writer.newLine();
				flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
										
			writer.close();

			logger.finest(CHANGED_MESSAGE);
			System.out.println(CHANGED_MESSAGE);
			System.out.println();	
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
		}
		else if(changeVariableType.equalsIgnoreCase("category")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getCategory();
					allTasksList.get(i).setCategory(newTerm);
					
					// if the new change term(category) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow)){
						allTasksList.get(i).setCategory(tempChangedTerm);
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");
						
						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						
						readAndExecuteCommand(filename, lastAction, flexWindow);
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString(), flexWindow));
					
					atLeastOneTaskChanged = true;
					
					tempTask = allTasksList.get(i);
					previousAction = "change category";
					
					lastAction.setPreviousChangedTerm(tempChangedTerm);
					lastAction.setPreviousAction(previousAction);
					lastAction.setPreviousTask(tempTask);
				}
			}		
			
			// if no changes have been made
			if(!atLeastOneTaskChanged){
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				flexWindow.getTextArea().append(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE);
				System.out.println();
				
				
				
				readAndExecuteCommand(filename, lastAction, flexWindow);	
			}
			
			// sort all tasks by date and starting time 
			SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
			
			// overwrites to the file, line by line
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			
			for(int j=0; j<allTasksList.size(); j++){
				writer.write(allTasksList.get(j).getPrintTaskString());
				writer.newLine();
				flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
										
			writer.close();

			logger.finest(CHANGED_MESSAGE);
			System.out.println(CHANGED_MESSAGE);
			System.out.println();	
			
			readAndExecuteCommand(filename, lastAction, flexWindow);
		}
		// invalid input case
		else{
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			// sort all tasks by date and starting time 
			SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);
			
			// overwrites to the file, line by line
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			
			for(int j=0; j<allTasksList.size(); j++){
				writer.write(allTasksList.get(j).getPrintTaskString());
				writer.newLine();
				flexWindow.getTextArea().append(allTasksList.get(j).getPrintTaskString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
										
			writer.close();

			logger.finest(CHANGED_MESSAGE);
			System.out.println(CHANGED_MESSAGE);
			System.out.println();	
			
			readAndExecuteCommand(filename, lastAction, flexWindow);	
		}
	}

	



}			