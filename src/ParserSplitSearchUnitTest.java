//@@author A0131830U
import static org.junit.Assert.*;

import org.junit.Test;

public class ParserSplitSearchUnitTest {
    Command searchTasknameCommand = ParserSplit.parse("search taskname ttt");
    Command searchDateCommand = ParserSplit.parse("search date 01/01/2015");
    Command searchDayCommand = ParserSplit.parse("search day Tuesday");
    Command searchStartCommand = ParserSplit.parse("search start 1000");
    Command searchEndCommand = ParserSplit.parse("search end 1100");
    Command searchPriority = ParserSplit.parse("search priority 1");
	@Test
	public void searchTaskname() {
	     assertEquals("taskname", searchTasknameCommand.getSearchType());
	     assertEquals("ttt", searchTasknameCommand.getKeyWord());
	}
	public void searchDate(){
		assertEquals("date", searchDateCommand.getSearchType());
		assertEquals("01/01/2015", searchDateCommand.getKeyWord());
	}
	public void searchDay(){
		assertEquals("day", searchDayCommand.getSearchType());
		assertEquals("Tuesday", searchDayCommand.getKeyWord());
	}
	
	public void searchStart(){
		assertEquals("start", searchStartCommand.getSearchType());
		assertEquals("1000",searchStartCommand.getKeyWord());
	}
    public void searchEnd(){
    	assertEquals("end", searchEndCommand.getSearchType());
    	assertEquals("1100", searchEndCommand.getKeyWord());
    }
    public void searchPriority(){
    	assertEquals("priority", searchPriority.getSearchType());
    	assertEquals("1",searchPriority.getKeyWord());
    }
}
