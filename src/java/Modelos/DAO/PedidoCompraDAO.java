/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.depositoDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.PedidoCompraDTO;
import Modelos.DTO.SucursalDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface PedidoCompraDAO {
    public boolean generarpedido( PedidoCompraDTO dto);
    public boolean anularpedido( PedidoCompraDTO dto);
    public boolean aprobarpedido( PedidoCompraDTO dto);
    public List<PedidoCompraDTO> recuperarPedido(PedidoCompraDTO dto);
    public List<MercaderiaDTO> getListMercaderia();
    public List<SucursalDTO> getListSucursal();
    public List<PedidoCompraDTO> getListConsultarTodo();
    public List<PedidoCompraDTO> getListcodigo();
    
}
