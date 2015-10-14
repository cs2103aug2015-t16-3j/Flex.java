import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.*;

public class CRUD {
	private static final Logger logger = Logger.getLogger(CRUD.class.getName());
			
	private static final String NOTHING_TO_UNDO_MESSAGE = "Nothing to undo as no valid 1) adding of a task, 2) deleting of a task, OR 3) Changing a task variable, has been carried out by the user during this program run.";
	private static final String DELETED_MESSAGE = "The specified task has been deleted.";
	private static String ADDED_MESSAGE = "The task has been successfully added.";

	private static final String VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE = "Valid input provided, but there are no matching tasks to have their information changed.";
	
	private static final String STARTING_TIME_LATER_THAN_ENDING_TIME_MESSAGE = "The new starting time is later than the current ending time.";
	private static final String ENDING_TIME_EARLIER_THAN_STARTING_TIME_MESSAGE = "The new ending time is earlier than the curent starting time.";
	
	private static final String CHANGED_MESSAGE = "The change to the task information is valid and processed.";
	private static final String CHANGE_UNDONE_MESSAGE= "The last valid change action has been undone.";	
	private static final String DELETE_UNDONE_MESSAGE = "The last valid delete action has been undone.";
	private static final String ADD_UNDONE_MESSAGE = "The last valid add action has been undone.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";

	private static final String TASK_DOES_NOT_EXIST_MESSAGE = "Task does not exist, so no such task can be deleted.";
	private static final String BLOCKED_MESSAGE = "Unable to add the new task, because the new task clashes with existing tasks (on the same date) which have not been marked as tasks which have been done.";
	private static final int HOUR_MINUTES = 60;
	
	
	// deletes a task
	static void deleteTask(String filename, String date, String taskTitle, LastAction lastAction, FlexWindow flexWindow) throws IOException {
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
			
			return;
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
		return;	
	}
	
