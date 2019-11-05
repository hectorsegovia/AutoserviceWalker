/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.EstadoDTO;
import Modelos.DTO.NCreditoDTO;
import Modelos.DTO.reg_comprasDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface NCreditoDAO {
    public boolean generarnotacredito( NCreditoDTO dto);
    public boolean anularnotacredito( NCreditoDTO dto);
    public List<NCreditoDTO> recuperarnotacredito(NCreditoDTO dto);
    public List<EstadoDTO> getListEstado();
    public List<reg_comprasDTO> getListRegistraCompras();
    public List<reg_comprasDTO> recuperarNotaCredito(NCreditoDTO dto);

}
