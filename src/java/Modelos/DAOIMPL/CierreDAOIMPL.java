/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.CierreDAO;
import Modelos.DTO.AperturacajaDTO;
import Modelos.DTO.CierreCajaDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public class CierreDAOIMPL implements CierreDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public CierreDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean agregar(CierreCajaDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.cierre_cajas(id_usuario, id_sucursal, id_caja, monto_cierre_efectivo, monto_targeta, caracter, fecha)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_usuario());
            ps.setInt(2, dto.getId_sucursal());
            ps.setInt(3, dto.getId_caja());
            ps.setInt(4, dto.getMonto_total());
            ps.setInt(5, dto.getMonto_total());
            ps.setString(6, "A");
            ps.setDate(7, Genericos.Genericos.retornarFecha(dto.getFecha()));
            if (ps.executeUpdate() > 0) {
                query = "UPDATE apertura_cajas SET caracter='I' where id_apertura=?;";
                ps = ConexionDB.getRutaConexion().prepareStatement(query);
                ps.setInt(1, dto.getId_apertura());
                if (ps.executeUpdate() <= 0) {
                    ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                    return false;
                }
                query = "update cajas set caracter='A' where id_caja=?";
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
            errorSQL = "Ocurrio un error al insertar el registro de condicion ";
            System.out.println(errorSQL);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<CierreCajaDTO> consultararqueo(Integer usuario, Integer sucursal, Integer caja, String fecha) {
        try {
            List<CierreCajaDTO> lista;
            CierreCajaDTO dto;
            query = "select SUM(deta.monto) as monto from detalle_cobro deta, cobro co, tipo_cobros ti, sucursales su, cajas ca\n"
                    + "where ti.id_ticobro=deta.id_ticobro and co.id_cobro=deta.id_cobro and su.id_sucursal=co.id_sucursal and ca.id_caja=co.id_caja\n"
                    + "and co.id_usuario=? and co.id_sucursal=? and co.fecha_cobro=current_date and co.id_caja=? and co.caracter='A'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, usuario);
            ps.setInt(2, sucursal);
            ps.setInt(3, caja);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CierreCajaDTO();
                dto.setMonto_total(rs.getInt("monto"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<AperturacajaDTO> getListAperturaarqueo() {
        try {
            List<AperturacajaDTO> lista;
            AperturacajaDTO dto;
            query = "select a.id_apertura, a.id_usuario, u.nombre, a.id_caja, c.descripcion as caja, a.id_sucursal, s.descripcion as sucursal, a.fecha_apertura\n"
                    + "from apertura_cajas a, cajas c, usuarios u, sucursales s where a.id_usuario=u.id_usuario and c.id_caja=a.id_caja\n"
                    + "and s.id_sucursal=a.id_sucursal and a.fecha_apertura=current_date and a.caracter='C' order by a.id_sucursal";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new AperturacajaDTO();
                dto.setId_apertura(rs.getInt("id_apertura"));
                dto.setId_usuario(rs.getInt("id_usuario"));
                dto.setNombre_usuario(rs.getString("nombre"));
                dto.setId_caja(rs.getInt("id_caja"));
                dto.setNombre_caja(rs.getString("caja"));
                dto.setId_sucursal(rs.getInt("id_sucursal"));
                dto.setNombre_sucursal(rs.getString("sucursal"));
                dto.setFecha(rs.getDate("fecha_apertura").toString());
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}
