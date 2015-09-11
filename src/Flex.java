import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Flex{

	private static Scanner sc;
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	private static boolean isValid; // used to check if a new task to be added is valid
	// that is, it is valid only if its starting time, or ending time, are NOT between the starting
	// and ending times of existing tasks which are NOT DONE YET
	
	
	public static void main(String[]args) throws IOException{
		// whereby the user's input is
		// for example:
		// java Flex.java schedule.txt
		String filename = args[0];
		
		//for checking
		System.out.println("filename: " + filename);
		
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
			// case 9: If the user's command is invalid
			else{
				System.out.println(INVALID_INPUT_MESSAGE);
				readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);
			}
		}
	}

	// undo the previous action, only if the previous action was adding a task,
	// deleting a task, 
	// or changing a task's variable
	// This is because there is no need to undo a search task
	private static void undo(String filename, String previousChangeTerm, String previousAction, Task previousTask) throws IOException {
		int whitespaceIndex1 = previousAction.trim().indexOf(" ");
		
		if(whitespaceIndex1 < 0 ){
		
			if(previousAction.equalsIgnoreCase("add")){
				System.out.println("previousChangeTerm: " + previousChangeTerm);
				System.out.println("previousAction: "+ previousAction);
				System.out.println("previousTask: " + previousTask.printTaskString());
				deleteTask(filename, previousTask.getDate(), previousTask.getTaskTitle(), previousChangeTerm, previousAction, previousTask);
			}
			else if(previousAction.equalsIgnoreCase("delete")){
				System.out.println("previousChangeTerm: " + previousChangeTerm);
				System.out.println("previousAction: "+ previousAction);
				System.out.println("previousTask: " + previousTask.printTaskString());
				addTask(filename, previousTask.printTaskString(), previousChangeTerm, previousAction, previousTask);
			}
		}
		else{
			
			if(previousAction.substring(0, whitespaceIndex1).trim().equalsIgnoreCase("change")){
				System.out.println("previousChangeTerm: " + previousChangeTerm);
				System.out.println("previousAction: " + previousAction);
				System.out.println("previousTask: " + previousTask.printTaskString());	
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
		
		isValid = true;
		
		// for example
		// 14/9/2015, 1000, 1159, title two, description two, 1, blocked

		String remainingCommandString1StartingWithDate = new String("");
		remainingCommandString1StartingWithDate = remainingCommandString1;
		int commaWhitespaceIndex1 = remainingCommandString1StartingWithDate.indexOf(", ");
		
		// for checking
		System.out.println("remainingCommandString1StartingWithDate: "+ remainingCommandString1StartingWithDate);
		String tempDate = new String("");
		tempDate = remainingCommandString1StartingWithDate.substring(0, commaWhitespaceIndex1);
		
		String remainingCommandString1StartingWithStartingTime = new String("");
		remainingCommandString1StartingWithStartingTime = remainingCommandString1StartingWithDate.substring(commaWhitespaceIndex1 + 2).trim();
		int commaWhitespaceIndex2 = remainingCommandString1StartingWithStartingTime.indexOf(", ");	
		String tempStartingTime = new String("");
		
		//for checking
		System.out.println("remainingCommandString1StartingWithStartingTime: " + remainingCommandString1StartingWithStartingTime);
		
		tempStartingTime = remainingCommandString1StartingWithStartingTime.substring(0, commaWhitespaceIndex2);

		// for checking
		System.out.println("tempStartingTime: " + tempStartingTime);
		
		String remainingCommandString1StartingWithEndingTime = new String("");
		remainingCommandString1StartingWithEndingTime = remainingCommandString1StartingWithStartingTime.substring(commaWhitespaceIndex2 +2).trim();
		int commaWhitespaceIndex3 = remainingCommandString1StartingWithEndingTime.indexOf(", ");	
		String tempEndingTime = new String("");
		
		// for checking
		System.out.println("remainingCommandString1StartingWithEndingTime: " + remainingCommandString1StartingWithEndingTime);
		
		tempEndingTime = remainingCommandString1StartingWithEndingTime.substring(0, commaWhitespaceIndex3);
				
		// for checking
		System.out.println("tempEndingTime: " + tempEndingTime);
		
		for (int i=0; i<allTasksList.size(); i++){
			if((allTasksList.get(i).getDate().equals(tempDate))&&(!allTasksList.get(i).getCategory().equalsIgnoreCase("done"))&&(((Integer.valueOf(allTasksList.get(i).getStartingTime()) <= Integer.valueOf(tempStartingTime))&&(Integer.valueOf(allTasksList.get(i).getEndingTime()) >= Integer.valueOf(tempStartingTime)))||((Integer.valueOf(allTasksList.get(i).getStartingTime()) <= Integer.valueOf(tempEndingTime))&&(Integer.valueOf(allTasksList.get(i).getEndingTime()) >= Integer.valueOf(tempEndingTime))))){
				
				// if the new task's information is invalid
				isValid = false;
				System.out.println(INVALID_INPUT_MESSAGE);					
				readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);												
			}
		}
		
		
		Task tempTask = new Task();
		
		allTasksList.add(new Task(remainingCommandString1));	
		
		tempTask = allTasksList.get(allTasksList.size()-1);
		
		// for checking
		// System.out.println("new task successfully added");
		
		// sort all tasks by date and starting time
		sortAllTasks(allTasksList);
		
		// overwrites to the file, line by line
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		for(int i=0; i<allTasksList.size(); i++){
			writer.write(allTasksList.get(i).printTaskString());
			writer.newLine();
		}
									
		writer.close();		
		
		System.out.println("previousChangeTerm: "+ previousChangeTerm);
		System.out.println("previousAction is set to add");		
		System.out.println("previousTask(tempTask): " + tempTask.printTaskString());
		readAndExecuteCommand(filename, previousChangeTerm, "add", tempTask);	
				
	}	
	
		
	// deletes a task
	private static void deleteTask(String filename, String date, String taskTitle, String previousChangeTerm, String previousAction, Task previousTask) throws IOException {
		// reads in the file, line by line
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
				// for checking
				// System.out.println("matching task successfully deleted");
			}
		}
		
		// sort all tasks by date and starting time
		sortAllTasks(allTasksList);
		
		// overwrites to the file, line by line
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		for(int i=0; i<allTasksList.size(); i++){
			writer.write(allTasksList.get(i).printTaskString());
			writer.newLine();
		}
									
		writer.close();
		
		System.out.println("previousChangeTerm: "+ previousChangeTerm);
		System.out.println("previousAction is set to delete");		
		System.out.println("previousTask(tempTask): " + tempTask.printTaskString());
		readAndExecuteCommand(filename, previousChangeTerm, "delete", tempTask);	
	}
	
	// search for tasks
	private static void searchTask(String filename, String remainingCommandString, String previousChangeTerm, String previousAction, Task previousTask) throws IOException{
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
		
		readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	

	}
	
	// changes one of the variables in a task, EXCEPT for the comparison value
	// for sorting all tasks by date and starting time
	private static void changeTaskVariable(String filename, String remainingCommandString, String previousChangeTerm, String previousAction, Task previousTask) throws IOException {		
		// for checking
		System.out.println("remainingCommandString for changeTaskVariable(): " + remainingCommandString);
		
		int whitespaceIndex1 = remainingCommandString.indexOf(" ");
		
		if(whitespaceIndex1 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		String changeVariableType = new String("");
		changeVariableType = remainingCommandString.substring(0, whitespaceIndex1).trim();
		
		// for checking
		System.out.println("changeVariableType: " + changeVariableType);
		
		String changeRemainingString = new String("");
		changeRemainingString = remainingCommandString.substring(whitespaceIndex1 + 1).trim();
		changeRemainingString.trim();
		
		// for checking
		System.out.println("changeRemainingString: " + changeRemainingString);
		
		int commaWhitespaceIndex1 = changeRemainingString.indexOf(", ");
		if(commaWhitespaceIndex1 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		String currentDate = new String("");
		currentDate = changeRemainingString.substring(0, commaWhitespaceIndex1);
		
		// for checking
		System.out.println("currentDate: " + currentDate);
		
		changeRemainingString = changeRemainingString.substring(commaWhitespaceIndex1 + 2).trim();
		changeRemainingString.trim();
		
		// for checking
		System.out.println("changeRemainingString: " + changeRemainingString);
		
		int commaWhitespaceIndex2 = changeRemainingString.indexOf(", ");
		if(commaWhitespaceIndex2 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		String currentTaskTitle = new String("");
		currentTaskTitle = changeRemainingString.substring(0, commaWhitespaceIndex2).trim();
		changeRemainingString.trim();
		
		// for checking
		System.out.println("changeRemainingString: " + changeRemainingString);
		
		// for checking
		System.out.println("currentTaskTitle: " + currentTaskTitle);
		
		changeRemainingString = changeRemainingString.substring(commaWhitespaceIndex2 + 2).trim();
		changeRemainingString.trim();
		
		// the original form of the changed variable, before it was changed
		String changedTerm = new String("");	
		
		// the new form of the changed variable
		String newTerm = new String("");
		newTerm = changeRemainingString;
		
		// for checking
		System.out.println("newTerm: " + newTerm);
				
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
		sortAllTasks(allTasksList);
		
		// overwrites to the file, line by line
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		for(int i=0; i<allTasksList.size(); i++){
			writer.write(allTasksList.get(i).printTaskString());
			writer.newLine();
		}
									
		writer.close();
		
		
		// for valid input cases
		System.out.println("previousChangeTerm(changedTerm): " + changedTerm);
		System.out.println("previousAction is set to: " + lastAction);		
		System.out.println("previousTask(tempTask): " + tempTask.printTaskString());
		readAndExecuteCommand(filename, changedTerm, lastAction, tempTask);	
		
		
	}

	
	// used to sort tasks by starting date and starting time
	private static void sortAllTasks(ArrayList<Task> allTasksList){
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