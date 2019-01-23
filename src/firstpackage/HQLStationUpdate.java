/**
 * @author Tania
 * @date 23 ene. 2019
 * @version 1.0
 * @description Class that updates the number of lines, accesses, origin trips and 
 * destiny trips that a station has using HQL
 * 
 */

package firstpackage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


/**
 * Class HQLStationUpdate
 */
public class HQLStationUpdate {

	/**
	 * @variable_name station_cod
	 * @type int
	 */
	int station_cod;
	
	/**
	 * Class HQLStationUpdate Constructor
	 */
	public HQLStationUpdate(int station_cod) {
		this.station_cod = station_cod;
		startOperations();
	}
	
	/**
	 * Class StationUpdate Constructor
	 */
	public HQLStationUpdate() {
	}
	
	/**
	 * Method that opens a session, query a station and update it
	 * @name startOperations 
	 */
	private void startOperations() {
		boolean correctStation;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		TEstaciones station = new TEstaciones();
		Transaction tx;

		try {
			Query q = session.createQuery("from TEstaciones where codEstacion = " + station_cod);
			List <TEstaciones> list = q.list();

			if(list.size() == 0) {
				System.out.println("La estación " + station_cod + " no existe.");
			} else {				
				tx = session.beginTransaction();		
				station = (TEstaciones)q.uniqueResult();
				Query q1=session.createQuery("update TEstaciones set numlineas = :lines_no," 
				+" numaccesos = :access_no, numviajesdestino = :destiny_no, numviajesprocedencia = :origin_no"
				+ " where codEstacion = :station_c");
				q1.setParameter("lines_no", Main.linesCount(session, station));
				q1.setParameter("access_no", Main.accessCount(session, station));
				q1.setParameter("destiny_no", Main.destinyTripsCount(session, station));
				q1.setParameter("origin_no", Main.originTripsCount(session, station));
				q1.setParameter("station_c", station_cod);
				int result = q1.executeUpdate();
				session.update(station);
				tx.commit();
				System.out.println("Estación actualizada correctamente");
			}			
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
		Query q = session.createQuery("from TEstaciones");
		List <TEstaciones> list = q.list();
		Iterator<TEstaciones> it= list.iterator();
		
		if(list.size() == 0) {
			System.out.println("No hay estaciones que mostrar");
		} else {	
		
			System.out.printf("%-40s\n", "LISTADO DE ESTACIONES");
			System.out.printf("%-20s %20s\n", "COD ESTACIÓN","NOMBRE ESTACIÓN");
			System.out.printf("%-40s\n", "_____________________________________________");
			
			while (it.hasNext()){
				TEstaciones station = it.next();
				System.out.printf("%7d %30s\n",station.getCodEstacion(), station.getNombre());
			}
		}
		}

}
