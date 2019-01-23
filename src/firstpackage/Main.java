/**
 * @author Tania
 * @date 22 ene. 2019
 * @version 1.0
 * @description 
 * 
 */
package firstpackage;

import java.util.Set;

import org.hibernate.Session;

/**
 * Class Main
 */
public class Main {

	/**
	 * Method
	 * @name main
	 * @param args 
	 */
	public static void main(String[] args) {
		//new StationLineInsertion(1, 3, 6);
		//new StationUpdate(2);
		new StationShow();
	}

	protected static int accessCount(Session session, TEstaciones station) {
		Set<TAccesos> stationList = station.getTAccesoses();
		return stationList.size();
	}

	protected static int linesCount(Session session, TEstaciones station) {
		Set<TLineaEstacion> stationList = station.getTLineaEstacions();
		return stationList.size();
	}

	protected static int destinyTripsCount(Session session, TEstaciones station) {
		Set<TAccesos> stationList = station.getTViajesesForEstaciondestino();
		return stationList.size();
	}

	protected static int originTripsCount(Session session, TEstaciones station) {
		Set<TAccesos> stationList = station.getTViajesesForEstacionorigen();
		return stationList.size();
	}
}
