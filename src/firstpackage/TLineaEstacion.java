/**
 * @author Tania
 * @date 22 ene. 2019
 * @version 1.0
 * @description Class generated for Hibernate
 * 
 */

package firstpackage;


// Generated 21-ene-2019 13:18:21 by Hibernate Tools 5.0.6.Final

/**
 * TLineaEstacion generated by hbm2java
 */
public class TLineaEstacion implements java.io.Serializable {

	/**
	 * @variable_name id
	 * @type TLineaEstacionId
	 */
	private TLineaEstacionId id;
	/**
	 * @variable_name TEstaciones
	 * @type TEstaciones
	 */
	private TEstaciones TEstaciones;
	/**
	 * @variable_name TLineas
	 * @type TLineas
	 */
	private TLineas TLineas;
	/**
	 * @variable_name orden
	 * @type int
	 */
	private int orden;

	/**
	 * Class TLineaEstacion Constructor
	 */
	public TLineaEstacion() {
	}

	/**
	 * Class TLineaEstacion Constructor
	 * @param id
	 * @param TEstaciones
	 * @param TLineas
	 * @param orden
	 */
	public TLineaEstacion(TLineaEstacionId id, TEstaciones TEstaciones, TLineas TLineas, int orden) {
		this.id = id;
		this.TEstaciones = TEstaciones;
		this.TLineas = TLineas;
		this.orden = orden;
	}

	/**
	 * Method that gets id
	 * @name getId
	 * @return 
	 */
	public TLineaEstacionId getId() {
		return this.id;
	}

	/**
	 * Method that sets id
	 * @name setId
	 * @param id 
	 */
	public void setId(TLineaEstacionId id) {
		this.id = id;
	}

	/**
	 * Method that gets TEstaciones
	 * @name getTEstaciones
	 * @return 
	 */
	public TEstaciones getTEstaciones() {
		return this.TEstaciones;
	}

	/**
	 * Method that sets TEstaciones
	 * @name setTEstaciones
	 * @param TEstaciones 
	 */
	public void setTEstaciones(TEstaciones TEstaciones) {
		this.TEstaciones = TEstaciones;
	}

	/**
	 * Method that gets TLineas
	 * @name getTLineas
	 * @return 
	 */
	public TLineas getTLineas() {
		return this.TLineas;
	}

	/**
	 * Method that sets TLineas
	 * @name setTLineas
	 * @param TLineas 
	 */
	public void setTLineas(TLineas TLineas) {
		this.TLineas = TLineas;
	}

	/**
	 * Method that gets orden
	 * @name getOrden
	 * @return 
	 */
	public int getOrden() {
		return this.orden;
	}

	/**
	 * Method that sets orden
	 * @name setOrden
	 * @param orden 
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}

}
