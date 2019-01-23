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
			System.out.printf("%-20s %20s\n", "COD ESTACIÓN: " + station.getCodEstacion(),"NOMBRE ESTACIÓN: " + station.getNombre());
			System.out.printf("%-30s %2d\n", "Números de líneas que pasan: ", station.getNumlineas());
			System.out.printf("%-30s %2d\n", "Número de accesos que tiene: ", station.getNumaccesos());
			System.out.printf("%-30s %2d\n", "NUM-Viajes-DESTINO: ",  station.getNumviajesdestino());
			System.out.printf("%-20s %20s\n", "COD-VIAJE", "NOMBRE-VIAJE-DESTINO");
			System.out.printf("%-40s\n", "_____________________________________________");
			
			if(station.getNumviajesdestino() > 0) {
				Set<TViajes> list = station.getTViajesesForEstaciondestino();
				Iterator<TViajes> iterator= list.iterator();

				while (iterator.hasNext()) {
					TViajes trip = iterator.next();
					System.out.printf("%5d %29s\n", trip.getCodViaje(), trip.getNombre());
				}
			}
			System.out.println("=============================================");
		}		
	}
	


}
