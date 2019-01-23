/**
 * @author Tania
 * @date 22 ene. 2019
 * @version 1.0
 * @description 
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
	int line_cod;
	int station_cod;
	int order;

	public StationLineInsertion(int line_cod, int station_cod, int order) {
		this.line_cod = line_cod;
		this.station_cod = station_cod;
		this.order = order;
		startOperations();
	}

	private void startOperations() {
		boolean lineExist;
		boolean stationExist;
		boolean stationLineExist;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		System.out.println("Comprobando si los datos son correctos...");
		TLineas line = new TLineas();
		TEstaciones station = new TEstaciones();
		TLineaEstacion stationLine = new TLineaEstacion();
		lineExist = checkLine(session, line);
		stationExist = checkStation(session, station);

		if (lineExist && stationExist) {
			stationLineExist = checkStationLine(session, stationLine);

			if (!stationLineExist) {
				System.out.println("No existe aún un registro con esos datos.");
				System.out.println("Comprobando el orden...");
				int correctOrder = 0;
				Query q = session.createQuery(
						"from TLineaEstacion where cod_linea = " + line_cod + "");
				List<TLineaEstacion> list = q.list();
				Iterator<TLineaEstacion> iter = list.iterator();				
				
				while (iter.hasNext()) {
					TLineaEstacion currentStationLine = (TLineaEstacion) iter.next();
					correctOrder = currentStationLine.getOrden() + 1;
				}
				
				if(order == correctOrder) {
					System.out.println("El orden es correcto.");
					System.out.println("Insertando registro...");
					insertRow(session);
				} else {
					System.out.println("Orden incorrecto. El valor correcto debería ser " + correctOrder);
				}
			}
		}

		session.close();
		//System.exit(0);
	}

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
	
	private boolean checkStationLine(Session session, TLineaEstacion stationLine) {
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

	private boolean checkLine(Session session, TLineas line) {
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

	private boolean checkStation(Session session, TEstaciones station) {
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

}
