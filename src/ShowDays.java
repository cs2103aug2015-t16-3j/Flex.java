import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.logging.*;

public class ShowDays {

	private static final Logger logger = Logger.getLogger(Flex.class.getName());

	private static FlexWindow flexWindow;
	private static Scanner sc;
	private static String filename = new String("");

	private static final String FIRST_COMMA_SPACE_MISSING_MESSAGE = "First comma and space are missing.";
	private static final String SECOND_COMMA_SPACE_MISSING_MESSAGE = "Second comma and space are missing.";
	private static final String THIRD_COMMA_SPACE_MISSING_MESSAGE = "Third comma and space are missing.";
	private static final String FOURTH_COMMA_SPACE_MISSING_MESSAGE = "Fourth comma and space are missing.";
	private static final String FIFTH_COMMA_SPACE_MISSING_MESSAGE = "Fifth comma and space are missing.";
	private static final String SIXTH_COMMA_SPACE_MISSING_MESSAGE = "Sixth comma and space are missing.";

	private static final String DATE_FIRST_SLASH_MISSING_MESSAGE = "First slash of date is missing.";
	private static final String DATE_SECOND_SLASH_MISSING_MESSAGE = "Second slash of date is missing.";

	private static final String DASH_IN_DAY_OF_DATE_MESSAGE = "At least one dash is in the date's day. Do take note that negative numbers are not allowed as well.";
	private static final String DAY_IN_DATE_MISSING_MESSAGE = "The day in the date is missing.";
	private static final String DAY_IN_DATE_NOT_A_NUMBER_MESSAGE = "The day in the date is not a number.";
	private static final String DAY_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE = "The day in the date should not have more than two digits.";
	private static final String DAY_IN_DATE_MORE_THAN_THIRTY_ONE_MESSAGE = "The day in the date is more than 31.";
	private static final String DAY_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE = "The day in the date is 0 or less than zero.";

	private static String DASH_IN_MONTH_OF_DATE_MESSAGE = "At least one dash is in the date's month. Do take note that negative numbers are not allowed as well.";
	private static String MONTH_IN_DATE_MISSING_MESSAGE = "The month in the date is missing.";
	private static String MONTH_IN_DATE_NOT_A_NUMBER_MESSAGE = "The month in the date is not a number.";
	private static String MONTH_IN_DATE_MORE_THAN_TWO_DIGITS_MESSAGE = "The month in the date should not have more than two digits.";
	private static String MONTH_IN_DATE_MORE_THAN_TWELVE_MESSAGE = "The day in the date is more than 12.";
	private static String MONTH_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE = "The month in the date is 0 or less than zero.";

	private static final String DASH_IN_YEAR_OF_DATE_MESSAGE = "At least one dash is in the date's year. Do take note that negative numbers are not allowed as well.";
	private static final String YEAR_IN_DATE_MISSING_MESSAGE = "The year in the date is missing.";
	private static final String YEAR_IN_DATE_NOT_A_NUMBER_MESSAGE = "The year in the date is not a number.";
	private static final String YEAR_IN_DATE_IS_ZERO_OR_LESS_THAN_ZERO_MESSAGE = "The year in the date is 0 or less than zero.";

	private static final String STARTING_TIME_MISSING_MESSAGE = "The starting time is missing. Do take note that the starting time follows the 4-digit twenty-four-hour format.";
	private static final String DASH_IN_STARTING_TIME_MESSAGE = "At least one dash is in the starting time. Do take note that negative numbers are not allowed as well. Also, do take note that the starting time follows the 4-digit twenty-four-hour format.";		
	private static final String STARTING_TIME_NOT_A_NUMBER_MESSAGE = "The starting time is not a number.";
	private static final String STARTING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE = "The starting time is not a 4-digit number. Do take note that the starting time follows the 4-digit twenty-four-hour format.";
	private static final String STARTING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE = "The starting time is a number which is greater than 2359 (11:59pm).";
	private static final String STARTING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE = "The hours of the starting time is greater than 23.";
	private static final String STARTING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE = "The minutes of the starting time is greater than 59.";
	private static final String STARTING_TIME_LATER_THAN_ENDING_TIME_MESSAGE = "The new starting time is later than the current ending time.";

