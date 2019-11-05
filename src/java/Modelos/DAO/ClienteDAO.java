/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.CiudadesDTO;
import Modelos.DTO.ClienteDTO;
import Modelos.DTO.Tipo_clienteDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface ClienteDAO extends OperacionesSQL<ClienteDTO>{
     public List<CiudadesDTO> getListCiudades();
     public List<Tipo_clienteDTO> getListTipoCliente();
}
