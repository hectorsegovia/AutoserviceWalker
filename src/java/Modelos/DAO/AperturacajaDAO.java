/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.AperturacajaDTO;
import Modelos.DTO.CajasDTO;
import Modelos.DTO.SucursalDTO;
import Modelos.DTO.UsuarioDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface AperturacajaDAO extends OperacionesSQL<AperturacajaDTO>{
    public List<CajasDTO> getListCajas();
    public List<SucursalDTO> getListSucursal();
    public List<UsuarioDTO>getListResponsables();
}
