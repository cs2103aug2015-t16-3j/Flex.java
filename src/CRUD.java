import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.*;

public class CRUD {
	private static final Logger logger = Logger.getLogger(CRUD.class.getName());

	private static final String NOTHING_TO_UNDO_MESSAGE = "Nothing to undo as no valid 1) adding of a task, 2) deleting of a task, OR 3) Changing a task variable, has been carried out by the user during this program run.";
	private static final String DELETED_MESSAGE = "The specified task has been deleted.";
	private static final String ADDED_MESSAGE = "The task has been successfully added.";
	private static final String CHANGED_MESSAGE = "(The change to the task information is valid and processed.)";
	private static final String CHANGE_UNDONE_MESSAGE = "The last valid change action has been undone.";
	private static final String DELETE_UNDONE_MESSAGE = "The last valid delete action has been undone.";
	private static final String ADD_UNDONE_MESSAGE = "The last valid add action has been undone.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	private static final String MARKED_DONE_OR_NOT_DONE_MESSAGE = "The task has been marked as 1) done, or 2) not done. This is possible because it was previously 1) not done, or 2) done, respectively.";
	private static final String TASK_DOES_NOT_EXIST_MESSAGE = "Task does not exist, so no such task can be deleted.";
	private static final String BLOCKED_MESSAGE = "Unable to add the new event task (which is not done), because the new task (which is not done) clashes with existing event tasks  (which are not done) (on the same date).";
	private static final String CLEAR_UNDONE_MESSAGE = "The clearing of the file has been undone.";
	private static final String UNCLEAR_UNDONE_MESSAGE = "The unclearing of the file has been undone.";
	private static final String CLEAR_MESSAGE = "The file has been cleared.";

	private static final int HOUR_MINUTES = 60;

	// deletes a task
	static String deleteTask(String filename, String remainingString, LastAction lastAction) throws IOException {
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
			} else if (Checker.isRecurringTaskInput(allTasksList.get(j).getScheduleString())) {
				recurringTasksList.add(allTasksList.get(j));
			}
		}

		Task tempTask = new Task();

		String tempString = new String("");

		tempString = remainingString.trim();

		if (tempString.length() <= 0) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
		}

		int whitespaceIndex1 = tempString.indexOf(" ");
		if (whitespaceIndex1 < 0) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return INVALID_INPUT_MESSAGE;
		}

		String deleteVariableType = new String("");
		deleteVariableType = tempString.substring(0, whitespaceIndex1).trim();

		tempString = tempString.substring(whitespaceIndex1 + 1).trim();

		if (tempString.length() <= 0) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return INVALID_INPUT_MESSAGE;
		}

		if (deleteVariableType.equalsIgnoreCase("floating")) {
			// delete floating <number>
			// for floating tasks

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
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return INVALID_INPUT_MESSAGE;
			}

			if (Integer.valueOf(tempNumber) <= 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return INVALID_INPUT_MESSAGE;
			}

			if (Integer.valueOf(tempNumber) > floatingTasksList.size()) {
				FlexWindow.getFeedback().appendText(TASK_DOES_NOT_EXIST_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(TASK_DOES_NOT_EXIST_MESSAGE);
				System.out.println(TASK_DOES_NOT_EXIST_MESSAGE);
				System.out.println();

				return TASK_DOES_NOT_EXIST_MESSAGE;
			}

			tempTask = allTasksList.get(deadlineOrEventTasksList.size() + Integer.valueOf(tempNumber) - 1);

			lastAction.setPreviousTask(tempTask);
			lastAction.setPreviousAction("delete");
			lastAction.setPreviousChangedScheduleString(null);

			allTasksList.remove(deadlineOrEventTasksList.size() + Integer.valueOf(tempNumber) - 1);

			taskExists = true;

		} else if (deleteVariableType.equalsIgnoreCase("rec")) {
			// delete rec <number>
			// for recurring tasks

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
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return INVALID_INPUT_MESSAGE;
			}

			if (Integer.valueOf(tempNumber) <= 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return INVALID_INPUT_MESSAGE;
			}

			if (Integer.valueOf(tempNumber) > recurringTasksList.size()) {
				FlexWindow.getFeedback().appendText(TASK_DOES_NOT_EXIST_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(TASK_DOES_NOT_EXIST_MESSAGE);
				System.out.println(TASK_DOES_NOT_EXIST_MESSAGE);
				System.out.println();

				return TASK_DOES_NOT_EXIST_MESSAGE;
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
			// for deadline and event tasks
			
			assert(Checker.isValidDate(deleteVariableType));

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
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return INVALID_INPUT_MESSAGE;
			}

			if (Integer.valueOf(tempNumber) <= 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return INVALID_INPUT_MESSAGE;
			}

			if (Integer.valueOf(tempNumber) > deadlineOrEventTasksList.size()) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(TASK_DOES_NOT_EXIST_MESSAGE);
				System.out.println(TASK_DOES_NOT_EXIST_MESSAGE);
				System.out.println();

				return TASK_DOES_NOT_EXIST_MESSAGE;
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
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return INVALID_INPUT_MESSAGE;
		}

		if (taskExists == false) {
			FlexWindow.getFeedback().appendText(TASK_DOES_NOT_EXIST_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(TASK_DOES_NOT_EXIST_MESSAGE);
			System.out.println(TASK_DOES_NOT_EXIST_MESSAGE);
			System.out.println();

			return TASK_DOES_NOT_EXIST_MESSAGE;
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

		FlexWindow.getFeedback().appendText(DELETED_MESSAGE + "\n");
		FlexWindow.getFeedback().appendText("\n");

		logger.finest(DELETED_MESSAGE);
		System.out.println(DELETED_MESSAGE);
		System.out.println();

		return DELETED_MESSAGE;
	}

	// changes one of the variables in a task, EXCEPT for the comparison value
	// for sorting all tasks by date and starting time
	// Case 7: changing a task's variable
	// each change/edit command starts with the hyphen on the far left

	// For Editing An Event Task

	// change <date> <number> to <newdate>
	// change <date> <number> priority to <newpriority>
	// change <date> <number> time to <newstart>-<newend>
	// change <date> <number> taskname to <newtaskname>

	// For Editing A Deadline Task
	// change <date> <number> to <newdate>
	// change <date> <number> end by <new end>
	// change <date> <number> by <newend> on <newdate>
	// change <date> <number> taskname to <newtaskname>

	// For Editing A Recurring Task
	// change rec <number> to every <newday>
	// change rec <number> time to <newstart>-<newend>
	// change rec <number> taskname to <newtaskname>

	// For Editing A Floating Task
	// change floating <number> taskname to <newtaskname>

	static String changeTaskVariable(String filename, String remainingCommandString, LastAction lastAction)
			throws IOException {

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
			} else if (Checker.isRecurringTaskInput(allTasksList.get(j).getScheduleString())) {
				recurringTasksList.add(allTasksList.get(j));
			}
		}

		int whitespaceIndex1 = tempString.indexOf(" ");
		if (whitespaceIndex1 < 0) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			return INVALID_INPUT_MESSAGE;
		}

		String firstTerm = tempString.substring(0, whitespaceIndex1).trim();

		tempString = tempString.substring(whitespaceIndex1 + 1).trim();

		if (tempString.length() == 0) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			return INVALID_INPUT_MESSAGE;
		}

		if (Checker.isValidDate(firstTerm)) {
			// CASE 1: for a deadline task or an event task
			
			
			assert(Checker.isValidDate(firstTerm));
			
			String date = firstTerm.trim();

			int whitespaceIndex2 = tempString.indexOf(" ");
			if (whitespaceIndex2 < 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			String number1 = tempString.substring(0, whitespaceIndex2).trim();
			char[] charArray1 = new char[number1.length()];
			number1.getChars(0, number1.length(), charArray1, 0);

			boolean isNumber4 = true;

			for (int c = 0; c < number1.length(); c++) {
				if (!Character.isDigit(charArray1[c])) {
					isNumber4 = false;
				}
			}

			if (!isNumber4) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			int taskCountForDate = 0;
			int firstTaskForDateIndex = -1;
			// e.g. firstTaskForDateIndex is 10
			// and taskCountForDate is 3 (index 10, index 11, index 12)
			// and the given number is 2
			// then the index of the task to have its information changed is
			// equal to 11
			// which is calculated as
			// firstTaskForDateIndex + Integer.valueOf(number1) - 1
			// = 10 + 2 - 1 = 11

			for (int i = 0; i < deadlineOrEventTasksList.size(); i++) {
				if (allTasksList.get(i).getDate().equalsIgnoreCase(date)) {
					firstTaskForDateIndex = i;
					break;
				}
			}

			for (int i = 0; i < deadlineOrEventTasksList.size(); i++) {
				if (allTasksList.get(i).getDate().equalsIgnoreCase(date)) {
					taskCountForDate += 1;
				}
			}

			if (firstTaskForDateIndex < 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			if ((Integer.valueOf(number1) <= 0) || (Integer.valueOf(number1) > taskCountForDate)) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			tempString = tempString.substring(whitespaceIndex2 + 1).trim();

			if (tempString.length() == 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			int tasknameWhitespaceToIndex2 = tempString.indexOf("taskname to ");
			int dateWhitespaceToIndex2 = tempString.indexOf("date to ");
			int timeWhitespaceToIndex2 = tempString.indexOf("time to ");
			int endWhitespaceByIndex2 = tempString.indexOf("end by ");
			int byIndex2 = tempString.indexOf("by ");

			if (tasknameWhitespaceToIndex2 == 0) {
				tempString = tempString.substring(tasknameWhitespaceToIndex2 + 12).trim();

				if (tempString.length() == 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String newTaskName = tempString.trim();
				String taskBeforeChange = allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1)
						.getScheduleString();

				allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).setTaskName(newTaskName);

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(
						allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).getScheduleString()));

			} else if (dateWhitespaceToIndex2 == 0) {
				tempString = tempString.substring(dateWhitespaceToIndex2 + 8).trim();

				if (tempString.length() == 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String newDate = tempString.trim();

				if (!Checker.isValidDate(newDate)) {
					
					assert(!Checker.isValidDate(newDate));
					
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String taskBeforeChange = allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1)
						.getScheduleString();

				allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).setDate(newDate);

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(
						allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).getScheduleString()));

			} else if (timeWhitespaceToIndex2 == 0) {

				tempString = tempString.substring(timeWhitespaceToIndex2 + 8).trim();

				if (tempString.length() == 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				int hyphenIndex3 = tempString.indexOf("-");

				if (hyphenIndex3 < 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String startingTime = tempString.substring(0, hyphenIndex3).trim();

				if (startingTime.length() != 4) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (!Checker.isValidTime(startingTime)) {
					assert(!Checker.isValidDate(startingTime));
					
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String endingTime = tempString.substring(hyphenIndex3 + 1).trim();

				if (endingTime.length() != 4) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (!Checker.isValidTime(endingTime)) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (Integer.valueOf(endingTime.substring(0, 2)) * HOUR_MINUTES
						+ Integer.valueOf(endingTime.substring(2, 4)) < Integer.valueOf(startingTime.substring(0, 2))
								* HOUR_MINUTES + Integer.valueOf(startingTime.substring(2, 4))) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String taskBeforeChange = allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1)
						.getScheduleString();

				// the task must to be changed should be an event task
				if (!(Checker.isEventTaskInput(taskBeforeChange) || Checker.isDoneEventTaskInput(taskBeforeChange))) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).setEnd(endingTime);

				allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).setStart(startingTime);

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(
						allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).getScheduleString()));

			} else if (endWhitespaceByIndex2 == 0) {
				tempString = tempString.substring(endWhitespaceByIndex2 + 7).trim();

				if (tempString.length() == 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String newEndingTime = tempString.trim();

				if (!Checker.isValidTime(newEndingTime)) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String taskBeforeChange = allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1)
						.getScheduleString();

				// the task to be changed should be a deadline task
				if (!(Checker.isDeadlineTaskInput(taskBeforeChange)
						|| Checker.isDoneDeadlineTaskInput(taskBeforeChange))) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).setEnd(newEndingTime);

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(
						allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).getScheduleString()));

			} else if (byIndex2 == 0) {
				tempString = tempString.substring(byIndex2 + 3).trim();
				if (tempString.length() == 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				int whitespaceOnWhitespaceIndex1 = tempString.indexOf(" on ");

				if (whitespaceOnWhitespaceIndex1 < 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String newEndingTime6 = tempString.substring(0, whitespaceOnWhitespaceIndex1).trim();

				if (newEndingTime6.length() != 4) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (!Checker.isValidTime(newEndingTime6)) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String newDate2 = tempString.substring(whitespaceOnWhitespaceIndex1 + 4).trim();

				if (newDate2.length() == 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (!Checker.isValidDate(newDate2)) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String taskBeforeChange = allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1)
						.getScheduleString();

				allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).setEnd(newEndingTime6);

				allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).setDate(newDate2);

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(
						allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).getScheduleString()));

			} else {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}
		} else if (firstTerm.equalsIgnoreCase("floating")) {
			// For editing a floating task
			// change floating <number> done

			int whitespaceIndex3 = tempString.indexOf(" ");
			if (whitespaceIndex3 < 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			String number2 = tempString.substring(0, whitespaceIndex3).trim();
			char[] charArray1 = new char[number2.length()];
			number2.getChars(0, number2.length(), charArray1, 0);

			boolean isNumber2 = true;

			for (int c = 0; c < number2.length(); c++) {
				if (!Character.isDigit(charArray1[c])) {
					isNumber2 = false;
				}
			}

			if (!isNumber2) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			if ((Integer.valueOf(number2) <= 0) || (Integer.valueOf(number2) > floatingTasksList.size())) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			tempString = tempString.substring(whitespaceIndex3 + 1).trim();

			if (tempString.length() == 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			int tasknameWhitespaceToIndex3 = tempString.indexOf("taskname to ");

			if (tasknameWhitespaceToIndex3 == 0) {
				tempString = tempString.substring(tasknameWhitespaceToIndex3 + 12).trim();

				if (tempString.length() == 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String newTaskName = tempString.trim();
				String taskBeforeChange = allTasksList
						.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1).getScheduleString();

				allTasksList.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1)
						.setTaskName(newTaskName);

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(allTasksList
						.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1).getScheduleString()));

			} else {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

		} else if (firstTerm.equalsIgnoreCase("rec")) {
			// For Editing A Recurring Task
			// change rec <number> to every <newday>
			// change rec <number> time to <newstart>-<newend>
			// change rec <number> taskname to <newtaskname>

			int whitespaceIndex2 = tempString.indexOf(" ");
			if (whitespaceIndex2 < 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
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
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			if ((Integer.valueOf(number1) <= 0) || (Integer.valueOf(number1) > recurringTasksList.size())) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			tempString = tempString.substring(whitespaceIndex2 + 1).trim();

			if (tempString.length() == 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			int tasknameWhitespaceToIndex5 = tempString.indexOf("taskname to ");
			int toWhitespaceEveryWhitespaceIndex1 = tempString.indexOf("to every ");
			int timeWhitespaceToWhitespaceIndex1 = tempString.indexOf("time to ");

			if (tasknameWhitespaceToIndex5 == 0) {
				tempString = tempString.substring(tasknameWhitespaceToIndex5 + 12).trim();

				if (tempString.length() == 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String newTaskName = tempString.trim();
				String taskBeforeChange = allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString();

				allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.setTaskName(newTaskName);

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(allTasksList
						.get(deadlineOrEventTasksList.size() + floatingTasksList.size() + Integer.valueOf(number1) - 1)
						.getScheduleString()));

			} else if (toWhitespaceEveryWhitespaceIndex1 == 0) {
				tempString = tempString.substring(toWhitespaceEveryWhitespaceIndex1 + 9).trim();
				if (tempString.length() == 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (!Checker.isValidDay(tempString)) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
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
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				int hyphenIndex1 = tempString.indexOf("-");

				if (hyphenIndex1 < 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");
					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String startTime = tempString.substring(0, hyphenIndex1).trim();
				if (startTime.length() != 4) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (!Checker.isValidTime(startTime)) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String endTime = tempString.substring(hyphenIndex1 + 1).trim();
				if (endTime.length() != 4) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (!Checker.isValidTime(endTime)) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				int startTimeHours = Integer.valueOf(startTime.substring(0, 2).trim());
				int startTimeMinutes = Integer.valueOf(startTime.substring(2, 4).trim());
				int totalStartTime = startTimeHours * HOUR_MINUTES + startTimeMinutes;
				int endTimeHours = Integer.valueOf(endTime.substring(0, 2).trim());
				int endTimeMinutes = Integer.valueOf(endTime.substring(2, 4).trim());
				int totalEndTime = endTimeHours * HOUR_MINUTES + endTimeMinutes;

				if (totalEndTime < totalStartTime) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
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
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

		} else {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			return INVALID_INPUT_MESSAGE;
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

		FlexWindow.getFeedback().appendText(CHANGED_MESSAGE + "\n");
		FlexWindow.getFeedback().appendText("\n");

		logger.finest(CHANGED_MESSAGE);
		System.out.println(CHANGED_MESSAGE);
		System.out.println();

		return CHANGED_MESSAGE;

	}

	// adds a task
	static String addTask(String filename, String remainingCommandString, LastAction lastAction) throws IOException {
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
				|| Checker.isRecurringTaskInput(remainingCommandString1));

		// if the task is not valid, do not continue the process of adding a
		// task
		if (!isAddedTaskValid) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return INVALID_INPUT_MESSAGE;
		}
		Task temporaryTask = new Task(remainingCommandString1);
		// ***TO CHECK FOR CLASH IN START AND END FOR EVENT TASK INPUT***

		// This means that there is a clash between an existing event task which
		// is NOT marked as done
		// and a NEW task which is an event task which is NOT marked as done
		if (Checker.isEventTaskInput(remainingCommandString1)
				|| Checker.isDoneEventTaskInput(remainingCommandString1)) {

			int startingTimeHours = Integer.valueOf(temporaryTask.getStart().substring(0, 2).trim());
			int startingTimeMinutes = Integer.valueOf(temporaryTask.getStart().substring(2, 4).trim());
			int totalStartingTime = startingTimeHours * HOUR_MINUTES + startingTimeMinutes;

			int endingTimeHours = Integer.valueOf(temporaryTask.getEnd().substring(0, 2).trim());
			int endingTimeMinutes = Integer.valueOf(temporaryTask.getEnd().substring(2, 4).trim());
			int totalEndingTime = endingTimeHours * HOUR_MINUTES + endingTimeMinutes;

			for (int w = 0; w < allTasksList.size(); w++) {
				if ((Checker.isEventTaskInput(allTasksList.get(w).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(w).getScheduleString()))
						&& (allTasksList.get(w).getDate() != null)
						&& (allTasksList.get(w).getDate().equalsIgnoreCase(temporaryTask.getDate()))) {

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
						FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
						FlexWindow.getFeedback().appendText("\n");

						logger.finest(INVALID_INPUT_MESSAGE);
						System.out.println(INVALID_INPUT_MESSAGE);
						System.out.println();

						FlexWindow.getFeedback().appendText(BLOCKED_MESSAGE + "\n");
						FlexWindow.getFeedback().appendText("\n");

						logger.finest(BLOCKED_MESSAGE);
						System.out.println(BLOCKED_MESSAGE);
						System.out.println();

						return BLOCKED_MESSAGE;

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

		FlexWindow.getFeedback()
				.appendText("The task " + "\"" + tempTask.getDisplayString() + "\"" + " has been added." + "\n");
		FlexWindow.getFeedback().appendText("\n");

		return "The task " + "\"" + tempTask.getDisplayString() + "\"" + " has been added." + "\n";
	}

	// undo the previous VALID action, only if the previous action was adding a
	// task,
	// deleting a task,
	// or changing a task's variable
	// This is because there is no need to undo a search task
	static String undo(String filename, LastAction lastAction) throws IOException, NullPointerException {

		if (lastAction.getPreviousAction() == null || lastAction.getPreviousTask() == null) {
			FlexWindow.getFeedback().appendText(NOTHING_TO_UNDO_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(NOTHING_TO_UNDO_MESSAGE);
			System.out.println(NOTHING_TO_UNDO_MESSAGE);
			System.out.println();

			return NOTHING_TO_UNDO_MESSAGE;
		}

		int whitespaceIndex1 = lastAction.getPreviousAction().trim().indexOf(" ");

		if (whitespaceIndex1 < 0) {

			if (lastAction.getPreviousAction().equalsIgnoreCase("add")) {
				// undo add

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
						break;
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

				logger.finest(ADD_UNDONE_MESSAGE);
				System.out.println(ADD_UNDONE_MESSAGE);
				System.out.println();

				FlexWindow.getFeedback().appendText(ADD_UNDONE_MESSAGE);
				FlexWindow.getFeedback().appendText("\n");

				return ADD_UNDONE_MESSAGE;

			} else if (lastAction.getPreviousAction().equalsIgnoreCase("delete")) {
				// undo delete

				CRUD.addTask(filename, lastAction.getPreviousTask().getScheduleString(), lastAction);

				logger.finest(DELETE_UNDONE_MESSAGE);
				System.out.println(DELETE_UNDONE_MESSAGE);
				System.out.println();

				FlexWindow.getFeedback().appendText(DELETE_UNDONE_MESSAGE);
				FlexWindow.getFeedback().appendText("\n");

				return DELETE_UNDONE_MESSAGE;

			} else if (lastAction.getPreviousAction().trim().equalsIgnoreCase("change")) {
				// undo change

				String taskBeforeChange = lastAction.getPreviousChangedScheduleString();

				String taskAfterChange = lastAction.getPreviousTask().getScheduleString();

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

				// for exactness, equalsIgnoreCase is not used
				for (int i = 0; i < allTasksListUNDOCHANGE.size(); i++) {
					if (taskAfterChange.equals(allTasksListUNDOCHANGE.get(i).getScheduleString())) {
						allTasksListUNDOCHANGE.remove(i);
						break;
					}
				}

				// sort all tasks by date and starting time
				SortAndShow.sortAllTasksByDateAndStartingTime(allTasksListUNDOCHANGE);

				BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

				for (int j = 0; j < allTasksListUNDOCHANGE.size(); j++) {
					writer.write(allTasksListUNDOCHANGE.get(j).getScheduleString());
					writer.newLine();
				}

				writer.close();

				CRUD.addTask(filename, taskBeforeChange, lastAction);

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskAfterChange);
				lastAction.setPreviousTask(new Task(taskBeforeChange));

				logger.finest(CHANGE_UNDONE_MESSAGE);
				System.out.println(CHANGE_UNDONE_MESSAGE);
				System.out.println();

				FlexWindow.getFeedback().appendText(CHANGE_UNDONE_MESSAGE);
				FlexWindow.getFeedback().appendText("\n");

				return CHANGE_UNDONE_MESSAGE;

			} else if (lastAction.getPreviousAction().trim().equalsIgnoreCase("clear")) {

				BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

				for (int z = 0; z < lastAction.getClearTaskList().size(); z++) {
					writer.write(lastAction.getClearTaskList().get(z).getScheduleString());
					writer.newLine();
				}

				writer.close();

				lastAction.setPreviousAction("unclear");
				lastAction.setClearTaskList(lastAction.getClearTaskList());

				FlexWindow.getFeedback().appendText(CLEAR_UNDONE_MESSAGE);
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(CLEAR_UNDONE_MESSAGE);
				System.out.println(CLEAR_UNDONE_MESSAGE);
				System.out.println();

				return CLEAR_UNDONE_MESSAGE;

			} else if (lastAction.getPreviousAction().trim().equalsIgnoreCase("unclear")) {
				CRUD.clear(filename, lastAction);

				FlexWindow.getFeedback().appendText(UNCLEAR_UNDONE_MESSAGE);
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(UNCLEAR_UNDONE_MESSAGE);
				System.out.println(UNCLEAR_UNDONE_MESSAGE);
				System.out.println();

				return UNCLEAR_UNDONE_MESSAGE;
			}
		}

		return NOTHING_TO_UNDO_MESSAGE;
	}

	// mark a deadline, event or floating task
	// as done or not done
	static String markAsDone(String filename, String remainingCommandString, LastAction lastAction) throws IOException {
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

		for (int j = 0; j < allTasksList.size(); j++) {
			if (Checker.isDeadlineTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneDeadlineTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isEventTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneEventTaskInput(allTasksList.get(j).getScheduleString())) {
				deadlineOrEventTasksList.add(allTasksList.get(j));
			} else if (Checker.isFloatingTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneFloatingTaskInput(allTasksList.get(j).getScheduleString())) {
				floatingTasksList.add(allTasksList.get(j));
			}
		}

		int whitespaceIndex1 = tempString.indexOf(" ");
		if (whitespaceIndex1 < 0) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			return INVALID_INPUT_MESSAGE;
		}

		String firstTerm = tempString.substring(0, whitespaceIndex1).trim();

		tempString = tempString.substring(whitespaceIndex1 + 1).trim();

		if (tempString.length() == 0) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			return INVALID_INPUT_MESSAGE;
		}

		if (Checker.isValidDate(firstTerm)) {
			// CASE 1: for a deadline task or an event task
			// mark <date> <number> done
			// mark <date> <number> not done

			assert(Checker.isValidDate(firstTerm));
			
			String date = firstTerm.trim();

			int whitespaceIndex2 = tempString.indexOf(" ");
			if (whitespaceIndex2 < 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			String number1 = tempString.substring(0, whitespaceIndex2).trim();
			char[] charArray1 = new char[number1.length()];
			number1.getChars(0, number1.length(), charArray1, 0);

			boolean isNumber4 = true;

			for (int c = 0; c < number1.length(); c++) {
				if (!Character.isDigit(charArray1[c])) {
					isNumber4 = false;
				}
			}

			if (!isNumber4) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			int taskCountForDate = 0;
			int firstTaskForDateIndex = -1;
			// e.g. firstTaskForDateIndex is 10
			// and taskCountForDate is 3 (index 10, index 11, index 12)
			// and the given number is 2
			// then the index of the task to have its information changed is
			// equal to 11
			// which is calculated as
			// firstTaskForDateIndex + Integer.valueOf(number1) - 1
			// = 10 + 2 - 1 = 11

			for (int i = 0; i < deadlineOrEventTasksList.size(); i++) {
				if (allTasksList.get(i).getDate().equalsIgnoreCase(date)) {
					firstTaskForDateIndex = i;
					break;
				}
			}

			for (int i = 0; i < deadlineOrEventTasksList.size(); i++) {
				if (allTasksList.get(i).getDate().equalsIgnoreCase(date)) {
					taskCountForDate += 1;
				}
			}

			if (firstTaskForDateIndex < 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			if ((Integer.valueOf(number1) <= 0) || (Integer.valueOf(number1) > taskCountForDate)) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			tempString = tempString.substring(whitespaceIndex2 + 1).trim();

			if (tempString.length() == 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			int notWhitespaceDoneIndex2 = tempString.indexOf("not done");
			int doneIndex2 = tempString.indexOf("done");

			if (notWhitespaceDoneIndex2 == 0) {
				tempString = tempString.substring(notWhitespaceDoneIndex2 + 8).trim();

				if (tempString.length() != 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).getDone() == null) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String taskBeforeChange = allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1)
						.getScheduleString();

				allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).setNotDone();

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(
						allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).getScheduleString()));

			} else if (doneIndex2 == 0) {
				tempString = tempString.substring(doneIndex2 + 4).trim();

				if (tempString.length() != 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).getDone() != null) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String taskBeforeChange = allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1)
						.getScheduleString();

				allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).setDone();

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(
						allTasksList.get(firstTaskForDateIndex + Integer.valueOf(number1) - 1).getScheduleString()));

			}

		} else if (firstTerm.equalsIgnoreCase("floating")) {
			// For editing a floating task
			// mark floating <number> done
			// mark floating <number> not done

			int whitespaceIndex3 = tempString.indexOf(" ");
			if (whitespaceIndex3 < 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			String number2 = tempString.substring(0, whitespaceIndex3).trim();
			char[] charArray1 = new char[number2.length()];
			number2.getChars(0, number2.length(), charArray1, 0);

			boolean isNumber2 = true;

			for (int c = 0; c < number2.length(); c++) {
				if (!Character.isDigit(charArray1[c])) {
					isNumber2 = false;
				}
			}

			if (!isNumber2) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			if ((Integer.valueOf(number2) <= 0) || (Integer.valueOf(number2) > floatingTasksList.size())) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			tempString = tempString.substring(whitespaceIndex3 + 1).trim();

			if (tempString.length() == 0) {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}

			int notWhitespaceDoneIndex2 = tempString.indexOf("not done");
			int doneIndex2 = tempString.indexOf("done");

			if (notWhitespaceDoneIndex2 == 0) {
				tempString = tempString.substring(notWhitespaceDoneIndex2 + 8).trim();

				if (tempString.length() != 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (allTasksList.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1)
						.getDone() == null) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String taskBeforeChange = allTasksList
						.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1).getScheduleString();

				allTasksList.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1).setNotDone();

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(allTasksList
						.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1).getScheduleString()));

			} else if (doneIndex2 == 0) {
				tempString = tempString.substring(doneIndex2 + 4).trim();

				if (tempString.length() != 0) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				if (allTasksList.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1)
						.getDone() != null) {
					FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
					FlexWindow.getFeedback().appendText("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
					return INVALID_INPUT_MESSAGE;
				}

				String taskBeforeChange = allTasksList
						.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1).getScheduleString();

				allTasksList.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1).setDone();

				lastAction.setPreviousAction("change");
				lastAction.setPreviousChangedScheduleString(taskBeforeChange);
				lastAction.setPreviousTask(new Task(allTasksList
						.get(deadlineOrEventTasksList.size() + Integer.valueOf(number2) - 1).getScheduleString()));
			} else {
				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
				return INVALID_INPUT_MESSAGE;
			}
		} else {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			return INVALID_INPUT_MESSAGE;
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

		FlexWindow.getFeedback().appendText(MARKED_DONE_OR_NOT_DONE_MESSAGE + "\n");
		FlexWindow.getFeedback().appendText("\n");

		logger.finest(MARKED_DONE_OR_NOT_DONE_MESSAGE);
		System.out.println(MARKED_DONE_OR_NOT_DONE_MESSAGE);
		System.out.println();

		return MARKED_DONE_OR_NOT_DONE_MESSAGE;

	}

	// reads the .txt file, then clears it
	static void clear(String filename, LastAction lastAction) throws IOException {
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

		lastAction.setClearTaskList(allTasksList);
		lastAction.setPreviousAction("clear");

		// overwrite the .txt file with one single empty String
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write("");

		writer.close();

		logger.finest(CLEAR_MESSAGE);
		System.out.println(CLEAR_MESSAGE);
		System.out.println();

		FlexWindow.getFeedback().appendText(CLEAR_MESSAGE);
		FlexWindow.getFeedback().appendText("\n");

	}
}
