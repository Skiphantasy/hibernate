/**
 * @author Tania
 * @date 22 ene. 2019
 * @version 1.0
 * @description Main class that runs the program
 * 
 */

package firstpackage;

import java.util.Scanner;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 * Class Main
 */
public class Main {

	/**
	 * @variable_name stationLine
	 * @type StationLineInsertion
	 */
	private static StationLineInsertion stationLine;
	private static StationUpdate stationUpdate;
	private static HQLStationUpdate hqlStationUpdate;
	
	/**
	 * Method that runs the program
	 * @name main
	 * @param args 
	 */
	public static void main(String[] args) {
		System.out.println("Bienvenido/a al programa");
		showMenu();
	}
	
	/**
	 * Method that show menu options and let the user type options
	 * @name showMenu 
	 */
	public static void showMenu() {
		boolean isMenushown = false;
		String option;
		
		if(!isMenushown) {
			System.out.println("1...............Insertar registro en T_Linea_Estacion");
			System.out.println("2...............Actualizar T_Estaciones");
			System.out.println("3...............Visualizar Estaciones");
			System.out.println("4...............Actualizar T_Estaciones con HQL");
			System.out.println("5...............Visualizar Estaciones con HQL");
			System.out.println("6...............Salir");
			System.out.println("Teclee opción: ");	
			isMenushown = true;
		}
				
		do {
			if(!isMenushown) {
				System.out.println("1...............Insertar registro en T_Linea_Estacion");
				System.out.println("2...............Actualizar T_Estaciones");
				System.out.println("3...............Visualizar Estaciones");
				System.out.println("4...............Actualizar T_Estaciones con HQL");
				System.out.println("5...............Visualizar Estaciones con HQL");
				System.out.println("6...............Salir");
				System.out.println("Teclee opción: ");	
			}
			
			Scanner kb = new Scanner(System.in);
			option = kb.nextLine();
			
			switch(option) {
			case "1":
				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				Session session = sessionFactory.openSession();
				TLineas lines = new TLineas();
				TEstaciones stations = new TEstaciones();
				TLineaEstacion stationLines = new TLineaEstacion();
				int line, station, order;
				stationLine = new StationLineInsertion();
				
				do {
					do {
						System.out.println("Introduzca código de línea: ");
						line = kb.nextInt();							
					} while (!stationLine.checkLine(session, lines, line));
					
					do {
						System.out.println("Introduzca código de estación: ");
						station = kb.nextInt();					
					} while (!stationLine.checkStation(session, stations, station));
										
				} while(stationLine.checkStationLine(session, stationLines, line, station));
				do {
					System.out.println("Introduzca orden: ");
					order = kb.nextInt();					
				} while(!stationLine.checkOrder(session, line, order));		
				
				stationLine = new StationLineInsertion(line, station, order);
				session.close();
				isMenushown = false;
				break;
			case "2":
				sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();
				stations = new TEstaciones();
				stationLine = new StationLineInsertion();
				stationUpdate = new StationUpdate();
				stationUpdate.listStations(session);
				
				do {
					System.out.println("Introduzca código de estación: ");
					station = kb.nextInt();					
				} while (!stationLine.checkStation(session, stations, station));
				
				stationUpdate = new StationUpdate(station);
				isMenushown = false;
				session.close();
				break;
			case "3":
				new StationShow();
				isMenushown = false;
				break;
			case "4":
				sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();
				stations = new TEstaciones();
				stationLine = new StationLineInsertion();
				stationUpdate = new StationUpdate();
				stationUpdate.listStations(session);
				
				do {
					System.out.println("Introduzca código de estación: ");
					station = kb.nextInt();					
				} while (!stationLine.checkStation(session, stations, station));
				new HQLStationUpdate(station);
				session.close();
				isMenushown = false;
				break;
			case "5":
				new HQLStationShow();
				isMenushown = false;
				break;
			case "6":
				System.out.println("Fin del programa.");
				System.exit(0);
				break;
			default:
				System.out.println("Error. Teclee una opción válida");
				isMenushown = false;
				break;					
			}
		} while (!option.equals("6"));
	}

	/**
	 * Method that returns the total number of accesses for a station
	 * @name accessCount
	 * @param session
	 * @param station
	 * @return 
	 */
	protected static int accessCount(Session session, TEstaciones station) {
		Set<TAccesos> stationList = station.getTAccesoses();
		return stationList.size();		
	}

	/**
	 * Method that returns the total number of lines that travel through a station
	 * @name linesCount
	 * @param session
	 * @param station
	 * @return 
	 */
	protected static int linesCount(Session session, TEstaciones station) {
		Set<TLineaEstacion> stationList = station.getTLineaEstacions();
		return stationList.size();
	}

	/**
	 * Method that returns the total number of trips in which a station is the destiny
	 * @name destinyTripsCount
	 * @param session
	 * @param station
	 * @return 
	 */
	protected static int destinyTripsCount(Session session, TEstaciones station) {
		Set<TAccesos> stationList = station.getTViajesesForEstaciondestino();
		return stationList.size();
	}

	/**
	 * Method Method that returns the total number of trips in which a station is the origin
	 * @name originTripsCount
	 * @param session
	 * @param station
	 * @return 
	 */
	protected static int originTripsCount(Session session, TEstaciones station) {
		Set<TAccesos> stationList = station.getTViajesesForEstacionorigen();
		return stationList.size();
	}
}
