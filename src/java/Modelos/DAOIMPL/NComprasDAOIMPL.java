/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.NCreditoDAO;
import Modelos.DTO.EstadoDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.NCreditoDTO;
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
 * @author AspireES15
 */
public class NComprasDAOIMPL implements NCreditoDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;

    public NComprasDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean generarnotacredito(NCreditoDTO dto) {
        int idCredito;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.notas_creditos(fecha, observacion, id_estado, caracter)\n"
                    + "VALUES (?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setString(2, dto.getObservacion());
            ps.setInt(3, dto.getId_estado());
            ps.setInt(4, dto.getId_regcompras());
            ps.setString(5, "A");
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idCredito = rs.getInt("id_notascreditos");
                    for (MercaderiaDTO item : dto.getLista_mercaderias()) {
                        query = "INSERT INTO public.detalle_creditos(id_notascreditos, id_mercaderia, cantidad, monto, saldo, id_motivo)\n"
                                + "VALUES (?, ?, ?, ?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, idCredito);
                        ps.setInt(2, item.getId_mercaderia());
                        ps.setInt(3, item.getCantidad());
                        ps.setInt(4, item.getMonto());
                        ps.setInt(5, item.getSaldo());
                        ps.setInt(6, item.getId_medida());
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
            Logger.getLogger(NComprasDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean anularnotacredito(NCreditoDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "Update notas_creditos set id_estado='3' where id_notascreditos=?";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_notascreditos());
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
    public List<NCreditoDTO> recuperarnotacredito(NCreditoDTO dto) {
        List<NCreditoDTO> lista;
        NCreditoDTO item;
        List<MercaderiaDTO> listaMercaderia;
        MercaderiaDTO itemMercaderia;
        try {

            query = "select n.id_notascreditos, n.fecha, n.observacion, n.id_estado, deta.id_mercaderia, deta.cantidad, deta.monto, deta.saldo, deta.id_motivo\n"
                    + "from notas_creditos n, detalle_creditos deta, estados e, mercaderias m, motivos mo\n"
                    + "where n.id_notascreditos=deta.id_notascreditos and n.id_estado=e.id_estado and m.id_mercaderia=deta.id_mercaderia \n"
                    + "and mo.id_motivo=deta.id_motivo";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_notascreditos());
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            listaMercaderia = new ArrayList<>();
            item = new NCreditoDTO();

            while (rs.next()) {
                item.setId_notascreditos(rs.getInt("id_notascreditos"));
                item.setFecha(Genericos.Genericos.retornarFechaddMMyyyy(rs.getDate("fecha")));
                item.setObservacion(rs.getString("observacion"));
                item.setFecha(rs.getDate("fecha").toString());
                item.setId_estado(rs.getInt("id_estado"));

                itemMercaderia = new MercaderiaDTO();
                itemMercaderia.setId_mercaderia(rs.getInt("id_mercaderia"));
                itemMercaderia.setDescripcion(rs.getString("descripcion"));
                itemMercaderia.setCantidad(rs.getInt("cantidad"));
                itemMercaderia.setMonto(rs.getInt("monto"));
                itemMercaderia.setSaldo(rs.getInt("saldo"));
                itemMercaderia.setId_motivo(rs.getInt("id_motivo"));
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
    public List<reg_comprasDTO> getListRegistraCompras() {
        try {
            List<reg_comprasDTO> lista;
            reg_comprasDTO dto;
            query = "SELECT re.id_nota, re.fecha, reg.num_factura, pro.nombre \n"
                    + "FROM reg_compras re, orden_compras o, proveedores pro where  reg.id_ordencompra=o.id_ordencompra and pro.id_proveedor=o.id_proveedor and caracter='A'";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new reg_comprasDTO();
//                dto.setId_pedido(rs.getInt("id_pedido"));
//                dto.setFecha(rs.getDate("fecha").toString());
//                dto.setNombre_sucursal(rs.getString("descripcion"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<reg_comprasDTO> recuperarNotaCredito(NCreditoDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
