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
public class CierreCajaDTO {
    private Integer bandera;
    private Integer id_cierre;
    private Integer id_usuario;
    private String nombre_usuario;
    private Integer id_sucursal;
    private String nombre_sucursal;
    private Integer id_caja;
    private String nombre_caja;
    private Integer mefectivo;
    private Integer mtarjeta;
    private Integer monto_total;
    private String fecha;
    private Integer id_apertura;

    public Integer getId_apertura() {
        return id_apertura;
    }

    public void setId_apertura(Integer id_apertura) {
        this.id_apertura = id_apertura;
    }
    

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    

    public Integer getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(Integer monto_total) {
        this.monto_total = monto_total;
    }
    

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_cierre() {
        return id_cierre;
    }

    public void setId_cierre(Integer id_cierre) {
        this.id_cierre = id_cierre;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public Integer getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Integer id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getNombre_sucursal() {
        return nombre_sucursal;
    }

    public void setNombre_sucursal(String nombre_sucursal) {
        this.nombre_sucursal = nombre_sucursal;
    }

    public Integer getId_caja() {
        return id_caja;
    }

    public void setId_caja(Integer id_caja) {
        this.id_caja = id_caja;
    }

    public String getNombre_caja() {
        return nombre_caja;
    }

    public void setNombre_caja(String nombre_caja) {
        this.nombre_caja = nombre_caja;
    }

    public Integer getMefectivo() {
        return mefectivo;
    }

    public void setMefectivo(Integer mefectivo) {
        this.mefectivo = mefectivo;
    }

    public Integer getMtarjeta() {
        return mtarjeta;
    }

    public void setMtarjeta(Integer mtarjeta) {
        this.mtarjeta = mtarjeta;
    }
    
    
    
}