	// changes one of the variables in a task, EXCEPT for the comparison value
	// for sorting all tasks by date and starting time
	static void changeTaskVariable(String filename, String remainingCommandString, LastAction lastAction, FlexWindow flexWindow) throws IOException {		
		boolean atLeastOneTaskChanged = false;
		int whitespaceIndex1 = remainingCommandString.indexOf(" ");
		
		if(whitespaceIndex1 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			return;	
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
			
			return;	
		}
		
		String tempDateString = new String("");
	
		String currentDate = new String("");
		
		currentDate = changeRemainingString.substring(0, commaWhitespaceIndex1);
				
		tempDateString = changeRemainingString.substring(0, commaWhitespaceIndex1);
		
		if(!Checker.checkDate(tempDateString)&&!tempDateString.equalsIgnoreCase("undefined")){			
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");	

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			return;	
		}				
						
		changeRemainingString = changeRemainingString.substring(commaWhitespaceIndex1 + 2).trim();
		changeRemainingString.trim();
		
		int commaWhitespaceIndex2 = changeRemainingString.indexOf(", ");
		
		if(commaWhitespaceIndex2 < 0){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			return;	
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
		
		if(changeVariableType.equalsIgnoreCase("date")||changeVariableType.equalsIgnoreCase("day")){
			
			// check if this input by the user is valid
			String newDate = newTerm;
						
			if(!Checker.checkDate(newDate)&&!newDate.equalsIgnoreCase("undefined")){			
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");	

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				
				return;	
			}	
			
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getDate();
					allTasksList.get(i).setDate(newTerm);
					if(!((allTasksList.get(i).getDate().equalsIgnoreCase("undefined")) || (allTasksList.get(i).getStartingTime().equalsIgnoreCase("undefined")))){
						allTasksList.get(i).recalculateComparisonValue();
					}
					// if the new change term(date) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString())&&!Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())&&!Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setDate(tempChangedTerm);
						if(!((allTasksList.get(i).getDate().equalsIgnoreCase("undefined")) || (allTasksList.get(i).getStartingTime().equalsIgnoreCase("undefined")))){
							allTasksList.get(i).recalculateComparisonValue();
						}
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");

						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						return;
					}
					
					
					
					atLeastOneTaskChanged = true;
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString())||Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())||Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString()));				
					
					if(Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("floating");
					}
					else if(Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("deadline");
					}
					else if(((allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))||allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))&&Checker.checkTask(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("default");
					}

											
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
						
				return;	
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
			
			return;
			
		}
		else if(changeVariableType.equalsIgnoreCase("start")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getStartingTime();
					
					allTasksList.get(i).setStartingTime(newTerm);
					if(!((allTasksList.get(i).getDate().equalsIgnoreCase("undefined")) || (allTasksList.get(i).getStartingTime().equalsIgnoreCase("undefined")))){
						allTasksList.get(i).recalculateComparisonValue();
					}
					
					// if the new change term(starting time) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString())&&!Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())&&!Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setDate(tempChangedTerm);
						if(!((allTasksList.get(i).getDate().equalsIgnoreCase("undefined")) || (allTasksList.get(i).getStartingTime().equalsIgnoreCase("undefined")))){
							allTasksList.get(i).recalculateComparisonValue();
						}
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");

						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						return;
					}
					if((!allTasksList.get(i).getStartingTime().equalsIgnoreCase("undefined"))&&(!allTasksList.get(i).getEndingTime().equalsIgnoreCase("undefined"))){
					
					
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
							return;
						}
					}
					atLeastOneTaskChanged = true;
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString())||Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())||Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString()));
					
					if(Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("floating");
					}
					else if(Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("deadline");
					}
					else if(((allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))||allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))&&Checker.checkTask(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("default");
					}

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

				return;	
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
			
			return;
		}
		else if(changeVariableType.equalsIgnoreCase("end")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getEndingTime();
					allTasksList.get(i).setEndingTime(newTerm);
					
					// if the new change term(ending time) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString())&&!Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())&&!Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setDate(tempChangedTerm);
						allTasksList.get(i).recalculateComparisonValue();
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");

						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						return;
					}
					
					if((!allTasksList.get(i).getStartingTime().equalsIgnoreCase("undefined"))&&(!allTasksList.get(i).getEndingTime().equalsIgnoreCase("undefined"))){
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
							return;
						}
					}
					
					atLeastOneTaskChanged = true;
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString())||Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())||Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString()));
					
					if(Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("floating");
					}
					else if(Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("deadline");
					}
					else if(((allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))||allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))&&Checker.checkTask(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("default");
					}
					
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
							
				return;	
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
			
			return;
		}
		else if(changeVariableType.equalsIgnoreCase("title")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getTaskTitle();
					allTasksList.get(i).setTaskTitle(newTerm);
					
					// if the new change term(task title) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString())&&!Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())&&!Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setDate(tempChangedTerm);
						allTasksList.get(i).recalculateComparisonValue();
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");

						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						return;
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString())||Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())||Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString()));
					
					atLeastOneTaskChanged = true;

					if(Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("floating");
					}
					else if(Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("deadline");
					}
					else if(((allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))||allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))&&Checker.checkTask(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("default");
					}
					
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
				
				return;	
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
			
			return;
		}
		else if(changeVariableType.equalsIgnoreCase("description")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getTaskDescription();
					allTasksList.get(i).setTaskDescription(newTerm);
					
					// if the new change term(task description) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString())&&!Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())&&!Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setDate(tempChangedTerm);
						allTasksList.get(i).recalculateComparisonValue();
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");

						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						
						return;
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString())||Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())||Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString()));
					
					atLeastOneTaskChanged = true;

					if(Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("floating");
					}
					else if(Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("deadline");
					}
					else if(((allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))||allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))&&Checker.checkTask(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("default");
					}

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
				return;	
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
			
			return;
		}
		else if(changeVariableType.equalsIgnoreCase("priority")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getPriorityLevel().toString();
					allTasksList.get(i).setPriorityLevel(newTerm);
					
					// if the new change term(priority level) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setPriorityLevel(tempChangedTerm);
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");
						
						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						
						return;
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString())||Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())||Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString()));
					
					atLeastOneTaskChanged = true;
					
					if(Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("floating");
					}
					else if(Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("deadline");
					}
					else if(((allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))||allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))&&Checker.checkTask(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("default");
					}

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
				
				return;	
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
			
			return;
		}
		else if(changeVariableType.equalsIgnoreCase("category")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equalsIgnoreCase(currentDate))&&(allTasksList.get(i).getTaskTitle().equalsIgnoreCase(currentTaskTitle))){
					tempChangedTerm = allTasksList.get(i).getCategory();
					allTasksList.get(i).setCategory(newTerm);
					
					// if the new change term(category) is invalid, reverse the change, and stop going through the 
					// rest of the changeTaskVariable() method
					if(!Checker.checkTask(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory(tempChangedTerm);
						
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");
						
						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();
						
						return;
					}
					
					assert(Checker.checkTask(allTasksList.get(i).getPrintTaskString())||Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())||Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString()));
					
					atLeastOneTaskChanged = true;
					
					if(Checker.checkFloatingTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("floating");
					}
					else if(Checker.checkDeadlineTaskOutput(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("deadline");
					}
					else if(((allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))||allTasksList.get(i).getCategory().equalsIgnoreCase("floating"))&&Checker.checkTask(allTasksList.get(i).getPrintTaskString())){
						allTasksList.get(i).setCategory("default");
					}

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
				
				return;	
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
			
			return;
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
			
			return;	
		}
	}

	// adds a task
	static void addTask(String filename, String remainingCommandString, LastAction lastAction, FlexWindow flexWindow) throws IOException {
		String remainingCommandString1 = remainingCommandString.trim();
		
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
		boolean isAddedTaskValid = (Checker.checkFloatingTaskInput(remainingCommandString1)||Checker.checkFloatingTaskOutput(remainingCommandString1)||Checker.checkDeadlineTaskOutput(remainingCommandString1)||Checker.checkDeadlineTaskInput(remainingCommandString1)||Checker.checkTask(remainingCommandString1));
		
		// if the task is not valid, do not continue the process of adding a task
		if(!isAddedTaskValid){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");	

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			return;
		}
		
		// Only a usual (normal) task can be checked for blocking (as it has both starting times and ending times)
		if(Checker.checkTask(remainingCommandString1)){
		
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
				
					return;												
				}
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
		return;
				
	}	
	// undo the previous VALID action, only if the previous action was adding a task,
	// deleting a task, 
	// or changing a task's variable
	// This is because there is no need to undo a search task
	static void undo(String filename, LastAction lastAction, FlexWindow flexWindow) throws IOException, NullPointerException {
		
		if(lastAction.getPreviousAction()==null || lastAction.getPreviousTask() ==null){
			flexWindow.getTextArea().append(NOTHING_TO_UNDO_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");	

			logger.finest(NOTHING_TO_UNDO_MESSAGE);
			System.out.println(NOTHING_TO_UNDO_MESSAGE);
			System.out.println();
			
			return;
		}
		
		int whitespaceIndex1 = lastAction.getPreviousAction().trim().indexOf(" ");
		

		
		if(whitespaceIndex1 < 0 ){
		
			if(lastAction.getPreviousAction().equalsIgnoreCase("add")){

				logger.finest(ADD_UNDONE_MESSAGE);
				System.out.println(ADD_UNDONE_MESSAGE);
				System.out.println();
				
				deleteTask(filename, lastAction.getPreviousTask().getDate(), lastAction.getPreviousTask().getTaskTitle(), lastAction, flexWindow);
				
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
		



	

}
