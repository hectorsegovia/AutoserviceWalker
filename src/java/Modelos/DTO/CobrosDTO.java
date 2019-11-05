/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DTO;

import java.util.List;

/**
 *
 * @author AspireES15
 */
public class CobrosDTO {
    private Integer bandera;
    private Integer id_cobro;
    private String fecha;
    private Integer id_usuario;
    private Integer id_sucursal;
    private Integer id_venta;
    private Integer id_caja;
    private Integer id_apertura;
    
    private List<tipo_cobroDTO> lista_tipocobros;

    public Integer getId_caja() {
        return id_caja;
    }

    public void setId_caja(Integer id_caja) {
        this.id_caja = id_caja;
    }

    public Integer getId_apertura() {
        return id_apertura;
    }

    public void setId_apertura(Integer id_apertura) {
        this.id_apertura = id_apertura;
    }

    
    public List<tipo_cobroDTO> getLista_tipocobros() {
        return lista_tipocobros;
    }

    public void setLista_tipocobros(List<tipo_cobroDTO> lista_tipocobros) {
        this.lista_tipocobros = lista_tipocobros;
    }
    
    
    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_cobro() {
        return id_cobro;
    }

    public void setId_cobro(Integer id_cobro) {
        this.id_cobro = id_cobro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Integer id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public Integer getId_venta() {
        return id_venta;
    }

    public void setId_venta(Integer id_venta) {
        this.id_venta = id_venta;
    }
    
    
            
}
