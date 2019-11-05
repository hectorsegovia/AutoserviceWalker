/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.ProveedorDAO;
import Modelos.DTO.CiudadesDTO;
import Modelos.DTO.PaisDTO;
import Modelos.DTO.ProveedorDTO;
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
public class ProveedorDAOIMPL implements ProveedorDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public ProveedorDAOIMPL() {
        ConexionDB.getInstancia();
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
    public List<PaisDTO> getListPais() {
        try {
            List<PaisDTO> lista;
            PaisDTO dto;
            query = "SELECT id_pais, descripcion FROM paises ORDER BY id_pais;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new PaisDTO();
                dto.setId_pais(rs.getInt("id_pais"));
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
    public boolean agregar(ProveedorDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.proveedores(nombre, direccion, celular, ruc, cuenta1, id_ciudad, \n"
                    + "id_pais, caracter) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getDireccion());
            ps.setString(3, dto.getCelular());
            ps.setString(4, dto.getRuc());
            ps.setInt(5, dto.getCuenta1());
            ps.setInt(6, dto.getId_ciudad());
            ps.setInt(7, dto.getId_pais());
            ps.setString(8, "A");

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
    public boolean modificar(ProveedorDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE public.proveedores SET nombre=?, direccion=?, celular=?, ruc=?, cuenta1=?, \n"
                    + "id_ciudad=?, id_pais=?, caracter=? WHERE id_proveedor=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getDireccion());
            ps.setString(3, dto.getCelular());
            ps.setString(4, dto.getRuc());
            ps.setInt(5, dto.getCuenta1());
            ps.setInt(6, dto.getId_ciudad());
            ps.setInt(7, dto.getId_pais());
            ps.setString(8, "A");
            ps.setInt(9, dto.getId_proveedor());

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
            Logger.getLogger(ProveedorDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(ProveedorDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE proveedores set caracter='I' where id_proveedor=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_proveedor());

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
            Logger.getLogger(ProveedorDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<ProveedorDTO> consultarTodos() {
        try {
            List<ProveedorDTO> lista;
            ProveedorDTO dto;
            query = "SELECT pro.id_proveedor, pro.nombre, pro.celular, pro.ruc, c.descripcion as ciudad,\n"
                    + "p.descripcion as pais FROM public.proveedores pro, ciudades c, paises p\n"
                    + "                 where c.id_ciudad=pro.id_ciudad and p.id_pais=pro.id_pais and pro.caracter = 'A' ORDER BY id_proveedor;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ProveedorDTO();
                dto.setId_proveedor(rs.getInt("id_proveedor"));
                dto.setNombre(rs.getString("nombre"));
                dto.setCelular(rs.getString("celular"));
                dto.setRuc(rs.getString("ruc"));
                dto.setNombre_ciudad(rs.getString("ciudad"));
                dto.setNombre_pais(rs.getString("pais"));

                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<ProveedorDTO> consultarSegunId(Integer id) {
        try {
            List<ProveedorDTO> lista;
            ProveedorDTO dto;
            query = "SELECT id_proveedor, nombre, direccion, celular, ruc, cuenta1, id_ciudad, \n"
                    + "id_pais FROM public.proveedores where id_proveedor=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ProveedorDTO();
                dto.setId_proveedor(rs.getInt("id_proveedor"));
                dto.setNombre(rs.getString("nombre"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setCelular(rs.getString("celular"));
                dto.setRuc(rs.getString("ruc"));
                dto.setCuenta1(rs.getInt("cuenta1"));
                dto.setId_ciudad(rs.getInt("id_ciudad"));
                dto.setId_pais(rs.getInt("id_pais"));

                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
