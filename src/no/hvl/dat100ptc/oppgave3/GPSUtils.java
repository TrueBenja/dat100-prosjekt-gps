package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.TODO;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {
		double min = da[0];

		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		double[] latitudes = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();
		}
		return latitudes;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {
		double[] longitudes = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
		}
		return longitudes;
	}

	private static final int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		double latitude1, longitude1, latitude2, longitude2;
		latitude1 = toRadians(gpspoint1.getLatitude());
		longitude1 = toRadians(gpspoint1.getLongitude());
		latitude2 = toRadians(gpspoint2.getLatitude());
		longitude2 = toRadians(gpspoint2.getLongitude());

		return R * compute_c(compute_a(latitude1, latitude2, (latitude2 - latitude1), (longitude2 - longitude1)));
	}
	
	private static double compute_a(double phi1, double phi2, double deltaphi, double deltadelta) {
		return pow(sin((deltaphi) / 2), 2) + cos(phi1) * cos(phi2) * pow(sin(deltadelta / 2), 2);
	}

	private static double compute_c(double a) {
		return 2 * atan2(sqrt(a), sqrt(1 - a));
	}

	
	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs = gpspoint2.getTime() - gpspoint1.getTime();
		double speed = distance(gpspoint1, gpspoint2) / secs;

		return speed;
	}

	public static String formatTime(int secs) {

		String timestr = " ";
		String TIMESEP = ":";

		int hr = secs / 3600;
		int min = secs % 3600 / 60;
		int sec = secs % 60;

		if (hr < 10) {
			timestr += "0" + hr;
		} else {
			timestr += hr;
		}

		timestr += TIMESEP;

		if (min < 10) {
			timestr += "0" + min;
		} else {
			timestr += min;
		}

		timestr += TIMESEP;

		if (sec < 10) {
			timestr += "0" + sec;
		} else {
			timestr += sec;
		}

		return " " + timestr;
	}
	
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str = String.format("%.2f", d);

		for (int i = str.length(); i < TEXTWIDTH; i++) {
			str = " " + str;
		}
		return str;
	}
}
