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
public class ArqueoDTO {
    private Integer bandera;
    private Integer id_arqueo;
    private String observacion;
    private Integer monto_arqueo;
    private String fecha;
    private Integer id_usuario;
    private Integer id_sucursal;
    private Integer id_caja;
    private Integer id_apertura;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_arqueo() {
        return id_arqueo;
    }

    public void setId_arqueo(Integer id_arqueo) {
        this.id_arqueo = id_arqueo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getMonto_arqueo() {
        return monto_arqueo;
    }

    public void setMonto_arqueo(Integer monto_arqueo) {
        this.monto_arqueo = monto_arqueo;
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
    
    
}
