/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.tipo_facturaDAO;
import Modelos.DTO.Tipo_facturaDTO;
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
public class Tipo_facturasDAOIMPL implements tipo_facturaDAO{
    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;
            
    public Tipo_facturasDAOIMPL(){
        ConexionDB.getInstancia();
    }

    @Override
    public boolean agregar(Tipo_facturaDTO dto) {
try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO tipo_facturas(descripcion, caracter) VALUES(?,?);";
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
    public boolean modificar(Tipo_facturaDTO dto) {
try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE tipo_facturas SET descripcion=?, caracter='A' WHERE id_tifactura=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getDescripcion());
            ps.setInt(2, dto.getId_tifactura());
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
            Logger.getLogger(Tipo_facturasDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(Tipo_facturaDTO dto) {
try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE tipo_facturas set caracter='I' where id_tifactura=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_tifactura());

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
            Logger.getLogger(Tipo_facturasDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<Tipo_facturaDTO> consultarTodos() {
try {
            List<Tipo_facturaDTO> lista;
            Tipo_facturaDTO dto;
            query = "SELECT id_tifactura, descripcion FROM tipo_facturas where caracter='A' ORDER BY id_tifactura;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Tipo_facturaDTO();
                dto.setId_tifactura(rs.getInt("id_tifactura"));
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
    public List<Tipo_facturaDTO> consultarSegunId(Integer id) {
try {
            List<Tipo_facturaDTO> lista;
            Tipo_facturaDTO dto;
            query = "SELECT id_tifactura, descripcion FROM tipo_facturas where id_tifactura=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Tipo_facturaDTO();
                dto.setId_tifactura(rs.getInt("id_tifactura"));
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
