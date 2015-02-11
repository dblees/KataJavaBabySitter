 /**
  * Kata Note:
  * 	Code Coverage with eclemma - might cause false positive?
  * 	http://sourceforge.net/p/emma/bugs/113/
  */
package kata.babysitter;

/**
 * @author Dove Blees
 * @version 1.0
 * @since 2015 February 11
 * 
 * Babysitter
 * 
 * Calculate a Babysitter's one Day Salary based upon Rates per hour.
 * 
 * Kata Notes:
 * 		No need to complicate this with a nested static class / only coded what the test demanded.
 *		Specifications were a little raw, but kept the test driven design simplified.
 */
public class BabySitter {

	public final static String ERR_START_TIME_INVALID = "Start time must be no earlier that 5PM and no later than 3AM";
	public final static String ERR_DURATION_INVALID = "Duration of hours worked must not exceed 4AM, or 11 hours total.";
	public final static String ERR_BEDTIME_MIDNIGHT = "Bedtime must be no later than midnight.";

	public BabySitter() {}
	
	/**
	 * Fixed Dollar/hour rates for coverage intervals.
	 */
	public static enum Rate {
		StartTime(12), BedTime(8), midnight(16);

		public int value;

		private Rate(int value) {
			this.value = value;
		}
	}

	/**
	 * Returns a whole positive number representing Salary calculated.
	 * 
	 * @param startTime - number range between 5(pm) through 3(am).
	 * @param bedTime - number range between 5(pm) through 12(am).
	 * @param hoursWorked - number range between zero through 11 hours.
	 * @return Integer - Salary calculated by hours worked at Rate per hour
	 * @throws Exception
	 */
	public static Integer calculate(int startTime, int bedTime, int hoursWorked)
			throws Exception {
		if (startTime == 4)
			throw new Exception(ERR_START_TIME_INVALID);
		if (bedTime > 12 || bedTime < 5)
			throw new Exception(ERR_BEDTIME_MIDNIGHT);
		if (hoursWorked > 11)
			throw new Exception(ERR_DURATION_INVALID);
		
		int hoursOver = startTime < 4 ? startTime + hoursWorked : (startTime + hoursWorked) - 12;
		if (hoursOver > 4)
			throw new Exception(ERR_DURATION_INVALID);
		if(hoursWorked < 1)
			return 0;

		int salary = 0;
		int start2BedtimeHours = (startTime < bedTime) ? (bedTime - startTime) : 0;
		int midnightHours = ((startTime + hoursWorked) > 12) ? (startTime + hoursWorked) - 12 : 0;

		if (startTime < 12) {
			salary += start2BedtimeHours * Rate.StartTime.value;
			salary += ((hoursWorked - start2BedtimeHours) - midnightHours)
					* Rate.BedTime.value;
		}
		salary += midnightHours * Rate.midnight.value;

		return salary;
	}
}
