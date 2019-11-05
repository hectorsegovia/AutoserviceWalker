/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.CondicionDAO;
import Modelos.DTO.CondicionDTO;
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
public class CondicionDAOIMPL implements CondicionDAO{
    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;
            
    public CondicionDAOIMPL(){
        ConexionDB.getInstancia();
    }

    @Override
    public boolean agregar(CondicionDTO dto) {
try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO condicion(descripcion, caracter) VALUES(?,?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getDescripcion());
            ps.setString(2,"A");

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
    public boolean modificar(CondicionDTO dto) {
try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE condicion SET descripcion=?, caracter='I' WHERE id_condicion=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getDescripcion());
            ps.setInt(2, dto.getId_condicion());

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
            Logger.getLogger(CondicionDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(CondicionDTO dto) {
 try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE condicion set caracter='I' where id_condicion=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_condicion());

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
            Logger.getLogger(CondicionDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<CondicionDTO> consultarTodos() {
try {
            List<CondicionDTO> lista;
            CondicionDTO dto;
            query = "SELECT id_condicion, descripcion FROM condicion where caracter='A' ORDER BY id_condicion;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CondicionDTO();
                dto.setId_condicion(rs.getInt("id_condicion"));
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
    public List<CondicionDTO> consultarSegunId(Integer id) {
try {
            List<CondicionDTO> lista;
            CondicionDTO dto;
            query = "SELECT id_condicion, descripcion FROM condicion where id_condicion=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CondicionDTO();
                dto.setId_condicion(rs.getInt("id_condicion"));
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
