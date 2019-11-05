/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.OrdenDAO;
import Modelos.DTO.EstadoDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.OrdenDTO;
import Modelos.DTO.PedidoCompraDTO;
import Modelos.DTO.ProveedorDTO;
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
public class OrdenDAOIMPLE implements OrdenDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;

    public OrdenDAOIMPLE() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean generarorden(OrdenDTO dto) {
        int idOrden;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.orden_compras( fecha, id_usuario, id_estado, id_pedido, caracter, id_proveedor)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setInt(2, dto.getId_usuario());
            ps.setInt(3, dto.getId_estado());
            ps.setInt(4, dto.getId_pedido());
            ps.setString(5, "A");
            ps.setInt(6, dto.getId_proveedor());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idOrden = rs.getInt("id_ordencompra");
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO public.detalle_ordencompras(id_ordencompra, id_mercaderia, cantidad, precio, total)\n"
                                + "VALUES (?, ?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, idOrden);
                        ps.setInt(2, item.getId_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, item.getPrecio());
                        ps.setInt(5, item.getTotal());
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
                        query = "UPDATE pedidos SET id_estado='2' where id_pedido=?;";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, dto.getId_pedido());
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
                        ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                        return true; 
                    }
                }

            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdenDAOIMPLE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean anularorden(OrdenDTO dto
    ) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "Update orden_compras set id_estado='3' where id_ordencompra=?";
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
    public List<OrdenDTO> recuperarOrden(OrdenDTO dto
    ) {
        List<OrdenDTO> lista;
        OrdenDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {
            query = "select o.id_ordencompra, o.fecha, o.id_estado, o.id_pedido, o.id_proveedor, pro.nombre, \n"
                    + "deta.id_mercaderia, m.descripcion as mercaderia, deta.cantidad, deta.precio, deta.total\n"
                    + "from orden_compras o, estados e, pedidos p, proveedores pro, detalle_ordencompras deta, mercaderias m\n"
                    + "where p.id_pedido = o.id_pedido and pro.id_proveedor=o.id_proveedor and e.id_estado=o.id_estado and \n"
                    + "o.id_ordencompra=deta.id_ordencompra and m.id_mercaderia=deta.id_mercaderia and o.caracter='A' and o.id_ordencompra=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_orden());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
            item = new OrdenDTO();
            while (rs.next()) {
//              item.setFecha(Genericos.retornarFechaddMMyyyy(rs.getDate("fecha")));
                item.setId_orden(rs.getInt("id_ordencompra"));
                item.setFecha(rs.getDate("fecha").toString());
                item.setId_estado(rs.getInt("id_estado"));
                item.setId_pedido(rs.getInt("id_pedido"));
                item.setId_proveedor(rs.getInt("id_proveedor"));
                item.setNombre_proveedor(rs.getString("nombre"));
                itemMercaderia = new MercaderiaDTO();
                itemMercaderia.setId_mercaderia(rs.getInt("id_mercaderia"));
                itemMercaderia.setDescripcion(rs.getString("mercaderia"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                itemMercaderia.setPrecio(rs.getInt("precio"));
                itemMercaderia.setTotal(rs.getInt("total"));

                listaMercaderia.add(itemMercaderia);
                item.setLista_mercaderias(listaMercaderia);
            }
            lista.add(item);
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(OrdenDAOIMPLE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<EstadoDTO> getListEstado() {
        try {
            List<EstadoDTO> lista;
            EstadoDTO dto;
            query = "SELECT id_estado, descripcion FROM estados ORDER BY id_estado;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new EstadoDTO();
                dto.setId_estado(rs.getInt("id_estado"));
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
    public List<ProveedorDTO> getListOProveedor() {
        try {
            List<ProveedorDTO> lista;
            ProveedorDTO dto;
            query = "SELECT id_proveedor, nombre FROM proveedores ORDER BY id_proveedor;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ProveedorDTO();
                dto.setId_proveedor(rs.getInt("id_proveedor"));
                dto.setNombre(rs.getString("nombre"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<PedidoCompraDTO> getListPedido() {
        try {
            List<PedidoCompraDTO> lista;
            PedidoCompraDTO dto;
            query = "SELECT p.id_pedido, p.fecha, se.descripcion FROM sucursales se, pedidos p where se.id_sucursal=p.id_sucursal and p.id_estado='1' and p.caracter='A'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new PedidoCompraDTO();
                dto.setId_pedido(rs.getInt("id_pedido"));
                dto.setFecha(rs.getDate("fecha").toString());
                dto.setNombre_sucursal(rs.getString("descripcion"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<PedidoCompraDTO> recuperarPedido(OrdenDTO dto
    ) {
        List<PedidoCompraDTO> lista;
        PedidoCompraDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {

            query = "SELECT pe.id_pedido,\n"
                    + "ds.id_mercaderia, mer.descripcion, ds.cantidad, mer.precio\n"
                    + "                    FROM pedidos pe\n"
                    + "                     INNER JOIN usuarios usu ON pe.id_usuario=usu.id_usuario\n"
                    + "                     INNER JOIN sucursales su ON pe.id_sucursal=su.id_sucursal\n"
                    + "                    	INNER JOIN detalle_pedidos ds ON pe.id_pedido=ds.id_pedido\n"
                    + "                  	INNER JOIN mercaderias mer ON ds.id_mercaderia=mer.id_mercaderia\n"
                    + "                  WHERE pe.id_pedido=? and pe.id_estado=1 and pe.caracter='A'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_pedido());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
            item = new PedidoCompraDTO();

            while (rs.next()) {
//              item.setFecha(Genericos.retornarFechaddMMyyyy(rs.getDate("fecha")));
                item.setId_pedido(rs.getInt("id_pedido"));
                itemMercaderia = new MercaderiaDTO();
                itemMercaderia.setId_mercaderia(rs.getInt("id_mercaderia"));
                itemMercaderia.setDescripcion(rs.getString("descripcion"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                itemMercaderia.setPrecio(rs.getInt("precio"));
                itemMercaderia.setTotal(rs.getInt("precio") * rs.getInt("cantidad"));
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
    public List<OrdenDTO> getListConsultarTodo() {
        try {
            List<OrdenDTO> lista;
            OrdenDTO dto;
            query = "SELECT o.id_ordencompra, o.fecha, p.id_pedido, pro.nombre FROM proveedores pro, pedidos p, orden_compras o\n" +
"                                  where p.id_pedido=o.id_pedido and pro.id_proveedor=o.id_proveedor";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new OrdenDTO();
                dto.setId_orden(rs.getInt("id_ordencompra"));
                dto.setFecha(rs.getString("fecha"));
                dto.setId_pedido(rs.getInt("id_pedido"));
                dto.setNombre_proveedor(rs.getString("nombre"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}
