/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.NotaCreditoDAO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.notacreditoCompraDTO;
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
 * @author HectorSegovia
 */
public class NotaCreditocompraDAOIMPL implements NotaCreditoDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;

    public NotaCreditocompraDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean generarnotacreditocompra(notacreditoCompraDTO dto) {
        int num_notacredito;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO notas_creditos_compras(reg_compras_num_factura, reg_compras_fecha_factura, id_ordencompra, num_factura, fecha_factura, fecha, observacion, \n"
                    + "importe, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getReg_compras_num_factura());
            ps.setDate(2, Genericos.Genericos.retornarFecha(dto.getReg_compras_fecha_factura()));
            ps.setInt(3, dto.getId_ordencompra());
            ps.setString(4, dto.getNum_factura());
            ps.setDate(5, Genericos.Genericos.retornarFecha(dto.getFecha_factura()));
            ps.setDate(6, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setString(7, dto.getObservacion());
            ps.setInt(8, dto.getImporte());
            ps.setString(9, dto.getEstado());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    num_notacredito = rs.getInt("id_notacreditos_provee");
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO detalle_notacreditos_compras(id_notacreditos_provee, codigo_barra, cantidad, precio, id_impuesto)\n"
                                + "VALUES (?, ?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, num_notacredito);
                        ps.setString(2, item.getId_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, item.getPrecio());
                        ps.setInt(5, item.getId_impuesto());
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
            Logger.getLogger(NotaCreditocompraDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean anularnotacreditocompra(notacreditoCompraDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "Update notas_creditos_compras set estado='ANULADO' where id_notacreditos_provee=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_notacreditos_provee());
            if (ps.executeUpdate() > 0) {
//                query = "UPDATE notas_creditos_compras set estado='APROBADO' where id_ordencompra=?";
//                ps = ConexionDB.getRutaConexion().prepareStatement(query);
//                ps.setInt(1, dto.getId_ordencompra());
//                if (ps.executeUpdate() <= 0) {
//                    ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
//                    return false;
//                }
//                for (MercaderiaDTO item : dto.getLista_mercaderias()) {
//                    query = "UPDATE stock\n"
//                            + "   SET cantidad=(select cantidad from stock where codigo_barra=?) - ?\n"
//                            + " WHERE codigo_barra=? and id_deposito=?;";
//                    ps = ConexionDB.getRutaConexion().prepareStatement(query);
//                    ps.setString(1, item.getId_mercaderia());
//                    ps.setInt(2, item.getCantidad());
//                    ps.setString(3, item.getId_mercaderia());
//                    ps.setInt(4, 1);
//                    if (ps.executeUpdate() <= 0) {
//                        ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
//                        return false;
//                    }
//                }
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
    public boolean aprobarnotacreditocompra(notacreditoCompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<notacreditoCompraDTO> recuperarnotacreditocompra(notacreditoCompraDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<reg_comprasDTO> getListFacturasCompras() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<reg_comprasDTO> recuperarRegistrarcompra(notacreditoCompraDTO dto) {
        List<reg_comprasDTO> lista;
        reg_comprasDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {
            query = "select reg.num_factura, reg.fecha_factura, reg.id_ordencompra, ds.codigo_barra, mer.descripcion as mercaderias, mer.precio_compra, ds.cantidad, im.descripcion AS impuesto,\n"
                    + "(case when im.descripcion = 10 then \n"
                    + "(mer.precio_compra * ds.cantidad)\n"
                    + "else\n"
                    + "'0' end) as \"IVA 10%\",\n"
                    + "(case when im.descripcion = 5 then \n"
                    + "(mer.precio_compra * ds.cantidad)\n"
                    + "else\n"
                    + "'0' end) as \"IVA 5%\",\n"
                    + "(case when im.descripcion = 0 then \n"
                    + "(mer.precio_compra * ds.cantidad)\n"
                    + "else\n"
                    + "'0' end) as \"EXENTA\"\n"
                    + "FROM reg_compras reg \n"
                    + "INNER JOIN detalle_compras ds ON reg.num_factura=ds.num_factura\n"
                    + "INNER JOIN mercaderias mer ON ds.codigo_barra=mer.codigo_barra\n"
                    + "INNER JOIN impuestos im ON im.id_impuesto=mer.id_impuesto\n"
                    + "WHERE reg.num_factura='777777' and reg.estado='APROBADO'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNum_factura());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
            item = new reg_comprasDTO();

            while (rs.next()) {
//              item.setFecha(Genericos.retornarFechaddMMyyyy(rs.getDate("fecha")));
                item.setNum_factura(rs.getString("num_factura"));
                item.setFecha_factura(rs.getString("fecha_factura"));
                item.setId_ordencompra(rs.getInt("id_ordencompra"));
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
}
