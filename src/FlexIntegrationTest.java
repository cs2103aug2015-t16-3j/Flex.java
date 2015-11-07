//@@author A0124512W

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexIntegrationTest.java
// tests the functionality of some of the main methods in the program
// using unit-testing

// The .txt file under test
// is called FlexIntegrationTestDocument.txt

public class FlexIntegrationTest {

	private static final String ALL_TASKS_DISPLAYED_MESSAGE = "All the tasks in the schedule are displayed.";
	private static final String NOTHING_TO_UNDO_MESSAGE = "Nothing to undo as no valid previous command.";
	private static final String DELETED_MESSAGE = "The specified task has been deleted.";
	private static final String CHANGED_MESSAGE = "(The change to the task information is valid and processed.)";
	private static final String CHANGE_UNDONE_MESSAGE = "The last valid change action has been undone.";
	private static final String DELETE_UNDONE_MESSAGE = "The last valid delete action has been undone.";
	private static final String ADD_UNDONE_MESSAGE = "The last valid add action has been undone.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid command. Please try again.";
	private static final String MARKED_DONE_OR_NOT_DONE_MESSAGE = "The task has been marked as done/not done.";
	private static final String TASK_DOES_NOT_EXIST_MESSAGE = "Task does not exist.";
	private static final String BLOCKED_MESSAGE = "Unable to add the new event task, because of clashes with existing event tasks.";
	
	@Test
	public void test() throws IOException {
		String filename = "FlexIntegrationTestDocument.txt";
	
		String[] args = new String[100];
		
		FlexWindow.main(args);
		
		LastAction lastAction = new LastAction();

		String remainingCommandString = new String("");

		remainingCommandString = "change floating 1 taskname to Go to school on every weekday";

		// CRUD.undo() - undoing nothing

		assertEquals("undoing nothing", NOTHING_TO_UNDO_MESSAGE, CRUD.undo(filename, lastAction));

		assertEquals("undoing nothing", NOTHING_TO_UNDO_MESSAGE, CRUD.undo(filename, lastAction));

		// CRUD.changeTaskVariable();
		assertEquals("changing a task", INVALID_INPUT_MESSAGE,
				CRUD.changeTaskVariable(filename, remainingCommandString, lastAction));

		remainingCommandString = "floating 1 taskname to Go to school on every weekday";

		assertEquals("changing a task", CHANGED_MESSAGE,
				CRUD.changeTaskVariable(filename, remainingCommandString, lastAction));

		// CRUD.deleteTask();
		remainingCommandString = "floating ";

		assertEquals("deleting a task", INVALID_INPUT_MESSAGE,
				CRUD.deleteTask(filename, remainingCommandString, lastAction));
		
		remainingCommandString = "floating 8";

		assertEquals("deleting a task", TASK_DOES_NOT_EXIST_MESSAGE,
				CRUD.deleteTask(filename, remainingCommandString, lastAction));
		
		remainingCommandString = "26/10/2015 2";

		assertEquals("deleting a task", TASK_DOES_NOT_EXIST_MESSAGE,
				CRUD.deleteTask(filename, remainingCommandString, lastAction));
		
		remainingCommandString = "rec 6";

		assertEquals("deleting a task", TASK_DOES_NOT_EXIST_MESSAGE,
				CRUD.deleteTask(filename, remainingCommandString, lastAction));

		remainingCommandString = "floating 1";

		assertEquals("deleting a task", DELETED_MESSAGE,
				CRUD.deleteTask(filename, remainingCommandString, lastAction));

		// CRUD.addTask();

		remainingCommandString = "Go to school on weekdays;";

		assertEquals("adding a task", INVALID_INPUT_MESSAGE,
				CRUD.addTask(filename, remainingCommandString, lastAction));

		// 1100-1300 on 26/10/2015 (event task) exists
		
		// currently, if the task to be added is a (done or not done) event task, 
		// it is allowed to be added, if both its starting time and ending time is the same as
		// an existing (done or not done event task), i.e. multi-tasking
		// OR
		// the task to be added ends at or before the starting time of existing event tasks
		// OR
		// the task to be added starts at or after the ending time of existing event tasks
		
		remainingCommandString = "Meeting with CS2103/T group member(s); 1101-1300 on 26/10/2015; important;";

		assertEquals("adding a task", BLOCKED_MESSAGE,
				CRUD.addTask(filename, remainingCommandString, lastAction));
		
		remainingCommandString = "Meeting with CS2103/T group member(s); 1100-1259 on 26/10/2015; important;";

		assertEquals("adding a task", BLOCKED_MESSAGE,
				CRUD.addTask(filename, remainingCommandString, lastAction));	
		
		remainingCommandString = "Go to school on weekdays";

		assertEquals("adding a task", "The task " + "\"" + remainingCommandString + "\"" + " has been added." + "\n",
				CRUD.addTask(filename, remainingCommandString, lastAction));

		// CRUD.undo() - undoing something

		assertEquals("undoing something", ADD_UNDONE_MESSAGE, CRUD.undo(filename, lastAction));

		assertEquals("undoing something", DELETE_UNDONE_MESSAGE, CRUD.undo(filename, lastAction));
		
		// CRUD.markAsDone();
		
		remainingCommandString = "floating 1 not done";
		
		assertEquals("mark a task as not done, when it is already not done", INVALID_INPUT_MESSAGE, CRUD.markAsDone(filename, remainingCommandString, lastAction));
		
		remainingCommandString = "floating 1 done";
		
		assertEquals("mark a task as done, after it is done", MARKED_DONE_OR_NOT_DONE_MESSAGE, CRUD.markAsDone(filename, remainingCommandString, lastAction));
		
		remainingCommandString = "floating 1 done";
		
		assertEquals("mark a task as done, after it was already done", INVALID_INPUT_MESSAGE, CRUD.markAsDone(filename, remainingCommandString, lastAction));
		
		// CRUD.undo()
		
		assertEquals("undo the marking of a task as done", CHANGE_UNDONE_MESSAGE, CRUD.undo(filename, lastAction));

		// SortAndShow.readAndDisplayAll()

		assertEquals("show all tasks successfully i.e. reach the end of the method", ALL_TASKS_DISPLAYED_MESSAGE,
				SortAndShow.readAndDisplayAll(filename));

	}

}
