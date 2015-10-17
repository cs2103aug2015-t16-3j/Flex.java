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
	private static final String CHANGE_UNDONE_MESSAGE = "The last valid change action has been undone.";
	private static final String DELETE_UNDONE_MESSAGE = "The last valid delete action has been undone.";
	private static final String ADD_UNDONE_MESSAGE = "The last valid add action has been undone.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";

	private static final String TASK_DOES_NOT_EXIST_MESSAGE = "Task does not exist, so no such task can be deleted.";
	private static final String BLOCKED_MESSAGE = "Unable to add the new task, because the new task clashes with existing tasks (on the same date) which have not been marked as tasks which have been done.";
	private static final int HOUR_MINUTES = 60;

	// deletes a task
	static void deleteTask(String filename, String remainingString, LastAction lastAction, FlexWindow flexWindow)
			throws IOException {
		// reads in the file, line by line
		boolean taskExists = false;

		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
		ArrayList<Task> allTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {

				allTasksList.add(new Task(currentLine));
			}

		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();
		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> recurringTasksList = new ArrayList<Task>();

		for (int j = 0; j < allTasksList.size(); j++) {
			if (Checker.isDeadlineTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneDeadlineTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isEventTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneEventTaskInput(allTasksList.get(j).getScheduleString())) {
				deadlineOrEventTasksList.add(allTasksList.get(j));
			} else if (Checker.isFloatingTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneFloatingTaskInput(allTasksList.get(j).getScheduleString())) {
				floatingTasksList.add(allTasksList.get(j));
			} else if (Checker.isRecurringTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneRecurringTaskInput(allTasksList.get(j).getScheduleString())) {
				recurringTasksList.add(allTasksList.get(j));
			}
		}

		Task tempTask = new Task();

		String tempString = new String("");

		tempString = remainingString.trim();

		if (tempString.length() <= 0) {
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
		}

		int whitespaceIndex1 = tempString.indexOf(" ");
		if (whitespaceIndex1 < 0) {
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return;
		}

		String deleteVariableType = new String("");
		deleteVariableType = tempString.substring(0, whitespaceIndex1).trim();

		tempString = tempString.substring(whitespaceIndex1 + 1).trim();

		if (tempString.length() <= 0) {
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return;
		}

		if (deleteVariableType.equalsIgnoreCase("floating")) {
			String tempNumber = tempString.trim();

			char[] charArray = new char[tempNumber.length()];
			tempNumber.getChars(0, tempNumber.length(), charArray, 0);

			boolean isNumberValid = true;

			for (int b = 0; b < tempNumber.length(); b++) {
				if (!Character.isDigit(charArray[b])) {
					isNumberValid = false;
				}
			}

			if (!isNumberValid) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			if (Integer.valueOf(tempNumber) <= 0) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			if (Integer.valueOf(tempNumber) > floatingTasksList.size()) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			tempTask = allTasksList.get(deadlineOrEventTasksList.size() + Integer.valueOf(tempNumber) - 1);

			lastAction.setPreviousTask(tempTask);

			lastAction.setPreviousAction("delete");

			allTasksList.remove(deadlineOrEventTasksList.size() + Integer.valueOf(tempNumber) - 1);

			taskExists = true;

		} else if (deleteVariableType.equalsIgnoreCase("rec")) {
			// delete rec <number>

			String tempNumber = tempString.trim();

			char[] charArray1 = new char[tempNumber.length()];
			tempNumber.getChars(0, tempNumber.length(), charArray1, 0);

			boolean isNumberValid = true;

			for (int c = 0; c < tempNumber.length(); c++) {
				if (!Character.isDigit(charArray1[c])) {
					isNumberValid = false;
				}
			}

			if (!isNumberValid) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			if (Integer.valueOf(tempNumber) <= 0) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			if (Integer.valueOf(tempNumber) > recurringTasksList.size()) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			tempTask = allTasksList.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(tempNumber) - 1);

			lastAction.setPreviousTask(tempTask);

			lastAction.setPreviousAction("delete");

			allTasksList.remove(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(tempNumber) - 1);

			taskExists = true;

		} else if (Checker.isValidDate(deleteVariableType)) {
			// delete <date> <number>
			String tempNumber = tempString.trim();

			char[] charArray2 = new char[tempNumber.length()];
			tempNumber.getChars(0, tempNumber.length(), charArray2, 0);

			boolean isNumberValid = true;

			for (int d = 0; d < tempNumber.length(); d++) {
				if (!Character.isDigit(charArray2[d])) {
					isNumberValid = false;
				}
			}

			if (!isNumberValid) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			if (Integer.valueOf(tempNumber) <= 0) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			if (Integer.valueOf(tempNumber) > deadlineOrEventTasksList.size()) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			tempTask = deadlineOrEventTasksList.get(Integer.valueOf(tempNumber) - 1);

			lastAction.setPreviousTask(tempTask);

			lastAction.setPreviousAction("delete");

			allTasksList.remove(Integer.valueOf(tempNumber) - 1);

			taskExists = true;

		} else {
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return;
		}

		if (taskExists == false) {
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

		for (int i = 0; i < allTasksList.size(); i++) {
			writer.write(allTasksList.get(i).getScheduleString());
			writer.newLine();
		}

		writer.close();

		logger.finest(DELETED_MESSAGE);
		System.out.println(DELETED_MESSAGE);
		System.out.println();

		return;
	}

	// changes one of the variables in a task, EXCEPT for the comparison value
	// for sorting all tasks by date and starting time
	static void changeTaskVariable(String filename, String remainingCommandString, LastAction lastAction,
			FlexWindow flexWindow) throws IOException {
		boolean atLeastOneTaskChanged = false;

		String tempString = new String("");
		tempString = remainingCommandString.trim();

		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
		ArrayList<Task> allTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {

				allTasksList.add(new Task(currentLine));
			}

		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		Task tempTask = new Task();
		String tempChangedTerm = new String("");
		String previousAction = new String("");

		if (tempString.toLowerCase().indexOf("task name") == 0) {
			// case of "change task name, exacttaskname, newtaskname"

			int commaWhitespaceIndex1 = tempString.indexOf(", ");

			if (commaWhitespaceIndex1 < 0) {
				// INVALID
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

			tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();

			int commaWhitespaceIndex2 = tempString.indexOf(", ");

			if (commaWhitespaceIndex2 < 0) {
				// INVALID
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

			String taskNameMatchString = new String("");
			taskNameMatchString = tempString.substring(0, commaWhitespaceIndex2);

			if (taskNameMatchString.length() == 0) {
				// INVALID
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

			tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();

			if (tempString.length() == 0) {
				// INVALID
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

			String newTaskNameString = new String("");
			newTaskNameString = tempString;

			int commaWhitespaceIndex3 = newTaskNameString.indexOf(", ");

			if (commaWhitespaceIndex3 > 0) {
				// INVALID
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

			for (int i = 0; i < allTasksList.size(); i++) {
				if (allTasksList.get(i).getTaskName().equalsIgnoreCase(taskNameMatchString)) {

					tempChangedTerm = allTasksList.get(i).getTaskName();
					allTasksList.get(i).setTaskName(newTaskNameString);
					allTasksList.get(i).calculateAndSetComparisonValue(allTasksList.get(i).getScheduleString());

					tempTask = allTasksList.get(i);
					previousAction = "change task name";

					lastAction.setPreviousChangedTerm(tempChangedTerm);
					lastAction.setPreviousAction(previousAction);
					lastAction.setPreviousTask(tempTask);

					atLeastOneTaskChanged = true;
				}
			}
		} else if (tempString.toLowerCase().indexOf("change date") == 0) {
			// case of "change date, exacttaskname, newdate"

		} else if (tempString.toLowerCase().indexOf("change start") == 0) {
			// case of "change start, exacttaskname, newstart"

		} else if (tempString.toLowerCase().indexOf("change end") == 0) {
			// case of "change end, exacttaskname, newend"

		} else if (tempString.toLowerCase().indexOf("change priority") == 0) {
			// case of "change priority, exacttaskname, priority"

		} else if (tempString.toLowerCase().indexOf("change event to floating") == 0) {
			// case of "change event to floating, exacttaskname"

		} else if (tempString.toLowerCase().indexOf("change deadline to floating") == 0) {
			// case of "change deadline to floating, exacttaskname"

		} else if (tempString.toLowerCase().indexOf("change deadline to event") == 0) {
			// case of "change deadline to event, exacttaskname, newstart,
			// newpriority)

		} else if (tempString.toLowerCase().indexOf("change floating to deadline") == 0) {
			// case of "change floating to deadline, exacttaskname, date,
			// newend"

		} else if (tempString.toLowerCase().indexOf("change floating to event") == 0) {
			// case of "change floating to event, exacttaskname, date, start,
			// end, priority"

		} else if (tempString.toLowerCase().indexOf("change status") == 0) {
			// this is for marking tasks as done, or not done
			// done tasks will have " [done]", without the quotation marks
			// at the back of a Task's String form in the file
			// e.g.
			// 1) "change status, exacttaskname, done"
			// OR
			// 2) "change status, exacttaskname, not done"

		}

		// if no changes have been made
		if (!atLeastOneTaskChanged) {
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			flexWindow.getTextArea()
					.append(VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE + "\n");
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

		for (int i = 0; i < allTasksList.size(); i++) {
			writer.write(allTasksList.get(i).getScheduleString());
			writer.newLine();
		}

		writer.close();

		logger.finest(CHANGED_MESSAGE);
		System.out.println(CHANGED_MESSAGE);
		System.out.println();

		return;

	}

	// adds a task
	static void addTask(String filename, String remainingCommandString, LastAction lastAction, FlexWindow flexWindow)
			throws IOException {
		String remainingCommandString1 = remainingCommandString.trim();

		// reads in the file, line by line
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> allTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {

				allTasksList.add(new Task(currentLine));
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		// check for the validity of the potential Task's variables,
		// and print out error messages for only the first mistake made by the
		// user,
		// for the Task String
		boolean isAddedTaskValid = (Checker.isFloatingTaskInput(remainingCommandString1)
				|| Checker.isDoneFloatingTaskInput(remainingCommandString1)
				|| Checker.isDeadlineTaskInput(remainingCommandString1)
				|| Checker.isDoneDeadlineTaskInput(remainingCommandString1)
				|| Checker.isEventTaskInput(remainingCommandString1)
				|| Checker.isDoneEventTaskInput(remainingCommandString1)
				|| Checker.isRecurringTaskInput(remainingCommandString1))
				|| Checker.isDoneRecurringTaskInput(remainingCommandString1);

		// if the task is not valid, do not continue the process of adding a
		// task
		if (!isAddedTaskValid) {
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return;
		}

		// ***TO CHECK FOR CLASH IN START AND END FOR EVENT TASK INPUT LATER***

		allTasksList.add(new Task(remainingCommandString1));

		Task tempTask = allTasksList.get(allTasksList.size() - 1);

		// sort all tasks by date and starting time
		SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);

		// overwrites to the file, line by line
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

		for (int i = 0; i < allTasksList.size(); i++) {
			writer.write(allTasksList.get(i).getScheduleString());
			writer.newLine();
		}

		writer.close();

		logger.finest(ADDED_MESSAGE);
		System.out.println(ADDED_MESSAGE);
		System.out.println();

		lastAction.setPreviousAction("add");
		lastAction.setPreviousTask(tempTask);

		flexWindow.getTextArea()
				.append("The task " + "\"" + tempTask.getDisplayString() + "\"" + " has been added." + "\n");
		flexWindow.getTextArea().append("\n");

		return;
	}

	// undo the previous VALID action, only if the previous action was adding a
	// task,
	// deleting a task,
	// or changing a task's variable
	// This is because there is no need to undo a search task
	static void undo(String filename, LastAction lastAction, FlexWindow flexWindow)
			throws IOException, NullPointerException {

		if (lastAction.getPreviousAction() == null || lastAction.getPreviousTask() == null) {
			flexWindow.getTextArea().append(NOTHING_TO_UNDO_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(NOTHING_TO_UNDO_MESSAGE);
			System.out.println(NOTHING_TO_UNDO_MESSAGE);
			System.out.println();

			return;
		}

		int whitespaceIndex1 = lastAction.getPreviousAction().trim().indexOf(" ");

		if (whitespaceIndex1 < 0) {

			if (lastAction.getPreviousAction().equalsIgnoreCase("add")) {

				logger.finest(ADD_UNDONE_MESSAGE);
				System.out.println(ADD_UNDONE_MESSAGE);
				System.out.println();

				// reads in the file, line by line
				BufferedReader reader = null;

				reader = new BufferedReader(new FileReader(filename));
				String currentLine = null;

				ArrayList<Task> allTasksList = new ArrayList<Task>();

				do {
					currentLine = reader.readLine();
					if (currentLine != null) {

						allTasksList.add(new Task(currentLine));
					}
				} while (currentLine != null);

				if (reader != null) {
					reader.close();
				}
				
				Task tempTask = new Task();
				
				// for exactness, equalsIgnoreCase is not used
				for(int i=0; i<allTasksList.size(); i++){
					if(lastAction.getPreviousTask().getScheduleString().equals(allTasksList.get(i).getScheduleString())){
						tempTask = allTasksList.get(i);
						allTasksList.remove(i);
						lastAction.setPreviousAction("delete");
						lastAction.setPreviousTask(tempTask);
						
						BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

						for (int j = 0; j < allTasksList.size(); j++) {
							writer.write(allTasksList.get(j).getScheduleString());
							writer.newLine();
						}

						writer.close();
						
					}
				}
				

			} else if (lastAction.getPreviousAction().equalsIgnoreCase("delete")) {

				logger.finest(DELETE_UNDONE_MESSAGE);
				System.out.println(DELETE_UNDONE_MESSAGE);
				System.out.println();
	
				CRUD.addTask(filename, lastAction.getPreviousTask().getScheduleString(), lastAction, flexWindow);
			}
		} else {

			if (lastAction.getPreviousAction().trim().equalsIgnoreCase("change task name")) {

				logger.finest(CHANGE_UNDONE_MESSAGE);
				System.out.println(CHANGE_UNDONE_MESSAGE);
				System.out.println();

				changeTaskVariable(filename, "task name" + ", " + lastAction.getPreviousTask().getTaskName() + ", "
						+ lastAction.getPreviousChangedTerm(), lastAction, flexWindow);
			}

		}

		SortAndShow.readAndDisplayAll(filename, flexWindow);

	}
}
