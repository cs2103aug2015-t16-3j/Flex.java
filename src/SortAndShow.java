import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.*;

public class SortAndShow {

	private static final Logger logger = Logger.getLogger(Flex.class.getName());

	private static final String DISPLAY_SORTED_BY_STARTING_TIMES_MESSAGE = "The tasks, sorted by starting time, are displayed.";
	private static final String DISPLAY_SORTED_BY_ENDING_TIMES_MESSAGE = "The tasks, sorted by ending time, are displayed.";
	private static final String DISPLAY_SORTED_BY_TITLES_MESSAGE = "The tasks, sorted in alphabetical order by title, are displayed.";
	private static final String DISPLAY_SORTED_BY_DESCRIPTIONS_MESSAGE = "The tasks, sorted in alphabetical order by task description, are displayed.";
	private static final String DISPLAY_SORTED_BY_PRIORITY_LEVELS_MESSAGE = "The tasks, sorted in alphabetical order by priority level, is displayed.";
	private static final String DISPLAY_SORTED_BY_CATEGORIES_MESSAGE = "The tasks, sorted in alphabetical order by category, are displayed.";

	private static final String TASKS_NOT_DONE_DISPLAYED_MESSAGE = "The tasks which have not been marked as done are displayed.";
	private static final String ALL_TASKS_DISPLAYED_MESSAGE = "All the tasks in the schedule are displayed.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	// that is, it is valid only if its starting time, or ending time, are NOT between the starting
	// and ending times of existing tasks which are NOT DONE YET
	private static final int HOUR_MINUTES = 60;

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
