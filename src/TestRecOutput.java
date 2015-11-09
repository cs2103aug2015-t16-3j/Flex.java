

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

//FlexTestRec test that an input is a recurring task

//output
//<taskname>, <start>-<end> every <day>

public class TestRecOutput {
	
	@Test
	public void testOutput() throws IOException {
		
		//recurring task output
		assertTrue(Checker.isRecurringTaskOutput("task, 1000-1100 every monday"));
		assertTrue(Checker.isRecurringTaskOutput("task, 1100-1200 every tuesday"));
		assertTrue(!Checker.isRecurringTaskOutput("task, 1200-1300 every wednesday ; "));
		assertTrue(!Checker.isRecurringTaskOutput("task, 1300-1400 every thursday xxx "));
		assertTrue(!Checker.isRecurringTaskOutput("task, 1400-1500 every friday , "));
	}
	

}