	private static final String ENDING_TIME_MISSING_MESSAGE = "The ending time is missing. Do take note that the ending time follows the 4-digit twenty-four-hour format.";
	private static final String DASH_IN_ENDING_TIME_MESSAGE = "At least one dash is in the ending time. Do take note that negative numbers are not allowed as well. Also, do take note that the ending time follows the 4-digit twenty-four-hour format.";	
	private static final String ENDING_TIME_NOT_A_NUMBER_MESSAGE = "The ending time is not a number.";
	private static final String ENDING_TIME_IS_A_NUMBER_BUT_NOT_A_4_DIGIT_NUMBER_MESSAGE = "The ending time is not a 4-digit number. Do take note that the ending time follows the 4-digit twenty-four-hour format.";
	private static final String ENDING_TIME_IS_A_NUMBER_GREATER_THAN_TWO_THREE_FIVE_NINE_MESSAGE = "The ending time is a number which is greater than 2359 (11:59pm).";
	private static final String ENDING_TIME_HOURS_GREATER_THAN_TWENTY_THREE_MESSAGE = "The hours of the ending time is greater than 23.";
	private static final String ENDING_TIME_MINUTES_GREATER_THAN_FIFTY_NINE_MESSAGE = "The minutes of the ending time is greater than 59.";
	private static final String ENDING_TIME_EARLIER_THAN_STARTING_TIME_MESSAGE = "The new ending time is earlier than the curent starting time.";


	private static final String NUMBER_OF_DAYS_MORE_THAN_EXPECTED_MESSAGE = "The number of days, according to the month and year in this date, more than expected.";	

	private static final String DISPLAY_SORTED_BY_STARTING_TIMES_MESSAGE = "The tasks, sorted by starting time, are displayed.";
	private static final String DISPLAY_SORTED_BY_ENDING_TIMES_MESSAGE = "The tasks, sorted by ending time, are displayed.";
	private static final String DISPLAY_SORTED_BY_TITLES_MESSAGE = "The tasks, sorted in alphabetical order by title, are displayed.";
	private static final String DISPLAY_SORTED_BY_DESCRIPTIONS_MESSAGE = "The tasks, sorted in alphabetical order by task description, are displayed.";
	private static final String DISPLAY_SORTED_BY_PRIORITY_LEVELS_MESSAGE = "The tasks, sorted in alphabetical order by priority level, is displayed.";
	private static final String DISPLAY_SORTED_BY_CATEGORIES_MESSAGE = "The tasks, sorted in alphabetical order by category, are displayed.";

	private static final String NOTHING_TO_UNDO_MESSAGE = "Nothing to undo as no valid 1) adding of a task, 2) deleting of a task, OR 3) Changing a task variable, has been carried out by the user during this program run.";
	private static final String DELETED_MESSAGE = "The specified task has been deleted.";
	private static String ADDED_MESSAGE = "The task has been successfully added.";

	private static final String VALID_INPUT_WITHOUT_MATCHING_TASKS_TO_HAVE_INFORMATION_CHANGED_MESSAGE = "Valid input provided, but there are no matching tasks to have their information changed.";

	private static final String STARTING_DATE_REQUEST_MESSAGE = "Please enter the starting date (format: dd/mm/yyyy): " + "\n";

