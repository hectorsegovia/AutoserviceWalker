/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.ClienteDAO;
import Modelos.DTO.CiudadesDTO;
import Modelos.DTO.ClienteDTO;
import Modelos.DTO.Tipo_clienteDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AspireES15
 */
public class ClienteDAOIMPL implements ClienteDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public ClienteDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public List<CiudadesDTO> getListCiudades() {
        try {
            List<CiudadesDTO> lista;
            CiudadesDTO dto;
            query = "SELECT id_ciudad, descripcion FROM ciudades ORDER BY id_ciudad;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new CiudadesDTO();
                dto.setId_ciudad(rs.getInt("id_ciudad"));
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
    public List<Tipo_clienteDTO> getListTipoCliente() {
        try {
            List<Tipo_clienteDTO> lista;
            Tipo_clienteDTO dto;
            query = "SELECT id_ticliente, descripcion FROM tipo_clientes ORDER BY id_ticliente;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Tipo_clienteDTO();
                dto.setId_ticliente(rs.getInt("id_ticliente"));
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
    public boolean agregar(ClienteDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.clientes(nombre, ruc, id_ciudad, telefono, direccion, id_ticliente, caracter)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getRuc());
            ps.setInt(3, dto.getId_ciudad());
            ps.setString(4, dto.getTelefono());
            ps.setString(5, dto.getDireccion());
            ps.setInt(6, dto.getTicliente());
            ps.setString(7,"A");
            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            errorSQL = "Ocurrio un error al insertar el registro de condicion ";
            System.out.println(errorSQL);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean modificar(ClienteDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE public.clientes SET  nombre=?, ruc=?, id_ciudad=?, telefono=?, direccion=?, id_ticliente=?, caracter=?\n"
                    + " WHERE id_cliente=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getNombre());
            ps.setString(2, dto.getRuc());
            ps.setInt(3, dto.getId_ciudad());
            ps.setString(4, dto.getTelefono());
            ps.setString(5, dto.getDireccion());
            ps.setInt(6, dto.getTicliente());
            ps.setString(7, "A");
            ps.setInt(8, dto.getId_cliente());
            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            errorSQL = "Ocurrio un error al insertar el registro de condicion ";
            System.out.println(errorSQL);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(ClienteDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE clientes set caracter='I' where id_cliente=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_cliente());
            if (ps.executeUpdate() > 0) {
                ConexionDB.Transaccion(ConexionDB.TR.CONFIRMAR);
                return true;
            } else {
                ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
                return false;
            }
        } catch (SQLException ex) {
            errorSQL = "Ocurrio un error al insertar el registro de condicion ";
            System.out.println(errorSQL);
            Logger.getLogger(ClienteDAOIMPL.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<ClienteDTO> consultarTodos() {
        try {
            List<ClienteDTO> lista;
            ClienteDTO dto;
            query = "select cli.id_cliente, cli.nombre, cli.ruc, ci.id_ciudad, ci.descripcion as ciudad, cli.telefono, ti.id_ticliente, ti.descripcion as tipo_cliente\n" +
"                    from clientes cli, ciudades ci, tipo_clientes ti \n" +
"                    where ci.id_ciudad = cli.id_ciudad and ti.id_ticliente = cli.id_ticliente and cli.caracter='A' order by cli.id_cliente";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ClienteDTO();
                dto.setId_cliente(rs.getInt("id_cliente"));
                dto.setNombre(rs.getString("nombre"));
                dto.setRuc(rs.getString("ruc"));
                dto.setNombre_ciudad(rs.getString("ciudad"));
                dto.setTelefono(rs.getString("telefono"));
                dto.setNombre_ticliente(rs.getString("tipo_cliente"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<ClienteDTO> consultarSegunId(Integer id) {
       try {
            List<ClienteDTO> lista;
            ClienteDTO dto;
            query = "select cli.id_cliente, cli.nombre, cli.ruc, ci.id_ciudad, cli.telefono, cli.direccion, ti.id_ticliente\n" +
"                    from clientes cli, ciudades ci, tipo_clientes ti \n" +
"                    where ci.id_ciudad = cli.id_ciudad and ti.id_ticliente = cli.id_ticliente and cli.id_cliente=? ";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ClienteDTO();
                dto.setId_cliente(rs.getInt("id_cliente"));
                dto.setNombre(rs.getString("nombre"));
                dto.setRuc(rs.getString("ruc"));
                dto.setId_ciudad(rs.getInt("id_ciudad"));
                dto.setTelefono(rs.getString("telefono"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setTicliente(rs.getInt("id_ticliente"));

                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    }
    
    

