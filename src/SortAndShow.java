import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.*;

public class SortAndShow {

	private static final Logger logger = Logger.getLogger(SortAndShow.class.getName());

	private static final String ALL_TASKS_DISPLAYED_MESSAGE = "All the tasks in the schedule are displayed.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	// that is, it is valid only if its starting time, or ending time, are NOT
	// between the starting
	// and ending times of existing tasks which are NOT DONE YET

	// the form of searching for tasks without executing readAndExecuteCommand()
	// recursively
	// related to the user input command String "show week"
	static void searchAndShowTask(String filename, String remainingCommandString, FlexWindow flexWindow)
			throws IOException {

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> recurringTasksList = new ArrayList<Task>();
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

		// CASE 1: searching for tasks using a matching string for the task name
		// floating, event, deadline and recurring tasks all have a task name
		if (searchVariableType.equalsIgnoreCase("task") || searchVariableType.equalsIgnoreCase("taskname")) {
			for (int i = 0; i < allTasksList.size(); i++) {
				// a floating task (done or not done), a deadline task (done or
				// not done) or an event task has a task
				// name (done or not done)

				// a deadline task or an event task, will have a taskname
				if (Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getTaskName().indexOf(searchTerm) >= 0) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}

				// a floating task will also have a task name
				if (Checker.isFloatingTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneFloatingTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getTaskName().indexOf(searchTerm) >= 0) {
						floatingTasksList.add(allTasksList.get(i));
					}
				}

				// a recurring task will also have a task name
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getTaskName().indexOf(searchTerm) >= 0) {
						recurringTasksList.add(allTasksList.get(i));
					}
				}
			}
		} else if (searchVariableType.equalsIgnoreCase("date")) {

			// CASE 2: searching for tasks using an exact date
			// deadline tasks and event tasks have dates
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
				// only a deadline task (done or not done) or an event task
				// (done or not done) will have a date
				if (Checker.isDeadlineTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneDeadlineTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getDate().equalsIgnoreCase(searchTerm)) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}
			}
		} else if (searchVariableType.equalsIgnoreCase("day")) {

			// CASE 3: searching for tasks using an exact day using an exact day
			// (name)
			// e.g. sunday
			// recurring tasks have days
			String tempDay = searchTerm;

			// check if the date used for searching, is valid
			if (!Checker.isValidDay(tempDay)) {
				flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");
				flexWindow.getTextArea().append("\n");

				logger.finest(INVALID_INPUT_MESSAGE);
				System.out.println(INVALID_INPUT_MESSAGE);
				System.out.println();

				return;
			}

			for (int i = 0; i < allTasksList.size(); i++) {
				// only a recurring task
				// will have a day e.g. monday
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getDay().equalsIgnoreCase(searchTerm)) {
						recurringTasksList.add(allTasksList.get(i));
					}
				}
			}
		} else if (searchVariableType.equalsIgnoreCase("start")) {
			// CASE 4: searching for tasks using an exact starting time
			// event tasks and recurring tasks have starting times
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
				// an event task will have a starting time
				if (Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getStart().equalsIgnoreCase(searchTerm)) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}

				// a recurring task will also have a starting time
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getStart().equalsIgnoreCase(searchTerm)) {
						recurringTasksList.add(allTasksList.get(i));
					}
				}
			}
		} else if (searchVariableType.equalsIgnoreCase("end")) {
			// CASE 5: searching for tasks using an exact ending time
			// deadline tasks, event tasks and recurring tasks have ending times
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
				// deadline tasks and event tasks have an ending time
				if (Checker.isDeadlineTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneDeadlineTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getEnd().equalsIgnoreCase(searchTerm)) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}

				// recurring tasks also have an ending time
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getEnd().equalsIgnoreCase(searchTerm)) {
						recurringTasksList.add(allTasksList.get(i));
					}
				}
			}
		} else if (searchVariableType.equalsIgnoreCase("priority")) {
			// CASE 6: search for tasks using a matching String in the priority
			// (level)
			// recuring tasks and event tasks have a priority (level)

			for (int i = 0; i < allTasksList.size(); i++) {

				// event or deadline tasks have a priority (level)
				if (Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getPriority().toLowerCase().indexOf(searchTerm.toLowerCase()) >= 0) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}

				// recurring tasks also have a priority (level)
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getPriority().toLowerCase().indexOf(searchTerm.toLowerCase()) >= 0) {
						recurringTasksList.add(allTasksList.get(i));
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

		int deadlineOrEventTasksListCount = 0;

		for (int j = 0; j < deadlineOrEventTasksList.size(); j++) {
			deadlineOrEventTasksListCount += 1;
			if (deadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				flexWindow.getTextArea().append(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			} else {
				deadlineOrEventTasksListCount = 1;
				flexWindow.getTextArea()
						.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				flexWindow.getTextArea().append("\n");

				tempDate = deadlineOrEventTasksList.get(j).getDate();

				flexWindow.getTextArea().append("Date: " + tempDate + "\n");
				flexWindow.getTextArea().append("\n");

				flexWindow.getTextArea().append(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}
		if (!floatingTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			// floating header "Floating"
			flexWindow.getTextArea().append("Floating" + "\n");
			flexWindow.getTextArea().append("\n");

			int floatingTasksListCount = 0;
			for (int k = 0; k < floatingTasksList.size(); k++) {
				floatingTasksListCount += 1;
				flexWindow.getTextArea()
						.append(floatingTasksListCount + ". " + floatingTasksList.get(k).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

		if (!recurringTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			// recurring task header "Recurring"

			flexWindow.getTextArea().append("Recurring" + "\n");
			flexWindow.getTextArea().append("\n");

			int recurringTasksListCount = 0;

			for (int l = 0; l < recurringTasksList.size(); l++) {
				recurringTasksListCount += 1;
				flexWindow.getTextArea()
						.append(recurringTasksListCount + ". " + recurringTasksList.get(l).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

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

		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();
		ArrayList<Task> recurringTasksList = new ArrayList<Task>();
		ArrayList<Task> floatingTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isDeadlineTaskInput(currentLine) || Checker.isDoneDeadlineTaskInput(currentLine)
						|| Checker.isEventTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
					deadlineOrEventTasksList.add(new Task(currentLine));
				} else if (Checker.isFloatingTaskInput(currentLine) || Checker.isDoneFloatingTaskInput(currentLine)) {
					floatingTasksList.add(new Task(currentLine));
				} else if (Checker.isRecurringTaskInput(currentLine) || Checker.isDoneRecurringTaskInput(currentLine)) {
					recurringTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(deadlineOrEventTasksList);
		SortAndShow.sortAllTasksByDateAndStartingTime(floatingTasksList);
		SortAndShow.sortAllTasksByDateAndStartingTime(recurringTasksList);

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

		int deadlineOrEventTasksListCount = 0;

		for (int j = 0; j < deadlineOrEventTasksList.size(); j++) {
			deadlineOrEventTasksListCount += 1;
			if (deadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				flexWindow.getTextArea().append(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			} else {
				deadlineOrEventTasksListCount = 1;
				flexWindow.getTextArea()
						.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				flexWindow.getTextArea().append("\n");

				tempDate = deadlineOrEventTasksList.get(j).getDate();

				flexWindow.getTextArea().append("Date: " + tempDate + "\n");
				flexWindow.getTextArea().append("\n");

				flexWindow.getTextArea().append(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}
		if (!floatingTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			// floating header "Floating"

			flexWindow.getTextArea().append("Floating" + "\n");
			flexWindow.getTextArea().append("\n");

			int floatingTasksListCount = 0;
			for (int k = 0; k < floatingTasksList.size(); k++) {
				floatingTasksListCount += 1;
				flexWindow.getTextArea()
						.append(floatingTasksListCount + ". " + floatingTasksList.get(k).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}

		}

		// recurring task header "Recurring"
		if (!recurringTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");

			flexWindow.getTextArea().append("Recurring" + "\n");
			flexWindow.getTextArea().append("\n");

			int recurringTasksListCount = 0;

			for (int l = 0; l < recurringTasksList.size(); l++) {
				recurringTasksListCount += 1;
				flexWindow.getTextArea()
						.append(recurringTasksListCount + ". " + recurringTasksList.get(l).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}
		logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println();

		flexWindow.getTextArea().append("\n");
	}

	// used to sort tasks by starting date and starting time
	static void sortAllTasksByDateAndStartingTime(ArrayList<Task> allTasksList) {

		// sort all tasks by comparisonValue first
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

		// sort all deadline and event tasks by index of 5447-year block
		int size11 = deadlineOrEventTasksList.size();
		int s, start11, min_index11 = 0;

		for (start11 = 0; start11 < size11 - 1; start11++) {
			min_index11 = start11;

			for (s = start11 + 1; s < size11; s++) {
				if (deadlineOrEventTasksList.get(s).getLimitBlockIndex() < deadlineOrEventTasksList.get(min_index11)
						.getLimitBlockIndex()) {
					min_index11 = s;
				}
			}

			Task temp20 = deadlineOrEventTasksList.get(start11);
			Task temp21 = deadlineOrEventTasksList.get(min_index11);
			deadlineOrEventTasksList.set(start11, temp21);
			deadlineOrEventTasksList.set(min_index11, temp20);
		}

		
		// sort floating tasks in alphabetical order
		int size2 = floatingTasksList.size();
		int q, start2, min_index2 = 0;

		for (start2 = 0; start2 < size2 - 1; start2++) {
			min_index2 = start2;

			for (q = start2 + 1; q < size2; q++) {
				if (floatingTasksList.get(q).getScheduleString()
						.compareToIgnoreCase(floatingTasksList.get(min_index2).getScheduleString()) < 0) {
					min_index2 = q;
				}
			}

			Task temp3 = floatingTasksList.get(start2);
			Task temp4 = floatingTasksList.get(min_index2);
			floatingTasksList.set(start2, temp4);
			floatingTasksList.set(min_index2, temp3);
		}

		// sort recurring tasks by day and starting time
		int size3 = recurringTasksList.size();
		int k, start3, min_index3 = 0;

		for (start3 = 0; start3 < size3 - 1; start3++) {
			min_index3 = start3;

			for (k = start3 + 1; k < size3; k++) {
				if (recurringTasksList.get(k).getRecurringTaskValue() < recurringTasksList.get(min_index3)
						.getRecurringTaskValue()) {
					min_index3 = k;
				}
			}

			Task temp5 = recurringTasksList.get(start3);
			Task temp6 = recurringTasksList.get(min_index3);
			recurringTasksList.set(start3, temp6);
			recurringTasksList.set(min_index3, temp5);
		}

		ArrayList<Task> tempTasksList = new ArrayList<Task>();

		if (!deadlineOrEventTasksList.isEmpty()) {
			for (int m = 0; m < deadlineOrEventTasksList.size(); m++) {
				tempTasksList.add(deadlineOrEventTasksList.get(m));
			}
		}

		if (!floatingTasksList.isEmpty()) {
			for (int n = 0; n < floatingTasksList.size(); n++) {
				tempTasksList.add(floatingTasksList.get(n));
			}
		}

		if (!recurringTasksList.isEmpty()) {
			for (int o = 0; o < recurringTasksList.size(); o++) {
				tempTasksList.add(recurringTasksList.get(o));
			}
		}

		for (int w = 0; w < allTasksList.size(); w++) {
			allTasksList.set(w, tempTasksList.get(w));
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
				if (Checker.isEventTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
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
				if (Checker.isDeadlineTaskInput(currentLine) || Checker.isDoneDeadlineTaskInput(currentLine)) {
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

	// shows recurring tasks in the schedule
	static void showRecurringTasks(String filename, FlexWindow flexWindow) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> recurringTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isRecurringTaskInput(currentLine) || Checker.isDoneRecurringTaskInput(currentLine)) {
					recurringTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(recurringTasksList);

		for (int k = 0; k < recurringTasksList.size(); k++) {
			flexWindow.getTextArea().append(recurringTasksList.get(k).getDisplayString() + "\n");
			flexWindow.getTextArea().append("\n");
		}

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
				if (Checker.isFloatingTaskInput(currentLine) || Checker.isDoneFloatingTaskInput(currentLine)) {
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

		ArrayList<Task> doneRecurringTasksList = new ArrayList<Task>();
		ArrayList<Task> doneFloatingTasksList = new ArrayList<Task>();
		ArrayList<Task> doneDeadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isDoneFloatingTaskInput(currentLine)) {
					doneFloatingTasksList.add(new Task(currentLine));
				} else if (Checker.isDoneDeadlineTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
					doneDeadlineOrEventTasksList.add(new Task(currentLine));
				} else if (Checker.isDoneRecurringTaskInput(currentLine)) {
					doneRecurringTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}
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

		int doneDeadlineOrEventTasksListCount = 0;

		for (int j = 0; j < doneDeadlineOrEventTasksList.size(); j++) {
			doneDeadlineOrEventTasksListCount += 1;
			if (doneDeadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				flexWindow.getTextArea().append(doneDeadlineOrEventTasksListCount + ". "
						+ doneDeadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			} else {
				doneDeadlineOrEventTasksListCount = 1;
				flexWindow.getTextArea()
						.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				flexWindow.getTextArea().append("\n");

				tempDate = doneDeadlineOrEventTasksList.get(j).getDate();

				flexWindow.getTextArea().append("Date: " + tempDate + "\n");
				flexWindow.getTextArea().append("\n");

				flexWindow.getTextArea().append(doneDeadlineOrEventTasksListCount + ". "
						+ doneDeadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}
		if (!doneFloatingTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			// floating header "Floating"

			flexWindow.getTextArea().append("Floating" + "\n");
			flexWindow.getTextArea().append("\n");

			int floatingTasksListCount = 0;
			for (int k = 0; k < doneFloatingTasksList.size(); k++) {
				floatingTasksListCount += 1;
				flexWindow.getTextArea()
						.append(floatingTasksListCount + ". " + doneFloatingTasksList.get(k).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

		if (!doneRecurringTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			// recurring task header "Recurring"

			flexWindow.getTextArea().append("Recurring" + "\n");
			flexWindow.getTextArea().append("\n");

			int recurringTasksListCount = 0;

			for (int l = 0; l < doneRecurringTasksList.size(); l++) {
				recurringTasksListCount += 1;
				flexWindow.getTextArea().append(
						recurringTasksListCount + ". " + doneRecurringTasksList.get(l).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
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
		ArrayList<Task> recurringTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isFloatingTaskInput(currentLine)) {
					floatingTasksList.add(new Task(currentLine));
				} else if (Checker.isDeadlineTaskInput(currentLine) || Checker.isEventTaskInput(currentLine)) {
					deadlineOrEventTasksList.add(new Task(currentLine));
				} else if (Checker.isRecurringTaskInput(currentLine)) {
					recurringTasksList.add(new Task(currentLine));
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

		int deadlineOrEventTasksListCount = 0;

		for (int j = 0; j < deadlineOrEventTasksList.size(); j++) {
			deadlineOrEventTasksListCount += 1;
			if (deadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				flexWindow.getTextArea().append(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			} else {
				deadlineOrEventTasksListCount = 1;
				flexWindow.getTextArea()
						.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				flexWindow.getTextArea().append("\n");

				tempDate = deadlineOrEventTasksList.get(j).getDate();

				flexWindow.getTextArea().append("Date: " + tempDate + "\n");
				flexWindow.getTextArea().append("\n");

				flexWindow.getTextArea().append(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

		if (!floatingTasksList.isEmpty()) {

			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			// floating header "Floating"
			flexWindow.getTextArea().append("Floating" + "\n");
			flexWindow.getTextArea().append("\n");

			int floatingTasksListCount = 0;
			for (int k = 0; k < floatingTasksList.size(); k++) {
				floatingTasksListCount += 1;
				flexWindow.getTextArea()
						.append(floatingTasksListCount + ". " + floatingTasksList.get(k).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}

		if (!recurringTasksList.isEmpty()) {
			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			flexWindow.getTextArea()
					.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			flexWindow.getTextArea().append("\n");

			// recurring task header "Recurring"
			flexWindow.getTextArea().append("Recurring" + "\n");
			flexWindow.getTextArea().append("\n");

			int recurringTasksListCount = 0;

			for (int l = 0; l < recurringTasksList.size(); l++) {
				recurringTasksListCount += 1;
				flexWindow.getTextArea()
						.append(recurringTasksListCount + ". " + recurringTasksList.get(l).getDisplayString() + "\n");
				flexWindow.getTextArea().append("\n");
			}
		}
		logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println();

		flexWindow.getTextArea().append("\n");

	}

}