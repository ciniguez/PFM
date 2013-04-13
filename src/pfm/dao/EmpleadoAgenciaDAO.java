package pfm.dao;

import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Usuario;

public interface EmpleadoAgenciaDAO extends
		GenericDAO<EmpleadoAgencia, Integer> {

	public EmpleadoAgencia getAgenciaByEmpleado(Usuario empleado);

}
