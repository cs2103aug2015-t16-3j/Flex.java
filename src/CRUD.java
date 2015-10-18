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
	private static final String BLOCKED_MESSAGE = "Unable to add the new event task (which is not done), because the new task (which is not done) clashes with existing event tasks  (which are not done) (on the same date).";
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
			lastAction.setPreviousChangedScheduleString(null);

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

			tempTask = allTasksList
					.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(tempNumber) - 1);

			lastAction.setPreviousTask(tempTask);

			lastAction.setPreviousAction("delete");

			allTasksList.remove(
					deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(tempNumber) - 1);

			taskExists = true;

		} else if (Checker.isValidDate(deleteVariableType)) {
			// delete <date> <number>

			String date = deleteVariableType.trim();

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

			int counterIndex = 0;

			for (int r = 0; r < allTasksList.size(); r++) {
				if (Checker.isDeadlineTaskInput(allTasksList.get(r).getScheduleString())
						|| Checker.isDoneDeadlineTaskInput(allTasksList.get(r).getScheduleString())
						|| Checker.isEventTaskInput(allTasksList.get(r).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(r).getScheduleString())) {
					if (allTasksList.get(r).getDate().equalsIgnoreCase(date)) {
						counterIndex += 1;

						if (counterIndex == Integer.valueOf(tempNumber)) {
							tempTask = allTasksList.get(r);
							lastAction.setPreviousTask(tempTask);
							lastAction.setPreviousAction("delete");

							allTasksList.remove(r);
							taskExists = true;
						}
					}
				}
			}

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
	// Case 7: changing a task's variable
	// each change/edit command starts with the hyphen on the far left

	// For Editing An Event Task
	// change <date> <number> to <newdate>
	// change <date> <number> priority to <newpriority>
	// change <date> <number> time to <newstart>-<newend>

	// For Editing A Deadline Task
	// change <date> <number> to <newdate>
	// change <date> <number> end by <new end>
	// change <date> <number> by <newend> on <newdate>

	// For Editing A Recurring Task
	// change rec <number> to every <newday>
	// change rec <number> time to <newstart>-<newend>

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
		String tempChangedTerm = new String("");
		String previousAction = new String("");

		int whitespaceIndex1 = tempString.indexOf(" ");
		if (whitespaceIndex1 < 0) {
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			return;
		}

		String firstTerm = tempString.substring(0, whitespaceIndex1).trim();

		tempString = tempString.substring(whitespaceIndex1 + 1).trim();

		if (tempString.length() == 0) {
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			return;
		}

		// For Editing A Recurring Task
		// change rec <number> to every <newday>
		// change rec <number> time to <newstart>-<newend>
		if (firstTerm.equalsIgnoreCase("rec")) {
			int whitespaceIndex2 = tempString.indexOf(" ");
			if (whitespaceIndex2 < 0) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

			String number1 = tempString.substring(0, whitespaceIndex2).trim();
			char[] charArray1 = new char[number1.length()];
			number1.getChars(0, number1.length(), charArray1, 0);

			boolean isNumber1 = true;

			for (int c = 0; c < number1.length(); c++) {
				if (!Character.isDigit(charArray1[c])) {
					isNumber1 = false;
				}
			}

			if (!isNumber1) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

			if ((Integer.valueOf(number1) <= 0) || (Integer.valueOf(number1) > recurringTasksList.size())) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

			tempString = tempString.substring(whitespaceIndex2 + 1).trim();

			if (tempString.length() == 0) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

			int toWhitespaceEveryWhitespaceIndex1 = tempString.indexOf("to every ");
			int timeWhitespaceToWhitespaceIndex1 = tempString.indexOf("time to ");
			int notWhitespaceDoneIndex = tempString.indexOf("not done");
			int doneIndex = tempString.indexOf("done");

			if (notWhitespaceDoneIndex == 0) {
				tempString = tempString.substring(notWhitespaceDoneIndex + 8).trim();

				if (tempString.length() != 0) {
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				if (allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getDone() == null) {
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}
				
				String taskBeforeChange = allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString();

				allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.setNotDone();

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString()));

			} else if (doneIndex == 0) {
				tempString = tempString.substring(doneIndex + 4).trim();

				if (tempString.length() != 0) {
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}
				
				if (allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getDone() != null) {
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}		

				String taskBeforeChange = allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString();

				allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.setDone();

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString()));

			} else if (toWhitespaceEveryWhitespaceIndex1 == 0) {
				tempString = tempString.substring(toWhitespaceEveryWhitespaceIndex1 + 9).trim();
				if (tempString.length() == 0) {
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				if (!Checker.isValidDay(tempString)) {
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				String day = tempString.trim();

				String taskBeforeChange = allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString();

				allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.setDay(day.toLowerCase());

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString()));

			} else if (timeWhitespaceToWhitespaceIndex1 == 0) {

				tempString = tempString.substring(timeWhitespaceToWhitespaceIndex1 + 8).trim();

				if (tempString.length() == 0) {
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				int hyphenIndex1 = tempString.indexOf("-");

				if (hyphenIndex1 < 0) {
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				String startTime = tempString.substring(0, hyphenIndex1).trim();
				if (startTime.length() != 4) {
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				if (!Checker.isValidTime(startTime)) {
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				String endTime = tempString.substring(hyphenIndex1 + 1).trim();
				if (endTime.length() != 4) {
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				if (!Checker.isValidTime(endTime)) {
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				int startTimeHours = Integer.valueOf(startTime.substring(0, 2).trim());
				int startTimeMinutes = Integer.valueOf(startTime.substring(2, 4).trim());
				int totalStartTime = startTimeHours * HOUR_MINUTES + startTimeMinutes;
				int endTimeHours = Integer.valueOf(endTime.substring(0, 2).trim());
				int endTimeMinutes = Integer.valueOf(endTime.substring(2, 4).trim());
				int totalEndTime = endTimeHours * HOUR_MINUTES + endTimeMinutes;

				System.out.println("totalStartTime:" + totalStartTime);
				System.out.println("totalEndTime:" + totalEndTime);

				if (totalEndTime < totalStartTime) {
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return;
				}

				String taskBeforeChange = allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString();

				// set the end time first, then the start time, as tasks are
				// checked for starting time
				// being less than or equal to end time. Not following this
				// order WILL crash the program
				allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.setEnd(endTime);
				allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.setStart(startTime);

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString()));
			} else {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return;
			}

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
		Task temporaryTask = new Task(remainingCommandString1);
		// ***TO CHECK FOR CLASH IN START AND END FOR EVENT TASK INPUT***

		// This means that there is a clash between an existing event task which
		// is NOT marked as done
		// and a NEW task which is an event task which is NOT marked as done
		if (Checker.isEventTaskInput(remainingCommandString1)) {

			int startingTimeHours = Integer.valueOf(temporaryTask.getStart().substring(0, 2).trim());
			int startingTimeMinutes = Integer.valueOf(temporaryTask.getStart().substring(2, 4).trim());
			int totalStartingTime = startingTimeHours * HOUR_MINUTES + startingTimeMinutes;

			int endingTimeHours = Integer.valueOf(temporaryTask.getEnd().substring(0, 2).trim());
			int endingTimeMinutes = Integer.valueOf(temporaryTask.getEnd().substring(2, 4).trim());
			int totalEndingTime = endingTimeHours * HOUR_MINUTES + endingTimeMinutes;

			for (int w = 0; w < allTasksList.size(); w++) {
				if (Checker.isEventTaskInput(allTasksList.get(w).getScheduleString())) {

					int existingStartingTimeHours = Integer
							.valueOf(allTasksList.get(w).getStart().substring(0, 2).trim());
					int existingStartingTimeMinutes = Integer
							.valueOf(allTasksList.get(w).getStart().substring(2, 4).trim());
					int existingTotalStartingTime = existingStartingTimeHours * HOUR_MINUTES
							+ existingStartingTimeMinutes;

					int existingEndingTimeHours = Integer.valueOf(allTasksList.get(w).getEnd().substring(0, 2).trim());
					int existingEndingTimeMinutes = Integer
							.valueOf(allTasksList.get(w).getEnd().substring(2, 4).trim());
					int existingTotalEndingTime = existingEndingTimeHours * HOUR_MINUTES + existingEndingTimeMinutes;

					if ((totalStartingTime > existingTotalStartingTime) && (totalStartingTime < existingTotalEndingTime)
							|| ((totalEndingTime > existingTotalStartingTime)
									&& (totalEndingTime < existingTotalEndingTime))) {
						flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
						flexWindow.getTextArea().append("\n");

						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();

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
		lastAction.setPreviousChangedScheduleString(null);

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

		System.out.println("lastAction.getPreviousAction():" + lastAction.getPreviousAction());
		System.out.println(
				"lastAction.getPreviousTask().getScheduleString():" + lastAction.getPreviousTask().getScheduleString());
		System.out.println(
				"lastAction.getPreviousChangedScheduleString():" + lastAction.getPreviousChangedScheduleString());

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
				// undo add
				logger.finest(ADD_UNDONE_MESSAGE);
				System.out.println(ADD_UNDONE_MESSAGE);
				System.out.println();

				// reads in the file, line by line
				BufferedReader reader = null;

				reader = new BufferedReader(new FileReader(filename));
				String currentLine = null;

				ArrayList<Task> allTasksListUNDOADD = new ArrayList<Task>();

				do {
					currentLine = reader.readLine();
					if (currentLine != null) {

						allTasksListUNDOADD.add(new Task(currentLine));
					}
				} while (currentLine != null);

				if (reader != null) {
					reader.close();
				}

				Task tempTask = new Task();

				// for exactness, equalsIgnoreCase is not used
				for (int i = 0; i < allTasksListUNDOADD.size(); i++) {
					if (lastAction.getPreviousTask().getScheduleString()
							.equals(allTasksListUNDOADD.get(i).getScheduleString())) {
						tempTask = allTasksListUNDOADD.get(i);
						allTasksListUNDOADD.remove(i);
						lastAction.setPreviousAction("delete");
						lastAction.setPreviousTask(tempTask);
						lastAction.setPreviousChangedScheduleString(null);
					}
				}

				// sort all tasks by date and starting time
				SortAndShow.sortAllTasksByDateAndStartingTime(allTasksListUNDOADD);

				BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

				for (int j = 0; j < allTasksListUNDOADD.size(); j++) {
					writer.write(allTasksListUNDOADD.get(j).getScheduleString());
					writer.newLine();
				}

				writer.close();

			} else if (lastAction.getPreviousAction().equalsIgnoreCase("delete")) {
				// undo delete
				logger.finest(DELETE_UNDONE_MESSAGE);
				System.out.println(DELETE_UNDONE_MESSAGE);
				System.out.println();

				CRUD.addTask(filename, lastAction.getPreviousTask().getScheduleString(), lastAction, flexWindow);

			} else if (lastAction.getPreviousAction().trim().equalsIgnoreCase("change")) {
				// undo change
				logger.finest(CHANGE_UNDONE_MESSAGE);
				System.out.println(CHANGE_UNDONE_MESSAGE);
				System.out.println();

				String taskBeforeChange = lastAction.getPreviousChangedScheduleString();
				System.out.println("taskBeforeChange:" + taskBeforeChange);

				String taskAfterChange = lastAction.getPreviousTask().getScheduleString();
				System.out.println("taskAfterChange:" + taskAfterChange);

				// reads in the file, line by line
				BufferedReader reader = null;

				reader = new BufferedReader(new FileReader(filename));
				String currentLine = null;

				ArrayList<Task> allTasksListUNDOCHANGE = new ArrayList<Task>();

				do {
					currentLine = reader.readLine();
					if (currentLine != null) {

						allTasksListUNDOCHANGE.add(new Task(currentLine));
					}
				} while (currentLine != null);

				if (reader != null) {
					reader.close();
				}

				Task tempTask1 = new Task();

				System.out.println("allTasksListUNDOCHANGE.size() BEFORE for loop to remove task:"
						+ allTasksListUNDOCHANGE.size());

				// for exactness, equalsIgnoreCase is not used
				for (int i = 0; i < allTasksListUNDOCHANGE.size(); i++) {
					if (taskAfterChange.equals(allTasksListUNDOCHANGE.get(i).getScheduleString())) {
						tempTask1 = allTasksListUNDOCHANGE.get(i);
						allTasksListUNDOCHANGE.remove(i);

					}
				}

				System.out.println(
						"allTasksListUNDOCHANGE.size() AFTER for loop to remove task:" + allTasksListUNDOCHANGE.size());

				// sort all tasks by date and starting time
				SortAndShow.sortAllTasksByDateAndStartingTime(allTasksListUNDOCHANGE);

				BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

				for (int j = 0; j < allTasksListUNDOCHANGE.size(); j++) {
					writer.write(allTasksListUNDOCHANGE.get(j).getScheduleString());
					writer.newLine();
				}

				writer.close();

				System.out.println("before CRUD.addTask()");

				System.out.println("lastAction.getPreviousAction():" + lastAction.getPreviousAction());
				System.out.println("lastAction.getPreviousTask().getScheduleString():"
						+ lastAction.getPreviousTask().getScheduleString());
				System.out.println("lastAction.getPreviousChangedScheduleString():"
						+ lastAction.getPreviousChangedScheduleString());

				System.out
						.println("allTasksListUNDOCHANGE.size() BEFORE CRUD.addTask:" + allTasksListUNDOCHANGE.size());

				System.out.println();

				CRUD.addTask(filename, taskBeforeChange, lastAction, flexWindow);

				System.out.println("after CRUD.addTask()");

				System.out.println("lastAction.getPreviousAction():" + lastAction.getPreviousAction());
				System.out.println("lastAction.getPreviousTask().getScheduleString():"
						+ lastAction.getPreviousTask().getScheduleString());
				System.out.println("lastAction.getPreviousChangedScheduleString():"
						+ lastAction.getPreviousChangedScheduleString());

				System.out.println("allTasksListUNDOCHANGE.size() After CRUD.addtask:" + allTasksListUNDOCHANGE.size());

				System.out.println();

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskAfterChange);
				lastAction.setPreviousTask(new Task(taskBeforeChange));

				System.out.println("after setting lastAction before exiting undo()");

				System.out.println("lastAction.getPreviousAction():" + lastAction.getPreviousAction());
				System.out.println("lastAction.getPreviousTask().getScheduleString():"
						+ lastAction.getPreviousTask().getScheduleString());
				System.out.println("lastAction.getPreviousChangedScheduleString():"
						+ lastAction.getPreviousChangedScheduleString());

				System.out.println();

			}

		}

	}
}
