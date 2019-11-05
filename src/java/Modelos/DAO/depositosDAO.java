/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.SucursalDTO;
import Modelos.DTO.depositoDTO;
import java.util.List;

/**
 *
 * @author GuruW10P
 */
public interface depositosDAO extends OperacionesSQL<depositoDTO>{
    public List<SucursalDTO> getListSucursales();
    
}
