/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.ArqueoDAO;
import Modelos.DTO.AperturacajaDTO;
import Modelos.DTO.ArqueoDTO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public class ArqueoDAOIMPL implements ArqueoDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public ArqueoDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public List<ArqueoDTO> consultararqueo(Integer usuario, Integer sucursal, Integer caja, String fecha) {
        try {
            List<ArqueoDTO> lista;
            ArqueoDTO dto;
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
                dto = new ArqueoDTO();
                dto.setMonto_arqueo(rs.getInt("monto"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean agregar(ArqueoDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.arqueos(monto_arqueo, id_usuario, id_sucursal, caracter, id_caja, id_apertura, fecha_hora)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getMonto_arqueo());
            ps.setInt(2, dto.getId_usuario());
            ps.setInt(3, dto.getId_sucursal());
            ps.setString(4, "A");
            ps.setInt(5, dto.getId_caja());
            ps.setInt(6, dto.getId_apertura());
            ps.setDate(7, Genericos.Genericos.retornarFecha(dto.getFecha()));
            if (ps.executeUpdate() > 0) {
                query = "UPDATE apertura_cajas SET caracter='C' where id_apertura=?;";
                ps = ConexionDB.getRutaConexion().prepareStatement(query);
                ps.setInt(1, dto.getId_apertura());
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
    public List<AperturacajaDTO> getListAperturaarqueo() {
        try {
            List<AperturacajaDTO> lista;
            AperturacajaDTO dto;
            query = "select a.id_apertura, a.id_usuario, u.nombre, a.id_caja, c.descripcion as caja, a.id_sucursal, s.descripcion as sucursal, a.fecha_apertura\n"
                    + "from apertura_cajas a, cajas c, usuarios u, sucursales s where a.id_usuario=u.id_usuario and c.id_caja=a.id_caja\n"
                    + "and s.id_sucursal=a.id_sucursal and a.fecha_apertura=current_date and a.caracter='A' order by a.id_sucursal";
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
