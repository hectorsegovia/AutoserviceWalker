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
public class RecepcionDTO {
    private Integer bandera;
    private Integer id_recepcion;
    private String fecha;
    private Integer id_ordencompra;
    private Integer id_usuario;
    private String observacion;
    private String estado;
    private String nombre_proveedor;
    
    private List<MercaderiaDTO> lista_mercaderias;

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }

    public Integer getId_ordencompra() {
        return id_ordencompra;
    }

    public void setId_ordencompra(Integer id_ordencompra) {
        this.id_ordencompra = id_ordencompra;
    }

  

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_recepcion() {
        return id_recepcion;
    }

    public void setId_recepcion(Integer id_recepcion) {
        this.id_recepcion = id_recepcion;
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

    public List<MercaderiaDTO> getLista_mercaderias() {
        return lista_mercaderias;
    }

    public void setLista_mercaderias(List<MercaderiaDTO> lista_mercaderias) {
        this.lista_mercaderias = lista_mercaderias;
    }
    
    
}
