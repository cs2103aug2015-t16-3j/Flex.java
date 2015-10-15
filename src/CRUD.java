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
	static void deleteTask(String filename, String tempTaskName, LastAction lastAction, FlexWindow flexWindow)
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

		Task tempTask = new Task();

		for (int i = 0; i < allTasksList.size(); i++) {
			if (allTasksList.get(i).getTaskName().equalsIgnoreCase(tempTaskName)) {
				tempTask = allTasksList.get(i);
				allTasksList.remove(i);
				taskExists = true;
			}
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

		lastAction.setPreviousAction("delete");
		lastAction.setPreviousTask(tempTask);

		flexWindow.getTextArea().append("\n");

		BufferedReader reader1 = null;

		reader1 = new BufferedReader(new FileReader(filename));
		String currentLine1 = null;

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine1 = reader1.readLine();
			if (currentLine1 != null) {
				if (Checker.isFloatingTask(currentLine1)) {
					floatingTasksList.add(new Task(currentLine1));
				} else if (Checker.isDeadlineTask(currentLine1) || Checker.isEventTask(currentLine1)) {
					deadlineOrEventTasksList.add(new Task(currentLine1));
				}
			}
		} while (currentLine1 != null);

		if (reader1 != null) {
			reader1.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(deadlineOrEventTasksList);

		String tempDate = new String("");

		if (!deadlineOrEventTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");
			tempDate = deadlineOrEventTasksList.get(0).getDate();
			flexWindow.getTextArea().append("Date: " + tempDate + "\n");
			flexWindow.getTextArea().append("\n");
		}

		for (int j = 0; j < deadlineOrEventTasksList.size(); j++) {
			if (deadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				flexWindow.getTextArea().append(deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			} else {
				flexWindow.getTextArea()
						.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				flexWindow.getTextArea().append("\n");

				tempDate = deadlineOrEventTasksList.get(j).getDate();

				flexWindow.getTextArea().append("Date: " + tempDate + "\n");
				flexWindow.getTextArea().append("\n");

				flexWindow.getTextArea().append(deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

		flexWindow.getTextArea()
				.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
						+ "\n");
		flexWindow.getTextArea().append("\n");

		for (int k = 0; k < floatingTasksList.size(); k++) {
			flexWindow.getTextArea().append(floatingTasksList.get(k).getDisplayString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		System.out.println();

		flexWindow.getTextArea().append("\n");

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

		for (int i = 0; i < allTasksList.size(); i++) {

			// case of "change task name, exacttaskname, newtaskname"
			if (tempString.toLowerCase().indexOf("task name") == 0) {
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

				System.out.println("allTasksList.get(i).getTaskName() : " + allTasksList.get(i).getTaskName());

				if (allTasksList.get(i).getTaskName().equalsIgnoreCase(taskNameMatchString)) {
					// FOR CHECKING
					System.out.println("taskNameMatchString: " + taskNameMatchString);

					// CHECKING
					System.out.println("BEFORE: " + allTasksList.get(i).getScheduleString());

					tempChangedTerm = allTasksList.get(i).getTaskName();
					allTasksList.get(i).setTaskName(newTaskNameString);

					// CHECKING
					System.out.println("AFTER: " + allTasksList.get(i).getScheduleString());

					tempTask = allTasksList.get(i);
					previousAction = "change task name";

					lastAction.setPreviousChangedTerm(tempChangedTerm);
					lastAction.setPreviousAction(previousAction);
					lastAction.setPreviousTask(tempTask);

					atLeastOneTaskChanged = true;
				}
			} else if (tempString.toLowerCase().indexOf("change date") == 0) {

			} else if (tempString.toLowerCase().indexOf("change start") == 0) {

			} else if (tempString.toLowerCase().indexOf("change end") == 0) {

			} else if (tempString.toLowerCase().indexOf("change priority") == 0) {

			} else if (tempString.toLowerCase().indexOf("change event to floating") == 0) {

			} else if (tempString.toLowerCase().indexOf("change deadline to floating") == 0) {

			} else if (tempString.toLowerCase().indexOf("change deadline to event") == 0) {

			} else if (tempString.toLowerCase().indexOf("change floating to deadline") == 0) {

			} else if (tempString.toLowerCase().indexOf("change floating to event") == 0) {

			}
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
			flexWindow.getTextArea().append(allTasksList.get(i).getDisplayString() + "\n");
			flexWindow.getTextArea().append("\n");
			writer.newLine();
		}

		writer.close();

		logger.finest(CHANGED_MESSAGE);
		System.out.println(CHANGED_MESSAGE);
		System.out.println();

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
		boolean isAddedTaskValid = (Checker.isFloatingTask(remainingCommandString1)
				|| Checker.isDeadlineTask(remainingCommandString1) || Checker.isEventTask(remainingCommandString1));

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

		// Only a usual (normal) task can be checked for blocking (as it has
		// both starting times and ending times)
		if (Checker.isEventTask(remainingCommandString1)) {
			String tempString = new String("");

			tempString = remainingCommandString.trim();

			int commaWhitespaceIndex1 = tempString.indexOf(", ");

			// the task of the normal task
			String tempTaskName = new String("");
			tempTaskName = tempString.substring(0, commaWhitespaceIndex1).trim();

			tempString = tempString.substring(commaWhitespaceIndex1 + 2).trim();

			int commaWhitespaceIndex2 = tempString.indexOf(", ");

			// the date of the normal task
			String tempDate = new String("");
			tempDate = tempString.substring(0, commaWhitespaceIndex2).trim();

			tempString = tempString.substring(commaWhitespaceIndex2 + 2).trim();

			int commaWhitespaceIndex3 = tempString.indexOf(", ");

			// the start of the normal task
			String tempStart = new String("");
			tempStart = tempString.substring(0, commaWhitespaceIndex3).trim();

			tempString = tempString.substring(commaWhitespaceIndex3 + 2).trim();

			int commaWhitespaceIndex4 = tempString.indexOf(", ");

			// the end of the normal task
			String tempEnd = new String("");
			tempEnd = tempString.substring(0, commaWhitespaceIndex4).trim();

			// check for clashes in existing event tasks
			for (int i = 0; i < allTasksList.size(); i++) {
				if ((Checker.isEventTask(allTasksList.get(i).getScheduleString()))) {
					if ((allTasksList.get(i).getDate().equalsIgnoreCase(tempDate))) {
						if (((Integer.valueOf(allTasksList.get(i).getStart()) <= Integer.valueOf(tempStart))
								&& (Integer.valueOf(allTasksList.get(i).getEnd()) >= Integer.valueOf(tempStart)))
								|| ((Integer.valueOf(allTasksList.get(i).getStart()) <= Integer.valueOf(tempEnd))
										&& (Integer.valueOf(allTasksList.get(i).getEnd()) >= Integer
												.valueOf(tempEnd)))) {

							flexWindow.getTextArea().append(BLOCKED_MESSAGE + "\n");
							flexWindow.getTextArea().append("\n");

							logger.finest(BLOCKED_MESSAGE);
							System.out.println(BLOCKED_MESSAGE);
							System.out.println();

							return;
						}
					}
				}
			}
		}

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

		flexWindow.getTextArea().append("\n");

		BufferedReader reader1 = null;

		reader1 = new BufferedReader(new FileReader(filename));
		String currentLine1 = null;

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine1 = reader1.readLine();
			if (currentLine1 != null) {
				if (Checker.isFloatingTask(currentLine1)) {
					floatingTasksList.add(new Task(currentLine1));
				} else if (Checker.isDeadlineTask(currentLine1) || Checker.isEventTask(currentLine1)) {
					deadlineOrEventTasksList.add(new Task(currentLine1));
				}
			}
		} while (currentLine1 != null);

		if (reader1 != null) {
			reader1.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(deadlineOrEventTasksList);

		String tempDate = new String("");

		if (!deadlineOrEventTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");
			tempDate = deadlineOrEventTasksList.get(0).getDate();
			flexWindow.getTextArea().append("Date: " + tempDate + "\n");
			flexWindow.getTextArea().append("\n");
		}

		for (int j = 0; j < deadlineOrEventTasksList.size(); j++) {
			if (deadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				flexWindow.getTextArea().append(deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			} else {
				flexWindow.getTextArea()
						.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				flexWindow.getTextArea().append("\n");

				tempDate = deadlineOrEventTasksList.get(j).getDate();

				flexWindow.getTextArea().append("Date: " + tempDate + "\n");
				flexWindow.getTextArea().append("\n");

				flexWindow.getTextArea().append(deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

		flexWindow.getTextArea()
				.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
						+ "\n");
		flexWindow.getTextArea().append("\n");

		for (int k = 0; k < floatingTasksList.size(); k++) {
			flexWindow.getTextArea().append(floatingTasksList.get(k).getDisplayString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		System.out.println();

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

				deleteTask(filename, lastAction.getPreviousTask().getTaskName(), lastAction, flexWindow);

			} else if (lastAction.getPreviousAction().equalsIgnoreCase("delete")) {

				logger.finest(DELETE_UNDONE_MESSAGE);
				System.out.println(DELETE_UNDONE_MESSAGE);
				System.out.println();

				addTask(filename, lastAction.getPreviousTask().getScheduleString(), lastAction, flexWindow);
			}
		}
		// }
		// }
		// else{

		// if(lastAction.getPreviousAction().substring(0,
		// whitespaceIndex1).trim().equalsIgnoreCase("change")){

		// logger.finest(CHANGE_UNDONE_MESSAGE);
		// System.out.println(CHANGE_UNDONE_MESSAGE);
		// System.out.println();

		// int whitespaceIndex2 = lastAction.getPreviousAction().indexOf(" ");

		// changeTaskVariable(filename,
		// lastAction.getPreviousAction().substring(whitespaceIndex2 + 1).trim()
		// + " " + lastAction.getPreviousTask().getDate() + ", " +
		// lastAction.getPreviousTask().getTaskTitle() + ", " +
		// lastAction.getPreviousChangedTerm(), lastAction, flexWindow);

		// }
		// }
		// }
	}
}
