//@@author A0124901R

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

//FlexTestRec test that an input is a recurring task

//input
//add <taskname>; <start>-<end> every <day>

public class TestRecBoundary {
	
	@Test
	public void testOutput() throws IOException {
		
		//boundary cases
		assertTrue(!Checker.isRecurringTaskInput("task; 00000-2359 every monday"));
		assertTrue(!Checker.isRecurringTaskInput("task; 0000-2400 every tuesday"));
		assertTrue(!Checker.isRecurringTaskInput("task; 0000-00000 every wednesday"));
		
	}

}
