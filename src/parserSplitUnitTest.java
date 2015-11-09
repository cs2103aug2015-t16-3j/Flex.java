import static org.junit.Assert.*;

import org.junit.Test;

public class parserSplitUnitTest {
	Command addNormalEvent = ParserSplit.parse("add taskname; 1000-1100 on 01/01/2015; 1");
    Command addCommandDeadline = ParserSplit.parse("add taskname; by 1200 on 01/01/2015");
    Command addCommandFloating = ParserSplit.parse("add taskname");
    Command addCommandRecurring = ParserSplit.parse("add taskname; 1000-1100 every tuesday");
	
	
	@Test
	//check for the add normal task
	public void testAddCommandEvent(){
		assertEquals("taskname", addNormalEvent .getTaskName());
		assertEquals("1000", addNormalEvent .getstartTime());
		assertEquals("1100", addNormalEvent .getEndTime());
		assertEquals("01/01/2015", addNormalEvent .getDate());
		assertEquals("1", addNormalEvent .getPriority());
	}
	
	//check for the add deadline task
	public void testAddDeadline(){
		assertEquals("taskname", addCommandDeadline.getTaskName());
		assertEquals("1200", addCommandDeadline.getEndTime());
		assertEquals("01/01/2015",addCommandDeadline.getDate());
	}
	
	//check for the add floating task
	public void testAddFloating(){
		assertEquals("taskname", addCommandFloating.getTaskName());
	}
	
	
	//check for the add recurring task
	public void testAddRecurring(){
		assertEquals("taskname", addCommandRecurring.getTaskName());
		assertEquals("1000", addCommandRecurring.getstartTime());
		assertEquals("1100", addCommandRecurring.getEndTime());
		assertEquals("tuesday", addCommandRecurring.getDay());
	}
 
}
