/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.CiudadesDTO;
import Modelos.DTO.PaisDTO;
import Modelos.DTO.ProveedorDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface ProveedorDAO extends OperacionesSQL<ProveedorDTO>{
    public List<CiudadesDTO> getListCiudades();    
       public List<PaisDTO> getListPais();
}
