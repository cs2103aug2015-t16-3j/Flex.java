<<<<<<< HEAD

=======
//@@author A0131835J
>>>>>>> fa73752610cb84d24203d771bb6708ec2ef706c9

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

// FlexTest3.java tests that the correct, valid date can be generated from a valid date

// Do take note that showWeek() uses generateNextDate(), and showWeek() has already called 
// checkDate() to verify the validity of the starting date of a week
// Therefore, only valid dates are used as for generateNextDate() 

public class TestValidDate {

	// number of days in each month for non-leap years
	private static final int JANUARY_DAYS = 31;
	private static final int FEBRUARY_DAYS = 28;
	private static final int MARCH_DAYS = 31;
	private static final int APRIL_DAYS = 30;
	private static final int MAY_DAYS = 31;
	private static final int JUNE_DAYS = 30;
	private static final int JULY_DAYS = 31;
	private static final int AUGUST_DAYS = 31;
	private static final int SEPTEMBER_DAYS = 30;
	private static final int OCTOBER_DAYS = 31;
	private static final int NOVEMBER_DAYS = 30;
	private static final int DECEMBER_DAYS = 31;

	@Test
	public void testOutput() throws IOException {
		// all these cases are in the valid date partition
		// they are not the boundary case of 1/1/1
		// however, if sub-boundaries exists, most of them are sub-boundary
		// cases of at the start or end of months
		// for different years
		assertEquals("generating the next date from 27/1/2014", "28/1/2014", ShowDays.generateNextDate("27/1/2014"));

		assertEquals("generating the next date from " + JANUARY_DAYS + " /1/2014", "1/2/2014",
				ShowDays.generateNextDate("31/1/2014"));

		// leap year case
		assertEquals("generating the next date from " + (FEBRUARY_DAYS + 1) + "29/2/2012", "1/3/2012",
				ShowDays.generateNextDate("29/2/2012"));

		// leap year case
		assertEquals("generating the next date from " + FEBRUARY_DAYS + " /2/2012", "29/2/2012",
				ShowDays.generateNextDate("28/2/2012"));

		assertEquals("generating the next date from " + JANUARY_DAYS + " /1/2013", "1/2/2013",
				ShowDays.generateNextDate("31/1/2013"));

		assertEquals("generating the next date from " + FEBRUARY_DAYS + " /2/2013", "1/3/2013",
				ShowDays.generateNextDate("28/2/2013"));

		assertEquals("generating the next date from " + MARCH_DAYS + " /3/2013", "1/4/2013",
				ShowDays.generateNextDate("31/3/2013"));

		assertEquals("generating the next date from " + APRIL_DAYS + " /4/2013", "1/5/2013",
				ShowDays.generateNextDate("30/4/2013"));

		assertEquals("generating the next date from " + MAY_DAYS + " /5/2013", "1/6/2013",
				ShowDays.generateNextDate("31/5/2013"));

		assertEquals("generating the next date from " + JUNE_DAYS + " /6/2013", "1/7/2013",
				ShowDays.generateNextDate("30/6/2013"));

		assertEquals("generating the next date from " + JULY_DAYS + " /7/2013", "1/8/2013",
				ShowDays.generateNextDate("31/7/2013"));

		assertEquals("generating the next date from " + AUGUST_DAYS + " /8/2013", "1/9/2013",
				ShowDays.generateNextDate("31/8/2013"));

		assertEquals("generating the next date from " + SEPTEMBER_DAYS + " /9/2013", "1/10/2013",
				ShowDays.generateNextDate("30/9/2013"));

		assertEquals("generating the next date from " + OCTOBER_DAYS + " /10/2013", "1/11/2013",
				ShowDays.generateNextDate("31/10/2013"));

		assertEquals("generating the next date from " + NOVEMBER_DAYS + " /11/2013", "1/12/2013",
				ShowDays.generateNextDate("30/11/2013"));

		assertEquals("generating the next date from " + DECEMBER_DAYS + " /12/2013", "1/1/2014",
				ShowDays.generateNextDate("31/12/2013"));
	}
}