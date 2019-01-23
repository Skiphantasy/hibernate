/**
 * @author Tania
 * @date 22 ene. 2019
 * @version 1.0
 * @description 
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

	private static StationLineInsertion stationLine;
	private static StationUpdate stationUpdate;
	private static StationShow stationShow;
	private static HQLStationUpdate hqlStationUpdate;
	private static HQLStationShow hqlStationShow;
	
	/**
	 * Method
	 * @name main
	 * @param args 
	 */
	public static void main(String[] args) {
		System.out.println("Bienvenido/a al programa");
		showMenu();
		//new StationLineInsertion(1, 3, 6);
		//new StationUpdate(2);
		//new StationShow();
		//new HQLStationUpdate(1);
		//new HQLStationShow();
	}
	
	public static void showMenu() {
		Scanner kb = new Scanner(System.in);
		
		System.out.println("1...............Insertar registro en T_Linea_Estacion");
		System.out.println("2...............Actualizar T_Estaciones");
		System.out.println("3...............Visualizar Estaciones");
		System.out.println("4...............Actualizar T_Estaciones con HQL");
		System.out.println("5...............Visualizar Estaciones con HQL");
		System.out.println("6...............Salir");
		System.out.println("Teclee opción: ");
		
		String option;
		
		do {
			option = kb.nextLine();
			
			switch(option) {
			case "1":
				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				Session session = sessionFactory.openSession();
				TLineas lines = new TLineas();
				TEstaciones stations = new TEstaciones();
				int line, station, order;
				stationLine = new StationLineInsertion();
				
				do {
					System.out.println("Introduzca código de línea: ");
					line = kb.nextInt();					
				} while (!stationLine.checkLine(session, lines));
				do {
					System.out.println("Introduzca código de estación: ");
					station = kb.nextInt();					
				} while (!stationLine.checkStation(session, stations));
					System.out.println("Introduzca orden: ");
					order = kb.nextInt();
					
				stationLine = new StationLineInsertion(line, station, order);
			}
		} while (!option.equals("6"));
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
