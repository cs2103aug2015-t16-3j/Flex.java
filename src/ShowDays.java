import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.*;

public class ShowDays {

	private static final Logger logger = Logger.getLogger(ShowDays.class.getName());

	private static final String STARTING_DATE_REQUEST_MESSAGE = "Please enter the starting date (format: dd/mm/yyyy): "
			+ "\n";

	private static final String TASKS_FOR_WEEK_DISPLAYED_FRONT_MESSAGE = "The tasks for the whole week starting on ";
	private static final String TASKS_FOR_WEEK_DISLAYED_BACK_MESSAGE = " are displayed.";

	private static final String DATE_GENERATED_MESSAGE = "The starting date provided by the user for displaying the specified week's tasks is valid. The next valid date generated is ";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";

	// number of days in each month for non-leap years
	private static final int JANUARY_DAYS = 31;
	private static final int FEBRUARY_DAYS = 28;
	private static final int MARCH_DAYS = 31;
	private static final int APRIL_DAYS = 30;
	private static final int MAY_DAYS = 31;
	private static final int JUNE_DAYS = 30;
	private static final int JULY_DAYS = 31;
	private static final int AUGUST_DAYS = 31;
	private static final int SEPTEMBER_DAYS = 30;
	private static final int OCTOBER_DAYS = 31;
	private static final int NOVEMBER_DAYS = 30;
	private static final int DECEMBER_DAYS = 31;

	// used to show tasks which are in 7 consecutive days
	// starting from the date the user indicates
	static void showWeek(String filename, String date) throws IOException {

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

		FlexWindow.getFeedback().appendText(STARTING_DATE_REQUEST_MESSAGE +
		 "\n");
		FlexWindow.getFeedback().appendText("\n");

		logger.finest(STARTING_DATE_REQUEST_MESSAGE);
		System.out.println(STARTING_DATE_REQUEST_MESSAGE);
		System.out.println();

		// day 1

		String date1 = date.trim();

		// check if this input by the user is valid
		String tempDate = date1;

		// flexWindow.getFeedback().setText("");

		if (!Checker.isValidDate(tempDate)) {
			FlexWindow.getFeedback().appendText(INVALID_INPUT_MESSAGE + "\n");
			FlexWindow.getFeedback().appendText("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();

			return;
		}

		assert (Checker.isValidDate(tempDate));

		date1.trim();

		SortAndShow.searchAndShowTask(filename, "date " + date1);

		// day 2
		String date2 = generateNextDate(date1);

		date2.trim();

		SortAndShow.searchAndShowTask(filename, "date " + date2);

		// day 3
		String date3 = generateNextDate(date2);

		date3.trim();

		SortAndShow.searchAndShowTask(filename, "date " + date3);

		// day 4

		String date4 = generateNextDate(date3);

		date4.trim();

		SortAndShow.searchAndShowTask(filename, "date " + date4);

		// day 5
		String date5 = generateNextDate(date4);

		date5.trim();

		SortAndShow.searchAndShowTask(filename, "date " + date5);

		// day 6
		String date6 = generateNextDate(date5);

		date6.trim();

		SortAndShow.searchAndShowTask(filename, "date " + date6);

		// day 7
		String date7 = generateNextDate(date6);

		date7.trim();

		SortAndShow.searchAndShowTask(filename, "date " + date7);

		logger.finest(TASKS_FOR_WEEK_DISPLAYED_FRONT_MESSAGE + date1 + TASKS_FOR_WEEK_DISLAYED_BACK_MESSAGE);
		System.out.println(TASKS_FOR_WEEK_DISPLAYED_FRONT_MESSAGE + date1 + TASKS_FOR_WEEK_DISLAYED_BACK_MESSAGE);
		System.out.println();
	}

	// generates the next date, given the day, month and year of a date
	// assumed to be in the format dd/mm/yyyy
	static String generateNextDate(String date) {

		assert (Checker.isValidDate(date));

		String tempDate = date;

		// the three variables of the current date
		int slashIndex1 = tempDate.indexOf("/");
		int currentDay = Integer.valueOf(tempDate.substring(0, slashIndex1));
		tempDate = tempDate.substring(slashIndex1 + 1).trim();

		int slashIndex2 = tempDate.indexOf("/");
		int currentMonth = Integer.valueOf(tempDate.substring(0, slashIndex2));

		int currentYear = Integer.valueOf(tempDate.substring(slashIndex2 + 1));

		// the three variables of the next date
		int newDay = -1;
		int newMonth = -1;
		int newYear = -1;

		boolean isLeapYear = false;
		boolean isLastDayOfMonth = false;
		boolean isLastDayOfYear = false;

		if (currentYear % 4 == 0) {
			isLeapYear = true;
		}

		if (currentMonth == 1) {
			if (currentDay == JANUARY_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 2) {
			// if it is a leap year, the last day of February is 29th of
			// February for that year
			// Note: FEBRUARY_DAYS = 28;
			if (isLeapYear) {
				if (currentDay == (FEBRUARY_DAYS + 1)) {
					isLastDayOfMonth = true;
				}
			} else {
				if (currentDay == (FEBRUARY_DAYS)) {
					isLastDayOfMonth = true;
				}
			}
		} else if (currentMonth == 3) {
			if (currentDay == MARCH_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 4) {
			if (currentDay == APRIL_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 5) {
			if (currentDay == MAY_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 6) {
			if (currentDay == JUNE_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 7) {
			if (currentDay == JULY_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 8) {
			if (currentDay == AUGUST_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 9) {
			if (currentDay == SEPTEMBER_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 10) {
			if (currentDay == OCTOBER_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 11) {
			if (currentDay == NOVEMBER_DAYS) {
				isLastDayOfMonth = true;
			}
		} else if (currentMonth == 12) {
			if (currentDay == DECEMBER_DAYS) {
				isLastDayOfMonth = true;
				isLastDayOfYear = true;
			}
		}

		// Case 1: if the date given is the last day of the year, that is
		// 31st December of that year
		if (isLastDayOfYear) {
			newDay = 1;
			newMonth = 1;
			newYear = currentYear + 1;
		}
		// Case 2: The given date is not the last day of the year,
		// but it is the last day of the month
		else if ((!isLastDayOfYear) && (isLastDayOfMonth)) {
			newDay = 1;
			newMonth = currentMonth + 1;
			newYear = currentYear;
		}
		// Case 3: The given date is not the last day of the year,
		// and also not the last day of the month
		else if ((!isLastDayOfYear) && (!isLastDayOfMonth)) {
			newDay = currentDay + 1;
			newMonth = currentMonth;
			newYear = currentYear;
		}

		logger.finest(DATE_GENERATED_MESSAGE + newDay + "/" + newMonth + "/" + newYear);

		return newDay + "/" + newMonth + "/" + newYear;

	}

}