	private static final String DONE_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are done, are displayed.";
	private static final String PENDING_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are pending, are displayed.";
	private static final String BLOCKED_TASKS_DISPLAYED_MESSAGE = "The tasks in the schedule, which are blocked, are displayed.";
	private static final String TASKS_NOT_DONE_DISPLAYED_MESSAGE = "The tasks which have not been marked as done are displayed.";
	private static final String ALL_TASKS_DISPLAYED_MESSAGE = "All the tasks in the schedule are displayed.";
	private static final String TASKS_FOR_WEEK_DISPLAYED_FRONT_MESSAGE = "The tasks for the whole week starting on ";
	private static final String TASKS_FOR_WEEK_DISLAYED_BACK_MESSAGE = " are displayed.";

	private static final String DATE_GENERATED_MESSAGE = "The starting date provided by the user for displaying the specified week's tasks is valid. The next valid date generated is ";
	private static final String CHANGED_MESSAGE = "The change to the task information is valid and processed.";
	private static final String CHANGE_UNDONE_MESSAGE= "The last valid change action has been undone.";	
	private static final String DELETE_UNDONE_MESSAGE = "The last valid delete action has been undone.";
	private static final String ADD_UNDONE_MESSAGE = "The last valid add action has been undone.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please try again.";
	// that is, it is valid only if its starting time, or ending time, are NOT between the starting
	// and ending times of existing tasks which are NOT DONE YET
	private static final String NO_SEARCH_RESULTS_MESSSAGE = "Valid input, but with no search results."; 
	private static final String TASK_DOES_NOT_EXIST_MESSAGE = "Task does not exist, so no such task can be deleted.";
	private static final String EXIT_MESSAGE = "Exiting the program.";
	private static final String BLOCKED_MESSAGE = "Unable to add the new task, because the new task clashes with existing tasks (on the same date) which have not been marked as tasks which have been done.";
	private static final String FILENAME_ACCEPTED_MESSAGE = "The filename is accepted.";
	private static final String PROCEED_MESSAGE = "Please proceed with the user input commands.";
	private static final String FILENAME_INPUT_MESSAGE = "Please enter the full path name of the .txt schedule file, including its name. For example: C:" + "\\" + "Users" + "\\" + "Owner" + "\\" + "Documents" + "\\" + "Flex" + "." + "java" + "\\" + "src" + "\\" + "FlexTest" + "." + "txt";
	private static final int HOUR_MINUTES = 60;

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
	static void showWeek(String filename, String previousChangeTerm, String previousAction, Task previousTask, FlexWindow flexWindow) throws IOException {
		sc = new Scanner(System.in);
		
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(filename));
		String currentLine = null;
	
		ArrayList<Task> allTasksList = new ArrayList<Task>();
		
		do{
			currentLine = reader.readLine();
			if(currentLine!=null){
				
				allTasksList.add(new Task(currentLine));				
			}
		}while(currentLine!=null);			
		
		if(reader!=null){
			reader.close();
		}				
		
		flexWindow.getTextArea().append(STARTING_DATE_REQUEST_MESSAGE + "\n");
		flexWindow.getTextArea().append("\n");

		logger.finest(STARTING_DATE_REQUEST_MESSAGE);
		System.out.println(STARTING_DATE_REQUEST_MESSAGE);
		System.out.println();
		
		// day 1
		
		String date1 = sc.nextLine();
		
		// check if this input by the user is valid
		String tempDate = date1;
		
		flexWindow.getTextArea().setText("");
				
		if(!Checker.checkDate(tempDate, flexWindow)){
			flexWindow.getTextArea().append(INVALID_INPUT_MESSAGE + "\n");	
			flexWindow.getTextArea().append("\n");

			logger.finest(INVALID_INPUT_MESSAGE);
			System.out.println(INVALID_INPUT_MESSAGE);
			System.out.println();
			
			Flex.readAndExecuteCommand(filename, previousChangeTerm, previousAction, previousTask, flexWindow);
		}
		
		assert(Checker.checkDate(tempDate, flexWindow));
		
		date1.trim();
		
		SortAndShow.searchAndShowTask(filename, "date " + date1,  flexWindow);
		
