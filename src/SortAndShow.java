
//@@author A0124512W

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

	private static final int HOUR_MINUTES = 60;

	// the form of searching for tasks without executing readAndExecuteCommand()
	// recursively
	// related to the user input command String "show week"
	static void searchAndShowTask(String filename, String remainingCommandString) throws IOException {

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> recurringTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		int whitespaceIndex1 = remainingCommandString.indexOf(" ");

		if (whitespaceIndex1 < 0) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

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

				assert (!Checker.isValidDate(tempDate));

				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

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

				assert (!Checker.isValidDay(tempDay));

				FlexWindow.getTextArea().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getTextArea().appendText("\n");

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

				assert (!Checker.isValidTime(tempStartTime));

				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

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

				assert (!Checker.isValidTime(tempEndTime));

				FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
				FlexWindow.getFeedback().appendText("\n");

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
			// Only event tasks have a priority (level)

			for (int i = 0; i < allTasksList.size(); i++) {

				// event or deadline tasks have a priority (level)
				if (Checker.isEventTaskInput(allTasksList.get(i).getScheduleString())
						|| Checker.isDoneEventTaskInput(allTasksList.get(i).getScheduleString())) {
					if (allTasksList.get(i).getPriority().toLowerCase().indexOf(searchTerm.toLowerCase()) >= 0) {
						deadlineOrEventTasksList.add(allTasksList.get(i));
					}
				}

			}
		} else {
			// invalid input case
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return;
		}

		String tempDate = new String("");

		if (!deadlineOrEventTasksList.isEmpty()) {
			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");
			tempDate = deadlineOrEventTasksList.get(0).getDate();
			FlexWindow.getTextArea().appendText("Date: " + tempDate + "\n");
			FlexWindow.getTextArea().appendText("\n");
		}

		int deadlineOrEventTasksListCount = 0;

		for (int j = 0; j < deadlineOrEventTasksList.size(); j++) {
			deadlineOrEventTasksListCount += 1;
			if (deadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				FlexWindow.getTextArea().appendText(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			} else {
				deadlineOrEventTasksListCount = 1;
				FlexWindow.getTextArea().appendText(
						"---------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				FlexWindow.getTextArea().appendText("\n");

				tempDate = deadlineOrEventTasksList.get(j).getDate();

				FlexWindow.getTextArea().appendText("Date: " + tempDate + "\n");
				FlexWindow.getTextArea().appendText("\n");

				FlexWindow.getTextArea().appendText(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			}
		}
		if (!floatingTasksList.isEmpty()) {
			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			// floating header "Floating"
			FlexWindow.getTextArea().appendText("Floating" + "\n");
			FlexWindow.getTextArea().appendText("\n");

			int floatingTasksListCount = 0;
			for (int k = 0; k < floatingTasksList.size(); k++) {
				floatingTasksListCount += 1;
				FlexWindow.getTextArea()
						.appendText(floatingTasksListCount + ". " + floatingTasksList.get(k).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			}
		}

		if (!recurringTasksList.isEmpty()) {
			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			// recurring task header "Recurring"

			FlexWindow.getTextArea().appendText("Recurring" + "\n");
			FlexWindow.getTextArea().appendText("\n");

			int recurringTasksListCount = 0;

			for (int l = 0; l < recurringTasksList.size(); l++) {
				recurringTasksListCount += 1;
				FlexWindow.getTextArea().appendText(
						recurringTasksListCount + ". " + recurringTasksList.get(l).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			}
		}

	}

	// displays all files in the schedule by Task.java's comparisonValue
	// in order of starting and ending time
	// each date, followed by each date's deadline tasks, then event tasks are
	// displayed
	// followed by floating tasks after each date
	static String readAndDisplayAll(String filename) throws IOException {
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

		String tempDate = new String("");

		if (!deadlineOrEventTasksList.isEmpty()) {
			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");
			tempDate = deadlineOrEventTasksList.get(0).getDate();
			FlexWindow.getTextArea().appendText("Date: " + tempDate + "\n");
			FlexWindow.getTextArea().appendText("\n");
		}

		int deadlineOrEventTasksListCount = 0;

		for (int j = 0; j < deadlineOrEventTasksList.size(); j++) {
			deadlineOrEventTasksListCount += 1;
			if (deadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				FlexWindow.getTextArea().appendText(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			} else {
				deadlineOrEventTasksListCount = 1;
				FlexWindow.getTextArea().appendText(
						"---------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				FlexWindow.getTextArea().appendText("\n");

				tempDate = deadlineOrEventTasksList.get(j).getDate();

				FlexWindow.getTextArea().appendText("Date: " + tempDate + "\n");
				FlexWindow.getTextArea().appendText("\n");

				FlexWindow.getTextArea().appendText(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			}
		}
		if (!floatingTasksList.isEmpty()) {
			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			// floating header "Floating"

			FlexWindow.getTextArea().appendText("Floating" + "\n");
			FlexWindow.getTextArea().appendText("\n");

			int floatingTasksListCount = 0;
			for (int k = 0; k < floatingTasksList.size(); k++) {
				floatingTasksListCount += 1;
				FlexWindow.getTextArea()
						.appendText(floatingTasksListCount + ". " + floatingTasksList.get(k).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			}

		}

		// recurring task header "Recurring"
		if (!recurringTasksList.isEmpty()) {
			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");

			FlexWindow.getTextArea().appendText("Recurring" + "\n");
			FlexWindow.getTextArea().appendText("\n");

			int recurringTasksListCount = 0;

			for (int l = 0; l < recurringTasksList.size(); l++) {
				recurringTasksListCount += 1;
				FlexWindow.getTextArea().appendText(
						recurringTasksListCount + ". " + recurringTasksList.get(l).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			}
		}
		logger.finest(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println(ALL_TASKS_DISPLAYED_MESSAGE);
		System.out.println();

		FlexWindow.getTextArea().appendText("\n");

		return ALL_TASKS_DISPLAYED_MESSAGE;
	}

	// used to display tasks given an ArrayList of Tasks
	static void readAndDisplayArrayListTasks(ArrayList<Task> taskList) {

		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();
		ArrayList<Task> recurringTasksList = new ArrayList<Task>();
		ArrayList<Task> floatingTasksList = new ArrayList<Task>();

		for (int i = 0; i < taskList.size(); i++) {
			if (Checker.isDeadlineTaskInput(taskList.get(i).getScheduleString())
					|| Checker.isDoneDeadlineTaskInput(taskList.get(i).getScheduleString())
					|| Checker.isEventTaskInput(taskList.get(i).getScheduleString())
					|| Checker.isDoneEventTaskInput(taskList.get(i).getScheduleString())) {
				deadlineOrEventTasksList.add(taskList.get(i));
			} else if (Checker.isFloatingTaskInput(taskList.get(i).getScheduleString())
					|| Checker.isDoneFloatingTaskInput(taskList.get(i).getScheduleString())) {
				floatingTasksList.add(taskList.get(i));
			} else if (Checker.isRecurringTaskInput(taskList.get(i).getScheduleString())) {
				recurringTasksList.add(taskList.get(i));
			}
		}

		String tempDate = new String("");

		if (!deadlineOrEventTasksList.isEmpty()) {
			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");
			tempDate = deadlineOrEventTasksList.get(0).getDate();
			FlexWindow.getTextArea().appendText("Date: " + tempDate + "\n");
			FlexWindow.getTextArea().appendText("\n");
		}

		int deadlineOrEventTasksListCount = 0;

		for (int j = 0; j < deadlineOrEventTasksList.size(); j++) {
			deadlineOrEventTasksListCount += 1;
			if (deadlineOrEventTasksList.get(j).getDate().equalsIgnoreCase(tempDate)) {
				FlexWindow.getTextArea().appendText(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			} else {
				deadlineOrEventTasksListCount = 1;
				FlexWindow.getTextArea().appendText(
						"---------------------------------------------------------------------------------------------------------------------------"
								+ "\n");
				FlexWindow.getTextArea().appendText("\n");

				tempDate = deadlineOrEventTasksList.get(j).getDate();

				FlexWindow.getTextArea().appendText("Date: " + tempDate + "\n");
				FlexWindow.getTextArea().appendText("\n");

				FlexWindow.getTextArea().appendText(deadlineOrEventTasksListCount + ". "
						+ deadlineOrEventTasksList.get(j).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			}
		}
		if (!floatingTasksList.isEmpty()) {
			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			// floating header "Floating"

			FlexWindow.getTextArea().appendText("Floating" + "\n");
			FlexWindow.getTextArea().appendText("\n");

			int floatingTasksListCount = 0;
			for (int k = 0; k < floatingTasksList.size(); k++) {
				floatingTasksListCount += 1;
				FlexWindow.getTextArea()
						.appendText(floatingTasksListCount + ". " + floatingTasksList.get(k).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			}

		}

		// recurring task header "Recurring"
		if (!recurringTasksList.isEmpty()) {
			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");
			FlexWindow.getTextArea().appendText("\n");

			FlexWindow.getTextArea().appendText(
					"---------------------------------------------------------------------------------------------------------------------------"
							+ "\n");

			FlexWindow.getTextArea().appendText("Recurring" + "\n");
			FlexWindow.getTextArea().appendText("\n");

			int recurringTasksListCount = 0;

			for (int l = 0; l < recurringTasksList.size(); l++) {
				recurringTasksListCount += 1;
				FlexWindow.getTextArea().appendText(
						recurringTasksListCount + ". " + recurringTasksList.get(l).getDisplayString() + "\n");
				FlexWindow.getTextArea().appendText("\n");
			}
		}

		FlexWindow.getTextArea().appendText("\n");
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

		// sort all deadline tasks by day

		int size100 = deadlineTasksList.size();
		int jj, start100, min_index100 = 0;

		for (start100 = 0; start100 < size100 - 1; start100++) {
			min_index100 = start100;

			for (jj = start100 + 1; jj < size100; jj++) {
				if (deadlineTasksList.get(jj).getActualDay() < deadlineTasksList.get(min_index100).getActualDay()) {
					min_index100 = jj;
				}
			}

			Task temp101 = deadlineTasksList.get(start100);
			Task temp102 = deadlineTasksList.get(min_index100);
			deadlineTasksList.set(start100, temp102);
			deadlineTasksList.set(min_index100, temp101);
		}

		// sort all deadline tasks by their month

		int size103 = deadlineTasksList.size();
		int kk, start103, min_index103 = 0;

		for (start103 = 0; start103 < size103 - 1; start103++) {
			min_index103 = start103;

			for (kk = start103 + 1; kk < size103; kk++) {
				if (deadlineTasksList.get(kk).getActualMonth() < deadlineTasksList.get(min_index103).getActualMonth()) {
					min_index103 = kk;
				}
			}

			Task temp104 = deadlineTasksList.get(start103);
			Task temp105 = deadlineTasksList.get(min_index103);
			deadlineTasksList.set(start103, temp105);
			deadlineTasksList.set(min_index103, temp104);
		}

		// sort all deadline tasks by their year

		int size106 = deadlineTasksList.size();
		int ll, start106, min_index106 = 0;

		for (start106 = 0; start106 < size106 - 1; start106++) {
			min_index106 = start106;

			for (ll = start106 + 1; ll < size106; ll++) {
				if (deadlineTasksList.get(ll).getActualYear() < deadlineTasksList.get(min_index106).getActualYear()) {
					min_index106 = ll;
				}
			}

			Task temp107 = deadlineTasksList.get(start106);
			Task temp108 = deadlineTasksList.get(min_index106);
			deadlineTasksList.set(start106, temp108);
			deadlineTasksList.set(min_index106, temp107);
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

		// sort all event tasks by day

		int size109 = eventTasksList.size();
		int mm, start109, min_index109 = 0;

		for (start109 = 0; start109 < size109 - 1; start109++) {
			min_index109 = start109;

			for (mm = start109 + 1; mm < size109; mm++) {
				if (eventTasksList.get(mm).getActualDay() < eventTasksList.get(min_index109).getActualDay()) {
					min_index109 = mm;
				}
			}

			Task temp110 = eventTasksList.get(start109);
			Task temp111 = eventTasksList.get(min_index109);
			eventTasksList.set(start109, temp111);
			eventTasksList.set(min_index109, temp110);
		}

		// sort all event tasks by their month

		int size112 = eventTasksList.size();
		int nn, start112, min_index112 = 0;

		for (start112 = 0; start112 < size112 - 1; start112++) {
			min_index112 = start112;

			for (nn = start112 + 1; nn < size112; nn++) {
				if (eventTasksList.get(nn).getActualMonth() < eventTasksList.get(min_index112).getActualMonth()) {
					min_index112 = nn;
				}
			}

			Task temp113 = eventTasksList.get(start112);
			Task temp114 = eventTasksList.get(min_index112);
			eventTasksList.set(start112, temp114);
			eventTasksList.set(min_index112, temp113);
		}

		// sort all event tasks by their year

		int size115 = eventTasksList.size();
		int oo, start115, min_index115 = 0;

		for (start115 = 0; start115 < size115 - 1; start115++) {
			min_index115 = start115;

			for (oo = start115 + 1; oo < size115; oo++) {
				if (eventTasksList.get(oo).getActualYear() < eventTasksList.get(min_index115).getActualYear()) {
					min_index115 = oo;
				}
			}

			Task temp116 = eventTasksList.get(start115);
			Task temp117 = eventTasksList.get(min_index115);
			eventTasksList.set(start115, temp117);
			eventTasksList.set(min_index115, temp116);
		}

		ArrayList<Task> tempDeadlineOrEventTasksList = new ArrayList<Task>();

		// add the deadline tasks into deadlineOrEventTasksList
		for (int r = 0; r < deadlineTasksList.size(); r++) {
			tempDeadlineOrEventTasksList.add(deadlineTasksList.get(r));
		}

		// then, add the event tasks into deadlineOrEventTasksList
		for (int s = 0; s < eventTasksList.size(); s++) {
			tempDeadlineOrEventTasksList.add(eventTasksList.get(s));
		}

		// NOTE: Sorting by day, then month, then year, will keep the date(from
		// 1/1/1) in order

		// sort all deadline and event tasks by day

		int size10 = tempDeadlineOrEventTasksList.size();
		int d, start10, min_index10 = 0;

		for (start10 = 0; start10 < size10 - 1; start10++) {
			min_index10 = start10;

			for (d = start10 + 1; d < size10; d++) {
				if (tempDeadlineOrEventTasksList.get(d).getActualDay() < tempDeadlineOrEventTasksList.get(min_index10)
						.getActualDay()) {
					min_index10 = d;
				}
			}

			Task temp11 = tempDeadlineOrEventTasksList.get(start10);
			Task temp12 = tempDeadlineOrEventTasksList.get(min_index10);
			tempDeadlineOrEventTasksList.set(start10, temp12);
			tempDeadlineOrEventTasksList.set(min_index10, temp11);
		}

		// sort all deadline and event tasks by their month

		int size13 = tempDeadlineOrEventTasksList.size();
		int e, start13, min_index13 = 0;

		for (start13 = 0; start13 < size13 - 1; start13++) {
			min_index13 = start13;

			for (e = start13 + 1; e < size13; e++) {
				if (tempDeadlineOrEventTasksList.get(e).getActualMonth() < tempDeadlineOrEventTasksList.get(min_index13)
						.getActualMonth()) {
					min_index13 = e;
				}
			}

			Task temp14 = tempDeadlineOrEventTasksList.get(start13);
			Task temp15 = tempDeadlineOrEventTasksList.get(min_index13);
			tempDeadlineOrEventTasksList.set(start13, temp15);
			tempDeadlineOrEventTasksList.set(min_index13, temp14);
		}

		// sort all deadline and event tasks by their year

		int size16 = tempDeadlineOrEventTasksList.size();
		int f, start16, min_index16 = 0;

		for (start16 = 0; start16 < size16 - 1; start16++) {
			min_index16 = start16;

			for (f = start16 + 1; f < size16; f++) {
				if (tempDeadlineOrEventTasksList.get(f).getActualYear() < tempDeadlineOrEventTasksList.get(min_index16)
						.getActualYear()) {
					min_index16 = f;
				}
			}

			Task temp17 = tempDeadlineOrEventTasksList.get(start16);
			Task temp18 = tempDeadlineOrEventTasksList.get(min_index16);
			tempDeadlineOrEventTasksList.set(start16, temp18);
			tempDeadlineOrEventTasksList.set(min_index16, temp17);
		}

		// sort the deadline and event tasks for each YEAR
		// to make the date for each year in ORDER

		ArrayList<Task> dateSortedDeadlineOrEventTasksList = new ArrayList<Task>();

		if (!tempDeadlineOrEventTasksList.isEmpty()) {
			ArrayList<Task> subTaskListt = new ArrayList<Task>();

			int year = tempDeadlineOrEventTasksList.get(0).getActualYear();

			for (int tt = 0; tt < tempDeadlineOrEventTasksList.size(); tt++) {
				if (tempDeadlineOrEventTasksList.get(tt).getActualYear() == year) {
					subTaskListt.add(tempDeadlineOrEventTasksList.get(tt));
				} else {
					// sort subTaskList
					int size460 = subTaskListt.size();
					int aa, start460, min_index460 = 0;

					for (start460 = 0; start460 < size460 - 1; start460++) {
						min_index460 = start460;

						for (aa = start460 + 1; aa < size460; aa++) {
							if (subTaskListt.get(aa).getDayAndMonthValue() < subTaskListt.get(min_index460)
									.getDayAndMonthValue()) {
								min_index460 = aa;
							}
						}

						Task temp470 = subTaskListt.get(start460);
						Task temp480 = subTaskListt.get(min_index460);
						subTaskListt.set(start460, temp480);
						subTaskListt.set(min_index460, temp470);
					}

					for (int uu = 0; uu < subTaskListt.size(); uu++) {
						dateSortedDeadlineOrEventTasksList.add(subTaskListt.get(uu));
					}

					subTaskListt.clear();

					year = tempDeadlineOrEventTasksList.get(tt).getActualYear();

					subTaskListt.add(tempDeadlineOrEventTasksList.get(tt));

				}
			}

			if (!subTaskListt.isEmpty()) {
				int size490 = subTaskListt.size();
				int ddd, start490, min_index490 = 0;

				for (start490 = 0; start490 < size490 - 1; start490++) {
					min_index490 = start490;

					for (ddd = start490 + 1; ddd < size490; ddd++) {
						if (subTaskListt.get(ddd).getDayAndMonthValue() < subTaskListt.get(min_index490)
								.getDayAndMonthValue()) {
							min_index490 = ddd;
						}
					}

					Task temp500 = subTaskListt.get(start490);
					Task temp510 = subTaskListt.get(min_index490);
					subTaskListt.set(start490, temp510);
					subTaskListt.set(min_index490, temp500);
				}

				for (int bbb = 0; bbb < subTaskListt.size(); bbb++) {
					dateSortedDeadlineOrEventTasksList.add(subTaskListt.get(bbb));
				}

				subTaskListt.clear();
			}

		}

		// sort the deadline and event tasks for each date
		// such that for each date, deadline tasks (sorted by ending time)
		// are displayed first
		// followed by event tasks sorted by starting time
		// displayed after that,
		// PER DATE

		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		if (!dateSortedDeadlineOrEventTasksList.isEmpty()) {
			ArrayList<Task> subTaskList = new ArrayList<Task>();

			String date = dateSortedDeadlineOrEventTasksList.get(0).getDate();

			for (int t = 0; t < dateSortedDeadlineOrEventTasksList.size(); t++) {
				if (dateSortedDeadlineOrEventTasksList.get(t).getDate().equalsIgnoreCase(date)) {
					subTaskList.add(dateSortedDeadlineOrEventTasksList.get(t));
				} else {
					// sort subTaskList
					int size46 = subTaskList.size();
					int aa, start46, min_index46 = 0;

					for (start46 = 0; start46 < size46 - 1; start46++) {
						min_index46 = start46;

						for (aa = start46 + 1; aa < size46; aa++) {
							if (subTaskList.get(aa).getDeadlineOrEventTimeValue() < subTaskList.get(min_index46)
									.getDeadlineOrEventTimeValue()) {
								min_index46 = aa;
							}
						}

						Task temp47 = subTaskList.get(start46);
						Task temp48 = subTaskList.get(min_index46);
						subTaskList.set(start46, temp48);
						subTaskList.set(min_index46, temp47);
					}

					for (int u = 0; u < subTaskList.size(); u++) {
						deadlineOrEventTasksList.add(subTaskList.get(u));
					}

					subTaskList.clear();

					date = dateSortedDeadlineOrEventTasksList.get(t).getDate();

					subTaskList.add(dateSortedDeadlineOrEventTasksList.get(t));

				}
			}

			if (!subTaskList.isEmpty()) {
				int size49 = subTaskList.size();
				int dd, start49, min_index49 = 0;

				for (start49 = 0; start49 < size49 - 1; start49++) {
					min_index49 = start49;

					for (dd = start49 + 1; dd < size49; dd++) {
						if (subTaskList.get(dd).getDeadlineOrEventTimeValue() < subTaskList.get(min_index49)
								.getDeadlineOrEventTimeValue()) {
							min_index49 = dd;
						}
					}

					Task temp50 = subTaskList.get(start49);
					Task temp51 = subTaskList.get(min_index49);
					subTaskList.set(start49, temp51);
					subTaskList.set(min_index49, temp50);
				}

				for (int bb = 0; bb < subTaskList.size(); bb++) {
					deadlineOrEventTasksList.add(subTaskList.get(bb));
				}

				subTaskList.clear();
			}

		}

		// sort floating tasks in alphabetical order
		int size22 = floatingTasksList.size();
		int h, start22, min_index22 = 0;

		for (start22 = 0; start22 < size22 - 1; start22++) {
			min_index22 = start22;

			for (h = start22 + 1; h < size22; h++) {
				if (floatingTasksList.get(h).getTaskName()
						.compareToIgnoreCase(floatingTasksList.get(min_index22).getTaskName()) < 0) {
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

		for (int z = 0; z < tempTasksList.size(); z++) {
			allTasksList.set(z, tempTasksList.get(z));
		}
	}

	// shows event tasks in the schedule
	static void showEventTasks(String filename) throws IOException {

		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> eventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isEventTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
					eventTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(eventTasksList);

		SortAndShow.readAndDisplayArrayListTasks(eventTasksList);

	}

	// shows deadline tasks in the schedule
	static void showDeadlineTasks(String filename) throws IOException {
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

		SortAndShow.readAndDisplayArrayListTasks(deadlineTasksList);

	}

	// shows recurring tasks in the schedule
	static void showRecurringTasks(String filename) throws IOException {
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

		readAndDisplayArrayListTasks(recurringTasksList);

	}

	// shows floating tasks in the schedule
	static void showFloatingTasks(String filename) throws IOException {
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

		SortAndShow.readAndDisplayArrayListTasks(floatingTasksList);

	}

	// show tasks which are marked as done by the user
	static void showDoneTasks(String filename) throws IOException {
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

		ArrayList<Task> allTasksList = new ArrayList<Task>();

		for (int i = 0; i < doneDeadlineOrEventTasksList.size(); i++) {
			allTasksList.add(doneDeadlineOrEventTasksList.get(i));
		}

		for (int j = 0; j < doneFloatingTasksList.size(); j++) {
			allTasksList.add(doneFloatingTasksList.get(j));
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);

		SortAndShow.readAndDisplayArrayListTasks(allTasksList);

	}

	// show tasks which are not marked as done by the user
	static void showNotDoneTasks(String filename) throws IOException {
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

		ArrayList<Task> allTasksList = new ArrayList<Task>();

		for (int i = 0; i < deadlineOrEventTasksList.size(); i++) {
			allTasksList.add(deadlineOrEventTasksList.get(i));
		}

		for (int j = 0; j < floatingTasksList.size(); j++) {
			allTasksList.add(floatingTasksList.get(j));
		}

		for (int k = 0; k < recurringTasksList.size(); k++) {
			allTasksList.add(recurringTasksList.get(k));
		}

		SortAndShow.sortAllTasksByDateAndStartingTime(allTasksList);

		SortAndShow.readAndDisplayArrayListTasks(allTasksList);

	}

	// all tasks sorted by task name
	static void showByTaskName(String filename) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> floatingTasksList = new ArrayList<Task>();
		ArrayList<Task> recurringTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isFloatingTaskInput(currentLine) || Checker.isDoneFloatingTaskInput(currentLine)) {
					floatingTasksList.add(new Task(currentLine));
				} else if (Checker.isDeadlineTaskInput(currentLine) || Checker.isDoneDeadlineTaskInput(currentLine)
						|| Checker.isEventTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
					deadlineOrEventTasksList.add(new Task(currentLine));
				} else if (Checker.isRecurringTaskInput(currentLine) || Checker.isRecurringTaskInput(currentLine)) {
					recurringTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		ArrayList<Task> allTasksList = new ArrayList<Task>();

		// sort deadline tasks in alphabetical order
		int size11 = deadlineOrEventTasksList.size();
		int g, start11, min_index11 = 0;

		for (start11 = 0; start11 < size11 - 1; start11++) {
			min_index11 = start11;

			for (g = start11 + 1; g < size11; g++) {
				if (deadlineOrEventTasksList.get(g).getTaskName()
						.compareToIgnoreCase(deadlineOrEventTasksList.get(min_index11).getTaskName()) < 0) {
					min_index11 = g;
				}
			}

			Task temp12 = deadlineOrEventTasksList.get(start11);
			Task temp13 = deadlineOrEventTasksList.get(min_index11);
			deadlineOrEventTasksList.set(start11, temp13);
			deadlineOrEventTasksList.set(min_index11, temp12);
		}

		for (int i = 0; i < deadlineOrEventTasksList.size(); i++) {
			allTasksList.add(deadlineOrEventTasksList.get(i));
		}

		// sort floating tasks in alphabetical order
		int size22 = floatingTasksList.size();
		int h, start22, min_index22 = 0;

		for (start22 = 0; start22 < size22 - 1; start22++) {
			min_index22 = start22;

			for (h = start22 + 1; h < size22; h++) {
				if (floatingTasksList.get(h).getTaskName()
						.compareToIgnoreCase(floatingTasksList.get(min_index22).getTaskName()) < 0) {
					min_index22 = h;
				}
			}

			Task temp23 = floatingTasksList.get(start22);
			Task temp24 = floatingTasksList.get(min_index22);
			floatingTasksList.set(start22, temp24);
			floatingTasksList.set(min_index22, temp23);
		}

		for (int j = 0; j < floatingTasksList.size(); j++) {
			allTasksList.add(floatingTasksList.get(j));
		}

		// sort recurring tasks in alphabetical order
		int size33 = recurringTasksList.size();
		int j, start33, min_index33 = 0;

		for (start33 = 0; start33 < size33 - 1; start33++) {
			min_index33 = start33;

			for (j = start33 + 1; j < size33; j++) {
				if (recurringTasksList.get(j).getTaskName()
						.compareToIgnoreCase(recurringTasksList.get(min_index33).getTaskName()) < 0) {
					min_index33 = j;
				}
			}

			Task temp34 = recurringTasksList.get(start33);
			Task temp35 = recurringTasksList.get(min_index33);
			recurringTasksList.set(start33, temp35);
			recurringTasksList.set(min_index33, temp34);
		}

		for (int k = 0; k < recurringTasksList.size(); k++) {
			allTasksList.add(recurringTasksList.get(k));
		}

		SortAndShow.readAndDisplayArrayListTasks(allTasksList);
	}

	// show event, and recurring tasks by starting time
	static void showByTaskStartingTime(String filename) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> recurringTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isEventTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
					deadlineOrEventTasksList.add(new Task(currentLine));
				} else if (Checker.isRecurringTaskInput(currentLine) || Checker.isRecurringTaskInput(currentLine)) {
					recurringTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		ArrayList<Task> allTasksList = new ArrayList<Task>();

		// sort deadline tasks in alphabetical order
		int size11 = deadlineOrEventTasksList.size();
		int g, start11, min_index11 = 0;

		for (start11 = 0; start11 < size11 - 1; start11++) {
			min_index11 = start11;

			for (g = start11 + 1; g < size11; g++) {
				if (Integer.valueOf(deadlineOrEventTasksList.get(g).getStart().substring(0, 2)) * HOUR_MINUTES
						+ Integer.valueOf(deadlineOrEventTasksList.get(g).getStart().substring(2, 4)) < Integer.valueOf(
								deadlineOrEventTasksList.get(min_index11).getStart().substring(0, 2)) * HOUR_MINUTES
								+ Integer.valueOf(
										deadlineOrEventTasksList.get(min_index11).getStart().substring(2, 4))) {
					min_index11 = g;
				}
			}

			Task temp12 = deadlineOrEventTasksList.get(start11);
			Task temp13 = deadlineOrEventTasksList.get(min_index11);
			deadlineOrEventTasksList.set(start11, temp13);
			deadlineOrEventTasksList.set(min_index11, temp12);
		}

		for (int i = 0; i < deadlineOrEventTasksList.size(); i++) {
			allTasksList.add(deadlineOrEventTasksList.get(i));
		}

		// sort recurring tasks in alphabetical order
		int size33 = recurringTasksList.size();
		int j, start33, min_index33 = 0;

		for (start33 = 0; start33 < size33 - 1; start33++) {
			min_index33 = start33;

			for (j = start33 + 1; j < size33; j++) {
				if (Integer.valueOf(recurringTasksList.get(j).getStart().substring(0, 2)) * HOUR_MINUTES
						+ Integer.valueOf(recurringTasksList.get(j).getStart().substring(2, 4)) < Integer
								.valueOf(recurringTasksList.get(min_index33).getStart().substring(0, 2)) * HOUR_MINUTES
								+ Integer.valueOf(recurringTasksList.get(min_index33).getStart().substring(2, 4))) {
					min_index33 = j;
				}
			}

			Task temp34 = recurringTasksList.get(start33);
			Task temp35 = recurringTasksList.get(min_index33);
			recurringTasksList.set(start33, temp35);
			recurringTasksList.set(min_index33, temp34);
		}

		for (int k = 0; k < recurringTasksList.size(); k++) {
			allTasksList.add(recurringTasksList.get(k));
		}

		SortAndShow.readAndDisplayArrayListTasks(allTasksList);
	}

	// sorts deadline, event and recurring tasks by ending time
	static void showByTaskEndingTime(String filename) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> recurringTasksList = new ArrayList<Task>();
		ArrayList<Task> deadlineOrEventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isDeadlineTaskInput(currentLine) || Checker.isDoneDeadlineTaskInput(currentLine)
						|| Checker.isEventTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
					deadlineOrEventTasksList.add(new Task(currentLine));
				} else if (Checker.isRecurringTaskInput(currentLine) || Checker.isRecurringTaskInput(currentLine)) {
					recurringTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		ArrayList<Task> allTasksList = new ArrayList<Task>();

		// sort deadline tasks in alphabetical order
		int size11 = deadlineOrEventTasksList.size();
		int g, start11, min_index11 = 0;

		for (start11 = 0; start11 < size11 - 1; start11++) {
			min_index11 = start11;

			for (g = start11 + 1; g < size11; g++) {
				if (Integer.valueOf(deadlineOrEventTasksList.get(g).getEnd().substring(0, 2)) * HOUR_MINUTES
						+ Integer.valueOf(deadlineOrEventTasksList.get(g).getEnd().substring(2, 4)) < Integer.valueOf(
								deadlineOrEventTasksList.get(min_index11).getEnd().substring(0, 2)) * HOUR_MINUTES
								+ Integer.valueOf(deadlineOrEventTasksList.get(min_index11).getEnd().substring(2, 4))) {
					min_index11 = g;
				}
			}

			Task temp12 = deadlineOrEventTasksList.get(start11);
			Task temp13 = deadlineOrEventTasksList.get(min_index11);
			deadlineOrEventTasksList.set(start11, temp13);
			deadlineOrEventTasksList.set(min_index11, temp12);
		}

		for (int i = 0; i < deadlineOrEventTasksList.size(); i++) {
			allTasksList.add(deadlineOrEventTasksList.get(i));
		}

		// sort recurring tasks in alphabetical order
		int size33 = recurringTasksList.size();
		int j, start33, min_index33 = 0;

		for (start33 = 0; start33 < size33 - 1; start33++) {
			min_index33 = start33;

			for (j = start33 + 1; j < size33; j++) {
				if (Integer.valueOf(recurringTasksList.get(j).getEnd().substring(0, 2)) * HOUR_MINUTES
						+ Integer.valueOf(recurringTasksList.get(j).getEnd().substring(2, 4)) < Integer
								.valueOf(recurringTasksList.get(min_index33).getEnd().substring(0, 2)) * HOUR_MINUTES
								+ Integer.valueOf(recurringTasksList.get(min_index33).getEnd().substring(2, 4))) {
					min_index33 = j;
				}
			}

			Task temp34 = recurringTasksList.get(start33);
			Task temp35 = recurringTasksList.get(min_index33);
			recurringTasksList.set(start33, temp35);
			recurringTasksList.set(min_index33, temp34);
		}

		for (int k = 0; k < recurringTasksList.size(); k++) {
			allTasksList.add(recurringTasksList.get(k));
		}

		SortAndShow.readAndDisplayArrayListTasks(allTasksList);
	}

	// show event tasks by priority
	static void showByTaskPriority(String filename) throws IOException {
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;

		ArrayList<Task> eventTasksList = new ArrayList<Task>();

		do {
			currentLine = reader.readLine();
			if (currentLine != null) {
				if (Checker.isEventTaskInput(currentLine) || Checker.isDoneEventTaskInput(currentLine)) {
					eventTasksList.add(new Task(currentLine));
				}
			}
		} while (currentLine != null);

		if (reader != null) {
			reader.close();
		}

		ArrayList<Task> allTasksList = new ArrayList<Task>();

		// sort deadline tasks in alphabetical order
		int size11 = eventTasksList.size();
		int g, start11, min_index11 = 0;

		for (start11 = 0; start11 < size11 - 1; start11++) {
			min_index11 = start11;

			for (g = start11 + 1; g < size11; g++) {
				if (eventTasksList.get(g).getPriority()
						.compareToIgnoreCase(eventTasksList.get(min_index11).getPriority()) < 0) {
					min_index11 = g;
				}
			}

			Task temp12 = eventTasksList.get(start11);
			Task temp13 = eventTasksList.get(min_index11);
			eventTasksList.set(start11, temp13);
			eventTasksList.set(min_index11, temp12);
		}

		for (int i = 0; i < eventTasksList.size(); i++) {
			allTasksList.add(eventTasksList.get(i));
		}

		SortAndShow.readAndDisplayArrayListTasks(allTasksList);
	}
}
