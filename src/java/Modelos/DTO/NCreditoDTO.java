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
public class NCreditoDTO {

    private Integer bandera;
    private Integer id_notascreditos;
    private String fecha;
    private String observacion;
    private Integer id_estado;
    private Integer id_regcompras;

    private List<MercaderiaDTO> lista_mercaderias;

    public Integer getId_regcompras() {
        return id_regcompras;
    }

    public void setId_regcompras(Integer id_regcompras) {
        this.id_regcompras = id_regcompras;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_notascreditos() {
        return id_notascreditos;
    }

    public void setId_notascreditos(Integer id_notascreditos) {
        this.id_notascreditos = id_notascreditos;
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

    public Integer getId_estado() {
        return id_estado;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public List<MercaderiaDTO> getLista_mercaderias() {
        return lista_mercaderias;
    }

    public void setLista_mercaderias(List<MercaderiaDTO> lista_mercaderias) {
        this.lista_mercaderias = lista_mercaderias;
    }

}
