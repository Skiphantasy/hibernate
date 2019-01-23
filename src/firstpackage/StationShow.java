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
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Class StationShow
 */
public class StationShow {

	/**
	 * Class StationShow Constructor
	 */
	public StationShow() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		destinyTripsCount(session);
	}
	
	private void destinyTripsCount(Session session) {
		Criteria criteria = session.createCriteria(TEstaciones.class);
		List<TEstaciones> stations = criteria.list();
		Iterator<TEstaciones> it= stations.iterator();

		while (it.hasNext()){
			TEstaciones station = it.next();
			System.out.println("COD ESTACIÓN: " + station.getCodEstacion());
			System.out.println("NOMBRE ESTACIÓN: " + station.getNombre());
			System.out.println("Números de líneas que pasan: " + Main.linesCount(session, station));
			System.out.println("Número de accesos que tiene: " + Main.accessCount(session, station));
			System.out.println("NUM-Viajes-DESTINO:" + Main.destinyTripsCount(session, station));
			System.out.println("___________________________________");
			System.out.println("COD-VIAJE  NOMBRE-VIAJE-DESTINO"); 
			Set<TViajes> list = station.getTViajesesForEstaciondestino();
			Iterator<TViajes> iterator= list.iterator();

			while (iterator.hasNext()){
			TViajes trip = iterator.next();
			System.out.println(trip.getCodViaje() + "    " + trip.getNombre());
			}
			System.out.println("=================================");
		}
		
		
	}
	


}
