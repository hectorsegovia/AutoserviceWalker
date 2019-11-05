/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;


import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.MotivoDTO;
import Modelos.DTO.SucursalDTO;
import Modelos.DTO.ajustesDTO;
import java.util.List;

/**
 *
 * @author GuruW10P
 */
public interface ajustesDAO {
    public boolean generarAjustes( ajustesDTO dto);
    public List<SucursalDTO> getListConsultarSucursal();
    public List<MotivoDTO> getListConsultarMotivo();
    public List<MercaderiaDTO> getListConsultarMercaderia();
    
}
