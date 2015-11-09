
//@@ author A0131830U
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParserSplitDeleteUnitTest {
	Command deleteNormalEvent = ParserSplit.parse("delete 01/01/2015 3");
    Command deleteCommandFloating = ParserSplit.parse("delete floating 1");
    Command deleteCommandRecurring = ParserSplit.parse("delete rec 1");
	
	
	@Test
	//check for the delete normal event and deadline task
	public void testDeleteCommandEvent(){
		assertEquals("01/01/2015", deleteNormalEvent.getDate());
		assertEquals(3, deleteNormalEvent.getNumber());
	}
	
	//check for the delete floating task
	public void testDeleteCommandFloating(){
		assertEquals("floating", deleteCommandFloating.getTaskType());
		assertEquals(1, deleteCommandFloating.getNumber());
	}
	//check for the delete recurring task
	public void testDeleteCommandRecurring(){
		assertEquals("recurring", deleteCommandRecurring.getTaskType());
		assertEquals(1, deleteCommandRecurring.getNumber());
	}
}
