import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Flex{

	private static Scanner sc;
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	
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
			if(firstWord.equals("exit")){												
			   	return;
			}	
			// Case 2: undo the last action
			else if(firstWord.equals("undo")){
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
			else if(firstWord.equals("add")){
				
				String remainingCommandString = command.substring(whitespaceIndex+1);
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
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex1 + 2);
				
				int commaWhitespaceIndex2 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex2 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex2 + 2);
				
				int commaWhitespaceIndex3 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex3 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex3 + 2);
				
				int commaWhitespaceIndex4 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex4 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex4 + 2);
				
				int commaWhitespaceIndex5 = remainingCommandStringCheck.indexOf(", ");			
				if(commaWhitespaceIndex5 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex5 + 2);	
				
				int commaWhitespaceIndex6 = remainingCommandStringCheck.indexOf(", ");
				if(commaWhitespaceIndex6 < 0){				
					System.out.println(INVALID_INPUT_MESSAGE);					
					readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);		
				}
				remainingCommandStringCheck = remainingCommandStringCheck.substring(commaWhitespaceIndex6 + 2);
				
				// only if input is valid
				// Note: This method will call readAndExecuteCommand again
				addTask(filename, remainingCommandString, previousChangeTerm, previousAction, previousTask);											
								
			}
			// Case 6: Deleting a task
			else if(firstWord.equals("delete")){
						
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
				date = remainingCommandString.substring(0, whitespaceIndex1);
				String taskTitle = new String("");
				taskTitle = remainingCommandString.substring(whitespaceIndex1+1);
				
				// only if input is valid
				deleteTask(filename, date, taskTitle, previousChangeTerm, previousAction, previousTask);							
													
			}
			// Case 7: changing a task's variable
			else if(firstWord.equals("change")){
				String remainingString = command.substring(whitespaceIndex+1);		
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
			else if(firstWord.equals("search")){
				String remainingString = command.substring(whitespaceIndex+1);		
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
	private static void undo(String filename, String previousAction, String previousChangeTerm, Task previousTask) throws IOException {
		if(previousAction.equals("add")){
			deleteTask(filename, previousTask.getDate(), previousTask.getTaskTitle(), previousChangeTerm, previousAction, previousTask);
		}
		else if(previousAction.equals("delete")){
			addTask(filename, previousTask.printTaskString(), previousChangeTerm, previousAction, previousTask);
		}
		else if(previousAction.equals("change")){
			changeTaskVariable(filename, "change " + previousTask.getDate() + " " + previousTask.getTaskTitle() + " " + previousChangeTerm, previousChangeTerm, previousAction, previousTask);
		}
		else{
			return;
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
		searchVariableType = remainingCommandString.substring(0, whitespaceIndex1);

		// for checking
		// System.out.println("searchVariableType: " + searchVariableType);
		
		String searchTerm = new String("");
		searchTerm = remainingCommandString.substring(whitespaceIndex1 + 1);
		
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
		
		if(searchVariableType.equals("date")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getDate().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equals("start")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getStartingTime().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equals("end")){		
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getEndingTime().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equals("title")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getTaskTitle().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equals("description")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getTaskDescription().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equals("priority")){
			for(int i=0; i<allTasksList.size(); i++){
				if(allTasksList.get(i).getPriorityLevel().equals(searchTerm)){
					allTasksList.get(i).printTask();
				}
			}
		}
		else if(searchVariableType.equals("category")){
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
		changeVariableType = remainingCommandString.substring(0, whitespaceIndex1);
		
		String changeRemainingString = new String("");
		changeRemainingString = remainingCommandString.substring(whitespaceIndex1 + 1);
		changeRemainingString.trim();
		
		int whitespaceIndex2 = changeRemainingString.indexOf(" ");
		if(whitespaceIndex2 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		String currentDate = new String("");
		currentDate = changeRemainingString.substring(0, whitespaceIndex2);
		
		changeRemainingString = changeRemainingString.substring(whitespaceIndex2 + 1);
		changeRemainingString.trim();
		
		int whitespaceIndex3 = changeRemainingString.indexOf(" ");
		if(whitespaceIndex3 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		String currentTaskTitle = new String("");
		currentTaskTitle = changeRemainingString.substring(0, whitespaceIndex3 + 1);
		changeRemainingString.trim();
		
		int whitespaceIndex4 = changeRemainingString.indexOf(" ");
		if(whitespaceIndex4 < 0){
			System.out.println(INVALID_INPUT_MESSAGE);
			
			readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask);	
		}
		
		// the original form of the changed variable, before it was changed
		String changeTerm = new String("");
		changeTerm = changeRemainingString.substring(0, whitespaceIndex4 + 1);	
		
		// reads in the file, line by line
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;		
		ArrayList<Task> allTasksList = new ArrayList<Task>();
						
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				// for checking
				System.out.println("task added for changeTaskVariable()");
				
				allTasksList.add(new Task(currentLine));
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		String changedTerm = new String("");
		
		Task tempTask = new Task();
		
		if(changeVariableType.equals("date")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getDate();
					allTasksList.get(i).setDate(changeTerm);
					tempTask = allTasksList.get(i);
				}
			}			
		}
		else if(changeVariableType.equals("start")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getStartingTime();
					allTasksList.get(i).setStartingTime(changeTerm);
					tempTask = allTasksList.get(i);
				}
			}		
		}
		else if(changeVariableType.equals("end")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getEndingTime();
					allTasksList.get(i).setEndingTime(changeTerm);
					tempTask = allTasksList.get(i);
				}
			}		
		}
		else if(changeVariableType.equals("title")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getTaskTitle();
					allTasksList.get(i).setTaskTitle(changeTerm);
					tempTask = allTasksList.get(i);
				}
			}		
		}
		else if(changeVariableType.equals("description")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getTaskDescription();
					allTasksList.get(i).setTaskDescription(changeTerm);
					tempTask = allTasksList.get(i);
				}
			}		
		}
		else if(changeVariableType.equals("priority")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getPriorityLevel().toString();
					allTasksList.get(i).setPriorityLevel(changeTerm);
					tempTask = allTasksList.get(i);
				}
			}		
		}
		else if(changeVariableType.equals("category")){
			for(int i=0; i<allTasksList.size(); i++){
				if((allTasksList.get(i).getDate().equals(currentDate))&&(allTasksList.get(i).getTaskTitle().equals(currentTaskTitle))){
					changedTerm = allTasksList.get(i).getCategory();
					allTasksList.get(i).setCategory(changeTerm);
					tempTask = allTasksList.get(i);
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
		readAndExecuteCommand(filename, changedTerm, "change", tempTask);	
		
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
