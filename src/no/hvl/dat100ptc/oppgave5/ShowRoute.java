package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import no.hvl.dat100ptc.TODO;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		
		xstep = scale(MAPXSIZE, minlon, maxlon);
		ystep = scale(MAPYSIZE, minlat, maxlat);
		
		showStatistics();

		showRouteMap(MARGIN + MAPYSIZE);

		replayRoute(MARGIN + MAPYSIZE);

	}

	public double scale(int maxsize, double minval, double maxval) {

		double step = maxsize / (Math.abs(maxval - minval));

		return step;
	}

	public void showRouteMap(int ybase) {
		setColor(0, 255, 0);

		int x = 0;
		int y = 0;

		int[] lastPoint = null;

		for (GPSPoint gpspoint : gpspoints) {
			x = (int)(xstep * Math.abs(gpspoint.getLongitude()  - minlon));
			y = (int)(ystep * Math.abs(gpspoint.getLatitude() - minlat));

			if (lastPoint != null) {
				drawLine(lastPoint[0], ybase - lastPoint[1], x, ybase - y);
			}

			lastPoint = new int[2];
			lastPoint[0] = x;
			lastPoint[1] = y;

			fillCircle(x, ybase - y, 5);
		}
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		drawString("Total time            :   " + GPSUtils.formatTime(gpscomputer.totalTime()), TEXTDISTANCE, TEXTDISTANCE);
		drawString("Total distance    :" + GPSUtils.formatDouble(gpscomputer.totalDistance() / 1000) + " km", TEXTDISTANCE, TEXTDISTANCE * 2);
		drawString("Total elevation   : " + GPSUtils.formatDouble(gpscomputer.totalElevation()) + " m", TEXTDISTANCE, TEXTDISTANCE * 3);
		drawString("Max speed          :" + GPSUtils.formatDouble(gpscomputer.maxSpeed() * 3.6) + " km/h", TEXTDISTANCE, TEXTDISTANCE * 4);
		drawString("Average speed  :" + GPSUtils.formatDouble(gpscomputer.averageSpeed() * 3.6) + " km/h", TEXTDISTANCE, TEXTDISTANCE * 5);
		drawString("Energy                : " + GPSUtils.formatDouble(gpscomputer.totalKcal(80)) + " kcal", TEXTDISTANCE, TEXTDISTANCE * 6);
	}

	public void replayRoute(int ybase) {
		setColor(0, 0, 255);
		int x = (int)(xstep * Math.abs(gpspoints[0].getLongitude() - minlon));
		int y = (int)(ystep * Math.abs(gpspoints[0].getLatitude() - minlat));

		int circle = fillCircle(x, ybase - y, 8);

		setSpeed(10);
		for (GPSPoint gpspoint : gpspoints) {
			x = (int)(xstep * Math.abs(gpspoint.getLongitude()  - minlon));
			y = (int)(ystep * Math.abs(gpspoint.getLatitude() - minlat));
			moveCircle(circle, x, ybase - y);
		}
	}

}
