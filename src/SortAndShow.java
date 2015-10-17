import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.*;

public class SortAndShow {

	private static final Logger logger = Logger.getLogger(SortAndShow.class.getName());

	private static final String DISPLAY_SORTED_BY_STARTING_TIMES_MESSAGE = "The tasks, sorted by starting time, are displayed.";
	private static final String DISPLAY_SORTED_BY_ENDING_TIMES_MESSAGE = "The tasks, sorted by ending time, are displayed.";
	private static final String DISPLAY_SORTED_BY_TITLES_MESSAGE = "The tasks, sorted in alphabetical order by title, are displayed.";
	private static final String DISPLAY_SORTED_BY_DESCRIPTIONS_MESSAGE = "The tasks, sorted in alphabetical order by task description, are displayed.";
	private static final String DISPLAY_SORTED_BY_PRIORITY_LEVELS_MESSAGE = "The tasks, sorted in alphabetical order by priority level, is displayed.";
	private static final String DISPLAY_SORTED_BY_CATEGORIES_MESSAGE = "The tasks, sorted in alphabetical order by category, are displayed.";

	private static final String NOT_DONE_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are not marked as "
			+ "done" + " for their categories, are displayed.";
	private static final String ALL_TASKS_DISPLAYED_MESSAGE = "All the tasks in the schedule are displayed.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	// that is, it is valid only if its starting time, or ending time, are NOT
	// between the starting
	// and ending times of existing tasks which are NOT DONE YET
	private static final int HOUR_MINUTES = 60;

