/**
 * @author Tania
 * @date 22 ene. 2019
 * @version 1.0
 * @description Class for Hibernate Utils
 * 
 */

package firstpackage;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


/**
 * Class HibernateUtil
 */
public class HibernateUtil {
	/**
	 * @variable_name sessionFactory
	 * @type SessionFactory
	 */
	private static final SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * Method that builds SessionFactory
	 * @name buildSessionFactory
	 * @return 
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure()
					.buildSessionFactory(new StandardServiceRegistryBuilder().configure().build());
		} catch (Throwable ex) {
			System.out.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Method that gets sessionFactory
	 * @name getSessionFactory
	 * @return 
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
