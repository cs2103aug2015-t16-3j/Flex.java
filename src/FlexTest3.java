import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import org.junit.Test;

// FlexTest3.java tests that the correct, valid date can be generated from a valid date

// Do take note that showWeek() uses generateNextDate(), and showWeek() has already called 
// checkDate() to verify the validity of the starting date of a week
// Therefore, only valid dates are used as for generateNextDate() 

public class FlexTest3{

	
	@Test
	public void testOutput() throws IOException{
		Flex tester = new Flex();
		
		assertEquals("generating the next date from 27/1/2014", "28/1/2014", tester.generateNextDate("27/1/2014"));
		
		assertEquals("generating the next date from 31/1/2014", "1/2/2014", tester.generateNextDate("31/1/2014"));
		
		assertEquals("generating the next date from 29/2/2012", "1/3/2012", tester.generateNextDate("29/2/2012"));
		
		assertEquals("generating the next date from 28/2/2012", "29/2/2012", tester.generateNextDate("28/2/2012"));
						
		assertEquals("generating the next date from 31/1/2013", "1/2/2013", tester.generateNextDate("31/1/2013"));
		
		assertEquals("generating the next date from 28/2/2013", "1/3/2013", tester.generateNextDate("28/2/2013"));
		
		assertEquals("generating the next date from 31/3/2013", "1/4/2013", tester.generateNextDate("31/3/2013"));
		
		assertEquals("generating the next date from 30/4/2013", "1/5/2013", tester.generateNextDate("30/4/2013"));
		
		assertEquals("generating the next date from 31/5/2013", "1/6/2013", tester.generateNextDate("31/5/2013"));
		
		assertEquals("generating the next date from 30/6/2013", "1/7/2013", tester.generateNextDate("30/6/2013"));
		
		assertEquals("generating the next date from 31/7/2013", "1/8/2013", tester.generateNextDate("31/7/2013"));
		
		assertEquals("generating the next date from 31/8/2013", "1/9/2013", tester.generateNextDate("31/8/2013"));
		
		assertEquals("generating the next date from 30/9/2013", "1/10/2013", tester.generateNextDate("30/9/2013"));
		
		assertEquals("generating the next date from 31/10/2013", "1/11/2013", tester.generateNextDate("31/10/2013"));
		
		assertEquals("generating the next date from 30/11/2013", "1/12/2013", tester.generateNextDate("30/11/2013"));
		
		assertEquals("generating the next date from 31/12/2013", "1/1/2014", tester.generateNextDate("31/12/2013"));		
	}
}