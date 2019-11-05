/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.tipo_tarjetaDAO;
import Modelos.DTO.tipo_tarjetasDTO;
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
public class tipo_tarjetasDAOIMPL implements tipo_tarjetaDAO{
    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;
            
            
    public tipo_tarjetasDAOIMPL(){
        ConexionDB.getInstancia();
    }

    @Override
    public boolean agregar(tipo_tarjetasDTO dto) {
try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO tipo_tarjetas(descripcion, caracter) VALUES(?,?);";
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
    public boolean modificar(tipo_tarjetasDTO dto) {
try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE tipo_tarjetas SET descripcion=?, caracter='A' WHERE id_tipo_tarjeta=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getDescripcion());
            ps.setInt(2, dto.getId_titarjeta());
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
            Logger.getLogger(tipo_tarjetasDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(tipo_tarjetasDTO dto) {
try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE tipo_tarjetas set caracter='I' where id_tipo_tarjeta=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_titarjeta());

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
            Logger.getLogger(tipo_tarjetasDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<tipo_tarjetasDTO> consultarTodos() {
try {
            List<tipo_tarjetasDTO> lista;
            tipo_tarjetasDTO dto;
            query = "SELECT id_tipo_tarjeta, descripcion FROM tipo_tarjetas where caracter='A' ORDER BY id_tipo_tarjeta;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new tipo_tarjetasDTO();
                dto.setId_titarjeta(rs.getInt("id_tipo_tarjeta"));
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
    public List<tipo_tarjetasDTO> consultarSegunId(Integer id) {
try {
            List<tipo_tarjetasDTO> lista;
            tipo_tarjetasDTO dto;
            query = "SELECT id_tipo_tarjeta, descripcion FROM tipo_tarjetas where id_tipo_tarjeta=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new tipo_tarjetasDTO();
                dto.setId_titarjeta(rs.getInt("id_tipo_tarjeta"));
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
