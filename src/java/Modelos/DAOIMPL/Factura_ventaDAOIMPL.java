/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.Factura_ventaDAO;
import Modelos.DTO.ClienteDTO;
import Modelos.DTO.CobrosDTO;
import Modelos.DTO.CondicionDTO;
import Modelos.DTO.Factura_ventaDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.tipo_cobroDTO;
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
public class Factura_ventaDAOIMPL implements Factura_ventaDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;

    public Factura_ventaDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean generarfactura(Factura_ventaDTO dto) {
        int idfactura;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.fac_venta(num_factura, ven_factura, fecha, id_condicion, id_usuario, id_cliente, id_timbrado, caracter, id_caja)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getNum_factura());
            ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getVen_factura()));
            ps.setDate(3, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setInt(4, dto.getId_condicion());
            ps.setInt(5, dto.getId_usuario());
            ps.setInt(6, dto.getId_cliente());
            ps.setInt(7, dto.getId_timbrado());
            ps.setString(8, "A");
            ps.setInt(9, dto.getId_caja());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idfactura = rs.getInt("id_venta");
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO public.detalle_ventas(id_venta, id_mercaderia, cantidad, ven_grab, ven_exentas, ven_iva, precio, sub_total, descuentos)\n"
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, idfactura);
                        ps.setString(2, item.getId_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, 0);
                        ps.setInt(5, 0);
                        ps.setInt(6, 0);
                        ps.setInt(7, item.getPrecio());
                        ps.setInt(8, item.getSubtotal());
                        ps.setInt(9, 0);
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
                        query = "UPDATE stock SET id_deposito=?, cantidad=(select cantidad from stock where id_mercaderia=?) - ? WHERE id_mercaderia=?;";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, 1);
                        ps.setString(2, item.getId_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setString(4, item.getId_mercaderia());
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
//                        query = "INSERT INTO public.cuenta_cobrar(id_venta, fecha, monto, caracter)\n"
//                                + "VALUES (?, ?, ?, ?);";
//                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
//                        ps.setInt(1, dto.getId_venta());
//                        ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getFecha()));
//                        ps.setInt(3, item.getSubtotal());
//                        ps.setString(4, "A");
//                        if (ps.executeUpdate() <= 0) {
//                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
//                            return false;
//                        }
                        ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                        return true;
                    }
                    ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                    return true;
                }

            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Factura_ventaDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean anularfactura(Factura_ventaDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "Update fac_venta set caracter='A' where id_ordencompra=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_venta());
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
    public List<Factura_ventaDTO> recuperarFactura(Factura_ventaDTO dto) {
        List<Factura_ventaDTO> lista;
        Factura_ventaDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {
            query = "select f.id_venta, f.num_factura, f.ven_factura, f.fecha, f.id_condicion, c.descripcion as condicion, f.id_cliente, cli.nombre, f.id_timbrado,\n"
                    + "deta.id_mercaderia, m.descripcion as mercaderia, deta.cantidad, deta.ven_iva, deta.precio, deta.sub_total, deta.descuentos \n"
                    + "from detalle_ventas deta, fac_venta f, mercaderias m, condicion c, clientes cli, timbrados t\n"
                    + "where f.id_venta=deta.id_venta and f.id_condicion=c.id_condicion and cli.id_cliente=f.id_cliente and m.id_mercaderia=deta.id_mercaderia and f.id_venta=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_venta());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
            item = new Factura_ventaDTO();
            while (rs.next()) {
//              item.setFecha(Genericos.retornarFechaddMMyyyy(rs.getDate("fecha")));
                item.setId_venta(rs.getInt("id_venta"));
                item.setNum_factura(rs.getInt("num_factura"));
                item.setVen_factura(rs.getDate("ven_factura").toString());
                item.setFecha(rs.getDate("fecha").toString());
                item.setId_condicion(rs.getInt("id_condicion"));
                item.setNombre_condicion(rs.getString("condicion"));
                item.setId_cliente(rs.getInt("id_cliente"));
                item.setNombre_cliente(rs.getString("nombre"));
                item.setId_timbrado(rs.getInt("id_timbrado"));
                itemMercaderia = new MercaderiaDTO();
                itemMercaderia.setId_mercaderia(rs.getString("id_mercaderia"));
                itemMercaderia.setDescripcion(rs.getString("mercaderia"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                itemMercaderia.setVen_iva(rs.getInt("ven_iva"));
                itemMercaderia.setPrecio(rs.getInt("precio"));
                itemMercaderia.setDescuentos(rs.getInt("sub_total"));
                itemMercaderia.setSubtotal(rs.getInt("descuentos"));
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
    public List<ClienteDTO> getListOCliente() {
        try {
            List<ClienteDTO> lista;
            ClienteDTO dto;
            query = "SELECT id_cliente, nombre FROM clientes ORDER BY id_cliente;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ClienteDTO();
                dto.setId_cliente(rs.getInt("id_cliente"));
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
    public List<Factura_ventaDTO> getListConsultarTodo() {
        try {
            List<Factura_ventaDTO> lista;
            Factura_ventaDTO dto;
            query = "select f.id_venta, f.num_factura, f.fecha, cli.nombre\n"
                    + "from fac_venta f, clientes cli\n"
                    + "where f.id_cliente=cli.id_cliente and f.caracter='A'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Factura_ventaDTO();
                dto.setId_venta(rs.getInt("id_venta"));
                dto.setFecha(rs.getString("fecha"));
                dto.setNum_factura(rs.getInt("num_factura"));
                dto.setNombre_cliente(rs.getString("nombre"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<MercaderiaDTO> getListMercaderia() {
        try {
            List<MercaderiaDTO> lista;
            MercaderiaDTO dto;
            query = "SELECT id_mercaderia, descripcion, precio FROM mercaderias ORDER BY id_mercaderia;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MercaderiaDTO();
                dto.setId_mercaderia(rs.getString("id_mercaderia"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setPrecio(rs.getInt("precio"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<tipo_cobroDTO> getListTipoCobro() {
        try {
            List<tipo_cobroDTO> lista;
            tipo_cobroDTO dto;

            query = "SELECT id_ticobro, descripcion FROM tipo_cobros ORDER BY id_ticobro;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new tipo_cobroDTO();

                dto.setId_ticobro(rs.getInt("id_ticobro"));
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
    public List<Factura_ventaDTO> getListidgenerado() {
        try {
            List<Factura_ventaDTO> lista;
            Factura_ventaDTO dto;
            query = "SELECT COALESCE (MAX(id_venta)) + 1 AS id FROM fac_venta;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Factura_ventaDTO();
                dto.setId_codigoultimo(rs.getInt("id"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Factura_ventaDTO> getListnumerofactura() {
        try {
            List<Factura_ventaDTO> lista;
            Factura_ventaDTO dto;
            query = "SELECT COALESCE (MAX(num_factura)) + 1 AS factura FROM fac_venta;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Factura_ventaDTO();
                dto.setNum_factura(rs.getInt("factura"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
