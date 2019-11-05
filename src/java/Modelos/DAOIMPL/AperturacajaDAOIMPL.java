/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.AperturacajaDAO;
import Modelos.DTO.AperturacajaDTO;
import Modelos.DTO.CajasDTO;
import Modelos.DTO.SucursalDTO;
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
public class AperturacajaDAOIMPL implements AperturacajaDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public AperturacajaDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public List<CajasDTO> getListCajas() {
        try {
            List<CajasDTO> lista;
            CajasDTO dto;
            query = "SELECT id_caja, descripcion FROM cajas where caracter='A' ORDER BY id_caja;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CajasDTO();
                dto.setId_caja(rs.getInt("id_caja"));
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
    public boolean agregar(AperturacajaDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.apertura_cajas(id_caja, id_sucursal, id_usuario, monto_aper_efectivo, total_apertura, caracter, fecha_apertura, id_responsable)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_caja());
            ps.setInt(2, dto.getId_sucursal());
            ps.setInt(3, dto.getId_usuario());
            ps.setInt(4, dto.getMontototal());
            ps.setInt(5, dto.getMontototal());
            ps.setString(6, "A");
            ps.setDate(7, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setInt(8, dto.getId_responsable());
            if (ps.executeUpdate() > 0) {
                query = "update cajas set caracter='I' where id_caja=?";
                ps = ConexionDB.getRutaConexion().prepareStatement(query);
                ps.setInt(1, dto.getId_caja());
                if (ps.executeUpdate() <= 0) {
                    ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                    return false;
                }
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AperturacajaDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            errorSQL = "Ocurrio un error al insertar el registro de condicion ";
            System.out.println(errorSQL);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean modificar(AperturacajaDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE public.apertura_cajas SET id_caja=?, id_sucursal=?, id_usuario=?, fecha_apertura=?, \n"
                    + "monto_aper_efectivo=?, monto_cheque=?, monto_targeta=?, total_apertura=?, caracter=?\n"
                    + "WHERE id_apertura=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_caja());
            ps.setInt(2, dto.getId_sucursal());
            ps.setInt(3, dto.getId_usuario());
            ps.setDate(4, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setInt(8, 12);
            ps.setString(9, "A");
            ps.setInt(10, dto.getId_apertura());
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
    public boolean eliminar(AperturacajaDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE apertura_cajas set caracter=I where id_apertura=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_apertura());

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
            Logger.getLogger(AperturacajaDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<AperturacajaDTO> consultarTodos() {
        try {
            List<AperturacajaDTO> lista;
            AperturacajaDTO dto;
            query = "SELECT a.id_apertura, ca.descripcion, u.nombre, a.fecha_apertura\n"
                    + "FROM public.apertura_cajas a, cajas ca, usuarios u\n"
                    + "where ca.id_caja=a.id_caja and u.id_usuario=a.id_usuario\n"
                    + "order by a.id_apertura;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new AperturacajaDTO();
                dto.setId_apertura(rs.getInt("id_apertura"));
                dto.setNombre_caja(rs.getString("descripcion"));
                dto.setNombre_usuario(rs.getString("nombre"));
                dto.setFecha(Genericos.Genericos.retornarFechaddMMyyyy(rs.getDate("fecha_apertura")));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<AperturacajaDTO> consultarSegunId(Integer id) {
        try {
            List<AperturacajaDTO> lista;
            AperturacajaDTO dto;
            query = "SELECT id_apertura, id_caja, id_sucursal, id_usuario, fecha_apertura, \n"
                    + "       monto_aper_efectivo\n"
                    + "  FROM public.apertura_cajas where id_apertura=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new AperturacajaDTO();
                dto.setId_apertura(rs.getInt("id_apertura"));
                dto.setId_caja(rs.getInt("id_caja"));
                dto.setId_sucursal(rs.getInt("id_sucursal"));
                dto.setId_usuario(rs.getInt("id_usuario"));
                dto.setFecha(Genericos.Genericos.retornarFechaddMMyyyy(rs.getDate("fecha_apertura")));
                dto.setMontoefectivo(rs.getInt("monto_aper_efectivo"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<UsuarioDTO> getListResponsables() {
        try {
            List<UsuarioDTO> lista;
            UsuarioDTO dto;
            query = "SELECT id_usuario, nombre FROM usuarios ORDER BY id_usuario;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new UsuarioDTO();
                dto.setId_usuario(rs.getInt("id_usuario"));
                dto.setNombre(rs.getString("nombre"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
