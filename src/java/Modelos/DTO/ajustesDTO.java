/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DTO;

import java.util.List;

/**
 *
 * @author GuruW10P
 */
public class ajustesDTO {
    private Integer bandera;
    private Integer id_ajuste;
    private String observacion;
    private String fecha_ajuste;
    private Integer id_usuario; 
    private Integer id_sucursal; 

    public Integer getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Integer id_sucursal) {
        this.id_sucursal = id_sucursal;
    }
    
    private List<MercaderiaDTO> lista_mercaderias;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_ajuste() {
        return id_ajuste;
    }

    public void setId_ajuste(Integer id_ajuste) {
        this.id_ajuste = id_ajuste;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFecha_ajuste() {
        return fecha_ajuste;
    }

    public void setFecha_ajuste(String fecha_ajuste) {
        this.fecha_ajuste = fecha_ajuste;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<MercaderiaDTO> getLista_mercaderias() {
        return lista_mercaderias;
    }

    public void setLista_mercaderias(List<MercaderiaDTO> lista_mercaderias) {
        this.lista_mercaderias = lista_mercaderias;
    }

    
}
