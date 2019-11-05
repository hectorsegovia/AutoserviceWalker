/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.BancosDAO;
import Modelos.DTO.bancosDTO;
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
public class BancosDAOIMPL implements BancosDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public BancosDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean agregar(bancosDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.bancos(nombre, direccion, telefono, caracter)\n"
                    + "VALUES (?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getDireccion());
            ps.setString(3, dto.getTelefono());
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
    public boolean modificar(bancosDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE public.bancos SET nombre=?, direccion=?, telefono=?, caracter='A'\n"
                    + " WHERE id_bancos=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getDireccion());
            ps.setString(3, dto.getTelefono());
            ps.setInt(4, dto.getId_banco());

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
            Logger.getLogger(BancosDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(bancosDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE bancos set caracter='I' where id_bancos=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_banco());

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
            Logger.getLogger(BancosDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<bancosDTO> consultarTodos() {
        try {
            List<bancosDTO> lista;
            bancosDTO dto;
            query = "SELECT id_bancos, nombre, direccion, telefono\n"
                    + "FROM public.bancos where caracter='A' ORDER BY id_bancos;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new bancosDTO();
                dto.setId_banco(rs.getInt("id_bancos"));
                dto.setNombre(rs.getString("nombre"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setTelefono(rs.getString("telefono"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<bancosDTO> consultarSegunId(Integer id) {
        try {
            List<bancosDTO> lista;
            bancosDTO dto;
            query = "SELECT id_bancos, nombre, direccion, telefono\n"
                    + "FROM public.bancos where id_bancos=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new bancosDTO();
                dto.setId_banco(rs.getInt("id_bancos"));
                dto.setNombre(rs.getString("nombre"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setTelefono(rs.getString("telefono"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
