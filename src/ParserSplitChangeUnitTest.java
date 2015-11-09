//@@author A0131830U
import static org.junit.Assert.*;

import org.junit.Test;

public class ParserSplitChangeUnitTest {
    Command changeNormaltasknameCommand = ParserSplit.parse("change 01/01/2015 1 taskname to ttt");
    Command changeNormalDateCommand = ParserSplit.parse("change 01/01/2015 1 date to 02/01/2015");
    Command changeNormalPriorityCommand = ParserSplit.parse("change 01/01/2015 1 priority to 1");
    Command changeNormalTimeCommand = ParserSplit.parse("change 01/01/2015 1 time to 1000-1100");
    Command changeDeadlineEndtimeCommand = ParserSplit.parse("change 01/01/2015 1 end by 1200");
    Command changeFloatingTasknameCommand = ParserSplit.parse("change floating 1 taskname to ttt");
    Command changeRecurringTasknameCommand = ParserSplit.parse("change rec 1 taskname to ttt");
    Command changeRecurringDayCommand = ParserSplit.parse("change rec 1 to every tuesday");
    Command changeRecurringTime = ParserSplit.parse("change rec 1 time to 1000-1100");
    Command markNormaltaskDone = ParserSplit.parse("mark 01/01/2015 1 done");
    Command markNormalTaskNotDone = ParserSplit.parse("mark 01/01/2015 1 not done");
    Command markFloatingTaskDone = ParserSplit.parse("mark floating 1 done");
    Command markFloatingTaskNotDone = ParserSplit.parse("mark floating 1 not done");
    
    //check for change of the normal task taskname
	@Test
	public void changeNormalTasknameCommand() {
		assertEquals("01/01/2015", changeNormaltasknameCommand.getDate());
		assertEquals(1, changeNormaltasknameCommand.getNumber());
		assertEquals("ttt", changeNormaltasknameCommand.getChangedTaskName());
	}
	
	//check for change of the normal task date
    public void changeNormalDateCommand(){
		assertEquals("01/01/2015", changeNormaltasknameCommand.getDate());
		assertEquals(1, changeNormaltasknameCommand.getNumber());
		assertEquals("02/01/2015", changeNormaltasknameCommand.getChangedDate());
	}
	
    //check for change of the normal task priority
	public void changeNormalPriorityCommand(){
		assertEquals("01/01/2015", changeNormalPriorityCommand.getDate());
		assertEquals(1, changeNormalPriorityCommand.getNumber());
		assertEquals("2",changeNormalPriorityCommand.getChangedPriority());
		
	}
	
	//check for change of the normal task time
	public void changeNormalTimeCommand(){
		assertEquals("01/01/2015", changeNormalTimeCommand.getDate());
		assertEquals(1, changeNormalTimeCommand.getNumber());
		assertEquals("1000", changeNormalTimeCommand.getChangedStartTime());
		assertEquals("1100", changeNormalTimeCommand.getChangedEndTime());
	}
    
	//check for change of the deadline endtime
	public void changeDeadLineEndTimeCommand(){
		assertEquals("01/01/2015", changeDeadlineEndtimeCommand.getDate());
		assertEquals(1, changeDeadlineEndtimeCommand.getNumber());
		assertEquals("1200", changeDeadlineEndtimeCommand.getEndTime());
	}
	
	//check for change of the floating taskname
	public void changeFloatingTasknameCommand(){
		assertEquals(1, changeFloatingTasknameCommand.getNumber());
		assertEquals("ttt", changeFloatingTasknameCommand.getChangedTaskName());
	}
	
	//check for change of the recurring taskname
	public void changeRecurringTasknameCommand(){
		assertEquals(1, changeRecurringTasknameCommand.getNumber());
		assertEquals("ttt", changeRecurringTasknameCommand.getChangedTaskName());
	}
	
	//check for change of the recurring day
	public void changeRecurringDayCommand(){
		assertEquals(1, changeRecurringDayCommand.getNumber());
		assertEquals("tuesday", changeRecurringDayCommand.getChangedDay());
	}
	
	//check for change of the recurring time
	public void changeRecurringTimeCommand(){
		assertEquals(1, changeRecurringTime.getNumber());
		assertEquals("1000", changeRecurringTime.getChangedStartTime());
		assertEquals("1100", changeRecurringTime.getChangedEndTime());
	}
	
	//check for mark normal task as done
	public void markNormalTaskDone(){
		assertEquals("01/01/2015", markNormaltaskDone.getDate());
		assertEquals(1, markNormaltaskDone.getNumber());
		assertEquals("done", markNormaltaskDone.getStatus());
	}
	
	//check for mark normal task as not done
	public void markNormalTaskNotDone(){
		assertEquals("01/01/2015", markNormalTaskNotDone.getDate());
		assertEquals(1, markNormalTaskNotDone.getNumber());
		assertEquals("not done", markNormalTaskNotDone.getStatus());
	}
	
	//check for mark floating task as done
    public void markFloatingTaskDone(){
    	assertEquals(1, markFloatingTaskDone.getNumber());
    	assertEquals("done", markFloatingTaskDone.getStatus());
    }
    
    //check for mark floating task as not done
    public void markFloatingTaskNotDone(){
    	assertEquals(1,markFloatingTaskNotDone.getNumber());
    	assertEquals("not done", markFloatingTaskNotDone.getStatus());
    }
	
}
