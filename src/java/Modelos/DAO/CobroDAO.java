/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.CobrosDTO;
import Modelos.DTO.SucursalDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface CobroDAO {
        public boolean generarcobros( CobrosDTO dto);
        public List<SucursalDTO> getListSucursal();
}
