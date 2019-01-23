/**
 * @author Tania
 * @date 23 ene. 2019
 * @version 1.0
 * @description 
 * 
 */
package firstpackage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Class HQLStationUpdate
 */
public class HQLStationUpdate {

	int station_cod;
	/**
	 * Class HQLStationUpdate Constructor
	 */
	public HQLStationUpdate(int station_cod) {
		this.station_cod = station_cod;
		startOperations();
	}
	
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
				System.out.println("Estación correcta.");
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
			
			correctStation = true;
		} catch (ObjectNotFoundException o) {
			System.out.println("La estación " + station_cod + " no existe.");
			correctStation = false;
		} catch (Exception e) {
			System.out.println("ERROR NO IDENTIFICADO");
		}

		session.close();
	}
}
