/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.EstadoDTO;
import Modelos.DTO.OrdenDTO;
import Modelos.DTO.PedidoCompraDTO;
import Modelos.DTO.ProveedorDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface OrdenDAO {
    public boolean generarorden( OrdenDTO dto);
    public boolean anularorden( OrdenDTO dto);
    public List<OrdenDTO> recuperarOrden(OrdenDTO dto);
    public List<EstadoDTO> getListEstado();
    public List<ProveedorDTO> getListOProveedor();
    public List<PedidoCompraDTO> getListPedido();
    public List<OrdenDTO> getListConsultarTodo(); 
    public List<PedidoCompraDTO> recuperarPedido(OrdenDTO dto);
}
