/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAOIMPL;

import Genericos.ConexionDB;
import Modelos.DAO.MercaderiaDAO;
import Modelos.DTO.Categoria_productoDTO;
import Modelos.DTO.FamiliasDTO;
import Modelos.DTO.ImpuestoDTO;
import Modelos.DTO.MarcasDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.SaborDTO;
import Modelos.DTO.SubcategoriaDTO;
import Modelos.DTO.tipo_familiaDTO;
import Modelos.DTO.unidad_medidaDTO;
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
public class MercaderiasDAOIMPL implements MercaderiaDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    String query;
    String errorSQL;

    public MercaderiasDAOIMPL() {
        ConexionDB.getInstancia();
    }

    @Override
    public List<MarcasDTO> getListMarcas() {
        try {
            List<MarcasDTO> lista;
            MarcasDTO dto;
            query = "SELECT id_marca, descripcion FROM marcas ORDER BY id_marca;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MarcasDTO();
                dto.setId_marca(rs.getInt("id_marca"));
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
    public List<SaborDTO> getListSabor() {
        try {
            List<SaborDTO> lista;
            SaborDTO dto;
            query = "SELECT id_sabor, descripcion FROM sabores ORDER BY id_sabor;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new SaborDTO();
                dto.setId_sabor(rs.getInt("id_sabor"));
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
    public List<Categoria_productoDTO> getListCategoria() {
        try {
            List<Categoria_productoDTO> lista;
            Categoria_productoDTO dto;
            query = "SELECT id_categoria, descripcion FROM categoria_productos ORDER BY id_categoria;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new Categoria_productoDTO();
                dto.setId_categoria(rs.getInt("id_categoria"));
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
    public List<FamiliasDTO> getListFamilias() {
        try {
            List<FamiliasDTO> lista;
            FamiliasDTO dto;
            query = "SELECT id_familia, descripcion FROM familias ORDER BY id_familia;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new FamiliasDTO();
                dto.setId_familia(rs.getInt("id_familia"));
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
    public List<SubcategoriaDTO> getListSubcategorias() {
        try {
            List<SubcategoriaDTO> lista;
            SubcategoriaDTO dto;
            query = "SELECT id_subcategoria, descripcion FROM subcategoria ORDER BY id_subcategoria;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new SubcategoriaDTO();
                dto.setId_subcategoria(rs.getInt("id_subcategoria"));
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
    public List<tipo_familiaDTO> getListTipofamilia() {
        try {
            List<tipo_familiaDTO> lista;
            tipo_familiaDTO dto;
            query = "SELECT id_tifamilia, descripcion FROM tipo_familias ORDER BY id_tifamilia;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new tipo_familiaDTO();
                dto.setId_tifamilia(rs.getInt("id_tifamilia"));
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
    public List<unidad_medidaDTO> getListUnidadmedida() {
        try {
            List<unidad_medidaDTO> lista;
            unidad_medidaDTO dto;
            query = "SELECT id_medida, descripcion FROM unidad_medidas ORDER BY id_medida;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new unidad_medidaDTO();
                dto.setId_medida(rs.getInt("id_medida"));
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
    public List<ImpuestoDTO> getListImpuestos() {
        try {
            List<ImpuestoDTO> lista;
            ImpuestoDTO dto;
            query = "SELECT id_impuesto, descripcion FROM impuestos ORDER BY id_impuesto;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new ImpuestoDTO();
                dto.setId_impuesto(rs.getInt("id_impuesto"));
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
    public boolean agregar(MercaderiaDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "INSERT INTO public.mercaderias(codigo_barra, descripcion, precio, id_marca, id_medida, \n"
                    + "id_sabor, id_impuesto, id_categoria, unidad, id_familia, id_subcategoria, id_tifamilia, caracter)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getCodigo_barra());
            ps.setString(2, dto.getDescripcion());
            ps.setInt(3, dto.getPrecio());
            ps.setInt(4, dto.getId_marca());
            ps.setInt(5, dto.getId_medida());
            ps.setInt(6, dto.getId_sabor());
            ps.setInt(7, dto.getId_impuesto());
            ps.setInt(8, dto.getId_categoria());
            ps.setInt(9, dto.getUnidad());
            ps.setInt(10, dto.getId_familia());
            ps.setInt(11, dto.getId_subcategoria());
            ps.setInt(12, dto.getId_tifamilia());
            ps.setString(13, "A");
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
    public boolean modificar(MercaderiaDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE public.mercaderias SET codigo_barra=?, descripcion=?, precio=?, id_marca=?, \n"
                    + "id_medida=?, id_sabor=?, id_impuesto=?, id_categoria=?, unidad=?, \n"
                    + "id_familia=?, id_subcategoria=?, id_tifamilia=?, caracter=?\n"
                    + "WHERE id_mercaderia=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setString(1, dto.getCodigo_barra());
            ps.setString(2, dto.getDescripcion());
            ps.setInt(3, dto.getPrecio());
            ps.setInt(4, dto.getId_marca());
            ps.setInt(5, dto.getId_medida());
            ps.setInt(6, dto.getId_sabor());
            ps.setInt(7, dto.getId_impuesto());
            ps.setInt(8, dto.getId_categoria());
            ps.setInt(9, dto.getUnidad());
            ps.setInt(10, dto.getId_familia());
            ps.setInt(11, dto.getId_subcategoria());
            ps.setInt(12, dto.getId_tifamilia());
            ps.setString(13, "A");
            ps.setInt(14, dto.getId_mercaderia());
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
            Logger.getLogger(MercaderiasDAOIMPL.class.getName()).log(Level.SEVERE, null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public boolean eliminar(MercaderiaDTO dto) {
        try {
            ConexionDB.Transaccion(ConexionDB.TR.INICIAR);
            query = "UPDATE mercaderias SET caracter='I' where id_mercaderia=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, dto.getId_mercaderia());
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
            Logger.getLogger(SucursalDAOIMP.class.getName()).log(Level.SEVERE,
                    null, ex);
            ConexionDB.Transaccion(ConexionDB.TR.CANCELAR);
            return false;
        }
    }

    @Override
    public List<MercaderiaDTO> consultarTodos() {
        try {
            List<MercaderiaDTO> lista;
            MercaderiaDTO dto;
            query = "select mer.id_mercaderia, mer.codigo_barra, (mer.descripcion||' '||mar.descripcion) as mercaderia, mer.precio, mar.id_marca \n"
                    + "from mercaderias mer, marcas mar\n"
                    + "where mar.id_marca=mer.id_marca and mer.caracter='A' order by mer.id_mercaderia;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MercaderiaDTO();
                dto.setId_mercaderia(rs.getInt("id_mercaderia"));
                dto.setCodigo_barra(rs.getString("codigo_barra"));
                dto.setDescripcion(rs.getString("mercaderia"));
                dto.setPrecio(rs.getInt("precio"));
                dto.setId_marca(rs.getInt("id_marca"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<MercaderiaDTO> consultarSegunId(Integer id) {
        try {
            List<MercaderiaDTO> lista;
            MercaderiaDTO dto;
            query = "SELECT id_mercaderia, codigo_barra, descripcion, precio, id_marca, id_medida, id_sabor, id_impuesto, id_categoria, unidad, id_familia, id_subcategoria, \n"
                    + "id_tifamilia\n"
                    + "FROM public.mercaderias where id_mercaderia=?;";
            ps = ConexionDB.getRutaConexion().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                dto = new MercaderiaDTO();
                dto.setId_mercaderia(rs.getInt("id_mercaderia"));
                dto.setCodigo_barra(rs.getString("codigo_barra"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setPrecio(rs.getInt("precio"));
                dto.setId_marca(rs.getInt("id_marca"));
                dto.setId_medida(rs.getInt("id_medida"));
                dto.setId_sabor(rs.getInt("id_sabor"));
                dto.setId_impuesto(rs.getInt("id_impuesto"));
                dto.setId_categoria(rs.getInt("id_categoria"));
                dto.setUnidad(rs.getInt("unidad"));
                dto.setId_familia(rs.getInt("id_familia"));
                dto.setId_subcategoria(rs.getInt("id_subcategoria"));
                dto.setId_tifamilia(rs.getInt("id_tifamilia"));
                lista.add(dto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}
