/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.OrdenDAO;
import Modelos.DTO.CondicionDTO;
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
            query = "INSERT INTO orden_compras(fecha, idcondicion_compra, id_proveedor, id_usuario, id_pedido, observacion, estado)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setInt(2, dto.getId_condicion());
            ps.setInt(3, dto.getId_proveedor());
            ps.setInt(4, dto.getId_usuario());
            ps.setInt(5, dto.getId_pedido());
            ps.setString(6, dto.getObservacion());
            ps.setString(7, dto.getEstado());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idOrden = rs.getInt("id_ordencompra");
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO detalle_ordencompras(id_ordencompra, codigo_barra, cantidad, precio, id_impuesto)\n"
                                + "VALUES (?, ?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, idOrden);
                        ps.setString(2, item.getId_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, item.getPrecio());
                        ps.setInt(5, item.getId_impuesto());
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
                        query = "UPDATE pedidos SET estado='FINALIZADO' where id_pedido=?;";
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
            query = "Update orden_compras set estado='ANULADO' where id_ordencompra=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_orden());
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
            query = "select ord.id_ordencompra, ord.fecha, ord.id_usuario, ord.id_pedido, sucu.descripcion as sucursal, ord.id_proveedor, pro.nombre, ord.observacion, ord.idcondicion_compra,\n"
                    + " ord. estado, ds.codigo_barra, mer.descripcion as mercaderias, mer.precio_compra, ds.cantidad, im.descripcion AS impuesto,\n"
                    + "	(case when im.descripcion = 10 then \n"
                    + "	(mer.precio_compra * ds.cantidad)\n"
                    + "	else\n"
                    + "	'0' end) as \"IVA 10%\", \n"
                    + "	(case when im.descripcion = 5 then \n"
                    + "	(mer.precio_compra * ds.cantidad)\n"
                    + "	else\n"
                    + "	'0' end) as \"IVA 5%\",\n"
                    + "	(case when im.descripcion = 0 then \n"
                    + "	(mer.precio_compra * ds.cantidad)\n"
                    + "	else\n"
                    + "	'0' end) as \"EXENTA\"\n"
                    + "	FROM orden_compras ord INNER JOIN usuarios usu ON ord.id_usuario=usu.id_usuario\n"
                    + "	INNER JOIN pedidos pe ON pe.id_pedido=ord.id_pedido\n"
                    + "	INNER JOIN proveedores pro ON pro.id_proveedor=ord.id_proveedor\n"
                    + "	INNER JOIN condicion_compras con ON con.idcondicion_compra=ord.idcondicion_compra\n"
                    + "	INNER JOIN detalle_ordencompras ds ON ord.id_ordencompra=ds.id_ordencompra\n"
                    + "	INNER JOIN sucursales sucu ON sucu.id_sucursal=pe.id_sucursal\n"
                    + "	INNER JOIN mercaderias mer ON ds.codigo_barra=mer.codigo_barra\n"
                    + "	INNER JOIN impuestos im ON im.id_impuesto=mer.id_impuesto\n"
                    + "	WHERE ord.id_ordencompra=? and ord.estado='PENDIENTE'";
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
                item.setEstado(rs.getString("estado"));
                item.setId_pedido(rs.getInt("id_pedido"));
                item.setNombre_sucursal(rs.getString("sucursal"));
                item.setId_proveedor(rs.getInt("id_proveedor"));
                item.setNombre_proveedor(rs.getString("nombre"));
                item.setObservacion(rs.getString("observacion"));
                item.setId_condicion(rs.getInt("idcondicion_compra"));
                itemMercaderia = new MercaderiaDTO();
                itemMercaderia.setId_mercaderia(rs.getString("codigo_barra"));
                itemMercaderia.setDescripcion(rs.getString("mercaderias"));
                itemMercaderia.setCantidad(rs.getInt("precio_compra"));
                itemMercaderia.setPrecio(rs.getInt("cantidad"));
                itemMercaderia.setExenta(rs.getInt("EXENTA"));
                itemMercaderia.setIva5(rs.getInt("IVA 5%"));
                itemMercaderia.setIva10(rs.getInt("IVA 10%"));
                itemMercaderia.setTotal(rs.getInt("precio_compra") * rs.getInt("cantidad"));
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
    public List<CondicionDTO> getListCondicion() {
        try {
            List<CondicionDTO> lista;
            CondicionDTO dto;
            query = "SELECT idcondicion_compra, descripcion FROM condicion_compras ORDER BY idcondicion_compra;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CondicionDTO();
                dto.setId_condicion(rs.getInt("idcondicion_compra"));
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
            query = "SELECT p.id_pedido, p.fecha, se.descripcion FROM sucursales se, pedidos p where se.id_sucursal=p.id_sucursal and estado='APROBADO'";
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

            query = "select pe.id_pedido,ds.codigo_barra, mer.descripcion as mercaderias, mer.precio_compra, ds.cantidad, im.descripcion AS impuesto,\n"
                    + "	(case when im.descripcion = 10 then \n"
                    + "	(mer.precio_compra * ds.cantidad)\n"
                    + "	else\n"
                    + "	'0' end) as \"IVA 10%\", \n"
                    + "	(case when im.descripcion = 5 then \n"
                    + "	(mer.precio_compra * ds.cantidad)\n"
                    + "	else\n"
                    + "	'0' end) as \"IVA 5%\",\n"
                    + "	(case when im.descripcion = 0 then \n"
                    + "	(mer.precio_compra * ds.cantidad)\n"
                    + "	else\n"
                    + "	'0' end) as \"EXENTA\"\n"
                    + "	FROM pedidos pe INNER JOIN usuarios usu ON pe.id_usuario=usu.id_usuario\n"
                    + "	INNER JOIN sucursales su ON pe.id_sucursal=su.id_sucursal\n"
                    + "	INNER JOIN detalle_pedidos ds ON pe.id_pedido=ds.id_pedido\n"
                    + "	INNER JOIN mercaderias mer ON ds.codigo_barra=mer.codigo_barra\n"
                    + "	INNER JOIN impuestos im ON im.id_impuesto=mer.id_impuesto\n"
                    + "	WHERE pe.id_pedido=? and pe.estado='APROBADO'";
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
                itemMercaderia.setId_mercaderia(rs.getString("codigo_barra"));
                itemMercaderia.setDescripcion(rs.getString("mercaderias"));
                itemMercaderia.setPrecio(rs.getInt("precio_compra"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                itemMercaderia.setIva10(rs.getInt("IVA 10%"));
                itemMercaderia.setIva5(rs.getInt("IVA 5%"));
                itemMercaderia.setExenta(rs.getInt("EXENTA"));
                itemMercaderia.setTotal(rs.getInt("precio_compra") * rs.getInt("cantidad"));
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
            query = "SELECT o.id_ordencompra, o.fecha, p.id_pedido, pro.nombre FROM proveedores pro, pedidos p, orden_compras o\n"
                    + "                                  where p.id_pedido=o.id_pedido and pro.id_proveedor=o.id_proveedor";
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

    @Override
    public List<OrdenDTO> getListcodigoS() {
        try {
            List<OrdenDTO> lista;
            OrdenDTO dto;
            query = "SELECT COALESCE (MAX(id_ordencompra),0)+1 as codigo\n"
                    + "FROM orden_compras;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new OrdenDTO();
                dto.setId_orden(rs.getInt("codigo"));
                lista.add(dto);
            }
            return lista;
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean aprobarorden(OrdenDTO dto) {
 try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "Update orden_compras set estado='APROBADO' where id_ordencompra=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_orden());
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

}