	// the form of searching for tasks without executing readAndExecuteCommand()
	// recursively
	// related to the user input command String "show week"
	static void searchAndShowTask(String filename, String remainingCommandString, FlexWindow flexWindow)
			throws IOException {

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		int whitespaceIndex1 = remainingCommandString.indexOf(" ");

		if (whitespaceIndex1 < 0) {
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

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {

				allTasksList.add(new Task(currentLine));
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		if (searchVariableType.equalsIgnoreCase("task")) {
			for (int i = 0; i < allTasksList.size(); i++) {
				// a floating task (done or not done), a deadline task (done or not done) or an event task has a task
				// name (done or not done)
				if (allTasksList.get(i).getTaskName().toLowerCase().indexOf(searchTerm.toLowerCase()) >= 0) {
					flexWindow.getTextArea().append(allTasksList.get(i).getDisplayString() + "\n");
					flexWindow.getTextArea().append("\n");
				}
			}
		} else if (searchVariableType.equalsIgnoreCase("date") || searchVariableType.equalsIgnoreCase("day")) {

			String tempDate = searchTerm;

			// check if the date used for searching, is valid
			if (!Checker.isValidDate(tempDate)) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			for (int i = 0; i < allTasksList.size(); i++) {
				// only a deadline task (done or not done) or an event task (done or not done) will have a date
				if (Checker.isDeadlineTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneDeadlineTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getDate().equalsIgnoreCase(searchTerm)) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}
			}
		} else if (searchVariableType.equalsIgnoreCase("start")) {

			String tempStartTime = searchTerm;

			// check if the starting time used for searching, is valid
			if (!Checker.isValidTime(tempStartTime)) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			for (int i = 0; i < allTasksList.size(); i++) {
				// only an event (done or not done) task will have a starting time
				if (Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getStart().equalsIgnoreCase(searchTerm)) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}
			}
		} else if (searchVariableType.equalsIgnoreCase("end")) {

			// check if the starting time used for searching, is valid
			String tempEndTime = searchTerm;

			if (!Checker.isValidTime(tempEndTime)) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			for (int i = 0; i < allTasksList.size(); i++) {
				// only a deadline task (done or not done) or an event task (done or not done)
				// will have an ending time
				if (Checker.isDeadlineTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneDeadlineTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getEnd().equalsIgnoreCase(searchTerm)) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}
			}
		} else if (searchVariableType.equalsIgnoreCase("priority")) {
			for (int i = 0; i < allTasksList.size(); i++) {
				// only an event task (done or not done) will have a priority (level)
				if (Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getPriority().toLowerCase().indexOf(searchTerm.toLowerCase()) >= 0) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}
			}
		} else {
			// invalid input case
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return;
		}

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

	}

	// displays all files in the schedule by Task.java's comparisonValue
	// in order of starting and ending time
	// each date, followed by each date's deadline tasks, then event tasks are
	// displayed
	// followed by floating tasks after each date
	static void readAndDisplayAll(String filename, FlexWindow flexWindow) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isDeadlineTaskInput(currentLine) || Checker.isDoneDeadlineTaskInput(currentLine)
						|| Checker.isEventTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
					deadlineOrEventTasksList.add(new Task(currentLine));
				} else if (Checker.isFloatingTaskInput(currentLine) || Checker.isDoneFloatingTaskInput(currentLine)) {
					floatingTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(deadlineOrEventTasksList);
		SortAndShow.sortAllTasksByDateAndStartingTime(floatingTasksList);

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
		
		// floating header "Floating"
		flexWindow.getTextArea().append("Floating");
		flexWindow.getTextArea().append("\n");
		
		
		for (int k = 0; k < floatingTasksList.size(); k++) {
			flexWindow.getTextArea().append(floatingTasksList.get(k).getDisplayString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println();

		flexWindow.getTextArea().append("\n");
	}

	// used to sort tasks by starting date and starting time
	static void sortAllTasksByDateAndStartingTime(ArrayList<Task> allTasksList) {
				
		// sort (done or not done) deadline tasks and event tasks by comparison value
		int size1 = allTasksList.size();
		int i, start1, min_index1 = 0;

		for (start1 = 0; start1 < size1 - 1; start1++) {
			min_index1 = start1;

			for (i = start1 + 1; i < size1; i++) {
				if (allTasksList.get(i).getComparisonValue() < allTasksList.get(min_index1).getComparisonValue()) {
					min_index1 = i;
				}
			}

			Task temp1 = allTasksList.get(start1);
			Task temp2 = allTasksList.get(min_index1);
			allTasksList.set(start1, temp2);
			allTasksList.set(min_index1, temp1);
		}


	}

	// shows event tasks in the schedule
	static void showEventTasks(String filename, FlexWindow flexWindow) throws IOException {

		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> showEventTasks = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isEventTaskInput(currentLine)) {
					showEventTasks.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(showEventTasks);

		String tempDate = new String("");

		if (!showEventTasks.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");
			tempDate = showEventTasks.get(0).getDate();
			flexWindow.getTextArea().append("Date: " + tempDate + "\n");
			flexWindow.getTextArea().append("\n");
		}

		for (int j = 0; j < showEventTasks.size(); j++) {
			if (showEventTasks.get(j).getDate().equalsIgnoreCase(tempDate)) {
				flexWindow.getTextArea().append(showEventTasks.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			} else {
				flexWindow.getTextArea()
						.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				flexWindow.getTextArea().append("\n");

				tempDate = showEventTasks.get(j).getDate();

				flexWindow.getTextArea().append("Date: " + tempDate + "\n");
				flexWindow.getTextArea().append("\n");

				flexWindow.getTextArea().append(showEventTasks.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

		flexWindow.getTextArea()
				.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
						+ "\n");
		flexWindow.getTextArea().append("\n");

		logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println();

		flexWindow.getTextArea().append("\n");
	}

	// shows deadline tasks in the schedule
	static void showDeadlineTasks(String filename, FlexWindow flexWindow) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> deadlineTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isDeadlineTaskInput(currentLine)) {
					deadlineTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(deadlineTasksList);

		String tempDate = new String("");

		if (!deadlineTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");
			tempDate = deadlineTasksList.get(0).getDate();
			flexWindow.getTextArea().append("Date: " + tempDate + "\n");
			flexWindow.getTextArea().append("\n");
		}

		for (int j = 0; j < deadlineTasksList.size(); j++) {
			if (deadlineTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				flexWindow.getTextArea().append(deadlineTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			} else {
				flexWindow.getTextArea()
						.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				flexWindow.getTextArea().append("\n");

				tempDate = deadlineTasksList.get(j).getDate();

				flexWindow.getTextArea().append("Date: " + tempDate + "\n");
				flexWindow.getTextArea().append("\n");

				flexWindow.getTextArea().append(deadlineTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

		flexWindow.getTextArea()
				.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
						+ "\n");
		flexWindow.getTextArea().append("\n");

		logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println();

		flexWindow.getTextArea().append("\n");

	}

	// shows floating tasks in the schedule
	static void showFloatingTasks(String filename, FlexWindow flexWindow) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isFloatingTaskInput(currentLine)) {
					floatingTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(floatingTasksList);

		for (int k = 0; k < floatingTasksList.size(); k++) {
			flexWindow.getTextArea().append(floatingTasksList.get(k).getDisplayString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println();

		flexWindow.getTextArea().append("\n");

	}

	// show tasks which are marked as done by the user
	static void showDoneTasks(String filename, FlexWindow flexWindow) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> doneFloatingTasksList = new ArrayList<Task>();
		ArrayList<Task> doneDeadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isDoneFloatingTaskInput(currentLine)) {
					doneFloatingTasksList.add(new Task(currentLine));
				} else if (Checker.isDoneDeadlineTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
					doneDeadlineOrEventTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(doneDeadlineOrEventTasksList);

		String tempDate = new String("");

		if (!doneDeadlineOrEventTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");
			tempDate = doneDeadlineOrEventTasksList.get(0).getDate();
			flexWindow.getTextArea().append("Date: " + tempDate + "\n");
			flexWindow.getTextArea().append("\n");
		}

		for (int j = 0; j < doneDeadlineOrEventTasksList.size(); j++) {
			if (doneDeadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				flexWindow.getTextArea().append(doneDeadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			} else {
				flexWindow.getTextArea()
						.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				flexWindow.getTextArea().append("\n");

				tempDate = doneDeadlineOrEventTasksList.get(j).getDate();

				flexWindow.getTextArea().append("Date: " + tempDate + "\n");
				flexWindow.getTextArea().append("\n");

				flexWindow.getTextArea().append(doneDeadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

		flexWindow.getTextArea()
				.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
						+ "\n");
		flexWindow.getTextArea().append("\n");

		for (int k = 0; k < doneFloatingTasksList.size(); k++) {
			flexWindow.getTextArea().append(doneFloatingTasksList.get(k).getDisplayString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

		logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println();

		flexWindow.getTextArea().append("\n");

	}

	// show tasks which are not marked as done by the user
	static void showNotDoneTasks(String filename, FlexWindow flexWindow) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isFloatingTaskInput(currentLine)) {
					floatingTasksList.add(new Task(currentLine));
				} else if (Checker.isDeadlineTaskInput(currentLine) || Checker.isEventTaskInput(currentLine)) {
					deadlineOrEventTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
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

		logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println();

		flexWindow.getTextArea().append("\n");

	}

}
