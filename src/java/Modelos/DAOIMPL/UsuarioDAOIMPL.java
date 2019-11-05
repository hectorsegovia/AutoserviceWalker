/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.UsuarioDAO;
import Modelos.DTO.EmpleadosDTO;
import Modelos.DTO.PerfilesDTO;
import Modelos.DTO.UsuarioDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AspireES15
 */
public class UsuarioDAOIMPL implements UsuarioDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public UsuarioDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public List<PerfilesDTO> getListPerfil() {
        try {
            List<PerfilesDTO> lista;
            PerfilesDTO dto;
            query = "SELECT id_perfil, descripcion FROM perfiles ORDER BY id_perfil;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new PerfilesDTO();
                dto.setId_perfil(rs.getInt("id_perfil"));
                dto.setDescripcion(rs.getString("descripcion"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<EmpleadosDTO> getListEmpleados() {
        try {
            List<EmpleadosDTO> lista;
            EmpleadosDTO dto;
            query = "select em.id_empleados, (em.nombre||' '||em.apellido) as empleado from empleados em";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new EmpleadosDTO();
                dto.setId_empleados(rs.getInt("id_empleados"));
                dto.setNombre(rs.getString("empleado"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean agregar(UsuarioDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.usuarios(nombre, clave, estado, id_perfil, id_empleados, caracter)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getClave());
            ps.setString(3, "A");
            ps.setInt(4, dto.getId_perfil());
            ps.setInt(5, dto.getId_empleados());
            ps.setString(6,"A");
            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            errorSQL = "Ocurrio un error al insertar el registro de condicion ";
            System.out.println(errorSQL);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean modificar(UsuarioDTO dto) {

        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE public.usuarios SET nombre=?, clave=?, estado=?, id_perfil=?, id_empleados=?, caracter='A'\n"
                    + " WHERE id_usuario=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getClave());
            ps.setString(3, "A");
            ps.setInt(4, dto.getId_perfil());
            ps.setInt(5, dto.getId_empleados());
            ps.setInt(6, dto.getId_usuario());

            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            errorSQL = "Ocurrio un error al insertar el registro de condicion ";
            System.out.println(errorSQL);
            Logger.getLogger(UsuarioDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(UsuarioDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE usuarios set estado='I' where id_usuario=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_usuario());

            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            errorSQL = "Ocurrio un error al insertar el registro de condicion ";
            System.out.println(errorSQL);
            Logger.getLogger(EstadoDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<UsuarioDTO> consultarTodos() {
        try {
            List<UsuarioDTO> lista;
            UsuarioDTO dto;
            query = "SELECT id_usuario, nombre, clave, estado, id_perfil, id_empleados\n"
                    + "  FROM public.usuarios where caracter='A' ORDER BY id_usuario;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new UsuarioDTO();
                dto.setId_usuario(rs.getInt("id_usuario"));
                dto.setNombre(rs.getString("nombre"));
                dto.setClave(rs.getString("clave"));
                dto.setId_perfil(rs.getInt("id_perfil"));
                dto.setId_empleados(rs.getInt("id_empleados"));

                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<UsuarioDTO> consultarSegunId(Integer id) {
        try {
            List<UsuarioDTO> lista;
            UsuarioDTO dto;
            query = "SELECT id_usuario, nombre, clave, estado, id_perfil, id_empleados\n"
                    + "  FROM public.usuarios where id_usuario=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new UsuarioDTO();
                dto.setId_usuario(rs.getInt("id_usuario"));
                dto.setNombre(rs.getString("nombre"));
                dto.setClave(rs.getString("clave"));
                dto.setId_perfil(rs.getInt("id_perfil"));
                dto.setId_empleados(rs.getInt("id_empleados"));

                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Integer getPermisoUsuario(UsuarioDTO dto) {
        try {
            query = "SELECT id_perfil FROM usuarios where estado='A' AND nombre=? AND clave=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getClave());
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_perfil");
            }
        } catch (SQLException ex) {

            Logger.getLogger(UsuarioDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 0;
    }
}
