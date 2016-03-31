package Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utils.MacRssiPair;
import Utils.Position;
import Utils.Utils;

/**
 * Simple Location finder that returns the first known APs location from the list of received MAC addresses
 * @author Bernd
 *
 */
public class OwnLocationFinder implements LocationFinder{
	
	private HashMap<String, Position> knownLocations; //Contains the known locations of APs. The long is a MAC address.
	public static final double RATIO = 0.224;
	public OwnLocationFinder(){
		knownLocations = Utils.getKnownLocations(); //Put the known locations in our hashMap
	}

	@Override
	public Position locate(MacRssiPair[] data) {
		//printMacs(data); //print all the received data
		return getHighestKnownFromList(data); //return the first known APs location
	}
	
	/**
	 * Returns the position of the first known AP found in the list of MacRssi pairs
	 * @param data
	 * @return
	 */
	private Position getHighestKnownFromList(MacRssiPair[] data){
		Position ret = new Position(0,0);
		double distanceB = 0.0;
		double distanceC = 0.0;
		double distanceA = 0.0;
		for(int i=0; i<data.length; i++){
			if (data[i].getMacAsString().equals("F4:CF:E2:54:E3:30")) {
				distanceB = (data[i].getRssi() + 54)/RATIO;
				System.out.println(distanceB);
			} else if (data[i].getMacAsString().equals("F4:CF:E2:2C:1B:40")){ 
				distanceC = (data[i].getRssi() + 55)/RATIO;
				System.out.println(distanceC);
			} else if (data[i].getMacAsString().equals("F4:CF:E2:2C:0F:20")) {
				System.out.println(distanceA);
				distanceA = (data[i].getRssi() + 54)/RATIO;
			}
		}
		return ret;
	}
	
	/**
	 * Outputs all the received MAC RSSI pairs to the standard out
	 * This method is provided so you can see the data you are getting
	 * @param data
	 */
	private void printMacs(MacRssiPair[] data) {
		for (MacRssiPair pair : data) {
			System.out.println(pair);
		}
	}
	
	
	public List<Position> makeBoundries(Position p, int distance) {
		Position upperBoundary = new Position(p.getX(), p.getY() + distance);
		Position lowerBoundary = new Position(p.getX(), p.getY() - distance);
		Position rightBoundary = new Position(p.getX() + distance, p.getY());
		Position leftBoundary = new Position(p.getX() - distance, p.getY());
		List<Position> boundaries = new ArrayList<Position>();
		return boundaries;
	}

}
