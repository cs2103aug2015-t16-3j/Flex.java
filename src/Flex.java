
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

public class Flex {

	private static final Logger logger = Logger.getLogger(Flex.class.getName());

	private static FlexWindow flexWindow;
	private static Scanner sc;
	private static String filename = new String("");

	private static final String DONE_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are marked as "
			+ "done" + " for their categories, are displayed.";
	private static final String NOT_DONE_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are not marked as "
			+ "done" + " for their categories, are displayed.";
	private static final String DEADLINE_TASKS_DISPLAYED_MESSAGE = "Deadline tasks in the schedule are displayed.";
	private static final String FLOATING_TASKS_DISPLAYED_MESSAGE = "Floating tasks in the schedule are displayed.";
	private static final String EVENT_TASKS_DISPLAYED_MESSAGE = "Event tasks in the schedule are displayed.";
	private static final String RECURRING_TASKS_DISPLAYED_MESSAGE = "Recurring tasks in the schedule are displayed.";

	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";

	private static final String EXIT_MESSAGE = "Exiting the program.";
	private static final String FILENAME_ACCEPTED_MESSAGE = "The filename is accepted.";
	private static final String PROCEED_MESSAGE = "Please proceed with the user input commands.";
	private static final String FILENAME_INPUT_MESSAGE = "Please enter the full path name of the .txt schedule file, including its name. For example: C:"
			+ "\\" + "Users" + "\\" + "Owner" + "\\" + "Documents" + "\\" + "Flex" + "." + "java" + "\\" + "src" + "\\"
			+ "FlexTest" + "." + "txt";

	// Note: The programs starts by typing "java Flex" in command line prompt.

