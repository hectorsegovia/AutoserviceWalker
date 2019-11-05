/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Genericos.Genericos;
import Modelos.DAO.EmpleadosDAO;
import Modelos.DTO.CargoDTO;
import Modelos.DTO.CiudadesDTO;
import Modelos.DTO.EmpleadosDTO;
import Modelos.DTO.SucursalDTO;
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
public class EmpleadosDAOIMPL implements EmpleadosDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public EmpleadosDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public List<CargoDTO> getListCargos() {
        try {
            List<CargoDTO> lista;
            CargoDTO dto;
            query = "SELECT id_cargo, descripcion FROM cargos ORDER BY id_cargo;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CargoDTO();
                dto.setId_cargo(rs.getInt("id_cargo"));
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
    public List<CiudadesDTO> getListCiudades() {
        try {
            List<CiudadesDTO> lista;
            CiudadesDTO dto;
            query = "SELECT id_ciudad, descripcion FROM ciudades ORDER BY id_ciudad;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CiudadesDTO();
                dto.setId_ciudad(rs.getInt("id_ciudad"));
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
    public List<SucursalDTO> getListSucursal() {
        try {
            List<SucursalDTO> lista;
            SucursalDTO dto;
            query = "SELECT id_sucursal, descripcion FROM sucursales ORDER BY id_sucursal;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new SucursalDTO();
                dto.setId_sucursal(rs.getInt("id_sucursal"));
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
    public boolean agregar(EmpleadosDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.empleados( nombre, apellido, direccion, celular, cedula, id_ciudad, id_cargo, fechanaci, id_sucursal, caracter)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getApellido());
            ps.setString(3, dto.getDireccion());
            ps.setString(4, dto.getCelular());
            ps.setInt(5, dto.getCedula());
            ps.setInt(6, dto.getId_ciudad());
            ps.setInt(7, dto.getId_cargo());
            ps.setDate(8, Genericos.retornarFecha(dto.getFechanaci()));
            ps.setInt(9, dto.getId_sucursal());
            ps.setString(10,"A");
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
    public boolean modificar(EmpleadosDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE public.empleados SET nombre=?, apellido=?, direccion=?, celular=?, \n"
                    + "cedula=?, id_ciudad=?, id_cargo=?, fechanaci=?, id_sucursal=?, caracter='A'\n"
                    + "WHERE id_empleados=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getApellido());
            ps.setString(3, dto.getDireccion());
            ps.setString(4, dto.getCelular());
            ps.setInt(5, dto.getCedula());
            ps.setInt(6, dto.getId_ciudad());
            ps.setInt(7, dto.getId_cargo());
            ps.setDate(8, Genericos.retornarFecha(dto.getFechanaci()));
            ps.setInt(9, dto.getId_sucursal());
            ps.setInt(10, dto.getId_empleados());
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
            Logger.getLogger(EmpleadosDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(EmpleadosDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE empleados set caracter='I' where id_empleados=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_sucursal());

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
            Logger.getLogger(SucursalDAOIMP.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<EmpleadosDTO> consultarTodos() {
        try {
            List<EmpleadosDTO> lista;
            EmpleadosDTO dto;
            query = "SELECT em.id_empleados, em.nombre, em.apellido, em.direccion, em.celular, em.cedula, ci.descripcion as ciudad, ca.descripcion as cargo, em.fechanaci, su.descripcion as sucursal\n"
                    + "FROM public.empleados em, cargos ca, ciudades ci, sucursales su \n"
                    + "WHERE ca.id_cargo=em.id_cargo and ci.id_ciudad=em.id_ciudad and su.id_sucursal=em.id_sucursal and em.caracter='A'\n"
                    + "ORDER BY id_empleados;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new EmpleadosDTO();
                dto.setId_empleados(rs.getInt("id_empleados"));
                dto.setNombre(rs.getString("nombre"));
                dto.setApellido(rs.getString("apellido"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setCelular(rs.getString("celular"));
                dto.setCedula(rs.getInt("cedula"));
                dto.setNombre_ciudad(rs.getString("ciudad"));
                dto.setNombre_cargo(rs.getString("cargo"));
                dto.setFechanaci(Genericos.retornarFechaddMMyyyy(rs.getDate("fechanaci")));
                dto.setNombre_sucursal(rs.getString("sucursal"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<EmpleadosDTO> consultarSegunId(Integer id) {
        try {
            List<EmpleadosDTO> lista;
            EmpleadosDTO dto;
            query = "SELECT em.id_empleados, em.nombre, em.apellido, em.direccion, em.celular, em.cedula, ci.id_ciudad as ciudad, ca.id_cargo as cargo, em.fechanaci, su.id_sucursal as sucursal\n" +
"                    FROM public.empleados em, cargos ca, ciudades ci, sucursales su \n" +
"                   WHERE ca.id_cargo=em.id_cargo and ci.id_ciudad=em.id_ciudad and su.id_sucursal=em.id_sucursal\n" +
"                   and id_empleados = ?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new EmpleadosDTO();
                dto.setId_empleados(rs.getInt("id_empleados"));
                dto.setNombre(rs.getString("nombre"));
                dto.setApellido(rs.getString("apellido"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setCelular(rs.getString("celular"));
                dto.setCedula(rs.getInt("cedula"));
                dto.setId_ciudad(rs.getInt("ciudad"));
                dto.setId_cargo(rs.getInt("cargo"));
                dto.setFechanaci(Genericos.retornarFechaddMMyyyy(rs.getDate("fechanaci")));
                dto.setId_sucursal(rs.getInt("sucursal"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
