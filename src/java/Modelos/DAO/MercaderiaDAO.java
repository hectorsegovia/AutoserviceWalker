/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.Categoria_productoDTO;
import Modelos.DTO.FamiliasDTO;
import Modelos.DTO.ImpuestoDTO;
import Modelos.DTO.MarcasDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.SaborDTO;
import Modelos.DTO.SubcategoriaDTO;
import Modelos.DTO.tipo_familiaDTO;
import Modelos.DTO.unidad_medidaDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface MercaderiaDAO extends OperacionesSQL<MercaderiaDTO>{
    public List<MarcasDTO> getListMarcas();
    public List<SaborDTO> getListSabor();
    public List<Categoria_productoDTO> getListCategoria();
    public List<FamiliasDTO> getListFamilias();
    public List<SubcategoriaDTO> getListSubcategorias();
    public List<tipo_familiaDTO> getListTipofamilia();
    public List<unidad_medidaDTO> getListUnidadmedida();
    public List<ImpuestoDTO> getListImpuestos();
}
