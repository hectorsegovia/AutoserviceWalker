/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.ClienteDTO;
import Modelos.DTO.CobrosDTO;
import Modelos.DTO.CondicionDTO;
import Modelos.DTO.Factura_ventaDTO;
import Modelos.DTO.MercaderiaDTO;
import Modelos.DTO.tipo_cobroDTO;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface Factura_ventaDAO {
    public boolean generarfactura( Factura_ventaDTO dto);
    public boolean anularfactura( Factura_ventaDTO dto);
    public List<Factura_ventaDTO> recuperarFactura(Factura_ventaDTO dto);
    public List<CondicionDTO> getListCondicion();
    public List<ClienteDTO> getListOCliente();
    public List<Factura_ventaDTO> getListConsultarTodo(); 
    public List<MercaderiaDTO> getListMercaderia();
    public List<tipo_cobroDTO> getListTipoCobro();
    public List<Factura_ventaDTO> getListidgenerado();
    public List<Factura_ventaDTO> getListnumerofactura();

}
