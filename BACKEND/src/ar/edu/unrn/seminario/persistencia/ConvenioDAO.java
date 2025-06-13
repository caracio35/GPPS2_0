package ar.edu.unrn.seminario.persistencia;

import java.time.LocalDate;

import ar.edu.unrn.seminario.exception.ExcepcionAplicacion;
import ar.edu.unrn.seminario.exception.ExcepcionPersistencia;
import ar.edu.unrn.seminario.modelo.Convenio;

public interface ConvenioDAO {

	void create(Convenio convenio) throws ExcepcionPersistencia, ExcepcionAplicacion;
	
	boolean existeConvenioParaPropuesta(String tituloPropuesta) throws ExcepcionPersistencia;

	LocalDate obtenerFechaInicio(String tituloPropuesta) throws ExcepcionPersistencia;

	LocalDate obtenerFechaFin(String tituloPropuesta) throws ExcepcionPersistencia;
	
	
}
