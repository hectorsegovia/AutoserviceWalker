/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.AperturacajaDTO;
import Modelos.DTO.CierreCajaDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface CierreDAO {
    public boolean agregar(CierreCajaDTO dto);
    public List<CierreCajaDTO> consultararqueo(Integer usuario, Integer sucursal, Integer caja, String fecha);
    public List<AperturacajaDTO> getListAperturaarqueo();
}
