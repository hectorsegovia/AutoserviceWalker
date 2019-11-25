/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.RecepcionesDAO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.OrdenDTO;
import Modelos.DTO.RecepcionDTO;
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
 * @author HectorSegovia
 */
public class RecepcionesDAOIMPL implements RecepcionesDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;

    public RecepcionesDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean reg_recepcion(RecepcionDTO dto) {
        int idrecepcion;
        int idrecepcionn;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE recepciones SET  fecha=?, id_ordencompra=?, id_usuario=?, descripcion=?, estado=?\n"
                    + " WHERE id_recepcion=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setInt(2, dto.getId_ordencompra());
            ps.setInt(3, dto.getId_usuario());
            ps.setString(4, dto.getObservacion());
            ps.setString(5, "PENDIENTE");
            ps.setInt(6, dto.getId_recepcion());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    query = "DELETE FROM detalle_recepciones WHERE id_recepcion=?;";
                    ps = ConexionDB.getRutaConexion().prepareStatement(query);
                    ps.setInt(1, dto.getId_recepcion());
                    ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                    if (ps.executeUpdate() <= 0) {
                        ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                        return false;
                    }
                    ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO detalle_recepciones(id_recepcion, codigo_barra, cantidad, cantidad_recibida, estado)\n"
                                + "VALUES (?, ?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, dto.getId_recepcion());
                        ps.setString(2, item.getId_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, item.getCantidad_recibida());
                        ps.setString(5, "PENDIENTE");
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
            Logger.getLogger(RecepcionesDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    @Override
    public boolean anular_recepcion(RecepcionDTO dto
    ) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "Update recepciones set estado='ANULADO' where id_recepcion=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_recepcion());
            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;

            }
        } catch (SQLException ex) {
            Logger.getLogger(RecepcionesDAOIMPL.class
                    .getName()).log(Level.SEVERE, null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean aprobar_recepcion(RecepcionDTO dto
    ) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "Update recepciones set estado='RECIBIDO' where id_recepcion=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_recepcion());
            if (ps.executeUpdate() > 0) {
                for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                    query = "UPDATE stock SET cantidad=(select cantidad from stock where codigo_barra=?) + ? WHERE codigo_barra=? and id_deposito=?;";
                    ps = ConexionDB.getRutaConexion().prepareStatement(query);
                    ps.setString(1, item.getId_mercaderia());
                    ps.setInt(2, item.getCantidad_recibida());
                    ps.setString(3, item.getId_mercaderia());
                    ps.setInt(4, 1);
                    if (ps.executeUpdate() <= 0) {
                        ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                        return false;
                    }
                    query = "UPDATE orden_compras set estado='FINALIZADO' where id_ordencompra=?";
                    ps = ConexionDB.getRutaConexion().prepareStatement(query);
                    ps.setInt(1, dto.getId_ordencompra());
                    if (ps.executeUpdate() <= 0) {
                        ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                        return false;
                    }
                    
                }
              
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;

            }
        } catch (SQLException ex) {
            Logger.getLogger(RecepcionesDAOIMPL.class
                    .getName()).log(Level.SEVERE, null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<RecepcionDTO> recuperar_recepciones(RecepcionDTO dto
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<OrdenDTO> recuperar_OrdenCompras(RecepcionDTO dto
    ) {
        List<OrdenDTO> lista;
        OrdenDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {
            query = "select re.id_ordencompra, pe.id_proveedor, pro.nombre, ds.codigo_barra, mer.descripcion as mercaderias,ds.cantidad\n"
                    + "                                                    FROM recepciones re \n"
                    + "                                                    INNER JOIN orden_compras pe ON pe.id_ordencompra=re.id_ordencompra\n"
                    + "                                     			INNER JOIN condicion_compras condi ON pe.idcondicion_compra=condi.idcondicion_compra\n"
                    + "                                  			INNER JOIN proveedores pro ON pe.id_proveedor=pro.id_proveedor\n"
                    + "                                   			INNER JOIN usuarios usu ON pe.id_usuario=usu.id_usuario\n"
                    + "                                    		INNER JOIN pedidos pedid ON pe.id_pedido=pedid.id_pedido\n"
                    + "                                                      	INNER JOIN detalle_ordencompras ds ON pe.id_ordencompra=ds.id_ordencompra\n"
                    + "                                   		INNER JOIN mercaderias mer ON ds.codigo_barra=mer.codigo_barra\n"
                    + "                                                         	INNER JOIN impuestos im ON im.id_impuesto=mer.id_impuesto\n"
                    + "                                                        	WHERE re.id_recepcion=? and re.estado='PENDIENTE'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_recepcion());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
            item = new OrdenDTO();
            while (rs.next()) {
                System.out.println("entrando");
                item.setId_orden(rs.getInt("id_ordencompra"));
                item.setId_proveedor(rs.getInt("id_proveedor"));
                item.setNombre_proveedor(rs.getString("nombre"));
                itemMercaderia = new MercaderiaDTO();
                itemMercaderia.setId_mercaderia(rs.getString("codigo_barra"));
                itemMercaderia.setDescripcion(rs.getString("mercaderias"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                listaMercaderia.add(itemMercaderia);
                item.setLista_mercaderias(listaMercaderia);
            }
            lista.add(item);
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(reg_comprasDAOIMPL.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<RecepcionDTO> getListOrdenCompras() {
        try {
            List<RecepcionDTO> lista;
            RecepcionDTO dto;
            query = "SELECT re.id_recepcion, re.id_ordencompra, re.fecha, pro.nombre FROM proveedores pro, orden_compras o, recepciones re\n"
                    + "                   where pro.id_proveedor=o.id_proveedor and o.id_ordencompra=re.id_ordencompra and re.estado='PENDIENTE'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new RecepcionDTO();
                dto.setId_recepcion(rs.getInt("id_recepcion"));
                dto.setId_ordencompra(rs.getInt("id_ordencompra"));
                dto.setFecha(rs.getString("fecha"));
                dto.setNombre_proveedor(rs.getString("nombre"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<RecepcionDTO> getListConsultarTodo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RecepcionDTO> getListcodigoS() {
        try {
            List<RecepcionDTO> lista;
            RecepcionDTO dto;
            query = "SELECT COALESCE (MAX(id_recepcion),0)+1 as codigo\n"
                    + "FROM recepciones;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new RecepcionDTO();
                dto.setId_recepcion(rs.getInt("codigo"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}
