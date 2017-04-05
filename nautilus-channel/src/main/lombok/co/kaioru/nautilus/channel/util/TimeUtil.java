package co.kaioru.nautilus.channel.util;

public class TimeUtil {

	public static final long ZERO_TIME = 94354848000000000L;
	public static final long FT_UT_OFFSET = 116444592000000000L;
	public static final long PERMANENT_TIME = 150842304000000000L;

	public static long getTimestamp(long time) {
		if (time == 0) {
			return PERMANENT_TIME;
		}
		return time * 10000 + FT_UT_OFFSET;
	}

}
