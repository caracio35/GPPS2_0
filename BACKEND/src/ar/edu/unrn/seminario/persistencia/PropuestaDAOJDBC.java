package ar.edu.unrn.seminario.persistencia;

import ar.edu.unrn.seminario.modelo.Actividad;
import ar.edu.unrn.seminario.modelo.Propuesta;

import java.util.ArrayList;
import java.util.List;

public class PropuestaDAOJDBC implements PropuestaDAO{

    public void create(Propuesta propuesta) {
        java.sql.Connection conn = null;
        java.sql.PreparedStatement statement = null;

        try {
            conn = ConnectionManager.getConnection();
            statement = conn.prepareStatement(
                    "INSERT INTO propuesta (titulo, descripcion, area_interes, objetivo, comentarios, aceptada, creador, alumno, tutor, profesor) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, propuesta.getTitulo());
            statement.setString(2, propuesta.getDescripcion());
            statement.setString(3, propuesta.getAreaDeInteres());
            statement.setString(4, propuesta.getObjetivo());
            statement.setString(5, propuesta.getComentarios());
            statement.setInt(6, propuesta.getAceptados());
            statement.setString(7, propuesta.getCreador().getUsuario());
            statement.setString(8, propuesta.getAlumno().getUsuario());
            statement.setString(9, propuesta.getTutor().getUsuario());
            statement.setString(10, propuesta.getProfesor().getUsuario());

            statement.executeUpdate();

            // Guardamos las actividades asociadas
            ActividadDAO actividadDAO = new ActividadDAOJDBC();
            for (Actividad actividad : propuesta.getActividades()) {
                actividadDAO.create(actividad, propuesta.getTitulo());
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Error al insertar propuesta: " + e.getMessage());
            // TODO: Crear y lanzar excepción propia
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }


    @Override
    public void update(Propuesta propuesta) {
        java.sql.Connection conn = null;
        java.sql.PreparedStatement statement = null;

        try {
            conn = ConnectionManager.getConnection();
            statement = conn.prepareStatement(
                    "UPDATE propuesta SET " +
                            "titulo = ?, " +
                            "descripcion = ?, " +
                            "area_interes = ?, " +
                            "objetivo = ?, " +
                            "comentarios = ?, " +
                            "aceptada = ?, " +
                            "creador = ?, " +
                            "alumno = ?, " +
                            "tutor = ?, " +
                            "profesor = ? " );

            // Establecemos los valores para cada campo
            statement.setString(1, propuesta.getTitulo());
            statement.setString(2, propuesta.getDescripcion());
            statement.setString(3, propuesta.getAreaDeInteres());
            statement.setString(4, propuesta.getObjetivo());
            statement.setString(5, propuesta.getComentarios());
            statement.setInt(6, propuesta.getAceptados());
            statement.setString(7, propuesta.getCreador().getUsuario());
            statement.setString(8, propuesta.getAlumno().getUsuario());
            statement.setString(9, propuesta.getTutor().getUsuario());
            statement.setString(10, propuesta.getProfesor().getUsuario());

            statement.executeUpdate();

        } catch (java.sql.SQLException e) {
            System.out.println("Error al actualizar propuesta: " + e.getMessage());
            // TODO: Crear y lanzar excepción propia
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(Propuesta propuesta) {

    }

    @Override
    public Propuesta find(Integer codigo) {
        return null;
    }

    @Override
    public List<Propuesta> findAll() {
        List<Propuesta> propuestas = new ArrayList<>();
        /*java.sql.Connection conn = null;
        java.sql.PreparedStatement statement = null;
        java.sql.ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            statement = conn.prepareStatement(
                    "SELECT p.*, " +
                            "c.nombre as creador_nombre, c.apellido as creador_apellido, " +
                            "a.nombre as alumno_nombre, a.apellido as alumno_apellido, " +
                            "t.nombre as tutor_nombre, t.apellido as tutor_apellido, " +
                            "pr.nombre as profesor_nombre, pr.apellido as profesor_apellido " +
                            "FROM propuesta p " +
                            "LEFT JOIN usuario uc ON p.creador = uc.usuario " +
                            "LEFT JOIN persona c ON uc.persona = c.dni " +
                            "LEFT JOIN usuario ua ON p.alumno = ua.usuario " +
                            "LEFT JOIN persona a ON ua.persona = a.dni " +
                            "LEFT JOIN usuario ut ON p.tutor = ut.usuario " +
                            "LEFT JOIN persona t ON ut.persona = t.dni " +
                            "LEFT JOIN usuario up ON p.profesor = up.usuario " +
                            "LEFT JOIN persona pr ON up.persona = pr.dni");

            rs = statement.executeQuery();

            ActividadDAO actividadDAO = new ActividadDAOJDBC();

            while (rs.next()) {
                // Crear usuarios
                Usuario creador = new Usuario(rs.getString("creador"),
                        new Persona(rs.getString("creador_nombre"), rs.getString("creador_apellido")));

                Usuario alumno = new Usuario(rs.getString("alumno"),
                        new Persona(rs.getString("alumno_nombre"), rs.getString("alumno_apellido")));

                Usuario tutor = new Usuario(rs.getString("tutor"),
                        new Persona(rs.getString("tutor_nombre"), rs.getString("tutor_apellido")));

                Usuario profesor = new Usuario(rs.getString("profesor"),
                        new Persona(rs.getString("profesor_nombre"), rs.getString("profesor_apellido")));

                // Obtener actividades de la propuesta
                List<Actividad> actividades = actividadDAO.findAllPorPropuesta(rs.getString("titulo"));

                // Crear propuesta
                Propuesta propuesta = new Propuesta(
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("area_interes"),
                        rs.getString("objetivo"),
                        rs.getString("comentarios"),
                        rs.getInt("aceptada"),
                        creador,
                        *//*alumno,
                        profesor,
                        tutor,*//*
                        actividades
                );

                propuestas.add(propuesta);
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Error al obtener propuestas: " + e.getMessage());
            // TODO: Crear y lanzar excepción propia
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
*/
        return propuestas;
    }
}