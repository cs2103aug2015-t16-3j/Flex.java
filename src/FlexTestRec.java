//@@author A0124901R

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

//FlexTestRec test that an input is a recurring task

//input
//add <taskname>; <start>-<end> every <day>

//output
//<taskname>, <start>-<end> every <day>

public class FlexTestRec {
	
	@Test
	public void testOutput() throws IOException {
		
		//recurring task input
		assertTrue(Checker.isRecurringTaskInput("task; 0000-0001 every monday"));
		assertTrue(Checker.isRecurringTaskInput("task; 0001-0002 every tuesday"));
		assertTrue(!Checker.isRecurringTaskInput("task; 0002-0003 every wednesday , "));
		assertTrue(!Checker.isRecurringTaskInput("task; 0003-0004 every thursday; medium"));
		assertTrue(!Checker.isRecurringTaskInput("task; 0004-0005 every friday bbb"));
		
		//boundary cases
		assertTrue(!Checker.isRecurringTaskInput("task; 00000-2359 every monday"));
		assertTrue(!Checker.isRecurringTaskInput("task; 0000-2400 every tuesday"));
		assertTrue(!Checker.isRecurringTaskInput("task; 0000-00000 every wednesday"));
		
		//recurring task output
		assertTrue(Checker.isRecurringTaskOutput("task, 1000-1100 every monday"));
		assertTrue(Checker.isRecurringTaskOutput("task, 1100-1200 every tuesday"));
		assertTrue(!Checker.isRecurringTaskOutput("task, 1200-1300 every wednesday ; "));
		assertTrue(!Checker.isRecurringTaskOutput("task, 1300-1400 every thursday xxx "));
		assertTrue(!Checker.isRecurringTaskOutput("task, 1400-1500 every friday , "));
	}
	

}
