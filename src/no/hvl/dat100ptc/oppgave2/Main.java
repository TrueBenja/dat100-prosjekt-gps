package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class Main {
	public static void main(String[] args) {
		GPSData gpsData = new GPSData(5);
		GPSPoint gpsPoint1 = new GPSPoint(1, 60.4, 60.1, 59.6);
		GPSPoint gpsPoint2 = new GPSPoint(2, 32.1, 65.7, 101.4);

		gpsData.insertGPS(gpsPoint1);
		gpsData.insertGPS(gpsPoint2);

		gpsData.print();
	}
}
