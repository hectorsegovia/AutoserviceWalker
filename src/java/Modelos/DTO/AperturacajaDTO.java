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
public class AperturacajaDTO {
    private Integer bandera;
    private Integer id_apertura;
    private Integer id_caja;
    private String nombre_caja;
    private Integer id_sucursal;
    private String nombre_sucursal;
    private Integer id_usuario;
    private String nombre_usuario;
    private String fecha;
    private Integer montoefectivo;
    private Integer montocheque;
    private Integer montotarjeta;
    private Integer montototal;
    private Integer id_responsable;

    public Integer getId_responsable() {
        return id_responsable;
    }

    public void setId_responsable(Integer id_responsable) {
        this.id_responsable = id_responsable;
    }
    

    public String getNombre_caja() {
        return nombre_caja;
    }

    public void setNombre_caja(String nombre_caja) {
        this.nombre_caja = nombre_caja;
    }

    public String getNombre_sucursal() {
        return nombre_sucursal;
    }

    public void setNombre_sucursal(String nombre_sucursal) {
        this.nombre_sucursal = nombre_sucursal;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    
    
    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_apertura() {
        return id_apertura;
    }

    public void setId_apertura(Integer id_apertura) {
        this.id_apertura = id_apertura;
    }

    public Integer getId_caja() {
        return id_caja;
    }

    public void setId_caja(Integer id_caja) {
        this.id_caja = id_caja;
    }

    public Integer getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Integer id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getMontoefectivo() {
        return montoefectivo;
    }

    public void setMontoefectivo(Integer montoefectivo) {
        this.montoefectivo = montoefectivo;
    }

    public Integer getMontocheque() {
        return montocheque;
    }

    public void setMontocheque(Integer montocheque) {
        this.montocheque = montocheque;
    }

    public Integer getMontotarjeta() {
        return montotarjeta;
    }

    public void setMontotarjeta(Integer montotarjeta) {
        this.montotarjeta = montotarjeta;
    }

    public Integer getMontototal() {
        return montototal;
    }

    public void setMontototal(Integer montototal) {
        this.montototal = montototal;
    }
    
    
}
