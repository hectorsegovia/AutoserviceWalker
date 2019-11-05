/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.CargoDTO;
import Modelos.DTO.CiudadesDTO;
import Modelos.DTO.EmpleadosDTO;
import Modelos.DTO.SucursalDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface EmpleadosDAO extends OperacionesSQL<EmpleadosDTO>{
     public List<CargoDTO> getListCargos();
      public List<CiudadesDTO> getListCiudades();    
       public List<SucursalDTO> getListSucursal();
}
