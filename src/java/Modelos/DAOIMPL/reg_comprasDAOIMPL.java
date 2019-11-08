/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.reg_comprasDAO;
import Modelos.DTO.EstadoDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.OrdenDTO;
import Modelos.DTO.reg_comprasDTO;
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
 * @author GuruW10P
 */
public class reg_comprasDAOIMPL implements reg_comprasDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;

    public reg_comprasDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean reg_compras(reg_comprasDTO dto) {
        int reg_compras;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO reg_compras(\n"
                    + "            num_factura, fecha_factura, id_condicion, id_estado, \n"
                    + "            id_usuario, id_ordencompra, caracter_1)\n"
                    + "    VALUES (?, ?, ?, ?, \n"
                    + "            ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getNum_factura());
            ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFecha_factura()));
            ps.setInt(3, 1);
            ps.setInt(4, dto.getId_estado());
            ps.setInt(5, dto.getId_usuario());
            ps.setInt(6, dto.getId_ordencompra());
            ps.setString(7, "A");
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    reg_compras = rs.getInt("id_regcompra");
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO detalle_compras(\n"
                                + "            id_regcompra, id_mercaderia, cantidad, monto_total, \n"
                                + "            precio)\n"
                                + "    VALUES (?, ?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, reg_compras);
                        ps.setString(2, item.getId_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, item.getTotal());
                        ps.setInt(5, item.getPrecio());
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
                        query = "UPDATE stock\n"
                                + "   SET cantidad=(select cantidad from stock where id_mercaderia=?) + ?\n"
                                + " WHERE id_mercaderia=? and id_deposito=?;";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setString(1, item.getId_mercaderia());
                        ps.setInt(2, item.getCantidad());
                        ps.setString(3, item.getId_mercaderia());
                        ps.setInt(4, 1);
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
//                        query = "INSERT INTO public.cuenta_cobrar(id_venta, fecha, monto, caracter)\n"
//                                + "VALUES (?, ?, ?, ?);";
//                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
//                        ps.setInt(1, reg_compras);
//                        ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFecha_factura()));
//                        ps.setInt(3, item.getTotal());
//                        ps.setString(4, "A");
//                        if (ps.executeUpdate() > 0) {
//                            ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
//                        }
                        query = "UPDATE orden_compras set caracter='I' where id_ordencompra=?";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, dto.getId_ordencompra());
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
            query = "Update reg_compras set id_estado='3' where id_regcompra=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_regcompra());
            if (ps.executeUpdate() > 0) {
                query = "UPDATE orden_compras set caracter='A' where id_ordencompra=?";
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

            query = "SELECT reg.num_factura, reg.fecha_factura, ord.id_ordencompra, reg.id_estado, ds.id_mercaderia, mer.descripcion AS Mercaderia, ds.cantidad, ds.monto_total, ds.precio\n"
                    + "FROM reg_compras reg\n"
                    + "INNER JOIN usuarios usu ON reg.id_usuario=usu.id_usuario\n"
                    + "INNER JOIN orden_compras ord ON reg.id_ordencompra=ord.id_ordencompra\n"
                    + "INNER JOIN detalle_compras ds ON reg.id_regcompra=ds.id_regcompra\n"
                    + "INNER JOIN mercaderias mer ON ds.id_mercaderia=mer.id_mercaderia\n"
                    + "WHERE reg.id_regcompra=? and reg.id_estado=1 and reg.caracter_1='A'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_regcompra());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
            item = new reg_comprasDTO();

            while (rs.next()) {
                item.setNum_factura(rs.getInt("num_factura"));
                item.setFecha_factura(rs.getString("fecha_factura"));
                item.setId_ordencompra(rs.getInt("id_ordencompra"));
                item.setId_estado(rs.getInt("id_estado"));

                itemMercaderia = new MercaderiaDTO();
                itemMercaderia.setId_mercaderia(rs.getString("id_mercaderia"));
                itemMercaderia.setDescripcion(rs.getString("Mercaderia"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                itemMercaderia.setTotal(rs.getInt("monto_total"));
                itemMercaderia.setPrecio(rs.getInt("precio"));
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
            query = "SELECT reg.id_regcompra, reg.num_factura, reg.fecha_factura, pro.nombre AS Provee\n"
                    + "FROM reg_compras reg\n"
                    + "INNER JOIN orden_compras ord ON reg.id_ordencompra=ord.id_ordencompra\n"
                    + "INNER JOIN proveedores pro ON ord.id_proveedor=pro.id_proveedor\n"
                    + "WHERE reg.id_estado=1 and reg.caracter_1='A'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new reg_comprasDTO();
                dto.setId_regcompra(rs.getInt("id_regcompra"));
                dto.setNum_factura(rs.getInt("num_factura"));
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
            query = "select o.id_ordencompra,\n"
                    + "deta.id_mercaderia, m.descripcion as mercaderia, deta.cantidad, deta.precio\n"
                    + "from orden_compras o, estados e, pedidos p, proveedores pro, detalle_ordencompras deta, mercaderias m\n"
                    + "where p.id_pedido = o.id_pedido and pro.id_proveedor=o.id_proveedor and e.id_estado=o.id_estado and \n"
                    + "o.id_ordencompra=deta.id_ordencompra and m.id_mercaderia=deta.id_mercaderia and o.id_ordencompra=?";
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
                System.out.println("Llegueeeee");
                itemMercaderia = new MercaderiaDTO();
                itemMercaderia.setId_mercaderia(rs.getString("id_mercaderia"));
                itemMercaderia.setDescripcion(rs.getString("mercaderia"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                itemMercaderia.setPrecio(rs.getInt("precio"));
                itemMercaderia.setTotal(rs.getInt("precio") * rs.getInt("precio"));
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
            query = "SELECT o.id_ordencompra, o.fecha, p.id_pedido, pro.nombre FROM proveedores pro, pedidos p, orden_compras o\n"
                    + "                                  where p.id_pedido=o.id_pedido and pro.id_proveedor=o.id_proveedor and o.caracter='A'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new OrdenDTO();
                dto.setId_orden(rs.getInt("id_ordencompra"));
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
}
