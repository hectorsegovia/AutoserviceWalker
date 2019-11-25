/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DTO;

import java.util.List;

/**
 *
 * @author HectorSegovia
 */
public class notacreditoCompraDTO {
    private Integer id_notacreditos_provee;
    private Integer bandera;
    private String reg_compras_num_factura;
    private String reg_compras_fecha_factura;
    private Integer id_ordencompra;
    private String num_factura;
    private String fecha_factura;
    private String fecha;
    private String observacion;
    private String estado; 
    private Integer importe; 
    
    private List<MercaderiaDTO> lista_mercaderias;

    public List<MercaderiaDTO> getLista_mercaderias() {
        return lista_mercaderias;
    }

    public void setLista_mercaderias(List<MercaderiaDTO> lista_mercaderias) {
        this.lista_mercaderias = lista_mercaderias;
    }
    
    public Integer getImporte() {
        return importe;
    }

    public void setImporte(Integer importe) {
        this.importe = importe;
    }

    
    
    public Integer getId_notacreditos_provee() {
        return id_notacreditos_provee;
    }

    public void setId_notacreditos_provee(Integer id_notacreditos_provee) {
        this.id_notacreditos_provee = id_notacreditos_provee;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public String getReg_compras_num_factura() {
        return reg_compras_num_factura;
    }

    public void setReg_compras_num_factura(String reg_compras_num_factura) {
        this.reg_compras_num_factura = reg_compras_num_factura;
    }

    public String getReg_compras_fecha_factura() {
        return reg_compras_fecha_factura;
    }

    public void setReg_compras_fecha_factura(String reg_compras_fecha_factura) {
        this.reg_compras_fecha_factura = reg_compras_fecha_factura;
    }

    public Integer getId_ordencompra() {
        return id_ordencompra;
    }

    public void setId_ordencompra(Integer id_ordencompra) {
        this.id_ordencompra = id_ordencompra;
    }

    public String getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(String num_factura) {
        this.num_factura = num_factura;
    }

    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
