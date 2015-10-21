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
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
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
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
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
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
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
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
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
				if (Checker.isRecurringTaskInput(allTasksList.get(i).getScheduleString())) {
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
				} else if (Checker.isRecurringTaskInput(currentLine)) {
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

		ArrayList<Task> deadlineTasksList = new ArrayList<Task>();
		ArrayList<Task> eventTasksList = new ArrayList<Task>();

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> recurringTasksList = new ArrayList<Task>();

		for (int j = 0; j < allTasksList.size(); j++) {
			if (Checker.isDeadlineTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneDeadlineTaskInput(allTasksList.get(j).getScheduleString())) {
				deadlineTasksList.add(allTasksList.get(j));
			} else if (Checker.isEventTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneEventTaskInput(allTasksList.get(j).getScheduleString())) {
				eventTasksList.add(allTasksList.get(j));
			} else if (Checker.isFloatingTaskInput(allTasksList.get(j).getScheduleString())
					|| Checker.isDoneFloatingTaskInput(allTasksList.get(j).getScheduleString())) {
				floatingTasksList.add(allTasksList.get(j));
			} else if (Checker.isRecurringTaskInput(allTasksList.get(j).getScheduleString())) {
				recurringTasksList.add(allTasksList.get(j));
			}
		}

		// sort deadline tasks by ending time
		int size1 = deadlineTasksList.size();
		int a, start1, min_index1 = 0;

		for (start1 = 0; start1 < size1 - 1; start1++) {
			min_index1 = start1;

			for (a = start1 + 1; a < size1; a++) {
				if (deadlineTasksList.get(a).getDeadlineEndingTime() < deadlineTasksList.get(min_index1)
						.getDeadlineEndingTime()) {
					min_index1 = a;
				}
			}

			Task temp2 = deadlineTasksList.get(start1);
			Task temp3 = deadlineTasksList.get(min_index1);
			deadlineTasksList.set(start1, temp3);
			deadlineTasksList.set(min_index1, temp2);
		}

		// sort event tasks by starting time
		int size4 = eventTasksList.size();
		int b, start4, min_index4 = 0;

		for (start4 = 0; start4 < size4 - 1; start4++) {
			min_index4 = start4;

			for (b = start4 + 1; b < size4; b++) {
				if (eventTasksList.get(b).getEventStartingTime() < eventTasksList.get(min_index4)
						.getEventStartingTime()) {
					min_index4 = b;
				}
			}

			Task temp5 = eventTasksList.get(start4);
			Task temp6 = eventTasksList.get(min_index4);
			eventTasksList.set(start4, temp6);
			eventTasksList.set(min_index4, temp5);
		}

		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		// add the deadline tasks into deadlineOrEventTasksList
		for (int r = 0; r < deadlineTasksList.size(); r++) {
			deadlineOrEventTasksList.add(deadlineTasksList.get(r));
		}

		// then, add the deadline tasks into deadlineOrEventTasksList
		for (int s = 0; s < eventTasksList.size(); s++) {
			deadlineOrEventTasksList.add(eventTasksList.get(s));
		}

		// NOTE: Sorting by day, then month, then year, will keep the date(from
		// 1/1/1) in order

		// sort all deadline and event tasks by day

		int size10 = deadlineOrEventTasksList.size();
		int d, start10, min_index10 = 0;

		for (start10 = 0; start10 < size10 - 1; start10++) {
			min_index10 = start10;

			for (d = start10 + 1; d < size10; d++) {
				if (deadlineOrEventTasksList.get(d).getActualDay() < deadlineOrEventTasksList.get(min_index10)
						.getActualDay()) {
					min_index10 = d;
				}
			}

			Task temp11 = deadlineOrEventTasksList.get(start10);
			Task temp12 = deadlineOrEventTasksList.get(min_index10);
			deadlineOrEventTasksList.set(start10, temp12);
			deadlineOrEventTasksList.set(min_index10, temp11);
		}

		// sort all deadline and event tasks by their month

		int size13 = deadlineOrEventTasksList.size();
		int e, start13, min_index13 = 0;

		for (start13 = 0; start13 < size13 - 1; start13++) {
			min_index13 = start13;

			for (e = start13 + 1; e < size13; e++) {
				if (deadlineOrEventTasksList.get(e).getActualMonth() < deadlineOrEventTasksList.get(min_index13)
						.getActualMonth()) {
					min_index13 = e;
				}
			}

			Task temp14 = deadlineOrEventTasksList.get(start13);
			Task temp15 = deadlineOrEventTasksList.get(min_index13);
			deadlineOrEventTasksList.set(start13, temp15);
			deadlineOrEventTasksList.set(min_index13, temp14);
		}

		// sort all deadline and event tasks by their year

		int size16 = deadlineOrEventTasksList.size();
		int f, start16, min_index16 = 0;

		for (start16 = 0; start16 < size16 - 1; start16++) {
			min_index16 = start16;

			for (f = start16 + 1; f < size16; f++) {
				if (deadlineOrEventTasksList.get(f).getActualYear() < deadlineOrEventTasksList.get(min_index16)
						.getActualYear()) {
					min_index16 = f;
				}
			}

			Task temp17 = deadlineOrEventTasksList.get(start16);
			Task temp18 = deadlineOrEventTasksList.get(min_index16);
			deadlineOrEventTasksList.set(start16, temp18);
			deadlineOrEventTasksList.set(min_index16, temp17);
		}

		// sort floating tasks in alphabetical order
		int size22 = floatingTasksList.size();
		int h, start22, min_index22 = 0;

		for (start22 = 0; start22 < size22 - 1; start22++) {
			min_index22 = start22;

			for (h = start22 + 1; h < size22; h++) {
				if (floatingTasksList.get(h).getScheduleString()
						.compareToIgnoreCase(floatingTasksList.get(min_index22).getScheduleString()) < 0) {
					min_index22 = h;
				}
			}

			Task temp23 = floatingTasksList.get(start22);
			Task temp24 = floatingTasksList.get(min_index22);
			floatingTasksList.set(start22, temp24);
			floatingTasksList.set(min_index22, temp23);
		}

		// sort recurring tasks by day and starting time
		int size28 = recurringTasksList.size();
		int m, start28, min_index28 = 0;

		for (start28 = 0; start28 < size28 - 1; start28++) {
			min_index28 = start28;

			for (m = start28 + 1; m < size28; m++) {
				if (recurringTasksList.get(m).getRecurringTaskValue() < recurringTasksList.get(min_index28)
						.getRecurringTaskValue()) {
					min_index28 = m;
				}
			}

			Task temp29 = recurringTasksList.get(start28);
			Task temp30 = recurringTasksList.get(min_index28);
			recurringTasksList.set(start28, temp30);
			recurringTasksList.set(min_index28, temp29);
		}

		ArrayList<Task> tempTasksList = new ArrayList<Task>();

		if (!deadlineOrEventTasksList.isEmpty()) {
			for (int n = 0; n < deadlineOrEventTasksList.size(); n++) {
				tempTasksList.add(deadlineOrEventTasksList.get(n));
			}
		}

		if (!floatingTasksList.isEmpty()) {
			for (int o = 0; o < floatingTasksList.size(); o++) {
				tempTasksList.add(floatingTasksList.get(o));
			}
		}

		if (!recurringTasksList.isEmpty()) {
			for (int p = 0; p < recurringTasksList.size(); p++) {
				tempTasksList.add(recurringTasksList.get(p));
			}
		}

		for (int z = 0; z < allTasksList.size(); z++) {
			allTasksList.set(z, tempTasksList.get(z));
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
				if (Checker.isRecurringTaskInput(currentLine)) {
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