/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.notacreditoCompraDTO;
import Modelos.DTO.reg_comprasDTO;
import java.util.List;

/**
 *
 * @author HectorSegovia
 */
public interface NotaCreditoDAO {
    public boolean generarnotacreditocompra( notacreditoCompraDTO dto);
    public boolean anularnotacreditocompra( notacreditoCompraDTO dto);
    public boolean aprobarnotacreditocompra( notacreditoCompraDTO dto);
    public List<notacreditoCompraDTO> recuperarnotacreditocompra(notacreditoCompraDTO dto);
    public List<reg_comprasDTO> getListFacturasCompras();
    public List<reg_comprasDTO> recuperarRegistrarcompra(notacreditoCompraDTO dto);
}
