/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.reg_comprasDAO;
import Modelos.DTO.CondicionDTO;
import Modelos.DTO.Condiones_compra;
import Modelos.DTO.EstadoDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.OrdenDTO;
import Modelos.DTO.reg_comprasDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GuruW10P
 */
public class reg_comprasDAOIMPL implements reg_comprasDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public reg_comprasDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean reg_compras(reg_comprasDTO dto) {
        int reg_compras;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO reg_compras(num_factura, fecha_factura, id_ordencompra, id_condicion, id_comprobante, estado, id_usuario, num_timbrado, fecha_timbrado)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getNum_factura());
            ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFecha_factura()));
            ps.setInt(3, dto.getId_ordencompra());
            ps.setInt(4, dto.getId_condicion());
            ps.setInt(5, dto.getCondicion_compra());
            ps.setString(6, dto.getId_estado());
            ps.setInt(7, dto.getId_usuario());
            ps.setString(8, dto.getNum_timbrado());
            ps.setDate(9, Genericos.Genericos.retornarFecha(dto.getFecha_timbrado()));
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    reg_compras = rs.getInt("num_factura");
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO detalle_compras(codigo_barra, num_factura, cantidad, precio)\n"
                                + "VALUES (?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setString(1, item.getId_mercaderia());
                        ps.setInt(2, reg_compras);
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, item.getPrecio());
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
        }
        return true;
    }

    @Override
    public boolean anular_reg_compras(reg_comprasDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "Update reg_compras set estado='ANULADO' where num_factura=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNum_factura());
            if (ps.executeUpdate() > 0) {
                query = "UPDATE orden_compras set estado='APROBADO' where id_ordencompra=?";
                ps = ConexionDB.getRutaConexion().prepareStatement(query);
                ps.setInt(1, dto.getId_ordencompra());
                if (ps.executeUpdate() <= 0) {
                    ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                    return false;
                }
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(reg_comprasDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<reg_comprasDTO> recuperar_reg_compras(reg_comprasDTO dto) {
        List<reg_comprasDTO> lista;
        reg_comprasDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {
            query = "select pe.num_factura, pe.fecha_factura, pe.id_ordencompra, pe.id_condicion, pe.id_comprobante, pe.estado, pe.id_usuario, pe.num_timbrado, pe.fecha_timbrado,  ord.id_proveedor, pro.nombre,  ds.codigo_barra, mer.descripcion as mercaderias, mer.precio_compra, ds.cantidad, im.descripcion AS impuesto,\n"
                    + "                                        (case when im.descripcion = 10 then \n"
                    + "                                     (mer.precio_compra * ds.cantidad)\n"
                    + "                                     	else\n"
                    + "                                      	'0' end) as \"IVA 10%\",\n"
                    + "                                    	(case when im.descripcion = 5 then \n"
                    + "                                      	(mer.precio_compra * ds.cantidad)\n"
                    + "                                  	else\n"
                    + "                                       	'0' end) as \"IVA 5%\",\n"
                    + "                                      	(case when im.descripcion = 0 then \n"
                    + "                                       	(mer.precio_compra * ds.cantidad)\n"
                    + "                                      	else\n"
                    + "                                    	'0' end) as \"EXENTA\"\n"
                    + "                                      FROM reg_compras pe \n"
                    + "					INNER JOIN tipo_comprobante ti ON pe.id_comprobante=ti.id_comprobante\n"
                    + "					INNER JOIN orden_compras ord ON ord.id_ordencompra=pe.id_ordencompra\n"
                    + "					INNER JOIN condicion con ON con.id_condicion=pe.id_condicion\n"
                    + "                   			INNER JOIN proveedores pro ON ord.id_proveedor=pro.id_proveedor\n"
                    + "                    			INNER JOIN usuarios usu ON pe.id_usuario=usu.id_usuario\n"
                    + "                                      	INNER JOIN detalle_compras ds ON pe.num_factura=ds.num_factura\n"
                    + "					INNER JOIN mercaderias mer ON ds.codigo_barra=mer.codigo_barra\n"
                    + "                                    	INNER JOIN impuestos im ON im.id_impuesto=mer.id_impuesto\n"
                    + "                                    	WHERE pe.num_factura=? and pe.estado='APROBADO'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNum_factura());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
            item = new reg_comprasDTO();
            while (rs.next()) {
                item.setNum_factura(rs.getString("num_factura"));
                item.setFecha_factura(rs.getString("fecha_factura"));
                item.setId_ordencompra(rs.getInt("id_ordencompra"));
                item.setId_condicion(rs.getInt("id_condicion"));
                item.setCondicion_compra(rs.getInt("id_comprobante"));
                item.setId_estado(rs.getString("estado"));
                item.setId_usuario(rs.getInt("id_usuario"));
                item.setNum_timbrado(rs.getString("num_timbrado"));
                item.setFecha_timbrado(rs.getString("fecha_timbrado"));
                item.setId_proveedor(rs.getInt("id_proveedor"));
                item.setNombre_proveedor(rs.getString("nombre"));
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
            Logger.getLogger(reg_comprasDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<reg_comprasDTO> getListConsultarTodo() {
        try {
            List<reg_comprasDTO> lista;
            reg_comprasDTO dto;
            query = "SELECT reg.num_factura, reg.fecha_factura, pro.nombre AS Provee\n"
                    + "                    FROM reg_compras reg\n"
                    + "                   INNER JOIN orden_compras ord ON reg.id_ordencompra=ord.id_ordencompra\n"
                    + "                   INNER JOIN proveedores pro ON ord.id_proveedor=pro.id_proveedor\n"
                    + "                  WHERE reg.estado='APROBADO';";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new reg_comprasDTO();
                dto.setNum_factura(rs.getString("num_factura"));
                dto.setFecha_factura(rs.getString("fecha_factura"));
                dto.setNombre_provee(rs.getString("Provee"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    @Override
    public List<OrdenDTO> recuperar_OrdenCompras(reg_comprasDTO dto) {
        List<OrdenDTO> lista;
        OrdenDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {
            query = "select ord.id_ordencompra, ord.id_proveedor, pro.nombre, ds.codigo_barra, mer.descripcion as mercaderias, SUM(ds.cantidad_recibida) as recibido, da.cantidad, da.precio,\n"
                    + "                                       (case when im.descripcion = 10 then\n"
                    + "                                                         	(da.precio * SUM(ds.cantidad_recibida))\n"
                    + "                                                          	else\n"
                    + "                                                          	'0' end) as \"IVA 10%\", \n"
                    + "                                                           	(case when im.descripcion = 5 then \n"
                    + "                                                            	(da.precio * SUM(ds.cantidad_recibida))\n"
                    + "                                                        	else\n"
                    + "                                                          '0' end) as \"IVA 5%\",\n"
                    + "                                                          	(case when im.descripcion = 0 then \n"
                    + "                                                          	(da.precio * SUM(ds.cantidad_recibida))\n"
                    + "                                                           	else\n"
                    + "                                                        	'0' end) as \"EXENTA\"\n"
                    + "                                       from orden_compras ord, proveedores pro, mercaderias mer, detalle_recepciones ds, recepciones re, detalle_ordencompras da, impuestos im\n"
                    + "                                       where pro.id_proveedor=ord.id_proveedor and mer.codigo_barra=ds.codigo_barra \n"
                    + "                                       and ord.id_ordencompra=re.id_ordencompra and re.id_recepcion=ds.id_recepcion\n"
                    + "                                      and ord.id_ordencompra=da.id_ordencompra and mer.codigo_barra=da.codigo_barra and im.id_impuesto=mer.id_impuesto  and ord.id_ordencompra=? and ds.estado='PENDIENTE'\n"
                    + "                                     group by ord.id_ordencompra, ord.id_proveedor, pro.nombre, ds.codigo_barra, mer.descripcion, da.cantidad, da.precio, im.descripcion";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_ordencompra());
            System.out.println("Codigo" + dto.getId_ordencompra());
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
                itemMercaderia.setPrecio(rs.getInt("precio"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                itemMercaderia.setCantidad_recibida(rs.getInt("recibido"));
                itemMercaderia.setExenta(rs.getInt("EXENTA"));
                itemMercaderia.setIva5(rs.getInt("IVA 5%"));
                itemMercaderia.setIva10(rs.getInt("IVA 10%"));
                itemMercaderia.setTotal(rs.getInt("precio") * rs.getInt("recibido"));
                listaMercaderia.add(itemMercaderia);
                item.setLista_mercaderias(listaMercaderia);
            }
            lista.add(item);
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(reg_comprasDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<OrdenDTO> getListOrdenCompras() {
        try {
            List<OrdenDTO> lista;
            OrdenDTO dto;
            query = "SELECT o.id_ordencompra, o.fecha, p.id_pedido, pro.nombre, re.id_recepcion \n"
                    + "                                       FROM proveedores pro, pedidos p, orden_compras o, recepciones re, detalle_recepciones dr, mercaderias mer \n"
                    + "                                        where p.id_pedido=o.id_pedido and pro.id_proveedor=o.id_proveedor  and re.id_recepcion=dr.id_recepcion and mer.codigo_barra=dr.codigo_barra\n"
                    + "                                       and o.id_ordencompra=re.id_ordencompra and o.estado='FINALIZADO' and dr.estado='PENDIENTE'\n"
                    + "                                      group by o.id_ordencompra, o.fecha, p.id_pedido, pro.nombre, re.id_recepcion";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new OrdenDTO();
                dto.setId_orden(rs.getInt("id_ordencompra"));
                dto.setId_recepcion(rs.getInt("id_recepcion"));
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
    public List<CondicionDTO> getListCondicion() {
        try {
            List<CondicionDTO> lista;
            CondicionDTO dto;
            query = "SELECT id_condicion, descripcion FROM condicion ORDER BY id_condicion;";
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
    public List<Condiones_compra> getListCondicioncompra() {
        try {
            List<Condiones_compra> lista;
            Condiones_compra dto;
            query = "SELECT idcondicion_compra, descripcion FROM condicion_compras ORDER BY idcondicion_compra;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Condiones_compra();
                dto.setId_condicioncompra(rs.getInt("idcondicion_compra"));
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
    public boolean aprobar_reg_compras(reg_comprasDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE reg_compras set estado='APROBADO' where num_factura=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNum_factura());
            if (ps.executeUpdate() > 0) {
                query = "UPDATE orden_compras set estado='FACTURADO' where id_ordencompra=?";
                ps = ConexionDB.getRutaConexion().prepareStatement(query);
                ps.setInt(1, dto.getId_ordencompra());
                if (ps.executeUpdate() <= 0) {
                    ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                    return false;
                }
                query = "UPDATE recepciones set estado='FACTURADO' where id_recepcion=?";
                ps = ConexionDB.getRutaConexion().prepareStatement(query);
                ps.setInt(1, dto.getId_recepcion());
                if (ps.executeUpdate() <= 0) {
                    ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                    return false;
                }
                for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                    query = "UPDATE detalle_recepciones set estado='FACTURADO' where codigo_barra=? and id_recepcion=? ";
                    ps = ConexionDB.getRutaConexion().prepareStatement(query);
                    ps.setString(1, item.getId_mercaderia());
                    ps.setInt(2, dto.getId_recepcion());
                    if (ps.executeUpdate() <= 0) {
                        ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                        return false;
                    }
                }
                if (dto.getNombre_condicion() == "CREDIDO 30, 60, 90") {
                    for (int x = 0; x <= 3; x++) {
                        query = "INSERT INTO cuenta_pagar(num_factura, fecha_factura, id_ordencompra, nro_proveedor, vto_cuota, monto_pagar, monto_pagado, saldo)\n"
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setString(1, dto.getNum_factura());
                        ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFecha_factura()));
                        ps.setInt(3, dto.getId_ordencompra());
                        ps.setInt(4, dto.getId_proveedor());
                        ps.setDate(5, Genericos.Genericos.retornarFecha(dto.getFecha()));
                        ps.setInt(6, 0);
                        ps.setInt(7, 0);
                        ps.setInt(8, 0);
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
                    }
                } else if (dto.getNombre_condicion() == "CREDITO 15, 30, 45") {
                    for (int x = 0; x <= 3; x++) {
                        query = "INSERT INTO cuenta_pagar(num_factura, fecha_factura, id_ordencompra, nro_proveedor, vto_cuota, monto_pagar, monto_pagado, saldo)\n"
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setString(1, dto.getNum_factura());
                        ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFecha_factura()));
                        ps.setInt(3, dto.getId_ordencompra());
                        ps.setInt(4, dto.getId_proveedor());
                        ps.setInt(5, 0);
                        ps.setInt(6, 0);
                        ps.setInt(7, 0);
                        ps.setInt(8, 0);
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
                    }

                }
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;

            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            errorSQL = "Ocurrio un error al insertar el registro de condicion ";
            System.out.println(errorSQL);
            Logger.getLogger(reg_comprasDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }
}
