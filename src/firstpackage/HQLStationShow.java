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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Class HQLStationShow
 */
public class HQLStationShow {

	/**
	 * Class HQLStationShow Constructor
	 */
	public HQLStationShow() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		destinyTripsCount(session);
	}
	
	private void destinyTripsCount(Session session) {
		Query q = session.createQuery("from TEstaciones");
		List <TEstaciones> list = q.list();
		Iterator <TEstaciones> iter=list.iterator();
		
		while (iter.hasNext()){
			TEstaciones station = iter.next();
			System.out.printf("%-20s %20s\n", "COD ESTACIÓN: " + station.getCodEstacion(),"NOMBRE ESTACIÓN: " + station.getNombre());
			System.out.printf("%-30s %2d\n", "Números de líneas que pasan: ", station.getNumlineas());
			System.out.printf("%-30s %2d\n", "Número de accesos que tiene: ", station.getNumaccesos());
			System.out.printf("%-30s %2d\n", "NUM-Viajes-DESTINO: ",  station.getNumviajesdestino());
			System.out.printf("%-20s %20s\n", "COD-VIAJE", "NOMBRE-VIAJE-DESTINO");
			System.out.printf("%-40s\n", "_____________________________________________");
			
			if(station.getNumviajesdestino() > 0) {
				Query q1 = session.createQuery("from TViajes "
						+ "where TEstacionesByEstaciondestino = :current_station");
				q1.setParameter("current_station", station);
				List <TViajes> triplist = q1.list();
				Iterator <TViajes> iterator = triplist.iterator();
				
				while (iterator.hasNext()) {
					TViajes trip = iterator.next();
					System.out.printf("%5d %29s\n", trip.getCodViaje(), trip.getNombre());
				}
			}
			System.out.println("=============================================");
		}		
	}

}
