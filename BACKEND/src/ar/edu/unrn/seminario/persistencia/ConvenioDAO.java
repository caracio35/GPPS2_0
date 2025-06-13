package ar.edu.unrn.seminario.persistencia;

import ar.edu.unrn.seminario.exception.ExcepcionAplicacion;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Convenio;

public interface ConvenioDAO {

	public void create(Convenio convenio) throws ExcepcionPersistencia, ExcepcionAplicacion;
}
