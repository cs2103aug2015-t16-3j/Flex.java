import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest1.java tests that valid dates in the required format are true, using valid date Strings, to test the 
// logic of checkDate()

public class FlexTest1{

	
	@Test
	public void testOutput() throws IOException{

		
		assertTrue(Checker.isValidDate("31/12/2012"));
		
		// this is the sub-boundary case of a date (in a month) in the valid date parition, 
		// provided that it is a leap year.
		assertTrue(Checker.isValidDate("29/2/2012"));
		
		assertTrue(Checker.isValidDate("28/2/2012"));
		
		assertTrue(Checker.isValidDate("31/1/2012"));
		
		assertTrue(Checker.isValidDate("1/1/2012"));
		
		assertTrue(Checker.isValidDate("1/1/2011"));
		
	}
}