	public static void main(String[] args) throws IOException {
		flexWindow = new FlexWindow();
		flexWindow.setTitle("Flex");
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

		while ((!tempFile.exists()) || (filename.length() <= 4)
				|| (!filename.substring(filename.length() - 4, filename.length()).equalsIgnoreCase(".txt"))) {
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

		// this method takes care of the manipulation to be done, as well as the
		// operation for exiting the program
		readAndExecuteCommand(filename, lastAction, flexWindow);
	}

	static void readAndExecuteCommand(String filename, LastAction lastAction, FlexWindow flexWindow)
			throws IOException {
		System.out.println();

		sc = new Scanner(System.in);

		String command = sc.nextLine();

		command.trim();

		System.out.println();

		String firstWord = new String("");

		int whitespaceIndex = 0;

		whitespaceIndex = command.indexOf(" ");

		// Note: clear the output display area after the user input command line
		// has been entered
		flexWindow.getTextArea().setText("");

		if (whitespaceIndex < 0) {

			firstWord = command;

			// Case 1: The program Flex.java will exit itself in Command Line
			// Prompt (cmd).
			if (firstWord.equalsIgnoreCase("exit")) {
				flexWindow.getTextArea().append(EXIT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(EXIT_MESSAGE);
				System.out.println(EXIT_MESSAGE);
				System.out.println();

				System.exit(1);
			} else if (firstWord.equalsIgnoreCase("undo")) {
				// Case 2: undo the last action
				// Note: This method will call readAndExecuteCommand again
				CRUD.undo(filename, lastAction, flexWindow);
			} else {
				// Case 3: invalid input
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();
			}
		} else {

			// first word in the user's input, if there is a whitespace
			// character in it
			firstWord = command.substring(0, whitespaceIndex);
			firstWord.trim();

			// Case 4: invalid input
			if (firstWord.substring(0, 1).equalsIgnoreCase("")) {

				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

			} else if (firstWord.equalsIgnoreCase("add")) {
				// Case 5: adding a task
				String remainingCommandString = command.substring(whitespaceIndex + 1).trim();
				remainingCommandString.trim();

				if (remainingCommandString.length() == 0) {
					// INVALID if the remaining command string is empty
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
				}

				boolean isAddedTaskValid = (Checker.isFloatingTaskInput(remainingCommandString)
						|| Checker.isDoneFloatingTaskInput(remainingCommandString)
						|| Checker.isDeadlineTaskInput(remainingCommandString)
						|| Checker.isDoneDeadlineTaskInput(remainingCommandString)
						|| Checker.isEventTaskInput(remainingCommandString)
						|| Checker.isDoneEventTaskInput(remainingCommandString)
						|| Checker.isRecurringTaskInput(remainingCommandString));

				// Only if the task is a floating task, a deadline task, or a
				// normal task, then it will be attempted to be added to the
				// .txt schedule file (i.e. tasks which are not done) yet
				if (isAddedTaskValid) {
					CRUD.addTask(filename, remainingCommandString, lastAction, flexWindow);
				} else {
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
				}
			} else if (firstWord.equalsIgnoreCase("delete")) {
				// Case 6: Deleting a task
				String remainingCommandString = command.substring(whitespaceIndex + 1);
				remainingCommandString = remainingCommandString.trim();

				if (remainingCommandString.length() == 0) {
					// INVALID if the remaining command string is empty
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
				}

				// only if input is valid
				CRUD.deleteTask(filename, remainingCommandString, lastAction, flexWindow);

			} else if (firstWord.equalsIgnoreCase("change")) {
				// Case 7: changing a task's variable
				// each change/edit command starts with the hyphen on the far
				// left

				String remainingCommandString = command.substring(whitespaceIndex + 1).trim();
				remainingCommandString.trim();

				if (remainingCommandString.length() == 0) {
					// INVALID if the remaining command string is empty
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
				}

				// only if input is valid
				// Note: This method will call readAndExecuteCommand again
				CRUD.changeTaskVariable(filename, remainingCommandString, lastAction, flexWindow);

			} else if (firstWord.equalsIgnoreCase("search")) {
				// Case 8: Search for tasks
				// (ignoring upper and lower cases),
				// and displaying the search results

				String remainingCommandString = command.substring(whitespaceIndex + 1).trim();
				remainingCommandString.trim();

				if (remainingCommandString.length() == 0) {
					// INVALID if the remaining command string is empty
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();
				}

				// only if the input is valid
				// Note: This method will call readAndExecuteCommand again
				SortAndShow.searchAndShowTask(filename, remainingCommandString, flexWindow);

			} else if ((firstWord.equalsIgnoreCase("show")) || (firstWord.equalsIgnoreCase("display"))) {
				// Case 9:
				// Show tasks organized in groups
				// or show all tasks
				// in the schedule file
				// without altering/editing/overwriting the schedule file

				String remainingString = command.substring(whitespaceIndex + 1).trim();
				remainingString.trim();

				// Note: The schedule file is already sorted by date and
				// starting time

				if ((remainingString.equalsIgnoreCase("by date")) || (remainingString.equalsIgnoreCase("all"))) {

					SortAndShow.readAndDisplayAll(filename, flexWindow);

				} else if (remainingString.equalsIgnoreCase("done")) {

					logger.finest(DONE_TASKS_DISPLAYED_MESSAGE);
					System.out.println(DONE_TASKS_DISPLAYED_MESSAGE);
					System.out.println();

					SortAndShow.showDoneTasks(filename, flexWindow);
				} else if (remainingString.equalsIgnoreCase("not done")) {

					logger.finest(NOT_DONE_TASKS_DISPLAYED_MESSAGE);
					System.out.println(NOT_DONE_TASKS_DISPLAYED_MESSAGE);
					System.out.println();

					SortAndShow.showNotDoneTasks(filename, flexWindow);
				} else if (remainingString.equalsIgnoreCase("deadline")) {
					logger.finest(DEADLINE_TASKS_DISPLAYED_MESSAGE);
					System.out.println(DEADLINE_TASKS_DISPLAYED_MESSAGE);
					System.out.println();

					SortAndShow.showDeadlineTasks(filename, flexWindow);
				} else if (remainingString.equalsIgnoreCase("floating")) {
					logger.finest(FLOATING_TASKS_DISPLAYED_MESSAGE);
					System.out.println(FLOATING_TASKS_DISPLAYED_MESSAGE);
					System.out.println();

					SortAndShow.showFloatingTasks(filename, flexWindow);
				} else if (remainingString.equalsIgnoreCase("event") || remainingString.equalsIgnoreCase("events")) {
					logger.finest(EVENT_TASKS_DISPLAYED_MESSAGE);
					System.out.println(EVENT_TASKS_DISPLAYED_MESSAGE);
					System.out.println();

					SortAndShow.showEventTasks(filename, flexWindow);
				} else if (remainingString.equalsIgnoreCase("recurring")) {
					logger.finest(RECURRING_TASKS_DISPLAYED_MESSAGE);
					System.out.println(RECURRING_TASKS_DISPLAYED_MESSAGE);
					System.out.println();

					SortAndShow.showRecurringTasks(filename, flexWindow);
				} else if (remainingString.equalsIgnoreCase("week")) {
					ShowDays.showWeek(filename, flexWindow);
				} else {
					flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
					flexWindow.getTextArea().append("\n");

					logger.finest(INVALID_INPUT_MESSAGE);
					System.out.println(INVALID_INPUT_MESSAGE);
					System.out.println();

				}
			} else {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

			}
		}
		readAndExecuteCommand(filename, lastAction, flexWindow);
	}

}