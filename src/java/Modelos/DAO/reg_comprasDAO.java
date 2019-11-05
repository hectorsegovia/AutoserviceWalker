/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.EstadoDTO;
import Modelos.DTO.OrdenDTO;
import Modelos.DTO.reg_comprasDTO;
import java.util.List;

/**
 *
 * @author GuruW10P
 */
public interface reg_comprasDAO {
    
    public boolean reg_compras( reg_comprasDTO dto);
    public boolean anular_reg_compras( reg_comprasDTO dto);
    public List<reg_comprasDTO> recuperar_reg_compras(reg_comprasDTO dto);
    public List<OrdenDTO> recuperar_OrdenCompras(reg_comprasDTO dto);
    public List<OrdenDTO> getListOrdenCompras();
    public List<EstadoDTO> getListEstado();
    public List<reg_comprasDTO> getListConsultarTodo();
    
}
