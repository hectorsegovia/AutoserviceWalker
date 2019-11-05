/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.CobroDAO;
import Modelos.DTO.CobrosDTO;
import Modelos.DTO.SucursalDTO;
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
public class CobrosDAOIMPLE implements CobroDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;

    public CobrosDAOIMPLE() {
        ConexionDB.getInstancia();
    }

    @Override
    public boolean generarcobros(CobrosDTO dto) {
        int idcobro;
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            System.out.print("Holaaa llegueee" + dto.getId_cobro() + dto.getLista_tipocobros());
            query = "INSERT INTO public.cobro(fecha_cobro, id_usuario, id_sucursal, id_venta, caracter, id_caja)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Genericos.Genericos.retornarFecha(dto.getFecha()));
            ps.setInt(2, dto.getId_usuario());
            ps.setInt(3, dto.getId_sucursal());
            ps.setInt(4, dto.getId_venta());
            ps.setString(5, "A");
            ps.setInt(6, dto.getId_caja());
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idcobro = rs.getInt("id_cobro");
                    for (tipo_cobroDTO item : dto.getLista_tipocobros()) {
                        query = "INSERT INTO public.detalle_cobro(id_cobro, id_ticobro, monto)\n"
                                + "VALUES (?, ?, ?);";
                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
                        ps.setInt(1, idcobro);
                        ps.setInt(2, item.getId_ticobro());
                        ps.setInt(3, item.getMonto());
                        if (ps.executeUpdate() <= 0) {
                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                            return false;
                        }
//                        query = "UPDATE pedidos SET id_estado='2' where id_pedido=?;";
//                        ps = ConexionDB.getRutaConexion().prepareStatement(query);
//                        ps.setInt(1, dto.getId_pedido());
//                        if (ps.executeUpdate() <= 0) {
//                            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
//                            return false;
//                        }
                        ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                        return true;
                    }
                }

            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CobrosDAOIMPLE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public List<SucursalDTO> getListSucursal() {
        try {
            List<SucursalDTO> lista;
            SucursalDTO dto;
            query = "select id_sucursal, descripcion from sucursales order by id_sucursal;";
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
}
