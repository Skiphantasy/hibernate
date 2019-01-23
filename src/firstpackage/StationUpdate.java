/**
 * @author Tania
 * @date 23 ene. 2019
 * @version 1.0
 * @description Class that updates the number of lines, accesses, origin trips and 
 * destiny trips that a station has
 * 
 */

package firstpackage;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Class StationUpdate
 */
public class StationUpdate {
	int station_cod;

	/**
	 * Class StationUpdate Constructor
	 */
	public StationUpdate(int station_cod) {
		this.station_cod = station_cod;
		startOperations();
	}
	
	/**
	 * Class StationUpdate Constructor
	 */
	public StationUpdate() {
	}

	/**
	 * Method that opens a session, query a station and update it
	 * @name startOperations 
	 */
	private void startOperations() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		TEstaciones station = new TEstaciones();
		Transaction tx;

		try {
			station = (TEstaciones) session.load(TEstaciones.class, station_cod);
			station.getCodEstacion();
			station.getNombre();
			station.getDireccion();
			station.getNumaccesos();
			station.getNumlineas();
			station.getNumviajesdestino();
			station.getNumviajesprocedencia();
			tx = session.beginTransaction();
			station.setNumlineas(Main.linesCount(session, station));
			station.setNumaccesos(Main.accessCount(session, station));
			station.setNumviajesdestino(Main.destinyTripsCount(session, station));
			station.setNumviajesprocedencia(Main.originTripsCount(session, station));
			session.update(station);
			tx.commit();
			System.out.println("Estación actualizada correctamente");
		} catch (ObjectNotFoundException o) {
			System.out.println("La estación " + station_cod + " no existe.");
		} catch (Exception e) {
			System.out.println("ERROR NO IDENTIFICADO");
		}

		session.close();
	}
	
	/**
	 * Method that shows all stations names and codes
	 * @name listStations
	 * @param session
	 * @param station 
	 */
	public void listStations(Session session) {
		Criteria criteria = session.createCriteria(TEstaciones.class);
		List<TEstaciones> stations = criteria.list();
		Iterator<TEstaciones> it= stations.iterator();

		System.out.printf("%-40s\n", "LISTADO DE ESTACIONES");
		System.out.printf("%-20s %20s\n", "COD ESTACIÓN","NOMBRE ESTACIÓN");
		System.out.printf("%-40s\n", "_____________________________________________");
		
		while (it.hasNext()){
			TEstaciones station = it.next();
			System.out.printf("%7d %30s\n",station.getCodEstacion(), station.getNombre());
		}
	}
}
