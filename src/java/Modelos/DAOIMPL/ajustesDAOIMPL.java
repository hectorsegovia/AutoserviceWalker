/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.ajustesDAO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.MotivoDTO;
import Modelos.DTO.SucursalDTO;
import Modelos.DTO.ajustesDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GuruW10P
 */
public class ajustesDAOIMPL implements ajustesDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;

    public ajustesDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean generarAjustes(ajustesDTO dto) {
        int reg_ajustes;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO ajustes(observacion, fecha_ajuste, id_usuario, caracter, id_sucursal)\n"
                    + "    VALUES (?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getObservacion());
            ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFecha_ajuste()));
            ps.setInt(3, dto.getId_usuario());
            ps.setString(4, "A");
            ps.setInt(5, dto.getId_sucursal());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    reg_ajustes = rs.getInt("id_ajuste");
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO detalle_ajustes(id_ajuste, id_mercaderia, id_motivo, cantidad)\n"
                                + "    VALUES (?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, reg_ajustes);
                        ps.setInt(2, item.getId_mercaderia());
                        ps.setInt(3, item.getId_motivo());
                        ps.setInt(4, item.getCantidad());
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
                        query = "UPDATE stock\n"
                                + "   SET cantidad=(select cantidad from stock where id_mercaderia=?) - ?\n"
                                + " WHERE id_mercaderia=? and id_deposito=?;";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, item.getId_mercaderia());
                        ps.setInt(2, item.getCantidad());
                        ps.setInt(3, item.getId_mercaderia());
                        ps.setInt(4, 1);
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }

                    }
                    ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                    return true;
                }
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(reg_comprasDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public List<SucursalDTO> getListConsultarSucursal() {
        try {
            List<SucursalDTO> lista;
            SucursalDTO dto;
            query = "SELECT id_sucursal, descripcion, caracter\n"
                    + "  FROM sucursales \n"
                    + "  WHERE caracter='A'\n"
                    + "  ORDER BY id_sucursal;";
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
    public List<MotivoDTO> getListConsultarMotivo() {
        try {
            List<MotivoDTO> lista;
            MotivoDTO dto;
            query = "SELECT id_motivo, descripcion, caracter\n"
                    + "  FROM motivos \n"
                    + "  WHERE caracter='A'\n"
                    + "  ORDER BY id_motivo;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MotivoDTO();
                dto.setId_motivo(rs.getInt("id_motivo"));
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
    public List<MercaderiaDTO> getListConsultarMercaderia() {
        try {
            List<MercaderiaDTO> lista;
            MercaderiaDTO dto;
            query = "SELECT id_mercaderia, descripcion FROM mercaderias ORDER BY id_mercaderia;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MercaderiaDTO();
                dto.setId_mercaderia(rs.getInt("id_mercaderia"));
                dto.setDescripcion(rs.getString("descripcion"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}
