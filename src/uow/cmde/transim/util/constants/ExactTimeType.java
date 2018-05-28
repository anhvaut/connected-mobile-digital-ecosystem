package uow.cmde.transim.util.constants;


/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */

public class ExactTimeType {

	/**
	 *  0 or (empty) - Frequency-based trips are not exactly scheduled. This is the default behavior.
	 *	1 - Frequency-based trips are exactly scheduled. For a frequencies.txt row, trips are scheduled starting with trip_start_time
	 */
	public static final int NOT_EXACTLY_SCHEDULED = 0;
	public static final int EXACTLY_SCHEDED = 1;
	 
}
