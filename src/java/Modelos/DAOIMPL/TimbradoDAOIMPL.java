/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.TimbradoDAO;
import Modelos.DTO.TimbradoDTO;
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
public class TimbradoDAOIMPL implements TimbradoDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public TimbradoDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean agregar(TimbradoDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.timbrados(timbrado, fechainicio, fechafin, caracter)\n"
                    + "VALUES (?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getTimbrado_nro());
            ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFechainicio()));
            ps.setDate(3, Genericos.Genericos.retornarFecha(dto.getFechainicio()));
            ps.setString(4,"A");

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
    public boolean modificar(TimbradoDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE public.timbrados SET timbrado=?, fechainicio=?, fechafin=?, caracter='A'\n"
                    + " WHERE id_timbrado=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getTimbrado_nro());
            ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFechainicio()));
            ps.setDate(3, Genericos.Genericos.retornarFecha(dto.getFechainicio()));
            ps.setInt(4, dto.getId_timbrado());

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
            Logger.getLogger(TimbradoDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(TimbradoDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE timbrados set caracter='I' where id_timbrado=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_timbrado());

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
            Logger.getLogger(TimbradoDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<TimbradoDTO> consultarTodos() {
        try {
            List<TimbradoDTO> lista;
            TimbradoDTO dto;
            query = "SELECT id_timbrado, timbrado, fechainicio, fechafin FROM public.timbrados where caracter='A' order by id_timbrado desc;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new TimbradoDTO();
                dto.setId_timbrado(rs.getInt("id_timbrado"));
                dto.setTimbrado_nro(rs.getInt("timbrado"));
                dto.setFechainicio(Genericos.Genericos.retornarFechaddMMyyyy(rs.getDate("fechainicio")));
                dto.setFechafin(Genericos.Genericos.retornarFechaddMMyyyy(rs.getDate("fechafin")));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<TimbradoDTO> consultarSegunId(Integer id) {
        try {
            List<TimbradoDTO> lista;
            TimbradoDTO dto;
            query = "SELECT id_timbrado, timbrado, fechainicio, fechafin FROM public.timbrados where id_timbrado=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new TimbradoDTO();
                dto.setId_timbrado(rs.getInt("id_timbrado"));
                dto.setTimbrado_nro(rs.getInt("timbrado"));
                dto.setFechainicio(Genericos.Genericos.retornarFechayyyyMMdd(rs.getString("fechainicio")));
                dto.setFechafin(Genericos.Genericos.retornarFechaddMMyyyy(rs.getDate("fechafin")));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
