/**
 * @author Tania
 * @date 22 ene. 2019
 * @version 1.0
 * @description Class that allows to the user a row in T_LineaEstacion
 * 
 */

package firstpackage;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;


/**
 * Class StationLineInsertion
 */
public class StationLineInsertion {

	/**
	 * Class StationLineInsertion Constructor
	 */
	/**
	 * @variable_name line_cod
	 * @type int
	 */
	int line_cod;
	/**
	 * @variable_name station_cod
	 * @type int
	 */
	int station_cod;
	/**
	 * @variable_name order
	 * @type int
	 */
	int order;
	/**
	 * @variable_name lineExist
	 * @type boolean
	 */
	boolean lineExist;
	/**
	 * @variable_name stationExist
	 * @type boolean
	 */
	boolean stationExist;
	/**
	 * @variable_name stationLineExist
	 * @type boolean
	 */
	boolean stationLineExist;

	/**
	 * Class StationLineInsertion Constructor
	 * @param line_cod
	 * @param station_cod
	 * @param order
	 */
	public StationLineInsertion(int line_cod, int station_cod, int order) {
		this.line_cod = line_cod;
		this.station_cod = station_cod;
		this.order = order;
		startOperations();
	}
	
	/**
	 * Class StationLineInsertion Constructor
	 */
	public StationLineInsertion() {		
	}

	/**
	 * Method that opens a session and verifies correct data
	 * @name startOperations 
	 */
	private void startOperations() {		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		System.out.println("Comprobando si los datos son correctos...");
		TLineas line = new TLineas();
		TEstaciones station = new TEstaciones();
		TLineaEstacion stationLine = new TLineaEstacion();
		lineExist = checkLine(session, line, line_cod);
		stationExist = checkStation(session, station, station_cod);

		if (lineExist && stationExist) {
			stationLineExist = checkStationLine(session, stationLine, line_cod, station_cod);

			if (!stationLineExist) {
				System.out.println("No existe aún un registro con esos datos.");
				System.out.println("Comprobando el orden...");
				
				if(checkOrder(session, line_cod, order)) {
					System.out.println("Insertando registro...");
					insertRow(session);
				} 
			}
		}

		session.close();
	}

	/**
	 * Method that insertes a row
	 * @name insertRow
	 * @param session 
	 */
	private void insertRow(Session session) {
		TLineaEstacionId id = new TLineaEstacionId(line_cod, station_cod);
		Transaction tx = session.beginTransaction();
		TLineaEstacion stationLine = new TLineaEstacion();
		stationLine.setId(id);
		stationLine.setOrden(order);
		session.save(stationLine);
		tx.commit();
		System.out.println("Se ha insertado el registro.");
		
	}
	
	/**
	 * Method that checks if the order is correct
	 * @name checkOrder
	 * @param session
	 * @param line_cod
	 * @param order
	 * @return 
	 */
	public boolean checkOrder(Session session, int line_cod, int order) {
		System.out.println("Comprobando el orden...");
		int correctOrder = 0;
		Query q = session.createQuery(
				"from TLineaEstacion where cod_linea = " + line_cod + " order by orden");
		List<TLineaEstacion> list = q.list();
		Iterator<TLineaEstacion> iter = list.iterator();				
		
		while (iter.hasNext()) {
			TLineaEstacion currentStationLine = (TLineaEstacion) iter.next();
			correctOrder = currentStationLine.getOrden() + 1;
		}
		
		if(order == correctOrder) {
			System.out.println("El orden es correcto.");
			return true;
		} else {
			System.out.println("Orden incorrecto. El valor correcto debería ser " + correctOrder);
			return false;
		}
	}
	
	/**
	 * Method that checks if there is a row with that station code and line code
	 * @name checkStationLine
	 * @param session
	 * @param stationLine
	 * @param line_cod
	 * @param station_cod
	 * @return 
	 */
	public boolean checkStationLine(Session session, TLineaEstacion stationLine, int line_cod, int station_cod) {
		try {
			TLineaEstacionId id = new TLineaEstacionId(line_cod, station_cod);
			stationLine = (TLineaEstacion) session.load(TLineaEstacion.class, id);
			stationLine.getOrden();
			stationLine.getId();
			System.out.println("El registro con el código de línea " + line_cod + " y código de estación " + station_cod
					+ " ya existe");
			return true;
		} catch (ObjectNotFoundException o) {
			return false;
		}
	}

	/**
	 * Method that checks if the line exists
	 * @name checkLine
	 * @param session
	 * @param line
	 * @param line_cod
	 * @return 
	 */
	public boolean checkLine(Session session, TLineas line, int line_cod) {
		try {
			line = (TLineas) session.load(TLineas.class, line_cod);
			line.getCodLinea();
			line.getNombre();
			System.out.println("Línea correcta.");
			return true;
		} catch (ObjectNotFoundException o) {
			System.out.println("La línea " + line_cod + " no existe.");
			return false;
		}
	}

	/**
	 * Method that checks if the station exists
	 * @name checkStation
	 * @param session
	 * @param station
	 * @param station_cod
	 * @return 
	 */
	public boolean checkStation(Session session, TEstaciones station, int station_cod) {
		try {
			station = (TEstaciones) session.load(TEstaciones.class, station_cod);
			station.getCodEstacion();
			station.getNombre();
			station.getDireccion();
			station.getNumaccesos();
			station.getNumlineas();
			station.getNumviajesdestino();
			station.getNumviajesprocedencia();
			System.out.println("Estación correcta.");
			return true;
		} catch (ObjectNotFoundException o) {
			System.out.println("La estación " + station_cod + " no existe.");
			return false;
		}
	}
	
	/**
	 * Method that gets station_cod
	 * @name getStation_cod
	 * @return 
	 */
	public int getStation_cod() {
		return station_cod;
	}

	/**
	 * Method that gets stationExist
	 * @name isStationExist
	 * @return 
	 */
	public boolean isStationExist() {
		return stationExist;
	}

	/**
	 * Method that gets stationLineExist
	 * @name isStationLineExist
	 * @return 
	 */
	public boolean isStationLineExist() {
		return stationLineExist;
	}
}
