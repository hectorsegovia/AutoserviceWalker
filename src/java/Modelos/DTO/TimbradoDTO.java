/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DTO;

/**
 *
 * @author AspireES15
 */
public class TimbradoDTO {
    private Integer bandera;
    private Integer id_timbrado;
    private Integer timbrado_nro;
    private String fechainicio;
    private String fechafin;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_timbrado() {
        return id_timbrado;
    }

    public void setId_timbrado(Integer id_timbrado) {
        this.id_timbrado = id_timbrado;
    }

    public Integer getTimbrado_nro() {
        return timbrado_nro;
    }

    public void setTimbrado_nro(Integer timbrado_nro) {
        this.timbrado_nro = timbrado_nro;
    }
    

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }
    
    
}
