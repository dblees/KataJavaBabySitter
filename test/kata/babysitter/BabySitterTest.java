package kata.babysitter;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Dove Blees
 * @version 1.0
 * @since 2015 February 11
 * 
 * Unit Tests - BabySitter calculator
 * 
 * Specifications are at the bottom for transparency.
 * 
 * Kata Notes: 
 * 		babySitterHasInstance - only put there to make class red > green for eclemma ...
 * 
 * 		Normally if I was unclear upon a specification I would ask the functional / technical
 * 		writer for clarification.
 * 
 * 		If that is not possible, I would try to make good assumptions, 
 * 			but I would not go beyond the specifications, 
 * 			that is a waste of time until proven otherwise.
 * 		Unknowns:
 * 			No mention of e2e tests (would be separate if so)
 * 			No mention of a static Main "Runner" required, so only testing class
 * 			No mention of by Date calculations (which lead to this next assumption)
 * 			No mention of leap year, Spring forward, Fall back.
 * 			if possible that one Day would allow gaps of sitter coverage?
 * 			Holiday special rates?
 * 			Discount Rate for regular Customers?
 * 			Ability to apply a discount?
 * 			If working for a sitting Company/LLC, is there a base fee/percentage off the top?
 * 		Informational:
 * 			No org.junit @Before @After necessary ...
 * 				no datareset such as db create / drop tables,
 * 				no file copy overwrites,
 * 				no logs to clean up
 * 				no mock or disposable objects.
 */
public class BabySitterTest {

	@Test
	public void babySitterHasInstance() throws Exception {
		BabySitter sitter = new BabySitter();
		Assert.assertTrue(sitter instanceof BabySitter);
	}
	
	@Test
	public void calculateNoHours() throws Exception {
		Integer salary = BabySitter.calculate(5, 7, 0);
		Assert.assertTrue(salary == 0);
	}

	@Test
	public void startEarlierThan5pmThrowsError() throws Exception {
		try {
			BabySitter.calculate(4, 9, 10);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(BabySitter.ERR_START_TIME_INVALID));
			return;
		}
		Assert.fail();
	}

	@Test
	public void durationGreaterThan11HoursThrowsError() throws Exception {
		try {
			BabySitter.calculate(5, 9, 12);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(BabySitter.ERR_DURATION_INVALID));
			return;
		}
		Assert.fail();
	}

	@Test
	public void bedTime1PastMidnightThrowsError() throws Exception {
		try {
			BabySitter.calculate(5, 1, 10);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(BabySitter.ERR_BEDTIME_MIDNIGHT));
			return;
		}
		Assert.fail();
	}

	@Test
	public void bedTimeGreaterThan12ThrowsError() throws Exception {
		try {
			BabySitter.calculate(12, 17, 4);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(BabySitter.ERR_BEDTIME_MIDNIGHT));
			return;
		}
		Assert.fail();
	}
	
	@Test
	public void hoursPast4AMThrowsError() throws Exception {
		try {
			BabySitter.calculate(12, 12, 5);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(BabySitter.ERR_DURATION_INVALID));
			return;
		}
		Assert.fail();
	}

	@Test
	public void startTimeBefore12AndHoursPast4AMThrowsError() throws Exception {
		try {
			BabySitter.calculate(11, 12, 6);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(BabySitter.ERR_DURATION_INVALID));
			return;
		}
		Assert.fail();
	}

	@Test
	public void startTimeAfter12AndHoursPast4AMThrowsError() throws Exception {
		try {
			BabySitter.calculate(1, 12, 5);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equals(BabySitter.ERR_DURATION_INVALID));
			return;
		}
		Assert.fail();
	}
	
	@Test
	public void startTimeOnly4Hours() throws Exception {
		Integer salary = BabySitter.calculate(5, 9, 4);
		int expected = (BabySitter.Rate.StartTime.value * 4);
		Assert.assertTrue("salary value:[" + salary + "] expected:[" + expected + "]", salary == expected);
	}
	
	@Test
	public void startTimeHoursToBedtimeHoursExactlyHoursWorkedBefore12am()
			throws Exception {
		Integer salary = BabySitter.calculate(5, 11, 6);
		Assert.assertTrue(salary == (12 * 6));
	}

	@Test
	public void hoursWorkedGreaterThanStartTimeHoursToBedtimeHoursRange()
			throws Exception {
		Integer salary = BabySitter.calculate(5, 10, 7);
		Assert.assertTrue(salary == (BabySitter.Rate.StartTime.value * 5)
				+ (BabySitter.Rate.BedTime.value * 2));
	}

	@Test
	public void bedtimeBeforeStartTime() throws Exception {
		Integer salary = BabySitter.calculate(6, 5, 6);
		Assert.assertTrue(salary == (BabySitter.Rate.BedTime.value * 6));
	}

	@Test
	public void midnightStartTime4Hours() throws Exception {
		Integer salary = BabySitter.calculate(12, 5, 4);
		int expected = (BabySitter.Rate.midnight.value * 4);
		Assert.assertTrue("salary value:[" + salary + "] expected:[" + expected + "]", salary == expected);
	}

	@Test
	public void AllRatesTest() throws Exception {
		Integer salary = BabySitter.calculate(7, 9, 6);
		int expected = (BabySitter.Rate.StartTime.value * 2)
				+ (BabySitter.Rate.BedTime.value * 3)
				+ (BabySitter.Rate.midnight.value * 1);
		Assert.assertTrue("salary value:[" + salary + "] expected:[" + expected + "]", salary == expected);
	}
}



/**
 * Babysitter Kata
 * 
 * Background
 * This kata simulates a babysitter working and getting paid for one night.
 * The rules are pretty straight forward:
 * 
 * The babysitter - starts no earlier than 5:00PM
 * - leaves no later than 4:00AM
 * - gets paid $12/hour from start-time to bedtime
 * - gets paid $8/hour from bedtime to midnight
 * - gets paid $16/hour from midnight to end of job
 * - gets paid for full hours (no fractional hours)
 * 
 * Feature: As a babysitter In order to get paid for 1 night of work I want to
 * calculate my nightly charge
 */
