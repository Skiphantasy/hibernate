/**
 * @author Tania
 * @date 23 ene. 2019
 * @version 1.0
 * @description 
 * 
 */

package firstpackage;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Iterator;
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

	private void startOperations() {
		boolean correctStation;
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
			System.out.println("Estación correcta.");
			tx = session.beginTransaction();
			station.setNumlineas(Main.linesCount(session, station));
			station.setNumaccesos(Main.accessCount(session, station));
			station.setNumviajesdestino(Main.destinyTripsCount(session, station));
			station.setNumviajesprocedencia(Main.originTripsCount(session, station));
			session.update(station);
			tx.commit();
			System.out.println("Estación actualizada correctamente");
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
