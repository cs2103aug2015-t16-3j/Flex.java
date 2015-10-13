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
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.logging.*;

public class Flex{
	
	private static final Logger logger = Logger.getLogger(Flex.class.getName());
		
	private static FlexWindow flexWindow;
	private static Scanner sc;
	private static String filename = new String("");
	
	private static final String DONE_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are marked as " + "done" + " for their categories, are displayed.";
	private static final String PENDING_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are marked as " + "pending" + " for their categories, are displayed.";
	private static final String BLOCKED_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are marked as " + "blocked" + " for their categories, are displayed.";
	private static final String NOT_DONE_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are not marked as " + "done" +" for their categories, are displayed.";
	private static final String DEADLINE_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are marked as " + "deadline" + " for their categories, are displayed.";
	private static final String FLOATING_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are marked as " + "floating" + " for their categories, are displayed.";
	
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	
	private static final String EXIT_MESSAGE = "Exiting the program.";
	private static final String FILENAME_ACCEPTED_MESSAGE = "The filename is accepted.";
	private static final String PROCEED_MESSAGE = "Please proceed with the user input commands.";
	private static final String FILENAME_INPUT_MESSAGE = "Please enter the full path name of the .txt schedule file, including its name. For example: C:" + "\\" + "Users" + "\\" + "Owner" + "\\" + "Documents" + "\\" + "Flex" + "." + "java" + "\\" + "src" + "\\" + "FlexTest" + "." + "txt";

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
				CRUD.undo(filename, lastAction, flexWindow);
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
				
	
			}
			// Case 5: adding a task
			else if(firstWord.equalsIgnoreCase("add")){
				
				String remainingCommandString = command.substring(whitespaceIndex+1).trim();
				remainingCommandString.trim();
				
				
				if(Checker.checkFloatingTaskInput(remainingCommandString)||Checker.checkDeadlineTaskInput(remainingCommandString)||Checker.checkTask(remainingCommandString)){
					// only if input is valid
					// Note: This method will call readAndExecuteCommand again
					CRUD.addTask(filename, remainingCommandString, lastAction, flexWindow);		
				}
				else{
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");		
				
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
				}
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
				
				if(!Checker.checkDate(date)&&!date.equalsIgnoreCase("undefined")){			
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");	
					
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					
					readAndExecuteCommand(filename, lastAction, flexWindow);	
				}	
				
				String taskTitle = new String("");
				taskTitle = remainingCommandString.substring(whitespaceIndex1+1).trim();
				
				// only if input is valid
				CRUD.deleteTask(filename, date, taskTitle, lastAction, flexWindow);							
													
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
				CRUD.changeTaskVariable(filename, remainingString, lastAction, flexWindow);
				
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
				if((remainingString.equalsIgnoreCase("by date"))||(remainingString.equalsIgnoreCase("by day")||(remainingString.equalsIgnoreCase("all")))||(remainingString.equalsIgnoreCase("by task date"))||(remainingString.equalsIgnoreCase("by task day"))){					
					SortAndShow.readAndDisplayAll(filename, flexWindow);
				}
				// Case 9-[2]: Show tasks by starting time (in order of minutes)
				else if((remainingString.equalsIgnoreCase("by starting time"))||(remainingString.equalsIgnoreCase("by start"))||(remainingString.equalsIgnoreCase("by start time"))||(remainingString.equalsIgnoreCase("by task starting time"))||(remainingString.equalsIgnoreCase("by task start"))||(remainingString.equalsIgnoreCase("by task start time"))){
					SortAndShow.sortAndShowByStartingTime(filename, flexWindow);
				}
				// Case 9-[3]: Show tasks by ending time (in order of minutes)
				else if(remainingString.equalsIgnoreCase(("by ending time"))||(remainingString.equalsIgnoreCase("by end"))||(remainingString.equalsIgnoreCase("by end time"))||(remainingString.equalsIgnoreCase("by task ending time"))||(remainingString.equalsIgnoreCase("by task end"))||(remainingString.equalsIgnoreCase("by date"))||(remainingString.equalsIgnoreCase("by task end time"))){
					SortAndShow.sortAndShowByEndingTime(filename, flexWindow);
				}
				// Case 9-[4]: Show tasks by title (in alphabetical order)
				else if((remainingString.equalsIgnoreCase("by title"))||(remainingString.equalsIgnoreCase("by task title"))){
					SortAndShow.sortAndShowByTaskTitle(filename, flexWindow);
				}
				// Case 9-[5]: Show tasks by description (in alphabetical order)
				else if((remainingString.equalsIgnoreCase("by description"))||(remainingString.equalsIgnoreCase("by task description"))){
					SortAndShow.sortAndShowByTaskDescription(filename, flexWindow);
				}
				// Case 9-[6]: shows tasks sorted by priority,
				// or rather, tasks with the same priority will be grouped together
				// and only the tasks with the same number priorityLevels will be in numerical order
				// from the smallest to the biggest number for their priorityLevels
				else if(remainingString.equalsIgnoreCase("by priority")||(remainingString.equalsIgnoreCase("by task priority"))||(remainingString.equalsIgnoreCase("by priority level"))||(remainingString.equalsIgnoreCase("by task priority level"))){
					SortAndShow.sortAndShowByPriority(filename, flexWindow);				
				}
				// Case 9-[7]: show tasks by category (in alphabetical order)
				// Meaning in this order - "blocked", "done", "pending"
				else if(remainingString.equalsIgnoreCase("by category")||(remainingString.equalsIgnoreCase("by task category"))){
					SortAndShow.sortAndShowByCategory(filename, flexWindow);
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
				// Case 9-[11]: show tasks which are not marked as done yet
				else if((remainingString.equalsIgnoreCase("blocked and pending"))||(remainingString.equalsIgnoreCase("blocked & pending"))||(remainingString.equalsIgnoreCase("pending and blocked"))||(remainingString.equalsIgnoreCase("pending & blocked"))||(remainingString.equalsIgnoreCase("not done"))){
					logger.finest(NOT_DONE_TASKS_DISPLAYED_MESSAGE);
					System.out.println();
					
					SortAndShow.showNotDoneTasks(filename, flexWindow);
				}		
				else if(remainingString.equalsIgnoreCase("deadline")){
					logger.finest(DEADLINE_TASKS_DISPLAYED_MESSAGE);
					System.out.println(DEADLINE_TASKS_DISPLAYED_MESSAGE);
					System.out.println();
					
					SortAndShow.searchAndShowTask(filename, "category" + " " + "deadline", flexWindow);
				}	
				else if(remainingString.equalsIgnoreCase("floating")){
					logger.finest(FLOATING_TASKS_DISPLAYED_MESSAGE);
					System.out.println(FLOATING_TASKS_DISPLAYED_MESSAGE);
					System.out.println();
					
					SortAndShow.searchAndShowTask(filename, "category" + " " + "floating", flexWindow);
				}	
				// Case 9-[14]: show the week starting on the date given by the user
				// the week includes the starting date of the week as one of the seven days in it
				else if(remainingString.equalsIgnoreCase("week")){
					ShowDays.showWeek(filename, flexWindow);
				}			
				// Case 9-[15]: invalid input for user input command String starting with the word "show"
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

}			