//@@author A0131830U
import static org.junit.Assert.*;

import org.junit.Test;

public class ParserSplitShowUnitTest {
    Command showallTask = ParserSplit.parse("show all");
    Command showdoneTask = ParserSplit.parse("show done");
    Command shownotdoneTask = ParserSplit.parse("show not done");
    Command showdeadlineTask = ParserSplit.parse("show deadline");
    Command showeventTask = ParserSplit.parse("show event");
    Command showrecurringTask = ParserSplit.parse("show recurring");
    Command showfloatingTask = ParserSplit.parse("show floating");
    Command showtaskname = ParserSplit.parse("show by taskname");
    Command showdate = ParserSplit.parse("show by date");
    Command showday = ParserSplit.parse("show by day");
    Command showstart = ParserSplit.parse("show by start");
    Command showEnd = ParserSplit.parse("show by end");
    Command showpriority = ParserSplit.parse("show by priority");
	@Test
	public void showAllTask() {
		assertEquals("all", showallTask.getShowKeyword());
		
	}
	public void showDoneTask(){
		assertEquals("done", showdoneTask.getShowKeyword());
	}
    
	public void showNotDoneTask(){
		assertEquals("not done", shownotdoneTask.getShowKeyword());
	}
	
	public void showDeadlineTask(){
		assertEquals("deadline", showdeadlineTask.getShowKeyword());
	}
	public void showEventTask(){
		assertEquals("event", showeventTask.getShowKeyword());
	}
	
	public void showRecurringTask(){
		assertEquals("recurring", showrecurringTask.getShowKeyword());
	}
	
	public void showFloatingTask(){
		assertEquals("floating", showfloatingTask.getShowKeyword());
	}
	
	public void showTaskName(){
		assertEquals("taskname", showtaskname.getShowKeyword());
	}
	
	public void showDate(){
		assertEquals("date", showdate.getShowKeyword());
	}
	
	public void showDay(){
		assertEquals("day", showday.getShowKeyword());
	}
	
	public void showStart(){
		assertEquals("start", showstart.getShowKeyword());
	}
	
	public void showEnd(){
		assertEquals("end", showEnd.getShowKeyword());
	}
	
	public void showPriority(){
		assertEquals("priority", showpriority.getShowKeyword());
	}
}
