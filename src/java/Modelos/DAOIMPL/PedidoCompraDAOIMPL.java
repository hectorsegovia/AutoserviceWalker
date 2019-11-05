/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.PedidoCompraDAO;
import Modelos.DTO.depositoDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.PedidoCompraDTO;
import Modelos.DTO.SucursalDTO;
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
 * @author AspireES15
 */
public class PedidoCompraDAOIMPL implements PedidoCompraDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;

    public PedidoCompraDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean generarpedido(PedidoCompraDTO dto) {
        int idPedido;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO pedidos(fecha, id_sucursal, id_usuario, observacion, estado)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getId_pedido());
            ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setInt(3, dto.getId_sucursal());
            ps.setInt(4, dto.getId_usuario());
            ps.setString(5, dto.getObservacion());
            ps.setString(6, dto.getEstado());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idPedido = rs.getInt("id_pedido");
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO detalle_pedidos(id_pedido, codigo_barra, cantidad)\n"
                                + "VALUES (?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, idPedido);
                        ps.setInt(2, item.getId_mercaderia());
                        ps.setInt(3, item.getCantidad());
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
            Logger.getLogger(PedidoCompraDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean anularpedido(PedidoCompraDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "Update pedidos set id_estado='3' where id_pedido=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_pedido());
            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoCompraDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<PedidoCompraDTO> recuperarPedido(PedidoCompraDTO dto) {
        List<PedidoCompraDTO> lista;
        PedidoCompraDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {

            query = "SELECT pe.fecha, usu.id_usuario, usu.nombre as usuario, pe.id_estado, su.id_sucursal, su.descripcion as sucursal, pe.observacion,\n"
                    + "ds.id_mercaderia, mer.descripcion, ds.cantidad, ds.id_deposito, depo.descripcion as deposito\n"
                    + "                    FROM pedidos pe\n"
                    + "                     INNER JOIN usuarios usu ON pe.id_usuario=usu.id_usuario\n"
                    + "                     INNER JOIN sucursales su ON pe.id_sucursal=su.id_sucursal\n"
                    + "                    	INNER JOIN detalle_pedidos ds ON pe.id_pedido=ds.id_pedido\n"
                    + "                  	INNER JOIN mercaderias mer ON ds.id_mercaderia=mer.id_mercaderia\n"
                    + "                  	INNER JOIN depositos depo ON ds.id_deposito=depo.id_deposito\n"
                    + "                  WHERE pe.id_pedido=? and pe.id_estado=1 and pe.caracter='A'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_pedido());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
            item = new PedidoCompraDTO();

            while (rs.next()) {
//              item.setFecha(Genericos.retornarFechaddMMyyyy(rs.getDate("fecha")));
                item.setFecha(rs.getDate("fecha").toString());
                item.setId_usuario(rs.getInt("id_usuario"));
                item.setNombre_usuario(rs.getString("usuario"));
                item.setId_sucursal(rs.getInt("id_sucursal"));
                item.setNombre_sucursal(rs.getString("sucursal"));
                item.setObservacion(rs.getString("observacion"));

                itemMercaderia = new MercaderiaDTO();
                itemMercaderia.setId_mercaderia(rs.getInt("id_mercaderia"));
                itemMercaderia.setDescripcion(rs.getString("descripcion"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                itemMercaderia.setId_deposito(rs.getInt("id_deposito"));
                itemMercaderia.setNombre_deposito(rs.getString("deposito"));

                listaMercaderia.add(itemMercaderia);
                item.setLista_mercaderias(listaMercaderia);
            }
            lista.add(item);
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(PedidoCompraDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<MercaderiaDTO> getListMercaderia() {
        try {
            List<MercaderiaDTO> lista;
            MercaderiaDTO dto;
            query = "SELECT st.codigo_barra, me.descripcion, cantidad FROM mercaderias me, stock st where me.codigo_barra=st.codigo_barra\n"
                    + "ORDER BY codigo_barra;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MercaderiaDTO();
                dto.setId_mercaderia(rs.getInt("codigo_barra"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setCantidadd(rs.getInt("cantidad"));
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
    public List<PedidoCompraDTO> getListConsultarTodo() {
        try {
            List<PedidoCompraDTO> lista;
            PedidoCompraDTO dto;
            query = "select pe.id_pedido, pe.fecha, su.descripcion as sucursal\n"
                    + "from pedidos pe, sucursales su\n"
                    + "where su.id_sucursal=pe.id_sucursal AND pe.caracter='A' and pe.id_estado=1 order by pe.id_pedido desc ; ";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new PedidoCompraDTO();
                dto.setId_pedido(rs.getInt("id_pedido"));
                dto.setFecha(Genericos.Genericos.retornarFechaddMMyyyy(rs.getDate("fecha")));
                dto.setNombre_sucursal(rs.getString("sucursal"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<depositoDTO> getListDeposito() {
        try {
            List<depositoDTO> lista;
            depositoDTO dto;
            query = "SELECT id_deposito, descripcion FROM depositos ORDER BY id_deposito;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new depositoDTO();
                dto.setId_deposito(rs.getInt("id_deposito"));
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