		// day 2
		String date2 = generateNextDate(date1);
		
		date2.trim();
		
		SortAndShow.searchAndShowTask(filename, "date " + date2, flexWindow);
		
		// day 3
		String date3 = generateNextDate(date2);
		
		date3.trim();
		
		SortAndShow.searchAndShowTask(filename, "date " + date3, flexWindow);
		
		// day 4
		
		String date4 = generateNextDate(date3);
		
		date4.trim();
		
		SortAndShow.searchAndShowTask(filename, "date " + date4, flexWindow);
		
		// day 5
		String date5 = generateNextDate(date4);
		
		date5.trim();
		
		SortAndShow.searchAndShowTask(filename, "date " + date5, flexWindow);
		
		// day 6
		String date6 = generateNextDate(date5);
		
		date6.trim();
		
		SortAndShow.searchAndShowTask(filename, "date " + date6, flexWindow);
		
		
		// day 7
		String date7 = generateNextDate(date6);
		
		date7.trim();
		
		SortAndShow.searchAndShowTask(filename, "date " + date7, flexWindow);

		logger.finest(TASKS_FOR_WEEK_DISPLAYED_FRONT_MESSAGE + date1 + TASKS_FOR_WEEK_DISLAYED_BACK_MESSAGE);
		System.out.println(TASKS_FOR_WEEK_DISPLAYED_FRONT_MESSAGE + date1 + TASKS_FOR_WEEK_DISLAYED_BACK_MESSAGE);
		System.out.println();
	}

	// generates the next date, given the day, month and year of a date
	// assumed to be in the format dd/mm/yyyy
	static String generateNextDate(String date) {
				
		assert(Checker.checkDate(date, flexWindow));
				
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

		if(currentYear%4==0){
			isLeapYear = true;
		}
		
		if(currentMonth==1){
			if(currentDay==JANUARY_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==2){
			// if it is a leap year, the last day of February is 29th of February for that year
			// Note: FEBRUARY_DAYS = 28;
			if(isLeapYear){
				if( currentDay == (FEBRUARY_DAYS + 1) ){
					isLastDayOfMonth = true;
				}
			}
			else{
				if(currentDay == (FEBRUARY_DAYS) ){
					isLastDayOfMonth = true;
				}
			}
		}
		else if(currentMonth==3){
			if(currentDay==MARCH_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==4){
			if(currentDay==APRIL_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==5){
			if(currentDay==MAY_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==6){
			if(currentDay==JUNE_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==7){
			if(currentDay==JULY_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==8){
			if(currentDay==AUGUST_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==9){
			if(currentDay==SEPTEMBER_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==10){
			if(currentDay==OCTOBER_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==11){
			if(currentDay==NOVEMBER_DAYS){
				isLastDayOfMonth = true;
			}
		}
		else if(currentMonth==12){
			if(currentDay==DECEMBER_DAYS){
				isLastDayOfMonth = true;
				isLastDayOfYear = true;
			}
		}
				
		// Case 1: if the date given is the last day of the year, that is
		// 31st December of that year
		if(isLastDayOfYear){
			newDay = 1;
			newMonth = 1;
			newYear = currentYear + 1;
		}
		// Case 2: The given date is not the last day of the year,
		// but it is the last day of the month
		else if((!isLastDayOfYear)&&(isLastDayOfMonth)){
			newDay = 1;
			newMonth = currentMonth + 1; 
			newYear = currentYear;
		}
		// Case 3: The given date is not the last day of the year,
		// and also not the last day of the month
		else if((!isLastDayOfYear)&&(!isLastDayOfMonth)){
			newDay = currentDay + 1;
			newMonth = currentMonth;
			newYear = currentYear;
		}

		logger.finest(DATE_GENERATED_MESSAGE + newDay +  "/" + newMonth + "/" + newYear);
			
		return newDay +  "/" + newMonth + "/" + newYear;
			
	}		

}
